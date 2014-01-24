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
