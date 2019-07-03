package io.shiftleft.cpgvalidator

import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.Direction

sealed trait ValidationError {
  def getCategory: ValidationErrorCategory
}

object ErrorHelper {
  def getNodeDetails(node: Vertex): String = {
    s"${node.label} details: id: ${node.id}, properties: ${node.valueMap}"
  }
}

case class EdgeDegreeError(node: Vertex,
                           edgeType: String,
                           direction: Direction,
                           invalidEdgeDegree: Int,
                           expectedDegreeRange: Range.Inclusive,
                           otherSideNodeTypes: List[String])
    extends ValidationError {
  override def toString: Label = {
    val start = expectedDegreeRange.start
    val end = if (expectedDegreeRange.end != Int.MaxValue) { expectedDegreeRange.end.toString } else { "N" }

    if (direction == Direction.OUT) {
      s"Expected $start to $end outgoing $edgeType edges " +
        s"from ${node.label} " +
        s"to ${otherSideNodeTypes.mkString(" or ")} but found $invalidEdgeDegree. " +
        ErrorHelper.getNodeDetails(node)
    } else {
      s"Expected $start to $end incoming $edgeType edges " +
        s"to ${node.label} " +
        s"from ${otherSideNodeTypes.mkString(" or ")} but found $invalidEdgeDegree. " +
        ErrorHelper.getNodeDetails(node)
    }
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeDegreeErrorCategory(this)
  }
}

case class NodeTypeError(node: Vertex, edgeType: String, direction: Direction, invalidOtherSideNodes: List[Vertex])
    extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"Expected no outgoing $edgeType edges from ${node.label()} to " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}. " +
        ErrorHelper.getNodeDetails(node)
    } else {
      s"Expected no incoming $edgeType edges to ${node.label()} from " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}. " +
        ErrorHelper.getNodeDetails(node)
    }
  }

  override def getCategory: ValidationErrorCategory = {
    NodeTypeErrorCategory(this)
  }
}

case class EdgeTypeError(node: Vertex, direction: Direction, invalidEdges: List[Edge]) extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"Expected no outgoing ${invalidEdges.map(_.label).mkString(" or ")} edges from ${node.label}. " +
        ErrorHelper.getNodeDetails(node)
    } else {
      s"Expected no incoming ${invalidEdges.map(_.label).mkString(" or ")} edges to ${node.label}. " +
        ErrorHelper.getNodeDetails(node)
    }
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeTypeErrorCategory(this)
  }
}
