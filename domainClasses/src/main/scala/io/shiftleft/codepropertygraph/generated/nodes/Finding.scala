package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Finding {
  def apply(graph: Graph, id: Long) = new Finding(graph, id)

  val Label = "FINDING"

  object PropertyNames {
    val Evidence = "evidence" 
    val Keyvaluepairs = "keyValuePairs" 
    val all: Set[String] = Set(Evidence, Keyvaluepairs)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
    val Evidence = new PropertyKey[Seq[CpgNode]]("evidence") 
    val Keyvaluepairs = new PropertyKey[Seq[KeyValuePair]]("keyValuePairs") 
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

  val factory = new NodeFactory[FindingDb] {
    override val forLabel = Finding.Label

    override def createNode(ref: NodeRef[FindingDb]) =
      new FindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Finding(graph, id)
  }
}

trait FindingBase extends CpgNode   {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def evidence: Seq[CpgNode]
def keyValuePairs: Seq[KeyValuePairBase]
}

class Finding(graph: Graph, id: Long) extends NodeRef[FindingDb](graph, id)
  with FindingBase
  with StoredNode
   {
  
    def evidence: Seq[CpgNode] = get().evidence
  def keyValuePairs: Seq[KeyValuePair] = get().keyValuePairs
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Finding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "evidence" 
case 2 => "keyValuePairs" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => evidence
case 2 => keyValuePairs
    }

  override def productPrefix = "Finding"
  override def productArity = 3
}

class FindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with FindingBase {

  override def layoutInformation: NodeLayoutInformation = Finding.layoutInformation



private var _evidence: Seq[CpgNode] = Seq.empty
def evidence: Seq[CpgNode] = this._evidence


private var _keyValuePairs: Seq[KeyValuePair] = Seq.empty
def keyValuePairs: Seq[KeyValuePair] = this._keyValuePairs


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]

  if (this._evidence != null && this._evidence.nonEmpty) { properties.put("evidence", this.evidence.asJava) }
  if (this._keyValuePairs != null && this._keyValuePairs.nonEmpty) { properties.put("keyValuePairs", this.keyValuePairs.asJava) }
  properties
}

  

  override def label: String = {
    Finding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "evidence" 
case 2 => "keyValuePairs" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => evidence
case 2 => keyValuePairs
    }

  override def productPrefix = "Finding"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FindingDb]

  override def property(key:String): AnyRef = {
    key match {
      
      case "evidence" => this._evidence
      case "keyValuePairs" => this._keyValuePairs
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
    
      case "evidence" => this._evidence = value match {
        case singleValue: CpgNode => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[CpgNode]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[CpgNode]]
      }
      case "keyValuePairs" => this._keyValuePairs = value match {
        case singleValue: KeyValuePair => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[KeyValuePair]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[KeyValuePair]]
      }
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewFinding]

  this._evidence = if(other.evidence == null) Nil else other.evidence.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[CpgNode]
    case oldNode:StoredNode => oldNode.asInstanceOf[CpgNode]
    case _ => throw new MatchError("unreachable")
  }}
  this._keyValuePairs = if(other.keyValuePairs == null) Nil else other.keyValuePairs.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[KeyValuePair]
    case oldNode:StoredNode => oldNode.asInstanceOf[KeyValuePair]
    case _ => throw new MatchError("unreachable")
  }}

}

}

/** Traversal steps for Finding */
class FindingTraversal[NodeType <: Finding](val traversal: Traversal[NodeType]) extends AnyVal {



}
