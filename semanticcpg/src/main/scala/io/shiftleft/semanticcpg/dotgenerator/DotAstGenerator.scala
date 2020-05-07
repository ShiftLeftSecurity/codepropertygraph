package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

object DotAstGenerator {

  def toDotAst[T <: nodes.AstNode](step: NodeSteps[T]): Steps[String] = step.map(dotAst)

  def dotAst(astRoot: nodes.AstNode): String = {
    val sb = new StringBuilder
    val name = astRoot match {
      case method: nodes.Method => method.name
      case _                    => ""
    }
    sb.append(s"digraph $name {\n")
    sb.append(nodesAndEdges(astRoot).mkString("\n"))
    sb.append("\n}\n")
    sb.toString
  }

  private def nodesAndEdges(astRoot: nodes.AstNode): List[String] = {

    def shouldBeDisplayed(v: nodes.AstNode): Boolean = !v.isInstanceOf[nodes.MethodParameterOut]

    val vertices = astRoot.ast.where(shouldBeDisplayed).l
    val edges = vertices.map(v => (v.getId, v.start.astChildren.where(shouldBeDisplayed).id.l))

    val nodeStrings = vertices.map { node =>
      s""""${node.getId}" [label = "${escape(stringRepr(node))}" ]""".stripMargin
    }

    val edgeStrings = edges.flatMap {
      case (id, childIds) =>
        childIds.map(childId => s"""  "$id" -> "$childId"  """)
    }

    nodeStrings ++ edgeStrings
  }

  private def stringRepr(vertex: nodes.AstNode): String = {
    vertex match {
      case call: nodes.Call               => (call.name, call.code).toString
      case expr: nodes.Expression         => (expr.label, expr.code).toString
      case method: nodes.Method           => (method.label, method.name).toString
      case ret: nodes.MethodReturn        => (ret.label, ret.typeFullName).toString
      case param: nodes.MethodParameterIn => ("PARAM", param.code).toString
      case _                              => ""
    }
  }

  private def escape(str: String): String = {
    str.replaceAllLiterally("\"", "\\\"")
  }
}
