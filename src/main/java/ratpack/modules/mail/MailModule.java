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

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import ratpack.launch.LaunchConfig;
import ratpack.modules.mail.internal.DefaultMailConfig;
import ratpack.modules.mail.internal.DefaultMailService;

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
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
