package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

/**
  * A compilation unit
  * */
class File[A <: nodes.File](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  def typeDecl: TypeDecl[nodes.TypeDecl] =
    new TypeDecl(
      raw
        .out(EdgeTypes.AST)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

  def namespace: Namespace[nodes.Namespace] =
    new Namespace(raw.out(EdgeTypes.AST).out(EdgeTypes.REF).cast[nodes.Namespace])

  def namespaceBlock: NamespaceBlock[nodes.NamespaceBlock] =
    new NamespaceBlock(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock])

  def comment: Comment[nodes.Comment] =
    new Comment(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[nodes.Comment])

}
