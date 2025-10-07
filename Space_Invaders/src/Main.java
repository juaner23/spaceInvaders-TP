import ui.GamePanel;  // Importa GamePanel del paquete ui/.
import javax.swing.*;  // Para JFrame y SwingUtilities.

// Sin "package" aquí - está en raíz src/ (estándar para main simple).

public  class Main {  // Clase en inglés, sin paquete.
    public static void main(String[] args) {
        // Por qué invokeLater: Corre GUI en thread separado (buena práctica Swing).
        SwingUtilities.invokeLater(() -> {
            // Crea la ventana principal (JFrame).
            JFrame ventana = new JFrame("Space Invaders - Movimiento con Teclas (JDK 25)");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Cierra la app al presionar X.
            ventana.setResizable(false);  // No permite cambiar tamaño (fijo 800x600).

            // Crea el panel de juego y lo agrega a la ventana (en el centro).
            GamePanel panel = new GamePanel();
            ventana.add(panel);
            ventana.pack();  // Ajusta el tamaño de la ventana al panel.
            ventana.setLocationRelativeTo(null);  // Centra la ventana en tu pantalla.
            ventana.setVisible(true);  // Muestra la ventana.

            // Asegura que el panel tenga foco para capturar teclas desde el inicio.
            panel.requestFocusInWindow();

            // Mensaje de debug en consola (en español).
            System.out.println("Ventana abierta - Usa flechas ←/→ para mover la nave. Posición inicial: " + panel.obtenerJugador());
        });
    }
}