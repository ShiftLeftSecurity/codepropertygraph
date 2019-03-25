package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.Steps
import shapeless.HList

trait PropertyAccessors[T <: StoredNode, Labels <: HList] {
  val raw: GremlinScala.Aux[T, Labels]

  def property[P](property: Key[P]): Steps[P, Labels] =
    new Steps[P, Labels](raw.value(property))

  def propertyFilter[Out, P](property: Key[P], value: P): Steps[T, Labels] =
    new Steps[T, Labels](raw.has(property, value))

  def propertyFilterMultiple[Out, P](property: Key[P], values: P*): Steps[T, Labels] =
    if (values.nonEmpty) {
      new Steps[T, Labels](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, value)
      }: _*))
    } else {
      new Steps[T, Labels](raw.filterOnEnd(unused => false))
    }

  def propertyFilterNot[Out, P](property: Key[P], value: P): Steps[T, Labels] =
    new Steps[T, Labels](raw.hasNot(property, value))

  def propertyFilterNotMultiple[Out, P](property: Key[P], values: P*): Steps[T, Labels] =
    if (values.nonEmpty) {
      new Steps[T, Labels](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.hasNot(property, value)
      }: _*))
    } else {
      new Steps[T, Labels](raw)
    }
}
