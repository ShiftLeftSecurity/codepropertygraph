package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait PropertyAccessors[T <: StoredNode] {
  val raw: GremlinScala[T]

  def property[P](property: Key[P]): Steps[P] =
    new Steps[P](raw.value(property))

  def propertyFilter[Out, P](property: Key[P], value: P): NodeSteps[T] =
    new NodeSteps[T](raw.has(property, value))

  def propertyFilterMultiple[Out, P](property: Key[P], values: P*): NodeSteps[T] =
    if (values.nonEmpty) {
      new NodeSteps[T](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, value)
      }: _*))
    } else {
      new NodeSteps[T](raw.filterOnEnd(_ => false))
    }

  def propertyFilterNot[Out, P](property: Key[P], value: P): NodeSteps[T] =
    new NodeSteps[T](raw.hasNot(property, value))

  def propertyFilterNotMultiple[Out, P](property: Key[P], values: P*): NodeSteps[T] =
    if (values.nonEmpty) {
      new NodeSteps[T](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.hasNot(property, value)
      }: _*))
    } else {
      new NodeSteps[T](raw)
    }
}
