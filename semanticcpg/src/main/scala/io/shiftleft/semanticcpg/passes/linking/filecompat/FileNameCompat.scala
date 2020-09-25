package io.shiftleft.semanticcpg.passes.linking.filecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import overflowdb.traversal._

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

    def updateDefaultFileName(node: StoredNode with HasFilename): Unit = {
      // When creating nodes via NewNode classes, filename is "", not null.
      // For operators, filename might also be null.
      if (node.filename == null || node.filename == "") {
        dstGraph.addNodeProperty(node, "FILENAME", node.start.file.name.headOption.getOrElse(""))
      }
    }

    cpg.namespaceBlock.foreach(updateDefaultFileName)
    cpg.typeDecl.foreach(updateDefaultFileName)
    cpg.method.foreach(updateDefaultFileName)

    Iterator(dstGraph.build())
  }
}
