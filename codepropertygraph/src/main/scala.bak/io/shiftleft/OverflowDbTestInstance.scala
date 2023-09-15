package io.shiftleft

import io.shiftleft.codepropertygraph.Cpg
import overflowdb.Graph

object OverflowDbTestInstance {

  def create: Graph = Cpg.emptyGraph

}
