package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes

object Shared {

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
