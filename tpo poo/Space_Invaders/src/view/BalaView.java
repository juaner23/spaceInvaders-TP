
package view;

public class BalaView {
    private final int[][] posicion;
    private final boolean activa;
    private final String imagen;




    public BalaView(int[][] pos, boolean a, String img) {
        imagen = img;
        activa = a;
        posicion = new int[2][2];
        posicion[0][0] = pos[0][0];
        posicion[0][1] = pos[0][1];
        posicion[1][0] = pos[1][0];
        posicion[1][1] = pos[1][1];
    }


    public int[] viewPosicion() { return new int[]{posicion[0][0], posicion[1][0]}; }
    public boolean viewEstado() { return activa; }
    public String getImagen() { return imagen; }



}