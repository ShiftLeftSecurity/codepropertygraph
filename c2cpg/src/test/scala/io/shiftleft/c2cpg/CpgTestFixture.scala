package io.shiftleft.c2cpg

import io.shiftleft.c2cpg.passes.{AstCreationPass, StubRemovalPass}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.{CfgCreationPass, FileCreationPass}
import io.shiftleft.utils.ProjectRoot
import io.shiftleft.x2cpg.SourceFiles
import overflowdb.traversal.TraversalSource

case class CpgTestFixture(projectName: String) {

  val cpg: Cpg = Cpg.emptyCpg
  val dirName: String = ProjectRoot.relativise(s"c2cpg/src/test/resources/testcode/$projectName")
  val keyPool: IntervalKeyPool = new IntervalKeyPool(1001, 2000)
  val filenames: List[String] = SourceFiles.determine(Set(dirName), Set(".c"))

  new MetaDataPass(cpg, Languages.C).createAndApply()
  new AstCreationPass(filenames, cpg, keyPool).createAndApply()
  if (cpg.method.nonEmpty) {
    new CfgCreationPass(cpg).createAndApply()
  }
  new StubRemovalPass(cpg).createAndApply()
  new FileCreationPass(cpg).createAndApply()

  def traversalSource: TraversalSource = TraversalSource(cpg.graph)

}
