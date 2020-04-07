package io.shiftleft.semanticcpg

import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.{Matchers, WordSpec}
import io.shiftleft.semanticcpg.language._

class OverlaysTests extends WordSpec with Matchers {

  "Overlays" should {

    "allow adding a name to the `overlays` list" in {
      val cpg = MockCpg().withMetaData().cpg
      Overlays.appendOverlayName(cpg, "foo")
      Overlays.appendOverlayName(cpg, "bar")
      cpg.metaData.head.overlays shouldBe List("foo", "bar")
    }

    "allow querying `overlays`" in {
      val cpg = MockCpg().withMetaData().cpg
      Overlays.appendOverlayName(cpg, "foo")
      Overlays.appendOverlayName(cpg, "bar")
      Overlays.appliedOverlays(cpg) shouldBe List("foo", "bar")
    }

  }

}
