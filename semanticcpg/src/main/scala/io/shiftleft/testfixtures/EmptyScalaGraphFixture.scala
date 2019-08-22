package io.shiftleft.testfixtures

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance

object EmptyScalaGraphFixture {
  def apply[T](fun: ScalaGraph => T): T = {
    val graph = OverflowDbTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }
}
