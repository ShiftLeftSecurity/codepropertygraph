package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language._

/**
  * Upon calculating reaching definitions, we find ourselves with
  * a set of incoming definitions `in(n)` for each node `n` of the
  * flow graph. This component determines those of the incoming
  * definitions that are relevant as the value they define is
  * actually used by `n`.
  * */
class UsageAnalyzer(in: Map[nodes.StoredNode, Set[Definition]]) {

  private val allNodes = in.keys.toList
  val usedIncomingDefs: Map[StoredNode, Map[StoredNode, Set[Definition]]] = initUsedIncomingDefs()

  def initUsedIncomingDefs(): Map[StoredNode, Map[StoredNode, Set[Definition]]] = {
    allNodes.map { node =>
      node -> usedIncomingDefsForNode(node)
    }.toMap
  }

  private def usedIncomingDefsForNode(node: nodes.StoredNode): Map[nodes.StoredNode, Set[Definition]] = {
    uses(node).map { use =>
      use -> in(node).filter { inElement =>
        sameVariable(use, inElement.node)
      }
    }.toMap
  }

  def uses(node: nodes.StoredNode): Set[nodes.Expression] = {
    val n: Set[nodes.Expression] = node match {
      case ret: nodes.Return =>
        ret.astChildren.collect { case x: nodes.Expression => x }.toSet
      case call: nodes.Call =>
        call.argument.toSet
      case _ => Set()
    }
    n.filterNot(_.isInstanceOf[nodes.FieldIdentifier])
  }

  /**
    * Compares arguments of calls with incoming definitions
    * to see if they refer to the same variable
    * */
  def sameVariable(use: nodes.Expression, incoming: StoredNode): Boolean = {
    incoming match {
      case param: nodes.MethodParameterIn =>
        use.code == param.name
      case call: nodes.Call =>
        use.code == call.code
      case identifier: nodes.Identifier => use.code == identifier.code
      case _                            => false
    }
  }

}
