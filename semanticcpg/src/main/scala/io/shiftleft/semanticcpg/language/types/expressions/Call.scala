package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.structure.{Member, Method, MethodReturn}

/**
  A call site
  */
class Call[A <: nodes.Call](raw: GremlinScala[A]) extends NodeSteps[A](raw) with EvalTypeAccessors[A] {

  /**
    Only statically dispatched calls
    */
  def isStatic: Call[A] =
    this.dispatchType("STATIC_DISPATCH")

  /**
    Only dynamically dispatched calls
    */
  def isDynamic: Call[A] =
    this.dispatchType("DYNAMIC_DISPATCH")

  /**
    The caller
    */
  def method: Method[nodes.Method] =
    new Method(raw.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    The receiver of a call if the call has a receiver associated.
    */
  def receiver: Expression[nodes.Expression] =
    new Expression(raw.out(EdgeTypes.RECEIVER).cast[nodes.Expression])

  /**
    Arguments of the call
    */
  def argument: Expression[nodes.Expression] =
    new Expression(raw.out(EdgeTypes.ARGUMENT).cast[nodes.Expression])

  /**
    `i'th` arguments of the call
    */
  def argument(i: Integer): Expression[nodes.Expression] =
    argument.argIndex(i)

  /**
    To formal method return parameter
    */
  def toMethodReturn: MethodReturn[nodes.MethodReturn] =
    new MethodReturn(
      raw
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .cast[nodes.MethodReturn])

  /**
    * Traverse to referenced members
    * */
  def referencedMember: Member[nodes.Member] =
    new Member(raw.out(EdgeTypes.REF).cast[nodes.Member])

}
