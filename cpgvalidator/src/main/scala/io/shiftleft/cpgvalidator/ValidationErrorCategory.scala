package io.shiftleft.cpgvalidator

import io.shiftleft.cpgvalidator.facts.FactConstructionClasses.Cardinality
import org.apache.tinkerpop.gremlin.structure.Direction

sealed trait ValidationErrorCategory

object EdgeDegreeErrorCategory {
  def apply(error: EdgeDegreeError): EdgeDegreeErrorCategory = {
    EdgeDegreeErrorCategory(
      error.node.label,
      error.edgeType,
      error.direction,
      error.otherSideNodeTypes
    )
  }
}

case class EdgeDegreeErrorCategory(nodeLabel: String,
                                   edgeType: String,
                                   direction: Direction,
                                   otherSideNodeTypes: List[String])
    extends ValidationErrorCategory

object NodeTypeErrorCategory {
  def apply(error: NodeTypeError): NodeTypeErrorCategory = {
    NodeTypeErrorCategory(error.node.label, error.edgeType, error.direction)
  }
}

case class NodeTypeErrorCategory(nodeLabel: String, edgeType: String, direction: Direction)
    extends ValidationErrorCategory

object EdgeTypeErrorCategory {
  def apply(error: EdgeTypeError): EdgeTypeErrorCategory = {
    EdgeTypeErrorCategory(
      error.node.label,
      error.direction,
      error.invalidEdges.map(_.label)
    )
  }
}

case class EdgeTypeErrorCategory(nodeLabel: String, direction: Direction, invalidEdgeTypes: List[String])
    extends ValidationErrorCategory

object KeyErrorCategory {
  def apply(error: KeyError): KeyErrorCategory = {
    KeyErrorCategory(error.node.label, error.nodeKeyType, error.cardinality)
  }
}

case class KeyErrorCategory(nodeLabel: String, nodeKeyType: String, cardinality: Cardinality)
    extends ValidationErrorCategory
