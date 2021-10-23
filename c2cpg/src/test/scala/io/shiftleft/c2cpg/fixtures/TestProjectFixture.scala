package io.shiftleft.c2cpg.fixtures

import io.shiftleft.c2cpg.C2Cpg.Config
import io.shiftleft.c2cpg.passes.AstCreationPass
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.semanticcpg.passes.metadata.MetaDataPass
import io.shiftleft.semanticcpg.passes.{CfgCreationPass, FileCreationPass}
import io.shiftleft.utils.ProjectRoot
import io.shiftleft.x2cpg.SourceFiles
import overflowdb.traversal.TraversalSource

case class TestProjectFixture(projectName: String) {

  private val cpg = Cpg.emptyCpg

  private val dirName: String = ProjectRoot.relativise(s"c2cpg/src/test/resources/testcode/$projectName")
  private val filenames: List[String] = SourceFiles.determine(Set(dirName), Set(".c"))

  new MetaDataPass(cpg, Languages.C).createAndApply()
  new AstCreationPass(filenames, cpg, None, Config()).createAndApply()
  new CfgCreationPass(cpg).createAndApply()
  new FileCreationPass(cpg).createAndApply()

  def traversalSource: TraversalSource = TraversalSource(cpg.graph)

}
