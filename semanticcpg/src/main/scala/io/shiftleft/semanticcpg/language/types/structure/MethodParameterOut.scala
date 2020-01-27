package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors._

class MethodParameterOut[A <: nodes.MethodParameterOut](raw: GremlinScala[A])
    extends NodeSteps[A](raw) with EvalTypeAccessors[A] {

  /* method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def index(num: Int): NodeSteps[A] =
    this.order(num)

  /* get all parameters from (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexFrom(num: Int): NodeSteps[A] =
    new NodeSteps(raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.gte(num: Integer)))

  /* get all parameters up to (and including)
   * method parameter indexes are  based, i.e. first parameter has index  (that's how java2cpg generates it) */
  def indexTo[Out](num: Int): NodeSteps[A] =
    new NodeSteps(raw.has(NodeKeys.METHOD_PARAMETER_OUT.ORDER, P.lte(num: Integer)))

  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Method])

  def argument: NodeSteps[nodes.Expression] = {
    new NodeSteps(
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

  def asInput: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(raw.in(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterIn])

  /**
    * Traverse to parameter type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])
}
