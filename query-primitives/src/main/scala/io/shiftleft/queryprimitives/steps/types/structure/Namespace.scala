package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

/**
  * A namespace, e.g., Java package or C# namespace
  * */
class Namespace[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.Namespace, Labels](raw)
    with NameAccessors[nodes.Namespace, Labels] {

  /**
    * The type declarations defined in this namespace
    * */
  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .in(EdgeTypes.REF)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
    )

  /**
    * Methods defined in this namespace
    * */
  def method: Method[Labels] =
    typeDecl.method
}
