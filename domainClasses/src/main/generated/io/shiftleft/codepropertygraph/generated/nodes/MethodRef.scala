package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object MethodRef {
  def apply(graph: Graph, id: Long) = new MethodRef(graph, id)

  val Label = "METHOD_REF"

  object PropertyNames {
    val ArgumentIndex           = "ARGUMENT_INDEX"
    val ArgumentName            = "ARGUMENT_NAME"
    val Code                    = "CODE"
    val ColumnNumber            = "COLUMN_NUMBER"
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME"
    val LineNumber              = "LINE_NUMBER"
    val MethodFullName          = "METHOD_FULL_NAME"
    val Order                   = "ORDER"
    val PossibleTypes           = "POSSIBLE_TYPES"
    val TypeFullName            = "TYPE_FULL_NAME"
    val all: Set[String] = Set(
      ArgumentIndex,
      ArgumentName,
      Code,
      ColumnNumber,
      DynamicTypeHintFullName,
      LineNumber,
      MethodFullName,
      Order,
      PossibleTypes,
      TypeFullName
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex           = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName            = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val Code                    = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber            = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val DynamicTypeHintFullName = new overflowdb.PropertyKey[IndexedSeq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME")
    val LineNumber              = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val MethodFullName          = new overflowdb.PropertyKey[String]("METHOD_FULL_NAME")
    val Order                   = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val PossibleTypes           = new overflowdb.PropertyKey[IndexedSeq[String]]("POSSIBLE_TYPES")
    val TypeFullName            = new overflowdb.PropertyKey[String]("TYPE_FULL_NAME")

  }

  object PropertyDefaults {
    val ArgumentIndex  = -1: Int
    val Code           = "<empty>"
    val MethodFullName = "<empty>"
    val Order          = -1: Int
    val TypeFullName   = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Capture.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation,
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
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Receiver.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array(
      "ARGUMENT",
      "AST",
      "CAPTURE",
      "CDG",
      "CFG",
      "DOMINATE",
      "EVAL_TYPE",
      "POST_DOMINATE",
      "REACHING_DEF",
      "REF",
      "TAGGED_BY"
    )
    val In: Array[String] = Array(
      "ARGUMENT",
      "AST",
      "CDG",
      "CFG",
      "CONDITION",
      "CONTAINS",
      "DOMINATE",
      "POST_DOMINATE",
      "REACHING_DEF",
      "RECEIVER"
    )
  }

  val factory = new NodeFactory[MethodRefDb] {
    override val forLabel = MethodRef.Label

    override def createNode(ref: NodeRef[MethodRefDb]) =
      new MethodRefDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodRef(graph, id)
  }
}

trait MethodRefBase extends AbstractNode with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[scala.Int]
  def dynamicTypeHintFullName: IndexedSeq[String]
  def lineNumber: Option[scala.Int]
  def methodFullName: String
  def order: scala.Int
  def possibleTypes: IndexedSeq[String]
  def typeFullName: String

}

class MethodRef(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodRefDb](graph_4762, id_4762)
    with MethodRefBase
    with StoredNode
    with Expression {
  override def argumentIndex: scala.Int                    = get().argumentIndex
  override def argumentName: Option[String]                = get().argumentName
  override def code: String                                = get().code
  override def columnNumber: Option[scala.Int]             = get().columnNumber
  override def dynamicTypeHintFullName: IndexedSeq[String] = get().dynamicTypeHintFullName
  override def lineNumber: Option[scala.Int]               = get().lineNumber
  override def methodFullName: String                      = get().methodFullName
  override def order: scala.Int                            = get().order
  override def possibleTypes: IndexedSeq[String]           = get().possibleTypes
  override def typeFullName: String                        = get().typeFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX"   => MethodRef.PropertyDefaults.ArgumentIndex
      case "CODE"             => MethodRef.PropertyDefaults.Code
      case "METHOD_FULL_NAME" => MethodRef.PropertyDefaults.MethodFullName
      case "ORDER"            => MethodRef.PropertyDefaults.Order
      case "TYPE_FULL_NAME"   => MethodRef.PropertyDefaults.TypeFullName
      case _                  => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[TemplateDom] = get().argumentOut
  override def _argumentOut              = get()._argumentOut

  def astOut: Iterator[Annotation] = get().astOut
  override def _astOut             = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstOut

  @deprecated("please use `_annotationViaAstOut`", "June 2024")
  def __annotationViaAstOut = _annotationViaAstOut

  def captureOut: Iterator[ClosureBinding] = get().captureOut
  override def _captureOut                 = get()._captureOut

  /** Traverse to CLOSURE_BINDING via CAPTURE OUT edge.
    */
  def _closureBindingViaCaptureOut: overflowdb.traversal.Traversal[ClosureBinding] = get()._closureBindingViaCaptureOut

  @deprecated("please use `_closureBindingViaCaptureOut`", "June 2024")
  def __closureBindingViaCaptureOut = _closureBindingViaCaptureOut

  def cdgOut: Iterator[CfgNode] = get().cdgOut
  override def _cdgOut          = get()._cdgOut

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def _blockViaCdgOut: overflowdb.traversal.Traversal[Block] = get()._blockViaCdgOut

  @deprecated("please use `_blockViaCdgOut`", "June 2024")
  def __blockViaCdgOut = _blockViaCdgOut

  /** Traverse to CALL via CDG OUT edge.
    */
  def _callViaCdgOut: overflowdb.traversal.Traversal[Call] = get()._callViaCdgOut

  @deprecated("please use `_callViaCdgOut`", "June 2024")
  def __callViaCdgOut = _callViaCdgOut

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def _controlStructureViaCdgOut: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaCdgOut

  @deprecated("please use `_controlStructureViaCdgOut`", "June 2024")
  def __controlStructureViaCdgOut = _controlStructureViaCdgOut

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def _fieldIdentifierViaCdgOut: overflowdb.traversal.Traversal[FieldIdentifier] = get()._fieldIdentifierViaCdgOut

  @deprecated("please use `_fieldIdentifierViaCdgOut`", "June 2024")
  def __fieldIdentifierViaCdgOut = _fieldIdentifierViaCdgOut

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def _identifierViaCdgOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaCdgOut

  @deprecated("please use `_identifierViaCdgOut`", "June 2024")
  def __identifierViaCdgOut = _identifierViaCdgOut

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def _jumpTargetViaCdgOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaCdgOut

  @deprecated("please use `_jumpTargetViaCdgOut`", "June 2024")
  def __jumpTargetViaCdgOut = _jumpTargetViaCdgOut

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def _literalViaCdgOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaCdgOut

  @deprecated("please use `_literalViaCdgOut`", "June 2024")
  def __literalViaCdgOut = _literalViaCdgOut

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def _methodRefViaCdgOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaCdgOut

  @deprecated("please use `_methodRefViaCdgOut`", "June 2024")
  def __methodRefViaCdgOut = _methodRefViaCdgOut

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def _methodReturnViaCdgOut: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaCdgOut

  @deprecated("please use `_methodReturnViaCdgOut`", "June 2024")
  def __methodReturnViaCdgOut = _methodReturnViaCdgOut

  /** Traverse to RETURN via CDG OUT edge.
    */
  def _returnViaCdgOut: overflowdb.traversal.Traversal[Return] = get()._returnViaCdgOut

  @deprecated("please use `_returnViaCdgOut`", "June 2024")
  def __returnViaCdgOut = _returnViaCdgOut

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def _typeRefViaCdgOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaCdgOut

  @deprecated("please use `_typeRefViaCdgOut`", "June 2024")
  def __typeRefViaCdgOut = _typeRefViaCdgOut

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def _unknownViaCdgOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaCdgOut

  @deprecated("please use `_unknownViaCdgOut`", "June 2024")
  def __unknownViaCdgOut = _unknownViaCdgOut

  def cfgOut: Iterator[AstNode] = get().cfgOut
  override def _cfgOut          = get()._cfgOut

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def _cfgNodeViaCfgOut: overflowdb.traversal.Traversal[CfgNode] = get()._cfgNodeViaCfgOut

  @deprecated("please use `_cfgNodeViaCfgOut`", "June 2024")
  def __cfgNodeViaCfgOut = _cfgNodeViaCfgOut

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def _methodReturnViaCfgOut: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaCfgOut

  @deprecated("please use `_methodReturnViaCfgOut`", "June 2024")
  def __methodReturnViaCfgOut = _methodReturnViaCfgOut

  def dominateOut: Iterator[CfgNode] = get().dominateOut
  override def _dominateOut          = get()._dominateOut

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = get()._blockViaDominateOut

  @deprecated("please use `_blockViaDominateOut`", "June 2024")
  def __blockViaDominateOut = _blockViaDominateOut

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: overflowdb.traversal.Traversal[Call] = get()._callViaDominateOut

  @deprecated("please use `_callViaDominateOut`", "June 2024")
  def __callViaDominateOut = _callViaDominateOut

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def _controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaDominateOut

  @deprecated("please use `_controlStructureViaDominateOut`", "June 2024")
  def __controlStructureViaDominateOut = _controlStructureViaDominateOut

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaDominateOut

  @deprecated("please use `_fieldIdentifierViaDominateOut`", "June 2024")
  def __fieldIdentifierViaDominateOut = _fieldIdentifierViaDominateOut

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaDominateOut

  @deprecated("please use `_identifierViaDominateOut`", "June 2024")
  def __identifierViaDominateOut = _identifierViaDominateOut

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def _jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaDominateOut

  @deprecated("please use `_jumpTargetViaDominateOut`", "June 2024")
  def __jumpTargetViaDominateOut = _jumpTargetViaDominateOut

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaDominateOut

  @deprecated("please use `_literalViaDominateOut`", "June 2024")
  def __literalViaDominateOut = _literalViaDominateOut

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaDominateOut

  @deprecated("please use `_methodRefViaDominateOut`", "June 2024")
  def __methodRefViaDominateOut = _methodRefViaDominateOut

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaDominateOut

  @deprecated("please use `_methodReturnViaDominateOut`", "June 2024")
  def __methodReturnViaDominateOut = _methodReturnViaDominateOut

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: overflowdb.traversal.Traversal[Return] = get()._returnViaDominateOut

  @deprecated("please use `_returnViaDominateOut`", "June 2024")
  def __returnViaDominateOut = _returnViaDominateOut

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaDominateOut

  @deprecated("please use `_typeRefViaDominateOut`", "June 2024")
  def __typeRefViaDominateOut = _typeRefViaDominateOut

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaDominateOut

  @deprecated("please use `_unknownViaDominateOut`", "June 2024")
  def __unknownViaDominateOut = _unknownViaDominateOut

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = get()._typeViaEvalTypeOut

  @deprecated("please use `_typeViaEvalTypeOut`", "June 2024")
  def __typeViaEvalTypeOut = _typeViaEvalTypeOut

  def postDominateOut: Iterator[CfgNode] = get().postDominateOut
  override def _postDominateOut          = get()._postDominateOut

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = get()._blockViaPostDominateOut

  @deprecated("please use `_blockViaPostDominateOut`", "June 2024")
  def __blockViaPostDominateOut = _blockViaPostDominateOut

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: overflowdb.traversal.Traversal[Call] = get()._callViaPostDominateOut

  @deprecated("please use `_callViaPostDominateOut`", "June 2024")
  def __callViaPostDominateOut = _callViaPostDominateOut

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaPostDominateOut

  @deprecated("please use `_controlStructureViaPostDominateOut`", "June 2024")
  def __controlStructureViaPostDominateOut = _controlStructureViaPostDominateOut

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaPostDominateOut

  @deprecated("please use `_fieldIdentifierViaPostDominateOut`", "June 2024")
  def __fieldIdentifierViaPostDominateOut = _fieldIdentifierViaPostDominateOut

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaPostDominateOut

  @deprecated("please use `_identifierViaPostDominateOut`", "June 2024")
  def __identifierViaPostDominateOut = _identifierViaPostDominateOut

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaPostDominateOut

  @deprecated("please use `_jumpTargetViaPostDominateOut`", "June 2024")
  def __jumpTargetViaPostDominateOut = _jumpTargetViaPostDominateOut

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaPostDominateOut

  @deprecated("please use `_literalViaPostDominateOut`", "June 2024")
  def __literalViaPostDominateOut = _literalViaPostDominateOut

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: overflowdb.traversal.Traversal[Method] = get()._methodViaPostDominateOut

  @deprecated("please use `_methodViaPostDominateOut`", "June 2024")
  def __methodViaPostDominateOut = _methodViaPostDominateOut

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaPostDominateOut

  @deprecated("please use `_methodRefViaPostDominateOut`", "June 2024")
  def __methodRefViaPostDominateOut = _methodRefViaPostDominateOut

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: overflowdb.traversal.Traversal[Return] = get()._returnViaPostDominateOut

  @deprecated("please use `_returnViaPostDominateOut`", "June 2024")
  def __returnViaPostDominateOut = _returnViaPostDominateOut

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaPostDominateOut

  @deprecated("please use `_typeRefViaPostDominateOut`", "June 2024")
  def __typeRefViaPostDominateOut = _typeRefViaPostDominateOut

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaPostDominateOut

  @deprecated("please use `_unknownViaPostDominateOut`", "June 2024")
  def __unknownViaPostDominateOut = _unknownViaPostDominateOut

  def reachingDefOut: Iterator[CfgNode] = get().reachingDefOut
  override def _reachingDefOut          = get()._reachingDefOut

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefOut

  @deprecated("please use `_callViaReachingDefOut`", "June 2024")
  def __callViaReachingDefOut = _callViaReachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefOut

  @deprecated("please use `_identifierViaReachingDefOut`", "June 2024")
  def __identifierViaReachingDefOut = _identifierViaReachingDefOut

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefOut

  @deprecated("please use `_literalViaReachingDefOut`", "June 2024")
  def __literalViaReachingDefOut = _literalViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaReachingDefOut

  @deprecated("please use `_methodParameterOutViaReachingDefOut`", "June 2024")
  def __methodParameterOutViaReachingDefOut = _methodParameterOutViaReachingDefOut

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaReachingDefOut

  @deprecated("please use `_methodRefViaReachingDefOut`", "June 2024")
  def __methodRefViaReachingDefOut = _methodRefViaReachingDefOut

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefOut

  @deprecated("please use `_returnViaReachingDefOut`", "June 2024")
  def __returnViaReachingDefOut = _returnViaReachingDefOut

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaReachingDefOut

  @deprecated("please use `_typeRefViaReachingDefOut`", "June 2024")
  def __typeRefViaReachingDefOut = _typeRefViaReachingDefOut

  def refOut: Iterator[Method] = get().refOut
  override def _refOut         = get()._refOut

  /** Traverse to referenced method. Traverse to METHOD via REF OUT edge.
    */
  /** Traverse to referenced method. */
  @overflowdb.traversal.help.Doc(info = """Traverse to referenced method.""")
  def referencedMethod: Method = get().referencedMethod

  @deprecated("please use `referencedMethod`", "June 2024")
  def _referencedMethod = referencedMethod

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  @deprecated("please use `_tagViaTaggedByOut`", "June 2024")
  def __tagViaTaggedByOut = _tagViaTaggedByOut

  def argumentIn: Iterator[Expression] = get().argumentIn
  override def _argumentIn             = get()._argumentIn

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def _callViaArgumentIn: Option[Call] = get()._callViaArgumentIn

  @deprecated("please use `_callViaArgumentIn`", "June 2024")
  def __callViaArgumentIn = _callViaArgumentIn

  /** Traverse to RETURN via ARGUMENT IN edge.
    */
  def _returnViaArgumentIn: Option[Return] = get()._returnViaArgumentIn

  @deprecated("please use `_returnViaArgumentIn`", "June 2024")
  def __returnViaArgumentIn = _returnViaArgumentIn

  def astIn: Iterator[Expression] = get().astIn
  override def _astIn             = get()._astIn

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: overflowdb.traversal.Traversal[Block] = get()._blockViaAstIn

  @deprecated("please use `_blockViaAstIn`", "June 2024")
  def __blockViaAstIn = _blockViaAstIn

  /** Traverse to CALL via AST IN edge.
    */
  def _callViaAstIn: overflowdb.traversal.Traversal[Call] = get()._callViaAstIn

  @deprecated("please use `_callViaAstIn`", "June 2024")
  def __callViaAstIn = _callViaAstIn

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: ControlStructure = get()._controlStructureViaAstIn

  @deprecated("please use `_controlStructureViaAstIn`", "June 2024")
  def __controlStructureViaAstIn = _controlStructureViaAstIn

  /** Traverse to RETURN via AST IN edge.
    */
  def _returnViaAstIn: overflowdb.traversal.Traversal[Return] = get()._returnViaAstIn

  @deprecated("please use `_returnViaAstIn`", "June 2024")
  def __returnViaAstIn = _returnViaAstIn

  def cdgIn: Iterator[CfgNode] = get().cdgIn
  override def _cdgIn          = get()._cdgIn

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: overflowdb.traversal.Traversal[Block] = get()._blockViaCdgIn

  @deprecated("please use `_blockViaCdgIn`", "June 2024")
  def __blockViaCdgIn = _blockViaCdgIn

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: overflowdb.traversal.Traversal[Call] = get()._callViaCdgIn

  @deprecated("please use `_callViaCdgIn`", "June 2024")
  def __callViaCdgIn = _callViaCdgIn

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaCdgIn

  @deprecated("please use `_controlStructureViaCdgIn`", "June 2024")
  def __controlStructureViaCdgIn = _controlStructureViaCdgIn

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier] = get()._fieldIdentifierViaCdgIn

  @deprecated("please use `_fieldIdentifierViaCdgIn`", "June 2024")
  def __fieldIdentifierViaCdgIn = _fieldIdentifierViaCdgIn

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaCdgIn

  @deprecated("please use `_identifierViaCdgIn`", "June 2024")
  def __identifierViaCdgIn = _identifierViaCdgIn

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaCdgIn

  @deprecated("please use `_jumpTargetViaCdgIn`", "June 2024")
  def __jumpTargetViaCdgIn = _jumpTargetViaCdgIn

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaCdgIn

  @deprecated("please use `_literalViaCdgIn`", "June 2024")
  def __literalViaCdgIn = _literalViaCdgIn

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaCdgIn

  @deprecated("please use `_methodRefViaCdgIn`", "June 2024")
  def __methodRefViaCdgIn = _methodRefViaCdgIn

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaCdgIn

  @deprecated("please use `_typeRefViaCdgIn`", "June 2024")
  def __typeRefViaCdgIn = _typeRefViaCdgIn

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaCdgIn

  @deprecated("please use `_unknownViaCdgIn`", "June 2024")
  def __unknownViaCdgIn = _unknownViaCdgIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def conditionIn: Iterator[ControlStructure] = get().conditionIn
  override def _conditionIn                   = get()._conditionIn

  /** Traverse to CONTROL_STRUCTURE via CONDITION IN edge.
    */
  def _controlStructureViaConditionIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaConditionIn

  @deprecated("please use `_controlStructureViaConditionIn`", "June 2024")
  def __controlStructureViaConditionIn = _controlStructureViaConditionIn

  def containsIn: Iterator[Method] = get().containsIn
  override def _containsIn         = get()._containsIn

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = get()._methodViaContainsIn

  @deprecated("please use `_methodViaContainsIn`", "June 2024")
  def __methodViaContainsIn = _methodViaContainsIn

  def dominateIn: Iterator[CfgNode] = get().dominateIn
  override def _dominateIn          = get()._dominateIn

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: overflowdb.traversal.Traversal[Block] = get()._blockViaDominateIn

  @deprecated("please use `_blockViaDominateIn`", "June 2024")
  def __blockViaDominateIn = _blockViaDominateIn

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: overflowdb.traversal.Traversal[Call] = get()._callViaDominateIn

  @deprecated("please use `_callViaDominateIn`", "June 2024")
  def __callViaDominateIn = _callViaDominateIn

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaDominateIn

  @deprecated("please use `_controlStructureViaDominateIn`", "June 2024")
  def __controlStructureViaDominateIn = _controlStructureViaDominateIn

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaDominateIn

  @deprecated("please use `_fieldIdentifierViaDominateIn`", "June 2024")
  def __fieldIdentifierViaDominateIn = _fieldIdentifierViaDominateIn

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaDominateIn

  @deprecated("please use `_identifierViaDominateIn`", "June 2024")
  def __identifierViaDominateIn = _identifierViaDominateIn

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def _jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaDominateIn

  @deprecated("please use `_jumpTargetViaDominateIn`", "June 2024")
  def __jumpTargetViaDominateIn = _jumpTargetViaDominateIn

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaDominateIn

  @deprecated("please use `_literalViaDominateIn`", "June 2024")
  def __literalViaDominateIn = _literalViaDominateIn

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def _methodViaDominateIn: overflowdb.traversal.Traversal[Method] = get()._methodViaDominateIn

  @deprecated("please use `_methodViaDominateIn`", "June 2024")
  def __methodViaDominateIn = _methodViaDominateIn

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaDominateIn

  @deprecated("please use `_methodRefViaDominateIn`", "June 2024")
  def __methodRefViaDominateIn = _methodRefViaDominateIn

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: overflowdb.traversal.Traversal[Return] = get()._returnViaDominateIn

  @deprecated("please use `_returnViaDominateIn`", "June 2024")
  def __returnViaDominateIn = _returnViaDominateIn

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaDominateIn

  @deprecated("please use `_typeRefViaDominateIn`", "June 2024")
  def __typeRefViaDominateIn = _typeRefViaDominateIn

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaDominateIn

  @deprecated("please use `_unknownViaDominateIn`", "June 2024")
  def __unknownViaDominateIn = _unknownViaDominateIn

  def postDominateIn: Iterator[CfgNode] = get().postDominateIn
  override def _postDominateIn          = get()._postDominateIn

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = get()._blockViaPostDominateIn

  @deprecated("please use `_blockViaPostDominateIn`", "June 2024")
  def __blockViaPostDominateIn = _blockViaPostDominateIn

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = get()._callViaPostDominateIn

  @deprecated("please use `_callViaPostDominateIn`", "June 2024")
  def __callViaPostDominateIn = _callViaPostDominateIn

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaPostDominateIn

  @deprecated("please use `_controlStructureViaPostDominateIn`", "June 2024")
  def __controlStructureViaPostDominateIn = _controlStructureViaPostDominateIn

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaPostDominateIn

  @deprecated("please use `_fieldIdentifierViaPostDominateIn`", "June 2024")
  def __fieldIdentifierViaPostDominateIn = _fieldIdentifierViaPostDominateIn

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaPostDominateIn

  @deprecated("please use `_identifierViaPostDominateIn`", "June 2024")
  def __identifierViaPostDominateIn = _identifierViaPostDominateIn

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def _jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaPostDominateIn

  @deprecated("please use `_jumpTargetViaPostDominateIn`", "June 2024")
  def __jumpTargetViaPostDominateIn = _jumpTargetViaPostDominateIn

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaPostDominateIn

  @deprecated("please use `_literalViaPostDominateIn`", "June 2024")
  def __literalViaPostDominateIn = _literalViaPostDominateIn

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaPostDominateIn

  @deprecated("please use `_methodRefViaPostDominateIn`", "June 2024")
  def __methodRefViaPostDominateIn = _methodRefViaPostDominateIn

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    get()._methodReturnViaPostDominateIn

  @deprecated("please use `_methodReturnViaPostDominateIn`", "June 2024")
  def __methodReturnViaPostDominateIn = _methodReturnViaPostDominateIn

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = get()._returnViaPostDominateIn

  @deprecated("please use `_returnViaPostDominateIn`", "June 2024")
  def __returnViaPostDominateIn = _returnViaPostDominateIn

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaPostDominateIn

  @deprecated("please use `_typeRefViaPostDominateIn`", "June 2024")
  def __typeRefViaPostDominateIn = _typeRefViaPostDominateIn

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaPostDominateIn

  @deprecated("please use `_unknownViaPostDominateIn`", "June 2024")
  def __unknownViaPostDominateIn = _unknownViaPostDominateIn

  def reachingDefIn: Iterator[CfgNode] = get().reachingDefIn
  override def _reachingDefIn          = get()._reachingDefIn

  /** Traverse to BLOCK via REACHING_DEF IN edge.
    */
  def _blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = get()._blockViaReachingDefIn

  @deprecated("please use `_blockViaReachingDefIn`", "June 2024")
  def __blockViaReachingDefIn = _blockViaReachingDefIn

  /** Traverse to CALL via REACHING_DEF IN edge.
    */
  def _callViaReachingDefIn: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefIn

  @deprecated("please use `_callViaReachingDefIn`", "June 2024")
  def __callViaReachingDefIn = _callViaReachingDefIn

  /** Traverse to CONTROL_STRUCTURE via REACHING_DEF IN edge.
    */
  def _controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaReachingDefIn

  @deprecated("please use `_controlStructureViaReachingDefIn`", "June 2024")
  def __controlStructureViaReachingDefIn = _controlStructureViaReachingDefIn

  /** Traverse to IDENTIFIER via REACHING_DEF IN edge.
    */
  def _identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefIn

  @deprecated("please use `_identifierViaReachingDefIn`", "June 2024")
  def __identifierViaReachingDefIn = _identifierViaReachingDefIn

  /** Traverse to LITERAL via REACHING_DEF IN edge.
    */
  def _literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefIn

  @deprecated("please use `_literalViaReachingDefIn`", "June 2024")
  def __literalViaReachingDefIn = _literalViaReachingDefIn

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = get()._methodViaReachingDefIn

  @deprecated("please use `_methodViaReachingDefIn`", "June 2024")
  def __methodViaReachingDefIn = _methodViaReachingDefIn

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF IN edge.
    */
  def _methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaReachingDefIn

  @deprecated("please use `_methodParameterInViaReachingDefIn`", "June 2024")
  def __methodParameterInViaReachingDefIn = _methodParameterInViaReachingDefIn

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF IN edge.
    */
  def _methodParameterOutViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaReachingDefIn

  @deprecated("please use `_methodParameterOutViaReachingDefIn`", "June 2024")
  def __methodParameterOutViaReachingDefIn = _methodParameterOutViaReachingDefIn

  /** Traverse to METHOD_REF via REACHING_DEF IN edge.
    */
  def _methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaReachingDefIn

  @deprecated("please use `_methodRefViaReachingDefIn`", "June 2024")
  def __methodRefViaReachingDefIn = _methodRefViaReachingDefIn

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefIn

  @deprecated("please use `_returnViaReachingDefIn`", "June 2024")
  def __returnViaReachingDefIn = _returnViaReachingDefIn

  /** Traverse to TYPE_REF via REACHING_DEF IN edge.
    */
  def _typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaReachingDefIn

  @deprecated("please use `_typeRefViaReachingDefIn`", "June 2024")
  def __typeRefViaReachingDefIn = _typeRefViaReachingDefIn

  /** Traverse to UNKNOWN via REACHING_DEF IN edge.
    */
  def _unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaReachingDefIn

  @deprecated("please use `_unknownViaReachingDefIn`", "June 2024")
  def __unknownViaReachingDefIn = _unknownViaReachingDefIn

  def receiverIn: Iterator[Call] = get().receiverIn
  override def _receiverIn       = get()._receiverIn

  /** Traverse to CALL via RECEIVER IN edge.
    */
  def _callViaReceiverIn: Option[Call] = get()._callViaReceiverIn

  @deprecated("please use `_callViaReceiverIn`", "June 2024")
  def __callViaReceiverIn = _callViaReceiverIn

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
    MethodRef.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "argumentIndex"
      case 2  => "argumentName"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "dynamicTypeHintFullName"
      case 6  => "lineNumber"
      case 7  => "methodFullName"
      case 8  => "order"
      case 9  => "possibleTypes"
      case 10 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => argumentIndex
      case 2  => argumentName
      case 3  => code
      case 4  => columnNumber
      case 5  => dynamicTypeHintFullName
      case 6  => lineNumber
      case 7  => methodFullName
      case 8  => order
      case 9  => possibleTypes
      case 10 => typeFullName
    }

  override def productPrefix = "MethodRef"
  override def productArity  = 11
}

class MethodRefDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with Expression with MethodRefBase {

  override def layoutInformation: NodeLayoutInformation = MethodRef.layoutInformation

  private var _argumentIndex: Integer                      = MethodRef.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int                             = _argumentIndex
  private var _argumentName: String                        = null
  def argumentName: Option[String]                         = Option(_argumentName).asInstanceOf[Option[String]]
  private var _code: String                                = MethodRef.PropertyDefaults.Code
  def code: String                                         = _code
  private var _columnNumber: Integer                       = null
  def columnNumber: Option[scala.Int]                      = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _dynamicTypeHintFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def dynamicTypeHintFullName: IndexedSeq[String]          = _dynamicTypeHintFullName
  private var _lineNumber: Integer                         = null
  def lineNumber: Option[scala.Int]                        = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _methodFullName: String                      = MethodRef.PropertyDefaults.MethodFullName
  def methodFullName: String                               = _methodFullName
  private var _order: Integer                              = MethodRef.PropertyDefaults.Order
  def order: scala.Int                                     = _order
  private var _possibleTypes: IndexedSeq[String]           = collection.immutable.ArraySeq.empty
  def possibleTypes: IndexedSeq[String]                    = _possibleTypes
  private var _typeFullName: String                        = MethodRef.PropertyDefaults.TypeFullName
  def typeFullName: String                                 = _typeFullName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("METHOD_FULL_NAME", methodFullName)
    properties.put("ORDER", order)
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    properties.put("TYPE_FULL_NAME", typeFullName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!((-1: Int) == argumentIndex)) { properties.put("ARGUMENT_INDEX", argumentIndex) }
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == methodFullName)) { properties.put("METHOD_FULL_NAME", methodFullName) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    if (!(("<empty>") == typeFullName)) { properties.put("TYPE_FULL_NAME", typeFullName) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](0)
  override def _argumentOut              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  def astOut: Iterator[Annotation] = createAdjacentNodeScalaIteratorByOffSet[Annotation](1)
  override def _astOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `_annotationViaAstOut`", "June 2024")
  def __annotationViaAstOut = _annotationViaAstOut

  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = astOut.collectAll[Annotation]

  def captureOut: Iterator[ClosureBinding] = createAdjacentNodeScalaIteratorByOffSet[ClosureBinding](2)
  override def _captureOut                 = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `_closureBindingViaCaptureOut`", "June 2024")
  def __closureBindingViaCaptureOut = _closureBindingViaCaptureOut

  def _closureBindingViaCaptureOut: overflowdb.traversal.Traversal[ClosureBinding] =
    captureOut.collectAll[ClosureBinding]

  def cdgOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _cdgOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `_blockViaCdgOut`", "June 2024")
  def __blockViaCdgOut = _blockViaCdgOut

  def _blockViaCdgOut: overflowdb.traversal.Traversal[Block] = cdgOut.collectAll[Block]
  @deprecated("please use `_callViaCdgOut`", "June 2024")
  def __callViaCdgOut = _callViaCdgOut

  def _callViaCdgOut: overflowdb.traversal.Traversal[Call] = cdgOut.collectAll[Call]
  @deprecated("please use `_controlStructureViaCdgOut`", "June 2024")
  def __controlStructureViaCdgOut = _controlStructureViaCdgOut

  def _controlStructureViaCdgOut: overflowdb.traversal.Traversal[ControlStructure] = cdgOut.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaCdgOut`", "June 2024")
  def __fieldIdentifierViaCdgOut = _fieldIdentifierViaCdgOut

  def _fieldIdentifierViaCdgOut: overflowdb.traversal.Traversal[FieldIdentifier] = cdgOut.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaCdgOut`", "June 2024")
  def __identifierViaCdgOut = _identifierViaCdgOut

  def _identifierViaCdgOut: overflowdb.traversal.Traversal[Identifier] = cdgOut.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaCdgOut`", "June 2024")
  def __jumpTargetViaCdgOut = _jumpTargetViaCdgOut

  def _jumpTargetViaCdgOut: overflowdb.traversal.Traversal[JumpTarget] = cdgOut.collectAll[JumpTarget]
  @deprecated("please use `_literalViaCdgOut`", "June 2024")
  def __literalViaCdgOut = _literalViaCdgOut

  def _literalViaCdgOut: overflowdb.traversal.Traversal[Literal] = cdgOut.collectAll[Literal]
  @deprecated("please use `_methodRefViaCdgOut`", "June 2024")
  def __methodRefViaCdgOut = _methodRefViaCdgOut

  def _methodRefViaCdgOut: overflowdb.traversal.Traversal[MethodRef] = cdgOut.collectAll[MethodRef]
  @deprecated("please use `_methodReturnViaCdgOut`", "June 2024")
  def __methodReturnViaCdgOut = _methodReturnViaCdgOut

  def _methodReturnViaCdgOut: overflowdb.traversal.Traversal[MethodReturn] = cdgOut.collectAll[MethodReturn]
  @deprecated("please use `_returnViaCdgOut`", "June 2024")
  def __returnViaCdgOut = _returnViaCdgOut

  def _returnViaCdgOut: overflowdb.traversal.Traversal[Return] = cdgOut.collectAll[Return]
  @deprecated("please use `_typeRefViaCdgOut`", "June 2024")
  def __typeRefViaCdgOut = _typeRefViaCdgOut

  def _typeRefViaCdgOut: overflowdb.traversal.Traversal[TypeRef] = cdgOut.collectAll[TypeRef]
  @deprecated("please use `_unknownViaCdgOut`", "June 2024")
  def __unknownViaCdgOut = _unknownViaCdgOut

  def _unknownViaCdgOut: overflowdb.traversal.Traversal[Unknown] = cdgOut.collectAll[Unknown]

  def cfgOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](4)
  override def _cfgOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  @deprecated("please use `_cfgNodeViaCfgOut`", "June 2024")
  def __cfgNodeViaCfgOut = _cfgNodeViaCfgOut

  def _cfgNodeViaCfgOut: overflowdb.traversal.Traversal[CfgNode] = cfgOut.collectAll[CfgNode]
  @deprecated("please use `_methodReturnViaCfgOut`", "June 2024")
  def __methodReturnViaCfgOut = _methodReturnViaCfgOut

  def _methodReturnViaCfgOut: overflowdb.traversal.Traversal[MethodReturn] = cfgOut.collectAll[MethodReturn]

  def dominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](5)
  override def _dominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  @deprecated("please use `_blockViaDominateOut`", "June 2024")
  def __blockViaDominateOut = _blockViaDominateOut

  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = dominateOut.collectAll[Block]
  @deprecated("please use `_callViaDominateOut`", "June 2024")
  def __callViaDominateOut = _callViaDominateOut

  def _callViaDominateOut: overflowdb.traversal.Traversal[Call] = dominateOut.collectAll[Call]
  @deprecated("please use `_controlStructureViaDominateOut`", "June 2024")
  def __controlStructureViaDominateOut = _controlStructureViaDominateOut

  def _controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    dominateOut.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaDominateOut`", "June 2024")
  def __fieldIdentifierViaDominateOut = _fieldIdentifierViaDominateOut

  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateOut.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaDominateOut`", "June 2024")
  def __identifierViaDominateOut = _identifierViaDominateOut

  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = dominateOut.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaDominateOut`", "June 2024")
  def __jumpTargetViaDominateOut = _jumpTargetViaDominateOut

  def _jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget] = dominateOut.collectAll[JumpTarget]
  @deprecated("please use `_literalViaDominateOut`", "June 2024")
  def __literalViaDominateOut = _literalViaDominateOut

  def _literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = dominateOut.collectAll[Literal]
  @deprecated("please use `_methodRefViaDominateOut`", "June 2024")
  def __methodRefViaDominateOut = _methodRefViaDominateOut

  def _methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = dominateOut.collectAll[MethodRef]
  @deprecated("please use `_methodReturnViaDominateOut`", "June 2024")
  def __methodReturnViaDominateOut = _methodReturnViaDominateOut

  def _methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = dominateOut.collectAll[MethodReturn]
  @deprecated("please use `_returnViaDominateOut`", "June 2024")
  def __returnViaDominateOut = _returnViaDominateOut

  def _returnViaDominateOut: overflowdb.traversal.Traversal[Return] = dominateOut.collectAll[Return]
  @deprecated("please use `_typeRefViaDominateOut`", "June 2024")
  def __typeRefViaDominateOut = _typeRefViaDominateOut

  def _typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = dominateOut.collectAll[TypeRef]
  @deprecated("please use `_unknownViaDominateOut`", "June 2024")
  def __unknownViaDominateOut = _unknownViaDominateOut

  def _unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = dominateOut.collectAll[Unknown]

  def evalTypeOut: Iterator[Type] = createAdjacentNodeScalaIteratorByOffSet[Type](6)
  override def _evalTypeOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)

  @deprecated("please use `_typeViaEvalTypeOut`", "June 2024")
  def __typeViaEvalTypeOut = _typeViaEvalTypeOut

  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def postDominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](7)
  override def _postDominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)

  @deprecated("please use `_blockViaPostDominateOut`", "June 2024")
  def __blockViaPostDominateOut = _blockViaPostDominateOut

  def _blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = postDominateOut.collectAll[Block]
  @deprecated("please use `_callViaPostDominateOut`", "June 2024")
  def __callViaPostDominateOut = _callViaPostDominateOut

  def _callViaPostDominateOut: overflowdb.traversal.Traversal[Call] = postDominateOut.collectAll[Call]
  @deprecated("please use `_controlStructureViaPostDominateOut`", "June 2024")
  def __controlStructureViaPostDominateOut = _controlStructureViaPostDominateOut

  def _controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateOut.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaPostDominateOut`", "June 2024")
  def __fieldIdentifierViaPostDominateOut = _fieldIdentifierViaPostDominateOut

  def _fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateOut.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaPostDominateOut`", "June 2024")
  def __identifierViaPostDominateOut = _identifierViaPostDominateOut

  def _identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = postDominateOut.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaPostDominateOut`", "June 2024")
  def __jumpTargetViaPostDominateOut = _jumpTargetViaPostDominateOut

  def _jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = postDominateOut.collectAll[JumpTarget]
  @deprecated("please use `_literalViaPostDominateOut`", "June 2024")
  def __literalViaPostDominateOut = _literalViaPostDominateOut

  def _literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal] = postDominateOut.collectAll[Literal]
  @deprecated("please use `_methodViaPostDominateOut`", "June 2024")
  def __methodViaPostDominateOut = _methodViaPostDominateOut

  def _methodViaPostDominateOut: overflowdb.traversal.Traversal[Method] = postDominateOut.collectAll[Method]
  @deprecated("please use `_methodRefViaPostDominateOut`", "June 2024")
  def __methodRefViaPostDominateOut = _methodRefViaPostDominateOut

  def _methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef] = postDominateOut.collectAll[MethodRef]
  @deprecated("please use `_returnViaPostDominateOut`", "June 2024")
  def __returnViaPostDominateOut = _returnViaPostDominateOut

  def _returnViaPostDominateOut: overflowdb.traversal.Traversal[Return] = postDominateOut.collectAll[Return]
  @deprecated("please use `_typeRefViaPostDominateOut`", "June 2024")
  def __typeRefViaPostDominateOut = _typeRefViaPostDominateOut

  def _typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef] = postDominateOut.collectAll[TypeRef]
  @deprecated("please use `_unknownViaPostDominateOut`", "June 2024")
  def __unknownViaPostDominateOut = _unknownViaPostDominateOut

  def _unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown] = postDominateOut.collectAll[Unknown]

  def reachingDefOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](8)
  override def _reachingDefOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)

  @deprecated("please use `_callViaReachingDefOut`", "June 2024")
  def __callViaReachingDefOut = _callViaReachingDefOut

  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = reachingDefOut.collectAll[Call]
  @deprecated("please use `_identifierViaReachingDefOut`", "June 2024")
  def __identifierViaReachingDefOut = _identifierViaReachingDefOut

  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  @deprecated("please use `_literalViaReachingDefOut`", "June 2024")
  def __literalViaReachingDefOut = _literalViaReachingDefOut

  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = reachingDefOut.collectAll[Literal]
  @deprecated("please use `_methodParameterOutViaReachingDefOut`", "June 2024")
  def __methodParameterOutViaReachingDefOut = _methodParameterOutViaReachingDefOut

  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefOut.collectAll[MethodParameterOut]
  @deprecated("please use `_methodRefViaReachingDefOut`", "June 2024")
  def __methodRefViaReachingDefOut = _methodRefViaReachingDefOut

  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = reachingDefOut.collectAll[MethodRef]
  @deprecated("please use `_returnViaReachingDefOut`", "June 2024")
  def __returnViaReachingDefOut = _returnViaReachingDefOut

  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = reachingDefOut.collectAll[Return]
  @deprecated("please use `_typeRefViaReachingDefOut`", "June 2024")
  def __typeRefViaReachingDefOut = _typeRefViaReachingDefOut

  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = reachingDefOut.collectAll[TypeRef]

  def refOut: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](9)
  override def _refOut         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)

  @deprecated("please use `referencedMethod`", "June 2024")
  def _referencedMethod = referencedMethod

  def referencedMethod: Method = try { refOut.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label REF to an adjacent METHOD is mandatory, but not defined for this METHOD_REF node with id=" + id,
        e
      )
  }

  def taggedByOut: Iterator[Tag] = createAdjacentNodeScalaIteratorByOffSet[Tag](10)
  override def _taggedByOut      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](10)

  @deprecated("please use `_tagViaTaggedByOut`", "June 2024")
  def __tagViaTaggedByOut = _tagViaTaggedByOut

  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def argumentIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](11)
  override def _argumentIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](11)

  @deprecated("please use `_callViaArgumentIn`", "June 2024")
  def __callViaArgumentIn = _callViaArgumentIn

  def _callViaArgumentIn: Option[Call] = argumentIn.collectAll[Call].nextOption()
  @deprecated("please use `_returnViaArgumentIn`", "June 2024")
  def __returnViaArgumentIn = _returnViaArgumentIn

  def _returnViaArgumentIn: Option[Return] = argumentIn.collectAll[Return].nextOption()

  def astIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](12)
  override def _astIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)

  @deprecated("please use `_blockViaAstIn`", "June 2024")
  def __blockViaAstIn = _blockViaAstIn

  def _blockViaAstIn: overflowdb.traversal.Traversal[Block] = astIn.collectAll[Block]
  @deprecated("please use `_callViaAstIn`", "June 2024")
  def __callViaAstIn = _callViaAstIn

  def _callViaAstIn: overflowdb.traversal.Traversal[Call] = astIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaAstIn`", "June 2024")
  def __controlStructureViaAstIn = _controlStructureViaAstIn

  def _controlStructureViaAstIn: ControlStructure = try { astIn.collectAll[ControlStructure].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent CONTROL_STRUCTURE is mandatory, but not defined for this METHOD_REF node with id=" + id,
        e
      )
  }
  @deprecated("please use `_returnViaAstIn`", "June 2024")
  def __returnViaAstIn = _returnViaAstIn

  def _returnViaAstIn: overflowdb.traversal.Traversal[Return] = astIn.collectAll[Return]

  def cdgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](13)
  override def _cdgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](13)

  @deprecated("please use `_blockViaCdgIn`", "June 2024")
  def __blockViaCdgIn = _blockViaCdgIn

  def _blockViaCdgIn: overflowdb.traversal.Traversal[Block] = cdgIn.collectAll[Block]
  @deprecated("please use `_callViaCdgIn`", "June 2024")
  def __callViaCdgIn = _callViaCdgIn

  def _callViaCdgIn: overflowdb.traversal.Traversal[Call] = cdgIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaCdgIn`", "June 2024")
  def __controlStructureViaCdgIn = _controlStructureViaCdgIn

  def _controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = cdgIn.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaCdgIn`", "June 2024")
  def __fieldIdentifierViaCdgIn = _fieldIdentifierViaCdgIn

  def _fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier] = cdgIn.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaCdgIn`", "June 2024")
  def __identifierViaCdgIn = _identifierViaCdgIn

  def _identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier] = cdgIn.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaCdgIn`", "June 2024")
  def __jumpTargetViaCdgIn = _jumpTargetViaCdgIn

  def _jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget] = cdgIn.collectAll[JumpTarget]
  @deprecated("please use `_literalViaCdgIn`", "June 2024")
  def __literalViaCdgIn = _literalViaCdgIn

  def _literalViaCdgIn: overflowdb.traversal.Traversal[Literal] = cdgIn.collectAll[Literal]
  @deprecated("please use `_methodRefViaCdgIn`", "June 2024")
  def __methodRefViaCdgIn = _methodRefViaCdgIn

  def _methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef] = cdgIn.collectAll[MethodRef]
  @deprecated("please use `_typeRefViaCdgIn`", "June 2024")
  def __typeRefViaCdgIn = _typeRefViaCdgIn

  def _typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef] = cdgIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaCdgIn`", "June 2024")
  def __unknownViaCdgIn = _unknownViaCdgIn

  def _unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown] = cdgIn.collectAll[Unknown]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](14)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](14)

  def conditionIn: Iterator[ControlStructure] = createAdjacentNodeScalaIteratorByOffSet[ControlStructure](15)
  override def _conditionIn                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](15)

  @deprecated("please use `_controlStructureViaConditionIn`", "June 2024")
  def __controlStructureViaConditionIn = _controlStructureViaConditionIn

  def _controlStructureViaConditionIn: overflowdb.traversal.Traversal[ControlStructure] =
    conditionIn.collectAll[ControlStructure]

  def containsIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](16)
  override def _containsIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](16)

  @deprecated("please use `_methodViaContainsIn`", "June 2024")
  def __methodViaContainsIn = _methodViaContainsIn

  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = containsIn.collectAll[Method]

  def dominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](17)
  override def _dominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](17)

  @deprecated("please use `_blockViaDominateIn`", "June 2024")
  def __blockViaDominateIn = _blockViaDominateIn

  def _blockViaDominateIn: overflowdb.traversal.Traversal[Block] = dominateIn.collectAll[Block]
  @deprecated("please use `_callViaDominateIn`", "June 2024")
  def __callViaDominateIn = _callViaDominateIn

  def _callViaDominateIn: overflowdb.traversal.Traversal[Call] = dominateIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaDominateIn`", "June 2024")
  def __controlStructureViaDominateIn = _controlStructureViaDominateIn

  def _controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    dominateIn.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaDominateIn`", "June 2024")
  def __fieldIdentifierViaDominateIn = _fieldIdentifierViaDominateIn

  def _fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateIn.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaDominateIn`", "June 2024")
  def __identifierViaDominateIn = _identifierViaDominateIn

  def _identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = dominateIn.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaDominateIn`", "June 2024")
  def __jumpTargetViaDominateIn = _jumpTargetViaDominateIn

  def _jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = dominateIn.collectAll[JumpTarget]
  @deprecated("please use `_literalViaDominateIn`", "June 2024")
  def __literalViaDominateIn = _literalViaDominateIn

  def _literalViaDominateIn: overflowdb.traversal.Traversal[Literal] = dominateIn.collectAll[Literal]
  @deprecated("please use `_methodViaDominateIn`", "June 2024")
  def __methodViaDominateIn = _methodViaDominateIn

  def _methodViaDominateIn: overflowdb.traversal.Traversal[Method] = dominateIn.collectAll[Method]
  @deprecated("please use `_methodRefViaDominateIn`", "June 2024")
  def __methodRefViaDominateIn = _methodRefViaDominateIn

  def _methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef] = dominateIn.collectAll[MethodRef]
  @deprecated("please use `_returnViaDominateIn`", "June 2024")
  def __returnViaDominateIn = _returnViaDominateIn

  def _returnViaDominateIn: overflowdb.traversal.Traversal[Return] = dominateIn.collectAll[Return]
  @deprecated("please use `_typeRefViaDominateIn`", "June 2024")
  def __typeRefViaDominateIn = _typeRefViaDominateIn

  def _typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef] = dominateIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaDominateIn`", "June 2024")
  def __unknownViaDominateIn = _unknownViaDominateIn

  def _unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown] = dominateIn.collectAll[Unknown]

  def postDominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](18)
  override def _postDominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](18)

  @deprecated("please use `_blockViaPostDominateIn`", "June 2024")
  def __blockViaPostDominateIn = _blockViaPostDominateIn

  def _blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = postDominateIn.collectAll[Block]
  @deprecated("please use `_callViaPostDominateIn`", "June 2024")
  def __callViaPostDominateIn = _callViaPostDominateIn

  def _callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = postDominateIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaPostDominateIn`", "June 2024")
  def __controlStructureViaPostDominateIn = _controlStructureViaPostDominateIn

  def _controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateIn.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaPostDominateIn`", "June 2024")
  def __fieldIdentifierViaPostDominateIn = _fieldIdentifierViaPostDominateIn

  def _fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateIn.collectAll[FieldIdentifier]
  @deprecated("please use `_identifierViaPostDominateIn`", "June 2024")
  def __identifierViaPostDominateIn = _identifierViaPostDominateIn

  def _identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = postDominateIn.collectAll[Identifier]
  @deprecated("please use `_jumpTargetViaPostDominateIn`", "June 2024")
  def __jumpTargetViaPostDominateIn = _jumpTargetViaPostDominateIn

  def _jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = postDominateIn.collectAll[JumpTarget]
  @deprecated("please use `_literalViaPostDominateIn`", "June 2024")
  def __literalViaPostDominateIn = _literalViaPostDominateIn

  def _literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = postDominateIn.collectAll[Literal]
  @deprecated("please use `_methodRefViaPostDominateIn`", "June 2024")
  def __methodRefViaPostDominateIn = _methodRefViaPostDominateIn

  def _methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = postDominateIn.collectAll[MethodRef]
  @deprecated("please use `_methodReturnViaPostDominateIn`", "June 2024")
  def __methodReturnViaPostDominateIn = _methodReturnViaPostDominateIn

  def _methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    postDominateIn.collectAll[MethodReturn]
  @deprecated("please use `_returnViaPostDominateIn`", "June 2024")
  def __returnViaPostDominateIn = _returnViaPostDominateIn

  def _returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = postDominateIn.collectAll[Return]
  @deprecated("please use `_typeRefViaPostDominateIn`", "June 2024")
  def __typeRefViaPostDominateIn = _typeRefViaPostDominateIn

  def _typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = postDominateIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaPostDominateIn`", "June 2024")
  def __unknownViaPostDominateIn = _unknownViaPostDominateIn

  def _unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = postDominateIn.collectAll[Unknown]

  def reachingDefIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](19)
  override def _reachingDefIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](19)

  @deprecated("please use `_blockViaReachingDefIn`", "June 2024")
  def __blockViaReachingDefIn = _blockViaReachingDefIn

  def _blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = reachingDefIn.collectAll[Block]
  @deprecated("please use `_callViaReachingDefIn`", "June 2024")
  def __callViaReachingDefIn = _callViaReachingDefIn

  def _callViaReachingDefIn: overflowdb.traversal.Traversal[Call] = reachingDefIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaReachingDefIn`", "June 2024")
  def __controlStructureViaReachingDefIn = _controlStructureViaReachingDefIn

  def _controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    reachingDefIn.collectAll[ControlStructure]
  @deprecated("please use `_identifierViaReachingDefIn`", "June 2024")
  def __identifierViaReachingDefIn = _identifierViaReachingDefIn

  def _identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = reachingDefIn.collectAll[Identifier]
  @deprecated("please use `_literalViaReachingDefIn`", "June 2024")
  def __literalViaReachingDefIn = _literalViaReachingDefIn

  def _literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal] = reachingDefIn.collectAll[Literal]
  @deprecated("please use `_methodViaReachingDefIn`", "June 2024")
  def __methodViaReachingDefIn = _methodViaReachingDefIn

  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = reachingDefIn.collectAll[Method]
  @deprecated("please use `_methodParameterInViaReachingDefIn`", "June 2024")
  def __methodParameterInViaReachingDefIn = _methodParameterInViaReachingDefIn

  def _methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    reachingDefIn.collectAll[MethodParameterIn]
  @deprecated("please use `_methodParameterOutViaReachingDefIn`", "June 2024")
  def __methodParameterOutViaReachingDefIn = _methodParameterOutViaReachingDefIn

  def _methodParameterOutViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefIn.collectAll[MethodParameterOut]
  @deprecated("please use `_methodRefViaReachingDefIn`", "June 2024")
  def __methodRefViaReachingDefIn = _methodRefViaReachingDefIn

  def _methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = reachingDefIn.collectAll[MethodRef]
  @deprecated("please use `_returnViaReachingDefIn`", "June 2024")
  def __returnViaReachingDefIn = _returnViaReachingDefIn

  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = reachingDefIn.collectAll[Return]
  @deprecated("please use `_typeRefViaReachingDefIn`", "June 2024")
  def __typeRefViaReachingDefIn = _typeRefViaReachingDefIn

  def _typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef] = reachingDefIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaReachingDefIn`", "June 2024")
  def __unknownViaReachingDefIn = _unknownViaReachingDefIn

  def _unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown] = reachingDefIn.collectAll[Unknown]

  def receiverIn: Iterator[Call] = createAdjacentNodeScalaIteratorByOffSet[Call](20)
  override def _receiverIn       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](20)

  @deprecated("please use `_callViaReceiverIn`", "June 2024")
  def __callViaReceiverIn = _callViaReceiverIn

  def _callViaReceiverIn: Option[Call] = receiverIn.collectAll[Call].nextOption()

  override def label: String = {
    MethodRef.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "argumentIndex"
      case 2  => "argumentName"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "dynamicTypeHintFullName"
      case 6  => "lineNumber"
      case 7  => "methodFullName"
      case 8  => "order"
      case 9  => "possibleTypes"
      case 10 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => argumentIndex
      case 2  => argumentName
      case 3  => code
      case 4  => columnNumber
      case 5  => dynamicTypeHintFullName
      case 6  => lineNumber
      case 7  => methodFullName
      case 8  => order
      case 9  => possibleTypes
      case 10 => typeFullName
    }

  override def productPrefix = "MethodRef"
  override def productArity  = 11

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodRefDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX"              => this._argumentIndex
      case "ARGUMENT_NAME"               => this._argumentName
      case "CODE"                        => this._code
      case "COLUMN_NUMBER"               => this._columnNumber
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
      case "LINE_NUMBER"                 => this._lineNumber
      case "METHOD_FULL_NAME"            => this._methodFullName
      case "ORDER"                       => this._order
      case "POSSIBLE_TYPES"              => this._possibleTypes
      case "TYPE_FULL_NAME"              => this._typeFullName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[scala.Int]
      case "ARGUMENT_NAME"  => this._argumentName = value.asInstanceOf[String]
      case "CODE"           => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"  => this._columnNumber = value.asInstanceOf[scala.Int]
      case "DYNAMIC_TYPE_HINT_FULL_NAME" =>
        this._dynamicTypeHintFullName = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: String                              => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[String]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[String]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "LINE_NUMBER"      => this._lineNumber = value.asInstanceOf[scala.Int]
      case "METHOD_FULL_NAME" => this._methodFullName = value.asInstanceOf[String]
      case "ORDER"            => this._order = value.asInstanceOf[scala.Int]
      case "POSSIBLE_TYPES" =>
        this._possibleTypes = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: String                              => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[String]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[String]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]

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
    this._argumentIndex = newNode.asInstanceOf[NewMethodRef].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewMethodRef].argumentName match {
      case None => null; case Some(value) => value
    }
    this._code = newNode.asInstanceOf[NewMethodRef].code
    this._columnNumber = newNode.asInstanceOf[NewMethodRef].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._dynamicTypeHintFullName =
      if (newNode.asInstanceOf[NewMethodRef].dynamicTypeHintFullName != null)
        newNode.asInstanceOf[NewMethodRef].dynamicTypeHintFullName
      else collection.immutable.ArraySeq.empty
    this._lineNumber = newNode.asInstanceOf[NewMethodRef].lineNumber match {
      case None => null; case Some(value) => value
    }
    this._methodFullName = newNode.asInstanceOf[NewMethodRef].methodFullName
    this._order = newNode.asInstanceOf[NewMethodRef].order
    this._possibleTypes =
      if (newNode.asInstanceOf[NewMethodRef].possibleTypes != null) newNode.asInstanceOf[NewMethodRef].possibleTypes
      else collection.immutable.ArraySeq.empty
    this._typeFullName = newNode.asInstanceOf[NewMethodRef].typeFullName

  }

}
