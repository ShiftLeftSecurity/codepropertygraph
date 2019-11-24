package io.shiftleft.codepropertygraph.cpgloading

import java.lang.{Boolean => JBoolean, Integer => JInt, Long => JLong}
import java.util.{NoSuchElementException, Collection => JCollection, Iterator => JIterator}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import org.apache.commons.configuration.Configuration
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.{T, Vertex}
import io.shiftleft.overflowdb.OdbGraph

import scala.jdk.CollectionConverters._
import scala.collection.mutable.ArrayBuffer
import io.shiftleft.overflowdb.OdbConfig
import io.shiftleft.utils.StringInterner

object ProtoToCpg {
  val logger: Logger = LogManager.getLogger(classOf[ProtoToCpg])

  def addProperties(keyValues: ArrayBuffer[AnyRef],
                    name: String,
                    value: PropertyValue,
                    interner: StringInterner = StringInterner.noop): Unit = {
    import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._
    value.getValueCase match {
      case INT_VALUE =>
        keyValues += interner.intern(name)
        keyValues += (value.getIntValue: JInt)
      case STRING_VALUE =>
        keyValues += interner.intern(name)
        keyValues += interner.intern(value.getStringValue)
      case BOOL_VALUE =>
        keyValues += interner.intern(name)
        keyValues += (value.getBoolValue: JBoolean)
      case STRING_LIST =>
        value.getStringList.getValuesList.asScala.foreach { elem: String =>
          keyValues += interner.intern(name)
          keyValues += interner.intern(elem)
        }
      case VALUE_NOT_SET => ()
      case _ =>
        throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
  }
}

class ProtoToCpg(overflowConfig: OdbConfig = OdbConfig.withoutOverflow) {
  import ProtoToCpg._
  private val nodeFilter = new NodeFilter
  private val odbGraph =
    OdbGraph.open(overflowConfig,
                  io.shiftleft.codepropertygraph.generated.nodes.Factories.AllAsJava,
                  io.shiftleft.codepropertygraph.generated.edges.Factories.AllAsJava)
  private val interner: StringInterner = StringInterner.makeStrongInterner()

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addVertexToOdbGraph)

  private def addVertexToOdbGraph(node: Node) = {
    try odbGraph.addVertex(nodeToArray(node): _*)
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
      val properties = edge.getPropertyList.asScala
      val keyValues = new ArrayBuffer[AnyRef](2 * properties.size)
      for (edgeProperty <- properties) {
        addProperties(keyValues, edgeProperty.getName.name(), edgeProperty.getValue, interner)
      }
      try {
        srcVertex.addEdge(edge.getType.name(), dstVertex, keyValues.toArray: _*)
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

  def build(): Cpg = new Cpg(odbGraph)

  private def findVertexById(edge: Edge, nodeId: Long): Vertex = {
    if (nodeId == -1)
      throw new IllegalArgumentException(
        "edge " + edge + " has illegal src|dst node. something seems wrong with the cpg")
    val vertices: JIterator[Vertex] = odbGraph.vertices(nodeId: JLong)
    if (!vertices.hasNext)
      throw new NoSuchElementException(
        "Couldn't find src|dst node " + nodeId + " for edge " + edge + " of type " + edge.getType.name)
    vertices.next
  }

  private def nodeToArray(node: Node): Array[AnyRef] = {
    val props = node.getPropertyList
    val keyValues = new ArrayBuffer[AnyRef](4 + (2 * props.size()))
    keyValues += T.id
    keyValues += (node.getKey: JLong)
    keyValues += T.label
    keyValues += node.getType.name()
    for (prop <- props.asScala) {
      addProperties(keyValues, prop.getName.name, prop.getValue, interner)
    }
    keyValues.toArray
  }
}
