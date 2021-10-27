package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.AstCreationPass
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass

object CompleteCpgFixture {
  def apply(code: String, fileName: String = "test.cpp")(f: Cpg => Unit): Unit = {
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      val cpg = Cpg.emptyCpg
      new MetaDataPass(cpg, Languages.NEWC).createAndApply()
      val astCreationPass = new AstCreationPass(cpg, None, Config(inputPaths = Set(dir.path.toString)))
      astCreationPass.createAndApply()
      new CfgCreationPass(cpg).createAndApply()
      new TypeNodePass(astCreationPass.usedTypes(), cpg).createAndApply()
      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
      f(cpg)
    }
  }
}

trait CompleteCpgFixture
