package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.types.expressions.{Call, Identifier, Literal}
import io.shiftleft.semanticcpg.language.types.propertyaccessors.{NameAccessors, ValueAccessors}
import io.shiftleft.semanticcpg.language.types.structure._
import io.shiftleft.semanticcpg.language.{NodeSteps => OriginalNodeSteps}

class Tag(override val raw: GremlinScala[nodes.Tag])
    extends OriginalNodeSteps[nodes.Tag](raw)
    with NameAccessors[nodes.Tag]
    with ValueAccessors[nodes.Tag] {

  def method: Method =
    new Method(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Method])

  def methodReturn: MethodReturn =
    new MethodReturn(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_RETURN)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodReturn])

  def parameter: MethodParameter =
    new MethodParameter(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodParameterIn])

  def parameterOut: MethodParameterOut =
    new MethodParameterOut(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.METHOD_PARAMETER_OUT)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.MethodParameterOut])

  def call: Call =
    new Call(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.CALL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Call])

  def identifier: Identifier =
    new Identifier(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.IDENTIFIER)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Identifier])

  def literal: Literal =
    new Literal(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.LITERAL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Literal])

  def local: Local =
    new Local(
      raw
        .in(EdgeTypes.TAGGED_BY)
        .hasLabel(NodeTypes.LOCAL)
        .order(By((x: Vertex) => x.id))
        .cast[nodes.Local])

}
