package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import overflowdb.Node
import overflowdb.traversal._
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

object Shared {

  case class Edge(src: nodes.StoredNode, dst: nodes.StoredNode, label: String = "")

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

  def namedGraphBegin(root: nodes.AstNode): StringBuilder = {
    val sb = new StringBuilder
    val name = root match {
      case method: nodes.Method => method.name
      case _                    => ""
    }
    sb.append(s"digraph $name {  \n")
  }

  def stringRepr(vertex: nodes.AstNode): String = {
    escape(
      vertex match {
        case call: nodes.Call               => (call.name, call.code).toString
        case expr: nodes.Expression         => (expr.label, expr.code, toCfgNode(expr).code).toString
        case method: nodes.Method           => (method.label, method.name).toString
        case ret: nodes.MethodReturn        => (ret.label, ret.typeFullName).toString
        case param: nodes.MethodParameterIn => ("PARAM", param.code).toString
        case local: nodes.Local             => (local.label, s"${local.code}: ${local.typeFullName}").toString
        case target: nodes.JumpTarget       => (target.label, target.name).toString
        case _                              => ""
      }
    )
  }

  def toCfgNode(node: nodes.StoredNode): nodes.CfgNode = {
    node match {
      case node: nodes.Identifier => node.parentExpression.get
      case node: nodes.MethodRef  => node.parentExpression.get
      case node: nodes.Literal    => node.parentExpression.get

      case node: nodes.MethodParameterIn => node.method

      case node: nodes.MethodParameterOut =>
        node.method.methodReturn

      case node: nodes.Call if MemberAccess.isGenericMemberAccessName(node.name) =>
        node.parentExpression.get

      case node: nodes.Call         => node
      case node: nodes.ImplicitCall => node
      case node: nodes.MethodReturn => node
      case node: nodes.Expression   => node
    }
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
