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
    traversal.map(dotGraph)

  private def dotGraph(method: nodes.Method)(implicit semantics: Semantics): String = {
    val sb = Shared.namedGraphBegin(method)
    sb.append(nodesAndEdges(method).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(methodNode: nodes.Method)(implicit semantics: Semantics): List[String] = {
    val vertices = methodNode.start.cfgNode.l ++ List(methodNode, methodNode.methodReturn) ++ methodNode.parameter.l
    val verticesToDisplay = vertices.filter(cfgNodeShouldBeDisplayed)

    def edgesToDisplay(dstNode: nodes.StoredNode, visited: List[nodes.StoredNode] = List()): List[Edge] = {
      if (visited.contains(dstNode)) {
        List()
      } else {
        val parents = expand(dstNode).filter(x => vertices.contains(x.src))
        val (visible, invisible) = parents.partition(x => cfgNodeShouldBeDisplayed(x.src))
        visible.toList ++ invisible.toList.flatMap { n =>
          edgesToDisplay(n.src, visited ++ List(dstNode)).map(y => Edge(y.src, dstNode))
        }
      }
    }

    val edges = verticesToDisplay.map { v =>
      edgesToDisplay(v)
    }

    val allIdsReferencedByEdges = edges.flatten.flatMap { edge =>
      Set(edge.src.id, edge.dst.id)
    }

    val nodeStrings = verticesToDisplay.map { node =>
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

  private def expand(v: nodes.StoredNode)(implicit semantics: Semantics): Iterator[Edge] = {

    val allInEdges = v
      .inE(EdgeTypes.REACHING_DEF)
      .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE)))

    val edgesFromMethods = allInEdges.filter(_.src.isInstanceOf[nodes.Method])

    v match {
      case trackingPoint: nodes.TrackingPoint =>
        trackingPoint.ddgInPathElem
          .map(x => Edge(x.node.asInstanceOf[nodes.StoredNode], v, x.inEdgeLabel))
          .iterator ++ edgesFromMethods.iterator
      case _ =>
        v.inE(EdgeTypes.REACHING_DEF)
          .map(x => Edge(x.outNode.asInstanceOf[nodes.StoredNode], v, x.property(EdgeKeys.VARIABLE)))
          .iterator
    }

  }

  private def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

}
