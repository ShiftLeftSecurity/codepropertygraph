package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.Node
import io.shiftleft.semanticcpg.language._

object Shared {

  def dotGraph(method: nodes.Method, expand: nodes.CfgNode => Iterator[nodes.CfgNode]): String = {
    val sb = Shared.namedGraphBegin(method)
    sb.append(nodesAndEdges(method, expand).mkString("\n"))
    Shared.graphEnd(sb)
  }

  def cfgNodeShouldBeDisplayed(v: Node): Boolean = !(
    v.isInstanceOf[nodes.Literal] ||
      v.isInstanceOf[nodes.Identifier] ||
      v.isInstanceOf[nodes.Block] ||
      v.isInstanceOf[nodes.ControlStructure] ||
      v.isInstanceOf[nodes.JumpTarget]
  )

  private def nodesAndEdges(methodNode: nodes.Method,
                            expand: nodes.CfgNode => Iterator[nodes.CfgNode]): List[String] = {
    val vertices = methodNode.start.cfgNode.l ++ List(methodNode, methodNode.methodReturn)
    val verticesToDisplay = vertices.filter(cfgNodeShouldBeDisplayed)

    def visibleNeighbors(v: nodes.CfgNode, visited: List[nodes.StoredNode] = List()): List[nodes.StoredNode] = {
      if (visited.contains(v)) {
        List()
      } else {
        val children = expand(v).filter(vertices.contains)
        val (visible, invisible) = children.partition(cfgNodeShouldBeDisplayed)
        visible.toList ++ invisible.toList.flatMap { n =>
          visibleNeighbors(n, visited ++ List(v))
        }
      }
    }

    val edges = verticesToDisplay.map { v =>
      (v.id2, visibleNeighbors(v).map(_.id2))
    }

    val nodeStrings = verticesToDisplay.map { node =>
      s""""${node.id2}" [label = "${Shared.stringRepr(node)}" ]""".stripMargin
    }

    val edgeStrings = edges.flatMap {
      case (id, childIds) =>
        childIds.map(childId => s"""  "$id" -> "$childId"  """)
    }

    nodeStrings ++ edgeStrings
  }

  def namedGraphBegin(root: nodes.AstNode): StringBuilder = {
    val sb = new StringBuilder
    val name = root match {
      case method: nodes.Method => method.name
      case _                    => ""
    }
    sb.append(s"digraph $name {\n")
  }

  def stringRepr(vertex: nodes.AstNode): String = {
    escape(
      vertex match {
        case call: nodes.Call               => (call.name, call.code).toString
        case expr: nodes.Expression         => (expr.label, expr.code).toString
        case method: nodes.Method           => (method.label, method.name).toString
        case ret: nodes.MethodReturn        => (ret.label, ret.typeFullName).toString
        case param: nodes.MethodParameterIn => ("PARAM", param.code).toString
        case local: nodes.Local             => (local.label, s"${local.code}: ${local.typeFullName}").toString
        case target: nodes.JumpTarget       => (target.label, target.name).toString
        case _                              => ""
      }
    )
  }

  private def escape(str: String): String = {
    str.replaceAllLiterally("\"", "\\\"")
  }

  def graphEnd(sb: StringBuilder): String = {
    sb.append("\n}\n")
    sb.toString
  }

}

abstract class CfgBasedDotGenerator {

  protected def expand(node: nodes.CfgNode): java.util.Iterator[nodes.StoredNode]

}
