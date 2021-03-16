package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object AnnotationParameterAssign {
  def apply(graph: Graph, id: Long) = new AnnotationParameterAssign(graph, id)

  val Label = "ANNOTATION_PARAMETER_ASSIGN"

  object PropertyNames {
    val Code = "CODE" 
    val Order = "ORDER" 
    val all: Set[String] = Set(Code, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Code = new PropertyKey[String]("CODE") 
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

  val factory = new NodeFactory[AnnotationParameterAssignDb] {
    override val forLabel = AnnotationParameterAssign.Label

    override def createNode(ref: NodeRef[AnnotationParameterAssignDb]) =
      new AnnotationParameterAssignDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = AnnotationParameterAssign(graph, id)
  }
}

trait AnnotationParameterAssignBase extends CpgNode with AstNodeBase with HasCode with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class AnnotationParameterAssign(graph: Graph, id: Long) extends NodeRef[AnnotationParameterAssignDb](graph, id)
  with AnnotationParameterAssignBase
  with StoredNode
  with AstNode {
    override def code: String = get().code
  override def order: java.lang.Integer = get().order
  
  def _annotationParameterViaAstOut: Iterator[AnnotationParameter] = get()._annotationParameterViaAstOut
def _annotationViaAstOut: Iterator[Annotation] = get()._annotationViaAstOut
def _annotationLiteralViaAstOut: Iterator[AnnotationLiteral] = get()._annotationLiteralViaAstOut
def _arrayInitializerViaAstOut: Iterator[ArrayInitializer] = get()._arrayInitializerViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _annotationViaAstIn: Iterator[Annotation] = get()._annotationViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    AnnotationParameterAssign.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => order
    }

  override def productPrefix = "AnnotationParameterAssign"
  override def productArity = 3
}

class AnnotationParameterAssignDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with AnnotationParameterAssignBase {

  override def layoutInformation: NodeLayoutInformation = AnnotationParameterAssign.layoutInformation

private var _code: String = null
def code: String = _code

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (code != null) { properties.put("CODE", code) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _annotationParameterViaAstOut: Iterator[AnnotationParameter] = _astOut.asScala.collect { case node: AnnotationParameter => node }
def _annotationViaAstOut: Iterator[Annotation] = _astOut.asScala.collect { case node: Annotation => node }
def _annotationLiteralViaAstOut: Iterator[AnnotationLiteral] = _astOut.asScala.collect { case node: AnnotationLiteral => node }
def _arrayInitializerViaAstOut: Iterator[ArrayInitializer] = _astOut.asScala.collect { case node: ArrayInitializer => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _annotationViaAstIn: Iterator[Annotation] = _astIn.asScala.collect { case node: Annotation => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    AnnotationParameterAssign.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "code" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => code
case 2 => order
    }

  override def productPrefix = "AnnotationParameterAssign"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[AnnotationParameterAssignDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CODE" => this._code
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CODE" => this._code = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewAnnotationParameterAssign]
   this._code = other.code
   this._order = other.order


}

}

/** Traversal steps for AnnotationParameterAssign */
class AnnotationParameterAssignTraversal[NodeType <: AnnotationParameterAssign](val traversal: Traversal[NodeType]) extends AnyVal {

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
