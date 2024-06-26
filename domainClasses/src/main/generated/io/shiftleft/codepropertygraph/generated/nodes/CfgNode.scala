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
    val ColumnNumber = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
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
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int
}

trait CfgNodeNew extends NewNode with AstNodeNew {
  def code_=(value: String): Unit
  def columnNumber_=(value: Option[scala.Int]): Unit
  def lineNumber_=(value: Option[scala.Int]): Unit
  def order_=(value: scala.Int): Unit
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int
}

trait CfgNode extends StoredNode with CfgNodeBase with AstNode {
  import overflowdb.traversal._

  def cfgIn: Iterator[? <: StoredNode]

  /** Traverse to EXPRESSION via CFG IN edge.
    */
  def expressionViaCfgIn: overflowdb.traversal.Traversal[Expression] =
    cfgIn.collectAll[Expression]

  @deprecated("please use `expressionViaCfgIn`", "June 2024")
  def _expressionViaCfgIn = expressionViaCfgIn

  /** Traverse to AST_NODE via CFG IN edge.
    */
  def astNodeViaCfgIn: overflowdb.traversal.Traversal[AstNode] =
    cfgIn.collectAll[AstNode]

  @deprecated("please use `astNodeViaCfgIn`", "June 2024")
  def _astNodeViaCfgIn = astNodeViaCfgIn

  /** Traverse to CFG_NODE via CFG IN edge.
    */
  def cfgNodeViaCfgIn: overflowdb.traversal.Traversal[CfgNode] =
    cfgIn.collectAll[CfgNode]

  @deprecated("please use `cfgNodeViaCfgIn`", "June 2024")
  def _cfgNodeViaCfgIn = cfgNodeViaCfgIn

  /** Traverse to BLOCK via CFG IN edge.
    */
  def blockViaCfgIn: overflowdb.traversal.Traversal[Block] =
    cfgIn.collectAll[Block]

  @deprecated("please use `blockViaCfgIn`", "June 2024")
  def _blockViaCfgIn = blockViaCfgIn

  /** Traverse to CALL_REPR via CFG IN edge.
    */
  def callReprViaCfgIn: overflowdb.traversal.Traversal[CallRepr] =
    cfgIn.collectAll[CallRepr]

  @deprecated("please use `callReprViaCfgIn`", "June 2024")
  def _callReprViaCfgIn = callReprViaCfgIn

  /** Traverse to CALL via CFG IN edge.
    */
  def callViaCfgIn: overflowdb.traversal.Traversal[Call] =
    cfgIn.collectAll[Call]

  @deprecated("please use `callViaCfgIn`", "June 2024")
  def _callViaCfgIn = callViaCfgIn

  /** Traverse to CONTROL_STRUCTURE via CFG IN edge.
    */
  def controlStructureViaCfgIn: overflowdb.traversal.Traversal[ControlStructure] =
    cfgIn.collectAll[ControlStructure]

  @deprecated("please use `controlStructureViaCfgIn`", "June 2024")
  def _controlStructureViaCfgIn = controlStructureViaCfgIn

  /** Traverse to FIELD_IDENTIFIER via CFG IN edge.
    */
  def fieldIdentifierViaCfgIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    cfgIn.collectAll[FieldIdentifier]

  @deprecated("please use `fieldIdentifierViaCfgIn`", "June 2024")
  def _fieldIdentifierViaCfgIn = fieldIdentifierViaCfgIn

  /** Traverse to IDENTIFIER via CFG IN edge.
    */
  def identifierViaCfgIn: overflowdb.traversal.Traversal[Identifier] =
    cfgIn.collectAll[Identifier]

  @deprecated("please use `identifierViaCfgIn`", "June 2024")
  def _identifierViaCfgIn = identifierViaCfgIn

  /** Traverse to JUMP_TARGET via CFG IN edge.
    */
  def jumpTargetViaCfgIn: overflowdb.traversal.Traversal[JumpTarget] =
    cfgIn.collectAll[JumpTarget]

  @deprecated("please use `jumpTargetViaCfgIn`", "June 2024")
  def _jumpTargetViaCfgIn = jumpTargetViaCfgIn

  /** Traverse to LITERAL via CFG IN edge.
    */
  def literalViaCfgIn: overflowdb.traversal.Traversal[Literal] =
    cfgIn.collectAll[Literal]

  @deprecated("please use `literalViaCfgIn`", "June 2024")
  def _literalViaCfgIn = literalViaCfgIn

  /** Traverse to DECLARATION via CFG IN edge.
    */
  def declarationViaCfgIn: overflowdb.traversal.Traversal[Declaration] =
    cfgIn.collectAll[Declaration]

  @deprecated("please use `declarationViaCfgIn`", "June 2024")
  def _declarationViaCfgIn = declarationViaCfgIn

  /** Traverse to METHOD via CFG IN edge.
    */
  def methodViaCfgIn: overflowdb.traversal.Traversal[Method] =
    cfgIn.collectAll[Method]

  @deprecated("please use `methodViaCfgIn`", "June 2024")
  def _methodViaCfgIn = methodViaCfgIn

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def methodRefViaCfgIn: overflowdb.traversal.Traversal[MethodRef] =
    cfgIn.collectAll[MethodRef]

  @deprecated("please use `methodRefViaCfgIn`", "June 2024")
  def _methodRefViaCfgIn = methodRefViaCfgIn

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def typeRefViaCfgIn: overflowdb.traversal.Traversal[TypeRef] =
    cfgIn.collectAll[TypeRef]

  @deprecated("please use `typeRefViaCfgIn`", "June 2024")
  def _typeRefViaCfgIn = typeRefViaCfgIn

  /** Traverse to UNKNOWN via CFG IN edge.
    */
  def unknownViaCfgIn: overflowdb.traversal.Traversal[Unknown] =
    cfgIn.collectAll[Unknown]

  @deprecated("please use `unknownViaCfgIn`", "June 2024")
  def _unknownViaCfgIn = unknownViaCfgIn

}
