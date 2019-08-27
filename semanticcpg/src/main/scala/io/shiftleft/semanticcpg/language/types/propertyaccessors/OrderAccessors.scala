package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait OrderAccessors[T <: StoredNode] extends PropertyAccessors[T] {
  def order(): Steps[Integer] =
    property(NodeKeys.ORDER)

  def order(value: Integer): NodeSteps[T] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): NodeSteps[T] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot(values: Integer*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
