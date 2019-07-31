package io.shiftleft.codepropertygraph.cpgloading

import java.lang.{Boolean => JBoolean, Integer => JInt, Long => JLong}
import java.util.{NoSuchElementException, Collection => JCollection, Iterator => JIterator}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import org.apache.commons.configuration.Configuration
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.{T, Vertex}
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

object ProtoToCpg {
  val logger: Logger = LogManager.getLogger(classOf[ProtoToCpg])

  def addProperties(kvs: ArrayBuffer[AnyRef], name: String, value: PropertyValue): Unit = {
    import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._
    value.getValueCase match {
      case INT_VALUE =>
        kvs += name
        kvs += (value.getIntValue: JInt)
      case STRING_VALUE =>
        kvs += name
        kvs += value.getStringValue
      case BOOL_VALUE =>
        kvs += name
        kvs += (value.getBoolValue: JBoolean)
      case STRING_LIST =>
        value.getStringList.getValuesList.asScala.foreach { elem: String =>
          kvs += name
          kvs += elem
        }
      case VALUE_NOT_SET => ()
      case _ =>
        throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
  }
}

class ProtoToCpg(val overflowConfig: OverflowDbConfig = OverflowDbConfig.withDefaults) {
  private val nodeFilter: NodeFilter = new NodeFilter
  private val tinkerGraph: TinkerGraph = mkTinkerGraph

  import ProtoToCpg._

  private def mkTinkerGraphConfig: Configuration = {
    val conf = TinkerGraph.EMPTY_CONFIGURATION
    if (overflowConfig.enabled)
      setupOverflowProperties(conf)
    else
      disableOverflowProperties(conf)
    conf
  }

  private def setupOverflowProperties(conf: Configuration): Unit = {
    conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, true)
    conf.setProperty(
      TinkerGraph.GREMLIN_TINKERGRAPH_OVERFLOW_HEAP_PERCENTAGE_THRESHOLD,
      overflowConfig.heapPercentageThreshold)
    overflowConfig.graphLocation.foreach(
      path => conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_GRAPH_LOCATION, path))
  }

  private def disableOverflowProperties(conf: Configuration): Unit = {
    conf.setProperty(TinkerGraph.GREMLIN_TINKERGRAPH_ONDISK_OVERFLOW_ENABLED, false)
  }

  private def mkTinkerGraph: TinkerGraph =
    TinkerGraph.open(
      mkTinkerGraphConfig,
      io.shiftleft.codepropertygraph.generated.nodes.Factories.AllAsJava,
      io.shiftleft.codepropertygraph.generated.edges.Factories.AllAsJava
    )

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addVertexToTinkerGraph)

  private def addVertexToTinkerGraph(node: Node) = {
    try
      tinkerGraph.addVertex(nodeToArray(node): _*)
    catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a vertex. proto:\n" + node, e)
    }
  }

  def addEdges(protoEdges: JCollection[Edge]): Unit =
    addEdges(protoEdges.asScala)

  def addEdges(protoEdges: Iterable[Edge]): Unit = {
    for (edge <- protoEdges) {
      val srcVertex = findVertexById(edge, edge.getSrc)
      val dstVertex = findVertexById(edge, edge.getDst)
      val properties: Seq[Edge.Property] = edge.getPropertyList.asScala
      val kvs = new ArrayBuffer[AnyRef](2 * properties.size)
      for (edgeProperty <- properties) {
        addProperties(kvs, edgeProperty.getName.name(), edgeProperty.getValue)
      }
      try {
        srcVertex.addEdge(edge.getType.name(), dstVertex, kvs.toArray: _*)
      } catch {
        case e: IllegalArgumentException =>
          val context = "label=" + edge.getType.name +
            ", srcNodeId=" + edge.getSrc +
            ", dstNodeId=" + edge.getDst +
            ", srcVertex=" + srcVertex +
            ", dstVertex=" + dstVertex
          logger.warn("Failed to insert an edge. context: " + context, e)
      }
    }
  }

  def build(): Cpg = new Cpg(tinkerGraph)

  private def findVertexById(edge: Edge, nodeId: Long): Vertex = {
    if (nodeId == -1)
      throw new IllegalArgumentException("edge " + edge + " has illegal src|dst node. something seems wrong with the cpg")
    val vertices: JIterator[Vertex] = tinkerGraph.vertices(nodeId: JLong)
    if (!vertices.hasNext)
      throw new NoSuchElementException("Couldn't find src|dst node " + nodeId + " for edge " + edge + " of type " + edge.getType.name)
    vertices.next
  }

  private def nodeToArray(node: Node): Array[AnyRef] = {
    val props = node.getPropertyList
    val kvs = new ArrayBuffer[AnyRef](4 + (2 * props.size()))
    kvs += T.id
    kvs += (node.getKey: JLong)
    kvs += T.label
    kvs += node.getType.name()
    for (prop <- props.asScala) {
      addProperties(kvs, prop.getName.name, prop.getValue)
    }
    kvs.toArray
  }
}
