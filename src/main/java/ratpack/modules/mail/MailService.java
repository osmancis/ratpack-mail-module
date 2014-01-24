package ratpack.modules.mail;

import javax.mail.MessagingException;

public interface MailService {
  void send(Email email) throws MessagingException;
}