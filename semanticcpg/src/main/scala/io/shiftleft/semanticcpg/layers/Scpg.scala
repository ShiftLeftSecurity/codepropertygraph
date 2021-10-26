package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.generated.PropertyNames

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
    cpg.graph.indexManager.createNodePropertyIndex(PropertyNames.FULL_NAME)
    val passes = Base.passes(cpg) ++ ControlFlow.passes(cpg) ++
      TypeRelations.passes(cpg) ++ CallGraph.passes(cpg)

    passes.zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }

}
