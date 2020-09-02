package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.HasLineNumber
import overflowdb.Node
import overflowdb.traversal.Traversal

class LineNumberAccessors[A <: Node with HasLineNumber](val traversal: Traversal[A]) extends AnyVal {

  def lineNumber: Traversal[Integer] =
    traversal.flatMap(_.lineNumber)

  def lineNumber(value: Integer): Traversal[A] =
    PropertyAccessors.filter(traversal, NodeKeys.LINE_NUMBER, value)

  def lineNumber(value: Integer*): Traversal[A] =
    PropertyAccessors.filterMultiple(traversal, NodeKeys.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): Traversal[A] =
    PropertyAccessors.filterNot(traversal, NodeKeys.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): Traversal[A] =
    PropertyAccessors.filterNotMultiple(traversal, NodeKeys.LINE_NUMBER, values: _*)

}
