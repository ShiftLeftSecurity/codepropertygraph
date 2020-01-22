package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasOrder, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class OrderAccessors[A <: StoredNode with HasOrder](steps: Steps[A]) extends PropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def order(): Steps[Integer] =
    property(NodeKeys.ORDER)

  def order(value: Integer): NodeSteps[A] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): NodeSteps[A] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): NodeSteps[A] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot(values: Integer*): NodeSteps[A] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
