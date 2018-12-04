package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait OrderAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {

  def order(): Steps[Integer, Integer, Labels] =
    property(NodeKeys.ORDER)

  def order(value: Integer): CpgSteps[T, Labels] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): CpgSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): CpgSteps[T, Labels] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot[Out](values: Integer*): CpgSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
