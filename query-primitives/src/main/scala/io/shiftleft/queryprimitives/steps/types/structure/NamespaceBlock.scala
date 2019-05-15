package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

class NamespaceBlock[Labels <: HList](raw: GremlinScala.Aux[nodes.NamespaceBlockRef, Labels])
    extends NodeSteps[nodes.NamespaceBlockRef, Labels](raw)
    with NameAccessors[nodes.NamespaceBlockRef, Labels] {

  /**
    * Namespaces for namespace blocks.
    * */
  def namespaces: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.REF).cast[nodes.NamespaceRef])

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDeclRef])

}
