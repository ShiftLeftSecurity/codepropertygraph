package io.shiftleft.console.embammonite

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  "EmbeddedAmmoniteShell" should {
    "start and shutdown without hanging" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      shell.shutdown()
    }

    "execute a command synchronously and return output" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      val result = shell.query("def foo() = {\n1\n}\n foo()")
      result shouldBe
        """defined function foo
          |res1: Int = 1
          |""".stripMargin
      shell.shutdown()
    }
  }

}
