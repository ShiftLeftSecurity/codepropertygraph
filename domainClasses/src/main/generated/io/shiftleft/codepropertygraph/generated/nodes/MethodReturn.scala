package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object MethodReturn {
  def apply(graph: Graph, id: Long) = new MethodReturn(graph, id)

  val Label = "METHOD_RETURN"

  object PropertyNames {
    val Code                    = "CODE"
    val ColumnNumber            = "COLUMN_NUMBER"
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME"
    val EvaluationStrategy      = "EVALUATION_STRATEGY"
    val LineNumber              = "LINE_NUMBER"
    val Order                   = "ORDER"
    val PossibleTypes           = "POSSIBLE_TYPES"
    val TypeFullName            = "TYPE_FULL_NAME"
    val all: Set[String] = Set(
      Code,
      ColumnNumber,
      DynamicTypeHintFullName,
      EvaluationStrategy,
      LineNumber,
      Order,
      PossibleTypes,
      TypeFullName
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code                    = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber            = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val DynamicTypeHintFullName = new overflowdb.PropertyKey[IndexedSeq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME")
    val EvaluationStrategy      = new overflowdb.PropertyKey[String]("EVALUATION_STRATEGY")
    val LineNumber              = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val Order                   = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val PossibleTypes           = new overflowdb.PropertyKey[IndexedSeq[String]]("POSSIBLE_TYPES")
    val TypeFullName            = new overflowdb.PropertyKey[String]("TYPE_FULL_NAME")

  }

  object PropertyDefaults {
    val Code               = "<empty>"
    val EvaluationStrategy = "<empty>"
    val Order              = -1: Int
    val TypeFullName       = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("EVAL_TYPE", "POST_DOMINATE", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CDG", "CFG", "DOMINATE", "REACHING_DEF")
  }

  val factory = new NodeFactory[MethodReturnDb] {
    override val forLabel = MethodReturn.Label

    override def createNode(ref: NodeRef[MethodReturnDb]) =
      new MethodReturnDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodReturn(graph, id)
  }
}

trait MethodReturnBase extends AbstractNode with CfgNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[scala.Int]
  def dynamicTypeHintFullName: IndexedSeq[String]
  def evaluationStrategy: String
  def lineNumber: Option[scala.Int]
  def order: scala.Int
  def possibleTypes: IndexedSeq[String]
  def typeFullName: String

}

class MethodReturn(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodReturnDb](graph_4762, id_4762)
    with MethodReturnBase
    with StoredNode
    with CfgNode {
  override def code: String                                = get().code
  override def columnNumber: Option[scala.Int]             = get().columnNumber
  override def dynamicTypeHintFullName: IndexedSeq[String] = get().dynamicTypeHintFullName
  override def evaluationStrategy: String                  = get().evaluationStrategy
  override def lineNumber: Option[scala.Int]               = get().lineNumber
  override def order: scala.Int                            = get().order
  override def possibleTypes: IndexedSeq[String]           = get().possibleTypes
  override def typeFullName: String                        = get().typeFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"                => MethodReturn.PropertyDefaults.Code
      case "EVALUATION_STRATEGY" => MethodReturn.PropertyDefaults.EvaluationStrategy
      case "ORDER"               => MethodReturn.PropertyDefaults.Order
      case "TYPE_FULL_NAME"      => MethodReturn.PropertyDefaults.TypeFullName
      case _                     => super.propertyDefaultValue(propertyKey)
    }
  }

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = get().typeViaEvalTypeOut

  @deprecated("please use `typeViaEvalTypeOut`", "June 2024")
  def _typeViaEvalTypeOut = typeViaEvalTypeOut

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

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get().tagViaTaggedByOut

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def astIn: Iterator[Method] = get().astIn
  override def _astIn         = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Method = get().methodViaAstIn

  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

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

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: overflowdb.traversal.Traversal[Return] = get().toReturn

  @deprecated("please use `toReturn`", "June 2024")
  def _toReturn = toReturn

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

  def reachingDefIn: Iterator[Return] = get().reachingDefIn
  override def _reachingDefIn         = get()._reachingDefIn

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = get().returnViaReachingDefIn

  @deprecated("please use `returnViaReachingDefIn`", "June 2024")
  def _returnViaReachingDefIn = returnViaReachingDefIn

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
    MethodReturn.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "dynamicTypeHintFullName"
      case 4 => "evaluationStrategy"
      case 5 => "lineNumber"
      case 6 => "order"
      case 7 => "possibleTypes"
      case 8 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => dynamicTypeHintFullName
      case 4 => evaluationStrategy
      case 5 => lineNumber
      case 6 => order
      case 7 => possibleTypes
      case 8 => typeFullName
    }

  override def productPrefix = "MethodReturn"
  override def productArity  = 9
}

class MethodReturnDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with CfgNode with MethodReturnBase {

  override def layoutInformation: NodeLayoutInformation = MethodReturn.layoutInformation

  private var _code: String                                = MethodReturn.PropertyDefaults.Code
  def code: String                                         = _code
  private var _columnNumber: Integer                       = null
  def columnNumber: Option[scala.Int]                      = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _dynamicTypeHintFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def dynamicTypeHintFullName: IndexedSeq[String]          = _dynamicTypeHintFullName
  private var _evaluationStrategy: String                  = MethodReturn.PropertyDefaults.EvaluationStrategy
  def evaluationStrategy: String                           = _evaluationStrategy
  private var _lineNumber: Integer                         = null
  def lineNumber: Option[scala.Int]                        = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _order: Integer                              = MethodReturn.PropertyDefaults.Order
  def order: scala.Int                                     = _order
  private var _possibleTypes: IndexedSeq[String]           = collection.immutable.ArraySeq.empty
  def possibleTypes: IndexedSeq[String]                    = _possibleTypes
  private var _typeFullName: String                        = MethodReturn.PropertyDefaults.TypeFullName
  def typeFullName: String                                 = _typeFullName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    properties.put("EVALUATION_STRATEGY", evaluationStrategy)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("ORDER", order)
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    properties.put("TYPE_FULL_NAME", typeFullName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    if (!(("<empty>") == evaluationStrategy)) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    if (!(("<empty>") == typeFullName)) { properties.put("TYPE_FULL_NAME", typeFullName) }

    properties
  }

  import overflowdb.traversal._
  def evalTypeOut: Iterator[Type] = createAdjacentNodeScalaIteratorByOffSet[Type](0)
  override def _evalTypeOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  @deprecated("please use `typeViaEvalTypeOut`", "June 2024")
  def _typeViaEvalTypeOut = typeViaEvalTypeOut

  def typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def postDominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](1)
  override def _postDominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

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

  def taggedByOut: Iterator[Tag] = createAdjacentNodeScalaIteratorByOffSet[Tag](2)
  override def _taggedByOut      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](3)
  override def _astIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

  def methodViaAstIn: Method = try { astIn.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_RETURN node with id=" + id,
        e
      )
  }

  def cdgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _cdgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

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

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](5)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  @deprecated("please use `toReturn`", "June 2024")
  def _toReturn = toReturn

  def toReturn: overflowdb.traversal.Traversal[Return] = cfgIn.collectAll[Return]

  def dominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](6)
  override def _dominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)

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

  def reachingDefIn: Iterator[Return] = createAdjacentNodeScalaIteratorByOffSet[Return](7)
  override def _reachingDefIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)

  @deprecated("please use `returnViaReachingDefIn`", "June 2024")
  def _returnViaReachingDefIn = returnViaReachingDefIn

  def returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = reachingDefIn.collectAll[Return]

  override def label: String = {
    MethodReturn.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "dynamicTypeHintFullName"
      case 4 => "evaluationStrategy"
      case 5 => "lineNumber"
      case 6 => "order"
      case 7 => "possibleTypes"
      case 8 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => dynamicTypeHintFullName
      case 4 => evaluationStrategy
      case 5 => lineNumber
      case 6 => order
      case 7 => possibleTypes
      case 8 => typeFullName
    }

  override def productPrefix = "MethodReturn"
  override def productArity  = 9

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodReturnDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"                        => this._code
      case "COLUMN_NUMBER"               => this._columnNumber
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
      case "EVALUATION_STRATEGY"         => this._evaluationStrategy
      case "LINE_NUMBER"                 => this._lineNumber
      case "ORDER"                       => this._order
      case "POSSIBLE_TYPES"              => this._possibleTypes
      case "TYPE_FULL_NAME"              => this._typeFullName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"          => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[scala.Int]
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
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
      case "LINE_NUMBER"         => this._lineNumber = value.asInstanceOf[scala.Int]
      case "ORDER"               => this._order = value.asInstanceOf[scala.Int]
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
    this._code = newNode.asInstanceOf[NewMethodReturn].code
    this._columnNumber = newNode.asInstanceOf[NewMethodReturn].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._dynamicTypeHintFullName =
      if (newNode.asInstanceOf[NewMethodReturn].dynamicTypeHintFullName != null)
        newNode.asInstanceOf[NewMethodReturn].dynamicTypeHintFullName
      else collection.immutable.ArraySeq.empty
    this._evaluationStrategy = newNode.asInstanceOf[NewMethodReturn].evaluationStrategy
    this._lineNumber = newNode.asInstanceOf[NewMethodReturn].lineNumber match {
      case None => null; case Some(value) => value
    }
    this._order = newNode.asInstanceOf[NewMethodReturn].order
    this._possibleTypes =
      if (newNode.asInstanceOf[NewMethodReturn].possibleTypes != null)
        newNode.asInstanceOf[NewMethodReturn].possibleTypes
      else collection.immutable.ArraySeq.empty
    this._typeFullName = newNode.asInstanceOf[NewMethodReturn].typeFullName

  }

}
