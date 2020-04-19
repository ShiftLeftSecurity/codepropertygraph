package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes, nodes}
import io.shiftleft.semanticcpg.Traversal
import io.shiftleft.semanticcpg.language._

/**
  * Formal method input parameter
  * */
@Traversal(elementType = classOf[nodes.MethodParameterIn])
class MethodParameter(val wrapped: NodeSteps[nodes.MethodParameterIn]) extends AnyVal {
  private def raw: GremlinScala[nodes.MethodParameterIn] = wrapped.raw

  /**
    * Traverse to all `num`th parameters
    * */
  def index(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.order(num)

  /**
    * Traverse to all parameters with index greater or equal than `num`
    * */
  def indexFrom(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.where(_.order >= num)

  /**
    * Traverse to all parameters with index smaller or equal than `num`
    * */
  def indexTo(num: Int): NodeSteps[nodes.MethodParameterIn] =
    wrapped.where(_.order <= num)

  /**
    * Traverse to method associated with this formal parameter
    * */
  def method: NodeSteps[nodes.Method] =
    new NodeSteps(raw.in(EdgeTypes.AST).cast[nodes.Method])

  /**
    * Traverse to arguments (actual parameters) associated with this formal parameter
    * */
  def argument(): NodeSteps[nodes.Expression] = {
    new NodeSteps(
      raw
        .sack((_: Integer, node: nodes.MethodParameterIn) => node.value2(NodeKeys.ORDER))
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

  /**
    * Traverse to corresponding formal output parameter
    * */
  def asOutput: NodeSteps[nodes.MethodParameterOut] =
    new NodeSteps(raw.out(EdgeTypes.PARAMETER_LINK).cast[nodes.MethodParameterOut])

  /**
    * Places (identifier) where this parameter is being referenced
    * */
  def referencingIdentifiers: NodeSteps[nodes.Identifier] =
    new NodeSteps(raw.in(EdgeTypes.REF).hasLabel(NodeTypes.IDENTIFIER).cast[nodes.Identifier])

  /**
    * Traverse to parameter type
    * */
  def typ: NodeSteps[nodes.Type] =
    new NodeSteps(raw.out(EdgeTypes.EVAL_TYPE).cast[nodes.Type])

}
