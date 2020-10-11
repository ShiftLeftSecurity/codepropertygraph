package io.shiftleft.fuzzyc2cpg

import org.scalatest.{Matchers, WordSpec}

class TestTest extends WordSpec with Matchers {
  val fixture = CpgTestFixture("testtest")

  "Tests" should {
    "load graphs" in {
      fixture.cpg.graph.nodeCount should be > 0
    }
  }

}
