package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import shapeless.HList

trait MethodInstFullNameAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {

  def methodInstFullName(): Steps[String, Labels] =
    property(NodeKeys.METHOD_INST_FULL_NAME)

  def methodInstFullName(value: String): NodeSteps[T, Labels] =
    propertyFilter(NodeKeys.METHOD_INST_FULL_NAME, value)

  def methodInstFullName(value: String*): NodeSteps[T, Labels] =
    propertyFilterMultiple(NodeKeys.METHOD_INST_FULL_NAME, value: _*)

  def methodInstFullNameNot(value: String): NodeSteps[T, Labels] =
    propertyFilterNot(NodeKeys.METHOD_INST_FULL_NAME, value)

  def methodInstFullNameNot[Out](values: String*): NodeSteps[T, Labels] =
    propertyFilterNotMultiple(NodeKeys.METHOD_INST_FULL_NAME, values: _*)

}
