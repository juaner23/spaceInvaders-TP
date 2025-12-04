

package Gui;

import controlador.controlador;

import game.Jugador;
import game.Invasor;
import game.Bala;
import game.Muro;

import view.JugadorView;
import view.InvasorView;
import view.BalaView;
import view.MuroView;
import view.PartidaView;

import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.List;

public class GamePanel extends JPanel {
    private final controlador ctrl;

    private final Image imgBateria   =   new ImageIcon(getClass().getResource("imagenes/Bateria.png")).getImage();
    private final Image imgNave      = new ImageIcon(getClass().getResource("imagenes/Nave.png")).getImage();
    private final Image imgProyectil =  new ImageIcon(getClass().getResource("imagenes/Proyectil.png")).getImage();
    private final Image imgMuro      = new ImageIcon(getClass().getResource("imagenes/Muro_energia.png")).getImage();

    public GamePanel() {
        this.ctrl = controlador.obtenerInstancia();
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                ctrl.manejarTeclas(e);
            }
        });
        setFocusable(true);
        requestFocusInWindow();


        Timer gameTimer = new Timer(16, e -> {
            ctrl.getPartida().actualizar(); // Llama al método del Modelo


            if (ctrl.getPartida().esGanador() || ctrl.getPartida().esPerdedor()) {
                ((Timer)e.getSource()).stop(); // Detiene el Timer
                manejarFinJuego();
            }

            repaint(); // Repinta la pantalla
        });
        gameTimer.start();

        ctrl.iniciarJuego(); // Llama al método del Controlador para iniciar el Modelo
    }

    private void manejarFinJuego() {

        String nombre = JOptionPane.showInputDialog(this, "Ingresa tu nombre:", "Game Over", JOptionPane.QUESTION_MESSAGE);

        if (nombre != null && !nombre.trim().isEmpty()) {
            ctrl.guardarPuntaje(nombre, ctrl.getPuntaje());

            List<String> topList = ctrl.getTopRanking(5);
            String top = String.join("\n", topList);

            JOptionPane.showMessageDialog(this, "Ranking Top 5:\n" + top, "Ranking", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);




        Jugador jugador = ctrl.getJugador();
        int[][] posJ = {{(int)jugador.getPosicionX(), (int)jugador.getPosicionX() + jugador.getAncho()},
                {(int)jugador.getPosicionY(), (int)jugador.getPosicionY() + jugador.getAltura()}};
        JugadorView jv = new JugadorView(jugador.getVidas(), posJ, "Bateria.png");

        if (imgBateria != null)
            g.drawImage(imgBateria, jv.viewPosicion()[0], jv.viewPosicion()[1], 50, 30, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(jv.viewPosicion()[0], jv.viewPosicion()[1], 50, 30);
        }


        List<Invasor> invasores = ctrl.getInvasores();
        for (Invasor inv : invasores) {
            if (!inv.estaVivo()) continue;
            int[][] posI = {{(int)inv.getX(), (int)inv.getX() + inv.getAncho()},
                    {(int)inv.getY(), (int)inv.getY() + inv.getAlto()}};
            InvasorView iv = new InvasorView(posI, inv.estaVivo(), "Nave.png");

            if (imgNave != null)
                g.drawImage(imgNave, iv.viewPosicion()[0], iv.viewPosicion()[1], 40, 30, null);
            else {
                g.setColor(Color.RED);
                g.fillRect(iv.viewPosicion()[0], iv.viewPosicion()[1], 40, 30);
            }
        }


        List<Bala> balas = ctrl.getBalas();
        for (Bala b : balas) {
            if (!b.estaActiva()) continue;
            int[][] posB = {{(int)b.getX(), (int)b.getX() + b.getAncho()},
                    {(int)b.getY(), (int)b.getY() + b.getAlto()}};
            BalaView bv = new BalaView(posB, b.estaActiva(), "Proyectil.png");

            if (imgProyectil != null)
                g.drawImage(imgProyectil, bv.viewPosicion()[0], bv.viewPosicion()[1], 4, 12, null);
            else {
                g.setColor(Color.YELLOW);
                g.fillRect(bv.viewPosicion()[0], bv.viewPosicion()[1], 4, 12);
            }
        }


        List<Muro> muros = ctrl.getMuros();
        for (Muro muro : muros) {
            if (!muro.estaVivo()) continue;
            int[][] posM = {{(int)muro.getX(), (int)muro.getX() + muro.getAncho()},
                    {(int)muro.getY(), (int)muro.getY() + muro.getAlto()}};
            MuroView mv = new MuroView(posM, muro.estaVivo(), "Muro_energia.png");

            if (imgMuro != null)
                g.drawImage(imgMuro, mv.viewPosicion()[0], mv.viewPosicion()[1], 20, 20, null);
            else {
                g.setColor(Color.GREEN);
                g.fillRect(mv.viewPosicion()[0], mv.viewPosicion()[1], 20, 20);
            }
        }


        PartidaView pv = new PartidaView(ctrl.getPuntaje(), ctrl.getNivel(), ctrl.getVidas());
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + pv.viewPuntaje(), 350, 20);
        g.drawString("Nivel: " + pv.viewNivel(), 10, 20);
        g.drawString("Vidas: " + pv.viewVidas(), 680, 20);
    }
}