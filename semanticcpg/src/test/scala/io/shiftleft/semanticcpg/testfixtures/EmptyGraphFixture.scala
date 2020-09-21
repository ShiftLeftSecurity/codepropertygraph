package io.shiftleft.semanticcpg.testfixtures

import io.shiftleft.OverflowDbTestInstance
import overflowdb.Graph

object EmptyGraphFixture {
  def apply[T](fun: Graph => T): T = {
    val graph = OverflowDbTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }
}
