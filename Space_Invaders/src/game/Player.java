package game;  // Paquete en inglés (estándar).

public class Player {

    private static final int ANCHO_PANTALLA = 800;
    private static final int ALTO_PANTALLA = 600;
    private static final int ANCHO_JUGADOR = 50;
    private static final int ALTURA_JUGADOR = 30;


    private double posicionX;  // Posición horizontal.
    private double posicionY;  // Posición vertical (fija abajo).
    private int vidas;         // Vidas iniciales.

    // Constructor.
    public Player() {
        this.posicionX = (ANCHO_PANTALLA / 2.0) - (ANCHO_JUGADOR / 2.0);  // Centro inicial en X.
        this.posicionY = ALTO_PANTALLA - ALTURA_JUGADOR - 10;              // Abajo con margen en Y.
        this.vidas = 3;  // 3 vidas base.
    }

    // Getters
    public double obtenerPosicionX() { return posicionX; }
    public double obtenerPosicionY() { return posicionY; }
    public int obtenerVidas() { return vidas; }
    public int obtenerAncho() { return ANCHO_JUGADOR; }
    public int obtenerAltura() { return ALTURA_JUGADOR; }

    // Setter
    public void establecerPosicionX(double posicionX) {
        this.posicionX = posicionX;
        if (this.posicionX < 0) this.posicionX = 0;  // Borde izquierdo.
        if (this.posicionX > ANCHO_PANTALLA - ANCHO_JUGADOR) this.posicionX = ANCHO_PANTALLA - ANCHO_JUGADOR;  // Borde derecho.
    }

    //Método de movimiento
    public void mover(double deltaX) {
        establecerPosicionX(this.posicionX + deltaX);
    }

    // toString
    @Override
    public String toString() {
        return "Jugador{posicionX=" + (int)posicionX + ", posicionY=" + (int)posicionY + ", vidas=" + vidas + "}";
    }
}
  