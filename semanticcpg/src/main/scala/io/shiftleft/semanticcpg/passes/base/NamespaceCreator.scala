package io.shiftleft.semanticcpg.passes.base

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.NewNamespace
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

/** Creates NAMESPACE nodes and connects NAMESPACE_BLOCKs to corresponding NAMESPACE nodes.
  *
  * This pass has no other pass as prerequisite.
  */
class NamespaceCreator(cpg: Cpg) extends CpgPass(cpg) {

  /** Creates NAMESPACE nodes and connects NAMESPACE_BLOCKs to corresponding NAMESPACE nodes.
    */
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    cpg.namespaceBlock
      .groupBy(_.name)
      .foreach { case (name: String, blocks) =>
        val namespace = NewNamespace().name(name)
        dstGraph.addNode(namespace)
        blocks.foreach(block => dstGraph.addEdgeFromOriginal(block, namespace, EdgeTypes.REF))
      }
    Iterator(dstGraph.build())
  }
}
