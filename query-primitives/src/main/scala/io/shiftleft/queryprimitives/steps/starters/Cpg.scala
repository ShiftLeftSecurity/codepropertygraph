package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NodeSteps, Steps}
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
class Cpg(val graph: Graph) {

  /**
    The underlying graph.

    This member provides raw access to the underlying graph.
    */
  implicit lazy val scalaGraph: ScalaGraph =
    graph.asScala

  /**
    Begin traversal at set of nodes
    */
  def atDomainVertices[NodeType <: nodes.StoredNode](
      domainVertices: Seq[NodeType]): NodeSteps[NodeType, HNil] =
    atVerticesWithId(domainVertices.map(_.id))

  /**
    Begin traversal at set of nodes - specified by their ids
    */
  def atVerticesWithId[NodeType <: nodes.StoredNode](ids: Seq[Any]): NodeSteps[NodeType, HNil] =
    if (ids.size == 0) new NodeSteps[NodeType, HNil](scalaGraph.V(-1).cast[NodeType])
    else new NodeSteps[NodeType, HNil](scalaGraph.V(ids: _*).cast[NodeType])

}
