package io.shiftleft.semanticcpg.passes

import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class CpgPassTests extends WordSpec with Matchers {

  "CpgPass" should {
    "add overlay name to CPG when running pass" in {
      val cpg = MockCpg().withMetaData().cpg
      class MyPass extends CpgPass(cpg, "foo") {
        override def run(): Iterator[DiffGraph] = {
          Iterator()
        }
        new MyPass().run()
        cpg.metaData.head.overlays shouldBe List("foo")
      }
    }
  }

}
