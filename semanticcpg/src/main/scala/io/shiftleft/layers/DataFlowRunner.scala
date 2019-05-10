package io.shiftleft.layers

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.CpgPassRunner
import io.shiftleft.passes.propagateedges.PropagateEdgePass
import io.shiftleft.passes.reachingdef.ReachingDefPass
import io.shiftleft.semanticsloader.Semantics

class DataFlowRunner(semantics: Semantics) {

  def run(cpg: Cpg, serializedCpg: SerializedCpg): Unit = {
    val graph = cpg.graph
    val runner = new CpgPassRunner(serializedCpg)
    val enhancementExecList = List(new PropagateEdgePass(graph, semantics), new ReachingDefPass(graph))

    enhancementExecList.foreach(runner.createStoreAndApplyOverlay(_))
  }

}
