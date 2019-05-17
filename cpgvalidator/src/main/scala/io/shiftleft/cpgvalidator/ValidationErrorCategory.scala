package io.shiftleft.cpgvalidator

import java.util.Objects

import org.apache.tinkerpop.gremlin.structure.Direction

sealed trait ValidationErrorCategory

object EdgeDegreeErrorCatergory {
  def apply(error: EdgeDegreeError): EdgeDegreeErrorCatergory = {
    EdgeDegreeErrorCatergory(error.node.label, error.edgeType, error.direction, error.otherSideNodeTypes)
  }
}

case class EdgeDegreeErrorCatergory(nodeLabel: String,
                                    edgeType: String,
                                    direction: Direction,
                                    otherSideNodeTypes: List[String]) extends ValidationErrorCategory

object NodeTypeErrorCategory {
  def apply(error: NodeTypeError): NodeTypeErrorCategory = {
    NodeTypeErrorCategory(error.node.label, error.edgeType, error.direction)
  }
}

case class NodeTypeErrorCategory(nodeLabel: String,
                                 edgeType: String,
                                 direction: Direction) extends ValidationErrorCategory

object EdgeTypeErrorCategory {
  def apply(error: EdgeTypeError): EdgeTypeErrorCategory = {
    EdgeTypeErrorCategory(error.node.label, error.direction, error.invalidEdges.map(_.label))
  }
}

case class EdgeTypeErrorCategory(nodeLabel: String,
                                 direction: Direction,
                                 invalidEdgeTypes: List[String]) extends ValidationErrorCategory

