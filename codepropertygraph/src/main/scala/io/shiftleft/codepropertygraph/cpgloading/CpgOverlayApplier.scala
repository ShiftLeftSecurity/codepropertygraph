package io.shiftleft.codepropertygraph.cpgloading

import java.lang.{Long => JLong}
import java.util.{ArrayList => JArrayList}

import gremlin.scala._
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, PropertyValue}
import org.apache.tinkerpop.gremlin.structure.{T, Vertex, VertexProperty}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Component to merge CPG overlay into existing graph
  * */
class CpgOverlayApplier {

  private val overlayNodeIdToSrcGraphNode: mutable.HashMap[Long, Vertex] = mutable.HashMap()
  private val overlayEdgeIdToSrcGraphEdge: mutable.HashMap[Long, Edge] = mutable.HashMap()

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
      val id = node.getKey
      val properties = node.getPropertyList.asScala

      val keyValues = new JArrayList[AnyRef](4 + (2 * properties.size))
      keyValues.add(T.id)
      keyValues.add(node.getKey: JLong)
      keyValues.add(T.label)
      keyValues.add(node.getType.name)
      properties.foreach { property =>
        ProtoToCpg.addProperties(keyValues, property.getName.name, property.getValue)
      }
      val newNode = graph.graph.addVertex(keyValues.toArray: _*)

      overlayNodeIdToSrcGraphNode.put(id, newNode)
    }
  }

  private def addEdges(overlay: CpgOverlay, graph: ScalaGraph) = {
    overlay.getEdgeList.asScala.foreach { edge =>
      val srcTinkerNode = getVertexForOverlayId(edge.getSrc, graph)
      val dstTinkerNode = getVertexForOverlayId(edge.getDst, graph)

      val properties = edge.getPropertyList.asScala
      val keyValues = new JArrayList[AnyRef](2 * properties.size)
      properties.foreach { property =>
        ProtoToCpg.addProperties(keyValues, property.getName.name, property.getValue)
      }
      val newEdge =
        srcTinkerNode.addEdge(edge.getType.toString, dstTinkerNode, keyValues.toArray: _*)
      overlayEdgeIdToSrcGraphEdge.put(newEdge.id.asInstanceOf[Long], newEdge)
    }
  }

  private def addNodeProperties(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    overlay.getNodePropertyList.asScala.foreach { additionalNodeProperty =>
      val property = additionalNodeProperty.getProperty
      val tinkerNode = getVertexForOverlayId(additionalNodeProperty.getNodeId, graph)
      addPropertyToElement(tinkerNode, property.getName.name, property.getValue)
    }
  }

  private def addEdgeProperties(overlay: CpgOverlay, graph: ScalaGraph): Unit = {
    overlay.getEdgePropertyList.asScala.foreach { additionalEdgeProperty =>
      val property = additionalEdgeProperty.getProperty
      val tinkerEdge = getEdgeForOverlayId(additionalEdgeProperty.getEdgeId, graph)
      addPropertyToElement(tinkerEdge, property.getName.name, property.getValue)
    }
  }

  private def getVertexForOverlayId(id: Long, graph: ScalaGraph): Vertex = {
    if (overlayNodeIdToSrcGraphNode.contains(id)) {
      overlayNodeIdToSrcGraphNode(id)
    } else {
      val iter = graph.graph.vertices(id.asInstanceOf[Object])
      assert(iter.hasNext, s"node with id=$id neither found in overlay nodes, nor in existing graph")
      nextChecked(iter)
    }
  }

  private def getEdgeForOverlayId(id: Long, graph: ScalaGraph): Edge = {
    if (overlayEdgeIdToSrcGraphEdge.contains(id)) {
      overlayEdgeIdToSrcGraphEdge(id)
    } else {
      val iter = graph.graph.edges(id.asInstanceOf[Object])
      assert(iter.hasNext, s"edge with id=$id neither found in overlay edges, nor in existing graph")
      nextChecked(iter)
    }
  }

  private def addPropertyToElement(tinkerElement: Element, propertyName: String, propertyValue: PropertyValue): Unit = {
    import PropertyValue.ValueCase._
    propertyValue.getValueCase match {
      case INT_VALUE =>
        tinkerElement.property(propertyName, propertyValue.getIntValue)
      case STRING_VALUE =>
        tinkerElement.property(propertyName, propertyValue.getStringValue)
      case BOOL_VALUE =>
        tinkerElement.property(propertyName, propertyValue.getBoolValue)
      case STRING_LIST if tinkerElement.isInstanceOf[Vertex] =>
        propertyValue.getStringList.getValuesList.forEach { value: String =>
          tinkerElement
            .asInstanceOf[Vertex]
            .property(VertexProperty.Cardinality.list, propertyName, value)
        }
      case STRING_LIST =>
        val propertyList = new JArrayList[AnyRef]()
        propertyList.addAll(propertyValue.getStringList.getValuesList)
        tinkerElement.property(propertyName, propertyList)
      case VALUE_NOT_SET =>
      case valueCase =>
        throw new RuntimeException("Error: unsupported property case: " + valueCase)
    }
  }

  private def nextChecked[T](iterator: java.util.Iterator[T]): T = {
    try {
      iterator.next
    } catch {
      case _: NoSuchElementException =>
        throw new NoSuchElementException()
    }
  }

}
