package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.Traversal

class Tag(val traversal: Traversal[nodes.Tag]) extends AnyVal {

  def member: Traversal[nodes.Member] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.MEMBER)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Member]

  def method: Traversal[nodes.Method] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Method]

  def methodReturn: Traversal[nodes.MethodReturn] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_RETURN)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.MethodReturn]

  def parameter: Traversal[nodes.MethodParameterIn] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_PARAMETER_IN)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.MethodParameterIn]

  def parameterOut: Traversal[nodes.MethodParameterOut] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.METHOD_PARAMETER_OUT)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.MethodParameterOut]

  def call: Traversal[nodes.Call] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.CALL)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Call]

  def identifier: Traversal[nodes.Identifier] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.IDENTIFIER)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Identifier]

  def literal: Traversal[nodes.Literal] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.LITERAL)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Literal]

  def local: Traversal[nodes.Local] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.LOCAL)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.Local]

  def file: Traversal[nodes.File] =
    traversal
      .in(EdgeTypes.TAGGED_BY)
      .hasLabel(NodeTypes.FILE)
      .l
      .sortBy(_.id)
      .to(Traversal) //TODO MP add orderBy/sortBy step
      .cast[nodes.File]

}
