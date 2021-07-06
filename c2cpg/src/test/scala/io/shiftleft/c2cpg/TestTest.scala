package io.shiftleft.c2cpg

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TestTest extends AnyWordSpec with Matchers {
  val fixture = CpgTestFixture("testtest")

  "Tests" should {
    "load graphs" in {
      fixture.cpg.graph.nodeCount should be > 0
    }
  }

}
