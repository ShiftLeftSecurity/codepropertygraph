package io.shiftleft

import io.shiftleft.codepropertygraph.generated.{edges, nodes}
import overflowdb.{Config, Graph}

object OverflowDbTestInstance {

  def create =
    Graph.open(Config.withoutOverflow, nodes.Factories.allAsJava, edges.Factories.allAsJava)

}
