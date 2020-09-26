package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DumpCfgTests extends AnyWordSpec with Matchers {

  "DumpCfg" should {

    "create two dot files for a CPG containing two methods" in {
      val cpg = MockCpg()
        .withMetaData()
        .withMethod("foo")
        .withMethod("bar")
        .cpg

      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
      File.usingTemporaryDirectory("dumpcfg") { tmpDir =>
        val opts = CfgDumpOptions(tmpDir.path.toString)
        new DumpCfg(opts).run(context)
        (tmpDir / "0-cfg.dot").exists shouldBe true
        (tmpDir / "1-cfg.dot").exists shouldBe true
        (tmpDir / "0-cfg.dot").size should not be 0
        (tmpDir / "1-cfg.dot").size should not be 0
      }
    }

  }

}
