package io.shiftleft.codepropertygraph

import flatgraph.Graph
import flatgraph.help.DocSearchPackages
import io.shiftleft.codepropertygraph.generated

/** TODO this is now being generated as well - for now we'll just forward calls to `generated.Cpg` next step is to
  * remove this class and move remove the `generated` part from the generated package
  */
object Cpg {
  val defaultDocSearchPackage: DocSearchPackages = generated.Cpg.defaultDocSearchPackage

  /** Syntactic sugar for `new Cpg(graph)`. Usage: `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope
    */
  def apply(implicit graph: Graph) = generated.Cpg(graph)

  /** Create an empty code property graph
    */
  def emptyCpg: generated.Cpg =
    generated.Cpg(emptyGraph)

  /** Instantiate cpg with storage. If the storage file already exists, it will load (a subset of) the data into memory.
    * Otherwise it will create an empty cpg. In either case, configuring storage means that OverflowDb will be stored to
    * disk on shutdown (`close`). I.e. if you want to preserve state between sessions, just use this method to
    * instantiate the cpg and ensure to properly `close` the cpg at the end.
    * @param path
    *   to the storage file, e.g. /home/user1/overflowdb.bin
    */
  def withStorage(path: String, deserializeOnClose: Boolean = true): generated.Cpg =
    generated.Cpg.withStorage(java.nio.file.Paths.get(path), deserializeOnClose)

  def emptyGraph: Graph =
    generated.Cpg.empty.graph

}
