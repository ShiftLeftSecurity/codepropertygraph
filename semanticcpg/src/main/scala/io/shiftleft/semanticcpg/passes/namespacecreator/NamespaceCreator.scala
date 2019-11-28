package io.shiftleft.semanticcpg.passes.namespacecreator

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph}

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
    val namespaceBlocks = cpg.graph.V.hasLabel(NodeTypes.NAMESPACE_BLOCK).toBuffer
    val blocksByName = namespaceBlocks.groupBy(_.value2(NodeKeys.NAME))
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
