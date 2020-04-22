import com.sun.mail.smtp.SMTPSaslAuthenticator;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import java.util.Properties;

public class SendEmail {
    private Properties properties;
    private Session session;
    private String username;
    private String password;

    public SendEmail(String server, String username, String password) {
        this.username = username;
        this.password = password;

        /**
         * Sets the properties for the mail-transport session.
         * Enables TLS
         * Sets port to g-mail smtp-port to 587
         * sets smtp-host for the receiving server
         * sets transport protocol to SMTP
         * enables smtp authentication
         */
        properties = System.getProperties();
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", 587);
        properties.setProperty("mail.smtp.host", server);
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.auth", "true");

        /**
         * Creates an authenticator object for the SMTP session
         */
        Authenticator auth = new SMTPAuthenticator();

        /**
         * Makes session for the transmisson using the properties set earlier
         * and applies authentication to the session
         */
        session = Session.getDefaultInstance(properties, auth);

    }

    /**
     * Creates an PasswordAthenticator for the SMTP Session
     */
    private class SMTPAuthenticator extends javax.mail.Authenticator {
        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
        }
    }

    /**
     * Method for sending emails
     *
     * @param from    - senders of the email
     * @param to      - recivers of the email
     * @param subject - subject for the email
     * @param body    - body text for the email
     * @return returns the result of sending the email
     */
    public String send(String from, String to, String subject, String body) {

        String resultText = "";

        /**
         * Creates an message for the email and sets the properites for "to", "Subject" and "body"
         */
        MimeMessage message = new MimeMessage(session);
        try {
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);
        } catch (AddressException e) {
            resultText = resultText + "Invalid email address\n";
            e.printStackTrace();
            return resultText;
        } catch (MessagingException e) {
            resultText = resultText + "Invalid email message text\n";
            e.printStackTrace();
            return resultText;
        }

        /**
         * sends the message and returns a message with the result of the sending of the email
         */
        try {
            Transport transport = session.getTransport();
            transport.send(message);
            resultText = resultText + "Message sent succesfully! \n";
            return resultText;
        } catch (MessagingException e) {
            resultText = resultText + "Message NOT sent succesfully!!!";
                    e.printStackTrace();
        }
        return resultText;
    }
}
