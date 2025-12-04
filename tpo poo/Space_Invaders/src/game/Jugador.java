

package game;

public class Jugador {

    private static final int ANCHO_JUGADOR = 50;
    private static final int ALTURA_JUGADOR = 30;

    private double posicionX;
    private double posicionY;
    private int vidas;

    // CAMBIADO: Recibe las dimensiones para inicializar la posición
    public Jugador(int anchoPantalla, int altoPantalla) {
        this.posicionX = (anchoPantalla / 2.0) - (ANCHO_JUGADOR / 2.0);
        this.posicionY = altoPantalla - ALTURA_JUGADOR - 10;
        this.vidas = 3;
    }

    public double getPosicionX() { return posicionX; }
    public double getPosicionY() { return posicionY; }
    public int getVidas() { return vidas; }
    public int getAncho() { return ANCHO_JUGADOR; }
    public int getAltura() { return ALTURA_JUGADOR; }


    public void setPosicionX(double posicionX, int anchoPantalla) {
        this.posicionX = posicionX;
        if (this.posicionX < 0) this.posicionX = 0;
        if (this.posicionX > anchoPantalla - ANCHO_JUGADOR) this.posicionX = anchoPantalla - ANCHO_JUGADOR;
    }


    public void mover(double deltaX, int anchoPantalla) {
        setPosicionX(this.posicionX + deltaX, anchoPantalla);
    }

    public void perderVida() {
        if (vidas > 0) vidas--;
    }
}