package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasValue, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class ValueAccessors[A <: StoredNode with HasValue](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def value(): Steps[String] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
