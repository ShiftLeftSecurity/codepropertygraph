package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait DependencyGroupIdAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def dependencyGroupId(): Steps[String, Labels] =
    stringProperty(NodeKeys.DEPENDENCY_GROUP_ID)

  def dependencyGroupId(value: String): NodeSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupId(value: String*): NodeSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.DEPENDENCY_GROUP_ID, value: _*)

  def dependencyGroupIdExact(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdExact(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)

  def dependencyGroupIdNot(value: String): NodeSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdNot(values: String*): NodeSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)
}
