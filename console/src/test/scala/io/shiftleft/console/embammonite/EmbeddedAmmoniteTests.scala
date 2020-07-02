package io.shiftleft.console.embammonite

import java.util.concurrent.Semaphore

import org.scalatest.{Matchers, WordSpec}

class EmbeddedAmmoniteTests extends WordSpec with Matchers {

  "EmbeddedAmmoniteShell" should {
    "start and shutdown without hanging" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      shell.shutdown()
    }

    "execute a command synchronously" in {
      val shell = new EmbeddedAmmonite()
      shell.start()
      val result = shell.query("def foo() = {\n1\n}\n foo()")
      result.out shouldBe
        """defined function foo
          |res1: Int = 1
          |""".stripMargin
      shell.shutdown()
    }

    "execute a command asynchronously" in {
      val shell = new EmbeddedAmmonite()
      val mutex = new Semaphore(0)
      shell.start()
      shell.queryAsync("val x = 0") { result =>
        result.out shouldBe "x: Int = 0\n"
        mutex.release()
      }
      mutex.acquire()
      shell.shutdown()
    }

  }

}
