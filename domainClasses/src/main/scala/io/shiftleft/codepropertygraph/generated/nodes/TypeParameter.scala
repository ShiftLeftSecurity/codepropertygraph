package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object TypeParameter {
  def apply(graph: Graph, id: Long) = new TypeParameter(graph, id)

  val Label = "TYPE_PARAMETER"

  object PropertyNames {
    val Name = "NAME" 
    val Order = "ORDER" 
    val all: Set[String] = Set(Name, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(edges.BindsTo.layoutInformation, edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array("AST","BINDS_TO")
  }

  val factory = new NodeFactory[TypeParameterDb] {
    override val forLabel = TypeParameter.Label

    override def createNode(ref: NodeRef[TypeParameterDb]) =
      new TypeParameterDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TypeParameter(graph, id)
  }
}

trait TypeParameterBase extends CpgNode with AstNodeBase with HasName with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class TypeParameter(graph: Graph, id: Long) extends NodeRef[TypeParameterDb](graph, id)
  with TypeParameterBase
  with StoredNode
  with AstNode {
    override def name: String = get().name
  override def order: java.lang.Integer = get().order
  
  def _typeArgumentViaBindsToIn: Iterator[TypeArgument] = get()._typeArgumentViaBindsToIn
override def _bindsToIn: JIterator[StoredNode] = get()._bindsToIn
def _typeDeclViaAstIn: Iterator[TypeDecl] = get()._typeDeclViaAstIn
def _methodViaAstIn: Method = get()._methodViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    TypeParameter.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => order
    }

  override def productPrefix = "TypeParameter"
  override def productArity = 3
}

class TypeParameterDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with TypeParameterBase {

  override def layoutInformation: NodeLayoutInformation = TypeParameter.layoutInformation

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _typeArgumentViaBindsToIn: Iterator[TypeArgument] = _bindsToIn.asScala.collect { case node: TypeArgument => node }
override def _bindsToIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaAstIn: Iterator[TypeDecl] = _astIn.asScala.collect { case node: TypeDecl => node }
def _methodViaAstIn: Method = _astIn.asScala.collect { case node: Method => node }.next()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    TypeParameter.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => order
    }

  override def productPrefix = "TypeParameter"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeParameterDb]

  override def property(key:String): AnyRef = {
    key match {
      case "NAME" => this._name
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewTypeParameter]
   this._name = other.name
   this._order = other.order


}

}

/** Traversal steps for TypeParameter */
class TypeParameterTraversal[NodeType <: TypeParameter](val traversal: Traversal[NodeType]) extends AnyVal {

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
