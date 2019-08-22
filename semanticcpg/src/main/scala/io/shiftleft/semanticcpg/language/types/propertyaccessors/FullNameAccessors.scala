package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import shapeless.HList

trait FullNameAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def fullName(): Steps[String, Labels] =
    stringProperty(NodeKeys.FULL_NAME)

  def fullName(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.FULL_NAME, value)

  def fullName(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.FULL_NAME, values: _*)

}
