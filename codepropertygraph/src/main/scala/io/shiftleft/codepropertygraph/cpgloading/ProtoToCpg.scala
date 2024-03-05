package io.shiftleft.codepropertygraph.cpgloading

import flatgraph.{DiffGraphBuilder, GNode}
import flatgraph.misc.Conversions.toShortSafely
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.PropertyKeys
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
  private val schema = io.shiftleft.codepropertygraph.generated.GraphSchema
  // TODO use centralised string interner everywhere, maybe move to flatgraph core - keep in mind strong references / GC.
  implicit private val interner: StringInterner = StringInterner.makeStrongInterner()
  
  // we need to run two passes of DiffGraphs: one to add the nodes, and one to add edges, set node properties etc
  private val diffGraphForAddingNodes = Cpg.newDiffGraphBuilder
  private val diffGraphAdditionalOps = Seq.newBuilder[DiffGraphBuilder => Any]

  def addNodes(nodes: JCollection[Node]): Unit =
    addNodes(nodes.asScala)

  def addNodes(nodes: Iterable[Node]): Unit = {
    nodes
      .filter(nodeFilter.filterNode)
      .foreach(protoNode => addNodeToGraph(protoNode))
  }

  private def addNodeToGraph(protoNode: Node): (Long, GNode) = {
//    val properties = node.getPropertyList.asScala.toSeq
//      .map(prop => (prop.getName.name, prop.getValue))
//      .map(toProperty)
    try {
      if (protoNode.getKey() == -1) {
        throw new IllegalArgumentException("node has illegal key -1. Something is wrong with the cpg.")
      }

      // dummy code - TODO implement differently, using the new 'odb-convert-lib'
      protoNode.getKey
      val nodeKind = schema.nodeKindByLabel(protoNode.getType.name())
      // buffer.add(dnode) // correlation between proto.Node and dnode is the order in the iterator
       // ... later on, second pass
      // val propertyKind = schema.getPropertyKindByName(...)
      // graphBuilder.setNodeProperty(dnode.storedRef.get, propertyKind, value)


      // TODO store node key -> object mapping in some internal temp map for future lookup by proto key
      val dnode = flatgraph.GenericDNode(nodeKind.toShortSafely)
      diffGraphForAddingNodes.addNode(dnode)
      // TODO how do i set properties for a dnode? `graphBuilder.setNodeProperty()` is only for GNodes
      // looking at GraphTests it looks like we have to do this in two rounds, but double check with Bernhard
//      odbGraph.+(node.getType.name, node.getKey, properties: _*)
      ???
    } catch {
      case e: Exception =>
        throw new RuntimeException("Failed to insert a node. proto:\n" + protoNode, e)
    }
  }

  def addEdge(dst: Long, src: Long, typ: EdgeType, propertyMaybe: Option[Edge.Property]): Unit = {
    val srcNode = findNodeById(src, typ)
    val dstNode = findNodeById(dst, typ)
    try {
      propertyMaybe match {
        case None =>
          diffGraphForAddingNodes.addEdge(srcNode, dstNode, typ.name())
        case Some(property) =>
          diffGraphForAddingNodes.addEdge(srcNode, dstNode, typ.name(), property.getValue)
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
    flatgraph.DiffGraphApplier.applyDiff(cpg.graph, diffGraphForAddingNodes)
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
