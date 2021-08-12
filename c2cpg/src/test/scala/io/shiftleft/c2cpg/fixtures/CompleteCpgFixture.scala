package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.typenodes.TypeNodePass

object CompleteCpgFixture {
  def apply(code: String, fileName: String = "test.cpp")(f: Cpg => Unit): Unit = {
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      val cpg = Cpg.emptyCpg
      val keyPool = new IntervalKeyPool(1001, 2000)
      val filenames = List(file.path.toAbsolutePath.toString)
      val astCreationPass = new AstCreationPass(filenames, cpg, keyPool, Config())
      astCreationPass.createAndApply()
      new CfgCreationPass(cpg).createAndApply()
      new StubRemovalPass(cpg).createAndApply()
      new TypeNodePass(astCreationPass.usedTypes(), cpg).createAndApply()
      f(cpg)
    }
  }
}

trait CompleteCpgFixture
