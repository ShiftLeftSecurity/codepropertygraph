package io.shiftleft.fuzzyc2cpg.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.{Languages, nodes}
import io.shiftleft.passes.{CpgPass, DiffGraph, KeyPool}
import io.shiftleft.semanticcpg.language.types.structure.{FileTraversal, NamespaceTraversal}

/**
  * A pass that creates a MetaData node, specifying that this
  * is a CPG for C, and a NamespaceBlock for anything that
  * cannot be assigned to any other namespace.
  * */
class CMetaDataPass(cpg: Cpg, keyPool: Option[KeyPool] = None) extends CpgPass(cpg, keyPool = keyPool) {
  override def run(): Iterator[DiffGraph] = {
    def addMetaDataNode(diffGraph: DiffGraph.Builder): Unit = {
      val metaNode = nodes.NewMetaData().language(Languages.C).version("0.1")
      diffGraph.addNode(metaNode)
    }

    def addAnyNamespaceBlock(diffGraph: DiffGraph.Builder): Unit = {
      val node = nodes
        .NewNamespaceBlock()
        .name(NamespaceTraversal.globalNamespaceName)
        .fullName(CMetaDataPass.getGlobalNamespaceBlockFullName(None))
        .filename(FileTraversal.UNKNOWN)
        .order(1)
      diffGraph.addNode(node)
    }

    val diffGraph = DiffGraph.newBuilder
    addMetaDataNode(diffGraph)
    addAnyNamespaceBlock(diffGraph)
    Iterator(diffGraph.build())
  }
}

object CMetaDataPass {

  def getGlobalNamespaceBlockFullName(fileNameOption: Option[String]): String = {
    fileNameOption match {
      case Some(fileName) =>
        s"$fileName:${NamespaceTraversal.globalNamespaceName}"
      case None =>
        NamespaceTraversal.globalNamespaceName
    }
  }

}
