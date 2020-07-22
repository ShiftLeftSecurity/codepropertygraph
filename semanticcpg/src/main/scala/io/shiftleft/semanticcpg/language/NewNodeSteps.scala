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
    newNode.storeRecursively2(diffBuilder)
    //diffBuilder.addNode(newNode)
    /*
    // add all `contained` nodes that are NewNodes to the DiffGraph
    newNode.allContainedNodes.collect {
      case containedNode: NewNode => storeRecursively(containedNode)
    }

    // create edges to `contained` nodes for this new node
    for {
      (localName, containedNodes) <- newNode.containedNodesByLocalName
      (containedNode, index) <- containedNodes.zipWithIndex
    } {
      val properties = Seq(
        EdgeKeys.LOCAL_NAME -> localName,
        EdgeKeys.INDEX -> index
      ).map { case KeyValue(key, value) => (key.name, value) }
      addEdge(diffBuilder, newNode, containedNode, ContainsNode.Label, properties)

    }*/
  }

  /*
  private def addEdge(diffBuilder: DiffGraph.Builder,
                      src: CpgNode,
                      dst: CpgNode,
                      label: String,
                      properties: Seq[(String, AnyRef)]): Unit =
    (src, dst) match {
      case (src: NewNode, dst: NewNode) => diffBuilder.addEdge(src, dst, label, properties)
      case (src: NewNode, dst: StoredNode) =>
        diffBuilder.addEdgeToOriginal(src, dst, label, properties)
      case (src: StoredNode, dst: NewNode) =>
        diffBuilder.addEdgeFromOriginal(src, dst, label, properties)
      case (src: StoredNode, dst: StoredNode) =>
        diffBuilder.addEdgeInOriginal(src, dst, label, properties)
      case (src, dst) =>
        val srcClassMaybe = Option(src).map(_.getClass)
        val dstClassMaybe = Option(dst).map(_.getClass)
        logger.warn(
          s"unhandled case, likely produced by a fauly pass: src=$src, src.getClass=$srcClassMaybe, dst=$dst, dstClass=$dstClassMaybe")
    } */

  def label: Steps[String] = new Steps(raw.map(_.label))
}

object NewNodeSteps {
  private val logger: Logger = LoggerFactory.getLogger(classOf[NewNodeSteps[_]])
}
