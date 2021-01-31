package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

object DotSerializer {

  case class Graph(vertices: List[nodes.StoredNode], edges: List[Edge]) {

    def ++(other: Graph): Graph = {
      Graph((this.vertices ++ other.vertices).distinct, (this.edges ++ other.edges).distinct)
    }

  }
  case class Edge(src: nodes.StoredNode,
                  dst: nodes.StoredNode,
                  srcVisible: Boolean = true,
                  label: String = "",
                  edgeType: String = "")

  def dotGraph(root: nodes.AstNode, graph: Graph, withEdgeTypes: Boolean = false): String = {
    val sb = DotSerializer.namedGraphBegin(root)
    val nodeStrings = graph.vertices.map(DotSerializer.nodeToDot)
    val edgeStrings = graph.edges.map(e => DotSerializer.edgeToDot(e, withEdgeTypes))
    sb.append((nodeStrings ++ edgeStrings).mkString("\n"))
    DotSerializer.graphEnd(sb)
  }

  private def namedGraphBegin(root: nodes.AstNode): StringBuilder = {
    val sb = new StringBuilder
    val name = root match {
      case method: nodes.Method => method.name
      case _                    => ""
    }
    sb.append(s"digraph $name {  \n")
    sb.append("  rankdir=LR\n")
  }

  private def stringRepr(vertex: nodes.StoredNode): String = {
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

  private def toCfgNode(node: nodes.StoredNode): nodes.CfgNode = {
    node match {
      case node: nodes.Identifier        => node.parentExpression.get
      case node: nodes.MethodRef         => node.parentExpression.get
      case node: nodes.Literal           => node.parentExpression.get
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

  private def nodeToDot(node: nodes.StoredNode): String = {
    s""""${node.id}" [label = "${DotSerializer.stringRepr(node)}" ]""".stripMargin
  }

  private def edgeToDot(edge: Edge, withEdgeTypes: Boolean): String = {
    val edgeLabel = if (withEdgeTypes) {
      edge.edgeType + ": " + DotSerializer.escape(edge.label)
    } else {
      DotSerializer.escape(edge.label)
    }
    val labelStr = Some(s""" [ label = "$edgeLabel"] """).filter(_ => edgeLabel != "").getOrElse("")
    s"""  "${edge.src.id}" -> "${edge.dst.id}" """ + labelStr
  }

  private def escape(str: String): String = {
    str.replace("\"", "\\\"")
  }

  private def graphEnd(sb: StringBuilder): String = {
    sb.append("\n}\n")
    sb.toString
  }

}
