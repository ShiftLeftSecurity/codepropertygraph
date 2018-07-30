package io.shiftleft.diffgraph

import io.shiftleft.proto.cpg.Cpg._
import io.shiftleft.proto.cpg.Cpg.CpgStruct
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import java.lang.{Long => JLong}

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
class DiffGraphProtoSerializer {
  import DiffGraph._

  val nodeToId: mutable.HashMap[Int, JLong] = mutable.HashMap()

  var curNodeId = 0
  private def generateNextNodeId(occupied: Set[JLong]): JLong = {
    curNodeId += 1
    while (occupied.contains(curNodeId)) {
      curNodeId += 1
    }
    curNodeId
  }

  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize(implicit diffGraph: DiffGraph): CpgOverlay = {
    implicit val builder = CpgOverlay.newBuilder()
    val occupied = occupiedIds(diffGraph)
    addNodes(occupied)
    addEdges()
    addNodeProperties()
    addEdgeProperties()
    builder.build()
  }

  private def occupiedIds(diffGraph: DiffGraph): Set[JLong] = {
    // val result =
    //   diffGraph.edgesFromOriginal.map(_.src.getId) ++
    //     diffGraph.edgesToOriginal.map(_.dst.getId) ++
    //     diffGraph.edgesInOriginal.flatMap(x => List(x.src.getId, x.dst.getId))

    // result.toSet
    ???
  }

  private def addNodes(occupied: Set[JLong])(implicit builder: CpgOverlay.Builder, diffGraph: DiffGraph) = {

    val nodes = diffGraph.nodes.map { node =>
      val nextNodeId = generateNextNodeId(occupied)

      val nodeBuilder = CpgStruct.Node
        .newBuilder()
        .setKey(nextNodeId)
        .setType(NodeType.valueOf(node.label))

      nodeToId.put(System.identityHashCode(node), nextNodeId)

      val properties = node.properties
        .filter { case (key, value) => !key.startsWith("_") }
        .map { case (key, value) => nodeProperty(key, value) }
        .asJava

      nodeBuilder.addAllProperty(properties).build()
    }
    builder.addAllNode(nodes.asJava)
  }

  private def addEdges()(implicit builder: CpgOverlay.Builder, diffGraph: DiffGraph): Unit = {

    ???
    // addProtoEdge(diffGraph.edgesInOriginal, { edge: EdgeInOriginal =>
    //   edge.src.getId
    // }, { edge: EdgeInOriginal =>
    //   edge.dst.getId
    // })

    // addProtoEdge(
    //   diffGraph.edgesFromOriginal, { edge: EdgeFromOriginal =>
    //     edge.src.getId
    //   }, { edge: EdgeFromOriginal =>
    //     nodeToId(System.identityHashCode(edge.dst))
    //   }
    // )

    // addProtoEdge(diffGraph.edgesToOriginal, { edge: EdgeToOriginal =>
    //   nodeToId(System.identityHashCode(edge.src))
    // }, { edge: EdgeToOriginal =>
    //   edge.dst.getId
    // })

    // addProtoEdge(
    //   diffGraph.edges, { edge: EdgeInDiffGraph =>
    //     nodeToId(System.identityHashCode(edge.src))
    //   }, { edge: EdgeInDiffGraph =>
    //     nodeToId(System.identityHashCode(edge.dst))
    //   }
    // )

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

  private def nodeProperty(key: String, value: Any) =
    CpgStruct.Node.Property
      .newBuilder()
      .setName(NodePropertyName.valueOf(key))
      .setValue(protoValueForPrimitive(value))
      .build()

  private def edgeProperty(key: String, value: Any) =
    CpgStruct.Edge.Property
      .newBuilder()
      .setName(EdgePropertyName.valueOf(key))
      .setValue(protoValueForPrimitive(value))
      .build()

  private def addNodeProperties()(implicit builder: CpgOverlay.Builder, diffGraph: DiffGraph): Unit = {
    // builder.addAllNodeProperty(
    //   diffGraph.nodeProperties.map { property =>
    //     AdditionalNodeProperty
    //       .newBuilder()
    //       .setNodeId(property.node.getId)
    //       .setProperty(nodeProperty(property.propertyKey, property.propertyValue))
    //       .build
    //   }.asJava
    // )
    ???
  }

  private def addEdgeProperties()(implicit builder: CpgOverlay.Builder, diffGraph: DiffGraph): Unit = {
    builder.addAllEdgeProperty(
      diffGraph.edgeProperties.map { property =>
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
