

import Gui.GamePanel;
import game.Ranking;
import controlador.controlador;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Space Invaders");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setResizable(false);

            GamePanel panel = new GamePanel(); // Crea la Vista

            controlador ctrl = controlador.obtenerInstancia();
            ctrl.setRanking(new Ranking());


            ctrl.iniciarJuego();

            ventana.add(panel);
            ventana.pack();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);

            panel.requestFocusInWindow();
        });
    }
}