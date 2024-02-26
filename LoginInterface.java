import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
public class LoginInterface extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;  // Aggiunto il pulsante di registrazione
    private Timer colorTimer;
    private JFrame UserprofileFrame;
    private int colorIndex = 0;
    private Color[] colors = {Color.RED, new Color(128, 0, 128), new Color(139, 69, 19), Color.ORANGE, Color.YELLOW, Color.BLUE, Color.GREEN, Color.BLACK, Color.WHITE};
    private Image defaultImage; // Immagine predefinita
    private Image hoverImage;   // Immagine per l'hovering
    public Image currentImage; // Immagine corrente
    public Image newImage = null;//nuova immagine
    private JFrame sitoFrame; // Dichiarazione di sitoFrame come variabile di istanza
    private Timer imageChangeTimer;// timer per il cambiamento dell'imagine
    private JPanel leftPanel;
    // Dichiarazione di una variabile di riferimento per la finestra di login
    private static LoginInterface loginInterface;
    ImageIcon faviconIcon = new ImageIcon("icona.png"); 
    private void login() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    if (authenticate(username, password)) {
        dispose(); // Chiudi la finestra di login
    } else {
        JOptionPane.showMessageDialog(null, "Accesso fallito. Riprova.");
    }
}
        
        private void saveUserData(String name, String surname, String email, String password,String username,String date) {
    try (FileWriter writer = new FileWriter("utenti.txt", true)) {
        // Scrivi i dati nel file utenti.txt
        writer.write("\n" + name + ":" + password + ":" + surname + ":" + email+":"+username+":"+date+":0");
    } catch (IOException e) {
        e.printStackTrace();
        // Gestisci l'eccezione in base alle tue esigenze
    }
}
        
        private boolean authenticate(String name, String password) {
            String storedUsername = null;
            String storedPassword = null;
            String storedSurname = null;
            String storedEmail = null;
            String storedname = null;
            String storeddate = null;
    
    try (BufferedReader br = new BufferedReader(new FileReader("utenti.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 7) {
                storedname = parts[0];
                storedPassword = parts[1];
                storedSurname = parts[2];
                storedEmail = parts[3];
                storedUsername=parts[4];
                storeddate=parts[5];
                if (storedUsername.equals(name) && storedPassword.equals(password)) {
                    // Crea un'istanza di SitoFrame e passa i dati necessari
                    sitoFrame = new SitoFrame(storedUsername, storedSurname, storedEmail, storedname, storeddate, currentImage, faviconIcon,null);
                    return true;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return false;
}

   private void openRegistrationScreen() {
    JFrame registrationFrame = new JFrame("Registrazione");
    registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    registrationFrame.setIconImage(faviconIcon.getImage());
    registrationFrame.setSize(this.getSize());
    registrationFrame.setLocationRelativeTo(this);

    JPanel backgroundPanel = new JPanel();
    backgroundPanel.setLayout(new GridBagLayout()); // Imposta il layout GridBagLayout oppure backgroundPanel.setLayout(new GridBagLayout());
    backgroundPanel.setBackground(Color.WHITE); // Imposta il colore di sfondo bianco
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    // Creazione del pannello per il formbox
    RoundedPanel formPanel = new RoundedPanel(20); // Imposta il raggio a 20 per bordi arrotondati
    formPanel.setBackground(new Color(51, 0, 110));
    formPanel.setLayout(new GridBagLayout());
    formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Aggiunta di padding per il formbox

    JLabel titleLabel = new JLabel("Benvenuto!");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
    titleLabel.setForeground(Color.WHITE);
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(titleLabel, gbc);
        colorTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleLabel.setForeground(colors[colorIndex]);
                colorIndex = (colorIndex + 1) % colors.length;
            }
        });
        colorTimer.start();

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Username:");
        Font labelFont = usernameLabel.getFont();
        usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        usernameLabel.setForeground(Color.WHITE);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(30);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        passwordLabel.setForeground(Color.WHITE);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(30);
        formPanel.add(passwordField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel nameLabel = new JLabel("Nome:");
        nameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        nameLabel.setForeground(Color.WHITE);
        formPanel.add(nameLabel, gbc);

        gbc.gridx = 1;
        JTextField nameField = new JTextField(30);
        formPanel.add(nameField, gbc);

        // Aggiungo il campo cognome
        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel surnameLabel = new JLabel("Cognome:");
        surnameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        surnameLabel.setForeground(Color.WHITE);
        formPanel.add(surnameLabel, gbc);

        gbc.gridx = 1;
        JTextField surnameField = new JTextField(30);
        formPanel.add(surnameField, gbc);

        // Aggiungo il campo email
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        emailLabel.setForeground(Color.WHITE);
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        JTextField emailField = new JTextField(30);
        formPanel.add(emailField, gbc);

        // Aggiungo il campo data di nascita
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel dobLabel = new JLabel("Data di Nascita:");
        dobLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        dobLabel.setForeground(Color.WHITE);
        formPanel.add(dobLabel, gbc);

        gbc.gridx = 1;
        JTextField dobField = new JTextField("01/01/1990", 30); // Testo predefinito: 01/01/1990
        formPanel.add(dobField, gbc);
        
        registerButton = new RoundedButton("Sign") {
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    setBackground(new Color(144, 15, 177)); // Colore verde scuro al passaggio del mouse
                } else {
                    setBackground(new Color(255,0,173));
                }
                super.paintComponent(g);
            }
        };
        registerButton.setFont(new Font("Calibri", Font.BOLD, 25));
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        registerButton.setContentAreaFilled(false);
        registerButton.setOpaque(true);
        JButton backButton = new JButton("Login"){
            @Override
            protected void paintComponent(Graphics g) {
                if (getModel().isRollover()) {
                    setBackground(new Color(144, 15, 177)); // Colore verde scuro al passaggio del mouse
                } else {
                    setBackground(new Color(255,0,173));
                }
                super.paintComponent(g);
            }
        };
        backButton.setFont(new Font("Calibri", Font.BOLD, 25));
        backButton.setForeground(Color.WHITE);
        gbc.gridy = 7; // Modifica la posizione del pulsante all'interno del formPanel
        formPanel.add(backButton, gbc);

        // Aggiungi un'azione al pulsante per tornare alla schermata di login
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginInterface loginInterface = new LoginInterface();
                loginInterface.setVisible(true);
                registrationFrame.dispose();
            }
        });
registerButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Recupera i dati dai campi di input
        String name = nameField.getText();
        String Username = usernameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String dateOfBirth = dobField.getText();

        // Chiama il metodo per salvare i dati
        saveUserData(name, surname, email, password,Username,dateOfBirth);
        
               // Invia l'email in modo asincrono
        Thread emailThread = new Thread(() -> {
            EmailSender emailSender = new EmailSender();
            emailSender.sendEmail(email);
        });
        emailThread.start();       
        // Crea un'istanza di SitoFrame e passa i dati necessari
        sitoFrame = new SitoFrame(name, surname, email, Username, dateOfBirth, currentImage, faviconIcon,null);
         // Crea un'istanza di UserProfileFrame e passa i dati necessari
        UserProfileFrame userProfileFrame = new UserProfileFrame(name, surname, email, Username, dateOfBirth, currentImage);        
        // Chiudi la finestra di registrazione o esegui altre azioni necessarie
        registrationFrame.dispose();
    }
});
    
    gbc.gridx = 0;
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.gridwidth = 2;
    formPanel.add(titleLabel, gbc);//titleLabel

    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    formPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    formPanel.add(surnameLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(surnameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    formPanel.add(emailLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    formPanel.add(passwordLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(passwordField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 5;
    formPanel.add(usernameLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(usernameField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 6;
    formPanel.add(dobLabel, gbc);
    gbc.gridx = 1;
    formPanel.add(dobField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 2;
    formPanel.add(registerButton, gbc);

    gbc.gridx = 0;
    gbc.gridy = 8;
    gbc.gridwidth = 2;
    formPanel.add(backButton, gbc);

    backgroundPanel.add(formPanel);
    registrationFrame.add(backgroundPanel);

    registrationFrame.pack();
    registrationFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    registrationFrame.setLocationRelativeTo(this);
    loginInterface.dispose();
    registrationFrame.setVisible(true);
}

    public LoginInterface() {
        // Inizializza la variabile loginInterface
         // Inizializza la variabile loginInterface
    loginInterface = this;
    setTitle("Login");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setIconImage(faviconIcon.getImage());
    setResizable(false);

    // Inizializza currentImage con l'immagine predefinita
    try {
        defaultImage = ImageIO.read(new File("spiaggia.jpg")); // Immagine predefinita
        hoverImage = ImageIO.read(new File("user.png"));     // Immagine per l'hovering
        currentImage = defaultImage; // Inizializza l'immagine corrente con l'immagine predefinita
    } catch (IOException e) {
        e.printStackTrace();
    }
        JPanel backgroundPanel = new JPanel();
        backgroundPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Creazione del pannello per il formbox
        RoundedPanel formPanel = new RoundedPanel(20); // Imposta il raggio a 20 per bordi arrotondati
        formPanel.setBackground(new Color(51, 0, 110));
        formPanel.setLayout(new GridBagLayout()); // Imposta il layout
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Aggiunta di padding per il formbox

        JLabel titleLabel = new JLabel("Benvenuto!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);

        colorTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                titleLabel.setForeground(colors[colorIndex]);
                colorIndex = (colorIndex + 1) % colors.length;
            }
        });
        colorTimer.start();

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        JLabel usernameLabel = new JLabel("Username:");
        Font labelFont = usernameLabel.getFont();
        usernameLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        usernameLabel.setForeground(Color.WHITE);
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(30);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        passwordLabel.setForeground(Color.WHITE);
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(30);
        formPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        loginButton = new RoundedButton("Login");
        loginButton.setFont(new Font("Calibri", Font.BOLD, 25));
        loginButton.setBackground(new Color(144, 15, 177));
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        formPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        registerButton = new RoundedButton("Sign");
        registerButton.setFont(new Font("Calibri", Font.BOLD, 25));
        registerButton.setBackground(new Color(144, 15, 177));
        registerButton.setForeground(Color.WHITE);
        formPanel.add(registerButton, gbc);

        // Aggiunta del formPanel al backgroundPanel
        gbc.gridy = 1;
        backgroundPanel.add(formPanel, gbc);

        getContentPane().add(backgroundPanel);
        
        // Inizializza il timer per controllare l'aggiornamento dell'immagine
    imageChangeTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (newImage == null) {
                // L'immagine � stata cambiata, aggiornala in "sito"
                JLabel imageLabel = (JLabel) leftPanel.getComponent(0);
                ImageIcon newImageIcon = new ImageIcon(newImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH));
                imageLabel.setIcon(newImageIcon);
                currentImage=SharedImage.getCurrentImage();
                newImage=SharedImage.getCurrentImage();
                sitoFrame.validate(); // Assicurati che "sitoFrame" si riallinei correttamente
                imageLabel.repaint();
            }
        }
    });

    // Avvia il timer
    imageChangeTimer.start();
        
        registerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Azione quando il pulsante viene cliccato
            }

            @Override
            public void mousePressed(MouseEvent e) {
                openRegistrationScreen();// Azione quando il pulsante viene premuto
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Azione quando il pulsante viene rilasciato
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pulsante quando il mouse entra
                registerButton.setBackground(new Color(255, 0, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina il colore originale quando il mouse esce
                registerButton.setBackground(new Color(144, 15, 177));
            }
        });
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {   
            }

            @Override
            public void mousePressed(MouseEvent e) {
                login();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(255, 0, 173));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(144, 15, 177));
            }
        });

        add(backgroundPanel);
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginInterface();
            }
        });
    }
}
/*
Observer Pattern:
L'uso di ActionListener e MouseListener per gestire gli eventi sui pulsanti e i campi di input riflette una forma di Observer Pattern. 
Questi ascoltatori sono osservatori che attendono eventi specifici e reagiscono ad essi.

Template Method Pattern:
Template Method Pattern è applicato nella creazione dei pulsanti registerButton e loginButton. Entrambi i pulsanti ereditano dalla classe
base JButton e ognuno di essi ridefinisce il metodo paintComponent(Graphics g) per personalizzare l'aspetto dei pulsanti in base allo stato
(hovering o normale).

*/