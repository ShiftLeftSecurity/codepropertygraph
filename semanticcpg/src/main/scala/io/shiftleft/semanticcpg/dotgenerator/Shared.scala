package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.utils.MemberAccess

object Shared {

  case class Graph(vertices: List[nodes.StoredNode], edges: List[Edge])
  case class Edge(src: nodes.StoredNode, dst: nodes.StoredNode, label: String = "")

  def namedGraphBegin(root: nodes.AstNode): StringBuilder = {
    val sb = new StringBuilder
    val name = root match {
      case method: nodes.Method => method.name
      case _                    => ""
    }
    sb.append(s"digraph $name {  \n")
  }

  def stringRepr(vertex: nodes.StoredNode): String = {
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

  def nodeToDot(node: nodes.StoredNode): String = {
    s""""${node.id}" [label = "${Shared.stringRepr(node)}" ]""".stripMargin
  }

  def edgeToDot(edge: Edge): String = {
    s"""  "${edge.src.id}" -> "${edge.dst.id}" """ +
      Some(s""" [ label = "${Shared.escape(edge.label)}"] """).filter(_ => edge.label != "").getOrElse("")
  }

  def escape(str: String): String = {
    str.replaceAllLiterally("\"", "\\\"")
  }

  def graphEnd(sb: StringBuilder): String = {
    sb.append("\n}\n")
    sb.toString
  }

}
