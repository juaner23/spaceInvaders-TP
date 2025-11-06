package view;

public class MuroView {
    private final int[][] posicion;
    private final boolean vivo;
    private final String imagen;

    public MuroView(int[][] pos, boolean v, String img) {
        imagen = img;
        vivo = v;
        posicion = new int[2][2];
        posicion[0][0] = pos[0][0];
        posicion[0][1] = pos[0][1];
        posicion[1][0] = pos[1][0];
        posicion[1][1] = pos[1][1];
    }

    public int[] viewPosicion() { return new int[]{posicion[0][0], posicion[1][0]}; }
    public boolean viewEstado() { return vivo; }
    public String getImagen() { return imagen; }
}