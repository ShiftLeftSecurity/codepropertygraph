package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait ValueAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def value(): Steps[String, Labels] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): Steps[T, Labels] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): Steps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): Steps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): Steps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): Steps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): Steps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
