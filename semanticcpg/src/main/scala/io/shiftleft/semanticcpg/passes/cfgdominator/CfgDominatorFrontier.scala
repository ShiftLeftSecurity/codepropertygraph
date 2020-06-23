package io.shiftleft.semanticcpg.passes.cfgdominator

import scala.collection.mutable

class CfgDominatorFrontier[N](cfgAdapter: CfgAdapter[N], domTreeAdapter: DomTreeAdapter[N]) {

  /**
    * Calculates a the dominator frontier for a set of CFG nodes.
    * The returned multimap associates the frontier nodes to each CFG node.
    *
    * The used algorithm is from: "A Simple, Fast Dominance Algorithm" from
    * "Keith D. Cooper, Timothy J. Harvey, and Ken Kennedy".
    */
  def calculate(allCfgNodes: Seq[N]): mutable.MultiDict[N, N] = {
    val domFrontier = mutable.MultiDict.empty[N, N]

    allCfgNodes.foreach { joinCandiate =>
      val predecessors = cfgAdapter.predecessors(joinCandiate).iterator.to(Seq)

      if (predecessors.size > 1) {
        val joinNode = joinCandiate

        domTreeAdapter.immediateDominator(joinNode) match {
          case Some(immediateJoinNodeDom) =>
            predecessors.foreach { predecessor =>
              var currentPred = Option(predecessor)

              while (currentPred.isDefined && currentPred.get != immediateJoinNodeDom) {
                domFrontier.addOne(currentPred.get, joinNode)
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
