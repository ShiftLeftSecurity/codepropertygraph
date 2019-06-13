package io.shiftleft.diffgraph

import io.shiftleft.proto.cpg.Cpg._
import io.shiftleft.proto.cpg.Cpg.CpgStruct
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import java.lang.{Long => JLong}

import scala.collection.JavaConverters._

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
class DiffGraphProtoSerializer() {
  import DiffGraph._

  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize()(implicit appliedDiffGraph: AppliedDiffGraph): CpgOverlay = {
    implicit val builder = CpgOverlay.newBuilder()
    addNodes()
    addEdges()
    addNodeProperties()
    addEdgeProperties()
    builder.build()
  }

  private def addNodes()(implicit builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph) = {
    appliedDiffGraph.diffGraph.nodes.foreach { node =>
      val nodeId = appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(node))

      val nodeBuilder = CpgStruct.Node
        .newBuilder()
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
  }

  private def addEdges()(implicit builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph): Unit = {
    val diffGraph = appliedDiffGraph.diffGraph

    addProtoEdge(diffGraph.edgesInOriginal)(_.src.getId, _.dst.getId)

    addProtoEdge(diffGraph.edgesFromOriginal)(_.src.getId,
                                              edge => appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.dst)))

    addProtoEdge(diffGraph.edgesToOriginal)(edge => appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.src)),
                                            _.dst.getId)

    addProtoEdge(diffGraph.edges)(
      edge => appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.src)),
      edge => appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.dst))
    )

    def addProtoEdge[T <: DiffEdge](edges: Seq[T])(srcIdGen: T => JLong, dstIdGen: T => JLong) = {
      edges.foreach(edge => builder.addEdge(protoEdge(edge, srcIdGen(edge), dstIdGen(edge))))
    }

    def protoEdge(edge: DiffEdge, srcId: JLong, dstId: JLong) = {
      val edgeBuilder = CpgStruct.Edge.newBuilder()

      edgeBuilder
        .setSrc(srcId)
        .setDst(dstId)
        .setType(EdgeType.valueOf(edge.label))

      edge.properties.foreach { property =>
        edgeBuilder.addProperty(edgeProperty(property._1, property._2))
      }

      edgeBuilder.build()
    }
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

  private def addNodeProperties()(implicit builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addAllNodeProperty(
      appliedDiffGraph.diffGraph.nodeProperties.map { property =>
        AdditionalNodeProperty
          .newBuilder()
          .setNodeId(property.nodeId)
          .setProperty(nodeProperty(property.propertyKey, property.propertyValue))
          .build
      }.asJava
    )
  }

  private def addEdgeProperties()(implicit builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addAllEdgeProperty(
      appliedDiffGraph.diffGraph.edgeProperties.map { property =>
        AdditionalEdgeProperty
          .newBuilder()
          .setEdgeId(property.edgeId)
          .setProperty(edgeProperty(property.propertyKey, property.propertyValue))
          .build
      }.asJava
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
