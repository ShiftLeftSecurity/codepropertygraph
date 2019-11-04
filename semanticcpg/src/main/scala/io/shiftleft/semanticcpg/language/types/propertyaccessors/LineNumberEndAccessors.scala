package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait LineNumberEndAccessors[T <: StoredNode] extends PropertyAccessors[T] {

  def lineNumberEnd(): Steps[Integer] =
    property(NodeKeys.LINE_NUMBER_END)

  def lineNumberEnd(value: Integer): NodeSteps[T] =
    propertyFilter(NodeKeys.LINE_NUMBER_END, value)

  def lineNumberEnd(value: Integer*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER_END, value: _*)

  def lineNumberEndNot(value: Integer): NodeSteps[T] =
    propertyFilterNot(NodeKeys.LINE_NUMBER_END, value)

  def lineNumberEndNot(values: Integer*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER_END, values: _*)

}
