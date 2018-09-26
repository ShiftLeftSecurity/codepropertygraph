package io.shiftleft.queryprimitives.utils

import gremlin.scala._
import io.shiftleft.proto.cpg
import io.shiftleft.proto.cpg.Cpg.{CpgStruct, PropertyValue}

object ScalaGraphToProto {

  def convertGraph(cpg: ScalaGraph): CpgStruct = {
    val graphBuilder = CpgStruct.newBuilder()

    cpg
      .V()
      .sideEffect { node =>
        val protoNode = convertNode(node)
        graphBuilder.addNode(protoNode)
      }
      .iterate()

    cpg
      .E()
      .sideEffect { edge =>
        val protoEdge = convertEdge(edge)
        graphBuilder.addEdge(protoEdge)
      }
      .iterate()

    graphBuilder.build()
  }

  private def convertNode(node: Vertex): CpgStruct.Node = {
    val builder = CpgStruct.Node.newBuilder()

    builder.setKey(node.id.asInstanceOf[Long])
    builder.setType(CpgStruct.Node.NodeType.valueOf(node.label))
    addNodeProperties(node, builder)

    builder.build()
  }

  private def addNodeProperties(node: Vertex, nodeBuilder: CpgStruct.Node.Builder): Unit = {
    val propertyMap = node.valueMap

    for ((propertyName, propertyValue) <- propertyMap) {
      val propertyBuilder = CpgStruct.Node.Property.newBuilder()
      propertyBuilder.setName(cpg.Cpg.NodePropertyName.valueOf(propertyName))
      propertyBuilder.setValue(convertPropertyValue(propertyValue))
      nodeBuilder.addProperty(propertyBuilder)
    }
  }

  private def convertEdge(edge: Edge): CpgStruct.Edge = {
    val builder = CpgStruct.Edge.newBuilder()

    builder.setDst(edge.inVertex.id.asInstanceOf[Long])
    builder.setSrc(edge.outVertex.id.asInstanceOf[Long])
    builder.setType(CpgStruct.Edge.EdgeType.valueOf(edge.label))
    addEdgeProperties(edge, builder)

    builder.build()
  }

  private def addEdgeProperties(edge: Edge, edgeBuilder: CpgStruct.Edge.Builder): Unit = {
    val propertyMap = edge.valueMap

    for ((propertyName, propertyValue) <- propertyMap) {
      val propertyBuilder = CpgStruct.Edge.Property.newBuilder()
      propertyBuilder.setName(cpg.Cpg.EdgePropertyName.valueOf(propertyName))
      propertyBuilder.setValue(convertPropertyValue(propertyValue))
      edgeBuilder.addProperty(propertyBuilder)
    }
  }

  private def convertPropertyValue(propertyValue: Any): PropertyValue = {
    val valueBuilder = cpg.Cpg.PropertyValue.newBuilder()
    propertyValue match {
      case value: String =>
        valueBuilder.setStringValue(value)
      case value: Int =>
        valueBuilder.setIntValue(value)
      case value: Boolean =>
        valueBuilder.setBoolValue(value)
    }
    valueBuilder.build
  }
}
