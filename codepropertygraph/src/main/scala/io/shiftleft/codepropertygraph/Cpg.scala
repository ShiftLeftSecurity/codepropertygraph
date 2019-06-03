package io.shiftleft.codepropertygraph

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import shapeless.HNil

object Cpg {

  /**
    * Syntactic sugar for `new Cpg(graph)`.
    * Usage:
    *   `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope
    */
  def apply(implicit graph: Graph) = new Cpg(graph)

  /**
    * Create an empty code property graph
    */
  def emptyCpg: Cpg = new Cpg(emptyGraph)

  /**
    * Returns a fresh, empty graph
    */
  private def emptyGraph: TinkerGraph =
    TinkerGraph
      .open(
        io.shiftleft.codepropertygraph.generated.nodes.Factories.AllAsJava,
        io.shiftleft.codepropertygraph.generated.edges.Factories.AllAsJava
      )
}

/**
  * Traversal starting point.
  * This is the starting point of all traversals. A variable of this
  * type named `cpg` is made available in the REPL.
  *
  * @param graph the underlying graph. An empty graph is created if this parameter is omitted.
  */
class Cpg(val graph: Graph = Cpg.emptyGraph)
    extends ext.queryprimitives.Enrichable
    with ext.queryprimitivesext.Enrichable
    with ext.securityprofile.Enrichable
    with ext.semanticcpg.Enrichable {

  /**
    * The underlying graph.
    *
    * This member provides raw access to the underlying graph.
    */
  implicit lazy val scalaGraph: ScalaGraph =
    graph.asScala

  /**
    * Closes code property graph.
    * No further operations can be performed on it.
    */
  def close(): Unit =
    graph.close
}
