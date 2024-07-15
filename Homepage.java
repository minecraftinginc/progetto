import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Homepage extends JFrame {
    private JPanel navbarPanel;
    private JPanel mainPanel;
    public Homepage() {
        setTitle("Homepage");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        int n=0;
        // Navbar con pulsanti
        navbarPanel = new JPanel();
        navbarPanel.setBackground(new Color(51, 0, 110));
        navbarPanel.setLayout(new BoxLayout(navbarPanel, BoxLayout.X_AXIS)); // Utilizza BoxLayout per allineare i componenti

        // Aggiungi l'immagine del logo
        ImageIcon logoIcon = getScaledImageIcon("Logo.png", 112, 90); // Imposta le dimensioni desiderate per il logo
        JLabel logoLabel = new JLabel(logoIcon);
        navbarPanel.add(logoLabel); // Aggiungi l'etichetta del logo alla barra di navigazione
        navbarPanel.add(Box.createHorizontalStrut(20)); // Aggiunge uno spaziatore orizzontale di 10 pixel
        Font font = new Font("Arial", Font.PLAIN, 18); // Specifica il font e le dimensioni
        RoundedButton feedbackButton = new RoundedButton("\u2665 Feedback"); // Usa la classe RoundedButton invece di JButton
        feedbackButton.setFont(font); // Imposta il font al pulsante
        navbarPanel.add(feedbackButton); // Aggiungi il pulsante "Feedback" prima del pulsante "Login/Registration"

        navbarPanel.add(Box.createHorizontalStrut(10)); // Aggiunge uno spaziatore orizzontale di 10 pixel
        RoundedButton videoButton = new RoundedButton("\u2022 Video Istruttivo"); // Utilizza il codice Unicode per il carattere del pallino
        videoButton.setFont(font); // Imposta il font al pulsante

        navbarPanel.add(videoButton); // Aggiungi il pulsante "Video Istruttivo" dopo il pulsante "Feedback"

        // Utilizza RoundedButton per il pulsante di login/registrazione
        JButton loginButton = new JButton();
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);
        loginButton.setFocusPainted(false);

        // Aggiungi l'icona al pulsante di login/registrazione
        ImageIcon loginIcon = getScaledImageIcon("s1.png", 45, 45);
        loginButton.setIcon(loginIcon);

        // Aggiungi un listener per gestire l'evento del passaggio del mouse
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Quando il mouse entra nel pulsante, cambia l'icona
                ImageIcon iconHover = getScaledImageIcon("s2.png", 45, 45);
                loginButton.setIcon(iconHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Quando il mouse esce dal pulsante, reimposta l'icona
                loginButton.setIcon(loginIcon);
            }
        });

        navbarPanel.add(Box.createHorizontalGlue());
        navbarPanel.add(loginButton);

        add(navbarPanel, BorderLayout.NORTH);       
        // Create the mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Crea il panel display ecc per stampare le immagini
        DisplayImagesWithDataFromLocalFolder displayPanel = new DisplayImagesWithDataFromLocalFolder.Builder()
        .setUtente(n)
        .setCategorie(null)
        .build();

        // Use GridLayout with 7 columns for the displayPanel
        GridLayout gridLayout = new GridLayout(0, 7);
        gridLayout.setVgap(50); // Imposta lo spazio verticale tra le immagini
        gridLayout.setHgap(0); // Imposta lo spazio orizzontale tra le immagini
        displayPanel.setLayout(gridLayout);

        // Add the displayPanel to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(displayPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Add the scrollPane to the mainPanel
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the mainPanel to the frame
        add(mainPanel, BorderLayout.CENTER);

        // Aggiunge azioni per i pulsanti della navbar
        loginButton.addActionListener(e -> {
            // Implementa l'azione del pulsante di accesso
            LoginInterface loginInterface = new LoginInterface();
            loginInterface.setVisible(true);
            // Chiudi la finestra corrente dopo aver aperto la nuova finestra
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor((Component) e.getSource());
            currentFrame.dispose();
        });
        Rating ratingSubject = new Rating();

        feedbackButton.addActionListener(e -> {
            FeedbackFrame feedbackFrame = new FeedbackFrame(ratingSubject);
            feedbackFrame.setVisible(true);
        });
        
videoButton.addActionListener(e -> {
    SwingUtilities.invokeLater(() -> {
        // Ottieni l'istanza di default del toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        // Ottieni le dimensioni dello schermo
        Dimension screenSize = toolkit.getScreenSize();
        int screenWidth = (int) screenSize.getWidth(); // Converti double in int
        int screenHeight = (int) screenSize.getHeight(); // Converti double in int
        JFrame videoFrame = new JFrame("Video Player");
        videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        videoFrame.setSize(screenWidth, screenHeight-35 );

        File videoFile = new File("uploads/simpaticovideopertech.mp4"); // Sostituisci con il percorso del tuo file video

        VideoPlayer videoPlayer = new VideoPlayer(videoFile);
        videoFrame.add(videoPlayer);

        videoFrame.setVisible(true);

        videoFrame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
           public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                videoPlayer.stopVideo();
           }
        });
    });
});
        // Massimizza la finestra per occupare tutto lo schermo
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    // Metodo per ridimensionare l'immagine e restituire un ImageIcon
    private ImageIcon getScaledImageIcon(String imagePath, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();
    
        // Calcoliamo il rapporto tra altezza e larghezza dell'immagine originale
        double aspectRatio = (double) originalImage.getHeight(null) / originalImage.getWidth(null);
    
        // Calcoliamo le nuove dimensioni rispettando il rapporto di aspetto
        int newWidth = width;
        int newHeight = height;
        if (width / aspectRatio > height) {
            newWidth = (int) (height / aspectRatio);
        } else {
            newHeight = (int) (width * aspectRatio);
        }
    
        // Ridimensioniamo l'immagine
        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Homepage());
    }
}