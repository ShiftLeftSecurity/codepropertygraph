package io.shiftleft.semanticcpg.passes.namespacecreator

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable

/**
  * Creates NAMESPACE nodes and connects NAMESPACE_BLOCKs
  * to corresponding NAMESPACE nodes.
  *
  * This pass has no other pass as prerequisite.
  */
class NamespaceCreator(cpg: Cpg) extends CpgPass(cpg) {

  /**
    * Creates NAMESPACE nodes and connects NAMESPACE_BLOCKs
    * to corresponding NAMESPACE nodes.
    * */
  override def run(): Iterator[DiffGraph] = {
    val blocksByName = cpg.namespaceBlock.toBuffer.groupBy(_.name)
    val dstGraph = DiffGraph.newBuilder

    blocksByName.foreach {
      case (name: String, blocks: mutable.Buffer[Vertex]) =>
        val namespace = new nodes.NewNamespace(name)
        dstGraph.addNode(namespace)
        blocks.foreach {
          case block: nodes.NamespaceBlock =>
            dstGraph.addEdgeFromOriginal(block, namespace, EdgeTypes.REF)
        }
    }
    Iterator(dstGraph.build())
  }
}
