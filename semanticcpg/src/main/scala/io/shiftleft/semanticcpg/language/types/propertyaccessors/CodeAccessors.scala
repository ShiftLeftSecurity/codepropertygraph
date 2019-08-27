package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait CodeAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {
  def code(): Steps[String] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
