package io.shiftleft.semanticcpg.language

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, Node, StoredNode}
import io.shiftleft.codepropertygraph.generated.edges.ContainsNode
import io.shiftleft.codepropertygraph.generated.EdgeKeys
import io.shiftleft.passes.DiffGraph
import org.apache.logging.log4j.{LogManager, Logger}

class NewNodeSteps[A <: NewNode](override val raw: GremlinScala[A]) extends Steps[A](raw) {
  import NewNodeSteps.logger

  def store()(implicit graph: DiffGraph): Unit =
    raw.sideEffect(storeRecursively).iterate()

  private def storeRecursively(newNode: NewNode)(implicit graph: DiffGraph): Unit = {
    graph.addNode(newNode)

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
      addEdge(graph, newNode, containedNode, ContainsNode.Label, properties)
    }
  }

  private def addEdge(graph: DiffGraph, src: Node, dst: Node, label: String, properties: Seq[(String, AnyRef)]): Unit =
    (src, dst) match {
      case (src: NewNode, dst: NewNode) => graph.addEdge(src, dst, label, properties)
      case (src: NewNode, dst: StoredNode) =>
        graph.addEdgeToOriginal(src, dst, label, properties)
      case (src: StoredNode, dst: NewNode) =>
        graph.addEdgeFromOriginal(src, dst, label, properties)
      case (src: StoredNode, dst: StoredNode) =>
        graph.addEdgeInOriginal(src, dst, label, properties)
      case (src, dst) =>
        val srcClassMaybe = Option(src).map(_.getClass)
        val dstClassMaybe = Option(dst).map(_.getClass)
        logger.warn(
          s"unhandled case, likely produced by a fauly pass: src=$src, src.getClass=$srcClassMaybe, dst=$dst, dstClass=$dstClassMaybe")
    }

  /**
    * Pretty print vertices
    * */
  override def p(): List[String] = {
    l.map {
      case node: NewNode => {
        val label = node.label
        val keyValPairs = node.properties.toList
          .filter(x => x._2.toString != "")
          .sortBy(_._1)
          .map(x => x._1 + ": " + x._2)
        s"($label): " + keyValPairs.mkString(", ")
      }
      case elem => elem.toString
    }
  }

}

object NewNodeSteps {
  private val logger: Logger = LogManager.getLogger(classOf[NewNodeSteps[_]])
}
