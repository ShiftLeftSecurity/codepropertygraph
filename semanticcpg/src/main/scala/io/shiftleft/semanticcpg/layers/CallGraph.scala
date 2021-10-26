package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.passes.linking.calllinker.StaticCallLinker
import io.shiftleft.semanticcpg.passes.linking.linker.MethodRefLinker

import scala.annotation.nowarn

object CallGraph {
  val overlayName: String = "callgraph"
  val description: String = "Call graph layer"
  def defaultOpts = new LayerCreatorOptions()

  def passes(cpg: Cpg): Iterator[CpgPassBase] = Iterator(
    new MethodRefLinker(cpg),
    new StaticCallLinker(cpg),
  )

}

@nowarn
class CallGraph(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {
  override val overlayName: String = ControlFlow.overlayName
  override val description: String = ControlFlow.description
  override val dependsOn = List(TypeRelations.overlayName)

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    CallGraph.passes(cpg).zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }
}
