package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasArgumentIndex, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class ArgumentIndexAccessors[A <: StoredNode with HasArgumentIndex](steps: Steps[A]) extends PropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def argIndex(): Steps[Integer] =
    property(NodeKeys.ARGUMENT_INDEX)

  def argIndex(value: Integer): NodeSteps[A] =
    propertyFilter(NodeKeys.ARGUMENT_INDEX, value)

  def argIndex(value: Integer*): NodeSteps[A] =
    propertyFilterMultiple(NodeKeys.ARGUMENT_INDEX, value: _*)

  def argIndexNot(value: Integer): NodeSteps[A] =
    propertyFilterNot(NodeKeys.ARGUMENT_INDEX, value)

  def argIndexNot[Out](values: Integer*): NodeSteps[A] =
    propertyFilterNotMultiple(NodeKeys.ARGUMENT_INDEX, values: _*)
}
