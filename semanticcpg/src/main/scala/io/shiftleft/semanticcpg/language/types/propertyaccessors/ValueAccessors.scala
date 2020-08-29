package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasValue
import overflowdb.Node
import overflowdb.traversal.Traversal

class ValueAccessors[A <: Node with HasValue](val traversal: Traversal[A]) extends AnyVal {

  def value: Traversal[String] =
    traversal.map(_.value)

  def value(value: String): Traversal[A] =
    StringPropertyAccessors.filter(traversal, NodeKeys.VALUE, value)

  def value(value: String*): Traversal[A] =
    StringPropertyAccessors.filterMultiple(traversal, NodeKeys.VALUE, value: _*)

  def valueExact(value: String): Traversal[A] =
    StringPropertyAccessors.filterExact(traversal, NodeKeys.VALUE, value)

  def valueExact(values: String*): Traversal[A] =
    StringPropertyAccessors.filterExactMultiple(traversal, NodeKeys.VALUE, values: _*)

  def valueNot(value: String): Traversal[A] =
    StringPropertyAccessors.filterNot(traversal, NodeKeys.VALUE, value)

  def valueNot(values: String*): Traversal[A] =
    StringPropertyAccessors.filterNotMultiple(traversal, NodeKeys.VALUE, values: _*)

}
