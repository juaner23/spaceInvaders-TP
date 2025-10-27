// src/game/Partida.java
package game;

import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Partida {
    private final int ANCHO = 800, ALTO = 600;
    private final double VELOCIDAD_INVASOR = 2.0;
    private final int BAJADA_INVASORES = 20;
    private final int PUNTOS_POR_INVASOR = 10;
    private final int FILAS_INVASORES = 3, COLUMNAS_INVASORES = 5;
    private final int ESPACIO_X_INVASORES = 50, ESPACIO_Y_INVASORES = 35;
    private final long COOLDOWN_JUGADOR_MS = 200;

    private Jugador jugador = new Jugador();
    private List<Bala> balas = new ArrayList<>();
    private List<Invasor> invasores = new ArrayList<>();
    private int puntaje = 0;
    private int nivel = 1;
    private int sentido = 1;
    private long ultimoDisparoJugador = 0;

    public Partida() {
        generarInvasores();
    }

    public void reiniciar() {
        jugador = new Jugador();
        balas.clear();
        invasores.clear();
        puntaje = 0;
        nivel = 1;
        sentido = 1;
        ultimoDisparoJugador = 0;
        generarInvasores();
    }

    public void actualizar() {
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
            inv.moverHorizontal(sentido, VELOCIDAD_INVASOR);
            if (inv.getX() <= 0 || inv.getX() + inv.getAncho() >= ANCHO) tocarBorde = true;
        }
        if (tocarBorde) {
            sentido *= -1;
            for (Invasor inv : invasores) if (inv.estaVivo()) inv.bajar(BAJADA_INVASORES);
        }
    }

    private void colisionesBalaInvasor() {
        for (Bala b : new ArrayList<>(balas)) {
            if (!b.estaActiva() || b.getLanzador() != 0) continue;
            for (Invasor inv : invasores) {
                if (!inv.estaVivo()) continue;
                if (b.colisionaCon(inv)) {
                    inv.destruir();
                    b.desactivar();
                    puntaje += PUNTOS_POR_INVASOR;
                    break;
                }
            }
        }
        balas.removeIf(b -> !b.estaActiva());
    }

    private void generarInvasores() {
        invasores.clear();
        for (int i = 0; i < FILAS_INVASORES; i++) {
            for (int j = 0; j < COLUMNAS_INVASORES; j++) {
                invasores.add(new Invasor(280 + j * ESPACIO_X_INVASORES, 40 + i * ESPACIO_Y_INVASORES));
            }
        }
    }

    public void dispararJugador() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparoJugador < COOLDOWN_JUGADOR_MS) return;
        ultimoDisparoJugador = ahora;
        double x = jugador.obtenerPosicionX() + jugador.obtenerAncho()/2.0 - 2;
        double y = jugador.obtenerPosicionY() - 12;
        balas.add(new Bala(x, y, 0));
    }

    public void moverJugador(double deltaX) {
        jugador.mover(deltaX);
    }

    // Getters para controlador
    public Jugador obtenerJugador() { return jugador; }
    public List<Bala> obtenerBalas() { return balas; }
    public List<Invasor> obtenerInvasores() { return invasores; }
    public int obtenerPuntaje() { return puntaje; }
    public int obtenerNivel() { return nivel; }

    // Generar vistas
    public JugadorView generarJugadorView() {
        int[][] pos = {{(int)jugador.obtenerPosicionX(), (int)jugador.obtenerPosicionX() + jugador.obtenerAncho()},
                {(int)jugador.obtenerPosicionY(), (int)jugador.obtenerPosicionY() + jugador.obtenerAltura()}};
        return new JugadorView(jugador.obtenerVidas(), pos, "Bateria.png");
    }

    public List<InvasorView> generarInvasoresView() {
        List<InvasorView> vistas = new ArrayList<>();
        for (Invasor inv : invasores) {
            int[][] pos = {{(int)inv.getX(), (int)inv.getX() + inv.getAncho()},
                    {(int)inv.getY(), (int)inv.getY() + inv.getAlto()}};
            vistas.add(new InvasorView(pos, inv.estaVivo(), "Nave.png"));
        }
        return vistas;
    }

    public List<BalaView> generarBalasView() {
        List<BalaView> vistas = new ArrayList<>();
        for (Bala b : balas) {
            int[][] pos = {{(int)b.getX(), (int)b.getX() + b.getAncho()},
                    {(int)b.getY(), (int)b.getY() + b.getAlto()}};
            vistas.add(new BalaView(pos, b.estaActiva(), "Proyectil.png"));
        }
        return vistas;
    }

    public PartidaView generarPartidaView() {
        return new PartidaView(puntaje, nivel, jugador.obtenerVidas());
    }
}
