package io.shiftleft.queryprimitives.steps.types.propertyaccessors

import gremlin.scala._
import gremlin.scala.dsl.{Converter, Steps}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.predicates.Text.textRegex
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import shapeless.HList

trait EvalTypeAccessors[T <: StoredNode, Labels <: HList] {
  def raw: GremlinScala[Vertex]
  implicit def converter: Converter.Aux[T, Vertex]

  def evalType(): Steps[String, String, Labels] =
    new Steps[String, String, Labels](raw.out(EdgeTypes.EVAL_TYPE).out(EdgeTypes.REF).value(NodeKeys.FULL_NAME))

  def evalType(_value: String): Steps[T, Vertex, Labels] =
    new Steps[T, Vertex, Labels](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME, textRegex(_value))))

  def evalType(_values: String*): Steps[T, Vertex, Labels] =
    if (_values.nonEmpty) {
      new Steps[T, Vertex, Labels](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .or(_values.map { _value => trav: GremlinScala[Vertex] =>
              trav.has(NodeKeys.FULL_NAME, textRegex(_value))
            }: _*)))
    } else {
      new Steps[T, Vertex, Labels](raw.filterOnEnd(unused => false))
    }

  def evalTypeExact(_value: String): Steps[T, Vertex, Labels] =
    new Steps[T, Vertex, Labels](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME, _value)))

  def evalTypeExact(_values: String*): Steps[T, Vertex, Labels] =
    if (_values.nonEmpty) {
      new Steps[T, Vertex, Labels](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .or(_values.map { _value => trav: GremlinScala[Vertex] =>
              trav.has(NodeKeys.FULL_NAME, _value)
            }: _*)))
    } else {
      new Steps[T, Vertex, Labels](raw.filterOnEnd(unused => false))
    }

  def evalTypeNot(_value: String): Steps[T, Vertex, Labels] =
    new Steps[T, Vertex, Labels](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .hasNot(NodeKeys.FULL_NAME, textRegex(_value))))

  def evalTypeNot(_values: String*): Steps[T, Vertex, Labels] =
    if (_values.nonEmpty) {
      new Steps[T, Vertex, Labels](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .or(_values.map { _value => trav: GremlinScala[Vertex] =>
              trav.hasNot(NodeKeys.FULL_NAME, textRegex(_value))
            }: _*)))
    } else {
      new Steps[T, Vertex, Labels](raw)
    }
}
