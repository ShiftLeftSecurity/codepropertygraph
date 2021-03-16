package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object MethodReturn {
  def apply(graph: Graph, id: Long) = new MethodReturn(graph, id)

  val Label = "METHOD_RETURN"

  object PropertyNames {
    val Code = "CODE" 
    val ColumnNumber = "COLUMN_NUMBER" 
    val DepthFirstOrder = "DEPTH_FIRST_ORDER" 
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME" 
    val EvaluationStrategy = "EVALUATION_STRATEGY" 
    val InternalFlags = "INTERNAL_FLAGS" 
    val LineNumber = "LINE_NUMBER" 
    val Order = "ORDER" 
    val TypeFullName = "TYPE_FULL_NAME" 
    val all: Set[String] = Set(Code, ColumnNumber, DepthFirstOrder, DynamicTypeHintFullName, EvaluationStrategy, InternalFlags, LineNumber, Order, TypeFullName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
    val ColumnNumber = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER") 
    val DepthFirstOrder = new PropertyKey[java.lang.Integer]("DEPTH_FIRST_ORDER") 
    val DynamicTypeHintFullName = new PropertyKey[Seq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME") 
    val EvaluationStrategy = new PropertyKey[String]("EVALUATION_STRATEGY") 
    val InternalFlags = new PropertyKey[java.lang.Integer]("INTERNAL_FLAGS") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val TypeFullName = new PropertyKey[String]("TYPE_FULL_NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.DynamicType.layoutInformation, edges.EvalType.layoutInformation, edges.TaggedBy.layoutInformation, edges.PostDominate.layoutInformation).asJava,
    List(edges.Ref.layoutInformation, edges.Dominate.layoutInformation, edges.Ast.layoutInformation, edges.Propagate.layoutInformation, edges.Cdg.layoutInformation, edges.Cfg.layoutInformation, edges.ReachingDef.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("DYNAMIC_TYPE","EVAL_TYPE","POST_DOMINATE","TAGGED_BY")
    val In: Array[String] = Array("AST","CDG","CFG","DOMINATE","PROPAGATE","REACHING_DEF","REF")
  }

  val factory = new NodeFactory[MethodReturnDb] {
    override val forLabel = MethodReturn.Label

    override def createNode(ref: NodeRef[MethodReturnDb]) =
      new MethodReturnDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodReturn(graph, id)
  }
}

trait MethodReturnBase extends CpgNode with TrackingPointBase with CfgNodeBase with HasCode with HasColumnNumber with HasDepthFirstOrder with HasDynamicTypeHintFullName with HasEvaluationStrategy with HasInternalFlags with HasLineNumber with HasOrder with HasTypeFullName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class MethodReturn(graph: Graph, id: Long) extends NodeRef[MethodReturnDb](graph, id)
  with MethodReturnBase
  with StoredNode
  with TrackingPoint with CfgNode {
    override def code: String = get().code
  override def columnNumber: Option[java.lang.Integer] = get().columnNumber
  override def depthFirstOrder: Option[java.lang.Integer] = get().depthFirstOrder
  override def dynamicTypeHintFullName: List[String] = get().dynamicTypeHintFullName
  override def evaluationStrategy: String = get().evaluationStrategy
  override def internalFlags: Option[java.lang.Integer] = get().internalFlags
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  override def order: java.lang.Integer = get().order
  override def typeFullName: String = get().typeFullName
  
  def _typeDeclViaDynamicTypeOut: Iterator[TypeDecl] = get()._typeDeclViaDynamicTypeOut
def _methodViaDynamicTypeOut: Option[Method] = get()._methodViaDynamicTypeOut
override def _dynamicTypeOut: JIterator[StoredNode] = get()._dynamicTypeOut
def _typeViaEvalTypeOut: Iterator[Type] = get()._typeViaEvalTypeOut
override def _evalTypeOut: JIterator[StoredNode] = get()._evalTypeOut
def _tagViaTaggedByOut: Iterator[Tag] = get()._tagViaTaggedByOut
override def _taggedByOut: JIterator[StoredNode] = get()._taggedByOut
def _literalViaPostDominateOut: Iterator[Literal] = get()._literalViaPostDominateOut
def _identifierViaPostDominateOut: Iterator[Identifier] = get()._identifierViaPostDominateOut
def _jumpTargetViaPostDominateOut: Iterator[JumpTarget] = get()._jumpTargetViaPostDominateOut
def _methodRefViaPostDominateOut: Iterator[MethodRef] = get()._methodRefViaPostDominateOut
def _typeRefViaPostDominateOut: Iterator[TypeRef] = get()._typeRefViaPostDominateOut
def _blockViaPostDominateOut: Iterator[Block] = get()._blockViaPostDominateOut
def _unknownViaPostDominateOut: Iterator[Unknown] = get()._unknownViaPostDominateOut
def _fieldIdentifierViaPostDominateOut: Iterator[FieldIdentifier] = get()._fieldIdentifierViaPostDominateOut
def _controlStructureViaPostDominateOut: Iterator[ControlStructure] = get()._controlStructureViaPostDominateOut
def _callViaPostDominateOut: Iterator[Call] = get()._callViaPostDominateOut
def _returnViaPostDominateOut: Iterator[Return] = get()._returnViaPostDominateOut
def _methodViaPostDominateOut: Iterator[Method] = get()._methodViaPostDominateOut
override def _postDominateOut: JIterator[StoredNode] = get()._postDominateOut
def _postExecutionCallViaRefIn: Iterator[PostExecutionCall] = get()._postExecutionCallViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
def _controlStructureViaDominateIn: Iterator[ControlStructure] = get()._controlStructureViaDominateIn
def _methodViaDominateIn: Iterator[Method] = get()._methodViaDominateIn
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
def _methodViaAstIn: Method = get()._methodViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _methodParameterInViaPropagateIn: Iterator[MethodParameterIn] = get()._methodParameterInViaPropagateIn
override def _propagateIn: JIterator[StoredNode] = get()._propagateIn
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
def _returnViaCfgIn: Option[Return] = get()._returnViaCfgIn
def _methodViaCfgIn: Option[Method] = get()._methodViaCfgIn
def _typeRefViaCfgIn: Iterator[TypeRef] = get()._typeRefViaCfgIn
def _identifierViaCfgIn: Iterator[Identifier] = get()._identifierViaCfgIn
def _methodRefViaCfgIn: Iterator[MethodRef] = get()._methodRefViaCfgIn
override def _cfgIn: JIterator[StoredNode] = get()._cfgIn
def _returnViaReachingDefIn: Iterator[Return] = get()._returnViaReachingDefIn
override def _reachingDefIn: JIterator[StoredNode] = get()._reachingDefIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    MethodReturn.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "columnNumber" 
case 3 => "depthFirstOrder" 
case 4 => "dynamicTypeHintFullName" 
case 5 => "evaluationStrategy" 
case 6 => "internalFlags" 
case 7 => "lineNumber" 
case 8 => "order" 
case 9 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => columnNumber
case 3 => depthFirstOrder
case 4 => dynamicTypeHintFullName
case 5 => evaluationStrategy
case 6 => internalFlags
case 7 => lineNumber
case 8 => order
case 9 => typeFullName
    }

  override def productPrefix = "MethodReturn"
  override def productArity = 10
}

class MethodReturnDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with TrackingPoint with CfgNode with MethodReturnBase {

  override def layoutInformation: NodeLayoutInformation = MethodReturn.layoutInformation

private var _code: String = null
def code: String = _code

private var _columnNumber: Option[java.lang.Integer] = None
def columnNumber: Option[java.lang.Integer] = _columnNumber

private var _depthFirstOrder: Option[java.lang.Integer] = None
def depthFirstOrder: Option[java.lang.Integer] = _depthFirstOrder

private var _dynamicTypeHintFullName: List[String] = Nil
def dynamicTypeHintFullName: List[String] = _dynamicTypeHintFullName

private var _evaluationStrategy: String = null
def evaluationStrategy: String = _evaluationStrategy

private var _internalFlags: Option[java.lang.Integer] = None
def internalFlags: Option[java.lang.Integer] = _internalFlags

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _typeFullName: String = null
def typeFullName: String = _typeFullName


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
depthFirstOrder.map { value => properties.put("DEPTH_FIRST_ORDER", value) }
if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) { properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName.asJava) }
if (evaluationStrategy != null) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }
internalFlags.map { value => properties.put("INTERNAL_FLAGS", value) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
if (order != null) { properties.put("ORDER", order) }
if (typeFullName != null) { properties.put("TYPE_FULL_NAME", typeFullName) }

  properties
}

  def _typeDeclViaDynamicTypeOut: Iterator[TypeDecl] = _dynamicTypeOut.asScala.collect { case node: TypeDecl => node }
def _methodViaDynamicTypeOut: Option[Method] = _dynamicTypeOut.asScala.collect { case node: Method => node }.nextOption()
override def _dynamicTypeOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeViaEvalTypeOut: Iterator[Type] = _evalTypeOut.asScala.collect { case node: Type => node }
override def _evalTypeOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _tagViaTaggedByOut: Iterator[Tag] = _taggedByOut.asScala.collect { case node: Tag => node }
override def _taggedByOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _literalViaPostDominateOut: Iterator[Literal] = _postDominateOut.asScala.collect { case node: Literal => node }
def _identifierViaPostDominateOut: Iterator[Identifier] = _postDominateOut.asScala.collect { case node: Identifier => node }
def _jumpTargetViaPostDominateOut: Iterator[JumpTarget] = _postDominateOut.asScala.collect { case node: JumpTarget => node }
def _methodRefViaPostDominateOut: Iterator[MethodRef] = _postDominateOut.asScala.collect { case node: MethodRef => node }
def _typeRefViaPostDominateOut: Iterator[TypeRef] = _postDominateOut.asScala.collect { case node: TypeRef => node }
def _blockViaPostDominateOut: Iterator[Block] = _postDominateOut.asScala.collect { case node: Block => node }
def _unknownViaPostDominateOut: Iterator[Unknown] = _postDominateOut.asScala.collect { case node: Unknown => node }
def _fieldIdentifierViaPostDominateOut: Iterator[FieldIdentifier] = _postDominateOut.asScala.collect { case node: FieldIdentifier => node }
def _controlStructureViaPostDominateOut: Iterator[ControlStructure] = _postDominateOut.asScala.collect { case node: ControlStructure => node }
def _callViaPostDominateOut: Iterator[Call] = _postDominateOut.asScala.collect { case node: Call => node }
def _returnViaPostDominateOut: Iterator[Return] = _postDominateOut.asScala.collect { case node: Return => node }
def _methodViaPostDominateOut: Iterator[Method] = _postDominateOut.asScala.collect { case node: Method => node }
override def _postDominateOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _postExecutionCallViaRefIn: Iterator[PostExecutionCall] = _refIn.asScala.collect { case node: PostExecutionCall => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _controlStructureViaDominateIn: Iterator[ControlStructure] = _dominateIn.asScala.collect { case node: ControlStructure => node }
def _methodViaDominateIn: Iterator[Method] = _dominateIn.asScala.collect { case node: Method => node }
def _identifierViaDominateIn: Iterator[Identifier] = _dominateIn.asScala.collect { case node: Identifier => node }
def _fieldIdentifierViaDominateIn: Iterator[FieldIdentifier] = _dominateIn.asScala.collect { case node: FieldIdentifier => node }
def _blockViaDominateIn: Iterator[Block] = _dominateIn.asScala.collect { case node: Block => node }
def _callViaDominateIn: Iterator[Call] = _dominateIn.asScala.collect { case node: Call => node }
def _methodRefViaDominateIn: Iterator[MethodRef] = _dominateIn.asScala.collect { case node: MethodRef => node }
def _literalViaDominateIn: Iterator[Literal] = _dominateIn.asScala.collect { case node: Literal => node }
def _unknownViaDominateIn: Iterator[Unknown] = _dominateIn.asScala.collect { case node: Unknown => node }
def _returnViaDominateIn: Iterator[Return] = _dominateIn.asScala.collect { case node: Return => node }
def _typeRefViaDominateIn: Iterator[TypeRef] = _dominateIn.asScala.collect { case node: TypeRef => node }
override def _dominateIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]
def _methodViaAstIn: Method = _astIn.asScala.collect { case node: Method => node }.next()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(6).asInstanceOf[JIterator[StoredNode]]
def _methodParameterInViaPropagateIn: Iterator[MethodParameterIn] = _propagateIn.asScala.collect { case node: MethodParameterIn => node }
override def _propagateIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(7).asInstanceOf[JIterator[StoredNode]]
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
override def _cdgIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(8).asInstanceOf[JIterator[StoredNode]]
def _returnViaCfgIn: Option[Return] = _cfgIn.asScala.collect { case node: Return => node }.nextOption()
def _methodViaCfgIn: Option[Method] = _cfgIn.asScala.collect { case node: Method => node }.nextOption()
def _typeRefViaCfgIn: Iterator[TypeRef] = _cfgIn.asScala.collect { case node: TypeRef => node }
def _identifierViaCfgIn: Iterator[Identifier] = _cfgIn.asScala.collect { case node: Identifier => node }
def _methodRefViaCfgIn: Iterator[MethodRef] = _cfgIn.asScala.collect { case node: MethodRef => node }
override def _cfgIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(9).asInstanceOf[JIterator[StoredNode]]
def _returnViaReachingDefIn: Iterator[Return] = _reachingDefIn.asScala.collect { case node: Return => node }
override def _reachingDefIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(10).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    MethodReturn.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "columnNumber" 
case 3 => "depthFirstOrder" 
case 4 => "dynamicTypeHintFullName" 
case 5 => "evaluationStrategy" 
case 6 => "internalFlags" 
case 7 => "lineNumber" 
case 8 => "order" 
case 9 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => columnNumber
case 3 => depthFirstOrder
case 4 => dynamicTypeHintFullName
case 5 => evaluationStrategy
case 6 => internalFlags
case 7 => lineNumber
case 8 => order
case 9 => typeFullName
    }

  override def productPrefix = "MethodReturn"
  override def productArity = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodReturnDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "COLUMN_NUMBER" => this._columnNumber.orNull
      case "DEPTH_FIRST_ORDER" => this._depthFirstOrder.orNull
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
      case "EVALUATION_STRATEGY" => this._evaluationStrategy
      case "INTERNAL_FLAGS" => this._internalFlags.orNull
      case "LINE_NUMBER" => this._lineNumber.orNull
      case "ORDER" => this._order
      case "TYPE_FULL_NAME" => this._typeFullName
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CODE" => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "DEPTH_FIRST_ORDER" => this._depthFirstOrder = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName = value match {
        case singleValue: String => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[String]]
      }
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
      case "INTERNAL_FLAGS" => this._internalFlags = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMethodReturn]
   this._code = other.code
   this._columnNumber = if(other.columnNumber != null) other.columnNumber else None
   this._depthFirstOrder = if(other.depthFirstOrder != null) other.depthFirstOrder else None
   this._dynamicTypeHintFullName = if(other.dynamicTypeHintFullName != null) other.dynamicTypeHintFullName else Nil
   this._evaluationStrategy = other.evaluationStrategy
   this._internalFlags = if(other.internalFlags != null) other.internalFlags else None
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None
   this._order = other.order
   this._typeFullName = other.typeFullName


}

}

/** Traversal steps for MethodReturn */
class MethodReturnTraversal[NodeType <: MethodReturn](val traversal: Traversal[NodeType]) extends AnyVal {

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


  /** Traverse to dynamicTypeHintFullName property */
  def dynamicTypeHintFullName: Traversal[String] =
    traversal.flatMap(_.dynamicTypeHintFullName)

  

  /** Traverse to evaluationStrategy property */
  def evaluationStrategy: Traversal[String] =
    traversal.map(_.evaluationStrategy)

    /**
    * Traverse to nodes where the evaluationStrategy matches the regular expression `value`
    * */
  def evaluationStrategy(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.evaluationStrategy == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.evaluationStrategy); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the evaluationStrategy matches at least one of the regular expressions in `values`
    * */
  def evaluationStrategy(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.evaluationStrategy); matcher.matches()}}
   }

  /**
    * Traverse to nodes where evaluationStrategy matches `value` exactly.
    * */
  def evaluationStrategyExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.evaluationStrategy == value}

  /**
    * Traverse to nodes where evaluationStrategy matches one of the elements in `values` exactly.
    * */
  def evaluationStrategyExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.evaluationStrategy)}
  }

  /**
    * Traverse to nodes where evaluationStrategy does not match the regular expression `value`.
    * */
  def evaluationStrategyNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.evaluationStrategy != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.evaluationStrategy); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where evaluationStrategy does not match any of the regular expressions in `values`.
    * */
  def evaluationStrategyNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.evaluationStrategy); matcher.matches()}}
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


  /** Traverse to typeFullName property */
  def typeFullName: Traversal[String] =
    traversal.map(_.typeFullName)

    /**
    * Traverse to nodes where the typeFullName matches the regular expression `value`
    * */
  def typeFullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeFullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeFullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the typeFullName matches at least one of the regular expressions in `values`
    * */
  def typeFullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.typeFullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where typeFullName matches `value` exactly.
    * */
  def typeFullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.typeFullName == value}

  /**
    * Traverse to nodes where typeFullName matches one of the elements in `values` exactly.
    * */
  def typeFullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.typeFullName)}
  }

  /**
    * Traverse to nodes where typeFullName does not match the regular expression `value`.
    * */
  def typeFullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.typeFullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.typeFullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where typeFullName does not match any of the regular expressions in `values`.
    * */
  def typeFullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.typeFullName); matcher.matches()}}
   }




}
