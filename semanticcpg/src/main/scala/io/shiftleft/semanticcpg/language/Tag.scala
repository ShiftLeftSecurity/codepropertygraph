package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.{Traversal, iterableToTraversal}

class Tag(val traversal: Traversal[nodes.Tag]) extends AnyVal {

  def member: Traversal[nodes.Member] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.MEMBER)
      .sortBy(_.id)
      .cast[nodes.Member]

  def method: Traversal[nodes.Method] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD)
      .sortBy(_.id)
      .cast[nodes.Method]

  def methodReturn: Traversal[nodes.MethodReturn] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_RETURN)
      .sortBy(_.id)
      .cast[nodes.MethodReturn]

  def parameter: Traversal[nodes.MethodParameterIn] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .sortBy(_.id)
      .cast[nodes.MethodParameterIn]

  def parameterOut: Traversal[nodes.MethodParameterOut] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_PARAMETER_OUT)
      .sortBy(_.id)
      .cast[nodes.MethodParameterOut]

  def call: Traversal[nodes.Call] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.CALL)
      .sortBy(_.id)
      .cast[nodes.Call]

  def identifier: Traversal[nodes.Identifier] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.IDENTIFIER)
      .sortBy(_.id)
      .cast[nodes.Identifier]

  def literal: Traversal[nodes.Literal] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.LITERAL)
      .sortBy(_.id)
      .cast[nodes.Literal]

  def local: Traversal[nodes.Local] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.LOCAL)
      .sortBy(_.id)
      .cast[nodes.Local]

  def file: Traversal[nodes.File] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.FILE)
      .sortBy(_.id)
      .cast[nodes.File]

}
