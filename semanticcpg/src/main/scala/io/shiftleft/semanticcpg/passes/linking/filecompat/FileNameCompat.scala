package io.shiftleft.semanticcpg.passes.linking.filecompat

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language._

/**
  * Updates NAMESPACE_BLOCKs, TYPE_DECLs and METHODs so that they all
  * have a FILENAME property if at all possible by defaulting to the
  * previously used, AST-based query.
  *
  * This pass should come after AST edges have been reconstructed, that
  * is, after Linker.  Is used by FileLinker.
  */
class FileNameCompat(cpg: Cpg) extends ParallelCpgPass[nodes.StoredNode with nodes.HasFilename](cpg) {

  override def partIterator: Iterator[nodes.StoredNode with nodes.HasFilename] = {
    cpg.namespaceBlock.toIterator() ++
      cpg.typeDecl.toIterator() ++
      cpg.method.toIterator()
  }

  override def runOnPart(node: nodes.StoredNode with nodes.HasFilename): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
    if (node.filename == null) {
      node.start.file.name.headOption().foreach { name =>
        dstGraph.addNodeProperty(node, "FILENAME", name)
      }
    }
    Some(dstGraph.build())
  }
}
