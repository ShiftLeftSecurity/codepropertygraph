package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasArgumentIndex
import overflowdb.Node
import overflowdb.traversal.Traversal

class ArgumentIndexAccessors[A <: Node with HasArgumentIndex](val traversal: Traversal[A]) extends AnyVal {

  def argIndex: Traversal[Integer] =
    traversal.map(_.argumentIndex)

  def argIndex(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeys.ARGUMENT_INDEX, value)

  def argIndex(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeys.ARGUMENT_INDEX, value: _*)

  def argIndexNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeys.ARGUMENT_INDEX, value)

  def argIndexNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeys.ARGUMENT_INDEX, values: _*)
}
