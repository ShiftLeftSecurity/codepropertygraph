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
    val ColumnNumber  = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val LineNumber    = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
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
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int
}

trait ExpressionNew extends NewNode with AstNodeNew with CfgNodeNew {
  def argumentIndex_=(value: scala.Int): Unit
  def argumentName_=(value: Option[String]): Unit
  def code_=(value: String): Unit
  def columnNumber_=(value: Option[scala.Int]): Unit
  def lineNumber_=(value: Option[scala.Int]): Unit
  def order_=(value: scala.Int): Unit
  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int
}

trait Expression extends StoredNode with ExpressionBase with AstNode with CfgNode {
  import overflowdb.traversal._
  def argumentOut: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via ARGUMENT OUT edge.
    */
  def expressionViaArgumentOut: overflowdb.traversal.Traversal[Expression] =
    argumentOut.collectAll[Expression]

  @deprecated("please use `expressionViaArgumentOut`", "June 2024")
  def _expressionViaArgumentOut = expressionViaArgumentOut

  /** Traverse to AST_NODE via ARGUMENT OUT edge.
    */
  def astNodeViaArgumentOut: overflowdb.traversal.Traversal[AstNode] =
    argumentOut.collectAll[AstNode]

  @deprecated("please use `astNodeViaArgumentOut`", "June 2024")
  def _astNodeViaArgumentOut = astNodeViaArgumentOut

  /** Traverse to CFG_NODE via ARGUMENT OUT edge.
    */
  def cfgNodeViaArgumentOut: overflowdb.traversal.Traversal[CfgNode] =
    argumentOut.collectAll[CfgNode]

  @deprecated("please use `cfgNodeViaArgumentOut`", "June 2024")
  def _cfgNodeViaArgumentOut = cfgNodeViaArgumentOut

  /** Traverse to TEMPLATE_DOM via ARGUMENT OUT edge.
    */
  def templateDomViaArgumentOut: overflowdb.traversal.Traversal[TemplateDom] =
    argumentOut.collectAll[TemplateDom]

  @deprecated("please use `templateDomViaArgumentOut`", "June 2024")
  def _templateDomViaArgumentOut = templateDomViaArgumentOut

  def astIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via AST IN edge.
    */
  def expressionViaAstIn: overflowdb.traversal.Traversal[Expression] =
    astIn.collectAll[Expression]

  @deprecated("please use `expressionViaAstIn`", "June 2024")
  def _expressionViaAstIn = expressionViaAstIn

  /** Traverse to AST_NODE via AST IN edge.
    */
  def astNodeViaAstIn: overflowdb.traversal.Traversal[AstNode] =
    astIn.collectAll[AstNode]

  @deprecated("please use `astNodeViaAstIn`", "June 2024")
  def _astNodeViaAstIn = astNodeViaAstIn

  /** Traverse to CFG_NODE via AST IN edge.
    */
  def cfgNodeViaAstIn: overflowdb.traversal.Traversal[CfgNode] =
    astIn.collectAll[CfgNode]

  @deprecated("please use `cfgNodeViaAstIn`", "June 2024")
  def _cfgNodeViaAstIn = cfgNodeViaAstIn

  /** Traverse to TEMPLATE_DOM via AST IN edge.
    */
  def templateDomViaAstIn: overflowdb.traversal.Traversal[TemplateDom] =
    astIn.collectAll[TemplateDom]

  @deprecated("please use `templateDomViaAstIn`", "June 2024")
  def _templateDomViaAstIn = templateDomViaAstIn

  def reachingDefIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via REACHING_DEF IN edge.
    */
  def expressionViaReachingDefIn: overflowdb.traversal.Traversal[Expression] =
    reachingDefIn.collectAll[Expression]

  @deprecated("please use `expressionViaReachingDefIn`", "June 2024")
  def _expressionViaReachingDefIn = expressionViaReachingDefIn

  /** Traverse to AST_NODE via REACHING_DEF IN edge.
    */
  def astNodeViaReachingDefIn: overflowdb.traversal.Traversal[AstNode] =
    reachingDefIn.collectAll[AstNode]

  @deprecated("please use `astNodeViaReachingDefIn`", "June 2024")
  def _astNodeViaReachingDefIn = astNodeViaReachingDefIn

  /** Traverse to CFG_NODE via REACHING_DEF IN edge.
    */
  def cfgNodeViaReachingDefIn: overflowdb.traversal.Traversal[CfgNode] =
    reachingDefIn.collectAll[CfgNode]

  @deprecated("please use `cfgNodeViaReachingDefIn`", "June 2024")
  def _cfgNodeViaReachingDefIn = cfgNodeViaReachingDefIn

  /** Traverse to TEMPLATE_DOM via REACHING_DEF IN edge.
    */
  def templateDomViaReachingDefIn: overflowdb.traversal.Traversal[TemplateDom] =
    reachingDefIn.collectAll[TemplateDom]

  @deprecated("please use `templateDomViaReachingDefIn`", "June 2024")
  def _templateDomViaReachingDefIn = templateDomViaReachingDefIn

}
