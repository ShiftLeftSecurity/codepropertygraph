package io.shiftleft.codepropertygraph.generated.nodes

object CfgNode {
  object PropertyNames {
    val Code             = "CODE"
    val ColumnNumber     = "COLUMN_NUMBER"
    val LineNumber       = "LINE_NUMBER"
    val Order            = "ORDER"
    val all: Set[String] = Set(Code, ColumnNumber, LineNumber, Order)
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")
  }

  object PropertyDefaults {
    val Code  = "<empty>"
    val Order = -1: Int
  }

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array("CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG")
  }

}

trait CfgNodeBase extends AbstractNode with AstNodeBase {
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int
}

trait CfgNodeNew extends NewNode with AstNodeNew {
  def code_=(value: String): Unit
  def columnNumber_=(value: Option[Integer]): Unit
  def lineNumber_=(value: Option[Integer]): Unit
  def order_=(value: scala.Int): Unit
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int
}

trait CfgNode extends StoredNode with CfgNodeBase with AstNode {
  import overflowdb.traversal._

  def cfgIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via CFG IN edge.
    */
  def _expressionViaCfgIn: overflowdb.traversal.Traversal[Expression] =
    cfgIn.collectAll[Expression]

  /** Traverse to AST_NODE via CFG IN edge.
    */
  def _astNodeViaCfgIn: overflowdb.traversal.Traversal[AstNode] =
    cfgIn.collectAll[AstNode]

  /** Traverse to CFG_NODE via CFG IN edge.
    */
  def _cfgNodeViaCfgIn: overflowdb.traversal.Traversal[CfgNode] =
    cfgIn.collectAll[CfgNode]

  /** Traverse to BLOCK via CFG IN edge.
    */
  def _blockViaCfgIn: overflowdb.traversal.Traversal[Block] =
    cfgIn.collectAll[Block]

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: overflowdb.traversal.Traversal[Call] =
    cfgIn.collectAll[Call]

  /** Traverse to CALL_REPR via CFG IN edge.
    */
  def _callReprViaCfgIn: overflowdb.traversal.Traversal[CallRepr] =
    cfgIn.collectAll[CallRepr]

  /** Traverse to CONTROL_STRUCTURE via CFG IN edge.
    */
  def _controlStructureViaCfgIn: overflowdb.traversal.Traversal[ControlStructure] =
    cfgIn.collectAll[ControlStructure]

  /** Traverse to FIELD_IDENTIFIER via CFG IN edge.
    */
  def _fieldIdentifierViaCfgIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    cfgIn.collectAll[FieldIdentifier]

  /** Traverse to IDENTIFIER via CFG IN edge.
    */
  def _identifierViaCfgIn: overflowdb.traversal.Traversal[Identifier] =
    cfgIn.collectAll[Identifier]

  /** Traverse to JUMP_TARGET via CFG IN edge.
    */
  def _jumpTargetViaCfgIn: overflowdb.traversal.Traversal[JumpTarget] =
    cfgIn.collectAll[JumpTarget]

  /** Traverse to LITERAL via CFG IN edge.
    */
  def _literalViaCfgIn: overflowdb.traversal.Traversal[Literal] =
    cfgIn.collectAll[Literal]

  /** Traverse to DECLARATION via CFG IN edge.
    */
  def _declarationViaCfgIn: overflowdb.traversal.Traversal[Declaration] =
    cfgIn.collectAll[Declaration]

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: overflowdb.traversal.Traversal[Method] =
    cfgIn.collectAll[Method]

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: overflowdb.traversal.Traversal[MethodRef] =
    cfgIn.collectAll[MethodRef]

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: overflowdb.traversal.Traversal[TypeRef] =
    cfgIn.collectAll[TypeRef]

  /** Traverse to UNKNOWN via CFG IN edge.
    */
  def _unknownViaCfgIn: overflowdb.traversal.Traversal[Unknown] =
    cfgIn.collectAll[Unknown]

}
