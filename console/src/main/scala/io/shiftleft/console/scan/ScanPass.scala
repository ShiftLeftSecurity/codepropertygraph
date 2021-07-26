package io.shiftleft.console.scan

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.console.Query
import io.shiftleft.passes.{CpgPassV2, DiffGraph, DiffGraphHandler, KeyPoolCreator}

class ScanPass(cpg: Cpg, queries: List[Query])
    extends CpgPassV2[Query](keyPools = Some(KeyPoolCreator.obtain(queries.size.toLong, 42949672950L).iterator)) {

  override def partIterator: Iterator[Query] = queries.iterator

  override def runOnPart(diffGraphHandler: DiffGraphHandler, query: Query): Unit = {
    val diffGraph = DiffGraph.newBuilder
    query(cpg).foreach(diffGraph.addNode)
    diffGraphHandler.addDiffGraph(diffGraph.build())
  }
}
