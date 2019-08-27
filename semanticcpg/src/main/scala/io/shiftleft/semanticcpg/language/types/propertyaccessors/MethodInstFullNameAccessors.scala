package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait MethodInstFullNameAccessors[T <: StoredNode] extends PropertyAccessors[T] {

  def methodInstFullName(): Steps[String] =
    property(NodeKeys.METHOD_INST_FULL_NAME)

  def methodInstFullName(value: String): NodeSteps[T] =
    propertyFilter(NodeKeys.METHOD_INST_FULL_NAME, value)

  def methodInstFullName(value: String*): NodeSteps[T] =
    propertyFilterMultiple(NodeKeys.METHOD_INST_FULL_NAME, value: _*)

  def methodInstFullNameNot(value: String): NodeSteps[T] =
    propertyFilterNot(NodeKeys.METHOD_INST_FULL_NAME, value)

  def methodInstFullNameNot[Out](values: String*): NodeSteps[T] =
    propertyFilterNotMultiple(NodeKeys.METHOD_INST_FULL_NAME, values: _*)

}
