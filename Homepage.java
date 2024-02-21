import java.awt.*;
import javax.swing.*;
import java.io.File;
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
        navbarPanel.setBackground(Color.BLACK);
        navbarPanel.setLayout(new BoxLayout(navbarPanel, BoxLayout.X_AXIS)); // Utilizza BoxLayout per allineare i componenti

        // Aggiungi il pulsante "Feedback" con un'icona di cuore
        Font font = new Font("Arial", Font.PLAIN, 20); // Specifica il font e le dimensioni
        JButton feedbackButton = new JButton("\u2665 Feedback"); // Utilizza il codice Unicode per il carattere cuore
        feedbackButton.setFont(font); // Imposta il font al pulsante
        navbarPanel.add(feedbackButton); // Aggiungi il pulsante "Feedback" prima del pulsante "Login/Registration"
        
        JButton videoButton = new JButton("\u2022 Video Istruttivo");
        videoButton.setFont(font);
        navbarPanel.add(videoButton);
        ImageIcon loginIcon = getScaledImageIcon("user.png", 20, 20);
        JButton loginButton = new JButton("Login/Registration", loginIcon);
        navbarPanel.add(Box.createHorizontalGlue()); // Aggiungi uno spazio elastico per spostare il pulsante a destra
        navbarPanel.add(loginButton);

        add(navbarPanel, BorderLayout.NORTH);        
        // Create the mainPanel
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create the DisplayImagesWithDataFromLocalFolder panel
        DisplayImagesWithDataFromLocalFolder displayPanel = new DisplayImagesWithDataFromLocalFolder(n,null);

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
        feedbackButton.addActionListener(e -> {
            FeedbackFrame feedbackFrame = new FeedbackFrame();
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
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Homepage());
    }
}/*
Strategy Pattern (Pattern Strategia):
L'uso di lambda expressions per specificare le azioni dei pulsanti (addActionListener(e -> {...})) può essere considerato 
un'implementazione di Strategy Pattern, in cui viene incapsulato un comportamento specifico (strategia) all'interno di un oggetto 
(lambda expression) che può essere cambiato dinamicamente.

Observer Pattern (Pattern Osservatore):
L'implementazione delle azioni dei pulsanti utilizza un approccio basato su eventi, dove un'azione (click del pulsante) viene catturata 
da un ascoltatore specifico (lambda expression).

Event Handling con ActionListener:
L'utilizzo di ActionListener per gestire le azioni dei pulsanti (loginButton, feedbackButton, videoButton) rappresenta un esempio di 
event handling. Questo consente di eseguire azioni specifiche quando i pulsanti vengono premuti. 
*/