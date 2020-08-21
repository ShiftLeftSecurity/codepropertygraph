package io.shiftleft.dataflowengineoss.passes.reachingdef

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.semanticcpg.language._

class UsageAnalyzer(in: Map[nodes.StoredNode, Set[Definition]]) {

  private val allNodes = in.keys.toList

  val usedIncomingDefs: Map[StoredNode, Map[StoredNode, Set[Definition]]] = initUsedIncomingDefs()

  def initUsedIncomingDefs(): Map[StoredNode, Map[StoredNode, Set[Definition]]] = {
    allNodes.map { node =>
      node ->
        uses(node).map { use =>
          use -> in(node).filter { inElement =>
            declaration(use) == declaration(inElement.node)
          }
        }.toMap
    }.toMap
  }

  def uses(node: nodes.StoredNode): Set[nodes.StoredNode] = {
    val n = node match {
      case ret: nodes.Return =>
        ret.astChildren.map(_.asInstanceOf[nodes.StoredNode]).toSet()
      case call: nodes.Call =>
        call.start.argument.toSet.map(_.asInstanceOf[nodes.StoredNode])
      case _ => Set()
    }
    n.filterNot(_.isInstanceOf[nodes.FieldIdentifier]).map(_.asInstanceOf[nodes.StoredNode])
  }

  private def declaration(node: nodes.StoredNode): Option[nodes.StoredNode] = {
    node match {
      case param: nodes.MethodParameterIn => Some(param)
      case _: nodes.Identifier            => node._refOut().nextOption
      case call: nodes.Call               =>
        // We map to the first call that has the exact same code. We use
        // this as a declaration
        call.method.start.call.codeExact(call.code).headOption
      case _ => None
    }
  }

}
