package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Tags {
  def apply(graph: Graph, id: Long) = new Tags(graph, id)

  val Label = "TAGS"

  object PropertyNames {
    val Tags = "tags" 
    val all: Set[String] = Set(Tags)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
    val Tags = new PropertyKey[Seq[Tag]]("tags") 
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

  val factory = new NodeFactory[TagsDb] {
    override val forLabel = Tags.Label

    override def createNode(ref: NodeRef[TagsDb]) =
      new TagsDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Tags(graph, id)
  }
}

trait TagsBase extends CpgNode   {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def tags: Seq[TagBase]
}

class Tags(graph: Graph, id: Long) extends NodeRef[TagsDb](graph, id)
  with TagsBase
  with StoredNode
   {
  
    def tags: Seq[Tag] = get().tags
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Tags.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "tags" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => tags
    }

  override def productPrefix = "Tags"
  override def productArity = 2
}

class TagsDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with TagsBase {

  override def layoutInformation: NodeLayoutInformation = Tags.layoutInformation



private var _tags: Seq[Tag] = Seq.empty
def tags: Seq[Tag] = this._tags


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]

  if (this._tags != null && this._tags.nonEmpty) { properties.put("tags", this.tags.asJava) }
  properties
}

  

  override def label: String = {
    Tags.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "tags" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => tags
    }

  override def productPrefix = "Tags"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagsDb]

  override def property(key:String): AnyRef = {
    key match {
      
      case "tags" => this._tags
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
    
      case "tags" => this._tags = value match {
        case singleValue: Tag => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[Tag]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[Tag]]
      }
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewTags]

  this._tags = if(other.tags == null) Nil else other.tags.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[Tag]
    case oldNode:StoredNode => oldNode.asInstanceOf[Tag]
    case _ => throw new MatchError("unreachable")
  }}

}

}

/** Traversal steps for Tags */
class TagsTraversal[NodeType <: Tags](val traversal: Traversal[NodeType]) extends AnyVal {



}
