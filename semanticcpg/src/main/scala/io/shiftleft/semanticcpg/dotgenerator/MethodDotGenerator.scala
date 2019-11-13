package io.shiftleft.semanticcpg.dotgenerator

import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.Vertex

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.semanticcpg.language._

import scala.annotation.tailrec

object MethodDotGenerator {

  /**
    * Generates a [[java.lang.String]] representation of a DOT graph for
    * each internal method contained in the set of methods selected in
    * the previous node step(s).
    *
    * @param methodStep A step resulting in a set of methods.
    * @return A [[java.lang.String]] containing a DOT graph for each internal method.
    */
  def toDotGraph(methodStep: NodeSteps[Method]): List[String] =
    methodStep.internal.l.map(generateDotFromMethod)

  private def generateDotFromMethod(method: Method): String = {
    val sb = new StringBuilder
    sb.append(s"digraph ${method.name} {\n")
    sb.append(dotFromMethod(method).mkString("\n"))
    sb.append("\n}\n")
    sb.toString
  }

  private def dotFromMethod(method: Method): List[String] = {

    @tailrec
    def go(subExpressions: List[Expression],
           parentExpression: Option[Expression] = None,
           currentGraph: List[String] = List.empty): List[String] = {

      val parentId = parentExpression.map(_.id.toString).getOrElse(method.id)

      subExpressions match {
        case Nil =>
          currentGraph
        case expr :: tail =>
          expr match {
            case ex: Block =>
              val dotExpression = s""" "$parentId" -> "${ex.id}" [label="BLOCK"];"""
              go(getNestedTopLevelExpressions(ex) ::: tail, Some(ex), currentGraph :+ dotExpression)
            case ex: ControlStructure =>
              val dotExpression = s""" "$parentId" -> "${ex.id}" [label="${ex.code}"];"""
              go(getNestedTopLevelExpressions(ex) ::: tail, Some(ex), currentGraph :+ dotExpression)
            case ex: Return =>
              val dotExpression = s""" "$parentId" -> "${ex.id}" [label="${ex.code}"];"""
              go(tail, parentExpression, currentGraph :+ dotExpression)
            case ex: Call =>
              val dotExpression = s""" "$parentId" -> "${ex.id}" [label="${ex.code}"];"""
              go(tail, parentExpression, currentGraph :+ dotExpression)
            case _ =>
              // Ignore all other node types.
              go(tail, parentExpression, currentGraph)
          }
      }
    }

    val methodExpressions = method
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.BLOCK)
      .out(EdgeTypes.AST)
      .not(_.hasLabel(NodeTypes.LOCAL, NodeTypes.TYPE_DECL))
      .cast[Expression]
      .l

    go(methodExpressions)
  }

  private def getNestedTopLevelExpressions(vertex: Vertex): List[Expression] = {
    vertex
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.BLOCK, NodeTypes.CONTROL_STRUCTURE, NodeTypes.RETURN, NodeTypes.CALL)
      .cast[Expression]
      .l
  }
}
