package controlador;

import game.Partida;
import game.Ranking;
import game.Muro;
import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.PartidaView;
import view.MuroView;
import view.RankingView;

import javax.swing.Timer;
import java.awt.event.KeyEvent;
import java.util.List;

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
        this.partida = new Partida();
        this.partida.iniciarNivel();
    }

    public void manejarTeclas(KeyEvent e) {
        int tecla = e.getKeyCode();
        if (tecla == KeyEvent.VK_LEFT)      partida.moverJugador(-5.0);
        else if (tecla == KeyEvent.VK_RIGHT)     partida.moverJugador(5.0);
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
            } else if (partida.esPerdedor()) {
                temporizador.stop();
                gameOver();
                System.out.println("Perdiste!");
            }
        });
        temporizador.start();



    }





    public void gameOver() {
        if (ranking != null) {
            String nombre = javax.swing.JOptionPane.showInputDialog(null, "Ingresa tu nombre:", "Game Over", javax.swing.JOptionPane.QUESTION_MESSAGE);
            if (nombre != null && !nombre.trim().isEmpty()) {
                ranking.guardarPuntajeBest(nombre, partida.getPuntaje());
                RankingView rv = obtenerRankingView(5);
                String top = String.join("\n", rv.getTopPuntajes());
                javax.swing.JOptionPane.showMessageDialog(null, "Ranking Top 5:\n" + top, "Ranking", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }


        }
    }

    public JugadorView obtenerJugadorView() { return partida.generarJugadorView(); }
    public List<InvasorView> obtenerInvasoresView() { return partida.generarInvasoresView(); }
    public List<BalaView> obtenerBalasView() { return partida.generarBalasView(); }
    public List<MuroView> obtenerMurosView()  { return partida.generarMurosView(); }
    public PartidaView obtenerPartidaView() { return partida.generarPartidaView(); }



    public Partida getPartida() { return partida; }
    public game.Jugador getJugador() { return partida.getJugador(); }
    public List<game.Bala> getBalas() { return partida.getBalas(); }
    public List<game.Invasor> getInvasores() { return partida.getInvasores(); }
    public List<Muro> getMuros() { return partida.getMuros(); }
    public int getPuntaje() { return partida.getPuntaje(); }
    public int getVidas() { return partida.getJugador().getVidas(); }
    public int getNivel() { return partida.getNivel(); }

    public void setRanking(Ranking r) { this.ranking = r; }
    public RankingView obtenerRankingView(int n) { return new RankingView(ranking.topN(n)); }



}