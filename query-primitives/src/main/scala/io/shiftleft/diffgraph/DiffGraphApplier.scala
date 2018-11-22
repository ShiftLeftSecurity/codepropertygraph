package io.shiftleft.diffgraph

import gremlin.scala._
import io.shiftleft.IdentityHashCode
import java.lang.{Long => JLong}
import org.apache.tinkerpop.gremlin.structure.Vertex

import scala.collection.mutable

case class AppliedDiffGraph(diffGraph: DiffGraph, nodeToGraphId: Map[IdentityHashCode, JLong])

/**
  * Component to merge diff graphs into existing (loaded) Tinkergraphs
  * */
class DiffGraphApplier {

  private val overlayNodeToTinkerNode: mutable.HashMap[Int, Vertex] = mutable.HashMap()
  private val InternalProperty                                      = "_"

  /**
    * Applies diff to existing (loaded) TinkerGraph
    **/
  def applyDiff(diffGraph: DiffGraph, graph: ScalaGraph): AppliedDiffGraph = {
    val nodeToGraphId = addNodes(diffGraph, graph)
    addEdges(diffGraph, graph)
    addNodeProperties(diffGraph, graph)
    addEdgeProperties(diffGraph, graph)
    AppliedDiffGraph(diffGraph, nodeToGraphId)
  }

  // We are in luck: TinkerGraph will assign ids to new nodes for us
  private def addNodes(diffGraph: DiffGraph, graph: ScalaGraph): Map[IdentityHashCode, JLong] = {
    diffGraph.nodes.map { node =>
      val newNode = graph.addVertex(node.label)
      node.properties.map {
        case (key, value) =>
          if (!key.startsWith(InternalProperty)) {
            newNode.property(key, value)
          }
      }
      overlayNodeToTinkerNode.put(System.identityHashCode(node), newNode)
      (IdentityHashCode(node), newNode.id.asInstanceOf[JLong])
    }.toMap
  }

  private def addEdges(diffGraph: DiffGraph, graph: ScalaGraph): Unit = {
    diffGraph.edges.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode(System.identityHashCode(edge.src))
      val dstTinkerNode = overlayNodeToTinkerNode(System.identityHashCode(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesFromOriginal.foreach { edge =>
      val srcTinkerNode = edge.src
      val dstTinkerNode = overlayNodeToTinkerNode(System.identityHashCode(edge.dst))
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edge)
    }

    diffGraph.edgesToOriginal.foreach { edge =>
      val srcTinkerNode = overlayNodeToTinkerNode(System.identityHashCode(edge.src))
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
