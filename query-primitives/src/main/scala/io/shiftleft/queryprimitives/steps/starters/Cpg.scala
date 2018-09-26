package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.queryprimitives.steps.CpgSteps
import io.shiftleft.queryprimitives.steps.Implicits._
import shapeless.HNil

object Cpg {

  /* syntactic sugar for `Cpg(graph)`. Usage:
   * `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope */
  def apply(implicit graph: Graph) = new Cpg(graph)
}

/**
  Traversal starting point
  This is the starting point of all traversals. A variable of this
  type named `cpg` is made available in the REPL.

  @param graph the underlying graph
  */
class Cpg(val graph: Graph) extends NodeTypeStarters {

  /**
    The underlying graph.

    This member provides raw access to the underlying graph.
    */
  implicit lazy val scalaGraph =
    graph.asScala

}
