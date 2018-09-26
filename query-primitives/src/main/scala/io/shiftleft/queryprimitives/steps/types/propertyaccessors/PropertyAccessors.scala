package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala._
import gremlin.scala.dsl.{Converter, Steps}
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait PropertyAccessors[T <: StoredNode, Labels <: HList] {
  val raw: GremlinScala[Vertex]
  implicit def converter: Converter.Aux[T, Vertex]

  def property[P](property: Key[P]): Steps[P, P, Labels] = {
    implicit val c = Converter.identityConverter[P]
    new Steps[P, P, Labels](raw.value(property))
  }

  def propertyFilter[Out, P](property: Key[P], value: P): Steps[T, Vertex, Labels] =
    new Steps[T, Vertex, Labels](raw.has(property, value))

  def propertyFilterMultiple[Out, P](property: Key[P], values: P*): Steps[T, Vertex, Labels] =
    if (values.nonEmpty) {
      new Steps[T, Vertex, Labels](raw.or(values.map { value => trav: GremlinScala[Vertex] =>
        trav.has(property, value)
      }: _*))
    } else {
      new Steps[T, Vertex, Labels](raw.filterOnEnd(unused => false))
    }

  def propertyFilterNot[Out, P](property: Key[P], value: P): Steps[T, Vertex, Labels] =
    new Steps[T, Vertex, Labels](raw.hasNot(property, value))

  def propertyFilterNotMultiple[Out, P](property: Key[P], values: P*): Steps[T, Vertex, Labels] =
    if (values.nonEmpty) {
      new Steps[T, Vertex, Labels](raw.or(values.map { value => trav: GremlinScala[Vertex] =>
        trav.hasNot(property, value)
      }: _*))
    } else {
      new Steps[T, Vertex, Labels](raw)
    }
}
