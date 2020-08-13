package io.shiftleft.semanticcpg.layers

import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EnhancedBaseCreatorTests extends AnyWordSpec with Matchers {

  "EnhancedBaseCreator" should {

    "add name of overlay to metadata field" in {
      val cpg = MockCpg().withMetaData().cpg
      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
      Overlays.appliedOverlays(cpg) shouldBe List(Scpg.overlayName)
    }

  }

}
