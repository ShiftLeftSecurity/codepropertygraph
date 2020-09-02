package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasVersion
import overflowdb.Node
import overflowdb.traversal.Traversal

class VersionAccessors[A <: Node with HasVersion](val traversal: Traversal[A]) extends AnyVal {

  def version: Traversal[String] =
    traversal.map(_.version)

  def version(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.VERSION, value)

  def version(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.VERSION, value: _*)

  def versionExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.VERSION, value)

  def versionExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.VERSION, values: _*)

  def versionNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.VERSION, value)

  def versionNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.VERSION, values: _*)

}
