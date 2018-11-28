package io.shiftleft.diffgraph

import gremlin.scala._
import io.shiftleft.IdentityHashWrapper
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import java.lang.{Long => JLong}
import org.apache.tinkerpop.gremlin.structure.Vertex

case class AppliedDiffGraph(diffGraph: DiffGraph,
                            nodeToGraphId: Map[IdentityHashWrapper[NewNode], JLong])

/**
  * Component to merge diff graphs into existing (loaded) Tinkergraphs
  * */
class DiffGraphApplier {

  private var overlayNodeToTinkerNode = Map[IdentityHashWrapper[NewNode], Vertex]()
  private val InternalProperty        = "_"

  /**
    * Applies diff to existing (loaded) TinkerGraph
    **/
  def applyDiff(diffGraph: DiffGraph, graph: ScalaGraph): AppliedDiffGraph = {
    addNodes(diffGraph, graph)
    addEdges(diffGraph, graph)
    addNodeProperties(diffGraph, graph)
    addEdgeProperties(diffGraph, graph)
    AppliedDiffGraph(diffGraph, overlayNodeToTinkerNode.map {
      case (wrappedNewNode, vertex) =>
        (wrappedNewNode, vertex.id.asInstanceOf[JLong])
    })
  }

  // We are in luck: TinkerGraph will assign ids to new nodes for us
  private def addNodes(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.nodes.foreach { node =>
      val newNode = graph.addVertex(node.label)

      node.properties.map {
        case (key, value) =>
          if (!key.startsWith(InternalProperty)) {
            newNode.property(key, value)
          }
      }
      overlayNodeToTinkerNode += IdentityHashWrapper(node) -> newNode
      (IdentityHashWrapper[NewNode](node), newNode.id.asInstanceOf[JLong])
    }
  }

  private def addEdges(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.edges.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode(IdentityHashWrapper(edge.src))
      val dstTinkerNode = overlayNodeToTinkerNode(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesFromOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = overlayNodeToTinkerNode(IdentityHashWrapper(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesToOriginal.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode(IdentityHashWrapper(edge.src))
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesInOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = edge.dst
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    def tinkerAddEdge(src: Vertex, dst: Vertex, edge: DiffGraph.DiffEdge) = {
      src.addEdge(edge.label,
                  dst,
                  edge.properties.flatMap(_.productIterator.toList.map(_.asInstanceOf[AnyRef])): _*)
    }
  }

  private def addNodeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.nodeProperties.foreach { property =>
      val tinkerNode = graph.V(property.node).toList().head
      tinkerNode.property(property.propertyKey, property.propertyValue)
    }
  }

  private def addEdgeProperties(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.edgeProperties.foreach { property =>
      val tinkerEdge = property.edge
      tinkerEdge.property(property.propertyKey, property.propertyValue)
    }
  }

}
