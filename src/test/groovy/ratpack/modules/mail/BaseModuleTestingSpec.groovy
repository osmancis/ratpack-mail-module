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

import ratpack.test.embed.BaseDirBuilder
import ratpack.test.embed.internal.PathBaseDirBuilder
import spock.lang.AutoCleanup
import spock.lang.Specification

import java.nio.file.Files

/**
 * @author Stefano Gualdi <stefano.gualdi@gmail.com>
 */
abstract class BaseModuleTestingSpec extends Specification {
  @AutoCleanup
  @Delegate
  BaseDirBuilder baseDir

  def setup() {
    // Setup base dir
    def tmp = Files.createTempDirectory("app")
    baseDir = new PathBaseDirBuilder(tmp)
  }

  def cleanup() {
  }
}
