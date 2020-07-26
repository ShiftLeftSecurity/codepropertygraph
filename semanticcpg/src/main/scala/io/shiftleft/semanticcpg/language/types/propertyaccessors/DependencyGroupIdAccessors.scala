package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasDependencyGroupId
import overflowdb.Node
import overflowdb.traversal.Traversal

class DependencyGroupIdAccessors[A <: Node with HasDependencyGroupId](val traversal: Traversal[A]) extends AnyVal {

  def dependencyGroupId: Traversal[String] =
    traversal.flatMap(_.dependencyGroupId)

  def dependencyGroupId(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupId(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, value: _*)

  def dependencyGroupIdExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, values: _*)

  def dependencyGroupIdNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, value)

  def dependencyGroupIdNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.DEPENDENCY_GROUP_ID, values: _*)
}
