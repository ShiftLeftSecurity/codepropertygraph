package io.shiftleft.passes.utils

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}

object Traversals {

  def walkAST(expression: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    expression.emit.repeat(_.out(EdgeTypes.AST)).dedup()
  }

  def argumentAtIndex(call: GremlinScala[Vertex], index: Int): GremlinScala[Vertex] = {
    getASTChildAtIndex(call.hasLabel(NodeTypes.CALL), index)
  }

  def getASTChildAtIndex(expression: GremlinScala[Vertex], index: Int): GremlinScala[Vertex] = {
    expression.out(EdgeTypes.AST).has(NodeKeys.ORDER, new Integer(index))
  }

  def walkCFG(cfgVertex: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    cfgVertex.emit.repeat(_.out(EdgeTypes.CFG).simplePath).dedup()
  }

  def inParameters(method: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    method.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD_PARAMETER_IN)
  }

  def outParameters(method: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    method.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD_PARAMETER_OUT)
  }

  def inParameterAtIndex(method: GremlinScala[Vertex], index: Int): GremlinScala[Vertex] = {
    inParameters(method).has(NodeKeys.ORDER, new Integer(index))
  }

  def outParameterAtIndex(method: GremlinScala[Vertex], index: Int): GremlinScala[Vertex] = {
    outParameters(method).has(NodeKeys.ORDER, new Integer(index))
  }

  def methodReturnFromMethod(method: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    method.out(EdgeTypes.AST).hasLabel(NodeTypes.METHOD_RETURN)
  }

  def methodFromMethodReturn(methodReturn: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    methodReturn.in(EdgeTypes.AST)
  }

  def cfgExit(expression: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    walkCFG(expression).hasLabel(NodeTypes.METHOD_RETURN)
  }

  def thisFromMethod(method: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    method.filterOnEnd(_ => false) // TODO
  }

  def cfgEntry(expression: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    expression
      .until(_.union(_.join(_.hasLabel(NodeTypes.METHOD)).join(_.has(NodeKeys.PARSER_TYPE_NAME, "Program"))))
      .repeat(_.in(EdgeTypes.CFG).simplePath)
  }

  def callsByTargetName(expression: GremlinScala[Vertex], name: String): GremlinScala[Vertex] = {
    expression.hasLabel(NodeTypes.CALL).has(NodeKeys.NAME, name)
  }

  def returnsFromMethod(method: GremlinScala[Vertex]): GremlinScala[Vertex] = {
    // Filter on label because they could also be THROW statements.
    methodReturnFromMethod(method).in(EdgeTypes.CFG).hasLabel(NodeTypes.RETURN)
  }
}
