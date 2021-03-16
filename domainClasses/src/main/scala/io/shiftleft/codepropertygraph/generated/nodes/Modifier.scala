package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Modifier {
  def apply(graph: Graph, id: Long) = new Modifier(graph, id)

  val Label = "MODIFIER"

  object PropertyNames {
    val ModifierType = "MODIFIER_TYPE" 
    val Order = "ORDER" 
    val all: Set[String] = Set(ModifierType, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val ModifierType = new PropertyKey[String]("MODIFIER_TYPE") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array("AST")
  }

  val factory = new NodeFactory[ModifierDb] {
    override val forLabel = Modifier.Label

    override def createNode(ref: NodeRef[ModifierDb]) =
      new ModifierDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Modifier(graph, id)
  }
}

trait ModifierBase extends CpgNode with AstNodeBase with HasModifierType with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Modifier(graph: Graph, id: Long) extends NodeRef[ModifierDb](graph, id)
  with ModifierBase
  with StoredNode
  with AstNode {
    override def modifierType: String = get().modifierType
  override def order: java.lang.Integer = get().order
  
  def _unknownViaAstIn: Iterator[Unknown] = get()._unknownViaAstIn
def _typeDeclViaAstIn: TypeDecl = get()._typeDeclViaAstIn
def _methodViaAstIn: Method = get()._methodViaAstIn
def _controlStructureViaAstIn: Iterator[ControlStructure] = get()._controlStructureViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Modifier.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "modifierType" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => modifierType
case 2 => order
    }

  override def productPrefix = "Modifier"
  override def productArity = 3
}

class ModifierDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with ModifierBase {

  override def layoutInformation: NodeLayoutInformation = Modifier.layoutInformation

private var _modifierType: String = null
def modifierType: String = _modifierType

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (modifierType != null) { properties.put("MODIFIER_TYPE", modifierType) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _unknownViaAstIn: Iterator[Unknown] = _astIn.asScala.collect { case node: Unknown => node }
def _typeDeclViaAstIn: TypeDecl = _astIn.asScala.collect { case node: TypeDecl => node }.next()
def _methodViaAstIn: Method = _astIn.asScala.collect { case node: Method => node }.next()
def _controlStructureViaAstIn: Iterator[ControlStructure] = _astIn.asScala.collect { case node: ControlStructure => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Modifier.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "modifierType" 
case 2 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => modifierType
case 2 => order
    }

  override def productPrefix = "Modifier"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ModifierDb]

  override def property(key:String): AnyRef = {
    key match {
      case "MODIFIER_TYPE" => this._modifierType
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "MODIFIER_TYPE" => this._modifierType = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewModifier]
   this._modifierType = other.modifierType
   this._order = other.order


}

}

/** Traversal steps for Modifier */
class ModifierTraversal[NodeType <: Modifier](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to modifierType property */
  def modifierType: Traversal[String] =
    traversal.map(_.modifierType)

    /**
    * Traverse to nodes where the modifierType matches the regular expression `value`
    * */
  def modifierType(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.modifierType == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.modifierType); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the modifierType matches at least one of the regular expressions in `values`
    * */
  def modifierType(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.modifierType); matcher.matches()}}
   }

  /**
    * Traverse to nodes where modifierType matches `value` exactly.
    * */
  def modifierTypeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.modifierType == value}

  /**
    * Traverse to nodes where modifierType matches one of the elements in `values` exactly.
    * */
  def modifierTypeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.modifierType)}
  }

  /**
    * Traverse to nodes where modifierType does not match the regular expression `value`.
    * */
  def modifierTypeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.modifierType != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.modifierType); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where modifierType does not match any of the regular expressions in `values`.
    * */
  def modifierTypeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.modifierType); matcher.matches()}}
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
