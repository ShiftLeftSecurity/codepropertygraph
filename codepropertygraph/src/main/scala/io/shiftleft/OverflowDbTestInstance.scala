package io.shiftleft

import io.shiftleft.codepropertygraph.generated.{edges, nodes}
import io.shiftleft.overflowdb.{OdbConfig, OdbGraph}

object OverflowDbTestInstance {

  def create =
    OdbGraph.open(OdbConfig.withoutOverflow, nodes.Factories.allAsJava, edges.Factories.allAsJava)

}
