import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// Interfaccia per definire il comportamento di sfondo del pulsante
interface BackgroundStrategy {
    Color getBackgroundColor();
}

// Implementazione di default della strategia di sfondo
class DefaultBackgroundStrategy implements BackgroundStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(144, 15, 177);
    }
}

// Implementazione della strategia di sfondo per lo stato di hover
class HoverBackgroundStrategy implements BackgroundStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(100, 0, 120);
    }
}

// Implementazione della strategia di sfondo per lo stato cliccato
class PressedBackgroundStrategy implements BackgroundStrategy {
    @Override
    public Color getBackgroundColor() {
        return new Color(80, 0, 100);
    }
}

public class RoundedButton extends JButton {
    private BackgroundStrategy defaultBackgroundStrategy;
    private BackgroundStrategy hoverBackgroundStrategy;
    private BackgroundStrategy pressedBackgroundStrategy;

    private BackgroundStrategy currentBackgroundStrategy;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setForeground(Color.WHITE);
        setBackground(new Color(144, 15, 177)); // Colore dello sfondo
        setFont(new Font("Arial", Font.PLAIN, 20)); // Specifica il font e le dimensioni

        defaultBackgroundStrategy = new DefaultBackgroundStrategy();
        hoverBackgroundStrategy = new HoverBackgroundStrategy();
        pressedBackgroundStrategy = new PressedBackgroundStrategy();

        currentBackgroundStrategy = defaultBackgroundStrategy;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBackgroundStrategy = hoverBackgroundStrategy;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBackgroundStrategy = defaultBackgroundStrategy; // Ripristina il colore originale
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentBackgroundStrategy = pressedBackgroundStrategy;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentBackgroundStrategy = hoverBackgroundStrategy;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(currentBackgroundStrategy.getBackgroundColor());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Disegna il rettangolo con angoli arrotondati
        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(170, 40); // Imposta le dimensioni del pulsante
    }
}//strategy
