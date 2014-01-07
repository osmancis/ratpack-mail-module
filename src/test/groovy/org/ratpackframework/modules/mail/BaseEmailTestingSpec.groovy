package org.ratpackframework.modules.mail

import com.icegreen.greenmail.util.GreenMail
import com.icegreen.greenmail.util.ServerSetupTest
import org.apache.commons.lang3.StringUtils
import ratpack.form.UploadedFile
import spock.lang.Shared

import javax.mail.BodyPart
import javax.mail.Message
import javax.mail.Multipart
import javax.mail.Part

abstract class BaseEmailTestingSpec extends BaseModuleTestingSpec {
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

    protected Email createEmail(String from, String to, String subject, String text) {
        Email email = new EmailMessage()
        email.from(from)
                .to(to)
                .subject(subject)
                .text(text)
    }

    protected Message[] getReceivedMessages() {
        greenMail.getReceivedMessages()
    }

    protected Message getLastMessage() {
        def messages = getReceivedMessages()
        messages[messages.size() - 1]
    }

    protected List<File> extractAttachments(Message message) {
        List<File> attachments = new ArrayList<File>()
        Multipart multipart = (Multipart) message.getContent()

        for (int i = 0; i < multipart.getCount(); i++) {
            BodyPart bodyPart = multipart.getBodyPart(i);
            if (!Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) && !StringUtils.isNotBlank(bodyPart.getFileName())) {
                continue; // dealing with attachments only
            }

            InputStream is = bodyPart.getInputStream()

            File f = file(bodyPart.getFileName()).toFile()
            FileOutputStream fos = new FileOutputStream(f)
            byte[] buf = new byte[4096]
            int bytesRead
            while ((bytesRead = is.read(buf)) != -1) {
                fos.write(buf, 0, bytesRead)
            }
            fos.close();
            attachments.add(f)
        }

        return attachments
    }

    protected String saveUploadedFile(UploadedFile f, String fileName) {
        def tmpFileName = file(fileName).toString()
        FileOutputStream fos = new FileOutputStream(tmpFileName)
        fos.write(f.bytes)
        fos.close()
        return tmpFileName
    }
}