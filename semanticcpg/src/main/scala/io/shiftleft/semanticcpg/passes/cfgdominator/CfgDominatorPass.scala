package io.shiftleft.semanticcpg.passes.cfgdominator

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{Method, StoredNode}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

/**
  * This pass has no prerequisites.
  */
class CfgDominatorPass(cpg: Cpg) extends ParallelCpgPass[Method](cpg) {

  override def partIterator: Iterator[Method] = cpg.method.iterator

  override def runOnPart(method: Method): Iterator[DiffGraph] = {
    val cfgAdapter = new CpgCfgAdapter()
    val dominatorCalculator = new CfgDominator(cfgAdapter)

    val reverseCfgAdapter = new ReverseCpgCfgAdapter()
    val postDominatorCalculator = new CfgDominator(reverseCfgAdapter)

    val dstGraph = DiffGraph.newBuilder

    val cfgNodeToImmediateDominator = dominatorCalculator.calculate(method)
    addDomTreeEdges(dstGraph, cfgNodeToImmediateDominator)

    val cfgNodeToPostImmediateDominator = postDominatorCalculator.calculate(method.methodReturn)
    addPostDomTreeEdges(dstGraph, cfgNodeToPostImmediateDominator)

    Iterator(dstGraph.build())
  }

  private def addDomTreeEdges(dstGraph: DiffGraph.Builder,
                              cfgNodeToImmediateDominator: Map[StoredNode, StoredNode]): Unit = {
    // TODO do not iterate over potential hash map to ensure same iteration order for
    // edge creation.
    cfgNodeToImmediateDominator.foreach {
      case (node, immediateDominator) =>
        dstGraph.addEdgeInOriginal(immediateDominator, node, EdgeTypes.DOMINATE)
    }
  }

  private def addPostDomTreeEdges(dstGraph: DiffGraph.Builder,
                                  cfgNodeToPostImmediateDominator: Map[StoredNode, StoredNode]): Unit = {
    // TODO do not iterate over potential hash map to ensure same iteration order for
    // edge creation.
    cfgNodeToPostImmediateDominator.foreach {
      case (node, immediatePostDominator) =>
        dstGraph.addEdgeInOriginal(immediatePostDominator, node, EdgeTypes.POST_DOMINATE)
    }
  }
}
