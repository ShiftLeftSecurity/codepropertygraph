package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.{HasVersion, StoredNode}
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class VersionAccessors[A <: StoredNode with HasVersion](steps: Steps[A]) extends StringPropertyAccessors[A] {
  override val raw: GremlinScala[A] = steps.raw

  def version(): Steps[String] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): NodeSteps[A] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): NodeSteps[A] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): NodeSteps[A] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): NodeSteps[A] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): NodeSteps[A] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): NodeSteps[A] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
