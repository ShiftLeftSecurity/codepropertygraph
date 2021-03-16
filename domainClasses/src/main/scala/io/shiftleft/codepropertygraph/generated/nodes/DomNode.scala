package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object DomNode {
  def apply(graph: Graph, id: Long) = new DomNode(graph, id)

  val Label = "DOM_NODE"

  object PropertyNames {
    val Name = "NAME" 
    val Attributes = "attributes" 
    val all: Set[String] = Set(Name, Attributes)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Name = new PropertyKey[String]("NAME") 
    val Attributes = new PropertyKey[Seq[DomAttribute]]("attributes") 
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ast.layoutInformation).asJava,
    List(edges.Contains.layoutInformation, edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST")
    val In: Array[String] = Array("AST","CONTAINS")
  }

  val factory = new NodeFactory[DomNodeDb] {
    override val forLabel = DomNode.Label

    override def createNode(ref: NodeRef[DomNodeDb]) =
      new DomNodeDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = DomNode(graph, id)
  }
}

trait DomNodeBase extends CpgNode  with HasName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def attributes: Seq[DomAttributeBase]
}

class DomNode(graph: Graph, id: Long) extends NodeRef[DomNodeDb](graph, id)
  with DomNodeBase
  with StoredNode
   {
    override def name: String = get().name
    def attributes: Seq[DomAttribute] = get().attributes
  def _domNodeViaAstOut: Iterator[DomNode] = get()._domNodeViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _configFileViaContainsIn: Iterator[ConfigFile] = get()._configFileViaContainsIn
override def _containsIn: JIterator[StoredNode] = get()._containsIn
def _domNodeViaAstIn: Iterator[DomNode] = get()._domNodeViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    DomNode.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "attributes" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => attributes
    }

  override def productPrefix = "DomNode"
  override def productArity = 3
}

class DomNodeDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with DomNodeBase {

  override def layoutInformation: NodeLayoutInformation = DomNode.layoutInformation

private var _name: String = null
def name: String = _name

private var _attributes: Seq[DomAttribute] = Seq.empty
def attributes: Seq[DomAttribute] = this._attributes


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (name != null) { properties.put("NAME", name) }
  if (this._attributes != null && this._attributes.nonEmpty) { properties.put("attributes", this.attributes.asJava) }
  properties
}

  def _domNodeViaAstOut: Iterator[DomNode] = _astOut.asScala.collect { case node: DomNode => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _configFileViaContainsIn: Iterator[ConfigFile] = _containsIn.asScala.collect { case node: ConfigFile => node }
override def _containsIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _domNodeViaAstIn: Iterator[DomNode] = _astIn.asScala.collect { case node: DomNode => node }
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    DomNode.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "attributes" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => attributes
    }

  override def productPrefix = "DomNode"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[DomNodeDb]

  override def property(key:String): AnyRef = {
    key match {
      case "NAME" => this._name
      case "attributes" => this._attributes
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "NAME" => this._name = value.asInstanceOf[String]
      case "attributes" => this._attributes = value match {
        case singleValue: DomAttribute => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[DomAttribute]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[DomAttribute]]
      }
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewDomNode]
   this._name = other.name
  this._attributes = if(other.attributes == null) Nil else other.attributes.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[DomAttribute]
    case oldNode:StoredNode => oldNode.asInstanceOf[DomAttribute]
    case _ => throw new MatchError("unreachable")
  }}

}

}

/** Traversal steps for DomNode */
class DomNodeTraversal[NodeType <: DomNode](val traversal: Traversal[NodeType]) extends AnyVal {

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




}
