package io.shiftleft.semanticcpg.passes.linking.filelinker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{EdgeTypes, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

import scala.collection.mutable

/**
  * Creates FILE nodes and connects NAMESPACE_BLOCKs, TYPE_DECLs and
  * METHODs to them via the FILENAME property.
  *
  * This pass has no other pass as prerequisite.
  */
class FileLinker(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    val originalFilesByName = mutable.Map[String, nodes.File]()
    var maxOrder = -1
    cpg.file.toIterator().foreach { file =>
      originalFilesByName.update(file.name, file)
      maxOrder = Math.max(maxOrder, file.order)
    }
    val newFilesByName = mutable.Map[String, nodes.NewFile]()

    def linkByFileName(node: nodes.StoredNode with nodes.HasFilename): Unit = {
      val name = node.filename
      if (name != "") {
        val originalFile = originalFilesByName.get(name)
        if (originalFile.isDefined) {
          dstGraph.addEdgeInOriginal(node, originalFile.get, EdgeTypes.SOURCE_FILE)
        } else {
          val newFile = newFilesByName.getOrElseUpdate(name, {
            maxOrder += 1
            val file = nodes.NewFile(name = name, order = maxOrder)
            dstGraph.addNode(file)
            file
          })
          dstGraph.addEdgeFromOriginal(node, newFile, EdgeTypes.SOURCE_FILE)
        }
      }
    }

    cpg.namespaceBlock.toIterator().foreach(linkByFileName)
    cpg.typeDecl.toIterator().foreach(linkByFileName)
    cpg.method.toIterator().foreach(linkByFileName)

    Iterator(dstGraph.build())
  }
}
