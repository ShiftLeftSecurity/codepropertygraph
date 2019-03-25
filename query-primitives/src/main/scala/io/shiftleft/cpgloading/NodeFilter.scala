package io.shiftleft.cpgloading

import io.shiftleft.codepropertygraph.generated.NodeKeyNames
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.Property
import io.shiftleft.proto.cpg.Cpg.NodePropertyName

import scala.collection.JavaConverters._
import scala.collection.mutable

class NodeFilter {
  private val typeFullNames       = mutable.Set[String]()
  private val methodInstFullNames = mutable.Set[String]()

  def filterNode(protoNode: Node): Boolean = {
    protoNode.getType match {
      case Node.NodeType.TYPE =>
        filterTypeNode(protoNode.getPropertyList.asScala, NodePropertyName.FULL_NAME, typeFullNames)
      case Node.NodeType.METHOD_INST =>
        filterTypeNode(protoNode.getPropertyList.asScala,
                       NodePropertyName.FULL_NAME,
                       methodInstFullNames)
      case _ =>
        true
    }
  }

  private def filterTypeNode(properties: mutable.Buffer[Property],
                             filterByProperty: NodePropertyName,
                             compareSet: mutable.Set[String]): Boolean = {
    val propertyOption =
      properties
        .find(_.getName == filterByProperty)
        .map(fullNameProperty => fullNameProperty.getValue.getStringValue)

    propertyOption match {
      case Some(property) =>
        if (compareSet.contains(property)) {
          false
        } else {
          compareSet += property
          true
        }
      case None =>
        true
    }
  }
}
