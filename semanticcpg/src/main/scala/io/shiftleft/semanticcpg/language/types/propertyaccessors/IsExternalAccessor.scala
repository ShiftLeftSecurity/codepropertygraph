package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import java.lang.{Boolean => JBoolean}

trait IsExternalAccessor[T <: StoredNode] extends PropertyAccessors[T] {
  def isExternal(): Steps[JBoolean] =
    property(NodeKeys.IS_EXTERNAL)

  def isExternal(value: JBoolean): NodeSteps[T] =
    propertyFilter(NodeKeys.IS_EXTERNAL, value)

  def isExternal(value: JBoolean*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.IS_EXTERNAL, value: _*)

  def isExternalNot(value: JBoolean): NodeSteps[T] =
    propertyFilterNot(NodeKeys.IS_EXTERNAL, value)

  def isExternalNot(values: JBoolean*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.IS_EXTERNAL, values: _*)

}
