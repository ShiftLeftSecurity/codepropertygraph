package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import io.shiftleft.queryprimitives.steps.types.expressions.generalizations.{DeclarationBase, Expression}
import io.shiftleft.queryprimitives.steps.types.propertyaccessors._
import shapeless.HList

/**
  * Formal method input parameter
  * */
class MethodParameter[Labels <: HList](raw: GremlinScala.Aux[nodes.MethodParameterIn, Labels])
    extends NodeSteps[nodes.MethodParameterIn, Labels](raw)
    with DeclarationBase[nodes.MethodParameterIn, Labels]
    with CodeAccessors[nodes.MethodParameterIn, Labels]
    with NameAccessors[nodes.MethodParameterIn, Labels]
    with OrderAccessors[nodes.MethodParameterIn, Labels]
    with LineNumberAccessors[nodes.MethodParameterIn, Labels]
    with EvalTypeAccessors[nodes.MethodParameterIn, Labels] {

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): MethodParameter[Labels] =
    order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index (that's how java2cpg generates it) */
  def indexFrom(num: Int): MethodParameter[Labels] =
    new MethodParameter[Labels](raw.has(NodeKeys.METHOD_PARAMETER_IN.ORDER, P.gte(num: Integer)))

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo(num: Int): MethodParameter[Labels] =
    new MethodParameter[Labels](raw.has(NodeKeys.METHOD_PARAMETER_IN.ORDER, P.lte(num: Integer)))

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: Method[Labels] =
    new Method[Labels](raw.in(EdgeTypes.AST).cast[nodes.Method])

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument() = {
    new Expression[Labels](
      raw
        .sack((sack: Integer, node: nodes.MethodParameterIn) => node.value2(NodeKeys.ORDER))
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

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: MethodParameterOut[Labels] =
    new MethodParameterOut[Labels](raw.out(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterOut])

  /**
    * Traverse to parameter type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
