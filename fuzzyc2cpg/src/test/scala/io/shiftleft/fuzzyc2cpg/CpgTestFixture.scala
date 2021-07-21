package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.fuzzyc2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.passes.{CpgPassRunner, IntervalKeyPool}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.{CfgCreationPass, FileCreationPass}
import io.shiftleft.utils.ProjectRoot
import io.shiftleft.x2cpg.SourceFiles
import overflowdb.traversal.TraversalSource

case class CpgTestFixture(projectName: String) {

  val cpg: Cpg = Cpg.emptyCpg
  val dirName = ProjectRoot.relativise(s"fuzzyc2cpg/src/test/resources/testcode/$projectName")
  val keyPoolFile1 = new IntervalKeyPool(1001, 2000)
  val filenames = SourceFiles.determine(Set(dirName), Set(".c"))

  val passRunner = new CpgPassRunner(cpg, outputDir = None, inverse = false)
  passRunner.addPass(new MetaDataPass(cpg, Languages.C))
  passRunner.addPass(new AstCreationPass(filenames, cpg, keyPoolFile1))
  passRunner.addPass(new CfgCreationPass(cpg))
  passRunner.addPass(new StubRemovalPass(cpg))
  passRunner.addPass(new FileCreationPass(cpg))
  passRunner.run()

  def traversalSource = TraversalSource(cpg.graph)

}
