package io.shiftleft.queryprimitives.steps.starters

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.queryprimitives.steps.{NodeSteps, ext}
import io.shiftleft.queryprimitives.steps.Implicits.GremlinScalaDeco
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import shapeless.HNil

object Cpg {

  /* syntactic sugar for `Cpg(graph)`. Usage:
   * `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope */
  def apply(implicit graph: Graph) = new Cpg(graph)

  implicit def toNodeTypeStarters(cpg: Cpg): NodeTypeStarters =
    new NodeTypeStarters(cpg)

  /**
    * Create an empty code property graph
    * */
  def emptyCpg: Cpg = new Cpg(emptyGraph)

  /**
    * Create an empty graph
    * */
  def emptyGraph: TinkerGraph =
    TinkerGraph
      .open(
        io.shiftleft.codepropertygraph.generated.nodes.Factories.AllAsJava,
        io.shiftleft.codepropertygraph.generated.edges.Factories.AllAsJava
      )
}

/**
  Traversal starting point
  This is the starting point of all traversals. A variable of this
  type named `cpg` is made available in the REPL.

  @param graph the underlying graph. An empty graph is created if this parameter is omitted.
  */
class Cpg(val graph: Graph = Cpg.emptyGraph)
    extends ext.Enrichable
    with ext.securityprofile.Enrichable
    with ext.semanticcpg.Enrichable {

  /**
    The underlying graph.

    This member provides raw access to the underlying graph.
    */
  implicit lazy val scalaGraph: ScalaGraph =
    graph.asScala

  /**
    Begin traversal at set of nodes - specified by their ids
    */
  def atVerticesWithId[NodeType <: nodes.StoredNode](ids: Seq[Any]): NodeSteps[NodeType, HNil] =
    if (ids.size == 0) new NodeSteps[NodeType, HNil](scalaGraph.V(-1).cast[NodeType])
    else new NodeSteps[NodeType, HNil](scalaGraph.V(ids: _*).cast[NodeType])

}
