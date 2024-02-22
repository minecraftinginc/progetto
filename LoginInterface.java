import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
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
import java.text.ParseException; // Add this import for ParseException
import java.text.SimpleDateFormat; // Add this import for SimpleDateFormat
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField; // Add this import for JFormattedTextField
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
                if (storedname.equals(name) && storedPassword.equals(password)) {
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

    JPanel registrationPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon imageIcon = new ImageIcon("spiaggia.jpg");
            Image image = imageIcon.getImage();
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }
    };

    GridBagLayout gridBagLayout = new GridBagLayout();
    registrationPanel.setLayout(gridBagLayout);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(7, 7, 7, 7);

    JLabel titleLabel = new JLabel("REGISTRAZIONE");
    titleLabel.setFont(new Font("Arial Black", Font.PLAIN, 30));
    titleLabel.setForeground(colors[colorIndex]); // Cambia colore come in login
    colorTimer.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            titleLabel.setForeground(colors[colorIndex]);
            colorIndex = (colorIndex + 1) % colors.length;
        }
    });

    // Aggiungi i campi di inserimento e le etichette per nome, cognome, email e password,username,data_nascita.
    JLabel nameLabel = new JLabel("Nome:");
    Font labelFont = new Font("Arial Black", Font.PLAIN, 20);
    nameLabel.setFont(labelFont);

    JTextField nameField = new JTextField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                super.paintComponent(g);
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            g2.drawOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    };

    JLabel surnameLabel = new JLabel("Cognome:");
    surnameLabel.setFont(labelFont);

    JTextField surnameField = new JTextField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                super.paintComponent(g);
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            g2.drawOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    };

    JLabel passwordLabel = new JLabel("Password:");
    passwordLabel.setForeground(Color.WHITE);
    passwordLabel.setFont(labelFont);

    JPasswordField passwordField = new JPasswordField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                super.paintComponent(g);
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            g2.drawOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    };
    
    JLabel usernameLabel = new JLabel("Username:");
    usernameLabel.setForeground(Color.WHITE);
    usernameLabel.setFont(labelFont);

JTextField usernameField = new JTextField(20) {
    @Override
    protected void paintComponent(Graphics g) {
        if (!isOpaque()) {
            super.paintComponent(g);
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillOval(0, 0, getWidth(), getHeight());
        g2.setColor(getForeground());
        g2.drawOval(0, 0, getWidth(), getHeight());
        super.paintComponent(g2);
        g2.dispose();
    }
};

    JLabel dobLabel = new JLabel("Date:");
    dobLabel.setForeground(Color.WHITE);
    dobLabel.setFont(labelFont);

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Define your date format
    JFormattedTextField dobField = new JFormattedTextField(dateFormat);

    try {
        dobField.setValue(dateFormat.parse("01/01/1990")); // Initial date value
    } catch (ParseException e) {
        e.printStackTrace();
    }    

    dobField.setColumns(10); // You can adjust the width of the field as needed
    
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setForeground(Color.WHITE);
    emailLabel.setFont(labelFont);

    JTextField emailField = new JTextField(20) {
        @Override
        protected void paintComponent(Graphics g) {
            if (!isOpaque()) {
                super.paintComponent(g);
                return;
            }
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            g2.drawOval(0, 0, getWidth(), getHeight());
            super.paintComponent(g2);
            g2.dispose();
        }
    };

    JButton registerButton = new JButton("Registrati") {
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isRollover()) {
                setBackground(new Color(0, 128, 0)); // Colore verde scuro al passaggio del mouse
            } else {
                setBackground(Color.GREEN);
            }
            super.paintComponent(g);
        }
    };
    registerButton.setFont(new Font("Arial", Font.BOLD, 30));
    registerButton.setForeground(Color.WHITE);
    registerButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
    registerButton.setContentAreaFilled(false);
    registerButton.setOpaque(true);

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
    registrationPanel.add(titleLabel, gbc);

    gbc.gridy = 1;
    gbc.gridwidth = 1;
    gbc.gridx = 0;
    registrationPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    registrationPanel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    registrationPanel.add(surnameLabel, gbc);
    gbc.gridx = 1;
    registrationPanel.add(surnameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    registrationPanel.add(emailLabel, gbc);
    gbc.gridx = 1;
    registrationPanel.add(emailField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    registrationPanel.add(passwordLabel, gbc);
    gbc.gridx = 1;
    registrationPanel.add(passwordField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 5;
    registrationPanel.add(usernameLabel, gbc);
    gbc.gridx = 1;
    registrationPanel.add(usernameField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 6;
    registrationPanel.add(dobField, gbc);
    gbc.gridx = 1;
    registrationPanel.add(dobField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.gridwidth = 2;
    registrationPanel.add(registerButton, gbc);

    registrationFrame.add(registrationPanel);

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
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("spiaggia.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Benvenuto!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(titleLabel, gbc);

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
        backgroundPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(30);
        backgroundPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font(labelFont.getName(), Font.BOLD, 22));
        passwordLabel.setForeground(Color.WHITE);
        backgroundPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        passwordField = new JPasswordField(30);
        backgroundPanel.add(passwordField, gbc);

        gbc.gridy = 3;
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 30));
        loginButton.setBackground(Color.GREEN);
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        backgroundPanel.add(loginButton, gbc);

        gbc.gridy = 4;
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 35));
        registerButton.setBackground(Color.BLUE);
        registerButton.setForeground(Color.WHITE);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        backgroundPanel.add(registerButton, gbc);
        
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
                // Azione quando il pulsante viene premuto
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Azione quando il pulsante viene rilasciato
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Cambia il colore del pulsante quando il mouse entra
                registerButton.setBackground(Color.BLUE.darker()); // Blu pi� scuro
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina il colore originale quando il mouse esce
                registerButton.setBackground(Color.BLUE);
            }
        });
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(Color.GREEN.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(Color.GREEN);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    login();
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationScreen();
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
I metodi come openRegistrationScreen(), login(), saveUserData() seguono un modello simile in quanto eseguono una sequenza di passaggi 
specifici in un ordine definito, ma lasciano alcuni dettagli di implementazione alle sottoclassi.

Decorator Pattern (possibile utilizzo):
In alcuni campi di input (JTextField e JPasswordField), è stata sovrascritta la logica di rendering per aggiungere effetti grafici 
(come l'ovale quando sono attivi).
*/