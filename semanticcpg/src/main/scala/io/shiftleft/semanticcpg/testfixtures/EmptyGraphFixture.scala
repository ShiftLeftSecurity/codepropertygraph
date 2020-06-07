package io.shiftleft.semanticcpg.testfixtures

import io.shiftleft.OverflowDbTestInstance
import overflowdb.OdbGraph

object EmptyGraphFixture {
  def apply[T](fun: OdbGraph => T): T = {
    val graph = OverflowDbTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }
}
