import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class UserProfileFrame extends JFrame {

    public UserProfileFrame(String username, String surname, String email, String name, String date, Image current) {
        // Carica l'immagine predefinita e l'immagine di hover
        Image defaultImage = null;
        Image hoverImage = null;
        try {
            defaultImage = ImageIO.read(new File("spiaggia.jpg"));
            hoverImage = ImageIO.read(new File("user.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Crea il modello
        UserProfileModel model = new UserProfileModel(username, surname, email, name, date, defaultImage);

        // Crea la vista
        UserProfileView view = new UserProfileView(defaultImage, hoverImage);

        // Passa l'immagine di hover al controller
        UserProfileController controller = new UserProfileController(view, model);
    }
}// MVC 