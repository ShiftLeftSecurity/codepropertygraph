package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import overflowdb.traversal.Traversal

class NamespaceBlock(val traversal: Traversal[nodes.NamespaceBlock]) extends AnyVal {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: Traversal[nodes.Namespace] =
    traversal.out(EdgeTypes.REF).cast[nodes.Namespace]

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: Traversal[nodes.TypeDecl] =
      traversal
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl]

}
