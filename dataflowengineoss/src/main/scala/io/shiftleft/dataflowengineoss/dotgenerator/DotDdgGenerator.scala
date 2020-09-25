package io.shiftleft.dataflowengineoss.dotgenerator

import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes, nodes}
import io.shiftleft.dataflowengineoss.language._
import io.shiftleft.dataflowengineoss.semanticsloader.Semantics
import io.shiftleft.semanticcpg.dotgenerator.Shared
import io.shiftleft.semanticcpg.dotgenerator.Shared.Edge
import overflowdb.Node
import overflowdb.traversal._
import io.shiftleft.semanticcpg.language._

object DotDdgGenerator {

  def toDotDdg(traversal: Traversal[nodes.Method])(implicit semantics: Semantics): Traversal[String] =
    traversal.map(dotGraphForMethod)

  private def dotGraphForMethod(method: nodes.Method)(implicit semantics: Semantics): String = {
    val sb = Shared.namedGraphBegin(method)
    sb.append(nodesAndEdges(method).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(methodNode: nodes.Method)(implicit semantics: Semantics): List[String] = {
    val entryNode = methodNode
    val paramNodes = methodNode.parameter.l
    val allOtherNodes = methodNode.start.cfgNode.l
    val exitNode = methodNode.methodReturn
    val allNodes: List[nodes.StoredNode] = List(entryNode, exitNode) ++ allOtherNodes ++ paramNodes
    val visibleNodes = allNodes.filter(shouldBeDisplayed)

    val edges = visibleNodes.map { dstNode =>
      inEdgesToDisplay(allNodes, dstNode)
    }

    val allIdsReferencedByEdges = edges.flatten.flatMap { edge: Edge =>
      Set(edge.src.id, edge.dst.id)
    }

    val nodeStrings = visibleNodes.map { node =>
      if (allIdsReferencedByEdges.contains(node.id)) {
        s""""${node.id}" [label = "${Shared.stringRepr(node)}" ]""".stripMargin
      } else {
        ""
      }
    }

    val edgeStrings = edges.flatMap { edges: List[Edge] =>
      edges.map(
        edge =>
          s"""  "${edge.src.id}" -> "${edge.dst.id}" """ +
            Some(s""" [ label = "${Shared.escape(edge.label)}"] """).filter(_ => edge.label != "").getOrElse(""))
    }

    nodeStrings ++ edgeStrings
  }

  private def shouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

  private def inEdgesToDisplay(vertices: List[nodes.StoredNode],
                               dstNode: nodes.StoredNode,
                               visited: List[nodes.StoredNode] = List())(implicit semantics: Semantics): List[Edge] = {
    if (visited.contains(dstNode)) {
      List()
    } else {
      val parents = expand(dstNode).filter(x => vertices.contains(x.src))
      val (visible, invisible) = parents.partition(x => shouldBeDisplayed(x.src))
      visible.toList ++ invisible.toList.flatMap { n =>
        inEdgesToDisplay(vertices, n.src, visited ++ List(dstNode)).map(y => Edge(y.src, dstNode))
      }
    }
  }

  private def expand(v: nodes.StoredNode)(implicit semantics: Semantics): Iterator[Edge] = {

    val allInEdges = v
      .inE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE)))

    v match {
      case trackingPoint: nodes.TrackingPoint =>
        trackingPoint.ddgInPathElem
          .map(x => Edge(x.node.asInstanceOf[nodes.StoredNode], v, x.outEdgeLabel))
          .iterator ++ allInEdges.filter(_.src.isInstanceOf[nodes.Method]).iterator
      case _ =>
        allInEdges.iterator
    }

  }

}
