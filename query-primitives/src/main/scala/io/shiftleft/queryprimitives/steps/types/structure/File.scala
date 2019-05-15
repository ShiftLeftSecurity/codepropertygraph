package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

/**
  * A source file
  * */
class File[Labels <: HList](raw: GremlinScala.Aux[nodes.FileRef, Labels])
    extends NodeSteps[nodes.FileRef, Labels](raw)
    with NameAccessors[nodes.FileRef, Labels] {

  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .out(EdgeTypes.AST)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
        .cast[nodes.TypeDeclRef])

  def namespace: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.AST).out(EdgeTypes.REF).cast[nodes.NamespaceRef])

  def namespaceBlock: NamespaceBlock[Labels] =
    new NamespaceBlock[Labels](raw.out(EdgeTypes.AST).cast[nodes.NamespaceBlockRef])
}
