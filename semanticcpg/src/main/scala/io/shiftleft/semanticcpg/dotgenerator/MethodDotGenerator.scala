package io.shiftleft.semanticcpg.dotgenerator

import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._

object MethodDotGenerator {

  /**
    * Generates a java.lang.String representation of a DOT graph for
    * each internal method contained in the set of methods selected in
    * the previous node step(s).
    *
    * @param methodStep A step resulting in a set of methods.
    * @return A java.lang.String containing a DOT graph for each internal method.
    */
  def toDotGraph(methodStep: NodeSteps[nodes.Method]): List[String] =
    methodStep.internal.l.map(generateDotFromMethod)

  def generateDotFromMethod(method: nodes.Method): String = {
    val sb = new StringBuilder
    sb.append(s"digraph ${method.name} {\n")
    sb.append(dotFromMethod(method).mkString("\n"))
    sb.append("\n}\n")
    sb.toString
  }

  def dotFromMethod(method: nodes.Method): List[String] = {

    def shouldBeDisplayed(v: nodes.AstNode): Boolean = !v.isInstanceOf[nodes.MethodParameterOut]

    val vertices = method.ast.where(shouldBeDisplayed).l
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
