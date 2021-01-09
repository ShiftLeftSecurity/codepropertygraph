package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, Operators, nodes}
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.DotSerializer.{Edge, Graph}
import overflowdb.Node
import io.shiftleft.semanticcpg.language._
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.semanticcpg.utils.MemberAccess.isGenericMemberAccessName
import overflowdb.traversal.jIteratortoTraversal

import scala.collection.mutable

class DdgGenerator {

  val edgeType = "DDG"
  private val edgeCache = mutable.Map[nodes.StoredNode, List[Edge]]()

  def generate(methodNode: nodes.Method)(implicit semantics: Semantics): Graph = {
    val entryNode = methodNode
    val paramNodes = methodNode.parameter.l
    val allOtherNodes = methodNode.cfgNode.l
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
      .filterNot(node => node.isInstanceOf[nodes.Call] && isGenericMemberAccessName(node.asInstanceOf[nodes.Call].name))

    val ddgEdges = edges.flatten
      .map { e: Edge =>
        e.copy(src = surroundingCall(e.src), dst = surroundingCall(e.dst))
      }
      .filter(e => e.src != e.dst)
      .filterNot(e => e.dst.isInstanceOf[nodes.Call] && isGenericMemberAccessName(e.dst.asInstanceOf[nodes.Call].name))
      .filterNot(e => e.src.isInstanceOf[nodes.Call] && isGenericMemberAccessName(e.src.asInstanceOf[nodes.Call].name))
      .distinct

    edgeCache.clear
    Graph(ddgNodes, ddgEdges)
  }

  private def surroundingCall(node: nodes.StoredNode): nodes.StoredNode = {
    node match {
      case arg: nodes.Expression => arg.inCall.headOption.getOrElse(node)
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

    if (edgeCache.contains(dstNode)) {
      return edgeCache(dstNode)
    }

    if (visited.contains(dstNode)) {
      List()
    } else {
      val parents = expand(dstNode)
      val (visible, invisible) = parents.partition(x => shouldBeDisplayed(x.src) && x.srcVisible)
      val result = visible.toList ++ invisible.toList.flatMap { n =>
        val parentInEdgesToDisplay = inEdgesToDisplay(n.src, visited ++ List(dstNode))
        parentInEdgesToDisplay.map(y => Edge(y.src, dstNode, y.srcVisible, edgeType = edgeType, label = y.label))
      }.distinct
      edgeCache.put(dstNode, result)
      result
    }
  }

  private def expand(v: nodes.StoredNode)(implicit semantics: Semantics): Iterator[Edge] = {

    val allInEdges = v
      .inE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, true, x.property(EdgeKeys.VARIABLE), edgeType))

    v match {
      case trackingPoint: nodes.TrackingPoint =>
        trackingPoint
          .ddgInPathElem(withInvisible = true)
          .map(x => Edge(x.node.asInstanceOf[nodes.StoredNode], v, x.visible, x.outEdgeLabel, edgeType))
          .iterator ++ allInEdges.filter(_.src.isInstanceOf[nodes.Method]).iterator
      case _ =>
        allInEdges.iterator
    }

  }

}
