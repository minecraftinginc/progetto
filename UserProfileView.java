import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UserProfileView {
    private JFrame profileFrame;
    private JPanel topPanel;
    private JLabel usernameTextLabel;
    private Image currentImage;
    private Image hoverImage;
    private JLabel passLabel;
    private JLabel nameTextLabel;
    private JLabel surnameTextLabel;
    private JLabel emailTextLabel;
    private JLabel dateTextLabel;

    public UserProfileView(Image currentImage, Image hoverImage) {
        this.currentImage = currentImage;
        this.hoverImage = hoverImage; // Aggiunto il passaggio dell'immagine di hover
        profileFrame = new JFrame("Profilo Utente");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Assumi che "faviconIcon" sia un oggetto ImageIcon già definito
        profileFrame.setIconImage(new ImageIcon("icona.png").getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());

        topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Pulisci il pannello disegnando un rettangolo bianco delle stesse dimensioni del pannello
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());
                // Disegna l'immagine di hover solo se il mouse è sopra il pannello
                if (hoverImage != null && isMouseOver()) {
                    g.drawImage(hoverImage, 0, 0, 120, 120, this);
                } else if (currentImage != null) { // Disegna l'immagine corrente se non c'è l'immagine di hover
                    g.drawImage(currentImage, 0, 0, 120, 120, this);
                }
            }
            
            // Metodo per controllare se il mouse è sopra il pannello
            private boolean isMouseOver() {
                return topPanel.getMousePosition() != null && topPanel.getVisibleRect().contains(topPanel.getMousePosition());
            }
        };

        topPanel.setPreferredSize(new Dimension(100, 100));

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Nome:");
        gbc.gridy = 0;
        gbc.gridx = 0;
        bottomPanel.add(nameLabel, gbc);

        nameTextLabel = new JLabel(); // Dichiarazione come variabile di istanza
        gbc.gridx = 1;
        bottomPanel.add(nameTextLabel, gbc);

        JLabel surnameLabel = new JLabel("Cognome:");
        gbc.gridy = 1;
        gbc.gridx = 0;
        bottomPanel.add(surnameLabel, gbc);

        surnameTextLabel = new JLabel();
        gbc.gridx = 1;
        bottomPanel.add(surnameTextLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridy = 2;
        gbc.gridx = 0;
        bottomPanel.add(emailLabel, gbc);

        emailTextLabel = new JLabel();
        gbc.gridx = 1;
        bottomPanel.add(emailTextLabel, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridy = 3;
        gbc.gridx = 0;
        bottomPanel.add(passwordLabel, gbc);

        passLabel = new JLabel("********");
        gbc.gridx = 1;
        bottomPanel.add(passLabel, gbc);
        
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 4;
        gbc.gridx = 0;
        bottomPanel.add(usernameLabel, gbc);

        usernameTextLabel = new JLabel();
        gbc.gridx = 1;
        bottomPanel.add(usernameTextLabel, gbc);
        
        JLabel dateLabel = new JLabel("Date:");
        gbc.gridy = 5;
        gbc.gridx = 0;
        bottomPanel.add(dateLabel, gbc);

        dateTextLabel = new JLabel();
        gbc.gridx = 1;
        bottomPanel.add(dateTextLabel, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.WEST);

        profileFrame.add(mainPanel);
        profileFrame.setSize(200, 300);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.setVisible(true);
    }

    public void updateProfile(UserProfileModel model) {
        // Aggiorna i componenti della vista con i dati del modello
        nameTextLabel.setText(model.getName());
        surnameTextLabel.setText(model.getSurname());
        emailTextLabel.setText(model.getEmail());
        dateTextLabel.setText(model.getDate());
        usernameTextLabel.setText(model.getUsername());
        // Aggiorna l'immagine corrente nel pannello superiore
        currentImage = model.getCurrentImage();
        topPanel.repaint();
    }
    public JPanel getTopPanel() {
        return topPanel;
    }
    public void updateTopPanelImage(Image image) {
        if (topPanel != null) {
            Graphics g = topPanel.getGraphics();
            // Pulisci il pannello disegnando un rettangolo bianco delle stesse dimensioni del pannello
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, topPanel.getWidth(), topPanel.getHeight());
            // Disegna l'immagine corrente
            g.drawImage(image, 0, 0, 120, 120, topPanel);
        }
    }
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
        topPanel.repaint();
    }
}
