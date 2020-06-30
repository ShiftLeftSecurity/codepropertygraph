package io.shiftleft.console.embammonite

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  "EmbeddedAmmoniteShell" should {
    "start and shutdown" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      shell.shutdown()
    }
  }

}
