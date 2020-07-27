package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasLineNumberEnd
import overflowdb.Node
import overflowdb.traversal.Traversal

class LineNumberEndAccessors[A <: Node with HasLineNumberEnd](val traversal: Traversal[A]) extends AnyVal {

  def lineNumberEnd(): Traversal[Integer] =
    traversal.map(_.lineNumberEnd)

  def lineNumberEnd(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeysOdb.LINE_NUMBER_END, value)

  def lineNumberEnd(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeysOdb.LINE_NUMBER_END, value: _*)

  def lineNumberEndNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeysOdb.LINE_NUMBER_END, value)

  def lineNumberEndNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.LINE_NUMBER_END, values: _*)

}
