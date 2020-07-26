package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasOrder
import overflowdb.Node
import overflowdb.traversal.Traversal

class OrderAccessors[A <: Node with HasOrder](val traversal: Traversal[A]) extends AnyVal {

  def order: Traversal[Integer] =
    traversal.map(_.order)

  def order(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeysOdb.ORDER, value)

  def order(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeysOdb.ORDER, value: _*)

  def orderNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeysOdb.ORDER, value)

  def orderNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.ORDER, values: _*)
}
