//package io.shiftleft.codepropertygraph.generated.nodes
//
//import overflowdb._
//import overflowdb.traversal.Traversal
//import scala.jdk.CollectionConverters._
//
//object TypeRef2 {
//  def apply(graph: Graph, id: Long) = new TypeRef2(graph, id)
//
//  val Label = "TYPE_REF"
//
//  object PropertyNames {
//    val ArgumentIndex = "ARGUMENT_INDEX"
//    val Code = "CODE"
//    val ColumnNumber = "COLUMN_NUMBER"
//    val LineNumber = "LINE_NUMBER"
//    val Order = "ORDER"
//    val TypeFullName = "TYPE_FULL_NAME"
//    val all: Set[String] = Set(ArgumentIndex, Code, ColumnNumber, LineNumber, Order, TypeFullName)
//    val allAsJava: java.util.Set[String] = all.asJava
//  }
//
//  object Properties {
//    val ArgumentIndex = new PropertyKey[java.lang.Integer]("ARGUMENT_INDEX")
//    val Code = new PropertyKey[String]("CODE")
//    val ColumnNumber = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER")
//    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER")
//    val Order = new PropertyKey[java.lang.Integer]("ORDER")
//    val TypeFullName = new PropertyKey[String]("TYPE_FULL_NAME")
//
//  }
//
//  val layoutInformation = new NodeLayoutInformation(
//    Label,
//    PropertyNames.allAsJava,
//    List(io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Capture.layoutInformation).asJava,
//    List(io.shiftleft.codepropertygraph.generated.edges.Cdg.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Receiver.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Condition.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
//io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation).asJava)
//
//
//  object Edges {
//    val Out: Array[String] = Array("CAPTURE","CDG","CFG","DOMINATE","EVAL_TYPE","POST_DOMINATE","TAGGED_BY")
//    val In: Array[String] = Array("ARGUMENT","AST","CDG","CFG","CONDITION","CONTAINS","DOMINATE","POST_DOMINATE","RECEIVER")
//  }
//
//  val factory = new NodeFactory[TypeRef2Db] {
//    override val forLabel = TypeRef2.Label
//
//    override def createNode(ref: NodeRef[TypeRef2Db]) =
//      new TypeRef2Db(ref.asInstanceOf[NodeRef[NodeDb]])
//
//    override def createNodeRef(graph: Graph, id: Long) = TypeRef2(graph, id)
//  }
//}
//
//trait TypeRef2Base extends AbstractNode with ExpressionBase with HasArgumentIndex with HasCode with HasColumnNumber with HasLineNumber with HasOrder with HasTypeFullName {
//  def asStored : StoredNode = this.asInstanceOf[StoredNode]
//
//
//}
//
//class TypeRef2(graph: Graph, id: Long) extends NodeRef[TypeRef2Db](graph, id)
//  with TypeRef2Base
//  with StoredNode
//  with Expression {
//
//def astIn = get().astIn
//override def _astIn = get()._astIn
//def _callViaAstIn: Traversal[Call] = get()._callViaAstIn
//def _blockViaAstIn: Traversal[Block] = get()._blockViaAstIn
//def _controlStructureViaAstIn: Traversal[ControlStructure] = get()._controlStructureViaAstIn
//def _returnViaAstIn: Traversal[Return] = get()._returnViaAstIn
//
//def cfgIn = get().cfgIn
//override def _cfgIn = get()._cfgIn
//def _jumpTargetViaCfgIn: Traversal[JumpTarget] = get()._jumpTargetViaCfgIn
//def _methodViaCfgIn: Traversal[Method] = get()._methodViaCfgIn
//def _identifierViaCfgIn: Traversal[Identifier] = get()._identifierViaCfgIn
//def _unknownViaCfgIn: Traversal[Unknown] = get()._unknownViaCfgIn
//def _literalViaCfgIn: Traversal[Literal] = get()._literalViaCfgIn
//def _fieldIdentifierViaCfgIn: Traversal[FieldIdentifier] = get()._fieldIdentifierViaCfgIn
//def _controlStructureViaCfgIn: Traversal[ControlStructure] = get()._controlStructureViaCfgIn
//def _blockViaCfgIn: Traversal[Block] = get()._blockViaCfgIn
//def _callViaCfgIn: Traversal[Call] = get()._callViaCfgIn
//def _methodRefViaCfgIn: Traversal[MethodRef] = get()._methodRefViaCfgIn
//def _typeRefViaCfgIn: Traversal[TypeRef2] = get()._typeRefViaCfgIn
//
//def conditionIn = get().conditionIn
//override def _conditionIn = get()._conditionIn
//def _controlStructureViaConditionIn: Traversal[ControlStructure] = get()._controlStructureViaConditionIn
//
//def containsIn = get().containsIn
//override def _containsIn = get()._containsIn
//def _methodViaContainsIn: Traversal[Method] = get()._methodViaContainsIn
//
//def argumentIn = get().argumentIn
//override def _argumentIn = get()._argumentIn
//def _returnViaArgumentIn: Traversal[Return] = get()._returnViaArgumentIn
//def _callViaArgumentIn: Traversal[Call] = get()._callViaArgumentIn
//
//
//  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
//  override def valueMap: java.util.Map[String, AnyRef] = get.valueMap
//  override def canEqual(that: Any): Boolean = get.canEqual(that)
//  override def label: String = {
//    TypeRef2.Label
//  }
//
//  override def productElementLabel(n: Int): String =
//    n match {
//      case 0 => "id"
//case 1 => "argumentIndex"
//case 2 => "code"
//case 3 => "columnNumber"
//case 4 => "lineNumber"
//case 5 => "order"
//case 6 => "typeFullName"
//    }
//
//  override def productElement(n: Int): Any =
//    n match {
//      case 0 => id
//case 1 => argumentIndex
//case 2 => code
//case 3 => columnNumber
//case 4 => lineNumber
//case 5 => order
//case 6 => typeFullName
//    }
//
//  override def productPrefix = "TypeRef2"
//  override def productArity = 7
//}
//
//class TypeRef2Db(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
//  with Expression with TypeRef2Base {
//
//  override def layoutInformation: NodeLayoutInformation = TypeRef2.layoutInformation
//
//private var _argumentIndex: java.lang.Integer = null
//def argumentIndex: java.lang.Integer = _argumentIndex
//
//private var _code: String = null
//def code: String = _code
//
//private var _columnNumber: Option[java.lang.Integer] = None
//def columnNumber: Option[java.lang.Integer] = _columnNumber
//
//private var _lineNumber: Option[java.lang.Integer] = None
//def lineNumber: Option[java.lang.Integer] = _lineNumber
//
//private var _order: java.lang.Integer = null
//def order: java.lang.Integer = _order
//
//private var _typeFullName: String = null
//def typeFullName: String = _typeFullName
//
//
//  /* all properties */
//  override def valueMap: java.util.Map[String, AnyRef] =  {
//  val properties = new java.util.HashMap[String, AnyRef]
//if (argumentIndex != null) { properties.put("ARGUMENT_INDEX", argumentIndex) }
//if (code != null) { properties.put("CODE", code) }
//columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
//lineNumber.map { value => properties.put("LINE_NUMBER", value) }
//if (order != null) { properties.put("ORDER", order) }
//if (typeFullName != null) { properties.put("TYPE_FULL_NAME", typeFullName) }
//
//  properties
//}
//
//def astIn: Traversal[Expression] = Traversal(createAdjacentNodeIteratorByOffSet[Expression](9))
//override def _astIn = createAdjacentNodeIteratorByOffSet[StoredNode](9)
//def _callViaAstIn: Traversal[Call] = astIn.collectAll[Call]
//def _blockViaAstIn: Traversal[Block] = astIn.collectAll[Block]
//def _controlStructureViaAstIn: Traversal[ControlStructure] = astIn.collectAll[ControlStructure]
//def _returnViaAstIn: Traversal[Return] = astIn.collectAll[Return]
//
//def cfgIn: Traversal[AstNode] = Traversal(createAdjacentNodeIteratorByOffSet[AstNode](12))
//override def _cfgIn = createAdjacentNodeIteratorByOffSet[StoredNode](12)
//def _jumpTargetViaCfgIn: Traversal[JumpTarget] = cfgIn.collectAll[JumpTarget]
//def _methodViaCfgIn: Traversal[Method] = cfgIn.collectAll[Method]
//def _identifierViaCfgIn: Traversal[Identifier] = cfgIn.collectAll[Identifier]
//def _unknownViaCfgIn: Traversal[Unknown] = cfgIn.collectAll[Unknown]
//def _literalViaCfgIn: Traversal[Literal] = cfgIn.collectAll[Literal]
//def _fieldIdentifierViaCfgIn: Traversal[FieldIdentifier] = cfgIn.collectAll[FieldIdentifier]
//def _controlStructureViaCfgIn: Traversal[ControlStructure] = cfgIn.collectAll[ControlStructure]
//def _blockViaCfgIn: Traversal[Block] = cfgIn.collectAll[Block]
//def _callViaCfgIn: Traversal[Call] = cfgIn.collectAll[Call]
//def _methodRefViaCfgIn: Traversal[MethodRef] = cfgIn.collectAll[MethodRef]
//def _typeRefViaCfgIn: Traversal[TypeRef2] = cfgIn.collectAll[TypeRef2]
//
//def conditionIn: Traversal[ControlStructure] = Traversal(createAdjacentNodeIteratorByOffSet[ControlStructure](13))
//override def _conditionIn = createAdjacentNodeIteratorByOffSet[StoredNode](13)
//def _controlStructureViaConditionIn: Traversal[ControlStructure] = conditionIn.collectAll[ControlStructure]
//
//def containsIn: Traversal[Method] = Traversal(createAdjacentNodeIteratorByOffSet[Method](14))
//override def _containsIn = createAdjacentNodeIteratorByOffSet[StoredNode](14)
//def _methodViaContainsIn: Traversal[Method] = containsIn.collectAll[Method]
//
//def argumentIn: Traversal[Expression] = Traversal(createAdjacentNodeIteratorByOffSet[Expression](15))
//override def _argumentIn = createAdjacentNodeIteratorByOffSet[StoredNode](15)
//def _returnViaArgumentIn: Traversal[Return] = argumentIn.collectAll[Return]
//def _callViaArgumentIn: Traversal[Call] = argumentIn.collectAll[Call]
//
//
//  override def label: String = {
//    TypeRef2.Label
//  }
//
//  override def productElementLabel(n: Int): String =
//    n match {
//      case 0 => "id"
//case 1 => "argumentIndex"
//case 2 => "code"
//case 3 => "columnNumber"
//case 4 => "lineNumber"
//case 5 => "order"
//case 6 => "typeFullName"
//    }
//
//  override def productElement(n: Int): Any =
//    n match {
//      case 0 => id
//case 1 => argumentIndex
//case 2 => code
//case 3 => columnNumber
//case 4 => lineNumber
//case 5 => order
//case 6 => typeFullName
//    }
//
//  override def productPrefix = "TypeRef2"
//  override def productArity = 7
//
//  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeRef2Db]
//
//  override def property(key:String): AnyRef = {
//    key match {
//      case "ARGUMENT_INDEX" => this._argumentIndex
//      case "CODE" => this._code
//      case "COLUMN_NUMBER" => this._columnNumber.orNull
//      case "LINE_NUMBER" => this._lineNumber.orNull
//      case "ORDER" => this._order
//      case "TYPE_FULL_NAME" => this._typeFullName
//
//      case _ => null
//    }
//  }
//
//  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
//    key match {
//      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[java.lang.Integer]
//      case "CODE" => this._code = value.asInstanceOf[String]
//      case "COLUMN_NUMBER" => this._columnNumber = value match {
//        case null | None => None
//        case someVal: java.lang.Integer => Some(someVal)
//      }
//      case "LINE_NUMBER" => this._lineNumber = value match {
//        case null | None => None
//        case someVal: java.lang.Integer => Some(someVal)
//      }
//      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
//      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]
//
//      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
//    }
//  }
//
//override def removeSpecificProperty(key: String): Unit =
//  this.updateSpecificProperty(key, null)
//
//override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
//  ???
//}
//
//}
//
