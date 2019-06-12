package io.shiftleft.semanticcpg

import gremlin.scala._
import io.shiftleft.TinkerGraphTestInstance

object PlainGraphFixture {
  def apply[T](fun: ScalaGraph => T): T = {
    val graph = TinkerGraphTestInstance.create
    try fun(graph)
    finally { graph.close() }
  }

}
