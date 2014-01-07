package ratpack.modules.mail.internal;

import com.sun.mail.smtp.SMTPTransport;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Calendar;
import java.util.Properties;

import ratpack.modules.mail.MailConfig;
import ratpack.modules.mail.MailService;
import ratpack.modules.mail.Email;

import javax.inject.Inject;

public class DefaultMailService implements MailService {
    private MailConfig config;
    private static Session session;

    @Inject
    public DefaultMailService(MailConfig config) {
        this.config = config;
    }

    public void send(Email email) throws MessagingException {
        Message message = prepareMessage(email);
        send(message);
    }

    private Session getSession() {
        if (session == null) {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", config.getHost());
            properties.put("mail.smtp.port", config.getPort());
            properties.put("mail.smtp.protocol", config.getProtocol());
            properties.put("mail.smtp.auth", config.isAuth() ? "true" : "false");
            properties.put("mail.smtp.starttls.enable", config.isStarttls() ? "true" : "false");
            properties.put("mail.smtp.channel", config.getChannel());
            properties.put("mail.debug", config.isDebug() ? "true" : "false");

            session = Session.getInstance(properties);
        }
        return session;
    }

    private Message prepareMessage(Email email) throws MessagingException {
        Multipart multipart = new MimeMultipart();

        MimeBodyPart mimeText = new MimeBodyPart();
        mimeText.setText(email.getText(), "utf-8");
        mimeText.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
        mimeText.setHeader("Content-Transfer-Encoding", "quoted-printable");
        multipart.addBodyPart(mimeText);

        Message message = new MimeMessage(getSession());
        message.setFrom(new InternetAddress(email.getFromAddress()));

        for (String to : email.getToAddresses()) {
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        }

        for (String cc : email.getCcAddresses()) {
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc));
        }

        for (String bcc : email.getBccAddresses()) {
            message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));
        }

        for (String attachment : email.getAttachments()) {
            MimeBodyPart mimeAttachment = new MimeBodyPart();
            FileDataSource fds = new FileDataSource(attachment);
            mimeAttachment.setDataHandler(new DataHandler(fds));
            mimeAttachment.setFileName(fds.getName());
            multipart.addBodyPart(mimeAttachment);
        }

        message.setContent(multipart);
        message.setSubject(email.getSubject());
        message.setHeader("X-Mailer", "Ratpack Mail Module");
        message.setSentDate(Calendar.getInstance().getTime());

        return message;
    }

    protected void send(Message message) throws MessagingException {
        String protocol = config.getProtocol();

        SMTPTransport smtpTransport = (SMTPTransport) getSession().getTransport(protocol);
        if (config.isAuth()) {
            smtpTransport.connect(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
        }
        else {
            smtpTransport.connect();
        }

        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }
}
