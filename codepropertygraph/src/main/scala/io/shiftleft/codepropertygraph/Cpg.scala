package io.shiftleft.codepropertygraph

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{edges, nodes}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

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
  def emptyCpg: Cpg =
    new Cpg(emptyGraph)

  /**
    * Instantiate cpg with storage.
    * If the storage file already exists, it will load (a subset of) the data into memory. Otherwise it will create an empty cpg.
    * In either case, configuring storage means that OverflowDb will be stored to disk on shutdown (`close`).
    * I.e. if you want to preserve state between sessions, just use this method to instantiate the cpg and ensure to properly `close` the cpg at the end.
    * @param path to the storage file, e.g. /home/user1/overflowdb.bin
    */
  def withStorage(path: String): Cpg = {
    val configuration = TinkerGraph.EMPTY_CONFIGURATION
    configuration.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_LOCATION, path)
    new Cpg(TinkerGraph.open(configuration, nodes.Factories.AllAsJava, edges.Factories.AllAsJava))
  }

  /**
    * Returns a fresh, empty graph
    */
  private def emptyGraph: TinkerGraph = {
    val config = TinkerGraph.EMPTY_CONFIGURATION
    config.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, false)
    TinkerGraph.open(config, nodes.Factories.AllAsJava, edges.Factories.AllAsJava)
  }
}

/**
  * Traversal starting point.
  * This is the starting point of all traversals. A variable of this
  * type named `cpg` is made available in the REPL.
  *
  * @param graph the underlying graph. An empty graph is created if this parameter is omitted.
  */
class Cpg(val graph: Graph = Cpg.emptyGraph)
    extends AutoCloseable
    with ext.queryprimitives.Enrichable
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
  override def close(): Unit =
    graph.close
}
