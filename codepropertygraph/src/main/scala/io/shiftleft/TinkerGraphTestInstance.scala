package io.shiftleft

import gremlin.scala._
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import io.shiftleft.codepropertygraph.generated.{edges, nodes}

object TinkerGraphTestInstance {

  def create: ScalaGraph = {
    val config = TinkerGraph.EMPTY_CONFIGURATION
    config.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, false)
    TinkerGraph.open(config,
      nodes.Factories.AllAsJava,
      edges.Factories.AllAsJava)
    .asScala()
  }

}
