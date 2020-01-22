package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasFullName, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class FullNameAccessors[A <: StoredNode with HasFullName](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def fullName(): Steps[String] =
    stringProperty(NodeKeys.FULL_NAME)

  def fullName(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.FULL_NAME, value)

  def fullName(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.FULL_NAME, values: _*)
}
