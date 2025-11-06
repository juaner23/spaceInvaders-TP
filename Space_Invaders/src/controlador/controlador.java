package controlador;

import game.Partida;
import game.Ranking;
import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;
import view.RankingView;

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

    private Partida partida;
    private Timer temporizador;
    private Ranking ranking;

    private controlador() {
        // Arranca explícitamente: crea Partida y inicia nivel
        this.partida = new Partida();
        this.partida.iniciarNivel();
    }

    public void manejarTeclas(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT)  partida.moverJugador(-5.0);
        else if (tecla == KeyEvent.VK_RIGHT) partida.moverJugador(5.0);
        else if (tecla == KeyEvent.VK_SPACE) partida.dispararJugador();
    }

    public void iniciarBucle(int intervaloMs) {
        if (temporizador != null) temporizador.stop();
        temporizador = new Timer(intervaloMs, e -> {
            partida.actualizar();
            if (partida.esGanador()) {
                temporizador.stop();
                gameOver();
                System.out.println("Ganaste!");
                // No cerrar
            } else if (partida.esPerdedor()) {
                temporizador.stop();
                gameOver(); // Nuevo: llamar gameOver al perder
                System.out.println("Perdiste!");
                // No cerrar
            }
        });
        temporizador.start();
    }

    // Delegar vistas (bajo acoplamiento)
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

    // Ranking (de tu compañera)
    public void setRanking(Ranking r) {
        this.ranking = r;
    }

    public void gameOver() {
        if (ranking != null) {
            // Pedir nombre
            String nombre = javax.swing.JOptionPane.showInputDialog(null, "Ingresa tu nombre:", "Game Over", javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (nombre != null && !nombre.trim().isEmpty()) {
                ranking.guardarPuntajeBest(nombre, partida.obtenerPuntaje());
                // Mostrar ranking
                RankingView rv = obtenerRankingView(5);
                String top = String.join("\n", rv.getTopPuntajes());
                javax.swing.JOptionPane.showMessageDialog(null, "Ranking Top 5:\n" + top, "Ranking", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    public RankingView obtenerRankingView(int n) {
        return new RankingView(ranking.topN(n));
    }
}