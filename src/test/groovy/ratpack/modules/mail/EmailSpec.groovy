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

package ratpack.modules.mail

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import ratpack.modules.mail.internal.DefaultMailConfig
import ratpack.modules.mail.internal.DefaultMailService
import spock.lang.Shared
import spock.lang.Specification

import javax.mail.Message

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
class EmailSpec extends Specification {

  @Shared
  GreenMail greenMail = null

  def setupSpec() {
    greenMail = new GreenMail(ServerSetupTest.SMTP)
    greenMail.start()
  }

  def cleanupSpec() {
    greenMail.stop()
  }

  def cleanup() {
    // Delete all messages after each feature
    greenMail.managers.imapHostManager.store.listMailboxes('*')*.deleteAllMessages()
  }

  def "greenmail is initialized"() {
    expect:
    greenMail != null
  }

  def "can send email with greenmail"() {
    when:
    GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com", "subject", "body")

    then:
    greenMail.getReceivedMessages()[0].getSubject() == "subject"
  }

  def "can send email with internal service"() {
    setup:
    def mailConfig = new DefaultMailConfig()
    mailConfig.setHost("localhost")
    mailConfig.setPort(greenMail.getSmtp().getPort())
    def service = new DefaultMailService(mailConfig)

    Email email = new EmailMessage()
    email.from("me@me.org")
      .to("you@you.org")
      .subject("test message")
      .text("This is a test message")

    when:
    service.send(email)

    then:
    Message[] messages = greenMail.getReceivedMessages()
    messages.size() == 1
    messages[0].subject == "test message"
  }
}
