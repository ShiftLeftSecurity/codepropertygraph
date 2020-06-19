package io.shiftleft.codepropertygraph.cpgloading

import java.io.IOException

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.StoredNode
import io.shiftleft.passes.DiffGraph
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, PropertyValue}
import io.shiftleft.utils.StringInterner
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.T
import overflowdb._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters._

private[cpgloading] object CpgOverlayLoader {
  private val logger = LogManager.getLogger(getClass)

  /**
    * Load overlays stored in the file with the name `filename`.
    */
  def load(filename: String, baseCpg: Cpg): Unit = {
    val applier = new CpgOverlayApplier(baseCpg.graph)
    ProtoCpgLoader
      .loadOverlays(filename)
      .map { overlays =>
        overlays.foreach(applier.applyDiff)
      }
      .recover {
        case e: IOException =>
          logger.error("Failed to load overlay from " + filename, e)
          Nil
      }
      .get
  }

  def loadInverse(filename: String, baseCpg: Cpg): Unit = {
    ProtoCpgLoader
      .loadDiffGraphs(filename)
      .map { diffGraphs =>
        diffGraphs.toList.reverse.map { diffGraph =>
          DiffGraph.Applier.applyDiff(DiffGraph.fromProto(diffGraph, baseCpg), baseCpg)
        }
      }
      .recover {
        case e: IOException =>
          logger.error("Failed to load overlay from " + filename, e)
          Nil
      }
      .get
  }

}

/**
  * Component to merge CPG overlay into existing graph
  *
  * @param graph the existing (loaded) graph to apply overlay to
  */
private class CpgOverlayApplier(graph: OdbGraph) {
  private val overlayNodeIdToSrcGraphNode: mutable.HashMap[Long, Node] = mutable.HashMap.empty

  // TODO use centralised string interner everywhere, maybe move to odb core - keep in mind strong references / GC.
  implicit val interner = StringInterner.noop

  /**
    * Applies diff to existing (loaded) OdbGraph
    */
  def applyDiff(overlay: CpgOverlay): Unit = {
    val inverseBuilder: DiffGraph.InverseBuilder = DiffGraph.InverseBuilder.noop
    addNodes(overlay, inverseBuilder)
    addEdges(overlay, inverseBuilder)
    addNodeProperties(overlay, inverseBuilder)
    addEdgeProperties(overlay, inverseBuilder)
  }

  def applyUndoableDiff(overlay: CpgOverlay): DiffGraph = {
    val inverseBuilder: DiffGraph.InverseBuilder = DiffGraph.InverseBuilder.newBuilder
    addNodes(overlay, inverseBuilder)
    addEdges(overlay, inverseBuilder)
    addNodeProperties(overlay, inverseBuilder)
    addEdgeProperties(overlay, inverseBuilder)
    inverseBuilder.build()
  }

  private def addNodes(overlay: CpgOverlay, inverseBuilder: DiffGraph.InverseBuilder): Unit = {
    overlay.getNodeList.asScala.foreach { node =>
      val properties = node.getPropertyList.asScala.toSeq
        .map(prop => (prop.getName.name, prop.getValue))
        .map(ProtoToCpg.toProperty)
      val newNode = graph + (node.getType.name, properties: _*)
      inverseBuilder.onNewNode(newNode.asInstanceOf[StoredNode])
      overlayNodeIdToSrcGraphNode.put(node.getKey, newNode)
    }
  }

  private def addEdges(overlay: CpgOverlay, inverseBuilder: DiffGraph.InverseBuilder) = {
    overlay.getEdgeList.asScala.foreach { edge =>
      val srcNode = getOdbNodeForOverlayId(edge.getSrc)
      val dstNode = getOdbNodeForOverlayId(edge.getDst)

      val properties = edge.getPropertyList.asScala.toSeq
        .map(prop => (prop.getName.name, prop.getValue))
        .map(ProtoToCpg.toProperty)
      val newEdge = srcNode --- (edge.getType.name, properties: _*) --> dstNode
      inverseBuilder.onNewEdge(newEdge)
    }
  }

  private def addNodeProperties(overlay: CpgOverlay, inverseBuilder: DiffGraph.InverseBuilder): Unit = {
    overlay.getNodePropertyList.asScala.foreach { additionalNodeProperty =>
      val property = additionalNodeProperty.getProperty
      val odbNode = getOdbNodeForOverlayId(additionalNodeProperty.getNodeId)
      addPropertyToElement(odbNode, property.getName.name, property.getValue, inverseBuilder)
    }
  }

  private def addEdgeProperties(overlay: CpgOverlay, inverseBuilder: DiffGraph.InverseBuilder): Unit = {
    overlay.getEdgePropertyList.asScala.foreach { additionalEdgeProperty =>
      throw new RuntimeException("Not implemented.")
    }
  }

  private def getOdbNodeForOverlayId(id: Long): Node = {
    if (overlayNodeIdToSrcGraphNode.contains(id)) {
      overlayNodeIdToSrcGraphNode(id)
    } else {
      graph
        .nodeOption(id)
        .getOrElse(throw new AssertionError(s"node with id=$id neither found in overlay nodes, nor in existing graph"))
    }
  }

  private def addPropertyToElement(odbElement: OdbElement,
                                   propertyName: String,
                                   propertyValue: PropertyValue,
                                   inverseBuilder: DiffGraph.InverseBuilder): Unit = {
    import PropertyValue.ValueCase._
    odbElement match {
      case storedNode: StoredNode =>
        inverseBuilder.onBeforeNodePropertyChange(storedNode, propertyName)
      case edge: OdbEdge =>
        inverseBuilder.onBeforeEdgePropertyChange(edge, propertyName)
    }

    propertyValue.getValueCase match {
      case INT_VALUE =>
        odbElement.setProperty(propertyName, propertyValue.getIntValue)
      case STRING_VALUE =>
        odbElement.setProperty(propertyName, propertyValue.getStringValue)
      case BOOL_VALUE =>
        odbElement.setProperty(propertyName, propertyValue.getBoolValue)
      case STRING_LIST =>
        val listBuilder = List.newBuilder[String]
        propertyValue.getStringList.getValuesList.forEach(listBuilder.addOne)
        odbElement.setProperty(propertyName, listBuilder.result)
      case VALUE_NOT_SET =>
      case valueCase =>
        throw new RuntimeException("Error: unsupported property case: " + valueCase)
    }
  }

}
