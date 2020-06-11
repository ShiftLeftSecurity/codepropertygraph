package io.shiftleft.cpgvalidator

import io.shiftleft.codepropertygraph.generated.NodeKeysOdb
import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.Cardinality
import org.apache.tinkerpop.gremlin.structure.Direction
import overflowdb.{NodeRef, OdbEdge}
import scala.jdk.CollectionConverters._

sealed trait ValidationError {
  def getCategory: ValidationErrorCategory
}

object ErrorHelper {
  def getNodeDetails(node: NodeRef[_]): String =
    s"details for node ${node.label}:\n" +
      "\t\t" + s"id: ${node.id}\n" +
      "\t\t" + s"properties:\n" +
      s"${node.propertyMap.asScala.map { case (key, value) => s"\t\t\t$key -> $value" }.mkString("\n")}"
}

case class EdgeDegreeError(node: NodeRef[_],
                           edgeType: String,
                           direction: Direction,
                           invalidEdgeDegree: Int,
                           expectedDegreeRange: Range.Inclusive,
                           otherSideNodeTypes: List[String])
    extends ValidationError {

  override def toString: String = {
    val start = expectedDegreeRange.start
    val end = if (expectedDegreeRange.end != Int.MaxValue) {
      expectedDegreeRange.end.toString
    } else { "N" }

    direction match {
      case Direction.OUT =>
        s"Expected $start to $end outgoing $edgeType edges\n" +
          "\t" + s"from: ${node.label}\n" +
          "\t" + s"to: ${otherSideNodeTypes.toSet.mkString(" or ")}\n" +
          "\t" + s"but found: $invalidEdgeDegree\n" +
          "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
      case _ =>
        s"Expected $start to $end incoming $edgeType edges\n" +
          "\t" + s"to: ${node.label}\n" +
          "\t" + s"from: ${otherSideNodeTypes.toSet.mkString(" or ")}\n" +
          "\t" + s"but found: $invalidEdgeDegree\n" +
          "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
    }
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeDegreeErrorCategory(this)
  }
}

case class NodeTypeError(node: NodeRef[_],
                         edgeType: String,
                         direction: Direction,
                         invalidOtherSideNodes: List[NodeRef[_]])
    extends ValidationError {
  override def toString: String = direction match {
    case Direction.OUT =>
      s"Expected no outgoing $edgeType edges\n" +
        "\t" + s"from: ${node.label}\n" +
        "\t" + s"to: ${invalidOtherSideNodes.map(_.label).toSet.mkString(" or ")}\n" +
        "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
    case _ =>
      s"Expected no incoming $edgeType edges\n" +
        "\t" + s"to: ${node.label}\n" +
        "\t" + s"from: ${invalidOtherSideNodes.map(_.label).toSet.mkString(" or ")}\n" +
        "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
  }

  override def getCategory: ValidationErrorCategory = {
    NodeTypeErrorCategory(this)
  }
}

case class EdgeTypeError(node: NodeRef[_], direction: Direction, invalidEdges: List[OdbEdge]) extends ValidationError {
  override def toString: String = direction match {
    case Direction.OUT =>
      s"Expected no outgoing ${invalidEdges.map(_.label).toSet.mkString(" or ")} edges\n" +
        "\t" + s"from: ${node.label}\n" +
        "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
    case _ =>
      s"Expected no incoming ${invalidEdges.map(_.label).toSet.mkString(" or ")} edges\n" +
        "\t" + s"to: ${node.label}\n" +
        "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeTypeErrorCategory(this)
  }
}

case class KeyError(node: NodeRef[_], nodeKeyType: String, cardinality: Cardinality) extends ValidationError {
  override def toString: String =
    s"Cardinality $cardinality of NodeKeyType $nodeKeyType violated for:\n" +
      "\t" + s"node: ${node.label}\n" +
      "\t" + s"${ErrorHelper.getNodeDetails(node)}\n"

  override def getCategory: ValidationErrorCategory = {
    KeyErrorCategory(this)
  }
}

case class CfgEdgeError(srcNode: NodeRef[_], dstNode: NodeRef[_], srcNodeMethod: NodeRef[_], dstNodeMethod: NodeRef[_])
    extends ValidationError {

  override def toString: String = {
    s"Found invalid CFG edge which stretches over method boundaries.\n" +
      "\t" + s"cfg edge start in method: ${srcNodeMethod.property(NodeKeysOdb.FULL_NAME)}\n" +
      "\t" + s"cfg edge start: ${ErrorHelper.getNodeDetails(srcNode)}\n" +
      "\t" + s"cfg edge end in method: ${dstNodeMethod.property(NodeKeysOdb.FULL_NAME)}\n" +
      "\t" + s"cfg edge end: ${ErrorHelper.getNodeDetails(dstNode)}\n"
  }

  override def getCategory: ValidationErrorCategory = {
    CfgEdgeErrorCategory(this)
  }
}
