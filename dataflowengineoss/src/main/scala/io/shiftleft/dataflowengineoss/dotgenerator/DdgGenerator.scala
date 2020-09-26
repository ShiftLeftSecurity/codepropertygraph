package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, nodes}
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.DotSerializer.{Edge, Graph}
import overflowdb.Node
import overflowdb.traversal._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._

class DdgGenerator {

  val edgeType = "DDG"

  def generate(methodNode: nodes.Method)(implicit semantics: Semantics): Graph = {
    val entryNode = methodNode
    val paramNodes = methodNode.parameter.l
    val allOtherNodes = methodNode.start.cfgNode.l
    val exitNode = methodNode.methodReturn
    val allNodes: List[nodes.StoredNode] = List(entryNode, exitNode) ++ paramNodes ++ allOtherNodes
    val visibleNodes = allNodes.filter(shouldBeDisplayed)

    val edges = visibleNodes.map { dstNode =>
      inEdgesToDisplay(dstNode)
    }

    val allIdsReferencedByEdges = edges.flatten.flatMap { edge: Edge =>
      Set(edge.src.id, edge.dst.id)
    }

    val ddgNodes = visibleNodes
      .filter(node => allIdsReferencedByEdges.contains(node.id))
      .map(surroundingCall)
    val ddgEdges = edges.flatten
      .map { e: Edge =>
        e.copy(src = surroundingCall(e.src), dst = surroundingCall(e.dst))
      }
      .filter(e => e.src != e.dst)
      .dedup
      .l
    Graph(ddgNodes, ddgEdges)
  }

  private def surroundingCall(node: nodes.StoredNode): nodes.StoredNode = {
    node match {
      case arg: nodes.Expression => arg.start.inCall.headOption.getOrElse(node)
      case _                     => node
    }
  }

  private def shouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

  private def inEdgesToDisplay(dstNode: nodes.StoredNode, visited: List[nodes.StoredNode] = List())(
      implicit semantics: Semantics): List[Edge] = {
    if (visited.contains(dstNode)) {
      List()
    } else {
      val parents = expand(dstNode)
      val (visible, invisible) = parents.partition(x => shouldBeDisplayed(x.src))
      visible.toList ++ invisible.toList.flatMap { n =>
        inEdgesToDisplay(n.src, visited ++ List(dstNode)).map(y => Edge(y.src, dstNode, edgeType = edgeType))
      }
    }
  }

  private def expand(v: nodes.StoredNode)(implicit semantics: Semantics): Iterator[Edge] = {

    val allInEdges = v
      .inE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE), edgeType))

    v match {
      case trackingPoint: nodes.TrackingPoint =>
        trackingPoint.ddgInPathElem
          .map(x => Edge(x.node.asInstanceOf[nodes.StoredNode], v, x.outEdgeLabel, edgeType))
          .iterator ++ allInEdges.filter(_.src.isInstanceOf[nodes.Method]).iterator
      case _ =>
        allInEdges.iterator
    }

  }

}
