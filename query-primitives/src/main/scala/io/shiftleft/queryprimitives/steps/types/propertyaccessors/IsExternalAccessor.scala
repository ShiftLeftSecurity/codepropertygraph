package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import java.lang.{Boolean => JBoolean}
import shapeless.HList

trait IsExternalAccessor[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def isExternal(): Steps[JBoolean, Labels] =
    property(NodeKeys.IS_EXTERNAL)

  def isExternal(value: JBoolean): Steps[T, Labels] =
    propertyFilter(NodeKeys.IS_EXTERNAL, value)

  def isExternal(value: JBoolean*): Steps[T, Labels] =
    propertyFilterMultiple(NodeKeys.IS_EXTERNAL, value: _*)

  def isExternalNot(value: JBoolean): Steps[T, Labels] =
    propertyFilterNot(NodeKeys.IS_EXTERNAL, value)

  def isExternalNot(values: JBoolean*): Steps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.IS_EXTERNAL, values: _*)

}
