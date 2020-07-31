package io.shiftleft.semanticcpg.passes.linking.filecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._

/**
  * Updates NAMESPACE_BLOCKs, TYPE_DECLs and METHODs so that they all
  * have a FILENAME property if at all possible by defaulting to the
  * previously used, AST-based query.
  *
  * This pass should come after AST edges have been reconstructed, that
  * is, after Linker.  Is used by FileLinker.
  */
class FileNameCompat(cpg: Cpg) extends CpgPass(cpg) {
  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    def updateDefaultFileName(node: nodes.StoredNode with nodes.HasFilename): Unit = {
      // When creating nodes via NewNode classes, filename is "", not null.
      if (node.filename == null || node.filename == "") {
        node.start.file.name.headOption().foreach { name =>
          dstGraph.addNodeProperty(node, "FILENAME", name)
        }
      }
    }

    cpg.namespaceBlock.toIterator().foreach(updateDefaultFileName)
    cpg.typeDecl.toIterator().foreach(updateDefaultFileName)
    cpg.method.toIterator().foreach(updateDefaultFileName)

    Iterator(dstGraph.build())
  }
}
