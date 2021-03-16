package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object MethodParameterOut {
  def apply(graph: Graph, id: Long) = new MethodParameterOut(graph, id)

  val Label = "METHOD_PARAMETER_OUT"

  object PropertyNames {
    val Code = "CODE" 
    val ColumnNumber = "COLUMN_NUMBER" 
    val EvaluationStrategy = "EVALUATION_STRATEGY" 
    val LineNumber = "LINE_NUMBER" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val TypeFullName = "TYPE_FULL_NAME" 
    val all: Set[String] = Set(Code, ColumnNumber, EvaluationStrategy, LineNumber, Name, Order, TypeFullName)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
    val ColumnNumber = new PropertyKey[java.lang.Integer]("COLUMN_NUMBER") 
    val EvaluationStrategy = new PropertyKey[String]("EVALUATION_STRATEGY") 
    val LineNumber = new PropertyKey[java.lang.Integer]("LINE_NUMBER") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    val TypeFullName = new PropertyKey[String]("TYPE_FULL_NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.EvalType.layoutInformation, edges.TaggedBy.layoutInformation).asJava,
    List(edges.ParameterLink.layoutInformation, edges.Ref.layoutInformation, edges.Ast.layoutInformation, edges.Propagate.layoutInformation, edges.TaintRemove.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("EVAL_TYPE","TAGGED_BY")
    val In: Array[String] = Array("AST","PARAMETER_LINK","PROPAGATE","REF","TAINT_REMOVE")
  }

  val factory = new NodeFactory[MethodParameterOutDb] {
    override val forLabel = MethodParameterOut.Label

    override def createNode(ref: NodeRef[MethodParameterOutDb]) =
      new MethodParameterOutDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodParameterOut(graph, id)
  }
}

trait MethodParameterOutBase extends CpgNode with DeclarationBase with TrackingPointBase with AstNodeBase with HasCode with HasColumnNumber with HasEvaluationStrategy with HasLineNumber with HasName with HasOrder with HasTypeFullName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class MethodParameterOut(graph: Graph, id: Long) extends NodeRef[MethodParameterOutDb](graph, id)
  with MethodParameterOutBase
  with StoredNode
  with Declaration with TrackingPoint with AstNode {
    override def code: String = get().code
  override def columnNumber: Option[java.lang.Integer] = get().columnNumber
  override def evaluationStrategy: String = get().evaluationStrategy
  override def lineNumber: Option[java.lang.Integer] = get().lineNumber
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  override def typeFullName: String = get().typeFullName
  
  def _typeViaEvalTypeOut: Iterator[Type] = get()._typeViaEvalTypeOut
override def _evalTypeOut: JIterator[StoredNode] = get()._evalTypeOut
def _tagViaTaggedByOut: Iterator[Tag] = get()._tagViaTaggedByOut
override def _taggedByOut: JIterator[StoredNode] = get()._taggedByOut
def _methodParameterInViaParameterLinkIn: Iterator[MethodParameterIn] = get()._methodParameterInViaParameterLinkIn
override def _parameterLinkIn: JIterator[StoredNode] = get()._parameterLinkIn
def _postExecutionCallViaRefIn: Iterator[PostExecutionCall] = get()._postExecutionCallViaRefIn
override def _refIn: JIterator[StoredNode] = get()._refIn
def _methodViaAstIn: Iterator[Method] = get()._methodViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
def _methodParameterInViaPropagateIn: Iterator[MethodParameterIn] = get()._methodParameterInViaPropagateIn
override def _propagateIn: JIterator[StoredNode] = get()._propagateIn
def _methodParameterInViaTaintRemoveIn: Iterator[MethodParameterIn] = get()._methodParameterInViaTaintRemoveIn
override def _taintRemoveIn: JIterator[StoredNode] = get()._taintRemoveIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    MethodParameterOut.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "columnNumber" 
case 3 => "evaluationStrategy" 
case 4 => "lineNumber" 
case 5 => "name" 
case 6 => "order" 
case 7 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => columnNumber
case 3 => evaluationStrategy
case 4 => lineNumber
case 5 => name
case 6 => order
case 7 => typeFullName
    }

  override def productPrefix = "MethodParameterOut"
  override def productArity = 8
}

class MethodParameterOutDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with Declaration with TrackingPoint with AstNode with MethodParameterOutBase {

  override def layoutInformation: NodeLayoutInformation = MethodParameterOut.layoutInformation

private var _code: String = null
def code: String = _code

private var _columnNumber: Option[java.lang.Integer] = None
def columnNumber: Option[java.lang.Integer] = _columnNumber

private var _evaluationStrategy: String = null
def evaluationStrategy: String = _evaluationStrategy

private var _lineNumber: Option[java.lang.Integer] = None
def lineNumber: Option[java.lang.Integer] = _lineNumber

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order

private var _typeFullName: String = null
def typeFullName: String = _typeFullName


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
if (evaluationStrategy != null) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }
lineNumber.map { value => properties.put("LINE_NUMBER", value) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }
if (typeFullName != null) { properties.put("TYPE_FULL_NAME", typeFullName) }

  properties
}

  def _typeViaEvalTypeOut: Iterator[Type] = _evalTypeOut.asScala.collect { case node: Type => node }
override def _evalTypeOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _tagViaTaggedByOut: Iterator[Tag] = _taggedByOut.asScala.collect { case node: Tag => node }
override def _taggedByOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _methodParameterInViaParameterLinkIn: Iterator[MethodParameterIn] = _parameterLinkIn.asScala.collect { case node: MethodParameterIn => node }
override def _parameterLinkIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _postExecutionCallViaRefIn: Iterator[PostExecutionCall] = _refIn.asScala.collect { case node: PostExecutionCall => node }
override def _refIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]
def _methodViaAstIn: Iterator[Method] = _astIn.asScala.collect { case node: Method => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(4).asInstanceOf[JIterator[StoredNode]]
def _methodParameterInViaPropagateIn: Iterator[MethodParameterIn] = _propagateIn.asScala.collect { case node: MethodParameterIn => node }
override def _propagateIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(5).asInstanceOf[JIterator[StoredNode]]
def _methodParameterInViaTaintRemoveIn: Iterator[MethodParameterIn] = _taintRemoveIn.asScala.collect { case node: MethodParameterIn => node }
override def _taintRemoveIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(6).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    MethodParameterOut.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "columnNumber" 
case 3 => "evaluationStrategy" 
case 4 => "lineNumber" 
case 5 => "name" 
case 6 => "order" 
case 7 => "typeFullName" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => columnNumber
case 3 => evaluationStrategy
case 4 => lineNumber
case 5 => name
case 6 => order
case 7 => typeFullName
    }

  override def productPrefix = "MethodParameterOut"
  override def productArity = 8

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodParameterOutDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "COLUMN_NUMBER" => this._columnNumber.orNull
      case "EVALUATION_STRATEGY" => this._evaluationStrategy
      case "LINE_NUMBER" => this._lineNumber.orNull
      case "NAME" => this._name
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
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
      case "LINE_NUMBER" => this._lineNumber = value match {
        case null | None => None
        case someVal: java.lang.Integer => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMethodParameterOut]
   this._code = other.code
   this._columnNumber = if(other.columnNumber != null) other.columnNumber else None
   this._evaluationStrategy = other.evaluationStrategy
   this._lineNumber = if(other.lineNumber != null) other.lineNumber else None
   this._name = other.name
   this._order = other.order
   this._typeFullName = other.typeFullName


}

}

/** Traversal steps for MethodParameterOut */
class MethodParameterOutTraversal[NodeType <: MethodParameterOut](val traversal: Traversal[NodeType]) extends AnyVal {

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
