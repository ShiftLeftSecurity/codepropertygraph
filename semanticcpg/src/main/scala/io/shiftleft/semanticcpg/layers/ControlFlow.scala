package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.passes.CfgCreationPass
import io.shiftleft.semanticcpg.passes.cfgdominator.CfgDominatorPass
import io.shiftleft.semanticcpg.passes.codepencegraph.CdgPass

import scala.annotation.nowarn

object ControlFlow {
  val overlayName: String = "controlflow"
  val description: String = "Control flow layer (including dominators and CDG edges)"
  def defaultOpts = new LayerCreatorOptions()

  def passes(cpg: Cpg): Iterator[CpgPassBase] = Iterator(
    new CfgCreationPass(cpg),
    new CfgDominatorPass(cpg),
    new CdgPass(cpg),
  )

}

@nowarn
class ControlFlow(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {
  override val overlayName: String = ControlFlow.overlayName
  override val description: String = ControlFlow.description
  override val dependsOn = List(Base.overlayName)

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean): Unit = {
    val cpg = context.cpg
    ControlFlow.passes(cpg).zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }
}
