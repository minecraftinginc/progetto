import javax.swing.*;
import java.awt.*;

public class RoundedPanel extends JPanel {
    private int radius;

    public RoundedPanel(int radius) {
        this.radius = radius;
        setOpaque(false); // Per rendere trasparente il pannello
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(radius, radius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
    }

    // Metodo per impostare il layout
    @Override
    public void setLayout(LayoutManager mgr) {
        super.setLayout(mgr);
    }

    // Metodo per impostare il colore di sfondo
    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
    }

    // Metodo per aggiungere componenti con i vincoli di griglia
    public void add(Component comp, GridBagConstraints constraints) {
        super.add(comp, constraints);
    }
}