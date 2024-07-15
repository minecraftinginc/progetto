import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FeedbackFrame extends JFrame implements Observer {
    private JTextArea feedbackTextArea;
    private JLabel ratingLabel;
    private Rating ratingSubject;
    ImageIcon faviconIcon = new ImageIcon("icona.png");

    private void unregisterObserver() {
        ratingSubject.removeObserver(this);
    }

    public FeedbackFrame(Rating ratingSubject) {
        this.ratingSubject = ratingSubject;
        ratingSubject.addObserver(this);

        setIconImage(faviconIcon.getImage());
        setTitle("Invia Feedback");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400); 
        setLocationRelativeTo(null);

        feedbackTextArea = new JTextArea(10, 40);
        JScrollPane scrollPane = new JScrollPane(feedbackTextArea);

        JPanel ratingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (int i = 1; i <= 5;i++) {
            JButton starButton = new JButton("\u2665"); // Simbolo stella
            starButton.setFont(new Font("Arial", Font.PLAIN, 24));
            int finalI = i; 
            starButton.addActionListener(e -> ratingSubject.setRating(finalI));
            ratingPanel.add(starButton);
        }

        ratingLabel = new JLabel("Valutazione: " + ratingSubject.getRating() + " stelle");

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
        mainPanel.add(ratingPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sendButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        add(ratingLabel, BorderLayout.SOUTH);

        setVisible(true);
    }
    @Override
    public void dispose() {
        unregisterObserver();
        super.dispose();
    }

    @Override
    public void update(int rating) {
        ratingLabel.setText("Valutazione: " + rating + " stelle");
    }

    private void writeFeedbackToFile(String feedback) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("feedback.txt", true))) {
            writer.write("Valutazione: " + ratingSubject.getRating() + " stelle");
            writer.newLine();
            writer.write(feedback);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Rating ratingSubject = new Rating();
            new FeedbackFrame(ratingSubject);
        });
    }
}
