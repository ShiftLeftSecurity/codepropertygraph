package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.predicates.Text.textRegex
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

trait StringPropertyAccessors[T <: StoredNode] {
  val raw: GremlinScala[T]

  protected def stringProperty(property: Key[String]) =
    new Steps[String](raw.value(property))

  protected def stringPropertyFilter(property: Key[String], value: String): NodeSteps[T] =
    new NodeSteps[T](raw.has(property, textRegex(value)))

  protected def stringPropertyFilterMultiple(property: Key[String], values: String*): NodeSteps[T] =
    if (values.nonEmpty) {
      new NodeSteps[T](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, textRegex(value))
      }: _*))
    } else {
      new NodeSteps[T](raw.filterOnEnd(_ => false))
    }

  protected def stringPropertyFilterExact[Out](property: Key[String], _value: String): NodeSteps[T] =
    new NodeSteps[T](raw.has(property, _value))

  protected def stringPropertyFilterExactMultiple[Out](property: Key[String], values: String*): NodeSteps[T] =
    if (values.nonEmpty) {
      new NodeSteps[T](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, value)
      }: _*))
    } else {
      new NodeSteps[T](raw.filterOnEnd(_ => false))
    }

  protected def stringPropertyFilterNot[Out](property: Key[String], value: String): NodeSteps[T] =
    new NodeSteps[T](raw.hasNot(property, textRegex(value)))

  protected def stringPropertyFilterNotMultiple[Out](property: Key[String], values: String*): NodeSteps[T] =
    if (values.nonEmpty) {
      new NodeSteps[T](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.hasNot(property, textRegex(value))
      }: _*))
    } else {
      new NodeSteps[T](raw)
    }
}
