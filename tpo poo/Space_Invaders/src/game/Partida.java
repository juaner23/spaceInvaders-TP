

package game;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Partida {
    private final int ANCHO = 800, ALTO = 600; // Constantes de entorno (SRP: Responsabilidad de Partida)
    private final double VELOCIDAD_INVASOR = 0.75;
    private final int BAJADA_INVASORES = 20;
    private final int PUNTOS_POR_INVASOR = 10;
    private final int FILAS_INVASORES = 3, COLUMNAS_INVASORES = 5;
    private final int ESPACIO_X_INVASORES = 50, ESPACIO_Y_INVASORES = 35;
    private final long COOLDOWN_JUGADOR_MS = 200;

    private Jugador jugador;
    private List<Bala> balas = new ArrayList<>();
    private List<Invasor> invasores = new ArrayList<>();
    private List<Muro> muros = new ArrayList<>();

    private int puntaje = 0;
    private int nivel = 1;
    private int sentido = 1;
    private long ultimoDisparoJugador = 0;

    public Partida() {


        this.jugador = new Jugador(ANCHO, ALTO);
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
            Rectangle rB = new Rectangle((int)b.getX(), (int)b.getY(), b.getAncho(), b.getAlto());
            Rectangle rJ = new Rectangle((int)jugador.getPosicionX(), (int)jugador.getPosicionY(), jugador.getAncho(), jugador.getAltura());
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

    public void dispararJugador() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparoJugador < COOLDOWN_JUGADOR_MS) return;
        ultimoDisparoJugador = ahora;
        double x = jugador.getPosicionX() + jugador.getAncho()/2.0 - 2;
        double y = jugador.getPosicionY() - 12;
        balas.add(new Bala(x, y, 0));
    }

    // MÉTODO CORREGIDO: Pasa el límite ANCHO, como requiere Jugador.mover(double, int)
    public void moverJugador(double deltaX) {
        jugador.mover(deltaX, ANCHO); // <--- CORRECCIÓN DE ERROR
    }

    public Jugador getJugador() { return jugador; }
    public List<Bala> getBalas() { return balas; }
    public List<Invasor> getInvasores() { return invasores; }
    public List<Muro> getMuros() { return muros; }
    public int getPuntaje() { return puntaje; }
    public int getNivel() { return nivel; }
}
        //QUEDA COMENTADO, NO RETORNA
        //for (Invasor inv : invasores) {
        // if (inv.estaVivo() && inv.getY() + inv.getAlto() >= 500) return true;
        //}
        //return jugador.getVidas() <= 0;
   // }
//}

