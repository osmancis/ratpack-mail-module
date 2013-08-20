package org.ratpackframework.modules.mail

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.GreenMailUtil
import com.icegreen.greenmail.util.ServerSetupTest
import org.ratpackframework.modules.mail.Email
import org.ratpackframework.modules.mail.EmailMessage
import org.ratpackframework.modules.mail.MailConfig
import org.ratpackframework.modules.mail.MailService
import spock.lang.Shared
import spock.lang.Specification

import javax.mail.Message

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
class EmailSpec extends Specification {

    @Shared GreenMail greenMail = null

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
        def mailConfig = new MailConfig()
        mailConfig.setHost("localhost")
        mailConfig.setPort(greenMail.getSmtp().getPort())
        def service = new MailService(mailConfig)

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
