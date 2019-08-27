package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait VersionAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {
  def version(): Steps[String] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
