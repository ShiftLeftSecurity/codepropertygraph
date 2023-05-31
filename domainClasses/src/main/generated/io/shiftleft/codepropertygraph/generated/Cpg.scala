package io.shiftleft.codepropertygraph.generated

import java.nio.file.{Path, Paths}
import overflowdb.traversal.help.{DocSearchPackages, TraversalHelp}
import overflowdb.{Config, Graph}
import scala.jdk.javaapi.CollectionConverters.asJava

object Cpg {
  implicit val defaultDocSearchPackage: DocSearchPackages = DocSearchPackages(getClass.getPackage.getName)

  /** Syntactic sugar for `new Cpg(graph)`. Usage: `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope
    */
  def apply(implicit graph: Graph) = new Cpg(graph)

  def empty: Cpg =
    new Cpg(emptyGraph)

  /** Instantiate Cpg with storage. If the storage file already exists, it will load (a subset of) the data into memory.
    * Otherwise it will create an empty Cpg. In either case, configuring storage means that OverflowDb will be stored to
    * disk on shutdown (`close`).
    * I.e. if you want to preserve state between sessions, just use this method to instantiate the Cpg and ensure to
    * properly `close` it at the end.
    * @param path
    *   to the storage file, e.g. /home/user1/overflowdb.bin
    */
  def withStorage(path: Path): Cpg =
    withConfig(Config.withoutOverflow.withStorageLocation(path))

  def withStorage(path: String): Cpg =
    withStorage(Paths.get(path))

  def withConfig(config: overflowdb.Config): Cpg =
    new Cpg(Graph.open(config, nodes.Factories.allAsJava, edges.Factories.allAsJava, convertPropertyForPersistence))

  def emptyGraph: Graph =
    Graph.open(
      Config.withoutOverflow,
      nodes.Factories.allAsJava,
      edges.Factories.allAsJava,
      convertPropertyForPersistence
    )

  def convertPropertyForPersistence(property: Any): Any =
    property match {
      case arraySeq: scala.collection.immutable.ArraySeq[_] => arraySeq.unsafeArray
      case coll: IterableOnce[Any]                          => asJava(coll.iterator.toArray)
      case other                                            => other
    }

}

/** Domain-specific wrapper for graph, starting point for traversals.
  * @param graph
  *   the underlying graph. An empty graph is created if this parameter is omitted.
  */
class Cpg(private val _graph: Graph = Cpg.emptyGraph) extends AutoCloseable {
  def graph: Graph = _graph

  def help(implicit searchPackageNames: DocSearchPackages): String =
    new TraversalHelp(searchPackageNames).forTraversalSources

  override def close(): Unit =
    graph.close

  override def toString(): String =
    String.format("Cpg (%s)", graph)
}
