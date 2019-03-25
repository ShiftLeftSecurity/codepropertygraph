package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait OrderAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def order(): Steps[Integer, Labels] =
    property(NodeKeys.ORDER)

  def order(value: Integer): Steps[T, Labels] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): Steps[T, Labels] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): Steps[T, Labels] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot[Out](values: Integer*): Steps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
