package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{ICallResolver, NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.structure.{Member, Method, MethodInst, MethodReturn}
import shapeless.HList

/**
  A call site
  */
class Call[Labels <: HList](raw: GremlinScala.Aux[nodes.CallRef, Labels])
    extends NodeSteps[nodes.CallRef, Labels](raw)
    with CodeAccessors[nodes.CallRef, Labels]
    with NameAccessors[nodes.CallRef, Labels]
    with OrderAccessors[nodes.CallRef, Labels]
    with SignatureAccessors[nodes.CallRef, Labels]
    with DispatchTypeAccessors[nodes.CallRef, Labels]
    with LineNumberAccessors[nodes.CallRef, Labels]
    with EvalTypeAccessors[nodes.CallRef, Labels]
    with ExpressionBase[nodes.CallRef, Labels] {

  /**
    Only statically dispatched calls
    */
  def isStatic: Call[Labels] = dispatchType("STATIC_DISPATCH")

  /**
    Only dynamically dispatched calls
    */
  def isDynamic: Call[Labels] = dispatchType("DYNAMIC_DISPATCH")

  /**
    The caller
    */
  override def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD).cast[nodes.MethodRef])

  /**
    The callee method
    */
  def calledMethod(implicit callResolver: ICallResolver): Method[Labels] = {
    calledMethodInstance(callResolver).method
  }

  /**
   The callee method instance
    */
  def calledMethodInstance(implicit callResolver: ICallResolver): MethodInst[Labels] =
    new MethodInst[Labels](
      sideEffect(callResolver.resolveDynamicCallSite).raw
        .out(EdgeTypes.CALL)
        .cast[nodes.MethodInstRef])

  /**
    Arguments of the call
    */
  def argument: Expression[Labels] =
    new Expression[Labels](raw.out(EdgeTypes.AST).cast[nodes.Expression])

  /**
    `i'th` arguments of the call
    */
  def argument(i: Integer): Expression[Labels] =
    argument.order(i)

  /**
    To formal method return parameter
    */
  def toMethodReturn: MethodReturn[Labels] =
    new MethodReturn[Labels](
      raw
        .out(EdgeTypes.CALL)
        .out(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .cast[nodes.MethodReturnRef])

  /**
    * Traverse to referenced members
    * */
  def referencedMember: Member[Labels] = new Member(
    raw.out(EdgeTypes.REF).cast[nodes.MemberRef]
  )

}
