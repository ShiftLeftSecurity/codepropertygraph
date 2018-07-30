package io.shiftleft.queryprimitives.steps

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, Node, StoredNode}
import io.shiftleft.codepropertygraph.generated.{EdgeKeys, EdgeTypes}
import io.shiftleft.diffgraph.DiffGraph
import scala.collection.JavaConverters._
import shapeless.HList

class NewNodeSteps[A <: NewNode: Marshallable, Labels <: HList](raw: GremlinScala.Aux[A, Labels]) {

  def store()(implicit graph: DiffGraph): Unit =
    raw.toList.foreach { newNode =>
      graph.addNode(newNode)

      // all `contained` nodes that are NewNodes also need to be added to the DiffGraph
      newNode.allContainedNodes.collect {
        case containedNode: NewNode => graph.addNode(containedNode)
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
        addEdge(graph, newNode, containedNode, EdgeTypes.CONTAINS, properties)
      }
    }

  private def addEdge(graph: DiffGraph, src: Node, dst: Node, label: String, properties: Seq[(String, AnyRef)]): Unit =
    (src, dst) match {
      case (src: NewNode, dst: NewNode) => graph.addEdge(src, dst, label, properties)
      case (src: NewNode, dst: StoredNode) =>
        graph.addEdgeToOriginal(src, dst.underlying, label, properties)
      case (src: StoredNode, dst: NewNode) =>
        graph.addEdgeFromOriginal(src.underlying, dst, label, properties)
      case (src: StoredNode, dst: StoredNode) =>
        graph.addEdgeInOriginal(src.underlying, dst.underlying, label, properties)
      case (_, _) => throw new NotImplementedError("this should never happen")
    }

}
