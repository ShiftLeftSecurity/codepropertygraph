package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object TagNodePair {
  def apply(graph: Graph, id: Long) = new TagNodePair(graph, id)

  val Label = "TAG_NODE_PAIR"

  object PropertyNames {
    val Tag = "tag" 
    val Node = "node" 
    val all: Set[String] = Set(Tag, Node)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
    val Tag = new PropertyKey[Tag]("tag") 
    val Node = new PropertyKey[CpgNode]("node") 
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List().asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array()
  }

  val factory = new NodeFactory[TagNodePairDb] {
    override val forLabel = TagNodePair.Label

    override def createNode(ref: NodeRef[TagNodePairDb]) =
      new TagNodePairDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TagNodePair(graph, id)
  }
}

trait TagNodePairBase extends CpgNode   {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def tag: TagBase
def node: CpgNode
}

class TagNodePair(graph: Graph, id: Long) extends NodeRef[TagNodePairDb](graph, id)
  with TagNodePairBase
  with StoredNode
   {
  
    def tag: Tag = get().tag
  def node: CpgNode = get().node
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    TagNodePair.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "tag" 
case 2 => "node" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => tag
case 2 => node
    }

  override def productPrefix = "TagNodePair"
  override def productArity = 3
}

class TagNodePairDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with TagNodePairBase {

  override def layoutInformation: NodeLayoutInformation = TagNodePair.layoutInformation



private var _tag: Tag = null
def tag: Tag = this._tag


private var _node: CpgNode = null
def node: CpgNode = this._node


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]

   if (this._tag != null) { properties.put("tag", this._tag) }
   if (this._node != null) { properties.put("node", this._node) }
  properties
}

  

  override def label: String = {
    TagNodePair.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "tag" 
case 2 => "node" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => tag
case 2 => node
    }

  override def productPrefix = "TagNodePair"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagNodePairDb]

  override def property(key:String): AnyRef = {
    key match {
      
      case "tag" => this._tag
      case "node" => this._node
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
    
      case "tag" => this._tag = value.asInstanceOf[Tag]
      case "node" => this._node = value.asInstanceOf[CpgNode]
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewTagNodePair]

  this._tag = other.tag match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[Tag]
    case oldNode: StoredNode => oldNode.asInstanceOf[Tag]
    case _ => throw new MatchError("unreachable")
  }
  this._node = other.node match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[CpgNode]
    case oldNode: StoredNode => oldNode.asInstanceOf[CpgNode]
    case _ => throw new MatchError("unreachable")
  }

}

}

/** Traversal steps for TagNodePair */
class TagNodePairTraversal[NodeType <: TagNodePair](val traversal: Traversal[NodeType]) extends AnyVal {



}
