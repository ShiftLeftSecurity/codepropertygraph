package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps
import io.shiftleft.semanticcpg.language.types.propertyaccessors.VersionAccessors

/**
  * A meta data entry
  * */
class MetaData(raw: GremlinScala[nodes.MetaData])
    extends NodeSteps[nodes.MetaData](raw)
    with VersionAccessors[nodes.MetaData] {

  /**
    * Returns the programming language of the code for which this CPG was
    * generated from.
    * */
  def language: GremlinScala[String] = raw.map(_.language)

}
