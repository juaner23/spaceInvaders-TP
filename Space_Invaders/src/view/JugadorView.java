
package view;

public class JugadorView {
    private final int vidas;
    private final int[][] posicion;
    private final String imagen;

    public JugadorView(int vid, int[][] pos, String img) {
        vidas = vid;
        imagen = img;
        posicion = new int[2][2];
        posicion[0][0] = pos[0][0];
        posicion[0][1] = pos[0][1];
        posicion[1][0] = pos[1][0];
        posicion[1][1] = pos[1][1];
    }

    public int viewVidas() { return vidas; }
    public int[] viewPosicion() { return new int[]{posicion[0][0], posicion[1][0]}; }
    public String getImagen() { return imagen; }
}