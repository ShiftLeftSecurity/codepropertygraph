package io.shiftleft.dataflowengineoss.layers.dataflows

import better.files.File
import io.shiftleft.dataflowengineoss.language.DataFlowCodeToCpgSuite
import io.shiftleft.semanticcpg.layers.LayerCreatorContext

class DumpPdgTests extends DataFlowCodeToCpgSuite {

  override val code =
    """
      |int foo() {}
      |int bar() {}
      |""".stripMargin

  "DumpPdg" should {

    "create two dot files for a CPG containing two methods" in {

      File.usingTemporaryDirectory("dumppdg") { tmpDir =>
        val opts = PdgDumpOptions(tmpDir.path.toString)
        implicit val s = semantics
        val layerContext = new LayerCreatorContext(cpg)
        new DumpPdg(opts).run(layerContext)
        (tmpDir / "0-pdg.dot").exists shouldBe true
        (tmpDir / "1-pdg.dot").exists shouldBe true
        (tmpDir / "0-pdg.dot").size should not be 0
        (tmpDir / "1-pdg.dot").size should not be 0
      }
    }

  }

}
