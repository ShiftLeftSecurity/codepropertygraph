package io.shiftleft.semanticcpg.language.types.structure

import gremlin.scala.GremlinScala
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.semanticcpg.language.{NodeSteps, Steps}

/**
  * A meta data entry
  * */
class MetaData(val wrapped: NodeSteps[nodes.MetaData]) extends AnyVal {
  private def raw: GremlinScala[nodes.MetaData] = wrapped.raw

  /**
    * Returns the programming language of the code for which this CPG was
    * generated from.
    * */
  def language: Steps[String] = wrapped.map(_.language)

}
