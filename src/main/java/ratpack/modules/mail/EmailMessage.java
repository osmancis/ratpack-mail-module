/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ratpack.modules.mail;

import com.google.common.base.Objects;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
public class EmailMessage implements Email {
  private String fromAddress;
  private Set<String> toAddresses = new HashSet<>();
  private Set<String> ccAddresses = new HashSet<>();
  private Set<String> bccAddresses = new HashSet<>();
  private Set<String> attachments = new HashSet<>();
  private String subject;
  private String text;

  public Email from(String address) {
    this.fromAddress = address;
    return this;
  }

  public Email to(String address) {
    this.toAddresses.add(address);
    return this;
  }

  public Email cc(String address) {
    this.ccAddresses.add(address);
    return this;
  }

  public Email bcc(String address) {
    this.bccAddresses.add(address);
    return this;
  }

  public Email subject(String subject) {
    this.subject = subject;
    return this;
  }

  public Email text(String text) {
    this.text = text;
    return this;
  }

  public Email attach(String filename) {
    this.attachments.add(filename);
    return this;
  }

  public Email attach(File filename) {
    this.attachments.add(filename.getAbsolutePath());
    return this;
  }

  public String getFromAddress() {
    return fromAddress;
  }

  public Set<String> getToAddresses() {
    return toAddresses;
  }

  public Set<String> getCcAddresses() {
    return ccAddresses;
  }

  public Set<String> getBccAddresses() {
    return bccAddresses;
  }

  public Set<String> getAttachments() {
    return attachments;
  }

  public String getSubject() {
    return subject;
  }

  public String getText() {
    return text;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this.getClass())
      .add("from", fromAddress)
      .add("toAddress", toAddresses)
      .add("ccAddress", ccAddresses)
      .add("bccAddress", bccAddresses)
      .add("subject", subject)
      .add("attachments", attachments)
      .toString();
  }
}
