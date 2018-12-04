package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import java.lang.{Boolean => JBoolean}
import shapeless.HList

trait IsExternalAccessor[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {

  def isExternal(): Steps[JBoolean, JBoolean, Labels] =
    property(NodeKeys.IS_EXTERNAL)

  def isExternal(value: JBoolean): CpgSteps[T, Labels] =
    propertyFilter(NodeKeys.IS_EXTERNAL, value)

  def isExternal(value: JBoolean*): CpgSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.IS_EXTERNAL, value: _*)

  def isExternalNot(value: JBoolean): CpgSteps[T, Labels] =
    propertyFilterNot(NodeKeys.IS_EXTERNAL, value)

  def isExternalNot(values: JBoolean*): CpgSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.IS_EXTERNAL, values: _*)

}
