package io.shiftleft.semanticcpg.layers

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.Languages
import io.shiftleft.passes.CpgPassBase
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.controlflow.CfgCreationPass
import io.shiftleft.semanticcpg.passes.controlflow.cfgdominator.CfgDominatorPass
import io.shiftleft.semanticcpg.passes.controlflow.codepencegraph.CdgPass

import java.util.concurrent.Executor
import scala.annotation.nowarn

object ControlFlow {
  val overlayName: String = "controlflow"
  val description: String = "Control flow layer (including dominators and CDG edges)"
  def defaultOpts = new LayerCreatorOptions()

  def passes(cpg: Cpg): Iterator[CpgPassBase] = {
    val cfgCreationPass = cpg.metaData.language.lastOption match {
      case Some(Languages.GHIDRA) => Iterator[CpgPassBase]()
      case Some(Languages.LLVM)   => Iterator[CpgPassBase]()
      case _                      => Iterator[CpgPassBase](new CfgCreationPass(cpg))
    }
    cfgCreationPass ++ Iterator(
      new CfgDominatorPass(cpg),
      new CdgPass(cpg),
    )
  }

}

@nowarn
class ControlFlow(optionsUnused: LayerCreatorOptions = null) extends LayerCreator {
  override val overlayName: String = ControlFlow.overlayName
  override val description: String = ControlFlow.description
  override val dependsOn = List(Base.overlayName)

  override def create(context: LayerCreatorContext, storeUndoInfo: Boolean)(implicit executor: Executor): Unit = {
    val cpg = context.cpg
    ControlFlow.passes(cpg).zipWithIndex.foreach {
      case (pass, index) =>
        runPass(pass, context, storeUndoInfo, index)
    }
  }
}
