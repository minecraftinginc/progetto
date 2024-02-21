import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UserProfileFrame extends JFrame {
    private JFrame profileFrame;
    private JPanel topPanel;
    private Image currentImage;
    private Image hoverImage;
    private Image newImage;
    private Image defaultImage;
    ImageIcon faviconIcon = new ImageIcon("icona.png");

    public UserProfileFrame(String username, String surname, String email,String name,String date,Image current) {
        profileFrame = new JFrame("Profilo Utente");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Assumi che "faviconIcon" sia un oggetto ImageIcon gi� definito
        profileFrame.setIconImage(faviconIcon.getImage());

        JPanel mainPanel = new JPanel(new BorderLayout());

        try {
            hoverImage = ImageIO.read(new File("user.png"));
            defaultImage = ImageIO.read(new File("spiaggia.jpg"));
            if (current != null)
                currentImage = current;
            else
                currentImage = defaultImage;
        } catch (IOException e) {
            e.printStackTrace();
            // Gestisci l'eccezione in base alle tue esigenze
        }

        topPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentImage != null) {
                    g.drawImage(currentImage, 0, 0, 100, 100, this);
                }
            }
        };

        topPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        newImage = ImageIO.read(selectedFile);
                        SharedImage.setCurrentImage(newImage); // Imposta la nuova immagine condivisa
                        topPanel.repaint();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                currentImage = hoverImage;
                topPanel.repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Image sharedImage = SharedImage.getCurrentImage();
                if (sharedImage == null)
                    currentImage = defaultImage;
                else
                    currentImage = sharedImage;
                topPanel.repaint();
            }
        });

        topPanel.setPreferredSize(new Dimension(100, 100));

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        bottomPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Nome:");
        gbc.gridy = 0;
        gbc.gridx = 0;
        bottomPanel.add(nameLabel, gbc);

        JLabel nameTextLabel = new JLabel(name);
        gbc.gridx = 1;
        bottomPanel.add(nameTextLabel, gbc);

        JLabel surnameLabel = new JLabel("Cognome:");
        gbc.gridy = 1;
        gbc.gridx = 0;
        bottomPanel.add(surnameLabel, gbc);

        JLabel surnameTextLabel = new JLabel(surname);
        gbc.gridx = 1;
        bottomPanel.add(surnameTextLabel, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridy = 2;
        gbc.gridx = 0;
        bottomPanel.add(emailLabel, gbc);

        JLabel emailTextLabel = new JLabel(email);
        gbc.gridx = 1;
        bottomPanel.add(emailTextLabel, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridy = 3;
        gbc.gridx = 0;
        bottomPanel.add(passwordLabel, gbc);

        JLabel passLabel = new JLabel("********");
        gbc.gridx = 1;
        bottomPanel.add(passLabel, gbc);
        
        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridy = 4;
        gbc.gridx = 0;
        bottomPanel.add(usernameLabel, gbc);

        JLabel usernameTextLabel = new JLabel(username);
        gbc.gridx = 1;
        bottomPanel.add(usernameTextLabel, gbc);
        
        JLabel dateLabel = new JLabel("Date:");
        gbc.gridy = 5;
        gbc.gridx = 0;
        bottomPanel.add(dateLabel, gbc);

        JLabel dateTextLabel = new JLabel(date);
        gbc.gridx = 1;
        bottomPanel.add(dateTextLabel, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(bottomPanel, BorderLayout.WEST);

        profileFrame.add(mainPanel);
        profileFrame.setSize(200, 300);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.setVisible(true);
    }

    // Aggiungi i metodi per impostare le immagini hover e predefinite
    public void setHoverImage(Image hoverImage) {
        this.hoverImage = hoverImage;
    }

    public void setDefaultImage(Image defaultImage) {
        this.defaultImage = defaultImage;
    }
}
/*
Observer pattern: L'utilizzo di MouseListener per rilevare eventi come il passaggio del mouse e il click sui pannelli potrebbe essere
paragonabile all'Observer pattern, in cui l'oggetto osservato (il pannello) notifica gli osservatori (i listener) quando accadono
determinati eventi. */