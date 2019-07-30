package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node
import io.shiftleft.proto.cpg.Cpg.NodePropertyName

import scala.collection.JavaConverters._
import scala.collection.mutable

/**
  * Removes duplicate nodes to avoid uniqueness restriction for parallel frontends.
  */
class NodeFilter {
  private val typeFullNames = mutable.Set[String]()

  def filterNode(protoNode: Node): Boolean = {
    protoNode.getType match {
      case Node.NodeType.TYPE =>
        filterTypeNode(protoNode.getPropertyList.asScala, NodePropertyName.FULL_NAME, typeFullNames)
      case _ =>
        true
    }
  }

  private def filterTypeNode(properties: mutable.Buffer[Node.Property],
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
