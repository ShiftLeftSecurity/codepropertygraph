package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait ValueAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def value(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.VALUE)

  def value(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilter(NodeKeys.VALUE, value)

  def value(value: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterMultiple(NodeKeys.VALUE, value: _*)

  def valueExact(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterExact(NodeKeys.VALUE, value)

  def valueExact(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.VALUE, values: _*)

  def valueNot(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterNot(NodeKeys.VALUE, value)

  def valueNot(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.VALUE, values: _*)

}
