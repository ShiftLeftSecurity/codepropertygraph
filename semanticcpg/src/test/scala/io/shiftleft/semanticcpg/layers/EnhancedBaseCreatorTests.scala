package io.shiftleft.semanticcpg.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.semanticcpg.Overlays
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.{Matchers, WordSpec}

class EnhancedBaseCreatorTests extends WordSpec with Matchers {

  "EnhancedBaseCreator" should {

    "add name of overlay to metadata field" in {
      val cpg = MockCpg().withMetaData().cpg
      val creator = new EnhancedBaseCreator(cpg, "C", new SerializedCpg())
      creator.create()
      Overlays.appliedOverlays(cpg) shouldBe List(EnhancedBaseCreator.overlayName)
    }

  }

}
