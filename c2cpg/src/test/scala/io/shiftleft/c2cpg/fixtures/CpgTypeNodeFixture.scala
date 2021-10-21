package io.shiftleft.c2cpg.fixtures

import better.files.File
import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.c2cpg.passes.AstCreationPass
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.passes.languagespecific.fuzzyc.MethodStubCreator
import io.shiftleft.semanticcpg.passes.linking.linker.{Linker, TypeLinker}
import io.shiftleft.semanticcpg.passes.typenodes.{TypeDeclStubCreator, TypeNodePass}

object CpgTypeNodeFixture {
  def apply(code: String, fileName: String = "test.c")(f: Cpg => Unit): Unit = {
    val cpg = Cpg.emptyCpg
    Global.usedTypes.clear()
    File.usingTemporaryDirectory("c2cpgtest") { dir =>
      val file = dir / fileName
      file.write(code)

      val keyPool = new IntervalKeyPool(1001, 2000)
      val filenames = List(file.path.toAbsolutePath.toString)
      val astCreationPass = new AstCreationPass(filenames, cpg, keyPool, Config())
      astCreationPass.createAndApply()
      new TypeNodePass(astCreationPass.usedTypes(), cpg).createAndApply()
      new TypeDeclStubCreator(cpg).createAndApply()
      new MethodStubCreator(cpg).createAndApply()
      new TypeLinker(cpg).createAndApply()
      new Linker(cpg).createAndApply()
    }
    f(cpg)
  }
}

trait CpgTypeNodeFixture
