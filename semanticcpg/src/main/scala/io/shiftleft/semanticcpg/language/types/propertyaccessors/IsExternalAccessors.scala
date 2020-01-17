package io.shiftleft.semanticcpg.language.types.propertyaccessors

import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}
import java.lang.{Boolean => JBoolean}

import gremlin.scala.GremlinScala

class IsExternalAccessors[A <: StoredNode](steps: Steps[A]) extends PropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def isExternal(): Steps[JBoolean] =
    property(NodeKeys.IS_EXTERNAL)

  def isExternal(value: JBoolean): NodeSteps[A] =
    propertyFilter(NodeKeys.IS_EXTERNAL, value)

  def isExternal(value: JBoolean*): NodeSteps[A] =
    propertyFilterMultiple(NodeKeys.IS_EXTERNAL, value: _*)

  def isExternalNot(value: JBoolean): NodeSteps[A] =
    propertyFilterNot(NodeKeys.IS_EXTERNAL, value)

  def isExternalNot(values: JBoolean*): NodeSteps[A] =
    propertyFilterNotMultiple(NodeKeys.IS_EXTERNAL, values: _*)

}
