package io.shiftleft.codepropertygraph.cpgloading

import java.util.{NoSuchElementException, Collection => JCollection}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._
import io.shiftleft.utils.StringInterner

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import overflowdb._

import scala.jdk.CollectionConverters._

object ProtoToCpg {
  val logger: Logger = LoggerFactory.getLogger(classOf[ProtoToCpg])

  def toProperty(keyValue: (String, PropertyValue))(implicit interner: StringInterner): Property[Any] =
    Property(keyValue._1, toRegularType(keyValue._2))

  private def toRegularType(value: PropertyValue)(implicit interner: StringInterner): Any =
    value.getValueCase match {
      case INT_VALUE     => value.getIntValue
      case BOOL_VALUE    => value.getBoolValue
      case STRING_VALUE  => interner.intern(value.getStringValue)
      case STRING_LIST   => value.getStringList.getValuesList.asScala.map(interner.intern).toList
      case VALUE_NOT_SET => ()
      case _             => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
}

class ProtoToCpg(overflowConfig: OdbConfig = OdbConfig.withoutOverflow) {
  import ProtoToCpg._
  private val nodeFilter = new NodeFilter
  private val odbGraph =
    OdbGraph.open(overflowConfig,
                  io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
                  io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava)
  // TODO use centralised string interner everywhere, maybe move to odb core - keep in mind strong references / GC.
  implicit private val interner: StringInterner = StringInterner.makeStrongInterner()

  def graph: OdbGraph = odbGraph

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addNodeToOdb)

  private def addNodeToOdb(node: Node) = {
    val properties = node.getPropertyList.asScala.toSeq
      .map(prop => (prop.getName.name, prop.getValue))
      .map(toProperty)
    //odbGraph.addNode()
    try odbGraph + (node.getType.name, node.getKey, properties: _*) //odbGraph.addNode(node.getKey, node.getType.name, properties: _*)
    catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a node. proto:\n" + node, e)
    }
  }

  def addEdges(protoEdges: JCollection[Edge]): Unit =
    addEdges(protoEdges.asScala)

  def addEdges(protoEdges: Iterable[Edge]): Unit = {
    for (edgeProto <- protoEdges) {
      val srcNode = findNodeById(edgeProto, edgeProto.getSrc)
      val dstNode = findNodeById(edgeProto, edgeProto.getDst)
      val properties = edgeProto.getPropertyList.asScala.toSeq
        .map(prop => (prop.getName.name, prop.getValue))
        .map(toProperty)
      try {
        srcNode --- (edgeProto.getType.name, properties: _*) --> dstNode
      } catch {
        case e: IllegalArgumentException =>
          val context = "label=" + edgeProto.getType.name +
            ", srcNodeId=" + edgeProto.getSrc +
            ", dstNodeId=" + edgeProto.getDst +
            ", srcNode=" + srcNode +
            ", dstNode=" + dstNode
          logger.warn("Failed to insert an edge. context: " + context, e)
      }
    }
  }

  def build(): Cpg = new Cpg(odbGraph)

  private def findNodeById(edge: Edge, nodeId: Long): overflowdb.Node = {
    if (nodeId == -1)
      throw new IllegalArgumentException(
        "edge " + edge + " has illegal src|dst node. something seems wrong with the cpg")
    odbGraph
      .nodeOption(nodeId)
      .getOrElse(
        throw new NoSuchElementException(
          "Couldn't find src|dst node " + nodeId + " for edge " + edge + " of type " + edge.getType.name))
  }

}
