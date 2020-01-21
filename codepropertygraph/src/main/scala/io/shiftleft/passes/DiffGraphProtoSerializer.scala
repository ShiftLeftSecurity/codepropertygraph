package io.shiftleft.passes

import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import io.shiftleft.proto.cpg.Cpg._
import java.lang.{Long => JLong}
import scala.jdk.CollectionConverters._
import org.apache.logging.log4j.{LogManager, Logger}
import io.shiftleft.codepropertygraph.generated.nodes.NewNode

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
class DiffGraphProtoSerializer() {
  private val logger = LogManager.getLogger(getClass)

  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize(appliedDiffGraph: AppliedDiffGraph): CpgOverlay = {
    implicit val builder = CpgOverlay.newBuilder()
    implicit val graph = appliedDiffGraph
    val diff = appliedDiffGraph.diffGraph
    diff.iterator.foreach {
      case c: DiffGraph.Change.CreateEdge       => addEdge(c, builder, appliedDiffGraph)
      case DiffGraph.Change.CreateNode(newNode) => addNode(builder, newNode, appliedDiffGraph)
      case DiffGraph.Change.SetNodeProperty(node, key, value) =>
        addNodeProperty(node.getId, key, value, builder, appliedDiffGraph)
      case DiffGraph.Change.SetEdgeProperty(_, _, _) => ???
      case DiffGraph.Change.RemoveNode(_)            => ???
      case DiffGraph.Change.RemoveNodeProperty(_, _) => ???
      case DiffGraph.Change.RemoveEdge(_)            => ???
      case DiffGraph.Change.RemoveEdgeProperty(_, _) => ???
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
    val diffGraph = appliedDiffGraph.diffGraph
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
      case t: Traversable[_] if t.isEmpty =>
        builder //empty property
      case t: Traversable[_] =>
        // determine property list type based on first element - assuming it's a homogeneous list
        t.head match {
          case _: String =>
            val b = StringList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[String]))
            builder.setStringList(b)
          case _: Boolean =>
            val b = BoolList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Boolean]))
            builder.setBoolList(b)
          case _: Int =>
            val b = IntList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Int]))
            builder.setIntList(b)
          case _: Long =>
            val b = LongList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Long]))
            builder.setLongList(b)
          case _: Float =>
            val b = FloatList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Float]))
            builder.setFloatList(b)
          case _: Double =>
            val b = DoubleList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Double]))
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
