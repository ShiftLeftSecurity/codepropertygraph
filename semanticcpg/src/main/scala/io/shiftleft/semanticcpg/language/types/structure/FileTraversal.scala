package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import overflowdb.traversal.Traversal

/**
  * A compilation unit
  * */
class FileTraversal(val traversal: Traversal[File]) extends AnyVal {

  def typeDecl: Traversal[TypeDecl] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.TYPE_DECL).cast[TypeDecl]

  def namespace: Traversal[Namespace] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.NAMESPACE_BLOCK).out(EdgeTypes.REF).cast[Namespace]

  def namespaceBlock: Traversal[NamespaceBlock] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[NamespaceBlock]

  def method: Traversal[Method] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.METHOD).cast[Method]

  def comment: Traversal[Comment] =
    traversal.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[Comment]

}

object FileTraversal {

  val UNKNOWN = "<unknown>"

}
