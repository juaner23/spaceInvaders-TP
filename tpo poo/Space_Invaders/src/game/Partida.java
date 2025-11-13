package game;

import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;
import view.MuroView;

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
    private List<Muro> muros = new ArrayList<>();

    private int puntaje = 0;
    private int nivel = 1;
    private int sentido = 1;
    private long ultimoDisparoJugador = 0;
    private boolean juegoTerminado = false;

    public Partida() {
        // Solo inicializa estructuras
    }

    public void iniciarNivel() {
        generarInvasores();
        generarMuros();
    }


    public void actualizar() {
        moverBalas();
        moverInvasores();
        colisionesBalaInvasor();
        colisionesBalaJugador();
        colisionesBalaMuro();
        dispararInvasores();

        if (esPerdedor() || esGanador()) {
            juegoTerminado = true;
        }
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

    private void colisionesBalaJugador() {
        for (Bala b : new ArrayList<>(balas)) {
            if (!b.estaActiva() || b.getLanzador() != 1) continue;
            Rectangle rB = new Rectangle((int) b.getX(), (int) b.getY(), b.getAncho(), b.getAlto());
            Rectangle rJ = new Rectangle((int) jugador.getPosicionX(), (int) jugador.getPosicionY(), jugador.getAncho(), jugador.getAltura());
            if (rB.intersects(rJ)) {
                jugador.perderVida();
                b.desactivar();
                break;
            }
        }
        balas.removeIf(b -> !b.estaActiva());
    }

    private void colisionesBalaMuro() {
        for (Bala b : new ArrayList<>(balas)) {
            if (!b.estaActiva()) continue;
            for (Muro muro : muros) {
                if (!muro.estaVivo()) continue;
                if (muro.colisionaCon(b)) {
                    muro.reducirHp();
                    b.desactivar();
                    break;
                }

            }
        }
        balas.removeIf(b -> !b.estaActiva());
        muros.removeIf(m -> !m.estaVivo());
    }

    private void generarInvasores() {
        invasores.clear();
        for (int i = 0; i < FILAS_INVASORES; i++) {
            for (int j = 0; j < COLUMNAS_INVASORES; j++) {
                invasores.add(new Invasor(280 + j * ESPACIO_X_INVASORES, 40 + i * ESPACIO_Y_INVASORES));
            }
        }
    }

    private void generarMuros() {
        muros.clear();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                muros.add(new Muro(100 + i * 150 + j * 20, 450));

            }
        }
    }

    private void dispararInvasores() {
        if (Math.random() < 0.05) {
            for (Invasor inv : invasores) {
                if (inv.estaVivo()) {
                    double x = inv.getX() + inv.getAncho() / 2.0 - 2;
                    double y = inv.getY() + inv.getAlto();
                    balas.add(new Bala(x, y, 1));
                    break;
                }
            }
        }


    }

    public boolean esGanador() {
        for (Invasor inv : invasores) {
            if (inv.estaVivo()) return false;
        }
        return true;
    }

    public boolean esPerdedor() {
        for (Invasor inv : invasores) {
            if (inv.estaVivo() && inv.getY() + inv.getAlto() >= 500) return true;
        }
        return jugador.getVidas() <= 0;
    }
    public PartidaView generarPartidaView() {
        return new PartidaView(puntaje, nivel, jugador.getVidas());
    }

    public void dispararJugador() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparoJugador < COOLDOWN_JUGADOR_MS) return;
        ultimoDisparoJugador = ahora;
        double x = jugador.getPosicionX() + jugador.getAncho() / 2.0 - 2;
        double y = jugador.getPosicionY() - 12;
        balas.add(new Bala(x, y, 0));
    }

    public void moverJugador(double deltaX) {
        jugador.mover(deltaX);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public List<Bala> getBalas() {
        return balas;
    }

    public List<Invasor> getInvasores() {
        return invasores;
    }

    public List<Muro> getMuros() {
        return muros;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public JugadorView generarJugadorView() {
        int[][] pos = {{(int) jugador.getPosicionX(), (int) jugador.getPosicionX() + jugador.getAncho()},
                {(int) jugador.getPosicionY(), (int) jugador.getPosicionY() + jugador.getAltura()}};
        return new JugadorView(jugador.getVidas(), pos, "Bateria.png");
    }

    public List<InvasorView> generarInvasoresView() {
        List<InvasorView> vistas = new ArrayList<>();
        for (Invasor inv : invasores) {
            int[][] pos = {{(int) inv.getX(), (int) inv.getX() + inv.getAncho()},
                    {(int) inv.getY(), (int) inv.getY() + inv.getAlto()}};
            vistas.add(new InvasorView(pos, inv.estaVivo(), "Nave.png"));
        }
        return vistas;
    }

    public List<BalaView> generarBalasView() {
        List<BalaView> vistas = new ArrayList<>();
        for (Bala b : balas) {
            int[][] pos = {{(int) b.getX(), (int) b.getX() + b.getAncho()},
                    {(int) b.getY(), (int) b.getY() + b.getAlto()}};
            vistas.add(new BalaView(pos, b.estaActiva(), "Proyectil.png"));
        }
        return vistas;
    }

    public List<MuroView> generarMurosView() {
        List<MuroView> vistas = new ArrayList<>();
        for (Muro muro : muros) {
            int[][] pos = {{(int) muro.getX(), (int) muro.getX() + muro.getAncho()},
                    {(int) muro.getY(), (int) muro.getY() + muro.getAlto()}};
            vistas.add(new MuroView(pos, muro.estaVivo(), "Muro_energia.png"));
        }
        return vistas;
    }


    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

}

