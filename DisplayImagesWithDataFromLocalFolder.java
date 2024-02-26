import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class DisplayImagesWithDataFromLocalFolder extends JPanel {
    public DisplayImagesWithDataFromLocalFolder(int utente,String categorie) {
        createAndShowGUI(utente,categorie);
    }

    private void createAndShowGUI(int utente,String categorie) {
        setLayout(new FlowLayout(FlowLayout.LEADING)); // Utilizza FlowLayout con wrap in alto

        ArrayList<ImageData> imageDataList = new ArrayList<>();
        String name;
        try {
            BufferedReader dataReader = new BufferedReader(new FileReader("oggetti.txt"));
            String line;
            while ((line = dataReader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 6) { // Assicurati che ci siano 6 campi
                    String imageName = fields[5];
                    if (imageName.startsWith("/uploads/")) {
                        imageName = imageName.substring("/uploads/".length());
                        File imageFile = new File("uploads/" + imageName);
                        if (imageFile.exists() && isImageFile(imageFile)) {
                            BufferedImage image = ImageIO.read(imageFile);
                            if (image != null) {
                                ImageData imageData = new ImageData();
                                imageData.image = resizeImage(image, 200, 200);
                                imageData.name = fields[0];
                                imageData.description = fields[1];
                                if (imageData.description.length() > 23) {//altrimenti troppo grande la descrizione
                                    imageData.description = imageData.description.substring(0, 20) + "...";
                                }
                                imageData.price = fields[2];
                                imageData.category = fields[3];
                                if(categorie==null){
                                    if (imageData.category.length() > 23) {//altrimenti troppo grande la descrizione
                                    imageData.category = imageData.category.substring(0, 20) + "...";
                                    } 
                                    imageDataList.add(imageData);
                                } else if(categorie!=null){// se ci sono categorie 
                                    String[] categorieArray = categorie.split(":");
                                    boolean categoryMatch = false;
                                    String[] imageDataCategories = imageData.category.split(":");
                                    boolean foundMatch = false;
                                    for (String imageDataCategory : imageDataCategories) {
                                        for (String category : categorieArray) {
                                            if (imageDataCategory.trim().equals(category.trim())) {
                                                categoryMatch = true;
                                                foundMatch = true; // Imposta la variabile per indicare che è stata trovata una corrispondenza
                                                break; // Interrompe solo il ciclo interno
                                            } else {
                                                categoryMatch = false;
                                            }
                                        }
                                        if (foundMatch) {
                                            break; // Interrompe il ciclo esterno se è stata trovata una corrispondenza
                                        }
                                    }
                                    if (imageData.category.length() > 23) {//altrimenti troppo grande la descrizione
                                    imageData.category = imageData.category.substring(0, 20) + "...";
                                    } 
                                    if (imageData.category.length() > 23) {//altrimenti troppo grande la descrizione
                                    imageData.description = imageData.description.substring(0, 20) + "...";
                                    }
                                    if (categoryMatch) {
                                    imageDataList.add(imageData);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            dataReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

for (ImageData imageData : imageDataList) {
    JPanel itemPanel = new JPanel();
    itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));
        itemPanel.add(new JLabel(new ImageIcon(imageData.image)));
        if (imageData.name.length() > 23) {//altrimenti troppo grande per il nome e non lo troverebbe i uploads
            name = imageData.name.substring(0, 20) + "...";
        }
        else {
            name = imageData.name;
        }
        itemPanel.add(new JLabel("Nome: " + name));
        //itemPanel.add(new JLabel("Descrizione: " + imageData.description));
        //itemPanel.add(new JLabel("Prezzo: " + imageData.price));
        itemPanel.add(new JLabel("Categoria: " + imageData.category));
    
    // Creazione del pulsante di riproduzione video
    ImageIcon buttonIcon = new ImageIcon("7A.png");//è il pulsante riproduci
    Image buttonImage = buttonIcon.getImage();
    Image newbuttonImage = buttonImage.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
    buttonIcon.setImage(newbuttonImage);
    JButton button = new JButton();
    button.setBorder(null);
    button.setBackground(null);
    button.setIcon(buttonIcon);
    button.setMinimumSize(new Dimension(50, 50));
    button.setPreferredSize(new Dimension(40, 40));
    if (utente == 1) {
        itemPanel.add(button);
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon hoverIcon = new ImageIcon("7B.png");
                Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(hoverImage));
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                File videoFile = new File("uploads/" + imageData.name+".mp4"); // Crea un oggetto File con il nome del video

                SwingUtilities.invokeLater(() -> {
                // Ottieni l'istanza di default del toolkit
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                // Ottieni le dimensioni dello schermo
                Dimension screenSize = toolkit.getScreenSize();
                int screenWidth = (int) screenSize.getWidth(); // Converti double in int
                int screenHeight = (int) screenSize.getHeight(); // Converti double in int
                
                JFrame videoFrame = new JFrame(imageData.name);
                videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                videoFrame.setSize(screenWidth, screenHeight-35);

                VideoPlayer videoPlayer = new VideoPlayer(videoFile);
                videoFrame.add(videoPlayer);

                videoFrame.setVisible(true);
                
            });
            }
            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon hoverIcon = new ImageIcon("7A.png");
                Image hoverImage = hoverIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(hoverImage));
            }
        });
        // Aggiungi strutture di spaziatura vuote tra gli elementi
        itemPanel.add(Box.createVerticalStrut(50));
    } else if (utente == 0) {
        // Aggiungi strutture di spaziatura vuote tra gli elementi
        itemPanel.add(Box.createVerticalStrut(10));
    }

    add(itemPanel);
}
}

    private static boolean isImageFile(File file) {
        String name = file.getName().toLowerCase();
        return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif") || name.endsWith(".bmp");
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }

    static class ImageData {
        BufferedImage image;
        String name;
        String description;
        String price;
        String category;
    }
}/*Strategy Pattern:
L'uso di strategie diverse per filtrare le immagini in base alle categorie può essere considerato una forma di Strategy Pattern. 
La logica per selezionare e filtrare le immagini in base alle categorie utilizza diverse strategie di filtraggio.

Model-View-Controller (MVC): La classe ImageData rappresenta il modello dei dati per le immagini. Contiene le informazioni sull'immagine come nome, 
descrizione, prezzo e categoria.La classe DisplayImagesWithDataFromLocalFolder è la vista. Si occupa di visualizzare le immagini e le relative informazioni.
Mentre per il controller non è presente una classe esplicita che funge da controller però il comportamento di risposta agli eventi del mouse per il 
pulsante di riproduzione del video agisce come controller

Observer Pattern:
L'aggiunta di un ActionListener ai pulsanti ("Riproduci") per gestire l'apertura di una finestra video mostra l'uso del pattern 
Observer per gestire gli eventi.
*/