package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

class UsageAnalyzer(in: Map[nodes.StoredNode, Set[Definition]]) {

  private val allNodes = in.keys.toList

  val usedIncomingDefs: Map[StoredNode, Map[StoredNode, Set[Definition]]] = initUsedIncomingDefs()

  def initUsedIncomingDefs(): Map[StoredNode, Map[StoredNode, Set[Definition]]] = {
    allNodes.map { node =>
      node ->
        uses(node).map { use =>
          use -> in(node).filter { inElement =>
            sameDeclaration(use, inElement.node)
          }
        }.toMap
    }.toMap
  }

  def uses(node: nodes.StoredNode): Set[nodes.StoredNode] = {
    val n = node match {
      case ret: nodes.Return =>
        ret.astChildren.toSet
      case call: nodes.Call =>
        call.start.argument.toSet
      case _ => Set()
    }
    n.filterNot(_.isInstanceOf[nodes.FieldIdentifier]).map(_.asInstanceOf[nodes.StoredNode])
  }

  def sameDeclaration(node1: StoredNode, node2: StoredNode): Boolean = {
    if (node1.isInstanceOf[nodes.Call] || node2.isInstanceOf[nodes.Call]) {
      node1.isInstanceOf[nodes.Call] && node2.isInstanceOf[nodes.Call] &&
      node1.asInstanceOf[nodes.Call].code == node2.asInstanceOf[nodes.Call].code
    } else {
      (declaration(node1) == declaration(node2)) && declaration(node1).isDefined
    }
  }

  private def declaration(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case param: nodes.MethodParameterIn => Some(param)
      case _: nodes.Identifier            => node._refOut().nextOption
      case _                              => None
    }
  }

}
