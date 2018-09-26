package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait FullNameAccessors[T <: StoredNode, Labels <: HList] extends StringPropertyAccessors[T, Labels] {
  def fullName(): Steps[String, String, Labels] =
    stringProperty(NodeKeys.FULL_NAME)

  def fullName(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilter(NodeKeys.FULL_NAME, value)

  def fullName(value: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterMultiple(NodeKeys.FULL_NAME, value: _*)

  def fullNameExact(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterExact(NodeKeys.FULL_NAME, value)

  def fullNameExact(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterExactMultiple(NodeKeys.FULL_NAME, values: _*)

  def fullNameNot(value: String): Steps[T, Vertex, Labels] =
    stringPropertyFilterNot(NodeKeys.FULL_NAME, value)

  def fullNameNot(values: String*): Steps[T, Vertex, Labels] =
    stringPropertyFilterNotMultiple(NodeKeys.FULL_NAME, values: _*)

}
