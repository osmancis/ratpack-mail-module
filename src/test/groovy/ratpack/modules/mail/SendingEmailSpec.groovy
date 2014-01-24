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

import ratpack.form.UploadedFile

import static ratpack.form.Forms.form

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
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
