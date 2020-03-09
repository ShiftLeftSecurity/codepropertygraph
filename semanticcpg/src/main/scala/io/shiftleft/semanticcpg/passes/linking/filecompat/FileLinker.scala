package io.shiftleft.semanticcpg.passes.linking.filecompat

import gremlin.scala._
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, nodes}
import io.shiftleft.semanticcpg.passes.linking.linker.Linker
import io.shiftleft.passes.{CpgPass, DiffGraph}

import scala.jdk.CollectionConverters._
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

    val linker = new Linker(cpg)

    val originalFileNameToNode = mutable.Map.empty[String, nodes.StoredNode]
    val newFileNameToNode = mutable.Map.empty[String, nodes.NewFile]

    var maxFileOrder = -1

    cpg.graph.graph.vertices().asScala.foreach {
      case node: nodes.File =>
        originalFileNameToNode += node.name -> node
        maxFileOrder = Math.max(maxFileOrder, node.order)
      case _ => // ignore
    }

    // Create SOURCE_FILE edges from nodes of various types
    // to FILE nodes.

    linker.linkToSingle(
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
      Some((srcNode: nodes.StoredNode, dstFullName: String) => {
        val newFile = newFileNameToNode.getOrElseUpdate(dstFullName, {
          maxFileOrder += 1
          val file = nodes.NewFile(name = dstFullName, order = maxFileOrder)
          dstGraph.addNode(file)
          file
        })
        dstGraph.addEdgeFromOriginal(srcNode, newFile, EdgeTypes.SOURCE_FILE)
      })
    )

    Iterator(dstGraph.build())
  }
}
