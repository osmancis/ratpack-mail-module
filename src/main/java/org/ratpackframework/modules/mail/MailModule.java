package org.ratpackframework.modules.mail;

import org.ratpackframework.launch.LaunchConfig;
import com.google.inject.AbstractModule;

public class MailModule extends AbstractModule {

    private final LaunchConfig launchConfig;

	public MailModule(LaunchConfig launchConfig) {
        this.launchConfig = launchConfig;
    }

	@Override
    protected void configure() {
        MailConfig config = new MailConfig();
        config.setHost(launchConfig.getOther("mail.host", "localhost"));
        config.setPort(Integer.parseInt(launchConfig.getOther("mail.port", "25")));
        config.setProtocol(launchConfig.getOther("mail.protocol", "smtp"));
        config.setAuth(Boolean.parseBoolean(launchConfig.getOther("mail.auth", "false")));
        config.setUsername(launchConfig.getOther("mail.username", null));
        config.setPassword(launchConfig.getOther("mail.password", null));
        config.setStarttls(Boolean.parseBoolean(launchConfig.getOther("mail.starttls", "false")));
        config.setChannel(launchConfig.getOther("mail.channel", "plain"));
        config.setDebug(Boolean.parseBoolean(launchConfig.getOther("mail.debug", "false")));
        bind(MailService.class).toInstance(new MailService(config));
    }
}
