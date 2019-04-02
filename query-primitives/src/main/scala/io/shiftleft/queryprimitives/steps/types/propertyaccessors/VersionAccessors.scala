package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait VersionAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def version(): Steps[String, Labels] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): Steps[T, Labels] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): Steps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): Steps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): Steps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): Steps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): Steps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
