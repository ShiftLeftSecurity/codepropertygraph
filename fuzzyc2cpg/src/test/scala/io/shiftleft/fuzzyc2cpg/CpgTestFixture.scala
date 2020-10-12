package io.shiftleft.fuzzyc2cpg

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.fuzzyc2cpg.passes.{AstCreationPass, CMetaDataPass, StubRemovalPass}
import io.shiftleft.passes.IntervalKeyPool
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.x2cpg.SourceFiles
import overflowdb.traversal.TraversalSource

case class CpgTestFixture(projectName: String) {

  val cpg: Cpg = Cpg.emptyCpg
  val dirName = String.format("fuzzyc2cpg/src/test/resources/testcode/%s", projectName)
  val keyPoolFile1 = new IntervalKeyPool(1001, 2000)
  val cfgKeyPool = new IntervalKeyPool(2001, 3000)
  val filenames = SourceFiles.determine(Set(dirName), Set(".c"))

  new CMetaDataPass(cpg).createAndApply()
  new AstCreationPass(filenames, cpg, keyPoolFile1).createAndApply()
  if (cpg.method.nonEmpty) {
    new CfgCreationPass(cpg, cfgKeyPool).createAndApply()
  }
  new StubRemovalPass(cpg).createAndApply()

  def traversalSource = TraversalSource(cpg.graph)

}
