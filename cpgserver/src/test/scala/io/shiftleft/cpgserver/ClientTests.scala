package io.shiftleft.cpgserver

import java.nio.file.Paths

import org.scalatest.{Matchers, WordSpec}

class ClientTests extends WordSpec with Matchers {

  "should execute cpgclientlibtests successfully" in {
    import scala.sys.process._
    val currentWorkingDirectory = new java.io.File(".").getAbsolutePath
    val testDir = Paths.get(currentWorkingDirectory, "cpgclientlib").toAbsolutePath.toString
    val testScript = Paths.get(testDir.toString, "runtests.sh").toAbsolutePath.toString
    sys.process.Process(Seq(testScript), new java.io.File(testDir)).!!
  }

}
