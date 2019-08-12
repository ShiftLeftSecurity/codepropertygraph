package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.OverflowDbTestInstance

object PlainGraphFixture {
  def apply[T](fun: ScalaGraph => T): T = {
    val graph = OverflowDbTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }
}
