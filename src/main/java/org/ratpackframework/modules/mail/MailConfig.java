package org.ratpackframework.modules.mail;

import com.google.common.base.Objects;

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
