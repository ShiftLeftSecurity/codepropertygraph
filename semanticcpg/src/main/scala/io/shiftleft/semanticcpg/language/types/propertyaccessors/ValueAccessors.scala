package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait ValueAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {
  def value(): Steps[String] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
