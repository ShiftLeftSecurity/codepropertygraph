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
    val Out: Array[String] = Array("POINTS_TO")
    val In: Array[String] =
      Array("CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "CFG", "POINTS_TO")
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
  def pointsToOut: Iterator[_ <: StoredNode]

  /** Traverse to CALL_REPR via POINTS_TO OUT edge.
    */
  def _callReprViaPointsToOut: overflowdb.traversal.Traversal[CallRepr] =
    pointsToOut.collectAll[CallRepr]

  /** Traverse to TYPE_REF via POINTS_TO OUT edge.
    */
  def _typeRefViaPointsToOut: overflowdb.traversal.Traversal[TypeRef] =
    pointsToOut.collectAll[TypeRef]

  /** Traverse to FIELD_IDENTIFIER via POINTS_TO OUT edge.
    */
  def _fieldIdentifierViaPointsToOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    pointsToOut.collectAll[FieldIdentifier]

  /** Traverse to METHOD_PARAMETER_OUT via POINTS_TO OUT edge.
    */
  def _methodParameterOutViaPointsToOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    pointsToOut.collectAll[MethodParameterOut]

  /** Traverse to ANNOTATION via POINTS_TO OUT edge.
    */
  def _annotationViaPointsToOut: overflowdb.traversal.Traversal[Annotation] =
    pointsToOut.collectAll[Annotation]

  /** Traverse to CONTROL_STRUCTURE via POINTS_TO OUT edge.
    */
  def _controlStructureViaPointsToOut: overflowdb.traversal.Traversal[ControlStructure] =
    pointsToOut.collectAll[ControlStructure]

  /** Traverse to RETURN via POINTS_TO OUT edge.
    */
  def _returnViaPointsToOut: overflowdb.traversal.Traversal[Return] =
    pointsToOut.collectAll[Return]

  /** Traverse to TEMPLATE_DOM via POINTS_TO OUT edge.
    */
  def _templateDomViaPointsToOut: overflowdb.traversal.Traversal[TemplateDom] =
    pointsToOut.collectAll[TemplateDom]

  /** Traverse to METHOD_RETURN via POINTS_TO OUT edge.
    */
  def _methodReturnViaPointsToOut: overflowdb.traversal.Traversal[MethodReturn] =
    pointsToOut.collectAll[MethodReturn]

  /** Traverse to METHOD_REF via POINTS_TO OUT edge.
    */
  def _methodRefViaPointsToOut: overflowdb.traversal.Traversal[MethodRef] =
    pointsToOut.collectAll[MethodRef]

  /** Traverse to JUMP_TARGET via POINTS_TO OUT edge.
    */
  def _jumpTargetViaPointsToOut: overflowdb.traversal.Traversal[JumpTarget] =
    pointsToOut.collectAll[JumpTarget]

  /** Traverse to IDENTIFIER via POINTS_TO OUT edge.
    */
  def _identifierViaPointsToOut: overflowdb.traversal.Traversal[Identifier] =
    pointsToOut.collectAll[Identifier]

  /** Traverse to EXPRESSION via POINTS_TO OUT edge.
    */
  def _expressionViaPointsToOut: overflowdb.traversal.Traversal[Expression] =
    pointsToOut.collectAll[Expression]

  /** Traverse to METHOD_PARAMETER_IN via POINTS_TO OUT edge.
    */
  def _methodParameterInViaPointsToOut: overflowdb.traversal.Traversal[MethodParameterIn] =
    pointsToOut.collectAll[MethodParameterIn]

  /** Traverse to ANNOTATION_LITERAL via POINTS_TO OUT edge.
    */
  def _annotationLiteralViaPointsToOut: overflowdb.traversal.Traversal[AnnotationLiteral] =
    pointsToOut.collectAll[AnnotationLiteral]

  /** Traverse to LITERAL via POINTS_TO OUT edge.
    */
  def _literalViaPointsToOut: overflowdb.traversal.Traversal[Literal] =
    pointsToOut.collectAll[Literal]

  /** Traverse to BLOCK via POINTS_TO OUT edge.
    */
  def _blockViaPointsToOut: overflowdb.traversal.Traversal[Block] =
    pointsToOut.collectAll[Block]

  /** Traverse to CALL via POINTS_TO OUT edge.
    */
  def _callViaPointsToOut: overflowdb.traversal.Traversal[Call] =
    pointsToOut.collectAll[Call]

  /** Traverse to METHOD via POINTS_TO OUT edge.
    */
  def _methodViaPointsToOut: overflowdb.traversal.Traversal[Method] =
    pointsToOut.collectAll[Method]

  /** Traverse to AST_NODE via POINTS_TO OUT edge.
    */
  def _astNodeViaPointsToOut: overflowdb.traversal.Traversal[AstNode] =
    pointsToOut.collectAll[AstNode]

  /** Traverse to UNKNOWN via POINTS_TO OUT edge.
    */
  def _unknownViaPointsToOut: overflowdb.traversal.Traversal[Unknown] =
    pointsToOut.collectAll[Unknown]

  /** Traverse to CFG_NODE via POINTS_TO OUT edge.
    */
  def _cfgNodeViaPointsToOut: overflowdb.traversal.Traversal[CfgNode] =
    pointsToOut.collectAll[CfgNode]

  /** Traverse to ARRAY_INITIALIZER via POINTS_TO OUT edge.
    */
  def _arrayInitializerViaPointsToOut: overflowdb.traversal.Traversal[ArrayInitializer] =
    pointsToOut.collectAll[ArrayInitializer]

  def pointsToIn: Iterator[_ <: StoredNode]

  /** Traverse to METHOD_PARAMETER_IN via POINTS_TO IN edge.
    */
  def _methodParameterInViaPointsToIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    pointsToIn.collectAll[MethodParameterIn]

  /** Traverse to EXPRESSION via POINTS_TO IN edge.
    */
  def _expressionViaPointsToIn: overflowdb.traversal.Traversal[Expression] =
    pointsToIn.collectAll[Expression]

  /** Traverse to CFG_NODE via POINTS_TO IN edge.
    */
  def _cfgNodeViaPointsToIn: overflowdb.traversal.Traversal[CfgNode] =
    pointsToIn.collectAll[CfgNode]

  /** Traverse to FIELD_IDENTIFIER via POINTS_TO IN edge.
    */
  def _fieldIdentifierViaPointsToIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    pointsToIn.collectAll[FieldIdentifier]

  /** Traverse to METHOD_PARAMETER_OUT via POINTS_TO IN edge.
    */
  def _methodParameterOutViaPointsToIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    pointsToIn.collectAll[MethodParameterOut]

  /** Traverse to JUMP_TARGET via POINTS_TO IN edge.
    */
  def _jumpTargetViaPointsToIn: overflowdb.traversal.Traversal[JumpTarget] =
    pointsToIn.collectAll[JumpTarget]

  /** Traverse to UNKNOWN via POINTS_TO IN edge.
    */
  def _unknownViaPointsToIn: overflowdb.traversal.Traversal[Unknown] =
    pointsToIn.collectAll[Unknown]

  /** Traverse to IDENTIFIER via POINTS_TO IN edge.
    */
  def _identifierViaPointsToIn: overflowdb.traversal.Traversal[Identifier] =
    pointsToIn.collectAll[Identifier]

  /** Traverse to TYPE_REF via POINTS_TO IN edge.
    */
  def _typeRefViaPointsToIn: overflowdb.traversal.Traversal[TypeRef] =
    pointsToIn.collectAll[TypeRef]

  /** Traverse to METHOD via POINTS_TO IN edge.
    */
  def _methodViaPointsToIn: overflowdb.traversal.Traversal[Method] =
    pointsToIn.collectAll[Method]

  /** Traverse to ANNOTATION_LITERAL via POINTS_TO IN edge.
    */
  def _annotationLiteralViaPointsToIn: overflowdb.traversal.Traversal[AnnotationLiteral] =
    pointsToIn.collectAll[AnnotationLiteral]

  /** Traverse to METHOD_RETURN via POINTS_TO IN edge.
    */
  def _methodReturnViaPointsToIn: overflowdb.traversal.Traversal[MethodReturn] =
    pointsToIn.collectAll[MethodReturn]

  /** Traverse to TEMPLATE_DOM via POINTS_TO IN edge.
    */
  def _templateDomViaPointsToIn: overflowdb.traversal.Traversal[TemplateDom] =
    pointsToIn.collectAll[TemplateDom]

  /** Traverse to ARRAY_INITIALIZER via POINTS_TO IN edge.
    */
  def _arrayInitializerViaPointsToIn: overflowdb.traversal.Traversal[ArrayInitializer] =
    pointsToIn.collectAll[ArrayInitializer]

  /** Traverse to AST_NODE via POINTS_TO IN edge.
    */
  def _astNodeViaPointsToIn: overflowdb.traversal.Traversal[AstNode] =
    pointsToIn.collectAll[AstNode]

  /** Traverse to CALL via POINTS_TO IN edge.
    */
  def _callViaPointsToIn: overflowdb.traversal.Traversal[Call] =
    pointsToIn.collectAll[Call]

  /** Traverse to ANNOTATION via POINTS_TO IN edge.
    */
  def _annotationViaPointsToIn: overflowdb.traversal.Traversal[Annotation] =
    pointsToIn.collectAll[Annotation]

  /** Traverse to LITERAL via POINTS_TO IN edge.
    */
  def _literalViaPointsToIn: overflowdb.traversal.Traversal[Literal] =
    pointsToIn.collectAll[Literal]

  /** Traverse to METHOD_REF via POINTS_TO IN edge.
    */
  def _methodRefViaPointsToIn: overflowdb.traversal.Traversal[MethodRef] =
    pointsToIn.collectAll[MethodRef]

  /** Traverse to CALL_REPR via POINTS_TO IN edge.
    */
  def _callReprViaPointsToIn: overflowdb.traversal.Traversal[CallRepr] =
    pointsToIn.collectAll[CallRepr]

  /** Traverse to RETURN via POINTS_TO IN edge.
    */
  def _returnViaPointsToIn: overflowdb.traversal.Traversal[Return] =
    pointsToIn.collectAll[Return]

  /** Traverse to CONTROL_STRUCTURE via POINTS_TO IN edge.
    */
  def _controlStructureViaPointsToIn: overflowdb.traversal.Traversal[ControlStructure] =
    pointsToIn.collectAll[ControlStructure]

  /** Traverse to BLOCK via POINTS_TO IN edge.
    */
  def _blockViaPointsToIn: overflowdb.traversal.Traversal[Block] =
    pointsToIn.collectAll[Block]

  def cfgIn: Iterator[_ <: StoredNode]

  /** Traverse to CFG_NODE via CFG IN edge.
    */
  def _cfgNodeViaCfgIn: overflowdb.traversal.Traversal[CfgNode] =
    cfgIn.collectAll[CfgNode]

  /** Traverse to AST_NODE via CFG IN edge.
    */
  def _astNodeViaCfgIn: overflowdb.traversal.Traversal[AstNode] =
    cfgIn.collectAll[AstNode]

  /** Traverse to JUMP_TARGET via CFG IN edge.
    */
  def _jumpTargetViaCfgIn: overflowdb.traversal.Traversal[JumpTarget] =
    cfgIn.collectAll[JumpTarget]

  /** Traverse to EXPRESSION via CFG IN edge.
    */
  def _expressionViaCfgIn: overflowdb.traversal.Traversal[Expression] =
    cfgIn.collectAll[Expression]

  /** Traverse to BLOCK via CFG IN edge.
    */
  def _blockViaCfgIn: overflowdb.traversal.Traversal[Block] =
    cfgIn.collectAll[Block]

  /** Traverse to CONTROL_STRUCTURE via CFG IN edge.
    */
  def _controlStructureViaCfgIn: overflowdb.traversal.Traversal[ControlStructure] =
    cfgIn.collectAll[ControlStructure]

  /** Traverse to LITERAL via CFG IN edge.
    */
  def _literalViaCfgIn: overflowdb.traversal.Traversal[Literal] =
    cfgIn.collectAll[Literal]

  /** Traverse to TYPE_REF via CFG IN edge.
    */
  def _typeRefViaCfgIn: overflowdb.traversal.Traversal[TypeRef] =
    cfgIn.collectAll[TypeRef]

  /** Traverse to DECLARATION via CFG IN edge.
    */
  def _declarationViaCfgIn: overflowdb.traversal.Traversal[Declaration] =
    cfgIn.collectAll[Declaration]

  /** Traverse to METHOD via CFG IN edge.
    */
  def _methodViaCfgIn: overflowdb.traversal.Traversal[Method] =
    cfgIn.collectAll[Method]

  /** Traverse to CALL via CFG IN edge.
    */
  def _callViaCfgIn: overflowdb.traversal.Traversal[Call] =
    cfgIn.collectAll[Call]

  /** Traverse to CALL_REPR via CFG IN edge.
    */
  def _callReprViaCfgIn: overflowdb.traversal.Traversal[CallRepr] =
    cfgIn.collectAll[CallRepr]

  /** Traverse to FIELD_IDENTIFIER via CFG IN edge.
    */
  def _fieldIdentifierViaCfgIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    cfgIn.collectAll[FieldIdentifier]

  /** Traverse to IDENTIFIER via CFG IN edge.
    */
  def _identifierViaCfgIn: overflowdb.traversal.Traversal[Identifier] =
    cfgIn.collectAll[Identifier]

  /** Traverse to METHOD_REF via CFG IN edge.
    */
  def _methodRefViaCfgIn: overflowdb.traversal.Traversal[MethodRef] =
    cfgIn.collectAll[MethodRef]

  /** Traverse to UNKNOWN via CFG IN edge.
    */
  def _unknownViaCfgIn: overflowdb.traversal.Traversal[Unknown] =
    cfgIn.collectAll[Unknown]

}
