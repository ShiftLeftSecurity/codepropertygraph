package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait CodeAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def code(): Steps[String, Labels] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): Steps[T, Labels] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): Steps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): Steps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): Steps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): Steps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): Steps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
