package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.codepropertygraph.generated.nodes.HasLineNumber
import overflowdb.Node
import overflowdb.traversal.Traversal

class LineNumberAccessors[A <: Node with HasLineNumber](val traversal: Traversal[A]) extends AnyVal {

  def lineNumber: Traversal[Integer] =
    traversal.flatMap(_.lineNumber)

  def lineNumber(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeysOdb.LINE_NUMBER, value)

  def lineNumber(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeysOdb.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeysOdb.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeysOdb.LINE_NUMBER, values: _*)

}
