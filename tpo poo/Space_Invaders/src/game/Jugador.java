package game;

public class Jugador {
    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 600;
    private static final int ANCHO_JUGADOR = 50;
    private static final int ALTURA_JUGADOR = 30;

    private double posicionX;
    private double posicionY;
    private int vidas;

    public Jugador() {
        this.posicionX = (ANCHO_PANTALLA / 2.0) - (ANCHO_JUGADOR / 2.0);
        this.posicionY = ALTO_PANTALLA - ALTURA_JUGADOR - 10;
        this.vidas = 3;
    }

    public double getPosicionX() { return posicionX; }
    public double getPosicionY() { return posicionY; }
    public int getVidas() { return vidas; }
    public int getAncho() { return ANCHO_JUGADOR; }
    public int getAltura() { return ALTURA_JUGADOR; }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
        if (this.posicionX < 0) this.posicionX = 0;
        if (this.posicionX > ANCHO_PANTALLA - ANCHO_JUGADOR) this.posicionX = ANCHO_PANTALLA - ANCHO_JUGADOR;
    }

    public void mover(double deltaX) {
        setPosicionX(this.posicionX + deltaX);
    }

    public void perderVida() {
        if (vidas > 0) vidas--;
    }
}
