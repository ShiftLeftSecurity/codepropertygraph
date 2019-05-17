package io.shiftleft.cpgvalidator

import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.Direction

sealed trait ValidationError {
  def getCategory: ValidationErrorCategory
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
        s"to ${otherSideNodeTypes.mkString(" or ")} but found $invalidEdgeDegree."
    } else {
      s"Expected $start to $end incoming $edgeType edges " +
        s"to ${node.label} " +
        s"from ${otherSideNodeTypes.mkString(" or ")} but found $invalidEdgeDegree."
    }
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeDegreeErrorCatergory(this)
  }
}

case class NodeTypeError(node: Vertex, edgeType: String, direction: Direction, invalidOtherSideNodes: List[Vertex])
    extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"Expected no outgoing $edgeType edges from ${node.label()} to " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}."
    } else {
      s"Expected no incoming $edgeType edges to ${node.label()} from " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}."
    }
  }

  override def getCategory: ValidationErrorCategory = {
    NodeTypeErrorCategory(this)
  }
}

case class EdgeTypeError(node: Vertex, direction: Direction, invalidEdges: List[Edge]) extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"Expected no outgoing ${invalidEdges.map(_.label).mkString(" or ")} edges from ${node.label}."
    } else {
      s"Expected no incoming ${invalidEdges.map(_.label).mkString(" or ")} edges to ${node.label}."
    }
  }

  override def getCategory: ValidationErrorCategory = {
    EdgeTypeErrorCategory(this)
  }
}
