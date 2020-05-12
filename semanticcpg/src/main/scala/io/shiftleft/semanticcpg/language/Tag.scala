package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.types.expressions.{Call, IdentifierTrav, Literal}
import io.shiftleft.semanticcpg.language.types.structure._

class Tag(val wrapped: NodeSteps[nodes.Tag]) extends AnyVal {
  private def raw: GremlinScala[nodes.Tag] = wrapped.raw

  def member: NodeSteps[nodes.Member] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.MEMBER)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Member]
    )

  def method: NodeSteps[nodes.Method] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Method])

  def methodReturn: NodeSteps[nodes.MethodReturn] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodReturn])

  def parameter: NodeSteps[nodes.MethodParameterIn] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodParameterIn])

  def parameterOut: NodeSteps[nodes.MethodParameterOut] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_PARAMETER_OUT)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodParameterOut])

  def call: NodeSteps[nodes.Call] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.CALL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Call])

  def identifier: NodeSteps[nodes.Identifier] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.IDENTIFIER)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Identifier])

  def literal: NodeSteps[nodes.Literal] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.LITERAL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Literal])

  def local: NodeSteps[nodes.Local] =
    new NodeSteps(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.LOCAL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Local])

}
