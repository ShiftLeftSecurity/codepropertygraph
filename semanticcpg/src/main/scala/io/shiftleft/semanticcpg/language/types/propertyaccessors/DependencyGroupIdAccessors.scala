package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait DependencyGroupIdAccessors[T <: StoredNode] extends StringPropertyAccessors[T] {
  def dependencyGroupId(): Steps[String] =
    stringProperty(NodeKeys.DEPENDENCY_GROUP_ID)

  def dependencyGroupId(value: String): NodeSteps[T] =
    stringPropertyFilter(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupId(value: String*): NodeSteps[T] =
    stringPropertyFilterMultiple(NodeKeys.DEPENDENCY_GROUP_ID, value: _*)

  def dependencyGroupIdExact(value: String): NodeSteps[T] =
    stringPropertyFilterExact(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdExact(values: String*): NodeSteps[T] =
    stringPropertyFilterExactMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)

  def dependencyGroupIdNot(value: String): NodeSteps[T] =
    stringPropertyFilterNot(NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdNot(values: String*): NodeSteps[T] =
    stringPropertyFilterNotMultiple(NodeKeys.DEPENDENCY_GROUP_ID, values: _*)
}
