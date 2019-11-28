package io.shiftleft.semanticcpg.passes.cfgdominator

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.passes.{CpgPass,  DiffGraph, ParallelIteratorExecutor}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.ExpandTo

/**
  * This pass has no prerequisites.
  */
class CfgDominatorPass(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    val methodsIterator = cpg.method.toIterator()
    new ParallelIteratorExecutor(methodsIterator).map(perMethod)
  }

  private def perMethod(method: Vertex): DiffGraph = {
    val cfgAdapter = new CpgCfgAdapter()
    val dominatorCalculator = new CfgDominator(cfgAdapter)

    val reverseCfgAdapter = new ReverseCpgCfgAdapter()
    val postDominatorCalculator = new CfgDominator(reverseCfgAdapter)

    val dstGraph = DiffGraph.newBuilder

    val cfgNodeToImmediateDominator = dominatorCalculator.calculate(method)
    addDomTreeEdges(dstGraph, cfgNodeToImmediateDominator)

    val cfgNodeToPostImmediateDominator =
      postDominatorCalculator.calculate(ExpandTo.methodToFormalReturn(method))
    addPostDomTreeEdges(dstGraph, cfgNodeToPostImmediateDominator)

    dstGraph.build()
  }

  private def addDomTreeEdges(dstGraph: DiffGraph.Builder, cfgNodeToImmediateDominator: Map[Vertex, Vertex]): Unit = {
    // TODO do not iterate over potential hash map to ensure same interation order for
    // edge creation.
    cfgNodeToImmediateDominator.foreach {
      case (node, immediateDominator) =>
        dstGraph.addEdgeInOriginal(immediateDominator.asInstanceOf[StoredNode],
                                   node.asInstanceOf[StoredNode],
                                   EdgeTypes.DOMINATE)
    }
  }

  private def addPostDomTreeEdges(dstGraph: DiffGraph.Builder, cfgNodeToPostImmediateDominator: Map[Vertex, Vertex]): Unit = {
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
