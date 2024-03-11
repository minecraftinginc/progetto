import java.awt.Image;

public class UserProfileModel {
    private String username;
    private String surname;
    private String email;
    private String name;
    private String date;
    private Image currentImage;

    public UserProfileModel(String username, String surname, String email, String name, String date, Image currentImage) {
        this.username = username;
        this.surname = surname;
        this.email = email;
        this.name = name;
        this.date = date;
        this.currentImage = currentImage;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    // Aggiungi il metodo per impostare l'immagine corrente
    public void setCurrentImage(Image image) {
        this.currentImage = image;
    }
}
