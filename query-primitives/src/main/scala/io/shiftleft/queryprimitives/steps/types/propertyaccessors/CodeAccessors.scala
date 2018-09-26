package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait CodeAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def code(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.CODE)

  def code(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilter(NodeKeys.CODE, value)

  def code(value: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterMultiple(NodeKeys.CODE, value: _*)

  def codeExact(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterExact(NodeKeys.CODE, value)

  def codeExact(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.CODE, values: _*)

  def codeNot(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterNot(NodeKeys.CODE, value)

  def codeNot(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.CODE, values: _*)

}
