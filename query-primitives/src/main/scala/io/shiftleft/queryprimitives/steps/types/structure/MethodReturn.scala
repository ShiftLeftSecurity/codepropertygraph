package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.types.expressions.Call
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.Expression
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.{
  CodeAccessors,
  EvalTypeAccessors,
  LineNumberAccessors
}
import shapeless.HList

class MethodReturn[Labels <: HList](raw: GremlinScala.Aux[nodes.MethodReturn, Labels])
    extends NodeSteps[nodes.MethodReturn, Labels](raw)
    with CodeAccessors[nodes.MethodReturn, Labels]
    with LineNumberAccessors[nodes.MethodReturn, Labels]
    with EvalTypeAccessors[nodes.MethodReturn, Labels] {

  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.AST).cast[nodes.Method])

  def returnUser: Call[Labels] = {
    new Call[Labels](
      raw.in(EdgeTypes.AST).in(EdgeTypes.REF).in(EdgeTypes.CALL).cast[nodes.Call]
    )
  }

  /**
    *  Traverse to last expressions in CFG.
    *  Can be multiple.
    */
  def cfgLast: Expression[Labels] =
    new Expression[Labels](
      raw.in(EdgeTypes.CFG).cast[nodes.Expression]
    )

  /**
    * Traverse to return type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
