package io.shiftleft.console.embammonite

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  "EmbeddedAmmoniteShell" should {
    "start and shutdown without hanging" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      shell.shutdown()
    }

    "execute a command and return output" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      val uuid = UUID.randomUUID()
      shell.enqueue(uuid, "def foo() = {\n1\n}\n foo()")
      println(shell.result(uuid))
      shell.shutdown()
    }
  }

}
