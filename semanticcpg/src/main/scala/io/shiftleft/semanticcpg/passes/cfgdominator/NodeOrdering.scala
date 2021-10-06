package io.shiftleft.semanticcpg.passes.cfgdominator

import scala.collection.mutable

object NodeOrdering {

  /**
    * For a given CFG with the entry node `cfgEntry` and an expansion function `expand`,
    * return a map that associates each node with an index such that nodes are numbered
    * in post order.
    * */
  def createPostOrderNumbering[NodeType](cfgEntry: NodeType,
                                         expand: NodeType => Iterator[NodeType]): Map[NodeType, Int] = {
    var stack = (cfgEntry, expand(cfgEntry)) :: Nil
    val visited = mutable.Set.empty[NodeType]
    val numbering = mutable.Map.empty[NodeType, Int]
    var nextNumber = 0

    while (stack.nonEmpty) {
      val (node, successors) = stack.head
      visited += node

      if (successors.hasNext) {
        val successor = successors.next()
        if (!visited.contains(successor)) {
          stack = (successor, expand(successor)) :: stack
        }
      } else {
        stack = stack.tail
        numbering += (node -> nextNumber)
        nextNumber += 1
      }
    }
    numbering.toMap
  }

  def reverseNodeList[NodeType](ordering: List[(NodeType, Int)]): List[NodeType] = {
    ordering
      .sortBy { case (_, index) => -index }
      .map { case (node, _) => node }
  }

}
