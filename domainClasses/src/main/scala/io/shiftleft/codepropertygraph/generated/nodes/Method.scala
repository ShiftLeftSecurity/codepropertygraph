package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Method {
  def apply(graph: Graph, id: Long) = new Method(graph, id)

  val Label = "METHOD"

  object PropertyNames {
    val AstParentFullName = "AST_PARENT_FULL_NAME" 
    val AstParentType = "AST_PARENT_TYPE" 
    val BinarySignature = "BINARY_SIGNATURE" 
    val Code = "CODE" 
    val ColumnNumber = "COLUMN_NUMBER" 
    val ColumnNumberEnd = "COLUMN_NUMBER_END" 
    val DepthFirstOrder = "DEPTH_FIRST_ORDER" 
    val Filename = "FILENAME" 
    val FullName = "FULL_NAME" 
    val HasMapping = "HAS_MAPPING" 
    val InternalFlags = "INTERNAL_FLAGS" 
    val IsExternal = "IS_EXTERNAL" 
    val LineNumber = "LINE_NUMBER" 
    val LineNumberEnd = "LINE_NUMBER_END" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val Signature = "SIGNATURE" 
    val all: Set[String] = Set(AstParentFullName, AstParentType, BinarySignature, Code, ColumnNumber, ColumnNumberEnd, DepthFirstOrder, Filename, FullName, HasMapping, InternalFlags, IsExternal, LineNumber, LineNumberEnd, Name, Order, Signature)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val AstParentFullName = new PropertyKey[String]("AST_PARENT_FULL_NAME") 
    val AstParentType = new PropertyKey[String]("AST_PARENT_TYPE") 
    val BinarySignature = new PropertyKey[String]("BINARY_SIGNATURE") 
    val Code = new PropertyKey[String]("CODE") 
    val ColumnNumber = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER") 
    val ColumnNumberEnd = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER_END") 
    val DepthFirstOrder = new PropertyKey[java.lang.Integer]("DEPTH_FIRST_ORDER") 
    val Filename = new PropertyKey[String]("FILENAME") 
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val HasMapping = new PropertyKey[java.lang.Boolean]("HAS_MAPPING") 
    val InternalFlags = new PropertyKey[java.lang.Integer]("INTERNAL_FLAGS") 
    val IsExternal = new PropertyKey[java.lang.Boolean]("IS_EXTERNAL") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    val LineNumberEnd = new PropertyKey[java.lang.Integer]("LINE_NUMBER_END") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val Signature = new PropertyKey[String]("SIGNATURE") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Dominate.layoutInformation, edges.Ast.layoutInformation, edges.Contains.layoutInformation, edges.Cfg.layoutInformation, edges.SourceFile.layoutInformation, edges.ReachingDef.layoutInformation, edges.TaggedBy.layoutInformation).asJava,
    List(edges.Ref.layoutInformation, edges.Ast.layoutInformation, edges.Contains.layoutInformation, edges.Vtable.layoutInformation, edges.PostDominate.layoutInformation, edges.Call.layoutInformation, edges.DynamicType.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST","CFG","CONTAINS","DOMINATE","REACHING_DEF","SOURCE_FILE","TAGGED_BY")
    val In: Array[String] = Array("AST","CALL","CONTAINS","DYNAMIC_TYPE","POST_DOMINATE","REF","VTABLE")
  }

  val factory = new NodeFactory[MethodDb] {
    override val forLabel = Method.Label

    override def createNode(ref: NodeRef[MethodDb]) =
      new MethodDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Method(graph, id)
  }
}

trait MethodBase extends CpgNode with DeclarationBase with CfgNodeBase with AstNodeBase with HasAstParentFullName with HasAstParentType with HasBinarySignature with HasCode with HasColumnNumber with HasColumnNumberEnd with HasDepthFirstOrder with HasFilename with HasFullName with HasHasMapping with HasInternalFlags with HasIsExternal with HasLineNumber with HasLineNumberEnd with HasName with HasOrder with HasSignature {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Method(graph: Graph, id: Long) extends NodeRef[MethodDb](graph, id)
  with MethodBase
  with StoredNode
  with Declaration with CfgNode with AstNode {
    override def astParentFullName: String = get().astParentFullName
  override def astParentType: String = get().astParentType
  override def binarySignature: Option[String] = get().binarySignature
  override def code: String = get().code
  override def columnNumber: Option[java.lang.Integer] = get().columnNumber
  override def columnNumberEnd: Option[java.lang.Integer] = get().columnNumberEnd
  override def depthFirstOrder: Option[java.lang.Integer] = get().depthFirstOrder
  override def filename: String = get().filename
  override def fullName: String = get().fullName
  override def hasMapping: Option[java.lang.Boolean] = get().hasMapping
  override def internalFlags: Option[java.lang.Integer] = get().internalFlags
  override def isExternal: java.lang.Boolean = get().isExternal
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  override def lineNumberEnd: Option[java.lang.Integer] = get().lineNumberEnd
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  override def signature: String = get().signature
  
  def _identifierViaDominateOut: Iterator[Identifier] = get()._identifierViaDominateOut
def _fieldIdentifierViaDominateOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaDominateOut
def _blockViaDominateOut: Iterator[Block] = get()._blockViaDominateOut
def _callViaDominateOut: Iterator[Call] = get()._callViaDominateOut
def _methodRefViaDominateOut: Iterator[MethodRef] = get()._methodRefViaDominateOut
def _literalViaDominateOut: Iterator[Literal] = get()._literalViaDominateOut
def _unknownViaDominateOut: Iterator[Unknown] = get()._unknownViaDominateOut
def _methodReturnViaDominateOut: Iterator[MethodReturn] = get()._methodReturnViaDominateOut
def _returnViaDominateOut: Iterator[Return] = get()._returnViaDominateOut
def _typeRefViaDominateOut: Iterator[TypeRef] = get()._typeRefViaDominateOut
override def _dominateOut: JIterator[StoredNode] = get()._dominateOut
def _postExecutionCallViaAstOut: Iterator[PostExecutionCall] = get()._postExecutionCallViaAstOut
def _typeParameterViaAstOut: Iterator[TypeParameter] = get()._typeParameterViaAstOut
def _methodParameterOutViaAstOut: Iterator[MethodParameterOut] = get()._methodParameterOutViaAstOut
def _annotationViaAstOut: Iterator[Annotation] = get()._annotationViaAstOut
def _modifierViaAstOut: Iterator[Modifier] = get()._modifierViaAstOut
def _methodReturnViaAstOut: MethodReturn = get()._methodReturnViaAstOut
def _methodViaAstOut: Iterator[Method] = get()._methodViaAstOut
def _blockViaAstOut: Block = get()._blockViaAstOut
def _methodParameterInViaAstOut: Iterator[MethodParameterIn] = get()._methodParameterInViaAstOut
def _typeDeclViaAstOut: Iterator[TypeDecl] = get()._typeDeclViaAstOut
def _implicitCallViaAstOut: Iterator[ImplicitCall] = get()._implicitCallViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _methodRefViaContainsOut: Iterator[MethodRef] = get()._methodRefViaContainsOut
def _blockViaContainsOut: Iterator[Block] = get()._blockViaContainsOut
def _callViaContainsOut: Iterator[Call] = get()._callViaContainsOut
def _identifierViaContainsOut: Iterator[Identifier] = get()._identifierViaContainsOut
def _controlStructureViaContainsOut: Iterator[ControlStructure] = get()._controlStructureViaContainsOut
def _jumpTargetViaContainsOut: Iterator[JumpTarget] = get()._jumpTargetViaContainsOut
def _returnViaContainsOut: Iterator[Return] = get()._returnViaContainsOut
def _literalViaContainsOut: Iterator[Literal] = get()._literalViaContainsOut
def _fieldIdentifierViaContainsOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaContainsOut
def _typeRefViaContainsOut: Iterator[TypeRef] = get()._typeRefViaContainsOut
def _unknownViaContainsOut: Iterator[Unknown] = get()._unknownViaContainsOut
override def _containsOut: JIterator[StoredNode] = get()._containsOut
def _blockViaCfgOut: Iterator[Block] = get()._blockViaCfgOut
def _controlStructureViaCfgOut: Iterator[ControlStructure] = get()._controlStructureViaCfgOut
def _returnViaCfgOut: Iterator[Return] = get()._returnViaCfgOut
def _jumpTargetViaCfgOut: Iterator[JumpTarget] = get()._jumpTargetViaCfgOut
def _typeRefViaCfgOut: Iterator[TypeRef] = get()._typeRefViaCfgOut
def _identifierViaCfgOut: Iterator[Identifier] = get()._identifierViaCfgOut
def _fieldIdentifierViaCfgOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaCfgOut
def _methodRefViaCfgOut: Iterator[MethodRef] = get()._methodRefViaCfgOut
def _literalViaCfgOut: Iterator[Literal] = get()._literalViaCfgOut
def _methodReturnViaCfgOut: Option[MethodReturn] = get()._methodReturnViaCfgOut
def _callViaCfgOut: Iterator[Call] = get()._callViaCfgOut
def _unknownViaCfgOut: Iterator[Unknown] = get()._unknownViaCfgOut
override def _cfgOut: JIterator[StoredNode] = get()._cfgOut
def _fileViaSourceFileOut: Iterator[File] = get()._fileViaSourceFileOut
override def _sourceFileOut: JIterator[StoredNode] = get()._sourceFileOut
def _callViaReachingDefOut: Iterator[Call] = get()._callViaReachingDefOut
def _methodRefViaReachingDefOut: Iterator[MethodRef] = get()._methodRefViaReachingDefOut
def _returnViaReachingDefOut: Iterator[Return] = get()._returnViaReachingDefOut
def _literalViaReachingDefOut: Iterator[Literal] = get()._literalViaReachingDefOut
def _identifierViaReachingDefOut: Iterator[Identifier] = get()._identifierViaReachingDefOut
def _methodParameterInViaReachingDefOut: Iterator[MethodParameterIn] = get()._methodParameterInViaReachingDefOut
override def _reachingDefOut: JIterator[StoredNode] = get()._reachingDefOut
def _tagViaTaggedByOut: Iterator[Tag] = get()._tagViaTaggedByOut
override def _taggedByOut: JIterator[StoredNode] = get()._taggedByOut
def _methodRefViaRefIn: Iterator[MethodRef] = get()._methodRefViaRefIn
def _bindingViaRefIn: Iterator[Binding] = get()._bindingViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
def _namespaceBlockViaAstIn: Option[NamespaceBlock] = get()._namespaceBlockViaAstIn
def _typeDeclViaAstIn: Option[TypeDecl] = get()._typeDeclViaAstIn
def _methodViaAstIn: Option[Method] = get()._methodViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _fileViaContainsIn: Iterator[File] = get()._fileViaContainsIn
def _typeDeclViaContainsIn: Iterator[TypeDecl] = get()._typeDeclViaContainsIn
override def _containsIn: JIterator[StoredNode] = get()._containsIn
def _typeDeclViaVtableIn: Iterator[TypeDecl] = get()._typeDeclViaVtableIn
override def _vtableIn: JIterator[StoredNode] = get()._vtableIn
def _literalViaPostDominateIn: Iterator[Literal] = get()._literalViaPostDominateIn
def _typeRefViaPostDominateIn: Iterator[TypeRef] = get()._typeRefViaPostDominateIn
def _methodReturnViaPostDominateIn: Iterator[MethodReturn] = get()._methodReturnViaPostDominateIn
def _unknownViaPostDominateIn: Iterator[Unknown] = get()._unknownViaPostDominateIn
def _callViaPostDominateIn: Iterator[Call] = get()._callViaPostDominateIn
def _identifierViaPostDominateIn: Iterator[Identifier] = get()._identifierViaPostDominateIn
def _methodRefViaPostDominateIn: Iterator[MethodRef] = get()._methodRefViaPostDominateIn
def _blockViaPostDominateIn: Iterator[Block] = get()._blockViaPostDominateIn
def _fieldIdentifierViaPostDominateIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaPostDominateIn
def _controlStructureViaPostDominateIn: Iterator[ControlStructure] = get()._controlStructureViaPostDominateIn
def _returnViaPostDominateIn: Iterator[Return] = get()._returnViaPostDominateIn
override def _postDominateIn: JIterator[StoredNode] = get()._postDominateIn
def _callViaCallIn: Iterator[Call] = get()._callViaCallIn
override def _callIn: JIterator[StoredNode] = get()._callIn
def _methodReturnViaDynamicTypeIn: Iterator[MethodReturn] = get()._methodReturnViaDynamicTypeIn
def _callViaDynamicTypeIn: Iterator[Call] = get()._callViaDynamicTypeIn
def _methodRefViaDynamicTypeIn: Iterator[MethodRef] = get()._methodRefViaDynamicTypeIn
def _localViaDynamicTypeIn: Iterator[Local] = get()._localViaDynamicTypeIn
def _literalViaDynamicTypeIn: Iterator[Literal] = get()._literalViaDynamicTypeIn
def _blockViaDynamicTypeIn: Iterator[Block] = get()._blockViaDynamicTypeIn
def _methodParameterInViaDynamicTypeIn: Iterator[MethodParameterIn] = get()._methodParameterInViaDynamicTypeIn
def _fieldIdentifierViaDynamicTypeIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaDynamicTypeIn
def _unknownViaDynamicTypeIn: Iterator[Unknown] = get()._unknownViaDynamicTypeIn
def _identifierViaDynamicTypeIn: Iterator[Identifier] = get()._identifierViaDynamicTypeIn
def _typeRefViaDynamicTypeIn: Iterator[TypeRef] = get()._typeRefViaDynamicTypeIn
def _controlStructureViaDynamicTypeIn: Iterator[ControlStructure] = get()._controlStructureViaDynamicTypeIn
override def _dynamicTypeIn: JIterator[StoredNode] = get()._dynamicTypeIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Method.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "astParentFullName" 
case 2 => "astParentType" 
case 3 => "binarySignature" 
case 4 => "code" 
case 5 => "columnNumber" 
case 6 => "columnNumberEnd" 
case 7 => "depthFirstOrder" 
case 8 => "filename" 
case 9 => "fullName" 
case 10 => "hasMapping" 
case 11 => "internalFlags" 
case 12 => "isExternal" 
case 13 => "lineNumber" 
case 14 => "lineNumberEnd" 
case 15 => "name" 
case 16 => "order" 
case 17 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => astParentFullName
case 2 => astParentType
case 3 => binarySignature
case 4 => code
case 5 => columnNumber
case 6 => columnNumberEnd
case 7 => depthFirstOrder
case 8 => filename
case 9 => fullName
case 10 => hasMapping
case 11 => internalFlags
case 12 => isExternal
case 13 => lineNumber
case 14 => lineNumberEnd
case 15 => name
case 16 => order
case 17 => signature
    }

  override def productPrefix = "Method"
  override def productArity = 18
}

class MethodDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with Declaration with CfgNode with AstNode with MethodBase {

  override def layoutInformation: NodeLayoutInformation = Method.layoutInformation

private var _astParentFullName: String = null
def astParentFullName: String = _astParentFullName

private var _astParentType: String = null
def astParentType: String = _astParentType

private var _binarySignature: Option[String] = None
def binarySignature: Option[String] = _binarySignature

private var _code: String = null
def code: String = _code

private var _columnNumber: Option[java.lang.Integer] = None
def columnNumber: Option[java.lang.Integer] = _columnNumber

private var _columnNumberEnd: Option[java.lang.Integer] = None
def columnNumberEnd: Option[java.lang.Integer] = _columnNumberEnd

private var _depthFirstOrder: Option[java.lang.Integer] = None
def depthFirstOrder: Option[java.lang.Integer] = _depthFirstOrder

private var _filename: String = null
def filename: String = _filename

private var _fullName: String = null
def fullName: String = _fullName

private var _hasMapping: Option[java.lang.Boolean] = None
def hasMapping: Option[java.lang.Boolean] = _hasMapping

private var _internalFlags: Option[java.lang.Integer] = None
def internalFlags: Option[java.lang.Integer] = _internalFlags

private var _isExternal: java.lang.Boolean = null
def isExternal: java.lang.Boolean = _isExternal

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber

private var _lineNumberEnd: Option[java.lang.Integer] = None
def lineNumberEnd: Option[java.lang.Integer] = _lineNumberEnd

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _signature: String = null
def signature: String = _signature


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (astParentFullName != null) { properties.put("AST_PARENT_FULL_NAME", astParentFullName) }
if (astParentType != null) { properties.put("AST_PARENT_TYPE", astParentType) }
binarySignature.map { value => properties.put("BINARY_SIGNATURE", value) }
if (code != null) { properties.put("CODE", code) }
columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
columnNumberEnd.map { value => properties.put("COLUMN_NUMBER_END", value) }
depthFirstOrder.map { value => properties.put("DEPTH_FIRST_ORDER", value) }
if (filename != null) { properties.put("FILENAME", filename) }
if (fullName != null) { properties.put("FULL_NAME", fullName) }
hasMapping.map { value => properties.put("HAS_MAPPING", value) }
internalFlags.map { value => properties.put("INTERNAL_FLAGS", value) }
if (isExternal != null) { properties.put("IS_EXTERNAL", isExternal) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
lineNumberEnd.map { value => properties.put("LINE_NUMBER_END", value) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }
if (signature != null) { properties.put("SIGNATURE", signature) }

  properties
}

  def _identifierViaDominateOut: Iterator[Identifier] = _dominateOut.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaDominateOut: Iterator[FieldIdentifier] = _dominateOut.asScala.collect { case node: FieldIdentifier => node }
def _blockViaDominateOut: Iterator[Block] = _dominateOut.asScala.collect { case node: Block => node }
def _callViaDominateOut: Iterator[Call] = _dominateOut.asScala.collect { case node: Call => node }
def _methodRefViaDominateOut: Iterator[MethodRef] = _dominateOut.asScala.collect { case node: MethodRef => node }
def _literalViaDominateOut: Iterator[Literal] = _dominateOut.asScala.collect { case node: Literal => node }
def _unknownViaDominateOut: Iterator[Unknown] = _dominateOut.asScala.collect { case node: Unknown => node }
def _methodReturnViaDominateOut: Iterator[MethodReturn] = _dominateOut.asScala.collect { case node: MethodReturn => node }
def _returnViaDominateOut: Iterator[Return] = _dominateOut.asScala.collect { case node: Return => node }
def _typeRefViaDominateOut: Iterator[TypeRef] = _dominateOut.asScala.collect { case node: TypeRef => node }
override def _dominateOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _postExecutionCallViaAstOut: Iterator[PostExecutionCall] = _astOut.asScala.collect { case node: PostExecutionCall => node }
def _typeParameterViaAstOut: Iterator[TypeParameter] = _astOut.asScala.collect { case node: TypeParameter => node }
def _methodParameterOutViaAstOut: Iterator[MethodParameterOut] = _astOut.asScala.collect { case node: MethodParameterOut => node }
def _annotationViaAstOut: Iterator[Annotation] = _astOut.asScala.collect { case node: Annotation => node }
def _modifierViaAstOut: Iterator[Modifier] = _astOut.asScala.collect { case node: Modifier => node }
def _methodReturnViaAstOut: MethodReturn = _astOut.asScala.collect { case node: MethodReturn => node }.next()
def _methodViaAstOut: Iterator[Method] = _astOut.asScala.collect { case node: Method => node }
def _blockViaAstOut: Block = _astOut.asScala.collect { case node: Block => node }.next()
def _methodParameterInViaAstOut: Iterator[MethodParameterIn] = _astOut.asScala.collect { case node: MethodParameterIn => node }
def _typeDeclViaAstOut: Iterator[TypeDecl] = _astOut.asScala.collect { case node: TypeDecl => node }
def _implicitCallViaAstOut: Iterator[ImplicitCall] = _astOut.asScala.collect { case node: ImplicitCall => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _methodRefViaContainsOut: Iterator[MethodRef] = _containsOut.asScala.collect { case node: MethodRef => node }
def _blockViaContainsOut: Iterator[Block] = _containsOut.asScala.collect { case node: Block => node }
def _callViaContainsOut: Iterator[Call] = _containsOut.asScala.collect { case node: Call => node }
def _identifierViaContainsOut: Iterator[Identifier] = _containsOut.asScala.collect { case node: Identifier => node }
def _controlStructureViaContainsOut: Iterator[ControlStructure] = _containsOut.asScala.collect { case node: ControlStructure => node }
def _jumpTargetViaContainsOut: Iterator[JumpTarget] = _containsOut.asScala.collect { case node: JumpTarget => node }
def _returnViaContainsOut: Iterator[Return] = _containsOut.asScala.collect { case node: Return => node }
def _literalViaContainsOut: Iterator[Literal] = _containsOut.asScala.collect { case node: Literal => node }
def _fieldIdentifierViaContainsOut: Iterator[FieldIdentifier] = _containsOut.asScala.collect { case node: FieldIdentifier => node }
def _typeRefViaContainsOut: Iterator[TypeRef] = _containsOut.asScala.collect { case node: TypeRef => node }
def _unknownViaContainsOut: Iterator[Unknown] = _containsOut.asScala.collect { case node: Unknown => node }
override def _containsOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _blockViaCfgOut: Iterator[Block] = _cfgOut.asScala.collect { case node: Block => node }
def _controlStructureViaCfgOut: Iterator[ControlStructure] = _cfgOut.asScala.collect { case node: ControlStructure => node }
def _returnViaCfgOut: Iterator[Return] = _cfgOut.asScala.collect { case node: Return => node }
def _jumpTargetViaCfgOut: Iterator[JumpTarget] = _cfgOut.asScala.collect { case node: JumpTarget => node }
def _typeRefViaCfgOut: Iterator[TypeRef] = _cfgOut.asScala.collect { case node: TypeRef => node }
def _identifierViaCfgOut: Iterator[Identifier] = _cfgOut.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaCfgOut: Iterator[FieldIdentifier] = _cfgOut.asScala.collect { case node: FieldIdentifier => node }
def _methodRefViaCfgOut: Iterator[MethodRef] = _cfgOut.asScala.collect { case node: MethodRef => node }
def _literalViaCfgOut: Iterator[Literal] = _cfgOut.asScala.collect { case node: Literal => node }
def _methodReturnViaCfgOut: Option[MethodReturn] = _cfgOut.asScala.collect { case node: MethodReturn => node }.nextOption()
def _callViaCfgOut: Iterator[Call] = _cfgOut.asScala.collect { case node: Call => node }
def _unknownViaCfgOut: Iterator[Unknown] = _cfgOut.asScala.collect { case node: Unknown => node }
override def _cfgOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _fileViaSourceFileOut: Iterator[File] = _sourceFileOut.asScala.collect { case node: File => node }
override def _sourceFileOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _callViaReachingDefOut: Iterator[Call] = _reachingDefOut.asScala.collect { case node: Call => node }
def _methodRefViaReachingDefOut: Iterator[MethodRef] = _reachingDefOut.asScala.collect { case node: MethodRef => node }
def _returnViaReachingDefOut: Iterator[Return] = _reachingDefOut.asScala.collect { case node: Return => node }
def _literalViaReachingDefOut: Iterator[Literal] = _reachingDefOut.asScala.collect { case node: Literal => node }
def _identifierViaReachingDefOut: Iterator[Identifier] = _reachingDefOut.asScala.collect { case node: Identifier => node }
def _methodParameterInViaReachingDefOut: Iterator[MethodParameterIn] = _reachingDefOut.asScala.collect { case node: MethodParameterIn => node }
override def _reachingDefOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]
def _tagViaTaggedByOut: Iterator[Tag] = _taggedByOut.asScala.collect { case node: Tag => node }
override def _taggedByOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(6).asInstanceOf[JIterator[StoredNode]]
def _methodRefViaRefIn: Iterator[MethodRef] = _refIn.asScala.collect { case node: MethodRef => node }
def _bindingViaRefIn: Iterator[Binding] = _refIn.asScala.collect { case node: Binding => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(7).asInstanceOf[JIterator[StoredNode]]
def _namespaceBlockViaAstIn: Option[NamespaceBlock] = _astIn.asScala.collect { case node: NamespaceBlock => node }.nextOption()
def _typeDeclViaAstIn: Option[TypeDecl] = _astIn.asScala.collect { case node: TypeDecl => node }.nextOption()
def _methodViaAstIn: Option[Method] = _astIn.asScala.collect { case node: Method => node }.nextOption()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(8).asInstanceOf[JIterator[StoredNode]]
def _fileViaContainsIn: Iterator[File] = _containsIn.asScala.collect { case node: File => node }
def _typeDeclViaContainsIn: Iterator[TypeDecl] = _containsIn.asScala.collect { case node: TypeDecl => node }
override def _containsIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(9).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaVtableIn: Iterator[TypeDecl] = _vtableIn.asScala.collect { case node: TypeDecl => node }
override def _vtableIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(10).asInstanceOf[JIterator[StoredNode]]
def _literalViaPostDominateIn: Iterator[Literal] = _postDominateIn.asScala.collect { case node: Literal => node }
def _typeRefViaPostDominateIn: Iterator[TypeRef] = _postDominateIn.asScala.collect { case node: TypeRef => node }
def _methodReturnViaPostDominateIn: Iterator[MethodReturn] = _postDominateIn.asScala.collect { case node: MethodReturn => node }
def _unknownViaPostDominateIn: Iterator[Unknown] = _postDominateIn.asScala.collect { case node: Unknown => node }
def _callViaPostDominateIn: Iterator[Call] = _postDominateIn.asScala.collect { case node: Call => node }
def _identifierViaPostDominateIn: Iterator[Identifier] = _postDominateIn.asScala.collect { case node: Identifier => node }
def _methodRefViaPostDominateIn: Iterator[MethodRef] = _postDominateIn.asScala.collect { case node: MethodRef => node }
def _blockViaPostDominateIn: Iterator[Block] = _postDominateIn.asScala.collect { case node: Block => node }
def _fieldIdentifierViaPostDominateIn: Iterator[FieldIdentifier] = _postDominateIn.asScala.collect { case node: FieldIdentifier => node }
def _controlStructureViaPostDominateIn: Iterator[ControlStructure] = _postDominateIn.asScala.collect { case node: ControlStructure => node }
def _returnViaPostDominateIn: Iterator[Return] = _postDominateIn.asScala.collect { case node: Return => node }
override def _postDominateIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(11).asInstanceOf[JIterator[StoredNode]]
def _callViaCallIn: Iterator[Call] = _callIn.asScala.collect { case node: Call => node }
override def _callIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(12).asInstanceOf[JIterator[StoredNode]]
def _methodReturnViaDynamicTypeIn: Iterator[MethodReturn] = _dynamicTypeIn.asScala.collect { case node: MethodReturn => node }
def _callViaDynamicTypeIn: Iterator[Call] = _dynamicTypeIn.asScala.collect { case node: Call => node }
def _methodRefViaDynamicTypeIn: Iterator[MethodRef] = _dynamicTypeIn.asScala.collect { case node: MethodRef => node }
def _localViaDynamicTypeIn: Iterator[Local] = _dynamicTypeIn.asScala.collect { case node: Local => node }
def _literalViaDynamicTypeIn: Iterator[Literal] = _dynamicTypeIn.asScala.collect { case node: Literal => node }
def _blockViaDynamicTypeIn: Iterator[Block] = _dynamicTypeIn.asScala.collect { case node: Block => node }
def _methodParameterInViaDynamicTypeIn: Iterator[MethodParameterIn] = _dynamicTypeIn.asScala.collect { case node: MethodParameterIn => node }
def _fieldIdentifierViaDynamicTypeIn: Iterator[FieldIdentifier] = _dynamicTypeIn.asScala.collect { case node: FieldIdentifier => node }
def _unknownViaDynamicTypeIn: Iterator[Unknown] = _dynamicTypeIn.asScala.collect { case node: Unknown => node }
def _identifierViaDynamicTypeIn: Iterator[Identifier] = _dynamicTypeIn.asScala.collect { case node: Identifier => node }
def _typeRefViaDynamicTypeIn: Iterator[TypeRef] = _dynamicTypeIn.asScala.collect { case node: TypeRef => node }
def _controlStructureViaDynamicTypeIn: Iterator[ControlStructure] = _dynamicTypeIn.asScala.collect { case node: ControlStructure => node }
override def _dynamicTypeIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(13).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Method.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "astParentFullName" 
case 2 => "astParentType" 
case 3 => "binarySignature" 
case 4 => "code" 
case 5 => "columnNumber" 
case 6 => "columnNumberEnd" 
case 7 => "depthFirstOrder" 
case 8 => "filename" 
case 9 => "fullName" 
case 10 => "hasMapping" 
case 11 => "internalFlags" 
case 12 => "isExternal" 
case 13 => "lineNumber" 
case 14 => "lineNumberEnd" 
case 15 => "name" 
case 16 => "order" 
case 17 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => astParentFullName
case 2 => astParentType
case 3 => binarySignature
case 4 => code
case 5 => columnNumber
case 6 => columnNumberEnd
case 7 => depthFirstOrder
case 8 => filename
case 9 => fullName
case 10 => hasMapping
case 11 => internalFlags
case 12 => isExternal
case 13 => lineNumber
case 14 => lineNumberEnd
case 15 => name
case 16 => order
case 17 => signature
    }

  override def productPrefix = "Method"
  override def productArity = 18

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodDb]

  override def property(key:String): AnyRef = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName
      case "AST_PARENT_TYPE" => this._astParentType
      case "BINARY_SIGNATURE" => this._binarySignature.orNull
      case "CODE" => this._code
      case "COLUMN_NUMBER" => this._columnNumber.orNull
      case "COLUMN_NUMBER_END" => this._columnNumberEnd.orNull
      case "DEPTH_FIRST_ORDER" => this._depthFirstOrder.orNull
      case "FILENAME" => this._filename
      case "FULL_NAME" => this._fullName
      case "HAS_MAPPING" => this._hasMapping.orNull
      case "INTERNAL_FLAGS" => this._internalFlags.orNull
      case "IS_EXTERNAL" => this._isExternal
      case "LINE_NUMBER" => this._lineNumber.orNull
      case "LINE_NUMBER_END" => this._lineNumberEnd.orNull
      case "NAME" => this._name
      case "ORDER" => this._order
      case "SIGNATURE" => this._signature
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName = value.asInstanceOf[String]
      case "AST_PARENT_TYPE" => this._astParentType = value.asInstanceOf[String]
      case "BINARY_SIGNATURE" => this._binarySignature = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "CODE" => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "COLUMN_NUMBER_END" => this._columnNumberEnd = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "DEPTH_FIRST_ORDER" => this._depthFirstOrder = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "FILENAME" => this._filename = value.asInstanceOf[String]
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "HAS_MAPPING" => this._hasMapping = value match {
        case null | None => None
        case someVal: java.lang.Boolean => Some(someVal)
      }
      case "INTERNAL_FLAGS" => this._internalFlags = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "IS_EXTERNAL" => this._isExternal = value.asInstanceOf[java.lang.Boolean]
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "LINE_NUMBER_END" => this._lineNumberEnd = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "SIGNATURE" => this._signature = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMethod]
   this._astParentFullName = other.astParentFullName
   this._astParentType = other.astParentType
   this._binarySignature = if(other.binarySignature != null) other.binarySignature else None
   this._code = other.code
   this._columnNumber = if(other.columnNumber != null) other.columnNumber else None
   this._columnNumberEnd = if(other.columnNumberEnd != null) other.columnNumberEnd else None
   this._depthFirstOrder = if(other.depthFirstOrder != null) other.depthFirstOrder else None
   this._filename = other.filename
   this._fullName = other.fullName
   this._hasMapping = if(other.hasMapping != null) other.hasMapping else None
   this._internalFlags = if(other.internalFlags != null) other.internalFlags else None
   this._isExternal = other.isExternal
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None
   this._lineNumberEnd = if(other.lineNumberEnd != null) other.lineNumberEnd else None
   this._name = other.name
   this._order = other.order
   this._signature = other.signature

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for Method */
class MethodTraversal[NodeType <: Method](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to astParentFullName property */
  def astParentFullName: Traversal[String] =
    traversal.map(_.astParentFullName)

    /**
    * Traverse to nodes where the astParentFullName matches the regular expression `value`
    * */
  def astParentFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the astParentFullName matches at least one of the regular expressions in `values`
    * */
  def astParentFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.astParentFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where astParentFullName matches `value` exactly.
    * */
  def astParentFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.astParentFullName == value}

  /**
    * Traverse to nodes where astParentFullName matches one of the elements in `values` exactly.
    * */
  def astParentFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.astParentFullName)}
  }

  /**
    * Traverse to nodes where astParentFullName does not match the regular expression `value`.
    * */
  def astParentFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where astParentFullName does not match any of the regular expressions in `values`.
    * */
  def astParentFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.astParentFullName); matcher.matches()}}
   }



  /** Traverse to astParentType property */
  def astParentType: Traversal[String] =
    traversal.map(_.astParentType)

    /**
    * Traverse to nodes where the astParentType matches the regular expression `value`
    * */
  def astParentType(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentType == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentType); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the astParentType matches at least one of the regular expressions in `values`
    * */
  def astParentType(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.astParentType); matcher.matches()}}
   }

  /**
    * Traverse to nodes where astParentType matches `value` exactly.
    * */
  def astParentTypeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.astParentType == value}

  /**
    * Traverse to nodes where astParentType matches one of the elements in `values` exactly.
    * */
  def astParentTypeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.astParentType)}
  }

  /**
    * Traverse to nodes where astParentType does not match the regular expression `value`.
    * */
  def astParentTypeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.astParentType != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.astParentType); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where astParentType does not match any of the regular expressions in `values`.
    * */
  def astParentTypeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.astParentType); matcher.matches()}}
   }



  /** Traverse to binarySignature property */
  def binarySignature: Traversal[String] =
    traversal.flatMap(_.binarySignature)

    /**
    * Traverse to nodes where the binarySignature matches the regular expression `value`
    * */
  def binarySignature(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.binarySignature.isDefined && node.binarySignature.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.binarySignature.isDefined && {matcher.reset(node.binarySignature.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the binarySignature matches at least one of the regular expressions in `values`
    * */
  def binarySignature(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.binarySignature.isDefined && matchers.exists{ matcher => matcher.reset(node.binarySignature.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where binarySignature matches `value` exactly.
    * */
  def binarySignatureExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.binarySignature.isDefined && node.binarySignature.get == value}

  /**
    * Traverse to nodes where binarySignature matches one of the elements in `values` exactly.
    * */
  def binarySignatureExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.binarySignature.isDefined && vset.contains(node.binarySignature.get)}
  }

  /**
    * Traverse to nodes where binarySignature does not match the regular expression `value`.
    * */
  def binarySignatureNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.binarySignature.isEmpty || node.binarySignature.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.binarySignature.isEmpty || {matcher.reset(node.binarySignature.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where binarySignature does not match any of the regular expressions in `values`.
    * */
  def binarySignatureNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.binarySignature.isEmpty || !matchers.exists{ matcher => matcher.reset(node.binarySignature.get); matcher.matches()}}
   }



  /** Traverse to code property */
  def code: Traversal[String] =
    traversal.map(_.code)

    /**
    * Traverse to nodes where the code matches the regular expression `value`
    * */
  def code(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the code matches at least one of the regular expressions in `values`
    * */
  def code(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }

  /**
    * Traverse to nodes where code matches `value` exactly.
    * */
  def codeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.code == value}

  /**
    * Traverse to nodes where code matches one of the elements in `values` exactly.
    * */
  def codeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.code)}
  }

  /**
    * Traverse to nodes where code does not match the regular expression `value`.
    * */
  def codeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.code != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.code); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where code does not match any of the regular expressions in `values`.
    * */
  def codeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.code); matcher.matches()}}
   }



  /** Traverse to columnNumber property */
  def columnNumber: Traversal[java.lang.Integer] =
    traversal.flatMap(_.columnNumber)

    /**
    * Traverse to nodes where the columnNumber equals the given `value`
    * */
  def columnNumber(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumber.isDefined && node.columnNumber.get == value}

  /**
    * Traverse to nodes where the columnNumber equals at least one of the given `values`
    * */
  def columnNumber(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.columnNumber.isDefined && vset.contains(node.columnNumber.get)}
  }

  /**
    * Traverse to nodes where the columnNumber is greater than the given `value`
    * */
  def columnNumberGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumber.isDefined && node.columnNumber.get > value}

  /**
    * Traverse to nodes where the columnNumber is greater than or equal the given `value`
    * */
  def columnNumberGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumber.isDefined && node.columnNumber.get >= value}

  /**
    * Traverse to nodes where the columnNumber is less than the given `value`
    * */
  def columnNumberLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumber.isDefined && node.columnNumber.get < value}

  /**
    * Traverse to nodes where the columnNumber is less than or equal the given `value`
    * */
  def columnNumberLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumber.isDefined && node.columnNumber.get <= value}

  /**
    * Traverse to nodes where columnNumber is not equal to the given `value`.
    * */
  def columnNumberNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.columnNumber.isDefined || node.columnNumber.get != value}

  /**
    * Traverse to nodes where columnNumber is not equal to any of the given `values`.
    * */
  def columnNumberNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.columnNumber.isDefined || !vset.contains(node.columnNumber.get)}
  }


  /** Traverse to columnNumberEnd property */
  def columnNumberEnd: Traversal[java.lang.Integer] =
    traversal.flatMap(_.columnNumberEnd)

    /**
    * Traverse to nodes where the columnNumberEnd equals the given `value`
    * */
  def columnNumberEnd(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get == value}

  /**
    * Traverse to nodes where the columnNumberEnd equals at least one of the given `values`
    * */
  def columnNumberEnd(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.columnNumberEnd.isDefined && vset.contains(node.columnNumberEnd.get)}
  }

  /**
    * Traverse to nodes where the columnNumberEnd is greater than the given `value`
    * */
  def columnNumberEndGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get > value}

  /**
    * Traverse to nodes where the columnNumberEnd is greater than or equal the given `value`
    * */
  def columnNumberEndGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get >= value}

  /**
    * Traverse to nodes where the columnNumberEnd is less than the given `value`
    * */
  def columnNumberEndLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get < value}

  /**
    * Traverse to nodes where the columnNumberEnd is less than or equal the given `value`
    * */
  def columnNumberEndLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.columnNumberEnd.isDefined && node.columnNumberEnd.get <= value}

  /**
    * Traverse to nodes where columnNumberEnd is not equal to the given `value`.
    * */
  def columnNumberEndNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.columnNumberEnd.isDefined || node.columnNumberEnd.get != value}

  /**
    * Traverse to nodes where columnNumberEnd is not equal to any of the given `values`.
    * */
  def columnNumberEndNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.columnNumberEnd.isDefined || !vset.contains(node.columnNumberEnd.get)}
  }


  /** Traverse to depthFirstOrder property */
  def depthFirstOrder: Traversal[java.lang.Integer] =
    traversal.flatMap(_.depthFirstOrder)

    /**
    * Traverse to nodes where the depthFirstOrder equals the given `value`
    * */
  def depthFirstOrder(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.depthFirstOrder.isDefined && node.depthFirstOrder.get == value}

  /**
    * Traverse to nodes where the depthFirstOrder equals at least one of the given `values`
    * */
  def depthFirstOrder(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.depthFirstOrder.isDefined && vset.contains(node.depthFirstOrder.get)}
  }

  /**
    * Traverse to nodes where the depthFirstOrder is greater than the given `value`
    * */
  def depthFirstOrderGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.depthFirstOrder.isDefined && node.depthFirstOrder.get > value}

  /**
    * Traverse to nodes where the depthFirstOrder is greater than or equal the given `value`
    * */
  def depthFirstOrderGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.depthFirstOrder.isDefined && node.depthFirstOrder.get >= value}

  /**
    * Traverse to nodes where the depthFirstOrder is less than the given `value`
    * */
  def depthFirstOrderLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.depthFirstOrder.isDefined && node.depthFirstOrder.get < value}

  /**
    * Traverse to nodes where the depthFirstOrder is less than or equal the given `value`
    * */
  def depthFirstOrderLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.depthFirstOrder.isDefined && node.depthFirstOrder.get <= value}

  /**
    * Traverse to nodes where depthFirstOrder is not equal to the given `value`.
    * */
  def depthFirstOrderNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.depthFirstOrder.isDefined || node.depthFirstOrder.get != value}

  /**
    * Traverse to nodes where depthFirstOrder is not equal to any of the given `values`.
    * */
  def depthFirstOrderNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.depthFirstOrder.isDefined || !vset.contains(node.depthFirstOrder.get)}
  }


  /** Traverse to filename property */
  def filename: Traversal[String] =
    traversal.map(_.filename)

    /**
    * Traverse to nodes where the filename matches the regular expression `value`
    * */
  def filename(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    * */
  def filename(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }

  /**
    * Traverse to nodes where filename matches `value` exactly.
    * */
  def filenameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.filename == value}

  /**
    * Traverse to nodes where filename matches one of the elements in `values` exactly.
    * */
  def filenameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.filename)}
  }

  /**
    * Traverse to nodes where filename does not match the regular expression `value`.
    * */
  def filenameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where filename does not match any of the regular expressions in `values`.
    * */
  def filenameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }



  /** Traverse to fullName property */
  def fullName: Traversal[String] =
    traversal.map(_.fullName)

    /**
    * Traverse to nodes where the fullName matches the regular expression `value`
    * */
  def fullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the fullName matches at least one of the regular expressions in `values`
    * */
  def fullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where fullName matches `value` exactly.
    * */
  def fullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.fullName == value}

  /**
    * Traverse to nodes where fullName matches one of the elements in `values` exactly.
    * */
  def fullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.fullName)}
  }

  /**
    * Traverse to nodes where fullName does not match the regular expression `value`.
    * */
  def fullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where fullName does not match any of the regular expressions in `values`.
    * */
  def fullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
   }



  /** Traverse to hasMapping property */
  def hasMapping: Traversal[java.lang.Boolean] =
    traversal.flatMap(_.hasMapping)

    /**
    * Traverse to nodes where the hasMapping equals the given `value`
    * */
  def hasMapping(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{node => node.hasMapping.isDefined && node.hasMapping.get == value}

  /**
    * Traverse to nodes where hasMapping is not equal to the given `value`.
    * */
  def hasMappingNot(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{node => !node.hasMapping.isDefined || node.hasMapping.get == value}


  /** Traverse to internalFlags property */
  def internalFlags: Traversal[java.lang.Integer] =
    traversal.flatMap(_.internalFlags)

    /**
    * Traverse to nodes where the internalFlags equals the given `value`
    * */
  def internalFlags(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.internalFlags.isDefined && node.internalFlags.get == value}

  /**
    * Traverse to nodes where the internalFlags equals at least one of the given `values`
    * */
  def internalFlags(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.internalFlags.isDefined && vset.contains(node.internalFlags.get)}
  }

  /**
    * Traverse to nodes where the internalFlags is greater than the given `value`
    * */
  def internalFlagsGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.internalFlags.isDefined && node.internalFlags.get > value}

  /**
    * Traverse to nodes where the internalFlags is greater than or equal the given `value`
    * */
  def internalFlagsGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.internalFlags.isDefined && node.internalFlags.get >= value}

  /**
    * Traverse to nodes where the internalFlags is less than the given `value`
    * */
  def internalFlagsLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.internalFlags.isDefined && node.internalFlags.get < value}

  /**
    * Traverse to nodes where the internalFlags is less than or equal the given `value`
    * */
  def internalFlagsLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.internalFlags.isDefined && node.internalFlags.get <= value}

  /**
    * Traverse to nodes where internalFlags is not equal to the given `value`.
    * */
  def internalFlagsNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.internalFlags.isDefined || node.internalFlags.get != value}

  /**
    * Traverse to nodes where internalFlags is not equal to any of the given `values`.
    * */
  def internalFlagsNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.internalFlags.isDefined || !vset.contains(node.internalFlags.get)}
  }


  /** Traverse to isExternal property */
  def isExternal: Traversal[java.lang.Boolean] =
    traversal.map(_.isExternal)

    /**
    * Traverse to nodes where the isExternal equals the given `value`
    * */
  def isExternal(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{_.isExternal == value}

  /**
    * Traverse to nodes where isExternal is not equal to the given `value`.
    * */
  def isExternalNot(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{_.isExternal != value}


  /** Traverse to lineNumber property */
  def lineNumber: Traversal[java.lang.Integer] =
    traversal.flatMap(_.lineNumber)

    /**
    * Traverse to nodes where the lineNumber equals the given `value`
    * */
  def lineNumber(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get == value}

  /**
    * Traverse to nodes where the lineNumber equals at least one of the given `values`
    * */
  def lineNumber(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.lineNumber.isDefined && vset.contains(node.lineNumber.get)}
  }

  /**
    * Traverse to nodes where the lineNumber is greater than the given `value`
    * */
  def lineNumberGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get > value}

  /**
    * Traverse to nodes where the lineNumber is greater than or equal the given `value`
    * */
  def lineNumberGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get >= value}

  /**
    * Traverse to nodes where the lineNumber is less than the given `value`
    * */
  def lineNumberLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get < value}

  /**
    * Traverse to nodes where the lineNumber is less than or equal the given `value`
    * */
  def lineNumberLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumber.isDefined && node.lineNumber.get <= value}

  /**
    * Traverse to nodes where lineNumber is not equal to the given `value`.
    * */
  def lineNumberNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.lineNumber.isDefined || node.lineNumber.get != value}

  /**
    * Traverse to nodes where lineNumber is not equal to any of the given `values`.
    * */
  def lineNumberNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.lineNumber.isDefined || !vset.contains(node.lineNumber.get)}
  }


  /** Traverse to lineNumberEnd property */
  def lineNumberEnd: Traversal[java.lang.Integer] =
    traversal.flatMap(_.lineNumberEnd)

    /**
    * Traverse to nodes where the lineNumberEnd equals the given `value`
    * */
  def lineNumberEnd(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get == value}

  /**
    * Traverse to nodes where the lineNumberEnd equals at least one of the given `values`
    * */
  def lineNumberEnd(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => node.lineNumberEnd.isDefined && vset.contains(node.lineNumberEnd.get)}
  }

  /**
    * Traverse to nodes where the lineNumberEnd is greater than the given `value`
    * */
  def lineNumberEndGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get > value}

  /**
    * Traverse to nodes where the lineNumberEnd is greater than or equal the given `value`
    * */
  def lineNumberEndGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get >= value}

  /**
    * Traverse to nodes where the lineNumberEnd is less than the given `value`
    * */
  def lineNumberEndLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get < value}

  /**
    * Traverse to nodes where the lineNumberEnd is less than or equal the given `value`
    * */
  def lineNumberEndLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => node.lineNumberEnd.isDefined && node.lineNumberEnd.get <= value}

  /**
    * Traverse to nodes where lineNumberEnd is not equal to the given `value`.
    * */
  def lineNumberEndNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{node => !node.lineNumberEnd.isDefined || node.lineNumberEnd.get != value}

  /**
    * Traverse to nodes where lineNumberEnd is not equal to any of the given `values`.
    * */
  def lineNumberEndNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !node.lineNumberEnd.isDefined || !vset.contains(node.lineNumberEnd.get)}
  }


  /** Traverse to name property */
  def name: Traversal[String] =
    traversal.map(_.name)

    /**
    * Traverse to nodes where the name matches the regular expression `value`
    * */
  def name(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.name == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.name); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the name matches at least one of the regular expressions in `values`
    * */
  def name(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.name); matcher.matches()}}
   }

  /**
    * Traverse to nodes where name matches `value` exactly.
    * */
  def nameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.name == value}

  /**
    * Traverse to nodes where name matches one of the elements in `values` exactly.
    * */
  def nameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.name)}
  }

  /**
    * Traverse to nodes where name does not match the regular expression `value`.
    * */
  def nameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.name != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.name); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where name does not match any of the regular expressions in `values`.
    * */
  def nameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.name); matcher.matches()}}
   }



  /** Traverse to order property */
  def order: Traversal[java.lang.Integer] =
    traversal.map(_.order)

    /**
    * Traverse to nodes where the order equals the given `value`
    * */
  def order(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order == value}

  /**
    * Traverse to nodes where the order equals at least one of the given `values`
    * */
  def order(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => vset.contains(node.order)}
  }

  /**
    * Traverse to nodes where the order is greater than the given `value`
    * */
  def orderGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order > value}

  /**
    * Traverse to nodes where the order is greater than or equal the given `value`
    * */
  def orderGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order >= value}

  /**
    * Traverse to nodes where the order is less than the given `value`
    * */
  def orderLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order < value}

  /**
    * Traverse to nodes where the order is less than or equal the given `value`
    * */
  def orderLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order <= value}

  /**
    * Traverse to nodes where order is not equal to the given `value`.
    * */
  def orderNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order != value}

  /**
    * Traverse to nodes where order is not equal to any of the given `values`.
    * */
  def orderNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !vset.contains(node.order)}
  }


  /** Traverse to signature property */
  def signature: Traversal[String] =
    traversal.map(_.signature)

    /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.signature == value}

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.signature)}
  }

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }




}
