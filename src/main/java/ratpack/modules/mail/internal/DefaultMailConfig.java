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

package ratpack.modules.mail.internal;

import com.google.common.base.Objects;
import ratpack.modules.mail.MailConfig;

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
public class DefaultMailConfig implements MailConfig {

  private String host;
  private int port = 25;
  private String protocol = "smtp";
  private boolean auth;
  private String username;
  private String password;
  private boolean starttls;
  private String channel = "plain";
  private boolean debug;

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getProtocol() {
    return protocol;
  }

  public void setProtocol(String protocol) {
    this.protocol = protocol;
  }

  public boolean isAuth() {
    return auth;
  }

  public void setAuth(boolean auth) {
    this.auth = auth;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isStarttls() {
    return starttls;
  }

  public void setStarttls(boolean starttls) {
    this.starttls = starttls;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public boolean isDebug() {
    return debug;
  }

  public void setDebug(boolean debug) {
    this.debug = debug;
  }

  @Override
  public String toString() {
    return Objects.toStringHelper(this.getClass())
      .add("host", host)
      .add("port", port)
      .add("protocol", protocol)
      .add("auth", auth)
      .add("username", username)
      .add("password", password)
      .add("starttls", starttls)
      .add("debug", debug)
      .toString();
  }
}
