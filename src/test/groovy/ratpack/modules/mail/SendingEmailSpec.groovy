package ratpack.modules.mail

import ratpack.form.UploadedFile

import static ratpack.form.Forms.form

class SendingEmailSpec extends BaseEmailTestingSpec {
  def "can send a simple email"() {
    given:
    modules {
      register new MailModule()
    }

    handlers { MailService service ->
      post {
        def email = createEmail("me@me.org", "you@you.org", "test message", "This is a test message")
        service.send(email)
        response.send()
      }
    }

    when:
    post()

    then:
    response.statusCode == 200
    getReceivedMessages().size() == 1
    getLastMessage().subject == "test message"
  }

  def "can send an email with attachment"() {
    given:
    def fooFile = file "foo.txt", "dummy text"

    and:
    modules {
      register new MailModule()
    }

    handlers { MailService service ->
      post {
        def form = parse form()

        UploadedFile f = form.file("theFile")

        // Write the file
        def fileName = saveUploadedFile(f, "uploadedFile.txt")

        def email = createEmail(
          "me@me.org",
          "you@you.org",
          "test message",
          "This is a test message with attachment")

        email.attach(fileName)
        service.send(email)

        render "sent"
      }
    }

    when:
    // Send the file
    request.multiPart("theFile", fooFile.toFile())
    postText() == "sent"

    then:
    // Message received?
    def messages = getReceivedMessages()
    messages.size() == 1

    and:
    // Has attachment?
    def attachments = extractAttachments(messages[0])
    attachments.size() == 1

    and:
    // Attachment's filename and content are correct
    attachments[0].name == "uploadedFile.txt"
    attachments[0].text == "dummy text"
  }
}
