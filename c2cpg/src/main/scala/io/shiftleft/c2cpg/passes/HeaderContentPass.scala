package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{HasFilename, StoredNode}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import overflowdb.traversal.Traversal

class HeaderContentPass(cpg: Cpg) extends CpgPass(cpg) {

  override def run(): Iterator[DiffGraph] = {
    Global.headerAstCache.clear()
    val dstGraph = DiffGraph.newBuilder

    Traversal(cpg.graph.nodes()).foreach { srcNode =>
      if (!srcNode.inE(EdgeTypes.AST).hasNext) {
        srcNode match {
          case f: HasFilename if Global.headerToFilenameCache.contains(f.filename) =>
            val filename = f.filename
            val blocks = cpg.method
              .name(NamespaceTraversal.globalNamespaceName)
              .filename(Global.headerToFilenameCache(filename).toSeq: _*)
              .astChildren
              .isBlock
            blocks.foreach(dstGraph.addEdgeInOriginal(_, srcNode.asInstanceOf[StoredNode], EdgeTypes.AST))
          case _ =>
        }
      }
    }

    Iterator(dstGraph.build())
  }

}
