package io.shiftleft.cpgserver

import org.scalatest.{Matchers, WordSpec}

class ClientTests extends WordSpec with Matchers {

  "should execute cpgclientlibtests successfully" in {
    import scala.sys.process._
    println(new java.io.File(".").getCanonicalPath())
    sys.process.Process(Seq("/home/tmp/shiftleft/codepropertygraph/cpgclientlib/runtests.sh"), new java.io.File("/home/tmp/shiftleft/codepropertygraph/cpgclientlib/")).!!
  }

}
