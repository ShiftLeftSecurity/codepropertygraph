package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object FieldIdentifier {
  def apply(graph: Graph, id: Long) = new FieldIdentifier(graph, id)

  val Label = "FIELD_IDENTIFIER"

  object PropertyNames {
    val ArgumentIndex    = "ARGUMENT_INDEX"
    val ArgumentName     = "ARGUMENT_NAME"
    val CanonicalName    = "CANONICAL_NAME"
    val Code             = "CODE"
    val ColumnNumber     = "COLUMN_NUMBER"
    val LineNumber       = "LINE_NUMBER"
    val Order            = "ORDER"
    val all: Set[String] = Set(ArgumentIndex, ArgumentName, CanonicalName, Code, ColumnNumber, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName  = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val CanonicalName = new overflowdb.PropertyKey[String]("CANONICAL_NAME")
    val Code          = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber  = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber    = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order         = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val ArgumentIndex = -1: Int
    val CanonicalName = "<empty>"
    val Code          = "<empty>"
    val Order         = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ARGUMENT", "CDG", "CFG", "DOMINATE", "POST_DOMINATE", "TAGGED_BY")
    val In: Array[String] =
      Array("ARGUMENT", "AST", "CDG", "CFG", "CONTAINS", "DOMINATE", "POST_DOMINATE", "REACHING_DEF")
  }

  val factory = new NodeFactory[FieldIdentifierDb] {
    override val forLabel = FieldIdentifier.Label

    override def createNode(ref: NodeRef[FieldIdentifierDb]) =
      new FieldIdentifierDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = FieldIdentifier(graph, id)
  }
}

trait FieldIdentifierBase extends AbstractNode with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def canonicalName: String
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int

}

class FieldIdentifier(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[FieldIdentifierDb](graph_4762, id_4762)
    with FieldIdentifierBase
    with StoredNode
    with Expression {
  override def argumentIndex: scala.Int      = get().argumentIndex
  override def argumentName: Option[String]  = get().argumentName
  override def canonicalName: String         = get().canonicalName
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX" => FieldIdentifier.PropertyDefaults.ArgumentIndex
      case "CANONICAL_NAME" => FieldIdentifier.PropertyDefaults.CanonicalName
      case "CODE"           => FieldIdentifier.PropertyDefaults.Code
      case "ORDER"          => FieldIdentifier.PropertyDefaults.Order
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[TemplateDom] = get().argumentOut
  override def _argumentOut              = get()._argumentOut

  def cdgOut: Iterator[CfgNode] = get().cdgOut
  override def _cdgOut          = get()._cdgOut

  /** Traverse to BLOCK via CDG OUT edge.
    */
  def _blockViaCdgOut: overflowdb.traversal.Traversal[Block] = get()._blockViaCdgOut

  /** Traverse to CALL via CDG OUT edge.
    */
  def _callViaCdgOut: overflowdb.traversal.Traversal[Call] = get()._callViaCdgOut

  /** Traverse to CONTROL_STRUCTURE via CDG OUT edge.
    */
  def _controlStructureViaCdgOut: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaCdgOut

  /** Traverse to FIELD_IDENTIFIER via CDG OUT edge.
    */
  def _fieldIdentifierViaCdgOut: overflowdb.traversal.Traversal[FieldIdentifier] = get()._fieldIdentifierViaCdgOut

  /** Traverse to IDENTIFIER via CDG OUT edge.
    */
  def _identifierViaCdgOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaCdgOut

  /** Traverse to JUMP_TARGET via CDG OUT edge.
    */
  def _jumpTargetViaCdgOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaCdgOut

  /** Traverse to LITERAL via CDG OUT edge.
    */
  def _literalViaCdgOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaCdgOut

  /** Traverse to METHOD_REF via CDG OUT edge.
    */
  def _methodRefViaCdgOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaCdgOut

  /** Traverse to METHOD_RETURN via CDG OUT edge.
    */
  def _methodReturnViaCdgOut: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaCdgOut

  /** Traverse to RETURN via CDG OUT edge.
    */
  def _returnViaCdgOut: overflowdb.traversal.Traversal[Return] = get()._returnViaCdgOut

  /** Traverse to TYPE_REF via CDG OUT edge.
    */
  def _typeRefViaCdgOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaCdgOut

  /** Traverse to UNKNOWN via CDG OUT edge.
    */
  def _unknownViaCdgOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaCdgOut

  def cfgOut: Iterator[AstNode] = get().cfgOut
  override def _cfgOut          = get()._cfgOut

  /** Traverse to CALL via CFG OUT edge.
    */
  def _callViaCfgOut: overflowdb.traversal.Traversal[Call] = get()._callViaCfgOut

  /** Traverse to CFG_NODE via CFG OUT edge.
    */
  def _cfgNodeViaCfgOut: overflowdb.traversal.Traversal[CfgNode] = get()._cfgNodeViaCfgOut

  def dominateOut: Iterator[CfgNode] = get().dominateOut
  override def _dominateOut          = get()._dominateOut

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = get()._blockViaDominateOut

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: overflowdb.traversal.Traversal[Call] = get()._callViaDominateOut

  /** Traverse to CONTROL_STRUCTURE via DOMINATE OUT edge.
    */
  def _controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaDominateOut

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaDominateOut

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaDominateOut

  /** Traverse to JUMP_TARGET via DOMINATE OUT edge.
    */
  def _jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaDominateOut

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def _literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaDominateOut

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def _methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaDominateOut

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def _methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaDominateOut

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def _returnViaDominateOut: overflowdb.traversal.Traversal[Return] = get()._returnViaDominateOut

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def _typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaDominateOut

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def _unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaDominateOut

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

  def argumentIn: Iterator[Call] = get().argumentIn
  override def _argumentIn       = get()._argumentIn

  /** Traverse to CALL via ARGUMENT IN edge.
    */
  def _callViaArgumentIn: Call = get()._callViaArgumentIn

  def astIn: Iterator[Expression] = get().astIn
  override def _astIn             = get()._astIn

  /** Traverse to CALL via AST IN edge.
    */
  def _callViaAstIn: Call = get()._callViaAstIn

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaAstIn

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

  def containsIn: Iterator[Method] = get().containsIn
  override def _containsIn         = get()._containsIn

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = get()._methodViaContainsIn

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

  /** Traverse to JUMP_TARGET via DOMINATE IN edge.
    */
  def _jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaDominateIn

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

  def postDominateIn: Iterator[CfgNode] = get().postDominateIn
  override def _postDominateIn          = get()._postDominateIn

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def _blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = get()._blockViaPostDominateIn

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def _callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = get()._callViaPostDominateIn

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def _controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaPostDominateIn

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaPostDominateIn

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def _identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaPostDominateIn

  /** Traverse to JUMP_TARGET via POST_DOMINATE IN edge.
    */
  def _jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaPostDominateIn

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def _literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaPostDominateIn

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def _methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaPostDominateIn

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def _methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    get()._methodReturnViaPostDominateIn

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def _returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = get()._returnViaPostDominateIn

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def _typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaPostDominateIn

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def _unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaPostDominateIn

  def reachingDefIn: Iterator[TemplateDom] = get().reachingDefIn
  override def _reachingDefIn              = get()._reachingDefIn

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
    FieldIdentifier.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "canonicalName"
      case 4 => "code"
      case 5 => "columnNumber"
      case 6 => "lineNumber"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => canonicalName
      case 4 => code
      case 5 => columnNumber
      case 6 => lineNumber
      case 7 => order
    }

  override def productPrefix = "FieldIdentifier"
  override def productArity  = 8
}

class FieldIdentifierDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with Expression
    with FieldIdentifierBase {

  override def layoutInformation: NodeLayoutInformation = FieldIdentifier.layoutInformation

  private var _argumentIndex: scala.Int = FieldIdentifier.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int          = _argumentIndex
  private var _argumentName: String     = null
  def argumentName: Option[String]      = Option(_argumentName)
  private var _canonicalName: String    = FieldIdentifier.PropertyDefaults.CanonicalName
  def canonicalName: String             = _canonicalName
  private var _code: String             = FieldIdentifier.PropertyDefaults.Code
  def code: String                      = _code
  private var _columnNumber: Integer    = null
  def columnNumber: Option[Integer]     = Option(_columnNumber)
  private var _lineNumber: Integer      = null
  def lineNumber: Option[Integer]       = Option(_lineNumber)
  private var _order: scala.Int         = FieldIdentifier.PropertyDefaults.Order
  def order: scala.Int                  = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    properties.put("CANONICAL_NAME", canonicalName)
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
    if (!(("<empty>") == canonicalName)) { properties.put("CANONICAL_NAME", canonicalName) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](0)
  override def _argumentOut              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  def cdgOut: Iterator[CfgNode]                              = createAdjacentNodeScalaIteratorByOffSet[CfgNode](1)
  override def _cdgOut                                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _blockViaCdgOut: overflowdb.traversal.Traversal[Block] = cdgOut.collectAll[Block]
  def _callViaCdgOut: overflowdb.traversal.Traversal[Call]   = cdgOut.collectAll[Call]
  def _controlStructureViaCdgOut: overflowdb.traversal.Traversal[ControlStructure] = cdgOut.collectAll[ControlStructure]
  def _fieldIdentifierViaCdgOut: overflowdb.traversal.Traversal[FieldIdentifier]   = cdgOut.collectAll[FieldIdentifier]
  def _identifierViaCdgOut: overflowdb.traversal.Traversal[Identifier]             = cdgOut.collectAll[Identifier]
  def _jumpTargetViaCdgOut: overflowdb.traversal.Traversal[JumpTarget]             = cdgOut.collectAll[JumpTarget]
  def _literalViaCdgOut: overflowdb.traversal.Traversal[Literal]                   = cdgOut.collectAll[Literal]
  def _methodRefViaCdgOut: overflowdb.traversal.Traversal[MethodRef]               = cdgOut.collectAll[MethodRef]
  def _methodReturnViaCdgOut: overflowdb.traversal.Traversal[MethodReturn]         = cdgOut.collectAll[MethodReturn]
  def _returnViaCdgOut: overflowdb.traversal.Traversal[Return]                     = cdgOut.collectAll[Return]
  def _typeRefViaCdgOut: overflowdb.traversal.Traversal[TypeRef]                   = cdgOut.collectAll[TypeRef]
  def _unknownViaCdgOut: overflowdb.traversal.Traversal[Unknown]                   = cdgOut.collectAll[Unknown]

  def cfgOut: Iterator[AstNode]                            = createAdjacentNodeScalaIteratorByOffSet[AstNode](2)
  override def _cfgOut                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _callViaCfgOut: overflowdb.traversal.Traversal[Call] = cfgOut.collectAll[Call]
  def _cfgNodeViaCfgOut: overflowdb.traversal.Traversal[CfgNode] = cfgOut.collectAll[CfgNode]

  def dominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _dominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = dominateOut.collectAll[Block]
  def _callViaDominateOut: overflowdb.traversal.Traversal[Call]   = dominateOut.collectAll[Call]
  def _controlStructureViaDominateOut: overflowdb.traversal.Traversal[ControlStructure] =
    dominateOut.collectAll[ControlStructure]
  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateOut.collectAll[FieldIdentifier]
  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier]     = dominateOut.collectAll[Identifier]
  def _jumpTargetViaDominateOut: overflowdb.traversal.Traversal[JumpTarget]     = dominateOut.collectAll[JumpTarget]
  def _literalViaDominateOut: overflowdb.traversal.Traversal[Literal]           = dominateOut.collectAll[Literal]
  def _methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef]       = dominateOut.collectAll[MethodRef]
  def _methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = dominateOut.collectAll[MethodReturn]
  def _returnViaDominateOut: overflowdb.traversal.Traversal[Return]             = dominateOut.collectAll[Return]
  def _typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef]           = dominateOut.collectAll[TypeRef]
  def _unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown]           = dominateOut.collectAll[Unknown]

  def postDominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _postDominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
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

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](5)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def argumentIn: Iterator[Call] = createAdjacentNodeScalaIteratorByOffSet[Call](6)
  override def _argumentIn       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def _callViaArgumentIn: Call = try { argumentIn.collectAll[Call].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label ARGUMENT to an adjacent CALL is mandatory, but not defined for this FIELD_IDENTIFIER node with id=" + id,
        e
      )
  }

  def astIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](7)
  override def _astIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)
  def _callViaAstIn: Call = try { astIn.collectAll[Call].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent CALL is mandatory, but not defined for this FIELD_IDENTIFIER node with id=" + id,
        e
      )
  }
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = astIn.collectAll[Unknown]

  def cdgIn: Iterator[CfgNode]                              = createAdjacentNodeScalaIteratorByOffSet[CfgNode](8)
  override def _cdgIn                                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)
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

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](9)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)

  def containsIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](10)
  override def _containsIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](10)
  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = containsIn.collectAll[Method]

  def dominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](11)
  override def _dominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](11)
  def _blockViaDominateIn: overflowdb.traversal.Traversal[Block] = dominateIn.collectAll[Block]
  def _callViaDominateIn: overflowdb.traversal.Traversal[Call]   = dominateIn.collectAll[Call]
  def _controlStructureViaDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    dominateIn.collectAll[ControlStructure]
  def _fieldIdentifierViaDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateIn.collectAll[FieldIdentifier]
  def _identifierViaDominateIn: overflowdb.traversal.Traversal[Identifier] = dominateIn.collectAll[Identifier]
  def _jumpTargetViaDominateIn: overflowdb.traversal.Traversal[JumpTarget] = dominateIn.collectAll[JumpTarget]
  def _literalViaDominateIn: overflowdb.traversal.Traversal[Literal]       = dominateIn.collectAll[Literal]
  def _methodViaDominateIn: overflowdb.traversal.Traversal[Method]         = dominateIn.collectAll[Method]
  def _methodRefViaDominateIn: overflowdb.traversal.Traversal[MethodRef]   = dominateIn.collectAll[MethodRef]
  def _returnViaDominateIn: overflowdb.traversal.Traversal[Return]         = dominateIn.collectAll[Return]
  def _typeRefViaDominateIn: overflowdb.traversal.Traversal[TypeRef]       = dominateIn.collectAll[TypeRef]
  def _unknownViaDominateIn: overflowdb.traversal.Traversal[Unknown]       = dominateIn.collectAll[Unknown]

  def postDominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](12)
  override def _postDominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)
  def _blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = postDominateIn.collectAll[Block]
  def _callViaPostDominateIn: overflowdb.traversal.Traversal[Call]   = postDominateIn.collectAll[Call]
  def _controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateIn.collectAll[ControlStructure]
  def _fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateIn.collectAll[FieldIdentifier]
  def _identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = postDominateIn.collectAll[Identifier]
  def _jumpTargetViaPostDominateIn: overflowdb.traversal.Traversal[JumpTarget] = postDominateIn.collectAll[JumpTarget]
  def _literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal]       = postDominateIn.collectAll[Literal]
  def _methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef]   = postDominateIn.collectAll[MethodRef]
  def _methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    postDominateIn.collectAll[MethodReturn]
  def _returnViaPostDominateIn: overflowdb.traversal.Traversal[Return]   = postDominateIn.collectAll[Return]
  def _typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = postDominateIn.collectAll[TypeRef]
  def _unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = postDominateIn.collectAll[Unknown]

  def reachingDefIn: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](13)
  override def _reachingDefIn              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](13)

  override def label: String = {
    FieldIdentifier.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "canonicalName"
      case 4 => "code"
      case 5 => "columnNumber"
      case 6 => "lineNumber"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => canonicalName
      case 4 => code
      case 5 => columnNumber
      case 6 => lineNumber
      case 7 => order
    }

  override def productPrefix = "FieldIdentifier"
  override def productArity  = 8

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FieldIdentifierDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "ARGUMENT_NAME"  => this._argumentName
      case "CANONICAL_NAME" => this._canonicalName
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
      case "CANONICAL_NAME" => this._canonicalName = value.asInstanceOf[String]
      case "CODE"           => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"  => this._columnNumber = value.asInstanceOf[Integer]
      case "LINE_NUMBER"    => this._lineNumber = value.asInstanceOf[Integer]
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
    this._argumentIndex = newNode.asInstanceOf[NewFieldIdentifier].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewFieldIdentifier].argumentName.orNull
    this._canonicalName = newNode.asInstanceOf[NewFieldIdentifier].canonicalName
    this._code = newNode.asInstanceOf[NewFieldIdentifier].code
    this._columnNumber = newNode.asInstanceOf[NewFieldIdentifier].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewFieldIdentifier].lineNumber.orNull
    this._order = newNode.asInstanceOf[NewFieldIdentifier].order

  }

}
