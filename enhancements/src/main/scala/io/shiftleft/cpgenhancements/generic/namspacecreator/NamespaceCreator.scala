package io.shiftleft.cpgenhancements.generic.namspacecreator

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeKeyNames, NodeKeys, NodeTypes}
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.cpgenhancements.CpgEnhancement

import scala.collection.mutable

class NamespaceCreator(graph: ScalaGraph) extends CpgEnhancement(graph) {

  /**
    * Creates NAMESPACE nodes and connects NAMESPACE_BLOCKs
    * to corresponding NAMESPACE nodes.
    * */
  override def run(): Unit = {
    val namespaceBlocks = graph.V().hasLabel(NodeTypes.NAMESPACE_BLOCK).toBuffer
    val blocksByName = namespaceBlocks.groupBy(_.value2(NodeKeys.NAME))

    blocksByName.foreach {
      case (name: String, blocks: mutable.Buffer[Vertex]) =>
        val namespace = nodes.NewNamespace(name)
        dstGraph.addNode(namespace)
        blocks.foreach {
          case block: Vertex if block.label == NodeTypes.NAMESPACE_BLOCK =>
            dstGraph.addEdgeFromOriginal(block, namespace, EdgeTypes.REF)
        }
    }
  }
}
