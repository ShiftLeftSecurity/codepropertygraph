package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala.Vertex
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait OrderAccessors[T <: StoredNode, Labels <: HList] extends PropertyAccessors[T, Labels] {
  def order(): Steps[Integer, Integer, Labels] =
    property(NodeKeys.ORDER)

  def order(value: Integer): Steps[T, Vertex, Labels] =
    propertyFilter(NodeKeys.ORDER, value)

  def order(value: Integer*): Steps[T, Vertex, Labels] =
    propertyFilterMultiple(NodeKeys.ORDER, value: _*)

  def orderNot(value: Integer): Steps[T, Vertex, Labels] =
    propertyFilterNot(NodeKeys.ORDER, value)

  def orderNot[Out](values: Integer*): Steps[T, Vertex, Labels] =
    propertyFilterNotMultiple(NodeKeys.ORDER, values: _*)
}
