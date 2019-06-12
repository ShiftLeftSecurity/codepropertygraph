package io.shiftleft.semanticcpg

import gremlin.scala._
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

object PlainGraphFixture {
  def apply[T](fun: ScalaGraph => T): T = {
    val graph =
      TinkerGraph
        .open(io.shiftleft.codepropertygraph.generated.nodes.Factories.AllAsJava,
              io.shiftleft.codepropertygraph.generated.edges.Factories.AllAsJava)
        .asScala()
    try fun(graph) finally { graph.close() }
  }

}
