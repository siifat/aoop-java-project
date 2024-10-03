package lms.util;

import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailService {

    public static boolean sendEmail(String recipientEmail, String messageBody) {

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myEmail = "sifatullah.dev@gmail.com";
        String password = "ercb unfu iaas iigb";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, password);
            }
        });

        Message message = preparedMessage(session, myEmail, recipientEmail, messageBody);

        if (message != null) {
            Platform.runLater(() ->
                    new Alert(Alert.AlertType.INFORMATION, "Email send Successfully!").show());

        } else {
            Platform.runLater(() ->
                    new Alert(Alert.AlertType.INFORMATION, "Email send hoynai!").show());
        }

        try {
            Transport.send(message);
            return true;

        } catch (MessagingException e) {

            System.out.println(e.getMessage());
            return false;
        }
    }

    private static Message preparedMessage(Session session, String myEmail, String recipientEmail, String msg) {

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myEmail));
            message.setRecipients(Message.RecipientType.TO, new InternetAddress[]{new InternetAddress(recipientEmail)});

            message.setSubject("Security Code");
            message.setText(msg);

            return message;

        } catch (MessagingException m) {
            System.out.println(m.getMessage());
        }

        return null;
    }

    public static int generateRandomCode() {
        return (int) (Math.random() * 90000000) + 10000000;
    }
}
