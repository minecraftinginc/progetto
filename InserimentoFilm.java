import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.Paths;
import java.util.Locale;

class RoundBorder implements Border {

    private int radius;

    RoundBorder(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}

public abstract class InserimentoFilm extends JFrame {

    private JTextField nomeField, descrizioneField, prezzoField, categoriaField, registaField;

    protected InserimentoFilm() {
        setTitle("Inserimento Film");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Nome del Film:"));
        nomeField = createRoundedTextField();
        add(nomeField);

        add(new JLabel("Descrizione:"));
        descrizioneField = createRoundedTextField();
        add(descrizioneField);

        add(new JLabel("Prezzo:"));
        prezzoField = createRoundedTextField();
        add(prezzoField);

        add(new JLabel("Categoria:"));
        categoriaField = createRoundedTextField();
        add(categoriaField);

        add(new JLabel("Nome del Regista:"));
        registaField = createRoundedTextField();
        add(registaField);

        JButton inserisciButton = new JButton("Inserisci");
        inserisciButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scegliImmagineEInserisci();
            }
        });
        add(new JLabel()); // Spazio vuoto
        add(inserisciButton);

        setVisible(true);
    }

    private JTextField createRoundedTextField() {
        JTextField textField = new JTextField();
        textField.setBorder(new RoundBorder(10));
        return textField;
    }

    private void scegliImmagineEInserisci() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String selectedPath = fileChooser.getSelectedFile().getAbsolutePath();
            inserisciDati(selectedPath);
        }
    }

    private void inserisciDati(String immaginePath) {
        try {
            String nome = nomeField.getText();
            String descrizione = descrizioneField.getText();
            double prezzo = Double.parseDouble(prezzoField.getText());
            String categoria = categoriaField.getText();
            String regista = registaField.getText();
            String path=null;
            // Otteniamo il nome del file dall'intero percorso
            String nomeFile = Paths.get(immaginePath).getFileName().toString();

            // Creiamo un percorso per la cartella "uploads" nel percorso corrente
            Path cartellaUploads = Path.of("uploads");

            // Verifica se la cartella "uploads" esiste, altrimenti crea la cartella
            if (!Files.exists(cartellaUploads)) {
                Files.createDirectory(cartellaUploads);
            }

            // Creiamo un percorso per la destinazione del file nella cartella "uploads"
            Path destinazione = Paths.get("uploads", nomeFile);
            // Copiamo il file nella cartella "uploads"
            Files.copy(Path.of(immaginePath), destinazione, StandardCopyOption.REPLACE_EXISTING);
            path="/uploads/"+nomeFile;
            // Creiamo una stringa nel formato specificato con il nuovo percorso
            String datiFilm = String.format(Locale.US, "%s,%s,%.2f,%s,%s,%s", nome, descrizione, prezzo, categoria, regista, path);

            try (FileWriter fileWriter = new FileWriter("oggetti.txt", true)) {
                // Scrivi la stringa nel file
                fileWriter.write("\n" + datiFilm);

                // Resetta i campi di input dopo l'inserimento
                nomeField.setText("");
                descrizioneField.setText("");
                prezzoField.setText("");
                categoriaField.setText("");
                registaField.setText("");

                JOptionPane.showMessageDialog(this, "Film inserito con successo!");

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Inserisci un prezzo valido.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {//serve a vedere se funziona anche da sola
        SwingUtilities.invokeLater(() -> {
            InserimentoFilm inserimentoFilm = InserimentoFilmFactory.createInserimentoFilm();
        });
    }
}//factory method
