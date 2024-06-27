package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Return {
  def apply(graph: Graph, id: Long) = new Return(graph, id)

  val Label = "RETURN"

  object PropertyNames {
    val ArgumentIndex                    = "ARGUMENT_INDEX"
    val ArgumentName                     = "ARGUMENT_NAME"
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(ArgumentIndex, ArgumentName, Code, ColumnNumber, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
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

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Condition.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ARGUMENT", "AST", "CFG", "DOMINATE", "POST_DOMINATE", "REACHING_DEF", "TAGGED_BY")
    val In: Array[String] =
      Array("ARGUMENT", "AST", "CDG", "CFG", "CONDITION", "CONTAINS", "DOMINATE", "POST_DOMINATE", "REACHING_DEF")
  }

  val factory = new NodeFactory[ReturnDb] {
    override val forLabel = Return.Label

    override def createNode(ref: NodeRef[ReturnDb]) =
      new ReturnDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Return(graph, id)
  }
}

trait ReturnBase extends AbstractNode with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int

}

class Return(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ReturnDb](graph_4762, id_4762)
    with ReturnBase
    with StoredNode
    with Expression {
  override def argumentIndex: scala.Int        = get().argumentIndex
  override def argumentName: Option[String]    = get().argumentName
  override def code: String                    = get().code
  override def columnNumber: Option[scala.Int] = get().columnNumber
  override def lineNumber: Option[scala.Int]   = get().lineNumber
  override def order: scala.Int                = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX" => Return.PropertyDefaults.ArgumentIndex
      case "CODE"           => Return.PropertyDefaults.Code
      case "ORDER"          => Return.PropertyDefaults.Order
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[CfgNode] = get().argumentOut
  override def _argumentOut          = get()._argumentOut

  /** Traverse to BLOCK via ARGUMENT OUT edge.
    */
  def blockViaArgumentOut: Option[Block] = get().blockViaArgumentOut

  @deprecated("please use `blockViaArgumentOut`", "June 2024")
  def _blockViaArgumentOut = blockViaArgumentOut

  /** Traverse to CALL via ARGUMENT OUT edge.
    */
  def callViaArgumentOut: Option[Call] = get().callViaArgumentOut

  @deprecated("please use `callViaArgumentOut`", "June 2024")
  def _callViaArgumentOut = callViaArgumentOut

  /** Traverse to CONTROL_STRUCTURE via ARGUMENT OUT edge.
    */
  def controlStructureViaArgumentOut: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaArgumentOut

  @deprecated("please use `controlStructureViaArgumentOut`", "June 2024")
  def _controlStructureViaArgumentOut = controlStructureViaArgumentOut

  /** Traverse to IDENTIFIER via ARGUMENT OUT edge.
    */
  def identifierViaArgumentOut: Option[Identifier] = get().identifierViaArgumentOut

  @deprecated("please use `identifierViaArgumentOut`", "June 2024")
  def _identifierViaArgumentOut = identifierViaArgumentOut

  /** Traverse to JUMP_TARGET via ARGUMENT OUT edge.
    */
  def jumpTargetViaArgumentOut: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaArgumentOut

  @deprecated("please use `jumpTargetViaArgumentOut`", "June 2024")
  def _jumpTargetViaArgumentOut = jumpTargetViaArgumentOut

  /** Traverse to LITERAL via ARGUMENT OUT edge.
    */
  def literalViaArgumentOut: Option[Literal] = get().literalViaArgumentOut

  @deprecated("please use `literalViaArgumentOut`", "June 2024")
  def _literalViaArgumentOut = literalViaArgumentOut

  /** Traverse to METHOD_REF via ARGUMENT OUT edge.
    */
  def methodRefViaArgumentOut: Option[MethodRef] = get().methodRefViaArgumentOut

  @deprecated("please use `methodRefViaArgumentOut`", "June 2024")
  def _methodRefViaArgumentOut = methodRefViaArgumentOut

  /** Traverse to RETURN via ARGUMENT OUT edge.
    */
  def returnViaArgumentOut: Option[Return] = get().returnViaArgumentOut

  @deprecated("please use `returnViaArgumentOut`", "June 2024")
  def _returnViaArgumentOut = returnViaArgumentOut

  /** Traverse to TYPE_REF via ARGUMENT OUT edge.
    */
  def typeRefViaArgumentOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaArgumentOut

  @deprecated("please use `typeRefViaArgumentOut`", "June 2024")
  def _typeRefViaArgumentOut = typeRefViaArgumentOut

  /** Traverse to UNKNOWN via ARGUMENT OUT edge.
    */
  def unknownViaArgumentOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaArgumentOut

  @deprecated("please use `unknownViaArgumentOut`", "June 2024")
  def _unknownViaArgumentOut = unknownViaArgumentOut

  def astOut: Iterator[CfgNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to BLOCK via AST OUT edge.
    */
  def blockViaAstOut: overflowdb.traversal.Traversal[Block] = get().blockViaAstOut

  @deprecated("please use `blockViaAstOut`", "June 2024")
  def _blockViaAstOut = blockViaAstOut

  /** Traverse to CALL via AST OUT edge.
    */
  def callViaAstOut: overflowdb.traversal.Traversal[Call] = get().callViaAstOut

  @deprecated("please use `callViaAstOut`", "June 2024")
  def _callViaAstOut = callViaAstOut

  /** Traverse to CONTROL_STRUCTURE via AST OUT edge.
    */
  def controlStructureViaAstOut: overflowdb.traversal.Traversal[ControlStructure] = get().controlStructureViaAstOut

  @deprecated("please use `controlStructureViaAstOut`", "June 2024")
  def _controlStructureViaAstOut = controlStructureViaAstOut

  /** Traverse to IDENTIFIER via AST OUT edge.
    */
  def identifierViaAstOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaAstOut

  @deprecated("please use `identifierViaAstOut`", "June 2024")
  def _identifierViaAstOut = identifierViaAstOut

  /** Traverse to JUMP_TARGET via AST OUT edge.
    */
  def jumpTargetViaAstOut: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaAstOut

  @deprecated("please use `jumpTargetViaAstOut`", "June 2024")
  def _jumpTargetViaAstOut = jumpTargetViaAstOut

  /** Traverse to LITERAL via AST OUT edge.
    */
  def literalViaAstOut: overflowdb.traversal.Traversal[Literal] = get().literalViaAstOut

  @deprecated("please use `literalViaAstOut`", "June 2024")
  def _literalViaAstOut = literalViaAstOut

  /** Traverse to METHOD_REF via AST OUT edge.
    */
  def methodRefViaAstOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaAstOut

  @deprecated("please use `methodRefViaAstOut`", "June 2024")
  def _methodRefViaAstOut = methodRefViaAstOut

  /** Traverse to RETURN via AST OUT edge.
    */
  def returnViaAstOut: overflowdb.traversal.Traversal[Return] = get().returnViaAstOut

  @deprecated("please use `returnViaAstOut`", "June 2024")
  def _returnViaAstOut = returnViaAstOut

  /** Traverse to TYPE_REF via AST OUT edge.
    */
  def typeRefViaAstOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaAstOut

  @deprecated("please use `typeRefViaAstOut`", "June 2024")
  def _typeRefViaAstOut = typeRefViaAstOut

  /** Traverse to UNKNOWN via AST OUT edge.
    */
  def unknownViaAstOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaAstOut

  @deprecated("please use `unknownViaAstOut`", "June 2024")
  def _unknownViaAstOut = unknownViaAstOut

  def cfgOut: Iterator[MethodReturn] = get().cfgOut
  override def _cfgOut               = get()._cfgOut

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def methodReturnViaCfgOut: MethodReturn = get().methodReturnViaCfgOut

  @deprecated("please use `methodReturnViaCfgOut`", "June 2024")
  def _methodReturnViaCfgOut = methodReturnViaCfgOut

  def dominateOut: Iterator[CfgNode] = get().dominateOut
  override def _dominateOut          = get()._dominateOut

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: overflowdb.traversal.Traversal[Block] = get().blockViaDominateOut

  @deprecated("please use `blockViaDominateOut`", "June 2024")
  def _blockViaDominateOut = blockViaDominateOut

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: overflowdb.traversal.Traversal[Call] = get().callViaDominateOut

  @deprecated("please use `callViaDominateOut`", "June 2024")
  def _callViaDominateOut = callViaDominateOut

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaDominateOut

  @deprecated("please use `controlStructureViaDominateOut`", "June 2024")
  def _controlStructureViaDominateOut = controlStructureViaDominateOut

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaDominateOut

  @deprecated("please use `fieldIdentifierViaDominateOut`", "June 2024")
  def _fieldIdentifierViaDominateOut = fieldIdentifierViaDominateOut

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaDominateOut

  @deprecated("please use `identifierViaDominateOut`", "June 2024")
  def _identifierViaDominateOut = identifierViaDominateOut

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaDominateOut

  @deprecated("please use `jumpTargetViaDominateOut`", "June 2024")
  def _jumpTargetViaDominateOut = jumpTargetViaDominateOut

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = get().literalViaDominateOut

  @deprecated("please use `literalViaDominateOut`", "June 2024")
  def _literalViaDominateOut = literalViaDominateOut

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaDominateOut

  @deprecated("please use `methodRefViaDominateOut`", "June 2024")
  def _methodRefViaDominateOut = methodRefViaDominateOut

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaDominateOut

  @deprecated("please use `methodReturnViaDominateOut`", "June 2024")
  def _methodReturnViaDominateOut = methodReturnViaDominateOut

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: overflowdb.traversal.Traversal[Return] = get().returnViaDominateOut

  @deprecated("please use `returnViaDominateOut`", "June 2024")
  def _returnViaDominateOut = returnViaDominateOut

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaDominateOut

  @deprecated("please use `typeRefViaDominateOut`", "June 2024")
  def _typeRefViaDominateOut = typeRefViaDominateOut

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaDominateOut

  @deprecated("please use `unknownViaDominateOut`", "June 2024")
  def _unknownViaDominateOut = unknownViaDominateOut

  def postDominateOut: Iterator[CfgNode] = get().postDominateOut
  override def _postDominateOut          = get()._postDominateOut

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = get().blockViaPostDominateOut

  @deprecated("please use `blockViaPostDominateOut`", "June 2024")
  def _blockViaPostDominateOut = blockViaPostDominateOut

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def callViaPostDominateOut: overflowdb.traversal.Traversal[Call] = get().callViaPostDominateOut

  @deprecated("please use `callViaPostDominateOut`", "June 2024")
  def _callViaPostDominateOut = callViaPostDominateOut

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaPostDominateOut

  @deprecated("please use `controlStructureViaPostDominateOut`", "June 2024")
  def _controlStructureViaPostDominateOut = controlStructureViaPostDominateOut

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaPostDominateOut

  @deprecated("please use `fieldIdentifierViaPostDominateOut`", "June 2024")
  def _fieldIdentifierViaPostDominateOut = fieldIdentifierViaPostDominateOut

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaPostDominateOut

  @deprecated("please use `identifierViaPostDominateOut`", "June 2024")
  def _identifierViaPostDominateOut = identifierViaPostDominateOut

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaPostDominateOut

  @deprecated("please use `jumpTargetViaPostDominateOut`", "June 2024")
  def _jumpTargetViaPostDominateOut = jumpTargetViaPostDominateOut

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal] = get().literalViaPostDominateOut

  @deprecated("please use `literalViaPostDominateOut`", "June 2024")
  def _literalViaPostDominateOut = literalViaPostDominateOut

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def methodViaPostDominateOut: overflowdb.traversal.Traversal[Method] = get().methodViaPostDominateOut

  @deprecated("please use `methodViaPostDominateOut`", "June 2024")
  def _methodViaPostDominateOut = methodViaPostDominateOut

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaPostDominateOut

  @deprecated("please use `methodRefViaPostDominateOut`", "June 2024")
  def _methodRefViaPostDominateOut = methodRefViaPostDominateOut

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def returnViaPostDominateOut: overflowdb.traversal.Traversal[Return] = get().returnViaPostDominateOut

  @deprecated("please use `returnViaPostDominateOut`", "June 2024")
  def _returnViaPostDominateOut = returnViaPostDominateOut

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaPostDominateOut

  @deprecated("please use `typeRefViaPostDominateOut`", "June 2024")
  def _typeRefViaPostDominateOut = typeRefViaPostDominateOut

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaPostDominateOut

  @deprecated("please use `unknownViaPostDominateOut`", "June 2024")
  def _unknownViaPostDominateOut = unknownViaPostDominateOut

  def reachingDefOut: Iterator[CfgNode] = get().reachingDefOut
  override def _reachingDefOut          = get()._reachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaReachingDefOut

  @deprecated("please use `identifierViaReachingDefOut`", "June 2024")
  def _identifierViaReachingDefOut = identifierViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get().methodParameterOutViaReachingDefOut

  @deprecated("please use `methodParameterOutViaReachingDefOut`", "June 2024")
  def _methodParameterOutViaReachingDefOut = methodParameterOutViaReachingDefOut

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaReachingDefOut

  @deprecated("please use `methodRefViaReachingDefOut`", "June 2024")
  def _methodRefViaReachingDefOut = methodRefViaReachingDefOut

  /** Traverse to METHOD_RETURN via REACHING_DEF OUT edge.
    */
  def methodReturnViaReachingDefOut: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaReachingDefOut

  @deprecated("please use `methodReturnViaReachingDefOut`", "June 2024")
  def _methodReturnViaReachingDefOut = methodReturnViaReachingDefOut

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaReachingDefOut

  @deprecated("please use `typeRefViaReachingDefOut`", "June 2024")
  def _typeRefViaReachingDefOut = typeRefViaReachingDefOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get().tagViaTaggedByOut

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def argumentIn: Iterator[Return] = get().argumentIn
  override def _argumentIn         = get()._argumentIn

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def returnViaArgumentIn: Option[Return] = get().returnViaArgumentIn

  @deprecated("please use `returnViaArgumentIn`", "June 2024")
  def _returnViaArgumentIn = returnViaArgumentIn

  def astIn: Iterator[Expression] = get().astIn
  override def _astIn             = get()._astIn

  /** Traverse to BLOCK via AST IN edge.
    */
  def blockViaAstIn: overflowdb.traversal.Traversal[Block] = get().blockViaAstIn

  @deprecated("please use `blockViaAstIn`", "June 2024")
  def _blockViaAstIn = blockViaAstIn

  /** Traverse to CALL via AST IN edge.
    */
  def callViaAstIn: overflowdb.traversal.Traversal[Call] = get().callViaAstIn

  @deprecated("please use `callViaAstIn`", "June 2024")
  def _callViaAstIn = callViaAstIn

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def controlStructureViaAstIn: Option[ControlStructure] = get().controlStructureViaAstIn

  @deprecated("please use `controlStructureViaAstIn`", "June 2024")
  def _controlStructureViaAstIn = controlStructureViaAstIn

  /** Traverse to RETURN via AST IN edge.
    */
  def returnViaAstIn: overflowdb.traversal.Traversal[Return] = get().returnViaAstIn

  @deprecated("please use `returnViaAstIn`", "June 2024")
  def _returnViaAstIn = returnViaAstIn

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaAstIn

  @deprecated("please use `unknownViaAstIn`", "June 2024")
  def _unknownViaAstIn = unknownViaAstIn

  def cdgIn: Iterator[CfgNode] = get().cdgIn
  override def _cdgIn          = get()._cdgIn

  /** Traverse to BLOCK via CDG IN edge.
    */
  def blockViaCdgIn: overflowdb.traversal.Traversal[Block] = get().blockViaCdgIn

  @deprecated("please use `blockViaCdgIn`", "June 2024")
  def _blockViaCdgIn = blockViaCdgIn

  /** Traverse to CALL via CDG IN edge.
    */
  def callViaCdgIn: overflowdb.traversal.Traversal[Call] = get().callViaCdgIn

  @deprecated("please use `callViaCdgIn`", "June 2024")
  def _callViaCdgIn = callViaCdgIn

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = get().controlStructureViaCdgIn

  @deprecated("please use `controlStructureViaCdgIn`", "June 2024")
  def _controlStructureViaCdgIn = controlStructureViaCdgIn

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier] = get().fieldIdentifierViaCdgIn

  @deprecated("please use `fieldIdentifierViaCdgIn`", "June 2024")
  def _fieldIdentifierViaCdgIn = fieldIdentifierViaCdgIn

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaCdgIn

  @deprecated("please use `identifierViaCdgIn`", "June 2024")
  def _identifierViaCdgIn = identifierViaCdgIn

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaCdgIn

  @deprecated("please use `jumpTargetViaCdgIn`", "June 2024")
  def _jumpTargetViaCdgIn = jumpTargetViaCdgIn

  /** Traverse to LITERAL via CDG IN edge.
    */
  def literalViaCdgIn: overflowdb.traversal.Traversal[Literal] = get().literalViaCdgIn

  @deprecated("please use `literalViaCdgIn`", "June 2024")
  def _literalViaCdgIn = literalViaCdgIn

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaCdgIn

  @deprecated("please use `methodRefViaCdgIn`", "June 2024")
  def _methodRefViaCdgIn = methodRefViaCdgIn

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaCdgIn

  @deprecated("please use `typeRefViaCdgIn`", "June 2024")
  def _typeRefViaCdgIn = typeRefViaCdgIn

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaCdgIn

  @deprecated("please use `unknownViaCdgIn`", "June 2024")
  def _unknownViaCdgIn = unknownViaCdgIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def conditionIn: Iterator[ControlStructure] = get().conditionIn
  override def _conditionIn                   = get()._conditionIn

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def controlStructureViaConditionIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaConditionIn

  @deprecated("please use `controlStructureViaConditionIn`", "June 2024")
  def _controlStructureViaConditionIn = controlStructureViaConditionIn

  def containsIn: Iterator[Method] = get().containsIn
  override def _containsIn         = get()._containsIn

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def methodViaContainsIn: overflowdb.traversal.Traversal[Method] = get().methodViaContainsIn

  @deprecated("please use `methodViaContainsIn`", "June 2024")
  def _methodViaContainsIn = methodViaContainsIn

  def dominateIn: Iterator[CfgNode] = get().dominateIn
  override def _dominateIn          = get()._dominateIn

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def blockViaDominateIn: overflowdb.traversal.Traversal[Block] = get().blockViaDominateIn

  @deprecated("please use `blockViaDominateIn`", "June 2024")
  def _blockViaDominateIn = blockViaDominateIn

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def callViaDominateIn: overflowdb.traversal.Traversal[Call] = get().callViaDominateIn

  @deprecated("please use `callViaDominateIn`", "June 2024")
  def _callViaDominateIn = callViaDominateIn

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaDominateIn

  @deprecated("please use `controlStructureViaDominateIn`", "June 2024")
  def _controlStructureViaDominateIn = controlStructureViaDominateIn

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] = get().fieldIdentifierViaDominateIn

  @deprecated("please use `fieldIdentifierViaDominateIn`", "June 2024")
  def _fieldIdentifierViaDominateIn = fieldIdentifierViaDominateIn

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaDominateIn

  @deprecated("please use `identifierViaDominateIn`", "June 2024")
  def _identifierViaDominateIn = identifierViaDominateIn

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaDominateIn

  @deprecated("please use `jumpTargetViaDominateIn`", "June 2024")
  def _jumpTargetViaDominateIn = jumpTargetViaDominateIn

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def literalViaDominateIn: overflowdb.traversal.Traversal[Literal] = get().literalViaDominateIn

  @deprecated("please use `literalViaDominateIn`", "June 2024")
  def _literalViaDominateIn = literalViaDominateIn

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def methodViaDominateIn: overflowdb.traversal.Traversal[Method] = get().methodViaDominateIn

  @deprecated("please use `methodViaDominateIn`", "June 2024")
  def _methodViaDominateIn = methodViaDominateIn

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaDominateIn

  @deprecated("please use `methodRefViaDominateIn`", "June 2024")
  def _methodRefViaDominateIn = methodRefViaDominateIn

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def returnViaDominateIn: overflowdb.traversal.Traversal[Return] = get().returnViaDominateIn

  @deprecated("please use `returnViaDominateIn`", "June 2024")
  def _returnViaDominateIn = returnViaDominateIn

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaDominateIn

  @deprecated("please use `typeRefViaDominateIn`", "June 2024")
  def _typeRefViaDominateIn = typeRefViaDominateIn

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaDominateIn

  @deprecated("please use `unknownViaDominateIn`", "June 2024")
  def _unknownViaDominateIn = unknownViaDominateIn

  def postDominateIn: Iterator[CfgNode] = get().postDominateIn
  override def _postDominateIn          = get()._postDominateIn

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = get().blockViaPostDominateIn

  @deprecated("please use `blockViaPostDominateIn`", "June 2024")
  def _blockViaPostDominateIn = blockViaPostDominateIn

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = get().callViaPostDominateIn

  @deprecated("please use `callViaPostDominateIn`", "June 2024")
  def _callViaPostDominateIn = callViaPostDominateIn

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaPostDominateIn

  @deprecated("please use `controlStructureViaPostDominateIn`", "June 2024")
  def _controlStructureViaPostDominateIn = controlStructureViaPostDominateIn

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaPostDominateIn

  @deprecated("please use `fieldIdentifierViaPostDominateIn`", "June 2024")
  def _fieldIdentifierViaPostDominateIn = fieldIdentifierViaPostDominateIn

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaPostDominateIn

  @deprecated("please use `identifierViaPostDominateIn`", "June 2024")
  def _identifierViaPostDominateIn = identifierViaPostDominateIn

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaPostDominateIn

  @deprecated("please use `jumpTargetViaPostDominateIn`", "June 2024")
  def _jumpTargetViaPostDominateIn = jumpTargetViaPostDominateIn

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = get().literalViaPostDominateIn

  @deprecated("please use `literalViaPostDominateIn`", "June 2024")
  def _literalViaPostDominateIn = literalViaPostDominateIn

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaPostDominateIn

  @deprecated("please use `methodRefViaPostDominateIn`", "June 2024")
  def _methodRefViaPostDominateIn = methodRefViaPostDominateIn

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaPostDominateIn

  @deprecated("please use `methodReturnViaPostDominateIn`", "June 2024")
  def _methodReturnViaPostDominateIn = methodReturnViaPostDominateIn

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = get().returnViaPostDominateIn

  @deprecated("please use `returnViaPostDominateIn`", "June 2024")
  def _returnViaPostDominateIn = returnViaPostDominateIn

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaPostDominateIn

  @deprecated("please use `typeRefViaPostDominateIn`", "June 2024")
  def _typeRefViaPostDominateIn = typeRefViaPostDominateIn

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaPostDominateIn

  @deprecated("please use `unknownViaPostDominateIn`", "June 2024")
  def _unknownViaPostDominateIn = unknownViaPostDominateIn

  def reachingDefIn: Iterator[CfgNode] = get().reachingDefIn
  override def _reachingDefIn          = get()._reachingDefIn

  /** Traverse to BLOCK via REACHING_DEF IN edge.
    */
  def blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = get().blockViaReachingDefIn

  @deprecated("please use `blockViaReachingDefIn`", "June 2024")
  def _blockViaReachingDefIn = blockViaReachingDefIn

  /** Traverse to CALL via REACHING_DEF IN edge.
    */
  def callViaReachingDefIn: overflowdb.traversal.Traversal[Call] = get().callViaReachingDefIn

  @deprecated("please use `callViaReachingDefIn`", "June 2024")
  def _callViaReachingDefIn = callViaReachingDefIn

  /** Traverse to CONTROL_STRUCTURE via REACHING_DEF IN edge.
    */
  def controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaReachingDefIn

  @deprecated("please use `controlStructureViaReachingDefIn`", "June 2024")
  def _controlStructureViaReachingDefIn = controlStructureViaReachingDefIn

  /** Traverse to IDENTIFIER via REACHING_DEF IN edge.
    */
  def identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaReachingDefIn

  @deprecated("please use `identifierViaReachingDefIn`", "June 2024")
  def _identifierViaReachingDefIn = identifierViaReachingDefIn

  /** Traverse to LITERAL via REACHING_DEF IN edge.
    */
  def literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal] = get().literalViaReachingDefIn

  @deprecated("please use `literalViaReachingDefIn`", "June 2024")
  def _literalViaReachingDefIn = literalViaReachingDefIn

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = get().methodViaReachingDefIn

  @deprecated("please use `methodViaReachingDefIn`", "June 2024")
  def _methodViaReachingDefIn = methodViaReachingDefIn

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF IN edge.
    */
  def methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get().methodParameterInViaReachingDefIn

  @deprecated("please use `methodParameterInViaReachingDefIn`", "June 2024")
  def _methodParameterInViaReachingDefIn = methodParameterInViaReachingDefIn

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF IN edge.
    */
  def methodParameterOutViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    get().methodParameterOutViaReachingDefIn

  @deprecated("please use `methodParameterOutViaReachingDefIn`", "June 2024")
  def _methodParameterOutViaReachingDefIn = methodParameterOutViaReachingDefIn

  /** Traverse to METHOD_REF via REACHING_DEF IN edge.
    */
  def methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaReachingDefIn

  @deprecated("please use `methodRefViaReachingDefIn`", "June 2024")
  def _methodRefViaReachingDefIn = methodRefViaReachingDefIn

  /** Traverse to TYPE_REF via REACHING_DEF IN edge.
    */
  def typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaReachingDefIn

  @deprecated("please use `typeRefViaReachingDefIn`", "June 2024")
  def _typeRefViaReachingDefIn = typeRefViaReachingDefIn

  /** Traverse to UNKNOWN via REACHING_DEF IN edge.
    */
  def unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaReachingDefIn

  @deprecated("please use `unknownViaReachingDefIn`", "June 2024")
  def _unknownViaReachingDefIn = unknownViaReachingDefIn

  // In view of https://github.com/scala/bug/issues/4762 it is advisable to use different variable names in
  // patterns like `class Base(x:Int)` and `class Derived(x:Int) extends Base(x)`.
  // This must become `class Derived(x_4762:Int) extends Base(x_4762)`.
  // Otherwise, it is very hard to figure out whether uses of the identifier `x` refer to the base class x
  // or the derived class x.
  // When using that pattern, the class parameter `x_47672` should only be used in the `extends Base(x_4762)`
  // clause and nowhere else. Otherwise, the compiler may well decide that this is not just a constructor
  // parameter but also a field of the class, and we end up with two `x` fields. At best, this wastes memory;
  // at worst both fields go out-of-sync for hard-to-debug correctness bugs.

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def canEqual(that: Any): Boolean                                        = get.canEqual(that)
  override def label: String = {
    Return.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => order
    }

  override def productPrefix = "Return"
  override def productArity  = 7
}

class ReturnDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with Expression with ReturnBase {

  override def layoutInformation: NodeLayoutInformation = Return.layoutInformation

  private var _argumentIndex: Integer = Return.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int        = _argumentIndex
  private var _argumentName: String   = null
  def argumentName: Option[String]    = Option(_argumentName).asInstanceOf[Option[String]]
  private var _code: String           = Return.PropertyDefaults.Code
  def code: String                    = _code
  private var _columnNumber: Integer  = null
  def columnNumber: Option[scala.Int] = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _lineNumber: Integer    = null
  def lineNumber: Option[scala.Int]   = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _order: Integer         = Return.PropertyDefaults.Order
  def order: scala.Int                = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!((-1: Int) == argumentIndex)) { properties.put("ARGUMENT_INDEX", argumentIndex) }
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](0)
  override def _argumentOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  @deprecated("please use `blockViaArgumentOut`", "June 2024")
  def _blockViaArgumentOut = blockViaArgumentOut

  def blockViaArgumentOut: Option[Block] = argumentOut.collectAll[Block].nextOption()
  @deprecated("please use `callViaArgumentOut`", "June 2024")
  def _callViaArgumentOut = callViaArgumentOut

  def callViaArgumentOut: Option[Call] = argumentOut.collectAll[Call].nextOption()
  @deprecated("please use `controlStructureViaArgumentOut`", "June 2024")
  def _controlStructureViaArgumentOut = controlStructureViaArgumentOut

  def controlStructureViaArgumentOut: overflowdb.traversal.Traversal[ControlStructure] =
    argumentOut.collectAll[ControlStructure]
  @deprecated("please use `identifierViaArgumentOut`", "June 2024")
  def _identifierViaArgumentOut = identifierViaArgumentOut

  def identifierViaArgumentOut: Option[Identifier] = argumentOut.collectAll[Identifier].nextOption()
  @deprecated("please use `jumpTargetViaArgumentOut`", "June 2024")
  def _jumpTargetViaArgumentOut = jumpTargetViaArgumentOut

  def jumpTargetViaArgumentOut: overflowdb.traversal.Traversal[JumpTarget] = argumentOut.collectAll[JumpTarget]
  @deprecated("please use `literalViaArgumentOut`", "June 2024")
  def _literalViaArgumentOut = literalViaArgumentOut

  def literalViaArgumentOut: Option[Literal] = argumentOut.collectAll[Literal].nextOption()
  @deprecated("please use `methodRefViaArgumentOut`", "June 2024")
  def _methodRefViaArgumentOut = methodRefViaArgumentOut

  def methodRefViaArgumentOut: Option[MethodRef] = argumentOut.collectAll[MethodRef].nextOption()
  @deprecated("please use `returnViaArgumentOut`", "June 2024")
  def _returnViaArgumentOut = returnViaArgumentOut

  def returnViaArgumentOut: Option[Return] = argumentOut.collectAll[Return].nextOption()
  @deprecated("please use `typeRefViaArgumentOut`", "June 2024")
  def _typeRefViaArgumentOut = typeRefViaArgumentOut

  def typeRefViaArgumentOut: overflowdb.traversal.Traversal[TypeRef] = argumentOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaArgumentOut`", "June 2024")
  def _unknownViaArgumentOut = unknownViaArgumentOut

  def unknownViaArgumentOut: overflowdb.traversal.Traversal[Unknown] = argumentOut.collectAll[Unknown]

  def astOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](1)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `blockViaAstOut`", "June 2024")
  def _blockViaAstOut = blockViaAstOut

  def blockViaAstOut: overflowdb.traversal.Traversal[Block] = astOut.collectAll[Block]
  @deprecated("please use `callViaAstOut`", "June 2024")
  def _callViaAstOut = callViaAstOut

  def callViaAstOut: overflowdb.traversal.Traversal[Call] = astOut.collectAll[Call]
  @deprecated("please use `controlStructureViaAstOut`", "June 2024")
  def _controlStructureViaAstOut = controlStructureViaAstOut

  def controlStructureViaAstOut: overflowdb.traversal.Traversal[ControlStructure] = astOut.collectAll[ControlStructure]
  @deprecated("please use `identifierViaAstOut`", "June 2024")
  def _identifierViaAstOut = identifierViaAstOut

  def identifierViaAstOut: overflowdb.traversal.Traversal[Identifier] = astOut.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaAstOut`", "June 2024")
  def _jumpTargetViaAstOut = jumpTargetViaAstOut

  def jumpTargetViaAstOut: overflowdb.traversal.Traversal[JumpTarget] = astOut.collectAll[JumpTarget]
  @deprecated("please use `literalViaAstOut`", "June 2024")
  def _literalViaAstOut = literalViaAstOut

  def literalViaAstOut: overflowdb.traversal.Traversal[Literal] = astOut.collectAll[Literal]
  @deprecated("please use `methodRefViaAstOut`", "June 2024")
  def _methodRefViaAstOut = methodRefViaAstOut

  def methodRefViaAstOut: overflowdb.traversal.Traversal[MethodRef] = astOut.collectAll[MethodRef]
  @deprecated("please use `returnViaAstOut`", "June 2024")
  def _returnViaAstOut = returnViaAstOut

  def returnViaAstOut: overflowdb.traversal.Traversal[Return] = astOut.collectAll[Return]
  @deprecated("please use `typeRefViaAstOut`", "June 2024")
  def _typeRefViaAstOut = typeRefViaAstOut

  def typeRefViaAstOut: overflowdb.traversal.Traversal[TypeRef] = astOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaAstOut`", "June 2024")
  def _unknownViaAstOut = unknownViaAstOut

  def unknownViaAstOut: overflowdb.traversal.Traversal[Unknown] = astOut.collectAll[Unknown]

  def cfgOut: Iterator[MethodReturn] = createAdjacentNodeScalaIteratorByOffSet[MethodReturn](2)
  override def _cfgOut               = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `methodReturnViaCfgOut`", "June 2024")
  def _methodReturnViaCfgOut = methodReturnViaCfgOut

  def methodReturnViaCfgOut: MethodReturn = try { cfgOut.collectAll[MethodReturn].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label CFG to an adjacent METHOD_RETURN is mandatory, but not defined for this RETURN node with id=" + id,
        e
      )
  }

  def dominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _dominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `blockViaDominateOut`", "June 2024")
  def _blockViaDominateOut = blockViaDominateOut

  def blockViaDominateOut: overflowdb.traversal.Traversal[Block] = dominateOut.collectAll[Block]
  @deprecated("please use `callViaDominateOut`", "June 2024")
  def _callViaDominateOut = callViaDominateOut

  def callViaDominateOut: overflowdb.traversal.Traversal[Call] = dominateOut.collectAll[Call]
  @deprecated("please use `controlStructureViaDominateOut`", "June 2024")
  def _controlStructureViaDominateOut = controlStructureViaDominateOut

  def controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    dominateOut.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaDominateOut`", "June 2024")
  def _fieldIdentifierViaDominateOut = fieldIdentifierViaDominateOut

  def fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateOut.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaDominateOut`", "June 2024")
  def _identifierViaDominateOut = identifierViaDominateOut

  def identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = dominateOut.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaDominateOut`", "June 2024")
  def _jumpTargetViaDominateOut = jumpTargetViaDominateOut

  def jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget] = dominateOut.collectAll[JumpTarget]
  @deprecated("please use `literalViaDominateOut`", "June 2024")
  def _literalViaDominateOut = literalViaDominateOut

  def literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = dominateOut.collectAll[Literal]
  @deprecated("please use `methodRefViaDominateOut`", "June 2024")
  def _methodRefViaDominateOut = methodRefViaDominateOut

  def methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = dominateOut.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaDominateOut`", "June 2024")
  def _methodReturnViaDominateOut = methodReturnViaDominateOut

  def methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = dominateOut.collectAll[MethodReturn]
  @deprecated("please use `returnViaDominateOut`", "June 2024")
  def _returnViaDominateOut = returnViaDominateOut

  def returnViaDominateOut: overflowdb.traversal.Traversal[Return] = dominateOut.collectAll[Return]
  @deprecated("please use `typeRefViaDominateOut`", "June 2024")
  def _typeRefViaDominateOut = typeRefViaDominateOut

  def typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = dominateOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaDominateOut`", "June 2024")
  def _unknownViaDominateOut = unknownViaDominateOut

  def unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = dominateOut.collectAll[Unknown]

  def postDominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _postDominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  @deprecated("please use `blockViaPostDominateOut`", "June 2024")
  def _blockViaPostDominateOut = blockViaPostDominateOut

  def blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = postDominateOut.collectAll[Block]
  @deprecated("please use `callViaPostDominateOut`", "June 2024")
  def _callViaPostDominateOut = callViaPostDominateOut

  def callViaPostDominateOut: overflowdb.traversal.Traversal[Call] = postDominateOut.collectAll[Call]
  @deprecated("please use `controlStructureViaPostDominateOut`", "June 2024")
  def _controlStructureViaPostDominateOut = controlStructureViaPostDominateOut

  def controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateOut.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaPostDominateOut`", "June 2024")
  def _fieldIdentifierViaPostDominateOut = fieldIdentifierViaPostDominateOut

  def fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateOut.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaPostDominateOut`", "June 2024")
  def _identifierViaPostDominateOut = identifierViaPostDominateOut

  def identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = postDominateOut.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaPostDominateOut`", "June 2024")
  def _jumpTargetViaPostDominateOut = jumpTargetViaPostDominateOut

  def jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = postDominateOut.collectAll[JumpTarget]
  @deprecated("please use `literalViaPostDominateOut`", "June 2024")
  def _literalViaPostDominateOut = literalViaPostDominateOut

  def literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal] = postDominateOut.collectAll[Literal]
  @deprecated("please use `methodViaPostDominateOut`", "June 2024")
  def _methodViaPostDominateOut = methodViaPostDominateOut

  def methodViaPostDominateOut: overflowdb.traversal.Traversal[Method] = postDominateOut.collectAll[Method]
  @deprecated("please use `methodRefViaPostDominateOut`", "June 2024")
  def _methodRefViaPostDominateOut = methodRefViaPostDominateOut

  def methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef] = postDominateOut.collectAll[MethodRef]
  @deprecated("please use `returnViaPostDominateOut`", "June 2024")
  def _returnViaPostDominateOut = returnViaPostDominateOut

  def returnViaPostDominateOut: overflowdb.traversal.Traversal[Return] = postDominateOut.collectAll[Return]
  @deprecated("please use `typeRefViaPostDominateOut`", "June 2024")
  def _typeRefViaPostDominateOut = typeRefViaPostDominateOut

  def typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef] = postDominateOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaPostDominateOut`", "June 2024")
  def _unknownViaPostDominateOut = unknownViaPostDominateOut

  def unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown] = postDominateOut.collectAll[Unknown]

  def reachingDefOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](5)
  override def _reachingDefOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  @deprecated("please use `identifierViaReachingDefOut`", "June 2024")
  def _identifierViaReachingDefOut = identifierViaReachingDefOut

  def identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  @deprecated("please use `methodParameterOutViaReachingDefOut`", "June 2024")
  def _methodParameterOutViaReachingDefOut = methodParameterOutViaReachingDefOut

  def methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefOut.collectAll[MethodParameterOut]
  @deprecated("please use `methodRefViaReachingDefOut`", "June 2024")
  def _methodRefViaReachingDefOut = methodRefViaReachingDefOut

  def methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = reachingDefOut.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaReachingDefOut`", "June 2024")
  def _methodReturnViaReachingDefOut = methodReturnViaReachingDefOut

  def methodReturnViaReachingDefOut: overflowdb.traversal.Traversal[MethodReturn] =
    reachingDefOut.collectAll[MethodReturn]
  @deprecated("please use `typeRefViaReachingDefOut`", "June 2024")
  def _typeRefViaReachingDefOut = typeRefViaReachingDefOut

  def typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = reachingDefOut.collectAll[TypeRef]

  def taggedByOut: Iterator[Tag] = createAdjacentNodeScalaIteratorByOffSet[Tag](6)
  override def _taggedByOut      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def argumentIn: Iterator[Return] = createAdjacentNodeScalaIteratorByOffSet[Return](7)
  override def _argumentIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)

  @deprecated("please use `returnViaArgumentIn`", "June 2024")
  def _returnViaArgumentIn = returnViaArgumentIn

  def returnViaArgumentIn: Option[Return] = argumentIn.collectAll[Return].nextOption()

  def astIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](8)
  override def _astIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)

  @deprecated("please use `blockViaAstIn`", "June 2024")
  def _blockViaAstIn = blockViaAstIn

  def blockViaAstIn: overflowdb.traversal.Traversal[Block] = astIn.collectAll[Block]
  @deprecated("please use `callViaAstIn`", "June 2024")
  def _callViaAstIn = callViaAstIn

  def callViaAstIn: overflowdb.traversal.Traversal[Call] = astIn.collectAll[Call]
  @deprecated("please use `controlStructureViaAstIn`", "June 2024")
  def _controlStructureViaAstIn = controlStructureViaAstIn

  def controlStructureViaAstIn: Option[ControlStructure] = astIn.collectAll[ControlStructure].nextOption()
  @deprecated("please use `returnViaAstIn`", "June 2024")
  def _returnViaAstIn = returnViaAstIn

  def returnViaAstIn: overflowdb.traversal.Traversal[Return] = astIn.collectAll[Return]
  @deprecated("please use `unknownViaAstIn`", "June 2024")
  def _unknownViaAstIn = unknownViaAstIn

  def unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = astIn.collectAll[Unknown]

  def cdgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](9)
  override def _cdgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)

  @deprecated("please use `blockViaCdgIn`", "June 2024")
  def _blockViaCdgIn = blockViaCdgIn

  def blockViaCdgIn: overflowdb.traversal.Traversal[Block] = cdgIn.collectAll[Block]
  @deprecated("please use `callViaCdgIn`", "June 2024")
  def _callViaCdgIn = callViaCdgIn

  def callViaCdgIn: overflowdb.traversal.Traversal[Call] = cdgIn.collectAll[Call]
  @deprecated("please use `controlStructureViaCdgIn`", "June 2024")
  def _controlStructureViaCdgIn = controlStructureViaCdgIn

  def controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = cdgIn.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaCdgIn`", "June 2024")
  def _fieldIdentifierViaCdgIn = fieldIdentifierViaCdgIn

  def fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier] = cdgIn.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaCdgIn`", "June 2024")
  def _identifierViaCdgIn = identifierViaCdgIn

  def identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier] = cdgIn.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaCdgIn`", "June 2024")
  def _jumpTargetViaCdgIn = jumpTargetViaCdgIn

  def jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget] = cdgIn.collectAll[JumpTarget]
  @deprecated("please use `literalViaCdgIn`", "June 2024")
  def _literalViaCdgIn = literalViaCdgIn

  def literalViaCdgIn: overflowdb.traversal.Traversal[Literal] = cdgIn.collectAll[Literal]
  @deprecated("please use `methodRefViaCdgIn`", "June 2024")
  def _methodRefViaCdgIn = methodRefViaCdgIn

  def methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef] = cdgIn.collectAll[MethodRef]
  @deprecated("please use `typeRefViaCdgIn`", "June 2024")
  def _typeRefViaCdgIn = typeRefViaCdgIn

  def typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef] = cdgIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaCdgIn`", "June 2024")
  def _unknownViaCdgIn = unknownViaCdgIn

  def unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown] = cdgIn.collectAll[Unknown]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](10)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](10)

  def conditionIn: Iterator[ControlStructure] = createAdjacentNodeScalaIteratorByOffSet[ControlStructure](11)
  override def _conditionIn                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](11)

  @deprecated("please use `controlStructureViaConditionIn`", "June 2024")
  def _controlStructureViaConditionIn = controlStructureViaConditionIn

  def controlStructureViaConditionIn: overflowdb.traversal.Traversal[ControlStructure] =
    conditionIn.collectAll[ControlStructure]

  def containsIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](12)
  override def _containsIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)

  @deprecated("please use `methodViaContainsIn`", "June 2024")
  def _methodViaContainsIn = methodViaContainsIn

  def methodViaContainsIn: overflowdb.traversal.Traversal[Method] = containsIn.collectAll[Method]

  def dominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](13)
  override def _dominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](13)

  @deprecated("please use `blockViaDominateIn`", "June 2024")
  def _blockViaDominateIn = blockViaDominateIn

  def blockViaDominateIn: overflowdb.traversal.Traversal[Block] = dominateIn.collectAll[Block]
  @deprecated("please use `callViaDominateIn`", "June 2024")
  def _callViaDominateIn = callViaDominateIn

  def callViaDominateIn: overflowdb.traversal.Traversal[Call] = dominateIn.collectAll[Call]
  @deprecated("please use `controlStructureViaDominateIn`", "June 2024")
  def _controlStructureViaDominateIn = controlStructureViaDominateIn

  def controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    dominateIn.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaDominateIn`", "June 2024")
  def _fieldIdentifierViaDominateIn = fieldIdentifierViaDominateIn

  def fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateIn.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaDominateIn`", "June 2024")
  def _identifierViaDominateIn = identifierViaDominateIn

  def identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = dominateIn.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaDominateIn`", "June 2024")
  def _jumpTargetViaDominateIn = jumpTargetViaDominateIn

  def jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = dominateIn.collectAll[JumpTarget]
  @deprecated("please use `literalViaDominateIn`", "June 2024")
  def _literalViaDominateIn = literalViaDominateIn

  def literalViaDominateIn: overflowdb.traversal.Traversal[Literal] = dominateIn.collectAll[Literal]
  @deprecated("please use `methodViaDominateIn`", "June 2024")
  def _methodViaDominateIn = methodViaDominateIn

  def methodViaDominateIn: overflowdb.traversal.Traversal[Method] = dominateIn.collectAll[Method]
  @deprecated("please use `methodRefViaDominateIn`", "June 2024")
  def _methodRefViaDominateIn = methodRefViaDominateIn

  def methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef] = dominateIn.collectAll[MethodRef]
  @deprecated("please use `returnViaDominateIn`", "June 2024")
  def _returnViaDominateIn = returnViaDominateIn

  def returnViaDominateIn: overflowdb.traversal.Traversal[Return] = dominateIn.collectAll[Return]
  @deprecated("please use `typeRefViaDominateIn`", "June 2024")
  def _typeRefViaDominateIn = typeRefViaDominateIn

  def typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef] = dominateIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaDominateIn`", "June 2024")
  def _unknownViaDominateIn = unknownViaDominateIn

  def unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown] = dominateIn.collectAll[Unknown]

  def postDominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](14)
  override def _postDominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](14)

  @deprecated("please use `blockViaPostDominateIn`", "June 2024")
  def _blockViaPostDominateIn = blockViaPostDominateIn

  def blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = postDominateIn.collectAll[Block]
  @deprecated("please use `callViaPostDominateIn`", "June 2024")
  def _callViaPostDominateIn = callViaPostDominateIn

  def callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = postDominateIn.collectAll[Call]
  @deprecated("please use `controlStructureViaPostDominateIn`", "June 2024")
  def _controlStructureViaPostDominateIn = controlStructureViaPostDominateIn

  def controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateIn.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaPostDominateIn`", "June 2024")
  def _fieldIdentifierViaPostDominateIn = fieldIdentifierViaPostDominateIn

  def fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateIn.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaPostDominateIn`", "June 2024")
  def _identifierViaPostDominateIn = identifierViaPostDominateIn

  def identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = postDominateIn.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaPostDominateIn`", "June 2024")
  def _jumpTargetViaPostDominateIn = jumpTargetViaPostDominateIn

  def jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = postDominateIn.collectAll[JumpTarget]
  @deprecated("please use `literalViaPostDominateIn`", "June 2024")
  def _literalViaPostDominateIn = literalViaPostDominateIn

  def literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = postDominateIn.collectAll[Literal]
  @deprecated("please use `methodRefViaPostDominateIn`", "June 2024")
  def _methodRefViaPostDominateIn = methodRefViaPostDominateIn

  def methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = postDominateIn.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaPostDominateIn`", "June 2024")
  def _methodReturnViaPostDominateIn = methodReturnViaPostDominateIn

  def methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    postDominateIn.collectAll[MethodReturn]
  @deprecated("please use `returnViaPostDominateIn`", "June 2024")
  def _returnViaPostDominateIn = returnViaPostDominateIn

  def returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = postDominateIn.collectAll[Return]
  @deprecated("please use `typeRefViaPostDominateIn`", "June 2024")
  def _typeRefViaPostDominateIn = typeRefViaPostDominateIn

  def typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = postDominateIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaPostDominateIn`", "June 2024")
  def _unknownViaPostDominateIn = unknownViaPostDominateIn

  def unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = postDominateIn.collectAll[Unknown]

  def reachingDefIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](15)
  override def _reachingDefIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](15)

  @deprecated("please use `blockViaReachingDefIn`", "June 2024")
  def _blockViaReachingDefIn = blockViaReachingDefIn

  def blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = reachingDefIn.collectAll[Block]
  @deprecated("please use `callViaReachingDefIn`", "June 2024")
  def _callViaReachingDefIn = callViaReachingDefIn

  def callViaReachingDefIn: overflowdb.traversal.Traversal[Call] = reachingDefIn.collectAll[Call]
  @deprecated("please use `controlStructureViaReachingDefIn`", "June 2024")
  def _controlStructureViaReachingDefIn = controlStructureViaReachingDefIn

  def controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    reachingDefIn.collectAll[ControlStructure]
  @deprecated("please use `identifierViaReachingDefIn`", "June 2024")
  def _identifierViaReachingDefIn = identifierViaReachingDefIn

  def identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = reachingDefIn.collectAll[Identifier]
  @deprecated("please use `literalViaReachingDefIn`", "June 2024")
  def _literalViaReachingDefIn = literalViaReachingDefIn

  def literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal] = reachingDefIn.collectAll[Literal]
  @deprecated("please use `methodViaReachingDefIn`", "June 2024")
  def _methodViaReachingDefIn = methodViaReachingDefIn

  def methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = reachingDefIn.collectAll[Method]
  @deprecated("please use `methodParameterInViaReachingDefIn`", "June 2024")
  def _methodParameterInViaReachingDefIn = methodParameterInViaReachingDefIn

  def methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    reachingDefIn.collectAll[MethodParameterIn]
  @deprecated("please use `methodParameterOutViaReachingDefIn`", "June 2024")
  def _methodParameterOutViaReachingDefIn = methodParameterOutViaReachingDefIn

  def methodParameterOutViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefIn.collectAll[MethodParameterOut]
  @deprecated("please use `methodRefViaReachingDefIn`", "June 2024")
  def _methodRefViaReachingDefIn = methodRefViaReachingDefIn

  def methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = reachingDefIn.collectAll[MethodRef]
  @deprecated("please use `typeRefViaReachingDefIn`", "June 2024")
  def _typeRefViaReachingDefIn = typeRefViaReachingDefIn

  def typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef] = reachingDefIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaReachingDefIn`", "June 2024")
  def _unknownViaReachingDefIn = unknownViaReachingDefIn

  def unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown] = reachingDefIn.collectAll[Unknown]

  override def label: String = {
    Return.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => order
    }

  override def productPrefix = "Return"
  override def productArity  = 7

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ReturnDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "ARGUMENT_NAME"  => this._argumentName
      case "CODE"           => this._code
      case "COLUMN_NUMBER"  => this._columnNumber
      case "LINE_NUMBER"    => this._lineNumber
      case "ORDER"          => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[scala.Int]
      case "ARGUMENT_NAME"  => this._argumentName = value.asInstanceOf[String]
      case "CODE"           => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"  => this._columnNumber = value.asInstanceOf[scala.Int]
      case "LINE_NUMBER"    => this._lineNumber = value.asInstanceOf[scala.Int]
      case "ORDER"          => this._order = value.asInstanceOf[scala.Int]

      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

  override def removeSpecificProperty(key: String): Unit =
    this.updateSpecificProperty(key, null)

  override def _initializeFromDetached(
    data: overflowdb.DetachedNodeData,
    mapper: java.util.function.Function[overflowdb.DetachedNodeData, Node]
  ) =
    fromNewNode(data.asInstanceOf[NewNode], nn => mapper.apply(nn).asInstanceOf[StoredNode])

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = {
    this._argumentIndex = newNode.asInstanceOf[NewReturn].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewReturn].argumentName match {
      case None => null; case Some(value) => value
    }
    this._code = newNode.asInstanceOf[NewReturn].code
    this._columnNumber = newNode.asInstanceOf[NewReturn].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._lineNumber = newNode.asInstanceOf[NewReturn].lineNumber match { case None => null; case Some(value) => value }
    this._order = newNode.asInstanceOf[NewReturn].order

  }

}
