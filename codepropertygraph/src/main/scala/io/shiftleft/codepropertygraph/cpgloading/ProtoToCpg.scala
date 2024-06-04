package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase._
import io.shiftleft.utils.StringInterner
import org.slf4j.{Logger, LoggerFactory}
import overflowdb._

import java.util.{Collection => JCollection, NoSuchElementException}
import scala.jdk.CollectionConverters._

object ProtoToCpg {
  val logger: Logger = LoggerFactory.getLogger(classOf[ProtoToCpg])

  def toProperty(keyValue: (String, PropertyValue))(implicit interner: StringInterner): Property[Any] =
    new Property(keyValue._1, toRegularType(keyValue._2))

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

class ProtoToCpg(overflowConfig: Config = Config.withoutOverflow) {
  import ProtoToCpg._
  private val nodeFilter = new NodeFilter
  private val odbGraph   = Cpg.withConfig(overflowConfig).graph
  // TODO use centralised string interner everywhere, maybe move to odb core - keep in mind strong references / GC.
  implicit private val interner: StringInterner = StringInterner.makeStrongInterner()

  def graph: Graph = odbGraph

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addNodeToOdb)

  private def addNodeToOdb(node: Node) = {
    val properties = node.getPropertyList.asScala.toSeq
      .map(prop => (prop.getName.name, prop.getValue))
      .map(toProperty)
    try {
      if (node.getKey() == -1) {
        throw new IllegalArgumentException("node has illegal key -1. Something is wrong with the cpg.")
      }
      odbGraph.+(node.getType.name, node.getKey, properties*)
    } catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a node. proto:\n" + node, e)
    }
  }

  def addEdge(dst: Long, src: Long, typ: EdgeType, properties: Iterable[Edge.Property]): Unit = {
    val srcNode = findNodeById(src, typ)
    val dstNode = findNodeById(dst, typ)
    val propertyPairs = properties.toSeq
      .map(prop => (prop.getName.name, prop.getValue))
      .map(toProperty)
    try {
      srcNode.---(typ.name, propertyPairs*) --> dstNode
    } catch {
      case e: IllegalArgumentException =>
        val context = "label=" + typ.name +
          ", srcNodeId=" + src +
          ", dstNodeId=" + dst +
          ", srcNode=" + srcNode +
          ", dstNode=" + dstNode
        logger.warn("Failed to insert an edge. context: " + context, e)
    }
  }

  def build(): Cpg = new Cpg(odbGraph)

  private def findNodeById(nodeId: Long, typ: EdgeType): overflowdb.Node = {
    if (nodeId == -1)
      throw new IllegalArgumentException("Illegal src|dst node ID -1 on edge of type " + typ.name)
    odbGraph
      .nodeOption(nodeId)
      .getOrElse(
        throw new NoSuchElementException("Couldn't find src|dst node " + nodeId + " for edge of type " + typ.name)
      )
  }

}
