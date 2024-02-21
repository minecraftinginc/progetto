import java.awt.Image;
public class SharedImage {
    private static Image currentImage;

    public static Image getCurrentImage() {
        return currentImage;
    }

    public static void setCurrentImage(Image newImage) {
        currentImage = newImage;
    }
}