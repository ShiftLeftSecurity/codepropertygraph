package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait VersionAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def version(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.VERSION)

  def version(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilter(NodeKeys.VERSION, value)

  def version(value: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VERSION, value: _*)

  def versionExact(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterExact(NodeKeys.VERSION, value)

  def versionExact(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VERSION, values: _*)

  def versionNot(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterNot(NodeKeys.VERSION, value)

  def versionNot(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VERSION, values: _*)

}
