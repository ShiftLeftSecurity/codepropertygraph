package io.shiftleft.semanticcpg.language

import io.shiftleft.codepropertygraph.generated.EdgeKeysOdb
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.codepropertygraph.generated.nodes.{CpgNode, NewNode, StoredNode}
import io.shiftleft.passes.DiffGraph
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.Property
import overflowdb.traversal.Traversal

trait HasStoreMethod {
  def store()(implicit diffBuilder: DiffGraph.Builder): Unit
}

class NewNodeSteps[A <: NewNode](val traversal: Traversal[A]) extends HasStoreMethod {
  import NewNodeSteps.logger

  override def store()(implicit diffBuilder: DiffGraph.Builder): Unit =
    traversal.sideEffect(storeRecursively).iterate

  private def storeRecursively(newNode: NewNode)(implicit diffBuilder: DiffGraph.Builder): Unit = {
    diffBuilder.addNode(newNode)
  }

  def label: Steps[String] = new Steps(traversal.map(_.label))
}

object NewNodeSteps {
  private val logger: Logger = LoggerFactory.getLogger(classOf[NewNodeSteps[_]])
}
