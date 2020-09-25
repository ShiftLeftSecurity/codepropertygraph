package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.dotgenerator.Shared.{Edge, escape}
import overflowdb.Node
import overflowdb.traversal._
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

object DotCfgGenerator {

  def toDotCfg(traversal: Traversal[nodes.Method]): Traversal[String] =
    traversal.map(dotGraph(_, expand, cfgNodeShouldBeDisplayed))

  def dotGraph(method: nodes.Method,
               expand: nodes.StoredNode => Iterator[Edge],
               cfgNodeShouldBeDisplayed: Node => Boolean,
  ): String = {
    val sb = Shared.namedGraphBegin(method)
    sb.append(nodesAndEdges(method, expand, cfgNodeShouldBeDisplayed).mkString("\n"))
    Shared.graphEnd(sb)
  }

  private def nodesAndEdges(methodNode: nodes.Method,
                            expand: nodes.StoredNode => Iterator[Edge],
                            cfgNodeShouldBeDisplayed: Node => Boolean): List[String] = {
    val vertices = methodNode.start.cfgNode.l ++ List(methodNode, methodNode.methodReturn) ++ methodNode.parameter.l
    val verticesToDisplay = vertices.filter(cfgNodeShouldBeDisplayed)

    def edgesToDisplay(srcNode: nodes.StoredNode, visited: List[nodes.StoredNode] = List()): List[Edge] = {
      if (visited.contains(srcNode)) {
        List()
      } else {
        val children = expand(srcNode).filter(x => vertices.contains(x.dst))
        val (visible, invisible) = children.partition(x => cfgNodeShouldBeDisplayed(x.dst))
        visible.toList ++ invisible.toList.flatMap { n =>
          edgesToDisplay(n.dst, visited ++ List(srcNode)).map(y => Edge(srcNode, y.dst))
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
            Some(s""" [ label = "${escape(edge.label)}"] """).filter(_ => edge.label != "").getOrElse(""))
    }

    nodeStrings ++ edgeStrings
  }

  protected def expand(v: nodes.StoredNode): Iterator[Edge] = {
    v._cfgOut.asScala
      .filter(_.isInstanceOf[nodes.StoredNode])
      .map(node => Edge(v, node))
  }

  def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Literal] ||
      v.isInstanceOf[nodes.Identifier] ||
      v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget] ||
      v.isInstanceOf[nodes.MethodParameterIn]
  )

}
