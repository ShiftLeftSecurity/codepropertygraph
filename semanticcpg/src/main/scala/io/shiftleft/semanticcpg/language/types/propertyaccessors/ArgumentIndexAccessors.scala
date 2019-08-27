package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait ArgumentIndexAccessors[T <: StoredNode] extends PropertyAccessors[T] {
  def argIndex(): Steps[Integer] =
    property(NodeKeys.ARGUMENT_INDEX)

  def argIndex(value: Integer): NodeSteps[T] =
    propertyFilter(NodeKeys.ARGUMENT_INDEX, value)

  def argIndex(value: Integer*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.ARGUMENT_INDEX, value: _*)

  def argIndexNot(value: Integer): NodeSteps[T] =
    propertyFilterNot(NodeKeys.ARGUMENT_INDEX, value)

  def argIndexNot[Out](values: Integer*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.ARGUMENT_INDEX, values: _*)
}
