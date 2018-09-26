package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{DeclarationBase, Expression}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import shapeless.HList

class MethodParameterOut[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.MethodParameterOut, Labels](raw)
    with DeclarationBase[nodes.MethodParameterOut, Labels]
    with CodeAccessors[nodes.MethodParameterOut, Labels]
    with NameAccessors[nodes.MethodParameterOut, Labels]
    with OrderAccessors[nodes.MethodParameterOut, Labels]
    with LineNumberAccessors[nodes.MethodParameterOut, Labels]
    with EvalTypeAccessors[nodes.MethodParameterOut, Labels] {
  override val converter = Converter.forDomainNode[nodes.MethodParameterOut]

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): MethodParameterOut[Labels] =
    order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.gte(num: Integer)))

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo[Out](num: Int): MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.lte(num: Integer)))

  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.AST))

  def argument: Expression[Labels] =
    new Expression[Labels](raw.out(EdgeTypes.CALL_ARG_OUT))

  def asInput: MethodParameter[Labels] =
    new MethodParameter[Labels](raw.in(EdgeTypes.PARAMETER_LINK))
}
