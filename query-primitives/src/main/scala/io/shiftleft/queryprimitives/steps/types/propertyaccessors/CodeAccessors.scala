package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait CodeAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def code(): Steps[String, Labels] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
