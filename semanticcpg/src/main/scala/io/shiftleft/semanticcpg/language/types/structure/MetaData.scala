package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.NodeSteps

/**
  * A meta data entry
  * */
class MetaData[A <: nodes.MetaData](raw: GremlinScala[A]) extends NodeSteps[A](raw) {

  /**
    * Returns the programming language of the code for which this CPG was
    * generated from.
    * */
  def language: GremlinScala[String] = raw.map(_.language)

}
