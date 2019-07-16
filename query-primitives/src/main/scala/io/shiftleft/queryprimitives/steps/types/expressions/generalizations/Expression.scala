package io.shiftleft.queryprimitives.steps.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.{Call, Identifier, Literal}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.structure.{Method, MethodParameter, Type}
import shapeless.HList

import io.shiftleft.queryprimitives.steps.ICallResolver

/**
  An expression (base type)
  */
class Expression[Labels <: HList](raw: GremlinScala.Aux[nodes.Expression, Labels])
    extends NodeSteps[nodes.Expression, Labels](raw)
    with ExpressionBase[nodes.Expression, Labels] {}

trait ExpressionBase[NodeType <: nodes.Expression, Labels <: HList]
    extends OrderAccessors[NodeType, Labels]
    with ArgumentIndexAccessors[NodeType, Labels]
    with EvalTypeAccessors[NodeType, Labels]
    with CodeAccessors[NodeType, Labels]
    with LineNumberAccessors[NodeType, Labels]
    with AstNodeBase[NodeType, Labels] { this: NodeSteps[NodeType, Labels] =>

  /**
    Traverse to enclosing expression
    */
  def expressionUp: Expression[Labels] =
    new Expression[Labels](raw.in(EdgeTypes.AST).not(_.hasLabel(NodeTypes.LOCAL)).cast[nodes.Expression])

  /**
    Traverse to sub expressions
    */
  def expressionDown: Expression[Labels] =
    new Expression[Labels](
      raw
        .out(EdgeTypes.AST)
        .not(_.hasLabel(NodeTypes.LOCAL))
        .cast[nodes.Expression])

  /**
    If the expression is used as receiver for a call, this traverses to the call.
    */
  def receivedCall: Call[Labels] =
    new Call[Labels](
      raw
        .in(EdgeTypes.RECEIVER)
        .cast[nodes.Call]
    )

  /**
    Traverse to related parameter
    */
  def toParameter: MethodParameter[Labels] = {
    new MethodParameter[Labels](
      raw
        .sack((sack: Integer, node: nodes.Expression) => node.value2(NodeKeys.ARGUMENT_INDEX))
        .in(EdgeTypes.AST)
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .filterWithTraverser { traverser =>
          val parameterIndex = traverser.sack[Integer]
          traverser.get.value2(NodeKeys.ORDER) == parameterIndex
        }
        .cast[nodes.MethodParameterIn]
    )
  }

  /**
    Traverse to enclosing method
    */
  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.CONTAINS).cast[nodes.Method])

  /**
    * Traverse to next expression in CFG.
    */
  def cfgNext: Expression[Labels] =
    new Expression[Labels](
      raw
        .out(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD_RETURN))
        .cast[nodes.Expression]
    )

  /**
    * Traverse to previous expression in CFG.
    */
  def cfgPrev: Expression[Labels] =
    new Expression[Labels](
      raw
        .in(EdgeTypes.CFG)
        .filterNot(_.hasLabel(NodeTypes.METHOD))
        .cast[nodes.Expression]
    )

  /**
    * Traverse to expression evaluation type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
