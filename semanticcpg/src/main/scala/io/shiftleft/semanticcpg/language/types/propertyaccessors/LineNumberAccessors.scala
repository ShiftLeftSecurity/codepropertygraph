package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait LineNumberAccessors[T <: StoredNode] extends PropertyAccessors[T] {
  def lineNumber(): Steps[Integer] =
    property(NodeKeys.LINE_NUMBER)

  def lineNumber(value: Integer): NodeSteps[T] =
    propertyFilter(NodeKeys.LINE_NUMBER, value)

  def lineNumber(value: Integer*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): NodeSteps[T] =
    propertyFilterNot(NodeKeys.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER, values: _*)

}
