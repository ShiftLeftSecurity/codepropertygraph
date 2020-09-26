package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DumpCdgTests extends AnyWordSpec with Matchers {

  "DumpCdg" should {

    "create two dot files for a CPG containing two methods" in {
      val cpg = MockCpg()
        .withMetaData()
        .withMethod("foo")
        .withMethod("bar")
        .cpg

      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
      File.usingTemporaryDirectory("dumpcdg") { tmpDir =>
        val opts = CdgDumpOptions(tmpDir.path.toString)
        new DumpCdg(opts).run(context)
        (tmpDir / "0-cdg.dot").exists shouldBe true
        (tmpDir / "1-cdg.dot").exists shouldBe true
        (tmpDir / "0-cdg.dot").size should not be 0
        (tmpDir / "1-cdg.dot").size should not be 0
      }
    }

  }

}
