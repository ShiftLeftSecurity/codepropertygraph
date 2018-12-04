package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait CodeAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def code(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): CpgSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): CpgSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
