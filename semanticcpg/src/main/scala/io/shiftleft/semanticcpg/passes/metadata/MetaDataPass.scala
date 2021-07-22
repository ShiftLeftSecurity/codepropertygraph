package io.shiftleft.semanticcpg.passes.metadata

import io.shiftleft.codepropertygraph.generated.nodes.{NewMetaData, NewNamespaceBlock}
import io.shiftleft.passes.{CpgPass, DiffGraph, KeyPool}
import io.shiftleft.semanticcpg.language.types.structure.{FileTraversal, NamespaceTraversal}

/**
  * A pass that creates a MetaData node, specifying that this
  * is a CPG for language, and a NamespaceBlock for anything that
  * cannot be assigned to any other namespace.
  * */
class MetaDataPass(language: String, keyPool: Option[KeyPool] = None)
    extends CpgPass(keyPool = keyPool) {
  override def run(): Iterator[DiffGraph] = {
    def addMetaDataNode(diffGraph: DiffGraph.Builder): Unit = {
      val metaNode = NewMetaData().language(language).version("0.1")
      diffGraph.addNode(metaNode)
    }

    def addAnyNamespaceBlock(diffGraph: DiffGraph.Builder): Unit = {
      val node = NewNamespaceBlock()
        .name(NamespaceTraversal.globalNamespaceName)
        .fullName(MetaDataPass.getGlobalNamespaceBlockFullName(None))
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

object MetaDataPass {

  def getGlobalNamespaceBlockFullName(fileNameOption: Option[String]): String = {
    fileNameOption match {
      case Some(fileName) =>
        s"$fileName:${NamespaceTraversal.globalNamespaceName}"
      case None =>
        NamespaceTraversal.globalNamespaceName
    }
  }

}
