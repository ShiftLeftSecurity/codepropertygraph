package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import shapeless.HList

trait LineNumberAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def lineNumber(): Steps[Integer, Labels] =
    property(NodeKeys.LINE_NUMBER)

  def lineNumber(value: Integer): NodeSteps[T, Labels] =
    propertyFilter(NodeKeys.LINE_NUMBER, value)

  def lineNumber(value: Integer*): NodeSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): NodeSteps[T, Labels] =
    propertyFilterNot(NodeKeys.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): NodeSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER, values: _*)

}
