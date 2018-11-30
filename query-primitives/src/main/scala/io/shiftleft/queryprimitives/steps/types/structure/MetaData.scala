package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala._
import gremlin.scala.dsl.Converter
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.VersionAccessors
import shapeless.HList

/**
  * A meta data entry
  * */
class MetaData[Labels <: HList](raw: GremlinScala[Vertex])
    extends CpgSteps[nodes.MetaData, Labels](raw)
    with VersionAccessors[nodes.MetaData, Labels] {
  override val converter = Converter.forDomainNode[nodes.MetaData]
}
