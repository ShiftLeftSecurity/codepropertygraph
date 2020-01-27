package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._

class NamespaceBlock(raw: GremlinScala[nodes.NamespaceBlock]) extends NodeSteps[nodes.NamespaceBlock](raw) {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: NodeSteps[nodes.Namespace] =
    new NodeSteps(raw.out(EdgeTypes.REF).cast[nodes.Namespace])

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: NodeSteps[nodes.TypeDecl] =
    new NodeSteps(
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

}
