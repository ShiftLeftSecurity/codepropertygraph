package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait FullNameAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {
  def fullName(): Steps[String] =
    stringProperty(NodeKeys.FULL_NAME)

  def fullName(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.FULL_NAME, value)

  def fullName(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.FULL_NAME, values: _*)

}
