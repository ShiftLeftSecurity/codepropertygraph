package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait PropertyAccessors[A <: StoredNode] {
  def raw: GremlinScala[A]

  def property[P](property: Key[P]): Steps[P] =
    new Steps[P](raw.value(property))

  def propertyFilter[Out, P](property: Key[P], value: P): NodeSteps[A] =
    new NodeSteps[A](raw.has(property, value))

  def propertyFilterMultiple[Out, P](property: Key[P], values: P*): NodeSteps[A] =
    if (values.nonEmpty) {
      new NodeSteps[A](raw.or(values.map { value => (trav: GremlinScala[A]) =>
        trav.has(property, value)
      }: _*))
    } else {
      new NodeSteps[A](raw.filterOnEnd(_ => false))
    }

  def propertyFilterNot[Out, P](property: Key[P], value: P): NodeSteps[A] =
    new NodeSteps[A](raw.hasNot(property, value))

  def propertyFilterNotMultiple[Out, P](property: Key[P], values: P*): NodeSteps[A] =
    if (values.nonEmpty) {
      new NodeSteps[A](raw.or(values.map { value => (trav: GremlinScala[A]) =>
        trav.hasNot(property, value)
      }: _*))
    } else {
      new NodeSteps[A](raw)
    }
}
