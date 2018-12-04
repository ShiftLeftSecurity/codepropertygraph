package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait LineNumberAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {

  def lineNumber(): Steps[Integer, Integer, Labels] =
    property(NodeKeys.LINE_NUMBER)

  def lineNumber(value: Integer): CpgSteps[T, Labels] =
    propertyFilter(NodeKeys.LINE_NUMBER, value)

  def lineNumber(value: Integer*): CpgSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.LINE_NUMBER, value: _*)

  def lineNumberNot(value: Integer): CpgSteps[T, Labels] =
    propertyFilterNot(NodeKeys.LINE_NUMBER, value)

  def lineNumberNot(values: Integer*): CpgSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.LINE_NUMBER, values: _*)

}
