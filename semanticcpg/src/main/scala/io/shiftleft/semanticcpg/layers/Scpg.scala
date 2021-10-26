package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Languages, NodeTypes, PropertyNames}
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.CfgCreationPass

import scala.annotation.nowarn

object Scpg {
  val overlayName: String = "semanticcpg"
  val description: String = "linked code property graph (OSS)"

  def defaultOpts = new LayerCreatorOptions()
}

@nowarn
class Scpg(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {

  override val overlayName: String = Scpg.overlayName
  override val description: String = Scpg.description

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    val language = cpg.metaData.language.headOption
      .getOrElse(throw new Exception("Meta node missing."))

    cpg.graph.indexManager.createNodePropertyIndex(PropertyNames.FULL_NAME)

    val enhancementExecList = createEnhancementExecList(cpg, language)
    enhancementExecList.zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }

  private def createEnhancementExecList(cpg: Cpg, language: String): Iterator[CpgPassBase] = {

    def defaultPasses =
      Base.passes(cpg) ++ ControlFlow.passes(cpg) ++
        TypeRelations.passes(cpg) ++ CallGraph.passes(cpg)

    language match {
      case Languages.JAVASRC         => defaultPasses
      case Languages.C               => defaultPasses
      case Languages.JAVASCRIPT      => defaultPasses
      case Languages.FUZZY_TEST_LANG => defaultPasses
      case Languages.KOTLIN          => defaultPasses
      case Languages.NEWC            => defaultPasses
      case Languages.JAVA            => defaultPasses
      case Languages.LLVM            => defaultPasses.filterNot(_.isInstanceOf[CfgCreationPass])
      case Languages.GHIDRA          => defaultPasses.filterNot(_.isInstanceOf[CfgCreationPass])
      case _                         => defaultPasses
    }
  }

  override def probe(cpg: Cpg): Boolean = {
    cpg.graph.nodes(NodeTypes.METHOD_PARAMETER_OUT).hasNext
  }
}
