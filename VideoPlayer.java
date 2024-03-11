import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

interface VideoPlayerObserver {
    void onVideoStopped();
    void onVolumeChanged(double newVolume);
}

public class VideoPlayer extends JPanel implements VideoPlayerObserver {
    private MediaPlayer mediaPlayer;
    private JButton stopButton;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private boolean isVideoPlaying = true;
    public VideoPlayer(File videoFile) {
        createAndShowGUI(videoFile);
    }

    private void createAndShowGUI(File videoFile) {
        setLayout(new BorderLayout());

        JFXPanel fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> handleStopVideo());
        controlPanel.add(stopButton);

        volumeUpButton = new JButton("Volume Up");
        volumeUpButton.addActionListener(e -> adjustVolume(0.1));
        controlPanel.add(volumeUpButton);

        volumeDownButton = new JButton("Volume Down");
        volumeDownButton.addActionListener(e -> adjustVolume(-0.1));
        controlPanel.add(volumeDownButton);

        add(controlPanel, BorderLayout.SOUTH);

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
        if (isVideoPlaying) {
            stopVideo();
        } else {
            playVideo();
        }
        isVideoPlaying = !isVideoPlaying; // Inverte lo stato del video
    }

    public void stopVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            notifyObserversOnVideoStopped();
        }
    }
    public void playVideo() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
            notifyObserversOnVideoStopped();
        }
    }

    private void notifyObserversOnVideoStopped() {
        System.out.println("Handle Stop Video - isVideoPlaying: " + isVideoPlaying);
            if (isVideoPlaying) {
                stopButton.setText("Play");
            } else {
                stopButton.setText("Stop");
            }
        System.out.println("After Handle Stop Video - isVideoPlaying: " + isVideoPlaying);
    }

    public void adjustVolume(double delta) {
        if (mediaPlayer != null) {
            double currentVolume = mediaPlayer.getVolume();
            double newVolume = Math.max(0, Math.min(1, currentVolume + delta));
            mediaPlayer.setVolume(newVolume);
            notifyObserversOnVolumeChanged(newVolume);
        }
    }

    private void notifyObserversOnVolumeChanged(double newVolume) {
        volumeDownButton.setEnabled(newVolume > 0);
        volumeUpButton.setEnabled(newVolume < 1);
    }

    @Override
    public void onVideoStopped() {
        // Implementazione di ciò che deve accadere quando il video viene fermato
        notifyObserversOnVideoStopped();
    }

    @Override
    public void onVolumeChanged(double newVolume) {
        // Implementazione di ciò che deve accadere quando il volume viene cambiato
        volumeDownButton.setEnabled(newVolume > 0);
        volumeUpButton.setEnabled(newVolume < 1);
    }
}//OBSERVER
