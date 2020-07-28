package io.shiftleft.semanticcpg.passes.cfgdominator

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._
import overflowdb.Node

/**
  * This pass has no prerequisites.
  */
class CfgDominatorPass(cpg: Cpg) extends ParallelCpgPass[nodes.Method](cpg) {

  override def partIterator: Iterator[nodes.Method] = cpg.method.iterator

  override def runOnPart(method: nodes.Method): Iterator[DiffGraph] = {
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

  private def addDomTreeEdges(dstGraph: DiffGraph.Builder, cfgNodeToImmediateDominator: Map[Node, Node]): Unit = {
    // TODO do not iterate over potential hash map to ensure same interation order for
    // edge creation.
    cfgNodeToImmediateDominator.foreach {
      case (node, immediateDominator) =>
        dstGraph.addEdgeInOriginal(immediateDominator.asInstanceOf[StoredNode],
                                   node.asInstanceOf[StoredNode],
                                   EdgeTypes.DOMINATE)
    }
  }

  private def addPostDomTreeEdges(dstGraph: DiffGraph.Builder,
                                  cfgNodeToPostImmediateDominator: Map[Node, Node]): Unit = {
    // TODO do not iterate over potential hash map to ensure same interation order for
    // edge creation.
    cfgNodeToPostImmediateDominator.foreach {
      case (node, immediatePostDominator) =>
        dstGraph.addEdgeInOriginal(immediatePostDominator.asInstanceOf[StoredNode],
                                   node.asInstanceOf[StoredNode],
                                   EdgeTypes.POST_DOMINATE)
    }
  }
}
