package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object TypeArgument {
  def apply(graph: Graph, id: Long) = new TypeArgument(graph, id)

  val Label = "TYPE_ARGUMENT"

  object PropertyNames {
    val Order = "ORDER" 
    val all: Set[String] = Set(Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.BindsTo.layoutInformation, edges.Ref.layoutInformation).asJava,
    List(edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("BINDS_TO","REF")
    val In: Array[String] = Array("AST")
  }

  val factory = new NodeFactory[TypeArgumentDb] {
    override val forLabel = TypeArgument.Label

    override def createNode(ref: NodeRef[TypeArgumentDb]) =
      new TypeArgumentDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TypeArgument(graph, id)
  }
}

trait TypeArgumentBase extends CpgNode with AstNodeBase with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class TypeArgument(graph: Graph, id: Long) extends NodeRef[TypeArgumentDb](graph, id)
  with TypeArgumentBase
  with StoredNode
  with AstNode {
    override def order: java.lang.Integer = get().order
  
  def _typeParameterViaBindsToOut: Iterator[TypeParameter] = get()._typeParameterViaBindsToOut
override def _bindsToOut: JIterator[StoredNode] = get()._bindsToOut
def _typeViaRefOut: Iterator[Type] = get()._typeViaRefOut
override def _refOut: JIterator[StoredNode] = get()._refOut
def _methodInstViaAstIn: Iterator[MethodInst] = get()._methodInstViaAstIn
def _typeViaAstIn: Iterator[Type] = get()._typeViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    TypeArgument.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => order
    }

  override def productPrefix = "TypeArgument"
  override def productArity = 2
}

class TypeArgumentDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with TypeArgumentBase {

  override def layoutInformation: NodeLayoutInformation = TypeArgument.layoutInformation

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _typeParameterViaBindsToOut: Iterator[TypeParameter] = _bindsToOut.asScala.collect { case node: TypeParameter => node }
override def _bindsToOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeViaRefOut: Iterator[Type] = _refOut.asScala.collect { case node: Type => node }
override def _refOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _methodInstViaAstIn: Iterator[MethodInst] = _astIn.asScala.collect { case node: MethodInst => node }
def _typeViaAstIn: Iterator[Type] = _astIn.asScala.collect { case node: Type => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    TypeArgument.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => order
    }

  override def productPrefix = "TypeArgument"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeArgumentDb]

  override def property(key:String): AnyRef = {
    key match {
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewTypeArgument]
   this._order = other.order


}

}

/** Traversal steps for TypeArgument */
class TypeArgumentTraversal[NodeType <: TypeArgument](val traversal: Traversal[NodeType]) extends AnyVal {

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
