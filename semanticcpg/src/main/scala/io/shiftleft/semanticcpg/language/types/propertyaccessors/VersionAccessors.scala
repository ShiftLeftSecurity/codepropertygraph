package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasVersion
import overflowdb.Node
import overflowdb.traversal.Traversal

class VersionAccessors[A <: Node with HasVersion](val traversal: Traversal[A]) extends AnyVal {

  def version: Traversal[String] =
    traversal.map(_.version)

  def version(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeysOdb.VERSION, value)

  def version(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeysOdb.VERSION, value: _*)

  def versionExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeysOdb.VERSION, value)

  def versionExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeysOdb.VERSION, values: _*)

  def versionNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeysOdb.VERSION, value)

  def versionNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.VERSION, values: _*)

}
