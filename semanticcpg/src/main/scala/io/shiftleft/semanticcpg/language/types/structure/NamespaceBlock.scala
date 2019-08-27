package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors.NameAccessors

class NamespaceBlock(raw: GremlinScala[nodes.NamespaceBlock])
    extends NodeSteps[nodes.NamespaceBlock](raw)
    with NameAccessors[nodes.NamespaceBlock] {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: Namespace =
    new Namespace(raw.out(EdgeTypes.REF).cast[nodes.Namespace])

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl =
    new TypeDecl(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

}
