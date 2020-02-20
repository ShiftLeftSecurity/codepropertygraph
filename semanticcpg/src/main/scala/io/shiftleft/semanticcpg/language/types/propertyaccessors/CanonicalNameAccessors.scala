package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class CanonicalNameAccessors[A <: StoredNode](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def canonicalName(): Steps[String] =
    stringProperty(NodeKeys.CANONICAL_NAME)

  def canonicalName(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.CANONICAL_NAME, value)

  def canonicalName(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.CANONICAL_NAME, value: _*)

  def canonicalNameExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.CANONICAL_NAME, value)

  def canonicalNameExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.CANONICAL_NAME, values: _*)

  def canonicalNameNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.CANONICAL_NAME, value)

  def canonicalNameNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.CANONICAL_NAME, values: _*)

}
