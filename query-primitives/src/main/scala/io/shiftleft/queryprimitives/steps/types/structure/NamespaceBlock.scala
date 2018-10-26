package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.NameAccessors
import shapeless.HList

class NamespaceBlock[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.NamespaceBlock, Labels](raw)
    with NameAccessors[nodes.NamespaceBlock, Labels] {
  override val converter = Converter.forDomainNode[nodes.NamespaceBlock]

  def namespaces: Namespace[Labels] =
    new Namespace[Labels](raw.out(EdgeTypes.REF))
}
