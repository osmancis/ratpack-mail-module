package ratpack.modules.mail;

import java.io.File;
import java.util.Set;

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
public interface Email {
  Email from(String address);

  Email to(String address);

  Email cc(String address);

  Email bcc(String address);

  Email subject(String subject);

  Email text(String body);

  Email attach(String filename);

  Email attach(File filename);

  String getFromAddress();

  Set<String> getToAddresses();

  Set<String> getCcAddresses();

  Set<String> getBccAddresses();

  Set<String> getAttachments();

  String getSubject();

  String getText();
}
