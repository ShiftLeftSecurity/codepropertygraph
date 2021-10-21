package io.shiftleft.c2cpg.passes

import io.shiftleft.c2cpg.datastructures.Global
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.EdgeTypes
import io.shiftleft.codepropertygraph.generated.nodes.{HasFilename, StoredNode}
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language._
import io.shiftleft.semanticcpg.language.types.structure.NamespaceTraversal
import overflowdb.traversal.Traversal

import scala.collection.mutable

class HeaderContentPass(cpg: Cpg) extends CpgPass(cpg) {
  private val filenameToBlock: Map[String, StoredNode] =
    Global.headerAstCache.values
      .flatMap(_.includedInFilenames)
      .map(
        f =>
          f -> cpg.method
            .name(NamespaceTraversal.globalNamespaceName)
            .filename(f)
            .astChildren
            .isBlock
            .head)
      .toMap

  private val headerToFilename = mutable.Map.empty[String, Set[String]]

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    Global.headerAstCache.foreach {
      case (key, value) =>
        if (headerToFilename.contains(key._1)) {
          val old = headerToFilename(key._1)
          headerToFilename(key._1) = old ++ value.includedInFilenames
        } else {
          headerToFilename(key._1) = value.includedInFilenames
        }
    }

    Traversal(cpg.graph.nodes()).foreach { srcNode =>
      if (!srcNode.inE(EdgeTypes.AST).hasNext) {
        srcNode match {
          case f: HasFilename if headerToFilename.contains(f.filename) =>
            val filename = f.filename
            val blocks = headerToFilename(filename).map(filenameToBlock)
            blocks.foreach(dstGraph.addEdgeInOriginal(_, srcNode.asInstanceOf[StoredNode], EdgeTypes.AST))
          case _ =>
        }
      }
    }

    Iterator(dstGraph.build())
  }

}
