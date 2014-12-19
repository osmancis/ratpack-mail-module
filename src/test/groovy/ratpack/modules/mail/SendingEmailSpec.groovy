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

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.handler.codec.http.DefaultFullHttpRequest
import io.netty.handler.codec.http.HttpRequest
import io.netty.handler.codec.http.HttpVersion
import io.netty.handler.codec.http.multipart.HttpPostRequestEncoder
import ratpack.form.UploadedFile

import ratpack.http.client.RequestSpec

import ratpack.form.Form

import ratpack.groovy.test.embed.GroovyEmbeddedApp
import ratpack.test.embed.EmbeddedApp

import ratpack.test.http.TestHttpClient
import ratpack.test.http.TestHttpClients

import spock.lang.AutoCleanup

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
class SendingEmailSpec extends BaseEmailTestingSpec {
  @AutoCleanup
  EmbeddedApp app  
  
  @Delegate
  TestHttpClient client

  def "can send a simple email"() {
    given:
    app = GroovyEmbeddedApp.build {
      bindings {
        add new MailModule()
      }

      handlers { MailService service ->
        post {
          def email = createEmail("me@me.org", "you@you.org", "test message", "This is a test message")
          service.send(email)
          response.send()
        }
      }
    }

    and:
    client = TestHttpClients.testHttpClient(app)

    expect:
    post()
    response.statusCode == 200
    getReceivedMessages().size() == 1
    getLastMessage().subject == "test message"
  }

  def "can send an email with attachment"() {
    given:
    def testFileName = "foo.txt"
    def testUploadedFileName = "uploadedFile.txt"
    def testFileContent = "dummy text"
    def fooFile = file testFileName, testFileContent

    and:
    app = GroovyEmbeddedApp.build {
      bindings {
        add new MailModule()
      }

      handlers { MailService service ->
        post {
          def form = parse Form

          UploadedFile f = form.file("theFile")

          // Write the file
          def fileName = saveUploadedFile(f, testUploadedFileName)

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
    }

    and:
    client = TestHttpClients.testHttpClient(app)

    when:
    // Send the file
    requestSpec { RequestSpec requestSpec ->
      HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, io.netty.handler.codec.http.HttpMethod.POST, "/")
      HttpPostRequestEncoder httpPostRequestEncoder = new HttpPostRequestEncoder(request, true)
      httpPostRequestEncoder.addBodyFileUpload("theFile", fooFile.toFile(), "text/plain", true)
      
      request = httpPostRequestEncoder.finalizeRequest()
      
      request.headers().each {
        requestSpec.headers.set(it.key, it.value)
      }

      def chunks = []
      while (!httpPostRequestEncoder.isEndOfInput()) {
        chunks << httpPostRequestEncoder.readChunk(null).content()
      }

      requestSpec.body.buffer(Unpooled.wrappedBuffer(chunks as ByteBuf[]))
    }

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
    attachments[0].name == testUploadedFileName
    attachments[0].text == testFileContent
  }
}
