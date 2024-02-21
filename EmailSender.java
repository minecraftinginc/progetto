import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class EmailSender {

    public void sendEmail(String recipientEmail) {
        final String username = "zenocodino@gmail.com"; // Inserisci qui il tuo indirizzo email Gmail
        final String password = "nuzs cvep rexw hwoj"; // Password specifica per l'applicazione

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");//Per SSL, inserisci 465. Per TLS, inserisci 587.

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail)); // Utilizza l'email passata come destinatario
            message.setSubject("Iscrizione alla ED Film");
            message.setText("Le inviamo questa email per informarla che siete ufficialmente iscritti alla ED film e ora non potrete pi� essere liberi");

            Transport.send(message);

            System.out.println("Email inviata con successo a " + recipientEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
/*io quasi non lo metterei perchè non funziona anche se non mi dà errori però ci ho lavorato evidentemente ho qualche problema con gmail */