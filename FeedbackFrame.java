import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FeedbackFrame extends JFrame {
    private JTextArea feedbackTextArea;
    private JLabel ratingLabel; // Label per visualizzare la valutazione
    private int userRating = 0; // Valutazione iniziale
    ImageIcon faviconIcon = new ImageIcon("icona.png");

    public FeedbackFrame() {
        setIconImage(faviconIcon.getImage());
        setTitle("Invia Feedback");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400); // Aumentato l'altezza per includere le stelle
        setLocationRelativeTo(null);
        // area di testo per il feedback
        feedbackTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);

        // Creazione delle stelle
        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (int i = 1; i <= 5; i++) {
            JButton starButton = new JButton("\u2665"); // Simbolo stella
            starButton.setFont(new Font("Arial", Font.PLAIN, 24));
            int finalI = i; // Variabile finale per il listener
            starButton.addActionListener(e -> setRating(finalI));
            ratingPanel.add(starButton);
        }
        ratingLabel = new JLabel("Valutazione: " + userRating + " stelle");
        //pulsante per inviare il feedback
        JButton sendButton = new JButton("Invia Feedback");
        
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String feedback = feedbackTextArea.getText();
                if (!feedback.isEmpty()) {
                    writeFeedbackToFile(feedback);
                    JOptionPane.showMessageDialog(null, "Feedback inviato con successo!");
                    feedbackTextArea.setText("");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Inserisci del feedback prima di inviare.");
                }
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Aggiunta del pannello della valutazione sopra il pannello dei pulsanti
        mainPanel.add(ratingPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        add(ratingLabel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void setRating(int rating) {
        userRating = rating;
        ratingLabel.setText("Valutazione: " + userRating + " stelle");
    }

    private void writeFeedbackToFile(String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write("Valutazione: " + userRating + " stelle");
            writer.newLine();
            writer.write(feedback);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FeedbackFrame();
        });
    }
}/*Observer pattern : L'aggiornamento della valutazione quando vengono premute le stelle potrebbe essere 
interpretato come un'implementazione semplificata dell'Observer pattern, dove l'azione sul pulsante notifica un cambiamento 
nell'interfaccia.

Dependency Injection : La classe FeedbackFrame riceve e utilizza un'icona per il setIconImage() attraverso un 
oggetto ImageIcon. Sebbene sia un utilizzo semplice, potrebbe essere considerato un tipo di dependency injection.
*/