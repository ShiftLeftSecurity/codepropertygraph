package io.shiftleft

import io.shiftleft.codepropertygraph.generated.Cpg
import overflowdb.Graph

object OverflowDbTestInstance {

  def create: Graph = Cpg.emptyGraph

}
