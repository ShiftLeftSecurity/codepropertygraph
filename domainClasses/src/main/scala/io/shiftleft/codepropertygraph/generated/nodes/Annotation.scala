package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Annotation {
  def apply(graph: Graph, id: Long) = new Annotation(graph, id)

  val Label = "ANNOTATION"

  object PropertyNames {
    val Code = "CODE" 
    val FullName = "FULL_NAME" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val all: Set[String] = Set(Code, FullName, Name, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ast.layoutInformation).asJava,
    List(edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST")
    val In: Array[String] = Array("AST")
  }

  val factory = new NodeFactory[AnnotationDb] {
    override val forLabel = Annotation.Label

    override def createNode(ref: NodeRef[AnnotationDb]) =
      new AnnotationDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Annotation(graph, id)
  }
}

trait AnnotationBase extends CpgNode with AstNodeBase with HasCode with HasFullName with HasName with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Annotation(graph: Graph, id: Long) extends NodeRef[AnnotationDb](graph, id)
  with AnnotationBase
  with StoredNode
  with AstNode {
    override def code: String = get().code
  override def fullName: String = get().fullName
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  
  def _annotationParameterAssignViaAstOut: Iterator[AnnotationParameterAssign] = get()._annotationParameterAssignViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _memberViaAstIn: Iterator[Member] = get()._memberViaAstIn
def _typeDeclViaAstIn: Iterator[TypeDecl] = get()._typeDeclViaAstIn
def _methodViaAstIn: Iterator[Method] = get()._methodViaAstIn
def _annotationParameterAssignViaAstIn: Iterator[AnnotationParameterAssign] = get()._annotationParameterAssignViaAstIn
def _methodParameterInViaAstIn: Iterator[MethodParameterIn] = get()._methodParameterInViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Annotation.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "fullName" 
case 3 => "name" 
case 4 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => fullName
case 3 => name
case 4 => order
    }

  override def productPrefix = "Annotation"
  override def productArity = 5
}

class AnnotationDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with AnnotationBase {

  override def layoutInformation: NodeLayoutInformation = Annotation.layoutInformation

private var _code: String = null
def code: String = _code

private var _fullName: String = null
def fullName: String = _fullName

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
if (fullName != null) { properties.put("FULL_NAME", fullName) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _annotationParameterAssignViaAstOut: Iterator[AnnotationParameterAssign] = _astOut.asScala.collect { case node: AnnotationParameterAssign => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _memberViaAstIn: Iterator[Member] = _astIn.asScala.collect { case node: Member => node }
def _typeDeclViaAstIn: Iterator[TypeDecl] = _astIn.asScala.collect { case node: TypeDecl => node }
def _methodViaAstIn: Iterator[Method] = _astIn.asScala.collect { case node: Method => node }
def _annotationParameterAssignViaAstIn: Iterator[AnnotationParameterAssign] = _astIn.asScala.collect { case node: AnnotationParameterAssign => node }
def _methodParameterInViaAstIn: Iterator[MethodParameterIn] = _astIn.asScala.collect { case node: MethodParameterIn => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Annotation.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "fullName" 
case 3 => "name" 
case 4 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => fullName
case 3 => name
case 4 => order
    }

  override def productPrefix = "Annotation"
  override def productArity = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[AnnotationDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "FULL_NAME" => this._fullName
      case "NAME" => this._name
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CODE" => this._code = value.asInstanceOf[String]
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewAnnotation]
   this._code = other.code
   this._fullName = other.fullName
   this._name = other.name
   this._order = other.order

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for Annotation */
class AnnotationTraversal[NodeType <: Annotation](val traversal: Traversal[NodeType]) extends AnyVal {

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



}
