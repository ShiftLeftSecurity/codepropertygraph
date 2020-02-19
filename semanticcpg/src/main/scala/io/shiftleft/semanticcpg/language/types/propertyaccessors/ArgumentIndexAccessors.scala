package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class ArgumentIndexAccessors[NodeType <: StoredNode](wrapped: NodeSteps[NodeType]) extends PropertyAccessors[NodeType] {
  override def raw: GremlinScala[NodeType] = wrapped.raw

  def argIndex(): Steps[Integer] =
    property(NodeKeys.ARGUMENT_INDEX)

  def argIndex(value: Integer): NodeSteps[NodeType] =
    propertyFilter(NodeKeys.ARGUMENT_INDEX, value)

  def argIndex(value: Integer*): NodeSteps[NodeType] =
    propertyFilterMultiple(NodeKeys.ARGUMENT_INDEX, value: _*)

  def argIndexNot(value: Integer): NodeSteps[NodeType] =
    propertyFilterNot(NodeKeys.ARGUMENT_INDEX, value)

  def argIndexNot[Out](values: Integer*): NodeSteps[NodeType] =
    propertyFilterNotMultiple(NodeKeys.ARGUMENT_INDEX, values: _*)
}
