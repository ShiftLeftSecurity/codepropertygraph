package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait OrderAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def order(): Steps[Integer, Labels] =
    property(NodeKeys.ORDER)

  def order(value: Integer): NodeSteps[T, Labels] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): NodeSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): NodeSteps[T, Labels] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot(values: Integer*): NodeSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
