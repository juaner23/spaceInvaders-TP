
package controlador;

import game.Partida;
import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.util.List;
import game.Jugador;
import game.Bala;
import game.Invasor;

public class controlador {
    private static controlador instancia;
    public static controlador obtenerInstancia() {
        if (instancia == null) instancia = new controlador();
        return instancia;
    }

    private Partida partida = new Partida();
    private Timer temporizador;

    private controlador() {}

    public void manejarTeclas(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT)  partida.moverJugador(-5.0);
        else if (tecla == KeyEvent.VK_RIGHT) partida.moverJugador(5.0);
        else if (tecla == KeyEvent.VK_SPACE) partida.dispararJugador();
    }

    public void iniciarBucle(int intervaloMs) {
        if (temporizador != null) temporizador.stop();
        temporizador = new Timer(intervaloMs, e -> partida.actualizar());
        temporizador.start();
    }

    // Delegar getters a Partida
    public JugadorView obtenerJugadorView() { return partida.generarJugadorView(); }
    public List<InvasorView> obtenerInvasoresView() { return partida.generarInvasoresView(); }
    public List<BalaView> obtenerBalasView() { return partida.generarBalasView(); }
    public PartidaView obtenerPartidaView() { return partida.generarPartidaView(); }

    // Para compatibilidad (si Gui necesita acceso directo)
    public Partida obtenerPartida() { return partida; }
    public Jugador obtenerJugador() { return partida.obtenerJugador(); }
    public List<Bala> obtenerBalas() { return partida.obtenerBalas(); }
    public List<Invasor> obtenerInvasores() { return partida.obtenerInvasores(); }
    public int obtenerPuntaje() { return partida.obtenerPuntaje(); }
    public int obtenerVidas() { return partida.obtenerJugador().obtenerVidas(); }
    public int obtenerNivel() { return partida.obtenerNivel(); }
}