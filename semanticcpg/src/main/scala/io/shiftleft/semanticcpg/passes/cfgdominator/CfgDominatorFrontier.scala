package io.shiftleft.semanticcpg.passes.cfgdominator

import scala.collection.mutable

/**
  * Calculates the dominator frontier for a set of CFG nodes.
  * The returned multimap associates the frontier nodes to each CFG node.
  *
  * The used algorithm is from: "A Simple, Fast Dominance Algorithm" from
  * "Keith D. Cooper, Timothy J. Harvey, and Ken Kennedy".
  */
class CfgDominatorFrontier[NodeType](cfgAdapter: CfgAdapter[NodeType], domTreeAdapter: DomTreeAdapter[NodeType]) {

  private def doms(x: NodeType): Option[NodeType] = domTreeAdapter.immediateDominator(x)
  private def pred(x: NodeType): Seq[NodeType] = cfgAdapter.predecessors(x).iterator.to(Seq)
  private def onlyJoinNodes(x: NodeType): Option[(NodeType, Seq[NodeType])] =
    Option(pred(x)).filter(_.size > 1).map(p => (x, p))
  private def withIDom(x: NodeType, preds: Seq[NodeType]) =
    doms(x).map(i => (x, preds, i))

  def calculate(cfgNodes: Seq[NodeType]): mutable.MultiDict[NodeType, NodeType] = {
    val domFrontier = mutable.MultiDict.empty[NodeType, NodeType]

    for {
      cfgNode <- cfgNodes
      (nodeType, joinNodes) <- onlyJoinNodes(cfgNode)
      (joinNode, preds, joinNodeIDom) <- withIDom(nodeType, joinNodes)
    } preds.foreach { p =>
      var currentPred = Option(p)
      while (currentPred.isDefined && currentPred.get != joinNodeIDom) {
        domFrontier.addOne(currentPred.get, joinNode)
        currentPred = doms(currentPred.get)
      }
    }

    domFrontier
  }
}
