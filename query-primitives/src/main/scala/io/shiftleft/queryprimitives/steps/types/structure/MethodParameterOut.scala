package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{DeclarationBase, Expression}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import shapeless.HList

class MethodParameterOut[Labels <: HList](raw: GremlinScala.Aux[nodes.MethodParameterOutRef, Labels])
    extends NodeSteps[nodes.MethodParameterOutRef, Labels](raw)
    with DeclarationBase[nodes.MethodParameterOutRef, Labels]
    with CodeAccessors[nodes.MethodParameterOutRef, Labels]
    with NameAccessors[nodes.MethodParameterOutRef, Labels]
    with OrderAccessors[nodes.MethodParameterOutRef, Labels]
    with LineNumberAccessors[nodes.MethodParameterOutRef, Labels]
    with EvalTypeAccessors[nodes.MethodParameterOutRef, Labels] {

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
    new Method[Labels](raw.in(EdgeTypes.AST).cast[nodes.MethodRef])

  def argument: Expression[Labels] = {
    new Expression[Labels](
      raw
        .sack((sack: Integer, node: nodes.MethodParameterOutRef) => node.value2(NodeKeys.ORDER))
        .in(EdgeTypes.AST)
        .in(EdgeTypes.REF)
        .in(EdgeTypes.CALL)
        .out(EdgeTypes.AST)
        .filterWithTraverser { traverser =>
          val argumentIndex = traverser.sack[Integer]
          traverser.get.value2(NodeKeys.ARGUMENT_INDEX) == argumentIndex
        }
        .cast[nodes.Expression]
    )
  }

  def asInput: MethodParameter[Labels] =
    new MethodParameter[Labels](raw.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterInRef])

  /**
    * Traverse to parameter type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.TypeRef])
}
