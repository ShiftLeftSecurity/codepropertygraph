package io.shiftleft.semanticcpg.language.types.propertyaccessors

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys}
import io.shiftleft.codepropertygraph.predicates.Text.textRegex
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

class EvalTypeAccessors[NodeType <: StoredNode](val wrapped: NodeSteps[NodeType]) extends AnyVal {
  def raw: GremlinScala[NodeType] = wrapped.raw

  def evalType(): Steps[String] =
    new Steps[String](raw.out(EdgeTypes.EVAL_TYPE).out(EdgeTypes.REF).value(NodeKeys.FULL_NAME))

  def evalType(_value: String): NodeSteps[NodeType] =
    new NodeSteps[NodeType](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME, textRegex(_value))))

  def evalType(_values: String*): NodeSteps[NodeType] =
    if (_values.nonEmpty) {
      new NodeSteps[NodeType](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .asInstanceOf[GremlinScala[NodeType]]
            .or(_values.map { _value => (trav: GremlinScala[NodeType]) =>
              trav.has(NodeKeys.FULL_NAME, textRegex(_value))
            }: _*)))
    } else {
      new NodeSteps[NodeType](raw.filterOnEnd(_ => false))
    }

  def evalTypeExact(_value: String): NodeSteps[NodeType] =
    new NodeSteps[NodeType](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .has(NodeKeys.FULL_NAME, _value)))

  def evalTypeExact(_values: String*): NodeSteps[NodeType] =
    if (_values.nonEmpty) {
      new NodeSteps[NodeType](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .asInstanceOf[GremlinScala[NodeType]]
            .or(_values.map { _value => (trav: GremlinScala[NodeType]) =>
              trav.has(NodeKeys.FULL_NAME, _value)
            }: _*)))
    } else {
      new NodeSteps[NodeType](raw.filterOnEnd(_ => false))
    }

  def evalTypeNot(_value: String): NodeSteps[NodeType] =
    new NodeSteps[NodeType](
      raw.filter(
        _.out(EdgeTypes.EVAL_TYPE)
          .out(EdgeTypes.REF)
          .hasNot(NodeKeys.FULL_NAME, textRegex(_value))))

  def evalTypeNot(_values: String*): NodeSteps[NodeType] =
    if (_values.nonEmpty) {
      new NodeSteps[NodeType](
        raw.filter(
          _.out(EdgeTypes.EVAL_TYPE)
            .out(EdgeTypes.REF)
            .asInstanceOf[GremlinScala[NodeType]]
            .or(_values.map { _value => (trav: GremlinScala[NodeType]) =>
              trav.hasNot(NodeKeys.FULL_NAME, textRegex(_value))
            }: _*)))
    } else {
      new NodeSteps[NodeType](raw)
    }
}
