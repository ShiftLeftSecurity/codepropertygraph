package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.fuzzyc2cpg.passes.{AstCreationPass, CMetaDataPass, StubRemovalPass}
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.{CfgCreationPass, FileCreationPass}
import io.shiftleft.utils.ProjectRoot
import io.shiftleft.x2cpg.SourceFiles
import overflowdb.traversal.TraversalSource

case class CpgTestFixture(projectName: String) {

  val cpg: Cpg = Cpg.emptyCpg
  val dirName = ProjectRoot.relativise(s"fuzzyc2cpg/src/test/resources/testcode/$projectName")
  val keyPoolFile1 = new IntervalKeyPool(1001, 2000)
  val filenames = SourceFiles.determine(Set(dirName), Set(".c"))

  new CMetaDataPass(cpg).createAndApply()
  new AstCreationPass(filenames, cpg, keyPoolFile1).createAndApply()
  if (cpg.method.nonEmpty) {
    new CfgCreationPass(cpg).createAndApply()
  }
  new StubRemovalPass(cpg).createAndApply()
  new FileCreationPass(cpg).createAndApply()

  def traversalSource = TraversalSource(cpg.graph)

}
