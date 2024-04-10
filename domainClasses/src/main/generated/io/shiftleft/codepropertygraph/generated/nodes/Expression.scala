package io.shiftleft.codepropertygraph.generated.nodes

object Expression {
  object PropertyNames {
    val ArgumentIndex    = "ARGUMENT_INDEX"
    val ArgumentName     = "ARGUMENT_NAME"
    val Code             = "CODE"
    val ColumnNumber     = "COLUMN_NUMBER"
    val LineNumber       = "LINE_NUMBER"
    val Order            = "ORDER"
    val all: Set[String] = Set(ArgumentIndex, ArgumentName, Code, ColumnNumber, LineNumber, Order)
  }

  object Properties {
    val ArgumentIndex = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName  = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val Code          = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber  = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber    = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order         = new overflowdb.PropertyKey[scala.Int]("ORDER")
  }

  object PropertyDefaults {
    val ArgumentIndex = -1: Int
    val Code          = "<empty>"
    val Order         = -1: Int
  }

  object Edges {
    val Out: Array[String] = Array("ARGUMENT")
    val In: Array[String]  = Array("AST", "REACHING_DEF")
  }

}

trait ExpressionBase extends AbstractNode with AstNodeBase with CfgNodeBase {
  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int
}

trait ExpressionNew extends NewNode with AstNodeNew with CfgNodeNew {
  def argumentIndex_=(value: scala.Int): Unit
  def argumentName_=(value: Option[String]): Unit
  def code_=(value: String): Unit
  def columnNumber_=(value: Option[Integer]): Unit
  def lineNumber_=(value: Option[Integer]): Unit
  def order_=(value: scala.Int): Unit
  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int
}

trait Expression extends StoredNode with ExpressionBase with AstNode with CfgNode {
  import overflowdb.traversal._
  def argumentOut: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via ARGUMENT OUT edge.
    */
  def _expressionViaArgumentOut: overflowdb.traversal.Traversal[Expression] =
    argumentOut.collectAll[Expression]

  /** Traverse to AST_NODE via ARGUMENT OUT edge.
    */
  def _astNodeViaArgumentOut: overflowdb.traversal.Traversal[AstNode] =
    argumentOut.collectAll[AstNode]

  /** Traverse to CFG_NODE via ARGUMENT OUT edge.
    */
  def _cfgNodeViaArgumentOut: overflowdb.traversal.Traversal[CfgNode] =
    argumentOut.collectAll[CfgNode]

  /** Traverse to TEMPLATE_DOM via ARGUMENT OUT edge.
    */
  def _templateDomViaArgumentOut: overflowdb.traversal.Traversal[TemplateDom] =
    argumentOut.collectAll[TemplateDom]

  def astIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via AST IN edge.
    */
  def _expressionViaAstIn: overflowdb.traversal.Traversal[Expression] =
    astIn.collectAll[Expression]

  /** Traverse to AST_NODE via AST IN edge.
    */
  def _astNodeViaAstIn: overflowdb.traversal.Traversal[AstNode] =
    astIn.collectAll[AstNode]

  /** Traverse to CFG_NODE via AST IN edge.
    */
  def _cfgNodeViaAstIn: overflowdb.traversal.Traversal[CfgNode] =
    astIn.collectAll[CfgNode]

  /** Traverse to TEMPLATE_DOM via AST IN edge.
    */
  def _templateDomViaAstIn: overflowdb.traversal.Traversal[TemplateDom] =
    astIn.collectAll[TemplateDom]

  def reachingDefIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via REACHING_DEF IN edge.
    */
  def _expressionViaReachingDefIn: overflowdb.traversal.Traversal[Expression] =
    reachingDefIn.collectAll[Expression]

  /** Traverse to AST_NODE via REACHING_DEF IN edge.
    */
  def _astNodeViaReachingDefIn: overflowdb.traversal.Traversal[AstNode] =
    reachingDefIn.collectAll[AstNode]

  /** Traverse to CFG_NODE via REACHING_DEF IN edge.
    */
  def _cfgNodeViaReachingDefIn: overflowdb.traversal.Traversal[CfgNode] =
    reachingDefIn.collectAll[CfgNode]

  /** Traverse to TEMPLATE_DOM via REACHING_DEF IN edge.
    */
  def _templateDomViaReachingDefIn: overflowdb.traversal.Traversal[TemplateDom] =
    reachingDefIn.collectAll[TemplateDom]

}
