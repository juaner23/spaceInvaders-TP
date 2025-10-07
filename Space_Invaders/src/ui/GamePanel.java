package ui;  // Paquete en inglés.

import game.Player;  // Import en inglés.
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;  // Nuevo: Para KeyListener.

public class GamePanel extends JPanel {  // Clase en inglés.
    private Player jugador;  // Referencia al jugador.
    private double velocidadMovimiento;  // Nueva: Velocidad por frame (en español).

    public GamePanel() {
        this.jugador = new Player();  // Crea el jugador.
        this.velocidadMovimiento = 5.0;  // Nueva: 5 píxeles por frame (ajustable).
        setPreferredSize(new Dimension(800, 600));  // Tamaño de la pantalla.
        setBackground(Color.BLACK);  // Fondo negro (espacio).

        // Nuevo: Escucha teclas (KeyListener).
        addKeyListener(new KeyAdapter() {  // KeyAdapter simplifica KeyListener.
            @Override
            public void keyPressed(KeyEvent e) {  // Se llama al presionar tecla.
                int tecla = e.getKeyCode();  // Código de la tecla (ej: VK_LEFT = 37).
                if (tecla == KeyEvent.VK_LEFT) {  // Flecha izquierda.
                    jugador.mover(-velocidadMovimiento);  // Mueve izquierda.
                } else if (tecla == KeyEvent.VK_RIGHT) {  // Flecha derecha.
                    jugador.mover(velocidadMovimiento);  // Mueve derecha.
                }
                repaint();  // Redibuja el panel (llama a paintComponent).
            }
        });

        // Nuevo: Configura foco para que escuche teclas (debe tener foco la ventana).
        setFocusable(true);
        requestFocusInWindow();  // Pide foco al iniciar.

        // Nuevo: Timer para loop de juego (actualiza cada 16ms ≈ 60 FPS).
        Timer temporizador = new Timer(16, e -> {
            // Aquí irán actualizaciones futuras (ej: gravedad, IA invasores).
            // Por ahora, solo redibuja (para movimiento suave si agregamos animaciones).
            repaint();  // Redibuja constantemente.
        });
        temporizador.start();  // Inicia el loop.
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);  // Limpia el panel.

        // Dibuja el jugador como rectángulo azul (usa posición actual).
        g.setColor(Color.BLUE);  // Color del jugador.
        int x = (int) jugador.obtenerPosicionX();  // Obtiene X actual (puede cambiar con teclas).
        int y = (int) jugador.obtenerPosicionY();  // Obtiene Y (fija).
        g.fillRect(x, y, jugador.obtenerAncho(), jugador.obtenerAltura());  // Dibuja rectángulo.

        // Opcional: Dibuja info debug (vidas).
        g.setColor(Color.WHITE);  // Color blanco para texto.
        g.drawString("Vidas: " + jugador.obtenerVidas(), 10, 20);  // Muestra vidas arriba-izquierda.
    }

    // Getter en español.
    public Player obtenerJugador() { return jugador; }
}
