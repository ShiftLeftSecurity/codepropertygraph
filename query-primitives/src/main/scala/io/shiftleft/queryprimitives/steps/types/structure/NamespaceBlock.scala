package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

class NamespaceBlock[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.NamespaceBlock, Labels](raw)
    with NameAccessors[nodes.NamespaceBlock, Labels] {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.REF))

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](raw.out(EdgeTypes.AST).hasLabel(NodeTypes.TYPE_DECL))
}
