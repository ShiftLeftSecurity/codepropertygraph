package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait ValueAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def value(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): CpgSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): CpgSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
