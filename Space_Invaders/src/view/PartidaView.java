
package view;

public class PartidaView {
    private final int puntaje, nivel, vidas;

    public PartidaView(int p, int n, int v) {
        puntaje = p; nivel = n; vidas = v;
    }

    public int viewPuntaje() { return puntaje; }
    public int viewNivel() { return nivel; }
    public int viewVidas() { return vidas; }
}