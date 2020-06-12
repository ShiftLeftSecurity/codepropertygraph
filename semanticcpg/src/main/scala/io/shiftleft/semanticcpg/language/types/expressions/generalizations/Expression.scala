package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

import scala.jdk.CollectionConverters._

/**
  An expression (base type)
  */
class Expression[NodeType <: nodes.Expression](val wrapped: NodeSteps[NodeType]) extends AnyVal {
  private def raw: GremlinScala[NodeType] = wrapped.raw

  /**
    * Traverse to it's parent expression (e.g. call or return) by following the incoming AST nodes.
    * It's continuing it's walk until it hits an expression that's not a generic
    * "member access operation", e.g., "<operator>.memberAccess".
    * */
  def parentExpression: NodeSteps[nodes.Expression] =
    new NodeSteps(raw.map(_.parentExpression))

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
  def toParameter(implicit callResolver: ICallResolver): NodeSteps[nodes.MethodParameterIn] = parameter

  /**
    Traverse to related parameter, if the expression is an argument to a call and the call
    can be resolved.
    */
  def parameter(implicit callResolver: ICallResolver): NodeSteps[nodes.MethodParameterIn] = {
    val trav = for {
      expr <- raw.toIterator.collect { case node: nodes.HasArgumentIndex => node }
      call <- expr._argumentIn.asScala
      calledMethods <- callResolver.getCalledMethods(call.asInstanceOf[nodes.CallRepr])
      paramIn <- calledMethods._astOut.asScala.collect { case node: nodes.MethodParameterIn => node }
      if paramIn.order == expr.argumentIndex
    } yield paramIn

    new NodeSteps(__(trav.toSeq: _*))
  }

  /**
    Traverse to enclosing method
    */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.CONTAINS).cast[nodes.Method])

  /**
    * Traverse to expression evaluation type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
