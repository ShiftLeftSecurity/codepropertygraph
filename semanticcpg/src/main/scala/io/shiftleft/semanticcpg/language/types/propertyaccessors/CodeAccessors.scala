package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class CodeAccessors[A <: StoredNode](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def code(): Steps[String] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
