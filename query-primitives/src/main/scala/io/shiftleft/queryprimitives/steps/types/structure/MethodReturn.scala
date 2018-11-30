package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.Expression
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{
  CodeAccessors,
  EvalTypeAccessors,
  LineNumberAccessors
}
import shapeless.HList

class MethodReturn[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.MethodReturn, Labels](raw)
    with CodeAccessors[nodes.MethodReturn, Labels]
    with LineNumberAccessors[nodes.MethodReturn, Labels]
    with EvalTypeAccessors[nodes.MethodReturn, Labels] {
  override val converter = Converter.forDomainNode[nodes.MethodReturn]

  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.AST))

  def returnUser: Call[Labels] =
    new Call[Labels](raw.out(EdgeTypes.CALL_RET).hasLabel(NodeTypes.CALL))

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression[Labels] =
    new Expression[Labels](raw.in(EdgeTypes.CFG))

  /**
    * Traverse to return type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE))
}
