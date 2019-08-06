package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait VersionAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def version(): Steps[String, Labels] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
