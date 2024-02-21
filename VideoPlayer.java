import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class VideoPlayer extends JPanel {
    private MediaPlayer mediaPlayer;
    private JButton stopButton;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private int stopButtonClicks = 0;

    public VideoPlayer(File videoFile) {
        createAndShowGUI(videoFile);
    }

    private void createAndShowGUI(File videoFile) {
        setLayout(new BorderLayout());

        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // FlowLayout per posizionare i pulsanti nella stessa riga

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> handleStopVideo());
        controlPanel.add(stopButton); // Aggiunta del pulsante Stop al pannello dei controlli

        volumeUpButton = new JButton("Volume Up");
        volumeUpButton.addActionListener(e -> adjustVolume(0.1));
        controlPanel.add(volumeUpButton); // Aggiunta del pulsante Volume Up

        volumeDownButton = new JButton("Volume Down");
        volumeDownButton.addActionListener(e -> adjustVolume(-0.1));
        controlPanel.add(volumeDownButton); // Aggiunta del pulsante Volume Down

        add(controlPanel, BorderLayout.SOUTH); // Aggiunta del pannello dei controlli al VideoPlayer
    

        Platform.runLater(() -> {
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            javafx.scene.media.MediaView mediaView = new javafx.scene.media.MediaView(mediaPlayer);

            Scene scene = new Scene(new javafx.scene.layout.StackPane(mediaView));
            fxPanel.setScene(scene);

            mediaPlayer.play();
        });
    }

    private void handleStopVideo() {
        stopButtonClicks++;

        if (stopButtonClicks % 2 == 0) {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        } else {
            stopVideo();
        }
    }

    public void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    public void adjustVolume(double delta) {
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            double newVolume = Math.max(0, Math.min(1, currentVolume + delta));
            mediaPlayer.setVolume(newVolume);
        }
    }
}/*
Dependency Injection: La classe VideoPlayer riceve il File del video nel costruttore. Questo può essere visto come una forma 
di dependency injection, dove l'oggetto dipende da un'altra classe esterna per ottenere le sue dipendenze.
 */