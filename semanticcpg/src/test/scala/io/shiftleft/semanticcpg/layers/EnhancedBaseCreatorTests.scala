package io.shiftleft.semanticcpg.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.{Matchers, WordSpec}

class EnhancedBaseCreatorTests extends WordSpec with Matchers {

  "EnhancedBaseCreator" should {

    "add name of overlay to metadata field" in {
      val cpg = MockCpg().withMetaData().cpg
      val context = new LayerCreatorContext(cpg, new SerializedCpg())
      new Scpg().run(context)
      Overlays.appliedOverlays(cpg) shouldBe List(Scpg.overlayName)
    }

  }

}
