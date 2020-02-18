package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

/**
  * A compilation unit
  * */
class File(val wrapped: NodeSteps[nodes.File]) extends AnyVal {
  private def raw: GremlinScala[nodes.File] = wrapped.raw

  def typeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

  def namespace: NodeSteps[nodes.Namespace] =
    new NodeSteps(raw.out(EdgeTypes.AST).out(EdgeTypes.REF).cast[nodes.Namespace])

  def namespaceBlock: NodeSteps[nodes.NamespaceBlock] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock])

  def comment: NodeSteps[nodes.Comment] =
    new NodeSteps(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[nodes.Comment])

}
