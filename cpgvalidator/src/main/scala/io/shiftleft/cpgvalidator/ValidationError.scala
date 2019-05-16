package io.shiftleft.cpgvalidator

import gremlin.scala._
import org.apache.tinkerpop.gremlin.structure.Direction

sealed trait ValidationError

case class EdgeDegreeError(node: Vertex,
                           edgeType: String,
                           direction: Direction,
                           invalidEdgeDegree: Int,
                           expectedDegreeRange: Range.Inclusive,
                           otherSideNodeTypes: List[String]) extends ValidationError {
  override def toString: Label = {
    val start = expectedDegreeRange.start
    val end = if (expectedDegreeRange.end != Int.MaxValue) {expectedDegreeRange.end.toString} else {"N"}

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
}

case class NodeTypeError(node: Vertex,
                         edgeType: String,
                         direction: Direction,
                         invalidOtherSideNodes: List[Vertex]) extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"Expected no outgoing $edgeType edges from ${node.label()} to " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}."
    } else {
      s"Expected no incoming $edgeType edges to ${node.label()} from " +
        s"${invalidOtherSideNodes.map(_.label).mkString(" or ")}."
    }
  }
}

case class EdgeTypeError(node: Vertex,
                         direction: Direction,
                         invalidEdges: List[Edge]) extends ValidationError {
  override def toString: String = {
    if (direction == Direction.OUT) {
      s"${node.label} has invalid outgoing edges ${invalidEdges.map(_.label)}."
    } else {
      s"${node.label} has invalid incoming edges ${invalidEdges.map(_.label)}."
    }
  }
}
