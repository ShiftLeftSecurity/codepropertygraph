package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.Traversal

/**
  * A compilation unit
  * */
class File(val traversal: Traversal[nodes.File]) extends AnyVal {

  def typeDecl: Traversal[nodes.TypeDecl] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.TYPE_DECL).cast[nodes.TypeDecl]

  def namespace: Traversal[nodes.Namespace] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.NAMESPACE_BLOCK).out(EdgeTypes.REF).cast[nodes.Namespace]

  def namespaceBlock: Traversal[nodes.NamespaceBlock] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.NAMESPACE_BLOCK).cast[nodes.NamespaceBlock]

  def method: Traversal[nodes.Method] =
    traversal.in(EdgeTypes.SOURCE_FILE).hasLabel(NodeTypes.METHOD).cast[nodes.Method]

  def comment: Traversal[nodes.Comment] =
    traversal.out(EdgeTypes.AST).hasLabel(NodeTypes.COMMENT).cast[nodes.Comment]

}
