package org.ratpackframework.modules.mail;

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

    String getFromAddress();
   	Set<String> getToAddresses();
   	Set<String> getCcAddresses();
   	Set<String> getBccAddresses();
   	Set<String> getAttachments();
   	String getSubject();
   	String getText();
}
