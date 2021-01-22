package io.shiftleft.semanticcpg.passes.cfgdominator

import scala.collection.mutable

class CfgDominator[NodeType](adapter: CfgAdapter[NodeType]) {

  /**
    * Calculates the immediate dominators of all CFG nodes reachable from cfgEntry.
    * Since the cfgEntry does not have an immediate dominator, it has no entry in the
    * return map.
    *
    * The used algorithm is from: "A Simple, Fast Dominance Algorithm" from
    * "Keith D. Cooper, Timothy J. Harvey, and Ken Kennedy".
    */
  def calculate(cfgEntry: NodeType): mutable.Map[NodeType, NodeType] = {
    val UNDEFINED = -1
    val postOrderNumbering = createPostOrderNumbering(cfgEntry)
    val indexOf = postOrderNumbering.withDefaultValue(UNDEFINED) // Index of each node into dominators array.
    // We use withDefault because unreachable/dead
    // code nodes are not numbered but may be touched
    // as predecessors of reachable nodes.
    val nodesInReversePostOrder = postOrderNumbering.toList // Does not contain entry.
      .sortBy { case (node, index) => -index }
      .map { case (node, index) => node }
      .filter { _ != cfgEntry }
    val dominators = Array.fill(indexOf.size)(UNDEFINED)

    // Used in places where index might be UNDEFINED.
    def saveDominators(index: Int): Int = {
      if (index != UNDEFINED) {
        dominators(index)
      } else {
        UNDEFINED
      }
    }

    dominators(indexOf(cfgEntry)) = indexOf(cfgEntry)

    var changed = true
    while (changed) {
      changed = false
      nodesInReversePostOrder.foreach { node =>
        val firstNotUndefinedPred = adapter
          .predecessors(node)
          .iterator
          .find { predecessor =>
            saveDominators(indexOf(predecessor)) != UNDEFINED
          }
          .get

        var newImmediateDominator = indexOf(firstNotUndefinedPred)
        adapter.predecessors(node).iterator.foreach { predecessor =>
          val predecessorIndex = indexOf(predecessor)
          if (saveDominators(predecessorIndex) != UNDEFINED) {
            newImmediateDominator = intersect(dominators, predecessorIndex, newImmediateDominator)
          }
        }

        val nodeIndex = indexOf(node)
        if (dominators(nodeIndex) != newImmediateDominator) {
          dominators(nodeIndex) = newImmediateDominator
          changed = true
        }
      }

    }

    val postOrderNumberingToNode = postOrderNumbering.map { case (node, index) => (index, node) }

    postOrderNumbering.collect {
      case (node, index) if node != cfgEntry =>
        val immediateDominatorIndex = dominators(index)
        (node, postOrderNumberingToNode(immediateDominatorIndex))
    }
  }

  private def intersect(dominators: Array[Int], immediateDomIndex1: Int, immediateDomIndex2: Int): Int = {
    var finger1 = immediateDomIndex1
    var finger2 = immediateDomIndex2

    while (finger1 != finger2) {
      while (finger1 < finger2) {
        finger1 = dominators(finger1)
      }
      while (finger2 < finger1) {
        finger2 = dominators(finger2)
      }
    }
    finger1
  }

  private def createPostOrderNumbering(cfgEntry: NodeType): mutable.Map[NodeType, Int] = {
    var stack = (cfgEntry, adapter.successors(cfgEntry).iterator) :: Nil
    val visited = mutable.Set.empty[NodeType]
    val numbering = mutable.Map.empty[NodeType, Int]
    var nextNumber = 0

    while (stack.nonEmpty) {
      val (node, successorIt) = stack.head

      visited += node

      if (successorIt.hasNext) {
        val successor = successorIt.next()
        if (!visited.contains(successor)) {
          stack = (successor, adapter.successors(successor).iterator) :: stack
        }
      } else {
        stack = stack.tail
        numbering += (node -> nextNumber)
        nextNumber += 1
      }
    }

    numbering
  }
}
