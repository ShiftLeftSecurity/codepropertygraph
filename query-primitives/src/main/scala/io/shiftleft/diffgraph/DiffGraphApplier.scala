package io.shiftleft.diffgraph

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import java.lang.{Long => JLong}
import java.util

import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import org.apache.tinkerpop.gremlin.structure.Vertex

case class AppliedDiffGraph(diffGraph: DiffGraph,
                            private val nodeToTinkerNode: util.HashMap[IdentityHashWrapper[NewNode], Vertex]) {
  def nodeToGraphId(wrappedNode: IdentityHashWrapper[NewNode]): JLong = {
    nodeToTinkerNode.get(wrappedNode).id.asInstanceOf[JLong]
  }
}

/**
  * Component to merge diff graphs into existing (loaded) Tinkergraphs
  * */
class DiffGraphApplier {
  import DiffGraphApplier.InternalProperty

  private val overlayNodeToTinkerNode = new util.HashMap[IdentityHashWrapper[NewNode], Vertex]()

  /**
    * Applies diff to existing (loaded) TinkerGraph
    **/
  def applyDiff(diffGraph: DiffGraph, graph: ScalaGraph): AppliedDiffGraph = {
    addNodes(diffGraph, graph)
    addEdges(diffGraph, graph)
    addNodeProperties(diffGraph, graph)
    addEdgeProperties(diffGraph, graph)
    AppliedDiffGraph(diffGraph, overlayNodeToTinkerNode)
  }

  // We are in luck: TinkerGraph will assign ids to new nodes for us
  private def addNodes(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    val nodeTinkerNodePairs = diffGraph.nodes.map { node =>
      val newNode = graph.graph.addVertex(node.label)

      node.properties.filter { case (key, _) => !key.startsWith(InternalProperty) }.foreach {
        case (key, value: Traversable[_]) =>
          value.foreach { value =>
            newNode.property(Cardinality.list, key, value)
          }
        case (key, value) =>
          newNode.property(key, value)
      }
      (node, newNode)
    }
    nodeTinkerNodePairs.foreach {
      case (node, tinkerNode) =>
        overlayNodeToTinkerNode.put(IdentityHashWrapper(node), tinkerNode)
    }
  }

  private def addEdges(diffGraph: DiffGraph, graph: ScalaGraph) = {
    diffGraph.edges.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.src))
      val dstTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesFromOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesToOriginal.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode.get(IdentityHashWrapper(edge.src))
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesInOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    def tinkerAddEdge(src: Vertex, dst: Vertex, edge: DiffGraph.DiffEdge) = {
      val tinkerEdge = src.addEdge(edge.label, dst)

      edge.properties.foreach {
        case (key, value) =>
          tinkerEdge.property(key, value)
      }
    }
  }

  private def addNodeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    def lookupNode(id: JLong): Vertex =
      graph.graph.vertices(id).nextChecked

    diffGraph.nodeProperties.foreach { property =>
      val vertex = lookupNode(property.nodeId)
      vertex.property(property.propertyKey, property.propertyValue)
    }
  }

  private def addEdgeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    def lookupEdge(id: JLong): Edge =
      graph.graph.edges(id).nextChecked

    diffGraph.edgeProperties.foreach { property =>
      val edge = lookupEdge(property.edgeId)
      edge.property(property.propertyKey, property.propertyValue)
    }
  }

}

object DiffGraphApplier {
  private val InternalProperty = "_"
}
