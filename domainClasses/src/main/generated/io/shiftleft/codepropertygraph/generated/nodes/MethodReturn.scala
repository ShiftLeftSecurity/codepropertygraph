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
    val ColumnNumber            = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val DynamicTypeHintFullName = new overflowdb.PropertyKey[IndexedSeq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME")
    val EvaluationStrategy      = new overflowdb.PropertyKey[String]("EVALUATION_STRATEGY")
    val LineNumber              = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
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
  def columnNumber: Option[Integer]
  def dynamicTypeHintFullName: IndexedSeq[String]
  def evaluationStrategy: String
  def lineNumber: Option[Integer]
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
  override def columnNumber: Option[Integer]               = get().columnNumber
  override def dynamicTypeHintFullName: IndexedSeq[String] = get().dynamicTypeHintFullName
  override def evaluationStrategy: String                  = get().evaluationStrategy
  override def lineNumber: Option[Integer]                 = get().lineNumber
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
  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = get()._typeViaEvalTypeOut

  def postDominateOut: Iterator[CfgNode] = get().postDominateOut
  override def _postDominateOut          = get()._postDominateOut

  /** Traverse to BLOCK via POST_DOMINATE OUT edge.
    */
  def _blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = get()._blockViaPostDominateOut

  /** Traverse to CALL via POST_DOMINATE OUT edge.
    */
  def _callViaPostDominateOut: overflowdb.traversal.Traversal[Call] = get()._callViaPostDominateOut

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE OUT edge.
    */
  def _controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaPostDominateOut

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaPostDominateOut

  /** Traverse to IDENTIFIER via POST_DOMINATE OUT edge.
    */
  def _identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaPostDominateOut

  /** Traverse to JUMP_TARGET via POST_DOMINATE OUT edge.
    */
  def _jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaPostDominateOut

  /** Traverse to LITERAL via POST_DOMINATE OUT edge.
    */
  def _literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaPostDominateOut

  /** Traverse to METHOD via POST_DOMINATE OUT edge.
    */
  def _methodViaPostDominateOut: overflowdb.traversal.Traversal[Method] = get()._methodViaPostDominateOut

  /** Traverse to METHOD_REF via POST_DOMINATE OUT edge.
    */
  def _methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaPostDominateOut

  /** Traverse to RETURN via POST_DOMINATE OUT edge.
    */
  def _returnViaPostDominateOut: overflowdb.traversal.Traversal[Return] = get()._returnViaPostDominateOut

  /** Traverse to TYPE_REF via POST_DOMINATE OUT edge.
    */
  def _typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaPostDominateOut

  /** Traverse to UNKNOWN via POST_DOMINATE OUT edge.
    */
  def _unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaPostDominateOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[Method] = get().astIn
  override def _astIn         = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Method = get()._methodViaAstIn

  def cdgIn: Iterator[CfgNode] = get().cdgIn
  override def _cdgIn          = get()._cdgIn

  /** Traverse to BLOCK via CDG IN edge.
    */
  def _blockViaCdgIn: overflowdb.traversal.Traversal[Block] = get()._blockViaCdgIn

  /** Traverse to CALL via CDG IN edge.
    */
  def _callViaCdgIn: overflowdb.traversal.Traversal[Call] = get()._callViaCdgIn

  /** Traverse to CONTROL_STRUCTURE via CDG IN edge.
    */
  def _controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaCdgIn

  /** Traverse to FIELD_IDENTIFIER via CDG IN edge.
    */
  def _fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier] = get()._fieldIdentifierViaCdgIn

  /** Traverse to IDENTIFIER via CDG IN edge.
    */
  def _identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaCdgIn

  /** Traverse to JUMP_TARGET via CDG IN edge.
    */
  def _jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaCdgIn

  /** Traverse to LITERAL via CDG IN edge.
    */
  def _literalViaCdgIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaCdgIn

  /** Traverse to METHOD_REF via CDG IN edge.
    */
  def _methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaCdgIn

  /** Traverse to TYPE_REF via CDG IN edge.
    */
  def _typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaCdgIn

  /** Traverse to UNKNOWN via CDG IN edge.
    */
  def _unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaCdgIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  /** Traverse to RETURN via CFG IN edge.
    */
  def toReturn: overflowdb.traversal.Traversal[Return] = get().toReturn

  def dominateIn: Iterator[CfgNode] = get().dominateIn
  override def _dominateIn          = get()._dominateIn

  /** Traverse to BLOCK via DOMINATE IN edge.
    */
  def _blockViaDominateIn: overflowdb.traversal.Traversal[Block] = get()._blockViaDominateIn

  /** Traverse to CALL via DOMINATE IN edge.
    */
  def _callViaDominateIn: overflowdb.traversal.Traversal[Call] = get()._callViaDominateIn

  /** Traverse to CONTROL_STRUCTURE via DOMINATE IN edge.
    */
  def _controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaDominateIn

  /** Traverse to FIELD_IDENTIFIER via DOMINATE IN edge.
    */
  def _fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaDominateIn

  /** Traverse to IDENTIFIER via DOMINATE IN edge.
    */
  def _identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaDominateIn

  /** Traverse to LITERAL via DOMINATE IN edge.
    */
  def _literalViaDominateIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaDominateIn

  /** Traverse to METHOD via DOMINATE IN edge.
    */
  def _methodViaDominateIn: overflowdb.traversal.Traversal[Method] = get()._methodViaDominateIn

  /** Traverse to METHOD_REF via DOMINATE IN edge.
    */
  def _methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaDominateIn

  /** Traverse to RETURN via DOMINATE IN edge.
    */
  def _returnViaDominateIn: overflowdb.traversal.Traversal[Return] = get()._returnViaDominateIn

  /** Traverse to TYPE_REF via DOMINATE IN edge.
    */
  def _typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaDominateIn

  /** Traverse to UNKNOWN via DOMINATE IN edge.
    */
  def _unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaDominateIn

  def reachingDefIn: Iterator[Return] = get().reachingDefIn
  override def _reachingDefIn         = get()._reachingDefIn

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefIn

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
  def columnNumber: Option[Integer]                        = Option(_columnNumber)
  private var _dynamicTypeHintFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def dynamicTypeHintFullName: IndexedSeq[String]          = _dynamicTypeHintFullName
  private var _evaluationStrategy: String                  = MethodReturn.PropertyDefaults.EvaluationStrategy
  def evaluationStrategy: String                           = _evaluationStrategy
  private var _lineNumber: Integer                         = null
  def lineNumber: Option[Integer]                          = Option(_lineNumber)
  private var _order: scala.Int                            = MethodReturn.PropertyDefaults.Order
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
  def evalTypeOut: Iterator[Type]                               = createAdjacentNodeScalaIteratorByOffSet[Type](0)
  override def _evalTypeOut                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def postDominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](1)
  override def _postDominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _blockViaPostDominateOut: overflowdb.traversal.Traversal[Block] = postDominateOut.collectAll[Block]
  def _callViaPostDominateOut: overflowdb.traversal.Traversal[Call]   = postDominateOut.collectAll[Call]
  def _controlStructureViaPostDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateOut.collectAll[ControlStructure]
  def _fieldIdentifierViaPostDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateOut.collectAll[FieldIdentifier]
  def _identifierViaPostDominateOut: overflowdb.traversal.Traversal[Identifier] = postDominateOut.collectAll[Identifier]
  def _jumpTargetViaPostDominateOut: overflowdb.traversal.Traversal[JumpTarget] = postDominateOut.collectAll[JumpTarget]
  def _literalViaPostDominateOut: overflowdb.traversal.Traversal[Literal]       = postDominateOut.collectAll[Literal]
  def _methodViaPostDominateOut: overflowdb.traversal.Traversal[Method]         = postDominateOut.collectAll[Method]
  def _methodRefViaPostDominateOut: overflowdb.traversal.Traversal[MethodRef]   = postDominateOut.collectAll[MethodRef]
  def _returnViaPostDominateOut: overflowdb.traversal.Traversal[Return]         = postDominateOut.collectAll[Return]
  def _typeRefViaPostDominateOut: overflowdb.traversal.Traversal[TypeRef]       = postDominateOut.collectAll[TypeRef]
  def _unknownViaPostDominateOut: overflowdb.traversal.Traversal[Unknown]       = postDominateOut.collectAll[Unknown]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](2)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](3)
  override def _astIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _methodViaAstIn: Method = try { astIn.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_RETURN node with id=" + id,
        e
      )
  }

  def cdgIn: Iterator[CfgNode]                              = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _cdgIn                                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _blockViaCdgIn: overflowdb.traversal.Traversal[Block] = cdgIn.collectAll[Block]
  def _callViaCdgIn: overflowdb.traversal.Traversal[Call]   = cdgIn.collectAll[Call]
  def _controlStructureViaCdgIn: overflowdb.traversal.Traversal[ControlStructure] = cdgIn.collectAll[ControlStructure]
  def _fieldIdentifierViaCdgIn: overflowdb.traversal.Traversal[FieldIdentifier]   = cdgIn.collectAll[FieldIdentifier]
  def _identifierViaCdgIn: overflowdb.traversal.Traversal[Identifier]             = cdgIn.collectAll[Identifier]
  def _jumpTargetViaCdgIn: overflowdb.traversal.Traversal[JumpTarget]             = cdgIn.collectAll[JumpTarget]
  def _literalViaCdgIn: overflowdb.traversal.Traversal[Literal]                   = cdgIn.collectAll[Literal]
  def _methodRefViaCdgIn: overflowdb.traversal.Traversal[MethodRef]               = cdgIn.collectAll[MethodRef]
  def _typeRefViaCdgIn: overflowdb.traversal.Traversal[TypeRef]                   = cdgIn.collectAll[TypeRef]
  def _unknownViaCdgIn: overflowdb.traversal.Traversal[Unknown]                   = cdgIn.collectAll[Unknown]

  def cfgIn: Iterator[CfgNode]                         = createAdjacentNodeScalaIteratorByOffSet[CfgNode](5)
  override def _cfgIn                                  = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def toReturn: overflowdb.traversal.Traversal[Return] = cfgIn.collectAll[Return]

  def dominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](6)
  override def _dominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def _blockViaDominateIn: overflowdb.traversal.Traversal[Block] = dominateIn.collectAll[Block]
  def _callViaDominateIn: overflowdb.traversal.Traversal[Call]   = dominateIn.collectAll[Call]
  def _controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    dominateIn.collectAll[ControlStructure]
  def _fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateIn.collectAll[FieldIdentifier]
  def _identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = dominateIn.collectAll[Identifier]
  def _literalViaDominateIn: overflowdb.traversal.Traversal[Literal]       = dominateIn.collectAll[Literal]
  def _methodViaDominateIn: overflowdb.traversal.Traversal[Method]         = dominateIn.collectAll[Method]
  def _methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef]   = dominateIn.collectAll[MethodRef]
  def _returnViaDominateIn: overflowdb.traversal.Traversal[Return]         = dominateIn.collectAll[Return]
  def _typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef]       = dominateIn.collectAll[TypeRef]
  def _unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown]       = dominateIn.collectAll[Unknown]

  def reachingDefIn: Iterator[Return] = createAdjacentNodeScalaIteratorByOffSet[Return](7)
  override def _reachingDefIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)
  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = reachingDefIn.collectAll[Return]

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
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[Integer]
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
      case "LINE_NUMBER"         => this._lineNumber = value.asInstanceOf[Integer]
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
    this._columnNumber = newNode.asInstanceOf[NewMethodReturn].columnNumber.orNull
    this._dynamicTypeHintFullName =
      if (newNode.asInstanceOf[NewMethodReturn].dynamicTypeHintFullName != null)
        newNode.asInstanceOf[NewMethodReturn].dynamicTypeHintFullName
      else collection.immutable.ArraySeq.empty
    this._evaluationStrategy = newNode.asInstanceOf[NewMethodReturn].evaluationStrategy
    this._lineNumber = newNode.asInstanceOf[NewMethodReturn].lineNumber.orNull
    this._order = newNode.asInstanceOf[NewMethodReturn].order
    this._possibleTypes =
      if (newNode.asInstanceOf[NewMethodReturn].possibleTypes != null)
        newNode.asInstanceOf[NewMethodReturn].possibleTypes
      else collection.immutable.ArraySeq.empty
    this._typeFullName = newNode.asInstanceOf[NewMethodReturn].typeFullName

  }

}
