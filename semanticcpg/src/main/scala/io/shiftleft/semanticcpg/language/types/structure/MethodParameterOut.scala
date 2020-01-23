package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.expressions.generalizations.Expression
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

class MethodParameterOut(raw: GremlinScala[nodes.MethodParameterOut])
    extends NodeSteps[nodes.MethodParameterOut](raw)
    with EvalTypeAccessors[nodes.MethodParameterOut] {

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): MethodParameterOut =
    this.order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): MethodParameterOut =
    new MethodParameterOut(raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.gte(num: Integer)))

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo[Out](num: Int): MethodParameterOut =
    new MethodParameterOut(raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.lte(num: Integer)))

  def method: Method =
    new Method(raw.in(EdgeTypes.AST).cast[nodes.Method])

  def argument: Expression = {
    new Expression(
      raw
        .sack((_: Integer, node: nodes.MethodParameterOut) => node.value2(NodeKeys.ORDER))
        .in(EdgeTypes.AST)
        .in(EdgeTypes.CALL)
        .out(EdgeTypes.ARGUMENT)
        .filterWithTraverser { traverser =>
          val argumentIndex = traverser.sack[Integer]
          traverser.get.value2(NodeKeys.ARGUMENT_INDEX) == argumentIndex
        }
        .cast[nodes.Expression]
    )
  }

  def asInput: MethodParameter =
    new MethodParameter(raw.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterIn])

  /**
    * Traverse to parameter type
    * */
  def typ: Type =
    new Type(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
