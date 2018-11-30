package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

/**
  * A source file
  * */
class File[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.File, Labels](raw) with NameAccessors[nodes.File, Labels] {
  override val converter = Converter.forDomainNode[nodes.File]

  def typeDecl: TypeDecl[Labels] =
    new TypeDecl[Labels](
      raw
        .out(EdgeTypes.AST)
        .out(EdgeTypes.AST)
        .hasLabel(NodeTypes.TYPE_DECL)
    )

  def namespace: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.AST).out(EdgeTypes.REF))

  def namespaceBlock: NamespaceBlock[Labels] =
    new NamespaceBlock[Labels](raw.out(EdgeTypes.AST))
}
