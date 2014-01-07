package org.ratpackframework.modules.mail;

import org.ratpackframework.modules.mail.internal.DefaultMailConfig;
import org.ratpackframework.modules.mail.internal.DefaultMailService;
import ratpack.launch.LaunchConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MailModule extends AbstractModule {

	@Override
    protected void configure() {
        bind(MailService.class).to(DefaultMailService.class);
    }

    @SuppressWarnings("UnusedDeclaration")
    @Provides
    MailConfig provideMailConfig(LaunchConfig launchConfig) {
        MailConfig config = new DefaultMailConfig();

        config.setHost(launchConfig.getOther("mail.host", "localhost"));
        config.setPort(Integer.parseInt(launchConfig.getOther("mail.port", "25")));
        config.setProtocol(launchConfig.getOther("mail.protocol", "smtp"));
        config.setAuth(Boolean.parseBoolean(launchConfig.getOther("mail.auth", "false")));
        config.setUsername(launchConfig.getOther("mail.username", null));
        config.setPassword(launchConfig.getOther("mail.password", null));
        config.setStarttls(Boolean.parseBoolean(launchConfig.getOther("mail.starttls", "false")));
        config.setChannel(launchConfig.getOther("mail.channel", "plain"));
        config.setDebug(Boolean.parseBoolean(launchConfig.getOther("mail.debug", "false")));

        return config;
    }
}
