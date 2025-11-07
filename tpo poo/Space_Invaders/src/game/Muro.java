package game;

import java.awt.Rectangle;

public class Muro {
    private double x, y;
    private int ancho = 20, alto = 20;
    private int hp = 4; // 4 hits para destruir



    public Muro(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean estaVivo() {
        return hp > 0;
    }



    public void reducirHp() {
            if (hp > 0) hp--;
    }




    public double getX() { return x; }
    public double getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }

    public boolean colisionaCon(Bala b) {
        if (!estaVivo()) return false;
        Rectangle rM = new Rectangle((int)getX(), (int)getY(), getAncho(), getAlto());
        Rectangle rB = new Rectangle((int)b.getX(), (int)b.getY(), b.getAncho(), b.getAlto());
        return rM.intersects(rB);
    }
}