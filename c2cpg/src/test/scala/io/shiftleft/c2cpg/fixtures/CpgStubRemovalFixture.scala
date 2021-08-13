package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.CfgCreationPass

object CpgStubRemovalFixture {
  def apply(code: String, fileName: String = "test.c")(f: Cpg => Unit): Unit = {
    val cpg = Cpg.emptyCpg
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      val keyPool = new IntervalKeyPool(1001, 2000)
      val filenames = List(file.path.toAbsolutePath.toString)
      new AstCreationPass(filenames, cpg, keyPool, Config()).createAndApply()
      new CfgCreationPass(cpg).createAndApply()
      new StubRemovalPass(cpg).createAndApply()
    }
    f(cpg)
  }

}

trait CpgStubRemovalFixture
