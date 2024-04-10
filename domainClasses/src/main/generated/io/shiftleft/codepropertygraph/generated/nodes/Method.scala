package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Method {
  def apply(graph: Graph, id: Long) = new Method(graph, id)

  val Label = "METHOD"

  object PropertyNames {
    val AstParentFullName = "AST_PARENT_FULL_NAME"
    val AstParentType     = "AST_PARENT_TYPE"
    val Code              = "CODE"
    val ColumnNumber      = "COLUMN_NUMBER"
    val ColumnNumberEnd   = "COLUMN_NUMBER_END"
    val Filename          = "FILENAME"
    val FullName          = "FULL_NAME"
    val Hash              = "HASH"
    val IsExternal        = "IS_EXTERNAL"
    val LineNumber        = "LINE_NUMBER"
    val LineNumberEnd     = "LINE_NUMBER_END"
    val Name              = "NAME"
    val Offset            = "OFFSET"
    val OffsetEnd         = "OFFSET_END"
    val Order             = "ORDER"
    val Signature         = "SIGNATURE"
    val all: Set[String] = Set(
      AstParentFullName,
      AstParentType,
      Code,
      ColumnNumber,
      ColumnNumberEnd,
      Filename,
      FullName,
      Hash,
      IsExternal,
      LineNumber,
      LineNumberEnd,
      Name,
      Offset,
      OffsetEnd,
      Order,
      Signature
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val AstParentFullName = new overflowdb.PropertyKey[String]("AST_PARENT_FULL_NAME")
    val AstParentType     = new overflowdb.PropertyKey[String]("AST_PARENT_TYPE")
    val Code              = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber      = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val ColumnNumberEnd   = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER_END")
    val Filename          = new overflowdb.PropertyKey[String]("FILENAME")
    val FullName          = new overflowdb.PropertyKey[String]("FULL_NAME")
    val Hash              = new overflowdb.PropertyKey[String]("HASH")
    val IsExternal        = new overflowdb.PropertyKey[Boolean]("IS_EXTERNAL")
    val LineNumber        = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val LineNumberEnd     = new overflowdb.PropertyKey[Integer]("LINE_NUMBER_END")
    val Name              = new overflowdb.PropertyKey[String]("NAME")
    val Offset            = new overflowdb.PropertyKey[Integer]("OFFSET")
    val OffsetEnd         = new overflowdb.PropertyKey[Integer]("OFFSET_END")
    val Order             = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val Signature         = new overflowdb.PropertyKey[String]("SIGNATURE")

  }

  object PropertyDefaults {
    val AstParentFullName = "<empty>"
    val AstParentType     = "<empty>"
    val Code              = "<empty>"
    val Filename          = "<empty>"
    val FullName          = "<empty>"
    val IsExternal        = false
    val Name              = "<empty>"
    val Order             = -1: Int
    val Signature         = ""
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Call.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "CFG", "CONTAINS", "DOMINATE", "REACHING_DEF", "SOURCE_FILE", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CALL", "CFG", "CONTAINS", "POST_DOMINATE", "REF")
  }

  val factory = new NodeFactory[MethodDb] {
    override val forLabel = Method.Label

    override def createNode(ref: NodeRef[MethodDb]) =
      new MethodDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Method(graph, id)
  }
}

trait MethodBase extends AbstractNode with AstNodeBase with CfgNodeBase with DeclarationBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def astParentFullName: String
  def astParentType: String
  def code: String
  def columnNumber: Option[Integer]
  def columnNumberEnd: Option[Integer]
  def filename: String
  def fullName: String
  def hash: Option[String]
  def isExternal: Boolean
  def lineNumber: Option[Integer]
  def lineNumberEnd: Option[Integer]
  def name: String
  def offset: Option[Integer]
  def offsetEnd: Option[Integer]
  def order: scala.Int
  def signature: String

}

class Method(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodDb](graph_4762, id_4762)
    with MethodBase
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration {
  override def astParentFullName: String        = get().astParentFullName
  override def astParentType: String            = get().astParentType
  override def code: String                     = get().code
  override def columnNumber: Option[Integer]    = get().columnNumber
  override def columnNumberEnd: Option[Integer] = get().columnNumberEnd
  override def filename: String                 = get().filename
  override def fullName: String                 = get().fullName
  override def hash: Option[String]             = get().hash
  override def isExternal: Boolean              = get().isExternal
  override def lineNumber: Option[Integer]      = get().lineNumber
  override def lineNumberEnd: Option[Integer]   = get().lineNumberEnd
  override def name: String                     = get().name
  override def offset: Option[Integer]          = get().offset
  override def offsetEnd: Option[Integer]       = get().offsetEnd
  override def order: scala.Int                 = get().order
  override def signature: String                = get().signature
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "AST_PARENT_FULL_NAME" => Method.PropertyDefaults.AstParentFullName
      case "AST_PARENT_TYPE"      => Method.PropertyDefaults.AstParentType
      case "CODE"                 => Method.PropertyDefaults.Code
      case "FILENAME"             => Method.PropertyDefaults.Filename
      case "FULL_NAME"            => Method.PropertyDefaults.FullName
      case "IS_EXTERNAL"          => Method.PropertyDefaults.IsExternal
      case "NAME"                 => Method.PropertyDefaults.Name
      case "ORDER"                => Method.PropertyDefaults.Order
      case "SIGNATURE"            => Method.PropertyDefaults.Signature
      case _                      => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstOut

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  /** Root of the abstract syntax tree */
  @overflowdb.traversal.help.Doc(info = """Root of the abstract syntax tree""")
  def block: Block = get().block

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method] = get()._methodViaAstOut

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  /** Parameters of the method */
  @overflowdb.traversal.help.Doc(info = """Parameters of the method""")
  def parameter: overflowdb.traversal.Traversal[MethodParameterIn] = get().parameter

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def _methodParameterOutViaAstOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaAstOut

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  /** Formal return parameters */
  @overflowdb.traversal.help.Doc(info = """Formal return parameters""")
  def methodReturn: MethodReturn = get().methodReturn

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: overflowdb.traversal.Traversal[Modifier] = get()._modifierViaAstOut

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaAstOut

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = get()._typeParameterViaAstOut

  def cfgOut: Iterator[AstNode] = get().cfgOut
  override def _cfgOut          = get()._cfgOut

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  /** First control flow graph node */
  @overflowdb.traversal.help.Doc(info = """First control flow graph node""")
  def cfgFirst: overflowdb.traversal.Traversal[CfgNode] = get().cfgFirst

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def _methodReturnViaCfgOut: Option[MethodReturn] = get()._methodReturnViaCfgOut

  def containsOut: Iterator[CfgNode] = get().containsOut
  override def _containsOut          = get()._containsOut

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def _blockViaContainsOut: overflowdb.traversal.Traversal[Block] = get()._blockViaContainsOut

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def _callViaContainsOut: overflowdb.traversal.Traversal[Call] = get()._callViaContainsOut

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def _controlStructureViaContainsOut: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaContainsOut

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def _fieldIdentifierViaContainsOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaContainsOut

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def _identifierViaContainsOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaContainsOut

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def _jumpTargetViaContainsOut: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaContainsOut

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  /** Literals used in the method */
  @overflowdb.traversal.help.Doc(info = """Literals used in the method""")
  def literal: overflowdb.traversal.Traversal[Literal] = get().literal

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def _methodRefViaContainsOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaContainsOut

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def _returnViaContainsOut: overflowdb.traversal.Traversal[Return] = get()._returnViaContainsOut

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def _templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = get()._templateDomViaContainsOut

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def _typeRefViaContainsOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaContainsOut

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def _unknownViaContainsOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaContainsOut

  def dominateOut: Iterator[CfgNode] = get().dominateOut
  override def _dominateOut          = get()._dominateOut

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = get()._blockViaDominateOut

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def _callViaDominateOut: overflowdb.traversal.Traversal[Call] = get()._callViaDominateOut

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaDominateOut

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaDominateOut

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

  def reachingDefOut: Iterator[CfgNode] = get().reachingDefOut
  override def _reachingDefOut          = get()._reachingDefOut

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefOut

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def _methodParameterInViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaReachingDefOut

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaReachingDefOut

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefOut

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaReachingDefOut

  def sourceFileOut: Iterator[File] = get().sourceFileOut
  override def _sourceFileOut       = get()._sourceFileOut

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = get()._fileViaSourceFileOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Option[Method] = get()._methodViaAstIn

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def _namespaceBlockViaAstIn: Option[NamespaceBlock] = get()._namespaceBlockViaAstIn

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Option[TypeDecl] = get()._typeDeclViaAstIn

  def callIn: Iterator[Call] = get().callIn
  override def _callIn       = get()._callIn

  /** Traverse to CALL via CALL IN edge.
    */
  def _callViaCallIn: overflowdb.traversal.Traversal[Call] = get()._callViaCallIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def containsIn: Iterator[AstNode] = get().containsIn
  override def _containsIn          = get()._containsIn

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = get()._fileViaContainsIn

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def _typeDeclViaContainsIn: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaContainsIn

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

  def refIn: Iterator[StoredNode] = get().refIn
  override def _refIn             = get()._refIn

  /** Traverse to BINDING via REF IN edge.
    */
  def _bindingViaRefIn: overflowdb.traversal.Traversal[Binding] = get()._bindingViaRefIn

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def _methodRefViaRefIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaRefIn

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
    Method.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "astParentFullName"
      case 2  => "astParentType"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "columnNumberEnd"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "hash"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "lineNumberEnd"
      case 12 => "name"
      case 13 => "offset"
      case 14 => "offsetEnd"
      case 15 => "order"
      case 16 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => astParentFullName
      case 2  => astParentType
      case 3  => code
      case 4  => columnNumber
      case 5  => columnNumberEnd
      case 6  => filename
      case 7  => fullName
      case 8  => hash
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => lineNumberEnd
      case 12 => name
      case 13 => offset
      case 14 => offsetEnd
      case 15 => order
      case 16 => signature
    }

  override def productPrefix = "Method"
  override def productArity  = 17
}

class MethodDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration
    with MethodBase {

  override def layoutInformation: NodeLayoutInformation = Method.layoutInformation

  private var _astParentFullName: String = Method.PropertyDefaults.AstParentFullName
  def astParentFullName: String          = _astParentFullName
  private var _astParentType: String     = Method.PropertyDefaults.AstParentType
  def astParentType: String              = _astParentType
  private var _code: String              = Method.PropertyDefaults.Code
  def code: String                       = _code
  private var _columnNumber: Integer     = null
  def columnNumber: Option[Integer]      = Option(_columnNumber)
  private var _columnNumberEnd: Integer  = null
  def columnNumberEnd: Option[Integer]   = Option(_columnNumberEnd)
  private var _filename: String          = Method.PropertyDefaults.Filename
  def filename: String                   = _filename
  private var _fullName: String          = Method.PropertyDefaults.FullName
  def fullName: String                   = _fullName
  private var _hash: String              = null
  def hash: Option[String]               = Option(_hash)
  private var _isExternal: Boolean       = Method.PropertyDefaults.IsExternal
  def isExternal: Boolean                = _isExternal
  private var _lineNumber: Integer       = null
  def lineNumber: Option[Integer]        = Option(_lineNumber)
  private var _lineNumberEnd: Integer    = null
  def lineNumberEnd: Option[Integer]     = Option(_lineNumberEnd)
  private var _name: String              = Method.PropertyDefaults.Name
  def name: String                       = _name
  private var _offset: Integer           = null
  def offset: Option[Integer]            = Option(_offset)
  private var _offsetEnd: Integer        = null
  def offsetEnd: Option[Integer]         = Option(_offsetEnd)
  private var _order: scala.Int          = Method.PropertyDefaults.Order
  def order: scala.Int                   = _order
  private var _signature: String         = Method.PropertyDefaults.Signature
  def signature: String                  = _signature

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("AST_PARENT_FULL_NAME", astParentFullName)
    properties.put("AST_PARENT_TYPE", astParentType)
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    columnNumberEnd.map { value => properties.put("COLUMN_NUMBER_END", value) }
    properties.put("FILENAME", filename)
    properties.put("FULL_NAME", fullName)
    hash.map { value => properties.put("HASH", value) }
    properties.put("IS_EXTERNAL", isExternal)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    lineNumberEnd.map { value => properties.put("LINE_NUMBER_END", value) }
    properties.put("NAME", name)
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    properties.put("ORDER", order)
    properties.put("SIGNATURE", signature)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == astParentFullName)) { properties.put("AST_PARENT_FULL_NAME", astParentFullName) }
    if (!(("<empty>") == astParentType)) { properties.put("AST_PARENT_TYPE", astParentType) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    columnNumberEnd.map { value => properties.put("COLUMN_NUMBER_END", value) }
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    hash.map { value => properties.put("HASH", value) }
    if (!((false) == isExternal)) { properties.put("IS_EXTERNAL", isExternal) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    lineNumberEnd.map { value => properties.put("LINE_NUMBER_END", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (!(("") == signature)) { properties.put("SIGNATURE", signature) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = astOut.collectAll[Annotation]
  def block: Block = try { astOut.collectAll[Block].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label AST to an adjacent BLOCK is mandatory, but not defined for this METHOD node with id=" + id,
        e
      )
  }
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method]     = astOut.collectAll[Method]
  def parameter: overflowdb.traversal.Traversal[MethodParameterIn] = astOut.collectAll[MethodParameterIn]
  def _methodParameterOutViaAstOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    astOut.collectAll[MethodParameterOut]
  def methodReturn: MethodReturn = try { astOut.collectAll[MethodReturn].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label AST to an adjacent METHOD_RETURN is mandatory, but not defined for this METHOD node with id=" + id,
        e
      )
  }
  def _modifierViaAstOut: overflowdb.traversal.Traversal[Modifier]           = astOut.collectAll[Modifier]
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl]           = astOut.collectAll[TypeDecl]
  def _typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = astOut.collectAll[TypeParameter]

  def cfgOut: Iterator[AstNode]                         = createAdjacentNodeScalaIteratorByOffSet[AstNode](1)
  override def _cfgOut                                  = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def cfgFirst: overflowdb.traversal.Traversal[CfgNode] = cfgOut.collectAll[CfgNode]
  def _methodReturnViaCfgOut: Option[MethodReturn]      = cfgOut.collectAll[MethodReturn].nextOption()

  def containsOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](2)
  override def _containsOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _blockViaContainsOut: overflowdb.traversal.Traversal[Block] = containsOut.collectAll[Block]
  def _callViaContainsOut: overflowdb.traversal.Traversal[Call]   = containsOut.collectAll[Call]
  def _controlStructureViaContainsOut: overflowdb.traversal.Traversal[ControlStructure] =
    containsOut.collectAll[ControlStructure]
  def _fieldIdentifierViaContainsOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    containsOut.collectAll[FieldIdentifier]
  def _identifierViaContainsOut: overflowdb.traversal.Traversal[Identifier]   = containsOut.collectAll[Identifier]
  def _jumpTargetViaContainsOut: overflowdb.traversal.Traversal[JumpTarget]   = containsOut.collectAll[JumpTarget]
  def literal: overflowdb.traversal.Traversal[Literal]                        = containsOut.collectAll[Literal]
  def _methodRefViaContainsOut: overflowdb.traversal.Traversal[MethodRef]     = containsOut.collectAll[MethodRef]
  def _returnViaContainsOut: overflowdb.traversal.Traversal[Return]           = containsOut.collectAll[Return]
  def _templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = containsOut.collectAll[TemplateDom]
  def _typeRefViaContainsOut: overflowdb.traversal.Traversal[TypeRef]         = containsOut.collectAll[TypeRef]
  def _unknownViaContainsOut: overflowdb.traversal.Traversal[Unknown]         = containsOut.collectAll[Unknown]

  def dominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _dominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _blockViaDominateOut: overflowdb.traversal.Traversal[Block] = dominateOut.collectAll[Block]
  def _callViaDominateOut: overflowdb.traversal.Traversal[Call]   = dominateOut.collectAll[Call]
  def _fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateOut.collectAll[FieldIdentifier]
  def _identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier]     = dominateOut.collectAll[Identifier]
  def _literalViaDominateOut: overflowdb.traversal.Traversal[Literal]           = dominateOut.collectAll[Literal]
  def _methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef]       = dominateOut.collectAll[MethodRef]
  def _methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = dominateOut.collectAll[MethodReturn]
  def _returnViaDominateOut: overflowdb.traversal.Traversal[Return]             = dominateOut.collectAll[Return]
  def _typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef]           = dominateOut.collectAll[TypeRef]
  def _unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown]           = dominateOut.collectAll[Unknown]

  def reachingDefOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _reachingDefOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call]             = reachingDefOut.collectAll[Call]
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal]       = reachingDefOut.collectAll[Literal]
  def _methodParameterInViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterIn] =
    reachingDefOut.collectAll[MethodParameterIn]
  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefOut.collectAll[MethodParameterOut]
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = reachingDefOut.collectAll[MethodRef]
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return]       = reachingDefOut.collectAll[Return]
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef]     = reachingDefOut.collectAll[TypeRef]

  def sourceFileOut: Iterator[File] = createAdjacentNodeScalaIteratorByOffSet[File](5)
  override def _sourceFileOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = sourceFileOut.collectAll[File]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](6)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[AstNode]                        = createAdjacentNodeScalaIteratorByOffSet[AstNode](7)
  override def _astIn                                 = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)
  def _methodViaAstIn: Option[Method]                 = astIn.collectAll[Method].nextOption()
  def _namespaceBlockViaAstIn: Option[NamespaceBlock] = astIn.collectAll[NamespaceBlock].nextOption()
  def _typeDeclViaAstIn: Option[TypeDecl]             = astIn.collectAll[TypeDecl].nextOption()

  def callIn: Iterator[Call]                               = createAdjacentNodeScalaIteratorByOffSet[Call](8)
  override def _callIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)
  def _callViaCallIn: overflowdb.traversal.Traversal[Call] = callIn.collectAll[Call]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](9)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)

  def containsIn: Iterator[AstNode]                            = createAdjacentNodeScalaIteratorByOffSet[AstNode](10)
  override def _containsIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](10)
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = containsIn.collectAll[File]
  def _typeDeclViaContainsIn: overflowdb.traversal.Traversal[TypeDecl] = containsIn.collectAll[TypeDecl]

  def postDominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](11)
  override def _postDominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](11)
  def _blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = postDominateIn.collectAll[Block]
  def _callViaPostDominateIn: overflowdb.traversal.Traversal[Call]   = postDominateIn.collectAll[Call]
  def _controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateIn.collectAll[ControlStructure]
  def _fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateIn.collectAll[FieldIdentifier]
  def _identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = postDominateIn.collectAll[Identifier]
  def _literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal]       = postDominateIn.collectAll[Literal]
  def _methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef]   = postDominateIn.collectAll[MethodRef]
  def _methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    postDominateIn.collectAll[MethodReturn]
  def _returnViaPostDominateIn: overflowdb.traversal.Traversal[Return]   = postDominateIn.collectAll[Return]
  def _typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = postDominateIn.collectAll[TypeRef]
  def _unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = postDominateIn.collectAll[Unknown]

  def refIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)
  override def _refIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)
  def _bindingViaRefIn: overflowdb.traversal.Traversal[Binding]     = refIn.collectAll[Binding]
  def _methodRefViaRefIn: overflowdb.traversal.Traversal[MethodRef] = refIn.collectAll[MethodRef]

  override def label: String = {
    Method.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "astParentFullName"
      case 2  => "astParentType"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "columnNumberEnd"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "hash"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "lineNumberEnd"
      case 12 => "name"
      case 13 => "offset"
      case 14 => "offsetEnd"
      case 15 => "order"
      case 16 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => astParentFullName
      case 2  => astParentType
      case 3  => code
      case 4  => columnNumber
      case 5  => columnNumberEnd
      case 6  => filename
      case 7  => fullName
      case 8  => hash
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => lineNumberEnd
      case 12 => name
      case 13 => offset
      case 14 => offsetEnd
      case 15 => order
      case 16 => signature
    }

  override def productPrefix = "Method"
  override def productArity  = 17

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodDb]

  override def property(key: String): Any = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName
      case "AST_PARENT_TYPE"      => this._astParentType
      case "CODE"                 => this._code
      case "COLUMN_NUMBER"        => this._columnNumber
      case "COLUMN_NUMBER_END"    => this._columnNumberEnd
      case "FILENAME"             => this._filename
      case "FULL_NAME"            => this._fullName
      case "HASH"                 => this._hash
      case "IS_EXTERNAL"          => this._isExternal
      case "LINE_NUMBER"          => this._lineNumber
      case "LINE_NUMBER_END"      => this._lineNumberEnd
      case "NAME"                 => this._name
      case "OFFSET"               => this._offset
      case "OFFSET_END"           => this._offsetEnd
      case "ORDER"                => this._order
      case "SIGNATURE"            => this._signature

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName = value.asInstanceOf[String]
      case "AST_PARENT_TYPE"      => this._astParentType = value.asInstanceOf[String]
      case "CODE"                 => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"        => this._columnNumber = value.asInstanceOf[Integer]
      case "COLUMN_NUMBER_END"    => this._columnNumberEnd = value.asInstanceOf[Integer]
      case "FILENAME"             => this._filename = value.asInstanceOf[String]
      case "FULL_NAME"            => this._fullName = value.asInstanceOf[String]
      case "HASH"                 => this._hash = value.asInstanceOf[String]
      case "IS_EXTERNAL"          => this._isExternal = value.asInstanceOf[Boolean]
      case "LINE_NUMBER"          => this._lineNumber = value.asInstanceOf[Integer]
      case "LINE_NUMBER_END"      => this._lineNumberEnd = value.asInstanceOf[Integer]
      case "NAME"                 => this._name = value.asInstanceOf[String]
      case "OFFSET"               => this._offset = value.asInstanceOf[Integer]
      case "OFFSET_END"           => this._offsetEnd = value.asInstanceOf[Integer]
      case "ORDER"                => this._order = value.asInstanceOf[scala.Int]
      case "SIGNATURE"            => this._signature = value.asInstanceOf[String]

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
    this._astParentFullName = newNode.asInstanceOf[NewMethod].astParentFullName
    this._astParentType = newNode.asInstanceOf[NewMethod].astParentType
    this._code = newNode.asInstanceOf[NewMethod].code
    this._columnNumber = newNode.asInstanceOf[NewMethod].columnNumber.orNull
    this._columnNumberEnd = newNode.asInstanceOf[NewMethod].columnNumberEnd.orNull
    this._filename = newNode.asInstanceOf[NewMethod].filename
    this._fullName = newNode.asInstanceOf[NewMethod].fullName
    this._hash = newNode.asInstanceOf[NewMethod].hash.orNull
    this._isExternal = newNode.asInstanceOf[NewMethod].isExternal
    this._lineNumber = newNode.asInstanceOf[NewMethod].lineNumber.orNull
    this._lineNumberEnd = newNode.asInstanceOf[NewMethod].lineNumberEnd.orNull
    this._name = newNode.asInstanceOf[NewMethod].name
    this._offset = newNode.asInstanceOf[NewMethod].offset.orNull
    this._offsetEnd = newNode.asInstanceOf[NewMethod].offsetEnd.orNull
    this._order = newNode.asInstanceOf[NewMethod].order
    this._signature = newNode.asInstanceOf[NewMethod].signature

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewMethod].fullName, this.ref)
  }

}
