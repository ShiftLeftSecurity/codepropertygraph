package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.propertyaccessors.NameAccessors
import shapeless.HList

class NamespaceBlock[Labels <: HList](raw: GremlinScala.Aux[nodes.NamespaceBlock, Labels])
    extends NodeSteps[nodes.NamespaceBlock, Labels](raw)
    with NameAccessors[nodes.NamespaceBlock, Labels] {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.REF).cast[nodes.Namespace])

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDecl])

}
