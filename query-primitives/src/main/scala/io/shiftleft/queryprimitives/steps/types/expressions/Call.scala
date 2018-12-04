package io.shiftleft.queryprimitives.steps.types.expressions

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.ICallResolver
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations._
import io.shiftleft.queryprimitives.steps.types.structure.{Member, Method, MethodInst, MethodReturn}
import shapeless.HList

/**
  A call site
  */
class Call[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Call, Labels](raw)
    with CodeAccessors[nodes.Call, Labels]
    with NameAccessors[nodes.Call, Labels]
    with OrderAccessors[nodes.Call, Labels]
    with SignatureAccessors[nodes.Call, Labels]
    with DispatchTypeAccessors[nodes.Call, Labels]
    with LineNumberAccessors[nodes.Call, Labels]
    with EvalTypeAccessors[nodes.Call, Labels]
    with ExpressionBase[nodes.Call, Labels] {

  /**
    The caller
    */
  override def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.CONTAINS).hasLabel(NodeTypes.METHOD))

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
    // note: side effect writes edges into the graph
    new MethodInst[Labels](raw.sideEffect { v =>
      callResolver.resolveDynamicCallSite(v.toCC[nodes.Call])
    }.out(EdgeTypes.CALL))

  /**
    Arguments of the call
    */
  def argument: Expression[Labels] =
    new Expression[Labels](raw.out(EdgeTypes.AST))

  /**
    To formal method return parameter
    */
  def toMethodReturn: MethodReturn[Labels] =
    new MethodReturn[Labels](raw.in(EdgeTypes.CALL_RET))

  /**
    * Traverse to referenced members
    * */
  def referencedMember: Member[Labels] = new Member(
    raw.out(EdgeTypes.REF)
  )

}
