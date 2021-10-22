package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.AstCreationPass
import io.shiftleft.codepropertygraph.Cpg

object CpgAstOnlyFixture {
  def apply(code: String, fileName: String = "file.c"): Cpg = {
    val cpg = Cpg.emptyCpg
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      val filenames = List(file.path.toAbsolutePath.toString)
      new AstCreationPass(filenames, cpg, None, Config()).createAndApply()
    }
    cpg
  }
}

trait CpgAstOnlyFixture
