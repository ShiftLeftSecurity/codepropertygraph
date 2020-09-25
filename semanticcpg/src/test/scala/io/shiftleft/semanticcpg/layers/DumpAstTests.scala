package io.shiftleft.semanticcpg.layers

import better.files.File
import io.shiftleft.semanticcpg.testing.MockCpg
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DumpAstTests extends AnyWordSpec with Matchers {

  "DumpAst" should {

    "create two dot files for a CPG containing two methods" in {
      val cpg = MockCpg()
        .withMetaData()
        .withMethod("foo")
        .withMethod("bar")
        .cpg

      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
      File.usingTemporaryDirectory("dumpast") { tmpDir =>
        val opts = DumpOptions(tmpDir.path.toString)
        new DumpAst(opts).run(context)
        (tmpDir / "0-ast.dot").exists shouldBe true
        (tmpDir / "1-ast.dot").exists shouldBe true
        (tmpDir / "0-ast.dot").size should not be 0
        (tmpDir / "1-ast.dot").size should not be 0
      }
    }

  }

}
