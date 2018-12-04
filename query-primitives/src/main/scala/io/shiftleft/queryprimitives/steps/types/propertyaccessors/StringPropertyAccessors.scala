package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala._
import gremlin.scala.dsl.Steps
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.codepropertygraph.predicates.Text.textRegex
import io.shiftleft.queryprimitives.steps.CpgSteps
import shapeless.HList

trait StringPropertyAccessors[T <: StoredNode, Labels <: HList] {
  val raw: GremlinScala[Vertex]
  implicit def marshaller: Marshallable[T]

  protected def stringProperty(property: Key[String]) =
    new Steps[String, String, Labels](raw.value(property))

  protected def stringPropertyFilter(property: Key[String], value: String): CpgSteps[T, Labels] =
    new CpgSteps[T, Labels](raw.has(property, textRegex(value)))

  protected def stringPropertyFilterMultiple(property: Key[String], values: String*): CpgSteps[T, Labels] =
    if (values.nonEmpty) {
      new CpgSteps[T, Labels](raw.or(values.map { value => trav: GremlinScala[Vertex] =>
        trav.has(property, textRegex(value))
      }: _*))
    } else {
      new CpgSteps[T, Labels](raw.filterOnEnd(unused => false))
    }

  protected def stringPropertyFilterExact[Out](property: Key[String], _value: String): CpgSteps[T, Labels] =
    new CpgSteps[T, Labels](raw.has(property, _value))

  protected def stringPropertyFilterExactMultiple[Out](property: Key[String],
                                                       values: String*): CpgSteps[T, Labels] =
    if (values.nonEmpty) {
      new CpgSteps[T, Labels](raw.or(values.map { value => trav: GremlinScala[Vertex] =>
        trav.has(property, value)
      }: _*))
    } else {
      new CpgSteps[T, Labels](raw.filterOnEnd(unused => false))
    }

  protected def stringPropertyFilterNot[Out](property: Key[String], value: String): CpgSteps[T, Labels] =
    new CpgSteps[T, Labels](raw.hasNot(property, textRegex(value)))

  protected def stringPropertyFilterNotMultiple[Out](property: Key[String], values: String*): CpgSteps[T, Labels] =
    if (values.nonEmpty) {
      new CpgSteps[T, Labels](raw.or(values.map { value => trav: GremlinScala[Vertex] =>
        trav.hasNot(property, textRegex(value))
      }: _*))
    } else {
      new CpgSteps[T, Labels](raw)
    }
}
