

package game;
import java.awt.Rectangle;
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



    public boolean colisionaCon(Bala b) {
        if (!estaVivo()) return false;
        Rectangle rI = new Rectangle((int)getX(), (int)getY(), getAncho(), getAlto());
        Rectangle rB = new Rectangle((int)b.getX(), (int)b.getY(), b.getAncho(), b.getAlto());
        return rI.intersects(rB);
    }
}