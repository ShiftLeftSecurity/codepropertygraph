package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, CpgNode, StoredNode}
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.codepropertygraph.generated.EdgeKeys
import io.shiftleft.passes.DiffGraph
import org.slf4j.Logger
import org.slf4j.LoggerFactory

trait HasStoreMethod {
  def store()(implicit diffBuilder: DiffGraph.Builder): Unit
}

class NewNodeSteps[A <: NewNode](override val raw: GremlinScala[A]) extends Steps[A](raw) with HasStoreMethod {
  import NewNodeSteps.logger

  override def store()(implicit diffBuilder: DiffGraph.Builder): Unit =
    raw.sideEffect(storeRecursively).iterate()

  private def storeRecursively(newNode: NewNode)(implicit diffBuilder: DiffGraph.Builder): Unit = {
    diffBuilder.addNode(newNode)
  }

  def label: Steps[String] = new Steps(raw.map(_.label))
}

object NewNodeSteps {
  private val logger: Logger = LoggerFactory.getLogger(classOf[NewNodeSteps[_]])
}
