package io.shiftleft.queryprimitives.steps.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.NodeSteps
import io.shiftleft.queryprimitives.steps.types.propertyaccessors.VersionAccessors
import shapeless.HList

/**
  * A meta data entry
  * */
class MetaData[Labels <: HList](raw: GremlinScala.Aux[nodes.MetaData, Labels])
    extends NodeSteps[nodes.MetaData, Labels](raw)
    with VersionAccessors[nodes.MetaData, Labels] {

  /**
    * Programming language the code represented by this CPG was
    * generated from.
    * */
  def language: GremlinScala.Aux[String, Labels] = raw.map(_.language)

}
