package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasDependencyGroupId
import overflowdb.Node
import overflowdb.traversal.Traversal

class DependencyGroupIdAccessors[A <: Node with HasDependencyGroupId](val traversal: Traversal[A]) extends AnyVal {

  def dependencyGroupId: Traversal[String] =
    traversal.flatMap(_.dependencyGroupId)

  def dependencyGroupId(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupId(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.DEPENDENCY_GROUP_ID, value: _*)

  def dependencyGroupIdExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.DEPENDENCY_GROUP_ID, values: _*)

  def dependencyGroupIdNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.DEPENDENCY_GROUP_ID, values: _*)
}
