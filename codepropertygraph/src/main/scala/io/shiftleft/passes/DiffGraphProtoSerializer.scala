package io.shiftleft.passes

import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import java.lang.{Long => JLong}

import com.google.protobuf.ByteString
import gremlin.scala.Edge
import io.shiftleft.codepropertygraph.generated.nodes
import io.shiftleft.proto.cpg.Cpg.{
  AdditionalEdgeProperty,
  AdditionalNodeProperty,
  BoolList,
  CpgOverlay,
  CpgStruct,
  DoubleList,
  EdgePropertyName,
  FloatList,
  IntList,
  LongList,
  NodePropertyName,
  PropertyValue,
  StringList,
  DiffGraph => DiffGraphProto
}
import overflowdb.Node

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
class DiffGraphProtoSerializer {

  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize(appliedDiffGraph: AppliedDiffGraph): CpgOverlay = {
    import DiffGraph.Change._
    val builder = CpgOverlay.newBuilder
    appliedDiffGraph.diffGraph.iterator.foreach {
      case CreateNode(newNode) =>
        builder.addNode(addNode(newNode, appliedDiffGraph))
      case c: CreateEdge =>
        builder.addEdge(addEdge(c, appliedDiffGraph))
      case SetNodeProperty(node, key, value) =>
        builder.addNodeProperty(addNodeProperty(node.id2, key, value))
      case SetEdgeProperty(edge, key, value) =>
        builder.addEdgeProperty(addEdgeProperty(edge, key, value))
      case RemoveNode(_) | RemoveNodeProperty(_, _) | RemoveEdge(_) | RemoveEdgeProperty(_, _) =>
        throw new UnsupportedOperationException(
          "CpgOverlays can be stacked onto each other, therefor they cannot remove anything from the graph")
    }
    builder.build()
  }

  /**
    * Create a proto representation of a (potentially unapplied) DiffGraph (which may also be an
    * The DiffGraph may not (yet) be applied, and it may be an InverseDiffGraph, e.g. as created by {{{DiffGraph.Applier.applyDiff(..., undoable = true) }}}
    */
  def serialize(diffGraph: DiffGraph): DiffGraphProto = {
    import DiffGraph.Change._
    val builder = DiffGraphProto.newBuilder
    def newEntry = DiffGraphProto.Entry.newBuilder
    diffGraph.iterator
      .map {
        case SetNodeProperty(node, key, value) =>
          newEntry.setNodeProperty(addNodeProperty(node.id2, key, value))
        case SetEdgeProperty(edge, key, value) =>
          newEntry.setEdgeProperty(addEdgeProperty(edge, key, value))
        case RemoveNode(nodeId) => newEntry.setRemoveNode(removeNodeProto(nodeId))
        case RemoveEdge(edge)   => newEntry.setRemoveEdge(removeEdgeProto(edge))
        case RemoveNodeProperty(nodeId, propertyKey) =>
          newEntry.setRemoveNodeProperty(removeNodePropertyProto(nodeId, propertyKey))
        case RemoveEdgeProperty(edge, propertyKey) =>
          newEntry.setRemoveEdgeProperty(removeEdgePropertyProto(edge, propertyKey))
        case other => throw new NotImplementedError(s"not (yet?) supported for DiffGraph: ${other.getClass}")
      }
      .foreach(builder.addEntries)
    builder.build()
  }

  private def addNode(node: nodes.NewNode, appliedDiffGraph: AppliedDiffGraph): CpgStruct.Node = {
    val nodeId = appliedDiffGraph.nodeToGraphId(node)

    val nodeBuilder = CpgStruct.Node.newBuilder
      .setKey(nodeId)
      .setType(NodeType.valueOf(node.label))

    node.properties.foreach {
      case (key, value) if !key.startsWith("_") =>
        val property = nodeProperty(key, value)
        nodeBuilder.addProperty(property)
    }

    nodeBuilder.build
  }

  private def addEdge(change: DiffGraph.Change.CreateEdge, appliedDiffGraph: AppliedDiffGraph): CpgStruct.Edge = {
    val srcId: Long =
      if (change.sourceNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.src.asInstanceOf[nodes.NewNode])
      else
        change.src.asInstanceOf[Node].id2

    val dstId: Long =
      if (change.destinationNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.dst.asInstanceOf[nodes.NewNode])
      else
        change.dst.asInstanceOf[Node].id2

    makeEdge(change.label, srcId, dstId, change.properties)
  }

  private def makeEdge(label: String, srcId: Long, dstId: Long, properties: DiffGraph.Properties) = {
    val edgeBuilder = CpgStruct.Edge.newBuilder()

    edgeBuilder
      .setSrc(srcId)
      .setDst(dstId)
      .setType(EdgeType.valueOf(label))

    properties.foreach { property =>
      edgeBuilder.addProperty(edgeProperty(property._1, property._2))
    }

    edgeBuilder.build()
  }

  private def removeNodeProto(nodeId: Long) =
    DiffGraphProto.RemoveNode.newBuilder.setKey(nodeId).build

  private def removeEdgeProto(edge: Edge) =
    DiffGraphProto.RemoveEdge.newBuilder
      .setOutNodeKey(edge.outVertex.id.asInstanceOf[Long])
      .setInNodeKey(edge.inVertex.id.asInstanceOf[Long])
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setPropertiesHash(ByteString.copyFrom(DiffGraph.propertiesHash(edge)))
      .build

  private def removeNodePropertyProto(nodeId: Long, propertyKey: String) =
    DiffGraphProto.RemoveNodeProperty.newBuilder
      .setKey(nodeId)
      .setName(NodePropertyName.valueOf(propertyKey))
      .build

  private def removeEdgePropertyProto(edge: Edge, propertyKey: String) =
    DiffGraphProto.RemoveEdgeProperty.newBuilder
      .setOutNodeKey(edge.outVertex.id.asInstanceOf[Long])
      .setInNodeKey(edge.inVertex.id.asInstanceOf[Long])
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setPropertyName(EdgePropertyName.valueOf(propertyKey))
      .build

  private def nodeProperty(key: String, value: Any) = {
    CpgStruct.Node.Property
      .newBuilder()
      .setName(NodePropertyName.valueOf(key))
      .setValue(protoValue(value))
      .build()
  }

  private def edgeProperty(key: String, value: Any) =
    CpgStruct.Edge.Property
      .newBuilder()
      .setName(EdgePropertyName.valueOf(key))
      .setValue(protoValue(value))
      .build()

  private def addNodeProperty(nodeId: Long, key: String, value: AnyRef): AdditionalNodeProperty =
    AdditionalNodeProperty.newBuilder
      .setNodeId(nodeId)
      .setProperty(nodeProperty(key, value))
      .build

  private def addEdgeProperty(edge: Edge, key: String, value: AnyRef): AdditionalEdgeProperty =
    AdditionalEdgeProperty.newBuilder
      .setOutNodeKey(edge.outVertex.id.asInstanceOf[Long])
      .setInNodeKey(edge.inVertex.id.asInstanceOf[Long])
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setProperty(CpgStruct.Edge.Property.newBuilder
        .setName(EdgePropertyName.valueOf(key))
        .setValue(protoValue(value)))
      .build

  private def protoValue(value: Any): PropertyValue.Builder = {
    val builder = PropertyValue.newBuilder
    value match {
      case iterable: Iterable[_] if iterable.isEmpty => builder //empty property
      case iterable: Iterable[_]                     =>
        // determine property list type based on first element - assuming it's a homogeneous list
        iterable.head match {
          case _: String =>
            val b = StringList.newBuilder
            iterable.asInstanceOf[Iterable[String]].foreach(b.addValues)
            builder.setStringList(b)
          case _: Boolean =>
            val b = BoolList.newBuilder
            iterable.asInstanceOf[Iterable[Boolean]].foreach(b.addValues)
            builder.setBoolList(b)
          case _: Int =>
            val b = IntList.newBuilder
            iterable.asInstanceOf[Iterable[Int]].foreach(b.addValues)
            builder.setIntList(b)
          case _: Long =>
            val b = LongList.newBuilder
            iterable.asInstanceOf[Iterable[Long]].foreach(b.addValues)
            builder.setLongList(b)
          case _: Float =>
            val b = FloatList.newBuilder
            iterable.asInstanceOf[Iterable[Float]].foreach(b.addValues)
            builder.setFloatList(b)
          case _: Double =>
            val b = DoubleList.newBuilder
            iterable.asInstanceOf[Iterable[Double]].foreach(b.addValues)
            builder.setDoubleList(b)
          case _ => throw new RuntimeException("Unsupported primitive value type " + value.getClass)
        }
      case value => protoValueForPrimitive(value)
    }
  }

  private def protoValueForPrimitive(value: Any): PropertyValue.Builder = {
    val builder = PropertyValue.newBuilder
    value match {
      case v: String  => builder.setStringValue(v)
      case v: Boolean => builder.setBoolValue(v)
      case v: Int     => builder.setIntValue(v)
      case v: JLong   => builder.setLongValue(v)
      case v: Float   => builder.setFloatValue(v)
      case v: Double  => builder.setDoubleValue(v)
      case _          => throw new RuntimeException("Unsupported primitive value type " + value.getClass)
    }
  }

}
