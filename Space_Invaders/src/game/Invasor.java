// src/game/Invasor.java
package game;

public class Invasor {
    private double x, y;
    private int ancho = 40, alto = 30;
    private boolean vivo = true;

    public Invasor(double x, double y) { this.x = x; this.y = y; }

    public void moverHorizontal(int sentido, double v) { x += v * sentido; }
    public void bajar(double dy) { y += dy; }

    public boolean estaVivo() { return vivo; }
    public void destruir() { vivo = false; }

    public double getX() { return x; }
    public double getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
}