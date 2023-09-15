package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.misc.Misc.toShortSafely
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.{Edge, Node}
import io.shiftleft.proto.cpg.Cpg.PropertyValue
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase.*
import io.shiftleft.utils.StringInterner
import org.slf4j.{Logger, LoggerFactory}

import java.nio.file.Path
import java.util.{NoSuchElementException, Collection as JCollection}
import scala.jdk.CollectionConverters.*

object ProtoToCpg {
  val logger: Logger = LoggerFactory.getLogger(getClass)

//  def toProperty(keyValue: (String, PropertyValue))(implicit interner: StringInterner): Property[Any] =
//    new Property(keyValue._1, toRegularType(keyValue._2))

//  private def toRegularType(value: PropertyValue)(implicit interner: StringInterner): Any =
//    value.getValueCase match {
//      case INT_VALUE     => value.getIntValue
//      case BOOL_VALUE    => value.getBoolValue
//      case STRING_VALUE  => interner.intern(value.getStringValue)
//      case STRING_LIST   => value.getStringList.getValuesList.asScala.map(interner.intern).toList
//      case VALUE_NOT_SET => ()
//      case _             => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
//    }
}

class ProtoToCpg(storagePath: Option[Path]) {
  import ProtoToCpg._
  private val nodeFilter = new NodeFilter
  private val graphBuilder = Cpg.newDiffGraphBuilder
  private val schema = io.shiftleft.codepropertygraph.generated.GraphSchema
  // TODO use centralised string interner everywhere, maybe move to flatgraph core - keep in mind strong references / GC.
  implicit private val interner: StringInterner = StringInterner.makeStrongInterner()

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit =
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(addNodeToGraph)

  private def addNodeToGraph(node: Node) = {
//    val properties = node.getPropertyList.asScala.toSeq
//      .map(prop => (prop.getName.name, prop.getValue))
//      .map(toProperty)
    try {
      if (node.getKey() == -1) {
        throw new IllegalArgumentException("node has illegal key -1. Something is wrong with the cpg.")
      }

      // dummy code - TODO implement differently, using the new 'odb-convert-lib'
      val nodeKind = schema.nodeKindByLabel(node.getType.name())
      // val newNodeFactory = schema.getNewNodeFactory(nodeKind)
      // val dnode = newNodeFactory.create()  // no properties
      // buffer.add(dnode) // correlation between proto.Node and dnode is the order in the iterator
      // // ... later on, second pass
      // val propertyKind = schema.getPropertyKindByName(...)
      // graphBuilder.setNodeProperty(dnode.storedRef.get, propertyKind, value)




      // TODO store node key -> object mapping in some internal temp map for future lookup by proto key
      val dnode = flatgraph.GenericDNode(nodeKind.toShortSafely)
      graphBuilder.addNode(dnode)
      ???
      // TODO how do i set properties for a dnode? `graphBuilder.setNodeProperty()` is only for GNodes
      // looking at GraphTests it looks like we have to do this in two rounds, but double check with Bernhard
//      odbGraph.+(node.getType.name, node.getKey, properties: _*)
    } catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a node. proto:\n" + node, e)
    }
  }

  def addEdge(dst: Long, src: Long, typ: EdgeType, propertyMaybe: Option[Edge.Property]): Unit = {
    val srcNode = findNodeById(src, typ)
    val dstNode = findNodeById(dst, typ)
    try {
      propertyMaybe match {
        case None =>
          graphBuilder.addEdge(srcNode, dstNode, typ.name())
        case Some(property) =>
          graphBuilder.addEdge(srcNode, dstNode, typ.name(), property.getValue)
      }
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

  def build(): Cpg = {
    val cpg = storagePath match {
      case None => Cpg.empty
      case Some(path) => Cpg.withStorage(path)
    }
    flatgraph.DiffGraphApplier.applyDiff(cpg.graph, graphBuilder)
    cpg
  }

  private def findNodeById(nodeId: Long, typ: EdgeType): flatgraph.DNode = {
    if (nodeId == -1) {
      throw new IllegalArgumentException("Illegal src|dst node ID -1 on edge of type " + typ.name)
    } else {
//      odbGraph
//        .nodeOption(nodeId)
//        .getOrElse(
//          throw new NoSuchElementException("Couldn't find src|dst node " + nodeId + " for edge of type " + typ.name)
//        )
      // TODO lookup in some internal map, now that ids won't be assignable on creation?
      ???
    }

  }

}
