package ms.reporting;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.*;
import ms.utils.FileUtils;
import ms.utils.PropertiesLoader;

import jakarta.mail.internet.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EmailReport {
    private static Logger logger = LogManager.getLogger(EmailReport.class);
    private static final String pattern = "E, dd MMM yyyy HH:mm:ss z";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public static void sendReportToEmail() {

        // Recipient's email ID needs to be mentioned.
        final String recipient = System.getProperty("user_email").toString();
        // Sender's email ID needs to be mentioned
        final String sender = System.getProperty("sender_email").toString();
        final String password = System.getProperty("sender_password");
        // Assuming you are sending email from localhost
        final String host = "localhost";
        // Get system properties
        // Setup mail server
        PropertiesLoader.properties.setProperty("mail.smtp.host", host);
        PropertiesLoader.properties.setProperty("mail.transport.protocol", "smtp");
        PropertiesLoader.properties.put("mail.smtp.auth", true);
        PropertiesLoader.properties.put("mail.smtp.starttls.enable", true);
        PropertiesLoader.properties.put("mail.smtp.host", "smtp.gmail.com");
        PropertiesLoader.properties.put("mail.smtp.port", "587");

        // Get the default Session object.
        Session session = Session.getDefaultInstance(PropertiesLoader.properties,
                new Authenticator(){
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                sender, password);// Specify the Username and the PassWord
                    }
                });


        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject("Automation Test Report for : " + simpleDateFormat.format(new Date()));
            final Multipart multipart = new MimeMultipart();
            final MimeBodyPart messageBodyPart = new MimeBodyPart();
            final String content =
                    FileUtils.getContentFromHtmlFile("target/surefire-reports/emailable-report.html");
            messageBodyPart.setContent(content, "text/html");
            multipart.addBodyPart(messageBodyPart);

            addAttachments(multipart, "logs/application/Testlog.log");
            message.setContent(multipart);
            logger.debug("sending report...");
            Transport.send(message);
            logger.debug("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static void addAttachments(Multipart multipart, String fileName) throws MessagingException {
        final DataSource source = new FileDataSource(fileName);
        final MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(fileName);
        multipart.addBodyPart(messageBodyPart);
    }
}
