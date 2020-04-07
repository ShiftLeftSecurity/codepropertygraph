package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.Call
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{Method, MethodParameter, Type}

/**
  An expression (base type)
  */
class Expression[NodeType <: nodes.Expression](val wrapped: NodeSteps[NodeType]) extends AnyVal {
  private def raw: GremlinScala[NodeType] = wrapped.raw

  /**
    Traverse to enclosing expression
    */
  def expressionUp: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.in(EdgeTypes.AST).not(_.hasLabel(NodeTypes.LOCAL)).cast[nodes.Expression])

  /**
    Traverse to sub expressions
    */
  def expressionDown: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  /**
    If the expression is used as receiver for a call, this traverses to the call.
    */
  def receivedCall: NodeSteps[nodes.Call] =
    new NodeSteps(raw.in(EdgeTypes.RECEIVER).cast[nodes.Call])

  /**
    * Only those expressions which are (direct) arguments of a call
    * */
  def isArgument: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.filter(_.in(EdgeTypes.ARGUMENT)).cast[nodes.Expression])

  /**
    * Traverse to surrounding call
    * */
  def call: NodeSteps[nodes.Call] =
    new NodeSteps(raw.repeat(_.in(EdgeTypes.ARGUMENT)).until(_.hasLabel(NodeTypes.CALL)).cast[nodes.Call])

  /**
  Traverse to related parameter
    */
  @deprecated("", "October 2019")
  def toParameter: NodeSteps[nodes.MethodParameterIn] = parameter

  /**
    Traverse to related parameter, if the expression is an argument to a call and the call
    can be resolved.
    */
  def parameter: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(
      raw
        .sack((sack: Integer, node: nodes.Expression) => node.value2(NodeKeys.ARGUMENT_INDEX))
        .in(EdgeTypes.ARGUMENT)
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .filterWithTraverser { traverser =>
          val parameterIndex = traverser.sack[Integer]
          traverser.get.value2(NodeKeys.ORDER) == parameterIndex
        }
        .cast[nodes.MethodParameterIn]
    )

  /**
    Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.CONTAINS).cast[nodes.Method])

  /**
    * Traverse to next expression in CFG.
    */
  def cfgNext: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .out(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD_RETURN))
        .cast[nodes.Expression]
    )

  /**
    * Traverse to previous expression in CFG.
    */
  def cfgPrev: NodeSteps[nodes.Expression] =
    new NodeSteps(
      raw
        .in(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Expression]
    )

  /**
    * Traverse to expression evaluation type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
