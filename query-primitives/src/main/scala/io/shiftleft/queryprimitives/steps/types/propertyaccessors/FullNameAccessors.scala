package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait FullNameAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {

  def fullName(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.FULL_NAME)

  def fullName(value: String): CpgSteps[T, Labels] =
    stringPropertyFilter(NodeKeys.FULL_NAME, value)

  def fullName(value: String*): CpgSteps[T, Labels] =
    stringPropertyFilterMultiple(NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterExact(NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): CpgSteps[T, Labels] =
    stringPropertyFilterNot(NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): CpgSteps[T, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.FULL_NAME, values: _*)

}
