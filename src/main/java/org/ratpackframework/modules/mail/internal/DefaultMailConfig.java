package org.ratpackframework.modules.mail.internal;

import com.google.common.base.Objects;
import org.ratpackframework.modules.mail.MailConfig;

public class DefaultMailConfig implements MailConfig {

    private String host = null;
    private int port = 25;
    private String protocol = "smtp";
    private boolean auth = false;
    private String username = null;
    private String password = null;
    private boolean starttls = false;
    private String channel = "plain";
    private boolean debug = false;

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
