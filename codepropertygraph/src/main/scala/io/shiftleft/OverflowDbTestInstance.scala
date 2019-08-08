package io.shiftleft

import gremlin.scala._
import io.shiftleft.overflowdb.OdbGraph
import io.shiftleft.codepropertygraph.generated.{edges, nodes}
import io.shiftleft.overflowdb.OdbConfig

object OverflowDbTestInstance {

  def create =
    OdbGraph.open(OdbConfig.withoutOverflow, nodes.Factories.AllAsJava, edges.Factories.AllAsJava)

}
