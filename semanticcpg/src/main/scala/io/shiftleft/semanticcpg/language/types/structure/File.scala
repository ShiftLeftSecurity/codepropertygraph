package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language._

/**
  * A compilation unit
  * */
class File(raw: GremlinScala[nodes.File]) extends NodeSteps[nodes.File](raw) {

  def typeDecl: TypeDecl =
    new TypeDecl(
      raw
        .out(EdgeTypes.AST)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

  def namespace: Namespace =
    new Namespace(raw.out(EdgeTypes.AST).out(EdgeTypes.REF).cast[nodes.Namespace])

  def namespaceBlock: NamespaceBlock =
    new NamespaceBlock(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock])

  def comment: Comment =
    new Comment(raw.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[nodes.Comment])

}
