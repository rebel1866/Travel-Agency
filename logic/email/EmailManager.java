package by.epamtc.stanislavmelnikov.logic.email;

import by.epamtc.stanislavmelnikov.controller.controllerutils.PropertyLoader;
import by.epamtc.stanislavmelnikov.logic.exception.LogicException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Properties;

public class EmailManager {
    private final static String userKey = "email.user";
    private final static String passwordKey = "email.password";
    private final static String hostKey = "email.host";
    private final static String portKey = "email.port";

    public static void sendEmail(String email, String mes, String subject) throws LogicException {
        Logger logger = LogManager.getLogger(EmailManager.class);
        String user;
        String password;
        try {
            user = PropertyLoader.getProperty(userKey);
            password = PropertyLoader.getProperty(passwordKey);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new LogicException("cannot load properties", e);
        }
        Properties prop = getProperties();
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mes, "text/html; charset=utf-8");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            throw new LogicException("cannot send email", e);
        }
    }

    public static Properties getProperties() throws LogicException {
        String host;
        String port;
        try {
            host = PropertyLoader.getProperty(hostKey);
            port = PropertyLoader.getProperty(portKey);
        } catch (IOException e) {
            Logger logger = LogManager.getLogger(EmailManager.class);
            logger.error(e.getMessage(), e);
            throw new LogicException("cannot load properties", e);
        }
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", host);
        prop.put("mail.smtp.port", port);
        prop.put("mail.smtp.ssl.trust", host);
        return prop;
    }
}
