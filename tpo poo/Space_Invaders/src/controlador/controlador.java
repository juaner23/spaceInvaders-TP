

package controlador;

import game.Partida;
import game.Ranking;
import game.Muro;

import java.awt.event.KeyEvent;
import java.util.List;

public class controlador {
    private static controlador instancia;
    public static controlador obtenerInstancia() {
        if (instancia == null) instancia = new controlador();
        return instancia;
    }

    private Partida partida;
    private Ranking ranking;

    private controlador() {
        this.partida = new Partida();
    }

    public void manejarTeclas(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT)      partida.moverJugador(-5.0);
        else if (tecla == KeyEvent.VK_RIGHT)     partida.moverJugador(5.0);
        else if (tecla == KeyEvent.VK_SPACE) partida.dispararJugador();
    }

    //    método que la Vista llama para iniciar el modelo
    public void iniciarJuego() {
        this.partida.iniciarNivel();
    }


    public Partida getPartida() { return partida; }
    public game.Jugador getJugador() { return partida.getJugador(); }
    public List<game.Bala> getBalas() { return partida.getBalas(); }
    public List<game.Invasor> getInvasores() { return partida.getInvasores(); }
    public List<Muro> getMuros() { return partida.getMuros(); }
    public int getPuntaje() { return partida.getPuntaje(); }
    public int getVidas() { return partida.getJugador().getVidas(); }
    public int getNivel() { return partida.getNivel(); }

    public void setRanking(Ranking r) { this.ranking = r; }


    public List<String> getTopRanking(int n) {
        return ranking.topN(n);
    }

    public void guardarPuntaje(String nombre, int puntaje) {
        ranking.guardarPuntajeBest(nombre, puntaje);
    }
}