package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasCanonicalName
import overflowdb.Node
import overflowdb.traversal.Traversal

class CanonicalNameAccessors[A <: Node with HasCanonicalName](val traversal: Traversal[A]) extends AnyVal {

  def canonicalName: Traversal[String] =
    traversal.map(_.canonicalName)

  def canonicalName(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.CANONICAL_NAME, value)

  def canonicalName(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.CANONICAL_NAME, value: _*)

  def canonicalNameExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.CANONICAL_NAME, value)

  def canonicalNameExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.CANONICAL_NAME, values: _*)

  def canonicalNameNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.CANONICAL_NAME, value)

  def canonicalNameNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.CANONICAL_NAME, values: _*)

}
