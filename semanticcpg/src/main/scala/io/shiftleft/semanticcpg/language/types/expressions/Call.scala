package io.shiftleft.semanticcpg.language.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations._
import io.shiftleft.semanticcpg.language.types.structure.{Member, Method, MethodReturn}

/**
  A call site
  */
class Call(raw: GremlinScala[nodes.Call])
    extends NodeSteps[nodes.Call](raw)
    with CodeAccessors[nodes.Call]
    with NameAccessors[nodes.Call]
    with OrderAccessors[nodes.Call]
    with SignatureAccessors[nodes.Call]
    with DispatchTypeAccessors[nodes.Call]
    with LineNumberAccessors[nodes.Call]
    with EvalTypeAccessors[nodes.Call]
    with ExpressionBase[nodes.Call] {

  /**
    Only statically dispatched calls
    */
  def isStatic: Call = dispatchType("STATIC_DISPATCH")

  /**
    Only dynamically dispatched calls
    */
  def isDynamic: Call = dispatchType("DYNAMIC_DISPATCH")

  /**
    The caller
    */
  override def method: Method =
    new Method(raw.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD).cast[nodes.Method])

  /**
    The receiver of a call if the call has a receiver associated.
    */
  def receiver: Expression =
    new Expression(raw.out(EdgeTypes.RECEIVER).cast[nodes.Expression])

  /**
    Arguments of the call
    */
  def argument: Expression =
    new Expression(raw.out(EdgeTypes.AST).cast[nodes.Expression])

  /**
    `i'th` arguments of the call
    */
  def argument(i: Integer): Expression =
    argument.order(i)

  /**
    To formal method return parameter
    */
  def toMethodReturn: MethodReturn =
    new MethodReturn(
      raw
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .cast[nodes.MethodReturn])

  /**
    * Traverse to referenced members
    * */
  def referencedMember: Member = new Member(
    raw.out(EdgeTypes.REF).cast[nodes.Member]
  )

}
