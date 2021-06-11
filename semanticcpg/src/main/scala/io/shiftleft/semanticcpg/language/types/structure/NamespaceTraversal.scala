package io.shiftleft.semanticcpg.language.types.structure

import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal.Traversal

/**
  * A namespace, e.g., Java package or C# namespace
  * */
class NamespaceTraversal(val traversal: Traversal[nodes.Namespace]) extends AnyVal {

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: Traversal[nodes.TypeDecl] =
    traversal
      .in(EdgeTypes.REF)
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.TYPE_DECL)
      .cast[nodes.TypeDecl]

  /**
    * Methods defined in this namespace
    * */
  def method: Traversal[nodes.Method] =
    traversal
      .in(EdgeTypes.REF)
      .out(EdgeTypes.AST)
      .hasLabel(NodeTypes.METHOD)
      .cast[nodes.Method]

  /**
    * External namespaces - any namespaces
    * which contain one or more external type.
    * */
  def external: Traversal[nodes.Namespace] =
    traversal.where(_.typeDecl.external)

  /**
    * Internal namespaces - any namespaces
    * which contain one or more internal type
    * */
  def internal: Traversal[nodes.Namespace] =
    traversal.where(_.typeDecl.internal)

}

object NamespaceTraversal {

  val globalNamespaceName = "<global>"

}
