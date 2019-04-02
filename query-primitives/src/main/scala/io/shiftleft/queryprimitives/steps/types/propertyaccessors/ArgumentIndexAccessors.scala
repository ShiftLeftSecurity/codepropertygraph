package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait ArgumentIndexAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def argIndex(): Steps[Integer, Labels] =
    property(NodeKeys.ARGUMENT_INDEX)

  def argIndex(value: Integer): Steps[T, Labels] =
    propertyFilter(NodeKeys.ARGUMENT_INDEX, value)

  def argIndex(value: Integer*): Steps[T, Labels] =
    propertyFilterMultiple(NodeKeys.ARGUMENT_INDEX, value: _*)

  def argIndexNot(value: Integer): Steps[T, Labels] =
    propertyFilterNot(NodeKeys.ARGUMENT_INDEX, value)

  def argIndexNot[Out](values: Integer*): Steps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.ARGUMENT_INDEX, values: _*)
}
