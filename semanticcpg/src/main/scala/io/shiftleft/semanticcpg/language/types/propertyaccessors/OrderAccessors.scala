package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasOrder
import overflowdb.Node
import overflowdb.traversal.Traversal

class OrderAccessors[A <: Node with HasOrder](val traversal: Traversal[A]) extends AnyVal {

  def order: Traversal[Integer] =
    traversal.map(_.order)

  def order(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeys.ORDER, value)

  def order(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeys.ORDER, value)

  def orderNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeys.ORDER, values: _*)
}
