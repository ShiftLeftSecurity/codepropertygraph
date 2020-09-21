package io.shiftleft.semanticcpg.language.types.expressions.generalizations

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

import scala.jdk.CollectionConverters._

/**
  An expression (base type)
  */
class Expression[NodeType <: nodes.Expression](val traversal: Traversal[NodeType]) extends AnyVal {

  /**
    * Traverse to it's parent expression (e.g. call or return) by following the incoming AST nodes.
    * It's continuing it's walk until it hits an expression that's not a generic
    * "member access operation", e.g., "<operator>.memberAccess".
    * */
  def parentExpression: Traversal[nodes.Expression] =
    traversal.flatMap(_.parentExpression)

  /**
    Traverse to enclosing expression
    */
  def expressionUp: Traversal[nodes.Expression] =
    traversal.in(EdgeTypes.AST).not(_.hasLabel(NodeTypes.LOCAL)).cast[nodes.Expression]

  /**
    Traverse to sub expressions
    */
  def expressionDown: Traversal[nodes.Expression] =
    traversal
      .out(EdgeTypes.AST)
      .not(_.hasLabel(NodeTypes.LOCAL))
      .cast[nodes.Expression]

  /**
    If the expression is used as receiver for a call, this traverses to the call.
    */
  def receivedCall: Traversal[nodes.Call] =
    traversal.in(EdgeTypes.RECEIVER).cast[nodes.Call]

  /**
    * Only those expressions which are (direct) arguments of a call
    * */
  def isArgument: Traversal[nodes.Expression] =
    traversal.where(_.in(EdgeTypes.ARGUMENT)).cast[nodes.Expression]

  /**
    * Traverse to surrounding call
    * */
  def inCall: Traversal[nodes.Call] =
    traversal.repeat(_.in(EdgeTypes.ARGUMENT))(_.until(_.hasLabel(NodeTypes.CALL))).cast[nodes.Call]

  /**
    * Traverse to surrounding call
    * */
  @deprecated("Use inCall")
  def call: Traversal[nodes.Call] =
    traversal.repeat(_.in(EdgeTypes.ARGUMENT))(_.until(_.hasLabel(NodeTypes.CALL))).cast[nodes.Call]

  /**
  Traverse to related parameter
    */
  @deprecated("", "October 2019")
  def toParameter(implicit callResolver: ICallResolver): Traversal[nodes.MethodParameterIn] = parameter

  /**
    Traverse to related parameter, if the expression is an argument to a call and the call
    can be resolved.
    */
  def parameter(implicit callResolver: ICallResolver): Traversal[nodes.MethodParameterIn] =
    for {
      expr <- traversal.collect { case node: nodes.HasArgumentIndex => node }
      call <- expr._argumentIn.asScala
      calledMethods <- callResolver.getCalledMethods(call.asInstanceOf[nodes.CallRepr])
      paramIn <- calledMethods._astOut.asScala.collect { case node: nodes.MethodParameterIn => node }
      if paramIn.order == expr.argumentIndex
    } yield paramIn

  /**
    Traverse to enclosing method
    */
  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.CONTAINS).cast[nodes.Method]

  /**
    * Traverse to expression evaluation type
    * */
  def typ: Traversal[nodes.Type] =
    traversal.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type]

}
