package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object JumpTarget {
  def apply(graph: Graph, id: Long) = new JumpTarget(graph, id)

  val Label = "JUMP_TARGET"

  object PropertyNames {
    val ArgumentIndex = "ARGUMENT_INDEX" 
    val Code = "CODE" 
    val ColumnNumber = "COLUMN_NUMBER" 
    val InternalFlags = "INTERNAL_FLAGS" 
    val LineNumber = "LINE_NUMBER" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val ParserTypeName = "PARSER_TYPE_NAME" 
    val all: Set[String] = Set(ArgumentIndex, Code, ColumnNumber, InternalFlags, LineNumber, Name, Order, ParserTypeName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex = new PropertyKey[java.lang.Integer]("ARGUMENT_INDEX") 
    val Code = new PropertyKey[String]("CODE") 
    val ColumnNumber = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER") 
    val InternalFlags = new PropertyKey[java.lang.Integer]("INTERNAL_FLAGS") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val ParserTypeName = new PropertyKey[String]("PARSER_TYPE_NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Cdg.layoutInformation, edges.Cfg.layoutInformation, edges.Dominate.layoutInformation, edges.PostDominate.layoutInformation).asJava,
    List(edges.Dominate.layoutInformation, edges.Ast.layoutInformation, edges.Condition.layoutInformation, edges.Cdg.layoutInformation, edges.Contains.layoutInformation, edges.Cfg.layoutInformation, edges.Argument.layoutInformation, edges.PostDominate.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("CDG","CFG","DOMINATE","POST_DOMINATE")
    val In: Array[String] = Array("ARGUMENT","AST","CDG","CFG","CONDITION","CONTAINS","DOMINATE","POST_DOMINATE")
  }

  val factory = new NodeFactory[JumpTargetDb] {
    override val forLabel = JumpTarget.Label

    override def createNode(ref: NodeRef[JumpTargetDb]) =
      new JumpTargetDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = JumpTarget(graph, id)
  }
}

trait JumpTargetBase extends CpgNode with CfgNodeBase with AstNodeBase with HasArgumentIndex with HasCode with HasColumnNumber with HasInternalFlags with HasLineNumber with HasName with HasOrder with HasParserTypeName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class JumpTarget(graph: Graph, id: Long) extends NodeRef[JumpTargetDb](graph, id)
  with JumpTargetBase
  with StoredNode
  with CfgNode with AstNode {
    override def argumentIndex: java.lang.Integer = get().argumentIndex
  override def code: String = get().code
  override def columnNumber: Option[java.lang.Integer] = get().columnNumber
  override def internalFlags: Option[java.lang.Integer] = get().internalFlags
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  override def parserTypeName: String = get().parserTypeName
  
  def _typeRefViaCdgOut: Iterator[TypeRef] = get()._typeRefViaCdgOut
def _unknownViaCdgOut: Iterator[Unknown] = get()._unknownViaCdgOut
def _callViaCdgOut: Iterator[Call] = get()._callViaCdgOut
def _blockViaCdgOut: Iterator[Block] = get()._blockViaCdgOut
def _literalViaCdgOut: Iterator[Literal] = get()._literalViaCdgOut
def _fieldIdentifierViaCdgOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaCdgOut
def _jumpTargetViaCdgOut: Iterator[JumpTarget] = get()._jumpTargetViaCdgOut
def _returnViaCdgOut: Iterator[Return] = get()._returnViaCdgOut
def _controlStructureViaCdgOut: Iterator[ControlStructure] = get()._controlStructureViaCdgOut
def _identifierViaCdgOut: Iterator[Identifier] = get()._identifierViaCdgOut
def _methodReturnViaCdgOut: Iterator[MethodReturn] = get()._methodReturnViaCdgOut
def _methodRefViaCdgOut: Iterator[MethodRef] = get()._methodRefViaCdgOut
override def _cdgOut: JIterator[StoredNode] = get()._cdgOut
def _blockViaCfgOut: Iterator[Block] = get()._blockViaCfgOut
def _controlStructureViaCfgOut: Iterator[ControlStructure] = get()._controlStructureViaCfgOut
def _returnViaCfgOut: Iterator[Return] = get()._returnViaCfgOut
def _jumpTargetViaCfgOut: Iterator[JumpTarget] = get()._jumpTargetViaCfgOut
def _typeRefViaCfgOut: Iterator[TypeRef] = get()._typeRefViaCfgOut
def _identifierViaCfgOut: Iterator[Identifier] = get()._identifierViaCfgOut
def _fieldIdentifierViaCfgOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaCfgOut
def _methodRefViaCfgOut: Iterator[MethodRef] = get()._methodRefViaCfgOut
def _literalViaCfgOut: Iterator[Literal] = get()._literalViaCfgOut
def _callViaCfgOut: Iterator[Call] = get()._callViaCfgOut
def _unknownViaCfgOut: Iterator[Unknown] = get()._unknownViaCfgOut
override def _cfgOut: JIterator[StoredNode] = get()._cfgOut
def _jumpTargetViaDominateOut: Iterator[JumpTarget] = get()._jumpTargetViaDominateOut
def _controlStructureViaDominateOut: Iterator[ControlStructure] = get()._controlStructureViaDominateOut
def _identifierViaDominateOut: Iterator[Identifier] = get()._identifierViaDominateOut
def _fieldIdentifierViaDominateOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaDominateOut
def _blockViaDominateOut: Iterator[Block] = get()._blockViaDominateOut
def _callViaDominateOut: Iterator[Call] = get()._callViaDominateOut
def _methodRefViaDominateOut: Iterator[MethodRef] = get()._methodRefViaDominateOut
def _literalViaDominateOut: Iterator[Literal] = get()._literalViaDominateOut
def _unknownViaDominateOut: Iterator[Unknown] = get()._unknownViaDominateOut
def _returnViaDominateOut: Iterator[Return] = get()._returnViaDominateOut
def _typeRefViaDominateOut: Iterator[TypeRef] = get()._typeRefViaDominateOut
override def _dominateOut: JIterator[StoredNode] = get()._dominateOut
def _literalViaPostDominateOut: Iterator[Literal] = get()._literalViaPostDominateOut
def _jumpTargetViaPostDominateOut: Iterator[JumpTarget] = get()._jumpTargetViaPostDominateOut
def _typeRefViaPostDominateOut: Iterator[TypeRef] = get()._typeRefViaPostDominateOut
def _unknownViaPostDominateOut: Iterator[Unknown] = get()._unknownViaPostDominateOut
def _callViaPostDominateOut: Iterator[Call] = get()._callViaPostDominateOut
def _identifierViaPostDominateOut: Iterator[Identifier] = get()._identifierViaPostDominateOut
def _methodRefViaPostDominateOut: Iterator[MethodRef] = get()._methodRefViaPostDominateOut
def _blockViaPostDominateOut: Iterator[Block] = get()._blockViaPostDominateOut
def _fieldIdentifierViaPostDominateOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaPostDominateOut
def _controlStructureViaPostDominateOut: Iterator[ControlStructure] = get()._controlStructureViaPostDominateOut
def _returnViaPostDominateOut: Iterator[Return] = get()._returnViaPostDominateOut
override def _postDominateOut: JIterator[StoredNode] = get()._postDominateOut
def _jumpTargetViaDominateIn: Iterator[JumpTarget] = get()._jumpTargetViaDominateIn
def _controlStructureViaDominateIn: Iterator[ControlStructure] = get()._controlStructureViaDominateIn
def _identifierViaDominateIn: Iterator[Identifier] = get()._identifierViaDominateIn
def _fieldIdentifierViaDominateIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaDominateIn
def _blockViaDominateIn: Iterator[Block] = get()._blockViaDominateIn
def _callViaDominateIn: Iterator[Call] = get()._callViaDominateIn
def _methodRefViaDominateIn: Iterator[MethodRef] = get()._methodRefViaDominateIn
def _literalViaDominateIn: Iterator[Literal] = get()._literalViaDominateIn
def _unknownViaDominateIn: Iterator[Unknown] = get()._unknownViaDominateIn
def _returnViaDominateIn: Iterator[Return] = get()._returnViaDominateIn
def _typeRefViaDominateIn: Iterator[TypeRef] = get()._typeRefViaDominateIn
override def _dominateIn: JIterator[StoredNode] = get()._dominateIn
def _returnViaAstIn: Iterator[Return] = get()._returnViaAstIn
def _blockViaAstIn: Iterator[Block] = get()._blockViaAstIn
def _unknownViaAstIn: Iterator[Unknown] = get()._unknownViaAstIn
def _controlStructureViaAstIn: Iterator[ControlStructure] = get()._controlStructureViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _controlStructureViaConditionIn: Iterator[ControlStructure] = get()._controlStructureViaConditionIn
override def _conditionIn: JIterator[StoredNode] = get()._conditionIn
def _typeRefViaCdgIn: Iterator[TypeRef] = get()._typeRefViaCdgIn
def _unknownViaCdgIn: Iterator[Unknown] = get()._unknownViaCdgIn
def _callViaCdgIn: Iterator[Call] = get()._callViaCdgIn
def _blockViaCdgIn: Iterator[Block] = get()._blockViaCdgIn
def _literalViaCdgIn: Iterator[Literal] = get()._literalViaCdgIn
def _fieldIdentifierViaCdgIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaCdgIn
def _jumpTargetViaCdgIn: Iterator[JumpTarget] = get()._jumpTargetViaCdgIn
def _controlStructureViaCdgIn: Iterator[ControlStructure] = get()._controlStructureViaCdgIn
def _identifierViaCdgIn: Iterator[Identifier] = get()._identifierViaCdgIn
def _methodRefViaCdgIn: Iterator[MethodRef] = get()._methodRefViaCdgIn
override def _cdgIn: JIterator[StoredNode] = get()._cdgIn
def _methodViaContainsIn: Iterator[Method] = get()._methodViaContainsIn
override def _containsIn: JIterator[StoredNode] = get()._containsIn
def _blockViaCfgIn: Iterator[Block] = get()._blockViaCfgIn
def _controlStructureViaCfgIn: Iterator[ControlStructure] = get()._controlStructureViaCfgIn
def _jumpTargetViaCfgIn: Iterator[JumpTarget] = get()._jumpTargetViaCfgIn
def _typeRefViaCfgIn: Iterator[TypeRef] = get()._typeRefViaCfgIn
def _identifierViaCfgIn: Iterator[Identifier] = get()._identifierViaCfgIn
def _methodViaCfgIn: Iterator[Method] = get()._methodViaCfgIn
def _fieldIdentifierViaCfgIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaCfgIn
def _methodRefViaCfgIn: Iterator[MethodRef] = get()._methodRefViaCfgIn
def _literalViaCfgIn: Iterator[Literal] = get()._literalViaCfgIn
def _callViaCfgIn: Iterator[Call] = get()._callViaCfgIn
def _unknownViaCfgIn: Iterator[Unknown] = get()._unknownViaCfgIn
override def _cfgIn: JIterator[StoredNode] = get()._cfgIn
def _returnViaArgumentIn: Iterator[Return] = get()._returnViaArgumentIn
def _callViaArgumentIn: Iterator[Call] = get()._callViaArgumentIn
override def _argumentIn: JIterator[StoredNode] = get()._argumentIn
def _literalViaPostDominateIn: Iterator[Literal] = get()._literalViaPostDominateIn
def _jumpTargetViaPostDominateIn: Iterator[JumpTarget] = get()._jumpTargetViaPostDominateIn
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
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    JumpTarget.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "argumentIndex" 
case 2 => "code" 
case 3 => "columnNumber" 
case 4 => "internalFlags" 
case 5 => "lineNumber" 
case 6 => "name" 
case 7 => "order" 
case 8 => "parserTypeName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => argumentIndex
case 2 => code
case 3 => columnNumber
case 4 => internalFlags
case 5 => lineNumber
case 6 => name
case 7 => order
case 8 => parserTypeName
    }

  override def productPrefix = "JumpTarget"
  override def productArity = 9
}

class JumpTargetDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with CfgNode with AstNode with JumpTargetBase {

  override def layoutInformation: NodeLayoutInformation = JumpTarget.layoutInformation

private var _argumentIndex: java.lang.Integer = null
def argumentIndex: java.lang.Integer = _argumentIndex

private var _code: String = null
def code: String = _code

private var _columnNumber: Option[java.lang.Integer] = None
def columnNumber: Option[java.lang.Integer] = _columnNumber

private var _internalFlags: Option[java.lang.Integer] = None
def internalFlags: Option[java.lang.Integer] = _internalFlags

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _parserTypeName: String = null
def parserTypeName: String = _parserTypeName


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (argumentIndex != null) { properties.put("ARGUMENT_INDEX", argumentIndex) }
if (code != null) { properties.put("CODE", code) }
columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
internalFlags.map { value => properties.put("INTERNAL_FLAGS", value) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }
if (parserTypeName != null) { properties.put("PARSER_TYPE_NAME", parserTypeName) }

  properties
}

  def _typeRefViaCdgOut: Iterator[TypeRef] = _cdgOut.asScala.collect { case node: TypeRef => node }
def _unknownViaCdgOut: Iterator[Unknown] = _cdgOut.asScala.collect { case node: Unknown => node }
def _callViaCdgOut: Iterator[Call] = _cdgOut.asScala.collect { case node: Call => node }
def _blockViaCdgOut: Iterator[Block] = _cdgOut.asScala.collect { case node: Block => node }
def _literalViaCdgOut: Iterator[Literal] = _cdgOut.asScala.collect { case node: Literal => node }
def _fieldIdentifierViaCdgOut: Iterator[FieldIdentifier] = _cdgOut.asScala.collect { case node: FieldIdentifier => node }
def _jumpTargetViaCdgOut: Iterator[JumpTarget] = _cdgOut.asScala.collect { case node: JumpTarget => node }
def _returnViaCdgOut: Iterator[Return] = _cdgOut.asScala.collect { case node: Return => node }
def _controlStructureViaCdgOut: Iterator[ControlStructure] = _cdgOut.asScala.collect { case node: ControlStructure => node }
def _identifierViaCdgOut: Iterator[Identifier] = _cdgOut.asScala.collect { case node: Identifier => node }
def _methodReturnViaCdgOut: Iterator[MethodReturn] = _cdgOut.asScala.collect { case node: MethodReturn => node }
def _methodRefViaCdgOut: Iterator[MethodRef] = _cdgOut.asScala.collect { case node: MethodRef => node }
override def _cdgOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _blockViaCfgOut: Iterator[Block] = _cfgOut.asScala.collect { case node: Block => node }
def _controlStructureViaCfgOut: Iterator[ControlStructure] = _cfgOut.asScala.collect { case node: ControlStructure => node }
def _returnViaCfgOut: Iterator[Return] = _cfgOut.asScala.collect { case node: Return => node }
def _jumpTargetViaCfgOut: Iterator[JumpTarget] = _cfgOut.asScala.collect { case node: JumpTarget => node }
def _typeRefViaCfgOut: Iterator[TypeRef] = _cfgOut.asScala.collect { case node: TypeRef => node }
def _identifierViaCfgOut: Iterator[Identifier] = _cfgOut.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaCfgOut: Iterator[FieldIdentifier] = _cfgOut.asScala.collect { case node: FieldIdentifier => node }
def _methodRefViaCfgOut: Iterator[MethodRef] = _cfgOut.asScala.collect { case node: MethodRef => node }
def _literalViaCfgOut: Iterator[Literal] = _cfgOut.asScala.collect { case node: Literal => node }
def _callViaCfgOut: Iterator[Call] = _cfgOut.asScala.collect { case node: Call => node }
def _unknownViaCfgOut: Iterator[Unknown] = _cfgOut.asScala.collect { case node: Unknown => node }
override def _cfgOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _jumpTargetViaDominateOut: Iterator[JumpTarget] = _dominateOut.asScala.collect { case node: JumpTarget => node }
def _controlStructureViaDominateOut: Iterator[ControlStructure] = _dominateOut.asScala.collect { case node: ControlStructure => node }
def _identifierViaDominateOut: Iterator[Identifier] = _dominateOut.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaDominateOut: Iterator[FieldIdentifier] = _dominateOut.asScala.collect { case node: FieldIdentifier => node }
def _blockViaDominateOut: Iterator[Block] = _dominateOut.asScala.collect { case node: Block => node }
def _callViaDominateOut: Iterator[Call] = _dominateOut.asScala.collect { case node: Call => node }
def _methodRefViaDominateOut: Iterator[MethodRef] = _dominateOut.asScala.collect { case node: MethodRef => node }
def _literalViaDominateOut: Iterator[Literal] = _dominateOut.asScala.collect { case node: Literal => node }
def _unknownViaDominateOut: Iterator[Unknown] = _dominateOut.asScala.collect { case node: Unknown => node }
def _returnViaDominateOut: Iterator[Return] = _dominateOut.asScala.collect { case node: Return => node }
def _typeRefViaDominateOut: Iterator[TypeRef] = _dominateOut.asScala.collect { case node: TypeRef => node }
override def _dominateOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _literalViaPostDominateOut: Iterator[Literal] = _postDominateOut.asScala.collect { case node: Literal => node }
def _jumpTargetViaPostDominateOut: Iterator[JumpTarget] = _postDominateOut.asScala.collect { case node: JumpTarget => node }
def _typeRefViaPostDominateOut: Iterator[TypeRef] = _postDominateOut.asScala.collect { case node: TypeRef => node }
def _unknownViaPostDominateOut: Iterator[Unknown] = _postDominateOut.asScala.collect { case node: Unknown => node }
def _callViaPostDominateOut: Iterator[Call] = _postDominateOut.asScala.collect { case node: Call => node }
def _identifierViaPostDominateOut: Iterator[Identifier] = _postDominateOut.asScala.collect { case node: Identifier => node }
def _methodRefViaPostDominateOut: Iterator[MethodRef] = _postDominateOut.asScala.collect { case node: MethodRef => node }
def _blockViaPostDominateOut: Iterator[Block] = _postDominateOut.asScala.collect { case node: Block => node }
def _fieldIdentifierViaPostDominateOut: Iterator[FieldIdentifier] = _postDominateOut.asScala.collect { case node: FieldIdentifier => node }
def _controlStructureViaPostDominateOut: Iterator[ControlStructure] = _postDominateOut.asScala.collect { case node: ControlStructure => node }
def _returnViaPostDominateOut: Iterator[Return] = _postDominateOut.asScala.collect { case node: Return => node }
override def _postDominateOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _jumpTargetViaDominateIn: Iterator[JumpTarget] = _dominateIn.asScala.collect { case node: JumpTarget => node }
def _controlStructureViaDominateIn: Iterator[ControlStructure] = _dominateIn.asScala.collect { case node: ControlStructure => node }
def _identifierViaDominateIn: Iterator[Identifier] = _dominateIn.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaDominateIn: Iterator[FieldIdentifier] = _dominateIn.asScala.collect { case node: FieldIdentifier => node }
def _blockViaDominateIn: Iterator[Block] = _dominateIn.asScala.collect { case node: Block => node }
def _callViaDominateIn: Iterator[Call] = _dominateIn.asScala.collect { case node: Call => node }
def _methodRefViaDominateIn: Iterator[MethodRef] = _dominateIn.asScala.collect { case node: MethodRef => node }
def _literalViaDominateIn: Iterator[Literal] = _dominateIn.asScala.collect { case node: Literal => node }
def _unknownViaDominateIn: Iterator[Unknown] = _dominateIn.asScala.collect { case node: Unknown => node }
def _returnViaDominateIn: Iterator[Return] = _dominateIn.asScala.collect { case node: Return => node }
def _typeRefViaDominateIn: Iterator[TypeRef] = _dominateIn.asScala.collect { case node: TypeRef => node }
override def _dominateIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _returnViaAstIn: Iterator[Return] = _astIn.asScala.collect { case node: Return => node }
def _blockViaAstIn: Iterator[Block] = _astIn.asScala.collect { case node: Block => node }
def _unknownViaAstIn: Iterator[Unknown] = _astIn.asScala.collect { case node: Unknown => node }
def _controlStructureViaAstIn: Iterator[ControlStructure] = _astIn.asScala.collect { case node: ControlStructure => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]
def _controlStructureViaConditionIn: Iterator[ControlStructure] = _conditionIn.asScala.collect { case node: ControlStructure => node }
override def _conditionIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(6).asInstanceOf[JIterator[StoredNode]]
def _typeRefViaCdgIn: Iterator[TypeRef] = _cdgIn.asScala.collect { case node: TypeRef => node }
def _unknownViaCdgIn: Iterator[Unknown] = _cdgIn.asScala.collect { case node: Unknown => node }
def _callViaCdgIn: Iterator[Call] = _cdgIn.asScala.collect { case node: Call => node }
def _blockViaCdgIn: Iterator[Block] = _cdgIn.asScala.collect { case node: Block => node }
def _literalViaCdgIn: Iterator[Literal] = _cdgIn.asScala.collect { case node: Literal => node }
def _fieldIdentifierViaCdgIn: Iterator[FieldIdentifier] = _cdgIn.asScala.collect { case node: FieldIdentifier => node }
def _jumpTargetViaCdgIn: Iterator[JumpTarget] = _cdgIn.asScala.collect { case node: JumpTarget => node }
def _controlStructureViaCdgIn: Iterator[ControlStructure] = _cdgIn.asScala.collect { case node: ControlStructure => node }
def _identifierViaCdgIn: Iterator[Identifier] = _cdgIn.asScala.collect { case node: Identifier => node }
def _methodRefViaCdgIn: Iterator[MethodRef] = _cdgIn.asScala.collect { case node: MethodRef => node }
override def _cdgIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(7).asInstanceOf[JIterator[StoredNode]]
def _methodViaContainsIn: Iterator[Method] = _containsIn.asScala.collect { case node: Method => node }
override def _containsIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(8).asInstanceOf[JIterator[StoredNode]]
def _blockViaCfgIn: Iterator[Block] = _cfgIn.asScala.collect { case node: Block => node }
def _controlStructureViaCfgIn: Iterator[ControlStructure] = _cfgIn.asScala.collect { case node: ControlStructure => node }
def _jumpTargetViaCfgIn: Iterator[JumpTarget] = _cfgIn.asScala.collect { case node: JumpTarget => node }
def _typeRefViaCfgIn: Iterator[TypeRef] = _cfgIn.asScala.collect { case node: TypeRef => node }
def _identifierViaCfgIn: Iterator[Identifier] = _cfgIn.asScala.collect { case node: Identifier => node }
def _methodViaCfgIn: Iterator[Method] = _cfgIn.asScala.collect { case node: Method => node }
def _fieldIdentifierViaCfgIn: Iterator[FieldIdentifier] = _cfgIn.asScala.collect { case node: FieldIdentifier => node }
def _methodRefViaCfgIn: Iterator[MethodRef] = _cfgIn.asScala.collect { case node: MethodRef => node }
def _literalViaCfgIn: Iterator[Literal] = _cfgIn.asScala.collect { case node: Literal => node }
def _callViaCfgIn: Iterator[Call] = _cfgIn.asScala.collect { case node: Call => node }
def _unknownViaCfgIn: Iterator[Unknown] = _cfgIn.asScala.collect { case node: Unknown => node }
override def _cfgIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(9).asInstanceOf[JIterator[StoredNode]]
def _returnViaArgumentIn: Iterator[Return] = _argumentIn.asScala.collect { case node: Return => node }
def _callViaArgumentIn: Iterator[Call] = _argumentIn.asScala.collect { case node: Call => node }
override def _argumentIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(10).asInstanceOf[JIterator[StoredNode]]
def _literalViaPostDominateIn: Iterator[Literal] = _postDominateIn.asScala.collect { case node: Literal => node }
def _jumpTargetViaPostDominateIn: Iterator[JumpTarget] = _postDominateIn.asScala.collect { case node: JumpTarget => node }
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

  override def label: String = {
    JumpTarget.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "argumentIndex" 
case 2 => "code" 
case 3 => "columnNumber" 
case 4 => "internalFlags" 
case 5 => "lineNumber" 
case 6 => "name" 
case 7 => "order" 
case 8 => "parserTypeName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => argumentIndex
case 2 => code
case 3 => columnNumber
case 4 => internalFlags
case 5 => lineNumber
case 6 => name
case 7 => order
case 8 => parserTypeName
    }

  override def productPrefix = "JumpTarget"
  override def productArity = 9

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[JumpTargetDb]

  override def property(key:String): AnyRef = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "CODE" => this._code
      case "COLUMN_NUMBER" => this._columnNumber.orNull
      case "INTERNAL_FLAGS" => this._internalFlags.orNull
      case "LINE_NUMBER" => this._lineNumber.orNull
      case "NAME" => this._name
      case "ORDER" => this._order
      case "PARSER_TYPE_NAME" => this._parserTypeName
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[java.lang.Integer]
      case "CODE" => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "INTERNAL_FLAGS" => this._internalFlags = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "PARSER_TYPE_NAME" => this._parserTypeName = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewJumpTarget]
   this._argumentIndex = other.argumentIndex
   this._code = other.code
   this._columnNumber = if(other.columnNumber != null) other.columnNumber else None
   this._internalFlags = if(other.internalFlags != null) other.internalFlags else None
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None
   this._name = other.name
   this._order = other.order
   this._parserTypeName = other.parserTypeName


}

}

/** Traversal steps for JumpTarget */
class JumpTargetTraversal[NodeType <: JumpTarget](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to argumentIndex property */
  def argumentIndex: Traversal[java.lang.Integer] =
    traversal.map(_.argumentIndex)

    /**
    * Traverse to nodes where the argumentIndex equals the given `value`
    * */
  def argumentIndex(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex == value}

  /**
    * Traverse to nodes where the argumentIndex equals at least one of the given `values`
    * */
  def argumentIndex(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => vset.contains(node.argumentIndex)}
  }

  /**
    * Traverse to nodes where the argumentIndex is greater than the given `value`
    * */
  def argumentIndexGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex > value}

  /**
    * Traverse to nodes where the argumentIndex is greater than or equal the given `value`
    * */
  def argumentIndexGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex >= value}

  /**
    * Traverse to nodes where the argumentIndex is less than the given `value`
    * */
  def argumentIndexLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex < value}

  /**
    * Traverse to nodes where the argumentIndex is less than or equal the given `value`
    * */
  def argumentIndexLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex <= value}

  /**
    * Traverse to nodes where argumentIndex is not equal to the given `value`.
    * */
  def argumentIndexNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.argumentIndex != value}

  /**
    * Traverse to nodes where argumentIndex is not equal to any of the given `values`.
    * */
  def argumentIndexNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !vset.contains(node.argumentIndex)}
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


  /** Traverse to parserTypeName property */
  def parserTypeName: Traversal[String] =
    traversal.map(_.parserTypeName)

    /**
    * Traverse to nodes where the parserTypeName matches the regular expression `value`
    * */
  def parserTypeName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.parserTypeName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.parserTypeName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the parserTypeName matches at least one of the regular expressions in `values`
    * */
  def parserTypeName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.parserTypeName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where parserTypeName matches `value` exactly.
    * */
  def parserTypeNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.parserTypeName == value}

  /**
    * Traverse to nodes where parserTypeName matches one of the elements in `values` exactly.
    * */
  def parserTypeNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.parserTypeName)}
  }

  /**
    * Traverse to nodes where parserTypeName does not match the regular expression `value`.
    * */
  def parserTypeNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.parserTypeName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.parserTypeName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where parserTypeName does not match any of the regular expressions in `values`.
    * */
  def parserTypeNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.parserTypeName); matcher.matches()}}
   }




}
