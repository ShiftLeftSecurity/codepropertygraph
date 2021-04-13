package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, PropertyNames, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.File
import io.shiftleft.semanticcpg.passes.linking.linker.Linker

import scala.collection.mutable

/**
  * For all nodes with FILENAME fields, create corresponding FILE nodes
  * and connect node with FILE node via outgoing SOURCE_FILE edges.
  */
class FileCreationPass(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    val originalFileNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val newFileNameToNode = mutable.Map.empty[String, nodes.NewFile]

    cpg.file.foreach { node =>
      originalFileNameToNode += node.name -> node
    }

    def createFileIfDoesNotExist(srcNode: nodes.StoredNode, destFullName: String): Unit = {
      val dstFullName = if (destFullName == "") { File.UNKNOWN } else { destFullName }
      val newFile = newFileNameToNode.getOrElseUpdate(dstFullName, {
        val file = nodes.NewFile().name(dstFullName).order(0)
        dstGraph.addNode(file)
        file
      })
      dstGraph.addEdgeFromOriginal(srcNode, newFile, EdgeTypes.SOURCE_FILE)
    }

    // Create SOURCE_FILE edges from nodes of various types
    // to FILE nodes.

    Linker.linkToSingle(
      cpg,
      srcLabels = List(
        NodeTypes.NAMESPACE_BLOCK,
        NodeTypes.TYPE_DECL,
        NodeTypes.METHOD,
        NodeTypes.COMMENT
      ),
      dstNodeLabel = NodeTypes.FILE,
      edgeType = EdgeTypes.SOURCE_FILE,
      dstNodeMap = originalFileNameToNode,
      dstFullNameKey = PropertyNames.FILENAME,
      dstGraph,
      Some(createFileIfDoesNotExist)
    )

    Iterator(dstGraph.build())
  }
}
