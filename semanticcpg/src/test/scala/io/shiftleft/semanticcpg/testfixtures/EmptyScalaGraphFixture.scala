package io.shiftleft.semanticcpg.testfixtures

import gremlin.scala.ScalaGraph
import io.shiftleft.OverflowDbTestInstance

object EmptyScalaGraphFixture {
  def apply[T](fun: ScalaGraph => T): T = {
    val graph = OverflowDbTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }
}
