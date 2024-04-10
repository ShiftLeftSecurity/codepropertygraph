package io.shiftleft.codepropertygraph

import overflowdb.Graph
import overflowdb.traversal.help.DocSearchPackages

/** TODO this is now being generated as well - for now we'll just forward calls to `generated.Cpg` next step is to
  * remove this class and move remove the `generated` part from the generated package
  */
object Cpg {
  implicit val docSearchPackages: DocSearchPackages =
    DocSearchPackages("io.shiftleft", "io.joern")

  /** Syntactic sugar for `new Cpg(graph)`. Usage: `Cpg(graph)` or simply `Cpg` if you have an `implicit Graph` in scope
    */
  def apply(implicit graph: Graph) = new Cpg(graph)

  /** Create an empty code property graph
    */
  def emptyCpg: Cpg =
    new Cpg(emptyGraph)

  /** Instantiate cpg with storage. If the storage file already exists, it will load (a subset of) the data into memory.
    * Otherwise it will create an empty cpg. In either case, configuring storage means that OverflowDb will be stored to
    * disk on shutdown (`close`). I.e. if you want to preserve state between sessions, just use this method to
    * instantiate the cpg and ensure to properly `close` the cpg at the end.
    * @param path
    *   to the storage file, e.g. /home/user1/overflowdb.bin
    */
  def withStorage(path: String): Cpg =
    new Cpg(generated.Cpg.withStorage(path).graph)

  def withConfig(config: overflowdb.Config): Cpg =
    Cpg(generated.Cpg.withConfig(config).graph)

  def emptyGraph: Graph =
    generated.Cpg.emptyGraph

}
