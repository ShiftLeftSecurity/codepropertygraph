package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasFullName
import io.shiftleft.semanticcpg.language.Steps
import overflowdb.Node
import overflowdb.traversal.Traversal

class FullNameAccessors[A <: Node with HasFullName](val traversal: Traversal[A]) extends AnyVal {

  def fullName: Traversal[String] =
    traversal.map(_.fullName)

  def fullName(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.FULL_NAME, value)

  def fullName(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.FULL_NAME, values: _*)
}
