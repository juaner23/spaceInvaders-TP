// src/controlador/controlador.java
package controlador;

import game.Jugador;
import game.Bala;
import game.Invasor;
import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;

import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class controlador {
    private static controlador instancia;
    public static controlador obtenerInstancia() {
        if (instancia == null) instancia = new controlador();
        return instancia;
    }

    private final int ANCHO = 800, ALTO = 600;

    private Jugador jugador = new Jugador();
    private List<Bala> balas = new ArrayList<>();
    private List<Invasor> invasores = new ArrayList<>();

    private double velJugador = 5.0;
    private double velInvasor = 2.0;
    private int sentido = 1;
    private int puntaje = 0;
    private int nivel = 1;

    private long ultimoDisparoJugador = 0;
    private final long cooldownJugadorMs = 200;

    private Timer temporizador;

    private controlador() {
        generarInvasores(3, 5, 280, 40, 50, 35);
    }

    public void manejarTeclas(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT)  jugador.mover(-velJugador);
        else if (tecla == KeyEvent.VK_RIGHT) jugador.mover(velJugador);
        else if (tecla == KeyEvent.VK_SPACE) dispararJugador();
    }

    private void dispararJugador() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparoJugador < cooldownJugadorMs) return;
        ultimoDisparoJugador = ahora;
        double x = jugador.obtenerPosicionX() + jugador.obtenerAncho()/2.0 - 2;
        double y = jugador.obtenerPosicionY() - 12;
        balas.add(new Bala(x, y, 0));
    }

    public void iniciarBucle(int intervaloMs) {
        if (temporizador != null) temporizador.stop();
        temporizador = new Timer(intervaloMs, e -> actualizarJuego());
        temporizador.start();
    }

    private void actualizarJuego() {
        moverBalas();
        moverInvasores();
        colisionesBalaInvasor();
    }

    private void moverBalas() {
        Iterator<Bala> it = balas.iterator();
        while (it.hasNext()) {
            Bala b = it.next();
            b.mover();
            if (b.getY() < -b.getAlto() || b.getY() > ALTO) b.desactivar();
            if (!b.estaActiva()) it.remove();
        }
    }

    private void moverInvasores() {
        boolean tocarBorde = false;
        for (Invasor inv : invasores) {
            if (!inv.estaVivo()) continue;
            inv.moverHorizontal(sentido, velInvasor);
            if (inv.getX() <= 0 || inv.getX() + inv.getAncho() >= ANCHO) tocarBorde = true;
        }
        if (tocarBorde) {
            sentido *= -1;
            for (Invasor inv : invasores) if (inv.estaVivo()) inv.bajar(20);
        }
    }

    private void colisionesBalaInvasor() {
        for (Bala b : new ArrayList<>(balas)) {
            if (!b.estaActiva() || b.getLanzador() != 0) continue;
            Rectangle rB = new Rectangle((int)b.getX(), (int)b.getY(), b.getAncho(), b.getAlto());
            for (Invasor inv : invasores) {
                if (!inv.estaVivo()) continue;
                Rectangle rI = new Rectangle((int)inv.getX(), (int)inv.getY(), inv.getAncho(), inv.getAlto());
                if (rB.intersects(rI)) {
                    inv.destruir();
                    b.desactivar();
                    puntaje += 10;
                    break;
                }
            }
        }
        balas.removeIf(b -> !b.estaActiva());
    }

    private void generarInvasores(int filas, int cols, int x0, int y0, int dx, int dy) {
        invasores.clear();
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < cols; j++) {
                invasores.add(new Invasor(x0 + j * dx, y0 + i * dy));
            }
        }
    }

    public Jugador obtenerJugador() { return jugador; }
    public List<Bala> obtenerBalas() { return balas; }
    public List<Invasor> obtenerInvasores() { return invasores; }
    public int obtenerPuntaje() { return puntaje; }
    public int obtenerVidas() { return jugador.obtenerVidas(); }
    public int obtenerNivel() { return nivel; }

    // Métodos para vistas (inmutables)
    public JugadorView obtenerJugadorView() {
        int[][] pos = {{(int)jugador.obtenerPosicionX(), (int)jugador.obtenerPosicionX() + jugador.obtenerAncho()},
                {(int)jugador.obtenerPosicionY(), (int)jugador.obtenerPosicionY() + jugador.obtenerAltura()}};
        return new JugadorView(jugador.obtenerVidas(), pos, "Bateria.png");
    }

    public List<InvasorView> obtenerInvasoresView() {
        List<InvasorView> vistas = new ArrayList<>();
        for (Invasor inv : invasores) {
            int[][] pos = {{(int)inv.getX(), (int)inv.getX() + inv.getAncho()},
                    {(int)inv.getY(), (int)inv.getY() + inv.getAlto()}};
            vistas.add(new InvasorView(pos, inv.estaVivo(), "Nave.png"));
        }
        return vistas;
    }

    public List<BalaView> obtenerBalasView() {
        List<BalaView> vistas = new ArrayList<>();
        for (Bala b : balas) {
            int[][] pos = {{(int)b.getX(), (int)b.getX() + b.getAncho()},
                    {(int)b.getY(), (int)b.getY() + b.getAlto()}};
            vistas.add(new BalaView(pos, b.estaActiva(), "Proyectil.png"));
        }
        return vistas;
    }

    public PartidaView obtenerPartidaView() {
        return new PartidaView(puntaje, nivel, jugador.obtenerVidas());
    }
}