package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node
import io.shiftleft.proto.cpg.Cpg.NodePropertyName

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * Removes duplicate nodes to avoid uniqueness restriction for parallel frontends.
  */
class NodeFilter {
  private val typeFullNames = mutable.Set[String]()

  def filterNode(protoNode: Node): Boolean = {
    protoNode.getType match {
      case Node.NodeType.TYPE =>
        val fullNameOption = protoNode.getPropertyList.asScala.collectFirst {
          case prop if prop.getName == NodePropertyName.FULL_NAME => prop.getValue.getStringValue
        }
        if (fullNameOption.isDefined) typeFullNames.add(fullNameOption.get) else true
      case _ =>
        true
    }
  }
}
