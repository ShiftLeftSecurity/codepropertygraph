package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.*
import flatgraph.misc.ConversionException
import flatgraph.misc.Conversions.toShortSafely
import io.shiftleft.codepropertygraph.generated.{Cpg, PropertyKeys}
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase.*
import io.shiftleft.proto.cpg.Cpg.{CpgStruct, PropertyValue}
import io.shiftleft.utils.StringInterner
import org.slf4j.{Logger, LoggerFactory}

import java.nio.file.Path
import java.util.{NoSuchElementException, Collection as JCollection}
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

object ProtoToCpg {
  val logger: Logger = LoggerFactory.getLogger(getClass)

  def apply(protoCpg: CpgStruct, cpg: Cpg, nodeFilter: NodeFilter = new NodeFilter): Unit = {
    // TODO use centralised string interner everywhere, maybe move to flatgraph core - keep in mind strong references / GC.
    implicit val interner: StringInterner = StringInterner.makeStrongInterner()
    
    def nodesIter = protoCpg.getNodeList.iterator().asScala.filter(nodeFilter.filterNode)

    // we need to run two passes of DiffGraphs: one to add the nodes, and one to add edges, set node properties etc
    val protoNodeIdToGNode = addNodesRaw(nodesIter, cpg.graph)

    // add vertex properties
    val diffGraph = Cpg.newDiffGraphBuilder
    nodesIter.foreach { protoNode =>
      val protoNodeId = protoNode.getKey
      lazy val gNode = protoNodeIdToGNode.get(protoNodeId)
        .getOrElse(throw new ConversionException(s"node with proto node id=$protoNodeId not found in graph"))
      protoNode.getPropertyList.iterator().asScala.foreach { protoProperty =>
        diffGraph.setNodeProperty(gNode, protoProperty.getName.name(), extractPropertyValue(protoProperty.getValue()))
      }
    }

    protoCpg.getEdgeList.iterator().asScala.foreach { protoEdge =>
      List(protoEdge.getSrc, protoEdge.getDst).map(protoNodeIdToGNode.get) match {
        case List(Some(srcNode), Some(dstNode)) =>
          diffGraph.addEdge(srcNode, dstNode, protoEdge.getType.name(), extractEdgePropertyValue(protoEdge))
        case _ => // at least one of the nodes doesn't exist in the cpg, most likely because it was filtered out - ignore
      }
    }
    DiffGraphApplier.applyDiff(cpg.graph, diffGraph)
  }

  private def addNodesRaw(protoNodes: Iterator[CpgStruct.Node], graph: Graph): Map[Long, GNode] = {
    val diffGraphForRawNodes  = new DiffGraphBuilder(graph.schema)
    val protoNodeIdToGNode = mutable.Map.empty[Long, GenericDNode]
    protoNodes.foreach { protoNode =>
      val newNode = new GenericDNode(graph.schema.getNodeKindByLabel(protoNode.getType.name()).toShortSafely)
      diffGraphForRawNodes.addNode(newNode)
      protoNodeIdToGNode.put(protoNode.getKey, newNode)
    }
    DiffGraphApplier.applyDiff(graph, diffGraphForRawNodes)
    protoNodeIdToGNode.view.mapValues(_.storedRef.get).toMap
  }

  private def extractPropertyValue(value: PropertyValue)(implicit interner: StringInterner): Any = {
    value.getValueCase match {
      case INT_VALUE => value.getIntValue
      case BOOL_VALUE => value.getBoolValue
      case STRING_VALUE => interner.intern(value.getStringValue)
      case STRING_LIST => value.getStringList.getValuesList.asScala.map(interner.intern).toList
      case VALUE_NOT_SET => null
      case _ => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
  }

  /** flatgraph only supports 0..1 edge properties */
  private def extractEdgePropertyValue(protoEdge: CpgStruct.Edge)(implicit interner: StringInterner): Any = {
    val protoProperties = protoEdge.getPropertyList.asScala
    if (protoProperties.isEmpty) 
      null
    else if (protoProperties.size == 1)
      extractPropertyValue(protoProperties.head.getValue)
    else
      throw new IllegalArgumentException(s"flatgraph only supports zero or one edge properties, but the given edge has ${protoProperties.size} properties: $protoProperties")
  }

}
