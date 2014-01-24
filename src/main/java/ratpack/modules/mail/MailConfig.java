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

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
public interface MailConfig {
  String getHost();

  void setHost(String host);

  int getPort();

  void setPort(int port);

  String getProtocol();

  void setProtocol(String protocol);

  boolean isAuth();

  void setAuth(boolean auth);

  String getUsername();

  void setUsername(String username);

  String getPassword();

  void setPassword(String password);

  boolean isStarttls();

  void setStarttls(boolean starttls);

  String getChannel();

  void setChannel(String channel);

  boolean isDebug();

  void setDebug(boolean debug);
}
