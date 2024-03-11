import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
public class SitoFrame extends JFrame {
    private JFrame sitoFrame;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JLabel imageLabel;
    private Image currentImage;
    ImageIcon image1Icon = new ImageIcon("facebookicon.png");
    ImageIcon image2Icon = new ImageIcon("instagram-icon.png");
    ImageIcon image3Icon = new ImageIcon("youtubeicon.png");
    ImageIcon faviconIcon = new ImageIcon("icona.png");
    private Component spaceComponent; // Spazio vuoto nell'1%
    public SitoFrame(String username, String surname, String email,String name,String date, Image image, ImageIcon faviconIcon, String categorie) {
        sitoFrame = new JFrame("Sito");
        sitoFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sitoFrame.setIconImage(faviconIcon.getImage());
        this.currentImage = image;
        int delay = 1000;
        String amm=null;
        String storedname = null;
        String storedEmail = null;
            try (BufferedReader br = new BufferedReader(new FileReader("utenti.txt"))) {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(":");
            if (parts.length == 7) {
                storedname = parts[0];
                storedEmail = parts[3];
                if (storedname.equals(name) && storedEmail.equals(email)) {
                    amm = parts[6];
                }
            }
        } 
    }catch (IOException e) {
        e.printStackTrace();
    }    
        
        image1Icon = new ImageIcon(image1Icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        image2Icon = new ImageIcon(image2Icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        image3Icon = new ImageIcon(image3Icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        JButton  button1 = new JButton (image1Icon);
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.facebook.com/domenico.zeno.5?locale=it_IT"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        JButton  button2 = new JButton (image2Icon);
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.instagram.com/iperdrawing/"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        JButton  button3 = new JButton (image3Icon);
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.youtube.com/channel/UCdU8-p-rcKRXzyY68L3bzZg"));
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Image newImage = SharedImage.getCurrentImage();
                if (newImage != null) {
                    currentImage = newImage;
                }
                Image scaledImage = currentImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);
                imageLabel.setIcon(scaledIcon);
            }
        };

        Timer timer = new Timer(delay, taskPerformer);
        timer.start();
        
    leftPanel = new JPanel();
    leftPanel.setLayout(new GridLayout(10, 1));
    leftPanel.setBackground(Color.BLACK);

    imageLabel = new JLabel();
    leftPanel.add(imageLabel, BorderLayout.CENTER);

    ImageIcon userProfileIcon = new ImageIcon("1A.png");
    Image userimage = userProfileIcon.getImage();
    Image newImage = userimage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    userProfileIcon = new ImageIcon(newImage);
    JButton userProfileButton = new JButton();
    userProfileButton.setIcon(userProfileIcon);
    userProfileButton.setMinimumSize(new Dimension(100, 120)); // Impostazione della dimensione minima
    
    ImageIcon websiteIcon = new ImageIcon("2A.png");
    Image websiteImage = websiteIcon.getImage();
    Image newWebsiteImage = websiteImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    websiteIcon.setImage(newWebsiteImage);
    JButton websiteButton = new JButton();
    websiteButton.setIcon(websiteIcon);
    websiteButton.setMinimumSize(new Dimension(100, 100));

    ImageIcon contactsIcon = new ImageIcon("3A.png");
    Image contactsImage = contactsIcon.getImage();
    Image newContactsImage = contactsImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    contactsIcon.setImage(newContactsImage);
    JButton contactsButton = new JButton();
    contactsButton.setIcon(contactsIcon);
    contactsButton.setMinimumSize(new Dimension(100, 100));

    // Creazione del JButton per le Categorie
    ImageIcon categorieIcon = new ImageIcon("4A.png");
    Image categorieImage = categorieIcon.getImage();
    Image newcategorieImage = categorieImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    categorieIcon.setImage(newcategorieImage);
    JButton categorieButton = new JButton();
    categorieButton.setIcon(categorieIcon);
    categorieButton.setMinimumSize(new Dimension(100, 100));

    ImageIcon insertIcon = new ImageIcon("5A.png");
    Image insertImage = insertIcon.getImage();
    Image newInsertImage = insertImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    insertIcon.setImage(newInsertImage);
    JButton insertButton = new JButton();
    insertButton.setIcon(insertIcon);
    insertButton.setMinimumSize(new Dimension(100, 100));
    
    // Creazione del pulsante di logout
    ImageIcon logoutIcon = new ImageIcon("6A.png");
    Image logoutImage = logoutIcon.getImage();
    Image newlogoutImage = logoutImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    logoutIcon.setImage(newlogoutImage);
    JButton logout = new JButton();
    logout.setIcon(logoutIcon);
    logout.setMinimumSize(new Dimension(100, 100));

    userProfileButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("1B.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            userProfileButton.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            UserProfileFrame userProfileFrame = new UserProfileFrame(username, surname, email,name,date, currentImage);
            userProfileFrame.setVisible(true); // Aggiunta per rendere visibile il frame del profilo utente
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("1A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            userProfileButton.setIcon(new ImageIcon(hoverImage));
        }
    });

    websiteButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("2B.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            websiteButton.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            try {
                Desktop.getDesktop().browse(new URI("benvenuto.html"));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("2A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            websiteButton.setIcon(new ImageIcon(hoverImage));
        }
    });

    contactsButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("3B.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            contactsButton.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            FeedbackFrame feedbackFrame = new FeedbackFrame();
                feedbackFrame.setVisible(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("3A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            contactsButton.setIcon(new ImageIcon(hoverImage));
        }
    });

    insertButton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("5C.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            insertButton.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            InserimentoFilm inserimentoFilm = InserimentoFilmFactory.createInserimentoFilm(); // utilizza il Factory Method per ottenere un'istanza di InserimentoFilm
            inserimentoFilm.setVisible(true); // mostra la finestra InserimentoFilm
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("5A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            insertButton.setIcon(new ImageIcon(hoverImage));
        }
    });

    categorieButton.addMouseListener(new MouseAdapter() {

        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("4B.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            categorieButton.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            // Crea un nuovo frame per le categorie
            JFrame categorieFrame = new JFrame("Categorie");
            categorieFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            categorieFrame.setSize(400, 300);//dimensione del frame

            // Creazione dei JCheckBox per i tipi di video
            JCheckBox avventuraCheckBox = new JCheckBox("Avventura");
            JCheckBox storicoCheckBox = new JCheckBox("Storico");
            JCheckBox animazioneCheckBox = new JCheckBox("Animazione");
            JCheckBox animeCheckBox = new JCheckBox("Anime");
            JCheckBox azioneCheckBox = new JCheckBox("Azione");
            JCheckBox commediaCheckBox = new JCheckBox("Commedia");
            JCheckBox pauraCheckBox = new JCheckBox("Paura");
            JCheckBox thrillerCheckBox = new JCheckBox("Thriller");
            JCheckBox fantascienzaCheckBox = new JCheckBox("Fantascienza");

            Container contentPane = categorieFrame.getContentPane();
            contentPane.setLayout(new GridLayout(0, 1)); // Layout a colonna per i checkbox
            contentPane.add(avventuraCheckBox);
            contentPane.add(storicoCheckBox);
            contentPane.add(animazioneCheckBox);
            contentPane.add(animeCheckBox);
            contentPane.add(azioneCheckBox);
            contentPane.add(commediaCheckBox);
            contentPane.add(pauraCheckBox);
            contentPane.add(thrillerCheckBox);
            contentPane.add(fantascienzaCheckBox);
            JButton confermaButton = new JButton("Conferma");
            confermaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Raccolta delle selezioni delle checkbox
                    StringBuilder selezioni = new StringBuilder();
                    if (avventuraCheckBox.isSelected()) selezioni.append("Avventura:");
                    if (storicoCheckBox.isSelected()) selezioni.append("Storico:");
                    if (animazioneCheckBox.isSelected()) selezioni.append("Animazione:");
                    if (animeCheckBox.isSelected()) selezioni.append("Anime:");
                    if (azioneCheckBox.isSelected()) selezioni.append("Azione:");
                    if (commediaCheckBox.isSelected()) selezioni.append("Commedia:");
                    if (pauraCheckBox.isSelected()) selezioni.append("Paura:");
                    if (thrillerCheckBox.isSelected()) selezioni.append("Thriller:");
                    if (fantascienzaCheckBox.isSelected()) selezioni.append("Fantascienza:");

                    // Rimuove gli ultimi due punti se presente
                    if (selezioni.length() > 0) {
                        selezioni.deleteCharAt(selezioni.length() - 1);
                    }
                    // Controlla se sono state selezionate categorie
                    if (selezioni.length() == 0) {
                        // Nessuna categoria selezionata, gestisci questo caso qui
                        sitoFrame.dispose();
                        SitoFrame newSitoFrame = new SitoFrame(username, surname, email, name, date, image, faviconIcon, null);
                        newSitoFrame.setVisible(true);
                    } else {
                        // Altrimenti, chiamiamo SitoFrame2 e passiamo le selezioni
                        sitoFrame.dispose();
                        SitoFrame newSitoFrame = new SitoFrame(username, surname, email, name, date, image, faviconIcon, selezioni.toString());
                        newSitoFrame.setVisible(true);
                    }
                    // Chiudi il frame delle categorie
                    categorieFrame.dispose();
                }
            });

            // Aggiunta del pulsante "Conferma" al contentPane del frame delle categorie
            contentPane.add(confermaButton);
            categorieFrame.setVisible(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("4A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            categorieButton.setIcon(new ImageIcon(hoverImage));
        }
        
    });
    logout.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("6B.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logout.setIcon(new ImageIcon(hoverImage));
        }

        @Override
        public void mousePressed(MouseEvent e) {
            LoginInterface loginInterface = new LoginInterface();
            loginInterface.setVisible(true);
            // Chiudi il frame corrente
            sitoFrame.dispose();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            ImageIcon hoverIcon = new ImageIcon("6A.png");
            Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            logout.setIcon(new ImageIcon(hoverImage));
        }
    });
        leftPanel.add(userProfileButton);
        leftPanel.add(websiteButton);
        leftPanel.add(contactsButton);
        leftPanel.add(categorieButton);
        if("1".equals(amm))
        leftPanel.add(insertButton);
        leftPanel.add(logout);
        leftPanel.add(button1);
        leftPanel.add(button2);
        leftPanel.add(button3);

        // Creazione del pannello di sfondo per il rightPanel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setBackground(Color.BLACK);
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        // Aggiungi il pannello di sfondo al rightPanel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.BLACK);
        rightPanel.add(backgroundPanel);
// Creazione del campo di ricerca
JTextField searchField = new JTextField(30); // Larghezza iniziale
// Impostazione del tipo di carattere
Font font = new Font("Calibri", Font.PLAIN, 12);
searchField.setFont(font);
searchField.setPreferredSize(new Dimension(350, 30)); // Impostazione della larghezza desiderata
// Impostazione del testo di esempio e del colore grigio per il placeholder
searchField.setText("Cerca film");
searchField.setForeground(Color.GRAY);

// Aggiunta dell'ascoltatore per rimuovere il testo del placeholder quando il campo ottiene il focus
searchField.addFocusListener(new FocusListener() {
    @Override
    public void focusGained(FocusEvent e) {
        if (searchField.getText().equals("Cerca film")) {
            searchField.setText("");
            searchField.setForeground(Color.BLACK); // Cambia il colore del testo quando il campo è attivo
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (searchField.getText().isEmpty()) {
            searchField.setForeground(Color.GRAY);
            searchField.setText("Cerca film");
        }
    }
});
searchField.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        String videoFileName = searchField.getText(); // Ottieni il nome del file video
        File videoFile = new File("uploads/" + videoFileName+".mp4"); // Crea un oggetto File con il nome del video

        SwingUtilities.invokeLater(() -> {
            // Ottieni l'istanza di default del toolkit
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            // Ottieni le dimensioni dello schermo
            Dimension screenSize = toolkit.getScreenSize();
            int screenWidth = (int) screenSize.getWidth(); // Converti double in int
            int screenHeight = (int) screenSize.getHeight(); // Converti double in int
            
            JFrame videoFrame = new JFrame("Video Player");
            videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            videoFrame.setSize(screenWidth, screenHeight-35);

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
    }
});
    // Creazione dell'immagine e ridimensionamento
    ImageIcon icon = new ImageIcon("uploads/lente.jpg"); // Sostituisci con il percorso dell'icona
    Image img = icon.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);

    // Creazione di un'icona ridimensionata
    Icon searchIcon = new ImageIcon(img);

    // Creazione del pannello per il campo di ricerca e l'icona
    JPanel searchPanel = new JPanel();
    searchPanel.setLayout(new GridBagLayout());

    // Creazione di un JLabel per l'icona
    JLabel searchIconLabel = new JLabel(searchIcon);

    // Impostazione dei vincoli per posizionare il campo di ricerca a sinistra e l'icona a destra
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.WEST; // Imposta l'allineamento a sinistra
    searchPanel.add(searchField, gbc);

    // Imposta i vincoli per l'icona a destra del campo di ricerca
    gbc.gridx = 1;
    gbc.anchor = GridBagConstraints.EAST; // Imposta l'allineamento a destra
    searchPanel.add(searchIconLabel, gbc);

    // Creazione del pannello per la visualizzazione delle immagini
    DisplayImagesWithDataFromLocalFolder displayPanel = new DisplayImagesWithDataFromLocalFolder.Builder()
    .setUtente(1)
    .setCategorie(categorie)
    .build();
    FlowLayout flowLayout = new FlowLayout();
    flowLayout.setHgap(50);
    flowLayout.setVgap(50);
    displayPanel.setLayout(flowLayout);

    // Aggiunta del campo di ricerca e del pannello di visualizzazione al rightPanel
    rightPanel.add(searchPanel);
    rightPanel.add(displayPanel);

    // Creazione del JScrollPane per il displayPanel
    JScrollPane scrollPane = new JScrollPane(rightPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    // Creazione del JSplitPane con leftPanel e JScrollPane (rightPanel)
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, scrollPane);
    splitPane.setResizeWeight(0.005);
    splitPane.setDividerSize(1);
    
    // Aggiunta del JSplitPane al frame principale
    sitoFrame.add(splitPane);

    sitoFrame.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            // Calcola la larghezza del frame
            int frameWidth = sitoFrame.getWidth();
            // Imposta il numero di colonne in base alla larghezza del frame (personalizzabile)
            int columns = frameWidth / 250; // Ad esempio, ogni elemento ha una larghezza di 250 pixel
            // Imposta il layout del displayPanel in base al numero di colonne
            displayPanel.setLayout(new GridLayout(0, columns));
        }
    });

sitoFrame.add(splitPane);
sitoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Aggiungi un ascoltatore per calcolare le dimensioni quando la finestra cambia dimensione
        sitoFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                leftPanel.setPreferredSize(new Dimension(100, 100));
                spaceComponent.setPreferredSize(new Dimension(sitoFrame.getWidth() / 200, sitoFrame.getHeight()));
                sitoFrame.revalidate();
            }
        });

        sitoFrame.setVisible(true);
    }
}
/* DESIGN PATTERN
Strategy pattern: Potrebbe essere interpretato in parte per il modo in cui vengono gestite le azioni
degli ActionListener associati ai bottoni. Ogni bottone ha una strategia (azione) associata quando viene premuto.
 */