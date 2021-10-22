package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.passes.AstCreationPass
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.semanticcpg.layers.{LayerCreatorContext, Scpg}
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass

object CpgTypeNodeFixture {
  def apply(code: String, fileName: String = "test.c")(f: Cpg => Unit): Unit = {
    val cpg = Cpg.emptyCpg
    Global.usedTypes.clear()
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      new MetaDataPass(cpg, Languages.NEWC).createAndApply()
      val filenames = List(file.path.toAbsolutePath.toString)
      val astCreationPass = new AstCreationPass(filenames, cpg, None, Config())
      astCreationPass.createAndApply()
      new TypeNodePass(astCreationPass.usedTypes(), cpg).createAndApply()

      val context = new LayerCreatorContext(cpg)
      new Scpg().run(context)
    }
    f(cpg)
  }
}

trait CpgTypeNodeFixture
