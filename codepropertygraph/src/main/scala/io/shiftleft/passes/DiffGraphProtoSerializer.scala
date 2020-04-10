package io.shiftleft.passes

import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import io.shiftleft.proto.cpg.Cpg._
import java.lang.{Long => JLong}

import gremlin.scala.Edge
import io.shiftleft.codepropertygraph.generated.nodes.NewNode

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
class DiffGraphProtoSerializer() {

  def addEdgeProperty(builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph, edge: Edge, key: String, value: AnyRef): Unit = {
    builder.addEdgeProperty(
      AdditionalEdgeProperty.newBuilder()
        .setOutNodeKey(edge.outVertex().id.asInstanceOf[Long])
        .setInNodeKey(edge.inVertex().id.asInstanceOf[Long])
        .setEdgeType(EdgeType.valueOf(edge.label()))
        .setProperty(
          CpgStruct.Edge.Property.newBuilder()
            .setName(EdgePropertyName.valueOf(key))
            .setValue(protoValue(value))))
  }

  def serializeInverseGraph(builder: CpgOverlay.Builder, inverseGraph: DiffGraph): Unit = {
    inverseGraph.iterator.foreach {
      case c: DiffGraph.Change.CreateEdge       => ???
      case DiffGraph.Change.CreateNode(newNode) => ???
      case DiffGraph.Change.SetNodeProperty(node, key, value) => ???
      case DiffGraph.Change.SetEdgeProperty(edge, key, value) => ???
      case DiffGraph.Change.RemoveNode(_) => ???
      case DiffGraph.Change.RemoveNodeProperty(_, _) => ???
      case DiffGraph.Change.RemoveEdge(_)            => ???
      case DiffGraph.Change.RemoveEdgeProperty(_, _) => ???
    }
  }

  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize(appliedDiffGraph: AppliedDiffGraph, includeInverseGraph: Boolean = false): CpgOverlay = {
    implicit val builder = CpgOverlay.newBuilder()
    implicit val graph = appliedDiffGraph
    val diff = appliedDiffGraph.diffGraph
    diff.iterator.foreach {
      case c: DiffGraph.Change.CreateEdge       => addEdge(c, builder, appliedDiffGraph)
      case DiffGraph.Change.CreateNode(newNode) => addNode(builder, newNode, appliedDiffGraph)
      case DiffGraph.Change.SetNodeProperty(node, key, value) =>
        addNodeProperty(node.getId, key, value, builder, appliedDiffGraph)
      case DiffGraph.Change.SetEdgeProperty(edge, key, value) =>
        addEdgeProperty(builder, appliedDiffGraph, edge, key, value)
      case DiffGraph.Change.RemoveNode(_) => ???
      case DiffGraph.Change.RemoveNodeProperty(_, _) => ???
      case DiffGraph.Change.RemoveEdge(_)            => ???
      case DiffGraph.Change.RemoveEdgeProperty(_, _) => ???
    }
    if (includeInverseGraph) {
      appliedDiffGraph.inverseDiffGraph.foreach(
        inverseGraph => serializeInverseGraph(builder, inverseGraph)
      )
    }

    val overlay = builder.build()
    overlay
  }

  private def addNode(implicit builder: CpgOverlay.Builder, node: NewNode, appliedDiffGraph: AppliedDiffGraph): Unit = {
    val nodeId = appliedDiffGraph.nodeToGraphId(node)

    val nodeBuilder = CpgStruct.Node
      .newBuilder() // TODO: Can we cache builders???
      .setKey(nodeId)
      .setType(NodeType.valueOf(node.label))

    node.properties
      .foreach {
        case (key, value) if !key.startsWith("_") =>
          val property = nodeProperty(key, value)
          nodeBuilder.addProperty(property)
      }

    val finalNode = nodeBuilder.build()
    builder.addNode(finalNode)
  }

  private def addEdge(change: DiffGraph.Change.CreateEdge,
                      builder: CpgOverlay.Builder,
                      appliedDiffGraph: AppliedDiffGraph): Unit = {
    val srcId =
      if (change.sourceNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.src.asInstanceOf[NewNode])
      else
        change.src.getId

    val dstId =
      if (change.destinationNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.dst.asInstanceOf[NewNode])
      else
        change.dst.getId

    builder.addEdge(makeEdge(change.label, srcId, dstId, change.properties))
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

  private def addNodeProperty(nodeId: Long,
                              key: String,
                              value: AnyRef,
                              builder: CpgOverlay.Builder,
                              appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addNodeProperty(
      AdditionalNodeProperty
        .newBuilder()
        .setNodeId(nodeId)
        .setProperty(nodeProperty(key, value))
        .build
    )
  }

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
