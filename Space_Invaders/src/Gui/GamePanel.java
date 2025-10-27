// src/Gui/GamePanel.java
package Gui;

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

        var jv = ctrl.obtenerJugadorView();
        if (imgBateria != null)
            g.drawImage(imgBateria, jv.viewPosicion()[0], jv.viewPosicion()[1], 50, 30, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(jv.viewPosicion()[0], jv.viewPosicion()[1], 50, 30);
        }

        for (var iv : ctrl.obtenerInvasoresView()) {
            if (!iv.viewEstado()) continue;
            if (imgNave != null)
                g.drawImage(imgNave, iv.viewPosicion()[0], iv.viewPosicion()[1], 40, 30, null);
            else {
                g.setColor(Color.RED);
                g.fillRect(iv.viewPosicion()[0], iv.viewPosicion()[1], 40, 30);
            }
        }

        for (var bv : ctrl.obtenerBalasView()) {
            if (!bv.viewEstado()) continue;
            if (imgProyectil != null)
                g.drawImage(imgProyectil, bv.viewPosicion()[0], bv.viewPosicion()[1], 4, 12, null);
            else {
                g.setColor(Color.YELLOW);
                g.fillRect(bv.viewPosicion()[0], bv.viewPosicion()[1], 4, 12);
            }
        }

        var pv = ctrl.obtenerPartidaView();
        g.setColor(Color.WHITE);
        g.drawString("Puntos: " + pv.viewPuntaje(), 350, 20);
        g.drawString("Nivel: " + pv.viewNivel(), 10, 20);
        g.drawString("Vidas: " + pv.viewVidas(), 680, 20);
    }
}