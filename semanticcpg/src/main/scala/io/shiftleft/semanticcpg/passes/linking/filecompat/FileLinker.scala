package io.shiftleft.semanticcpg.passes.linking.filecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.passes.linking.linker.Linker

import scala.collection.mutable

/**
  * Links NAMESPACE_BLOCKs, TYPE_DECLs and METHODs to their FILEs via
  * SOURCE_FILE edges.
  *
  * This pass should come after FILENAME properties have been recovered,
  * that is, after FileNameCompat.
  */
class FileLinker(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    val originalFileNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val newFileNameToNode = mutable.Map.empty[String, nodes.NewFile]

    var maxFileOrder = -1

    cpg.file.foreach { node =>
      originalFileNameToNode += node.name -> node
      maxFileOrder = Math.max(maxFileOrder, node.order)
    }

    def createFileIfDoesNotExist(srcNode: nodes.StoredNode, dstFullName: String): Unit = {
      val newFile = newFileNameToNode.getOrElseUpdate(dstFullName, {
        maxFileOrder += 1
        val file = nodes.NewFile(name = dstFullName, order = maxFileOrder)
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
        NodeTypes.METHOD
      ),
      dstNodeLabel = NodeTypes.FILE,
      edgeType = EdgeTypes.SOURCE_FILE,
      dstNodeMap = originalFileNameToNode,
      dstFullNameKey = "FILENAME",
      dstGraph,
      Some(createFileIfDoesNotExist)
    )

    Iterator(dstGraph.build())
  }
}
