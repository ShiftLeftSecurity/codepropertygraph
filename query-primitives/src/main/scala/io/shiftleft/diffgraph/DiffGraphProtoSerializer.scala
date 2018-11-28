package io.shiftleft.diffgraph

import io.shiftleft.IdentityHashWrapper
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

  private def addNodes()(implicit builder: CpgOverlay.Builder,
                         appliedDiffGraph: AppliedDiffGraph) = {
    appliedDiffGraph.diffGraph.nodes.foreach { node =>
      val nodeId = appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(node))

      val nodeBuilder = CpgStruct.Node
        .newBuilder()
        .setKey(nodeId)
        .setType(NodeType.valueOf(node.label))

      val properties = node.properties
        .filter { case (key, value) => !key.startsWith("_") }
        .map { case (key, value) => nodeProperty(key, value) }
        .asJava

      nodeBuilder.addAllProperty(properties).build()

      val finalNode = nodeBuilder.build
      builder.addNode(finalNode)
    }
  }

  private def addEdges()(implicit builder: CpgOverlay.Builder,
                         appliedDiffGraph: AppliedDiffGraph): Unit = {
    val diffGraph = appliedDiffGraph.diffGraph

    addProtoEdge(diffGraph.edgesInOriginal, { edge: EdgeInOriginal =>
      edge.src.id.asInstanceOf[JLong]
    }, { edge: EdgeInOriginal =>
      edge.dst.id.asInstanceOf[JLong]
    })

    addProtoEdge(
      diffGraph.edgesFromOriginal, { edge: EdgeFromOriginal =>
        edge.src.id.asInstanceOf[JLong]
      }, { edge: EdgeFromOriginal =>
        appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.dst))
      }
    )

    addProtoEdge(diffGraph.edgesToOriginal, { edge: EdgeToOriginal =>
      appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.src))
    }, { edge: EdgeToOriginal =>
      edge.dst.id.asInstanceOf[JLong]
    })

    addProtoEdge(
      diffGraph.edges, { edge: EdgeInDiffGraph =>
        appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.src))
      }, { edge: EdgeInDiffGraph =>
        appliedDiffGraph.nodeToGraphId(IdentityHashWrapper(edge.dst))
      }
    )

    def addProtoEdge[T <: DiffEdge](edges: Seq[T], srcIdGen: T => JLong, dstIdGen: T => JLong) = {
      builder.addAllEdge(edges.map { edge =>
        protoEdge(edge, srcIdGen(edge), dstIdGen(edge))
      }.asJava)
    }

    def protoEdge(edge: DiffEdge, srcId: JLong, dstId: JLong) =
      CpgStruct.Edge
        .newBuilder()
        .setSrc(srcId)
        .setDst(dstId)
        .setType(EdgeType.valueOf(edge.label))
        .addAllProperty(
          edge.properties.map { case (key, value) => edgeProperty(key, value) }.asJava
        )
        .build
  }

  private def nodeProperty(key: String, value: Any) = {
    CpgStruct.Node.Property
      .newBuilder()
      .setName(NodePropertyName.valueOf(key))
      .setValue(protoValueForPrimitive(value))
      .build()
  }

  private def edgeProperty(key: String, value: Any) =
    CpgStruct.Edge.Property
      .newBuilder()
      .setName(EdgePropertyName.valueOf(key))
      .setValue(protoValueForPrimitive(value))
      .build()

  private def addNodeProperties()(implicit builder: CpgOverlay.Builder,
                                  appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addAllNodeProperty(
      appliedDiffGraph.diffGraph.nodeProperties.map { property =>
        AdditionalNodeProperty
          .newBuilder()
          .setNodeId(property.node.id.asInstanceOf[JLong])
          .setProperty(nodeProperty(property.propertyKey, property.propertyValue))
          .build
      }.asJava
    )
  }

  private def addEdgeProperties()(implicit builder: CpgOverlay.Builder,
                                  appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addAllEdgeProperty(
      appliedDiffGraph.diffGraph.edgeProperties.map { property =>
        AdditionalEdgeProperty
          .newBuilder()
          .setEdgeId(property.edge.id.asInstanceOf[JLong])
          .setProperty(edgeProperty(property.propertyKey, property.propertyValue))
          .build
      }.asJava
    )
  }

  private def protoValueForPrimitive(value: Any) = value match {
    case v: String  => PropertyValue.newBuilder().setStringValue(v)
    case v: Boolean => PropertyValue.newBuilder().setBoolValue(v)
    case v: Int     => PropertyValue.newBuilder().setIntValue(v)
    case v: JLong   => PropertyValue.newBuilder().setLongValue(v)
    case v: Float   => PropertyValue.newBuilder().setFloatValue(v)
    case v: Double  => PropertyValue.newBuilder().setDoubleValue(v)
    case _          => throw new RuntimeException("Unsupported value type")
  }

}
