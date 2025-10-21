// src/Gui/GamePanel.java
package Gui;

import game.Jugador;
import game.Bala;
import game.Invasor;
import controlador.controlador;

import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class GamePanel extends JPanel {
    private final controlador ctrl;

    private final Image imgBateria   = new ImageIcon(getClass().getResource("Bateria.png")).getImage();
    private final Image imgNave      = new ImageIcon(getClass().getResource("Nave.png")).getImage();
    private final Image imgProyectil = new ImageIcon(getClass().getResource("Proyectil.png")).getImage();

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

        new Timer(16, e -> repaint()).start();
        ctrl.iniciarBucle(16);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Jugador j = ctrl.obtenerJugador();
        if (imgBateria != null)
            g.drawImage(imgBateria, (int) j.obtenerPosicionX(), (int) j.obtenerPosicionY(), j.obtenerAncho(), j.obtenerAltura(), null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect((int) j.obtenerPosicionX(), (int) j.obtenerPosicionY(), j.obtenerAncho(), j.obtenerAltura());
        }

        for (Invasor inv : ctrl.obtenerInvasores()) {
            if (!inv.estaVivo()) continue;
            if (imgNave != null)
                g.drawImage(imgNave, (int) inv.getX(), (int) inv.getY(), inv.getAncho(), inv.getAlto(), null);
            else {
                g.setColor(Color.RED);
                g.fillRect((int) inv.getX(), (int) inv.getY(), inv.getAncho(), inv.getAlto());
            }
        }

        for (Bala b : ctrl.obtenerBalas()) {
            if (imgProyectil != null)
                g.drawImage(imgProyectil, (int) b.getX(), (int) b.getY(), b.getAncho(), b.getAlto(), null);
            else {
                g.setColor(Color.YELLOW);
                g.fillRect((int) b.getX(), (int) b.getY(), b.getAncho(), b.getAlto());
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + ctrl.obtenerPuntaje(), 350, 20);
        g.drawString("Nivel: " + ctrl.obtenerNivel(), 10, 20);
        g.drawString("Vidas: " + ctrl.obtenerVidas(), 680, 20);
    }
}