import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class UserProfileController {
    private UserProfileView view;
    private UserProfileModel model;
    private Image currentImage;
    private Image hoverImage;

    public UserProfileController(UserProfileView view, UserProfileModel model) {
        this.view = view;
        this.model = model;
        this.currentImage = model.getCurrentImage(); // Inizializza l'immagine corrente con quella del modello
        this.view.updateProfile(model); // Aggiorna la vista con i dati del modello

        // Carica l'immagine di hover
        try {
            hoverImage = ImageIO.read(new File("user.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Aggiungi un listener al pannello superiore della vista per gestire gli eventi del mouse
        this.view.getTopPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Apre un selettore di file per selezionare un'immagine
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        // Legge l'immagine selezionata dal file
                        currentImage = ImageIO.read(selectedFile);
                        // Imposta la nuova immagine corrente nel modello
                        model.setCurrentImage(currentImage);
                        // Aggiorna la vista con i nuovi dati del modello
                        view.updateProfile(model);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Imposta l'immagine di hover come immagine corrente quando il mouse entra nel pannello
                currentImage = hoverImage;
                // Aggiorna la vista con l'immagine di hover
                view.updateTopPanelImage(currentImage);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Ripristina l'immagine condivisa quando il mouse esce dal pannello
                currentImage = model.getCurrentImage();
                // Aggiorna la vista con l'immagine condivisa
                view.updateTopPanelImage(currentImage);
            }
        });
    }
}
