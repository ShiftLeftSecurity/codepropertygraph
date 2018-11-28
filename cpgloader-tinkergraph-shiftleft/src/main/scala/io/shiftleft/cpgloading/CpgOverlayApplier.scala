package io.shiftleft.cpgloading

import gremlin.scala._
import io.shiftleft.queryprimitives.steps.Implicits.JavaIteratorDeco
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}
import java.lang.{Long => JLong}
import org.apache.tinkerpop.gremlin.structure.{T, Vertex}

import scala.collection.mutable
import scala.collection.JavaConverters._

/**
  * Component to merge CPG overlay into existing graph
  * */
class CpgOverlayApplier {

  private val overlayNodeIdToSrcGraphNode: mutable.HashMap[Long, Vertex] = mutable.HashMap()
  private val overlayEdgeIdToSrcGraphEdge: mutable.HashMap[Long, Edge]   = mutable.HashMap()
  private val InternalProperty                                           = "_"

  /**
    * Applies diff to existing (loaded) TinkerGraph
    **/
  def applyDiff(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    addNodes(overlay, graph)
    addEdges(overlay, graph)
    addNodeProperties(overlay, graph)
    addEdgeProperties(overlay, graph)
  }

  private def addNodes(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    assert(graph.graph.features.vertex.supportsUserSuppliedIds,
           "this currently only works for graphs that allow user supplied ids")

    overlay.getNodeList.asScala.foreach { node =>
      val label   = node.getType.toString
      val id      = node.getKey.asInstanceOf[JLong]
      val newNode = graph.graph.addVertex(T.label, label, T.id, id)

      node.getPropertyList.asScala.foreach { property =>
        ProtoToCpgBase.addPropertyToElement(newNode, property.getName.name, property.getValue)
      }
      overlayNodeIdToSrcGraphNode.put(node.getKey, newNode)
    }
  }

  private def addEdges(overlay: CpgOverlay, graph: ScalaGraph) = {
    overlay.getEdgeList.asScala.foreach { edge =>
      val srcTinkerNode = getVertexForOverlayId(edge.getSrc, graph)
      val dstTinkerNode = getVertexForOverlayId(edge.getDst, graph)

      val newEdge = srcTinkerNode.addEdge(edge.getType.toString, dstTinkerNode)
      edge.getPropertyList.asScala.foreach { property =>
        // TODO MP refactor
        ProtoToCpgBase.addPropertyToElement(newEdge, property.getName.name, property.getValue)
      }
      overlayEdgeIdToSrcGraphEdge.put(newEdge.id.asInstanceOf[Long], newEdge)
    }
  }

  private def addNodeProperties(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    overlay.getNodePropertyList.asScala.foreach { additionalNodeProperty =>
      val property   = additionalNodeProperty.getProperty
      val tinkerNode = getVertexForOverlayId(additionalNodeProperty.getNodeId, graph)
      // TODO MP refactor
      ProtoToCpgBase.addPropertyToElement(tinkerNode, property.getName.name, property.getValue)
    }
  }

  private def addEdgeProperties(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    overlay.getEdgePropertyList.asScala.foreach { additionalEdgeProperty =>
      val property   = additionalEdgeProperty.getProperty
      val tinkerEdge = getEdgeForOverlayId(additionalEdgeProperty.getEdgeId, graph)
      // TODO MP refactor
      ProtoToCpgBase.addPropertyToElement(tinkerEdge, property.getName.name, property.getValue)
    }
  }

  private def getVertexForOverlayId(id: Long, graph: ScalaGraph): Vertex = {
    if (overlayNodeIdToSrcGraphNode.contains(id)) {
      overlayNodeIdToSrcGraphNode(id)
    } else {
      val iter = graph.graph.vertices(id.asInstanceOf[Object])
      assert(iter.hasNext,
             s"node with id=$id neither found in overlay nodes, nor in existing graph")
      iter.nextChecked
    }
  }

  private def getEdgeForOverlayId(id: Long, graph: ScalaGraph): Edge = {
    if (overlayEdgeIdToSrcGraphEdge.contains(id)) {
      overlayEdgeIdToSrcGraphEdge(id)
    } else {
      val iter = graph.graph.edges(id.asInstanceOf[Object])
      assert(iter.hasNext,
             s"edge with id=$id neither found in overlay edges, nor in existing graph")
      iter.nextChecked
    }
  }

}
