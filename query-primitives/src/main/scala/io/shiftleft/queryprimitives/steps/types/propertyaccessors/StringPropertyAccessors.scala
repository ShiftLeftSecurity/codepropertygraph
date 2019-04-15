package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.predicates.Text.textRegex
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
import shapeless.HList

trait StringPropertyAccessors[T <: StoredNode, Labels <: HList] {
  val raw: GremlinScala.Aux[T, Labels]

  protected def stringProperty(property: Key[String]) =
    new Steps[String, Labels](raw.value(property))

  protected def stringPropertyFilter(property: Key[String], value: String): NodeSteps[T, Labels] =
    new NodeSteps[T, Labels](raw.has(property, textRegex(value)))

  protected def stringPropertyFilterMultiple(property: Key[String], values: String*): NodeSteps[T, Labels] =
    if (values.nonEmpty) {
      new NodeSteps[T, Labels](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, textRegex(value))
      }: _*))
    } else {
      new NodeSteps[T, Labels](raw.filterOnEnd(unused => false))
    }

  protected def stringPropertyFilterExact[Out](property: Key[String], _value: String): NodeSteps[T, Labels] =
    new NodeSteps[T, Labels](raw.has(property, _value))

  protected def stringPropertyFilterExactMultiple[Out](property: Key[String], values: String*): NodeSteps[T, Labels] =
    if (values.nonEmpty) {
      new NodeSteps[T, Labels](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.has(property, value)
      }: _*))
    } else {
      new NodeSteps[T, Labels](raw.filterOnEnd(unused => false))
    }

  protected def stringPropertyFilterNot[Out](property: Key[String], value: String): NodeSteps[T, Labels] =
    new NodeSteps[T, Labels](raw.hasNot(property, textRegex(value)))

  protected def stringPropertyFilterNotMultiple[Out](property: Key[String], values: String*): NodeSteps[T, Labels] =
    if (values.nonEmpty) {
      new NodeSteps[T, Labels](raw.or(values.map { value => (trav: GremlinScala[T]) =>
        trav.hasNot(property, textRegex(value))
      }: _*))
    } else {
      new NodeSteps[T, Labels](raw)
    }
}
