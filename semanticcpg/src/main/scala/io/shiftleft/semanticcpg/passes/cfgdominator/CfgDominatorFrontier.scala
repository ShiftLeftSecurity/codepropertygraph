package io.shiftleft.semanticcpg.passes.cfgdominator

import scala.collection.mutable

class CfgDominatorFrontier[Node](cfgAdapter: CfgAdapter[Node], domTreeAdapter: DomTreeAdapter[Node]) {

  /**
    * Calculates a the dominator frontier for a set of CFG nodes.
    * The returned multimap associates the frontier nodes to each CFG node.
    *
    * The used algorithm is from: "A Simple, Fast Dominance Algorithm" from
    * "Keith D. Cooper, Timothy J. Harvey, and Ken Kennedy".
    *
    * n.b. there's a deprecation warning about MultiMap, but we can't use the replacement MultiDict from
    * scala-collection-contrib because it has a broken jar :(
    * https://github.com/scala/scala-collection-contrib/issues/51
    */
  def calculate(allCfgNodes: Seq[Node]): mutable.MultiMap[Node, Node] = {
    val domFrontier = new mutable.HashMap[Node, mutable.Set[Node]] with mutable.MultiMap[Node, Node]

    allCfgNodes.foreach { joinCandiate =>
      val predecessors = cfgAdapter.predecessors(joinCandiate).iterator.to(Seq)

      if (predecessors.size > 1) {
        val joinNode = joinCandiate

        domTreeAdapter.immediateDominator(joinNode) match {
          case Some(immediateJoinNodeDom) =>
            predecessors.foreach { predecessor =>
              var currentPred = Option(predecessor)

              while (currentPred.isDefined && currentPred.get != immediateJoinNodeDom) {
                domFrontier.addBinding(currentPred.get, joinNode)
                currentPred = domTreeAdapter.immediateDominator(currentPred.get)
              }
            }
          case _ =>
        }
      }
    }

    domFrontier
  }
}
