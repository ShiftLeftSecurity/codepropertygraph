package io.shiftleft.semanticcpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewFile, StoredNode}
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, NodeTypes, PropertyNames}
import io.shiftleft.passes.{DiffGraph, DiffGraphHandler, SimpleCpgPassV2}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.FileTraversal
import io.shiftleft.semanticcpg.passes.linking.linker.Linker

import scala.collection.mutable

/**
  * For all nodes with FILENAME fields, create corresponding FILE nodes
  * and connect node with FILE node via outgoing SOURCE_FILE edges.
  */
class FileCreationPass(cpg: Cpg) extends SimpleCpgPassV2 {
  override def run(diffGraphHandler: DiffGraphHandler): Unit = {
    val dstGraph = DiffGraph.newBuilder

    val originalFileNameToNode = mutable.Map.empty[String, StoredNode]
    val newFileNameToNode = mutable.Map.empty[String, NewFile]

    cpg.file.foreach { node =>
      originalFileNameToNode += node.name -> node
    }

    def createFileIfDoesNotExist(srcNode: StoredNode, destFullName: String): Unit = {
      if (destFullName != srcNode.propertyDefaultValue(PropertyNames.FILENAME)) {
        val dstFullName = if (destFullName == "") { FileTraversal.UNKNOWN } else { destFullName }
        val newFile = newFileNameToNode.getOrElseUpdate(dstFullName, {
          val file = NewFile().name(dstFullName).order(0)
          dstGraph.addNode(file)
          file
        })
        dstGraph.addEdgeFromOriginal(srcNode, newFile, EdgeTypes.SOURCE_FILE)
      }
    }

    // Create SOURCE_FILE edges from nodes of various types
    // to FILE

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

    diffGraphHandler.addDiffGraph(dstGraph.build())
  }
}
