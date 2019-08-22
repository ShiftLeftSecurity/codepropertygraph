package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import shapeless.HList

trait ValueAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def value(): Steps[String, Labels] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
