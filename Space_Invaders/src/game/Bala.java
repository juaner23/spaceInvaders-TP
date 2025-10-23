
package game;

public class Bala {
    private double x, y;
    private final int ancho = 4, alto = 12;
    private double velocidad = 6.0;
    private boolean activa = true;
    private int lanzador; // 0 jugador, 1 invasor

    public Bala(double x, double y, int lanzador) {
        this.x = x; this.y = y; this.lanzador = lanzador;
    }

    public void mover() { if (lanzador == 0) y -= velocidad; else y += velocidad; }
    public void desactivar() { activa = false; }

    public boolean estaActiva() { return activa; }
    public double getX() { return x; }
    public double getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getLanzador() { return lanzador; }
}