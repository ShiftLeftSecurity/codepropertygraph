package io.shiftleft.semanticcpg.passes

import io.shiftleft.passes.OverlayNamePass
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class OverlayNamePassTests extends WordSpec with Matchers {

  "AddOverlayNamePass" should {

    "add name to CPG" in {
     val cpg = MockCpg().withMetaData().cpg
      new OverlayNamePass(cpg, "aname").createAndApply()
      new OverlayNamePass(cpg, "anothername").createAndApply()
      cpg.metaData.head.overlays shouldBe List("aname", "anothername")
    }
  }

}
