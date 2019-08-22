package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.{DeclarationBase, Expression}
import io.shiftleft.semanticcpg.language.types.propertyaccessors._
import shapeless.HList

class MethodParameterOut[Labels <: HList](raw: GremlinScala.Aux[nodes.MethodParameterOut, Labels])
    extends NodeSteps[nodes.MethodParameterOut, Labels](raw)
    with DeclarationBase[nodes.MethodParameterOut, Labels]
    with CodeAccessors[nodes.MethodParameterOut, Labels]
    with NameAccessors[nodes.MethodParameterOut, Labels]
    with OrderAccessors[nodes.MethodParameterOut, Labels]
    with LineNumberAccessors[nodes.MethodParameterOut, Labels]
    with EvalTypeAccessors[nodes.MethodParameterOut, Labels] {

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
    new Method[Labels](raw.in(EdgeTypes.AST).cast[nodes.Method])

  def argument: Expression[Labels] = {
    new Expression[Labels](
      raw
        .sack((_: Integer, node: nodes.MethodParameterOut) => node.value2(NodeKeys.ORDER))
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
    new MethodParameter[Labels](raw.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterIn])

  /**
    * Traverse to parameter type
    * */
  def typ: Type[Labels] =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
