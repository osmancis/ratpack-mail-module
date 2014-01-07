package ratpack.modules.mail

import java.nio.file.Files
import ratpack.test.embed.BaseDirBuilder
import ratpack.test.embed.PathBaseDirBuilder
import ratpack.groovy.test.embed.ClosureBackedEmbeddedApplication
import ratpack.groovy.test.TestHttpClients
import ratpack.groovy.test.TestHttpClient
import spock.lang.AutoCleanup
import spock.lang.Specification

abstract class BaseModuleTestingSpec extends Specification {
	@AutoCleanup
	@Delegate
	BaseDirBuilder baseDir

	@Delegate
	ClosureBackedEmbeddedApplication application

	@Delegate
  	TestHttpClient client

	def setup() {
		// Setup base dir
        def tmp = Files.createTempDirectory("app")
		baseDir = new PathBaseDirBuilder(tmp)

		// Setup application
		application = new ClosureBackedEmbeddedApplication(baseDir)

		// Setup client
		client = TestHttpClients.testHttpClient(application)
	}

	def cleanup() {
		// Close application
		application.close()
	}
}
