package io.shiftleft.dataflowengine.layers.dataflows

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.dataflowengine.passes.propagateedges.PropagateEdgePass
import io.shiftleft.dataflowengine.passes.reachingdef.ReachingDefPass
import io.shiftleft.dataflowengine.semanticsloader.Semantics

class DataFlowRunner(semantics: Semantics) {

  def run(cpg: Cpg, serializedCpg: SerializedCpg): Unit = run(cpg, Some(serializedCpg))

  def run(cpg: Cpg, serializedCpg: Option[SerializedCpg] = None): Unit = {
    val enhancementExecList = List(new PropagateEdgePass(cpg, semantics), new ReachingDefPass(cpg))

    serializedCpg match {
      case Some(serializedCpg) => enhancementExecList.foreach(_.createApplySerializeAndStore(serializedCpg))
      case None =>                enhancementExecList.foreach(_.createAndApply)
    }
      enhancementExecList.foreach(_.createApplySerializeAndStore(serializedCpg.get))
    } else {
      enhancementExecList.foreach(_.createAndApply())
    }
  }

}
