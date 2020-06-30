package io.shiftleft.console.embammonite

import java.util.UUID

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  "EmbeddedAmmoniteShell" should {
    "start and shutdown" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      shell.shutdown()
    }

    "execute a command and return output" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      val uuid = UUID.randomUUID()
      shell.enqueue(uuid, "help")
      shell.result(uuid)
      shell.shutdown()
    }
  }

}
