package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Source {
  def apply(graph: Graph, id: Long) = new Source(graph, id)

  val Label = "SOURCE"

  object PropertyNames {
    val SourceType = "SOURCE_TYPE" 
    val Callingmethod = "callingMethod" 
    val Tags = "tags" 
    val Nodetype = "nodeType" 
    val Methodtags = "methodTags" 
    val Node = "node" 
    val Method = "method" 
    val Callsite = "callsite" 
    val all: Set[String] = Set(SourceType, Callingmethod, Tags, Nodetype, Methodtags, Node, Method, Callsite)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val SourceType = new PropertyKey[String]("SOURCE_TYPE") 
    val Callingmethod = new PropertyKey[Method]("callingMethod") 
    val Tags = new PropertyKey[Seq[Tag]]("tags") 
    val Nodetype = new PropertyKey[Type]("nodeType") 
    val Methodtags = new PropertyKey[Seq[Tag]]("methodTags") 
    val Node = new PropertyKey[TrackingPoint]("node") 
    val Method = new PropertyKey[Method]("method") 
    val Callsite = new PropertyKey[Call]("callsite") 
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

  val factory = new NodeFactory[SourceDb] {
    override val forLabel = Source.Label

    override def createNode(ref: NodeRef[SourceDb]) =
      new SourceDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Source(graph, id)
  }
}

trait SourceBase extends CpgNode  with HasSourceType {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def callingMethod: Option[MethodBase]
def tags: Seq[TagBase]
def nodeType: TypeBase
def methodTags: Seq[TagBase]
def node: TrackingPointBase
def method: MethodBase
def callsite: Option[CallBase]
}

class Source(graph: Graph, id: Long) extends NodeRef[SourceDb](graph, id)
  with SourceBase
  with StoredNode
   {
    override def sourceType: String = get().sourceType
    def callingMethod: Option[Method] = get().callingMethod
  def tags: Seq[Tag] = get().tags
  def nodeType: Type = get().nodeType
  def methodTags: Seq[Tag] = get().methodTags
  def node: TrackingPoint = get().node
  def method: Method = get().method
  def callsite: Option[Call] = get().callsite
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Source.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "sourceType" 
case 2 => "callingMethod" 
case 3 => "tags" 
case 4 => "nodeType" 
case 5 => "methodTags" 
case 6 => "node" 
case 7 => "method" 
case 8 => "callsite" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => sourceType
case 2 => callingMethod
case 3 => tags
case 4 => nodeType
case 5 => methodTags
case 6 => node
case 7 => method
case 8 => callsite
    }

  override def productPrefix = "Source"
  override def productArity = 9
}

class SourceDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with SourceBase {

  override def layoutInformation: NodeLayoutInformation = Source.layoutInformation

private var _sourceType: String = null
def sourceType: String = _sourceType

private var _callingMethod: Option[Method] = None
def callingMethod: Option[Method] = this._callingMethod


private var _tags: Seq[Tag] = Seq.empty
def tags: Seq[Tag] = this._tags


private var _nodeType: Type = null
def nodeType: Type = this._nodeType


private var _methodTags: Seq[Tag] = Seq.empty
def methodTags: Seq[Tag] = this._methodTags


private var _node: TrackingPoint = null
def node: TrackingPoint = this._node


private var _method: Method = null
def method: Method = this._method


private var _callsite: Option[Call] = None
def callsite: Option[Call] = this._callsite


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (sourceType != null) { properties.put("SOURCE_TYPE", sourceType) }
   if (this._callingMethod != null && this._callingMethod.nonEmpty) { properties.put("callingMethod", this._callingMethod.get) }
  if (this._tags != null && this._tags.nonEmpty) { properties.put("tags", this.tags.asJava) }
   if (this._nodeType != null) { properties.put("nodeType", this._nodeType) }
  if (this._methodTags != null && this._methodTags.nonEmpty) { properties.put("methodTags", this.methodTags.asJava) }
   if (this._node != null) { properties.put("node", this._node) }
   if (this._method != null) { properties.put("method", this._method) }
   if (this._callsite != null && this._callsite.nonEmpty) { properties.put("callsite", this._callsite.get) }
  properties
}

  

  override def label: String = {
    Source.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "sourceType" 
case 2 => "callingMethod" 
case 3 => "tags" 
case 4 => "nodeType" 
case 5 => "methodTags" 
case 6 => "node" 
case 7 => "method" 
case 8 => "callsite" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => sourceType
case 2 => callingMethod
case 3 => tags
case 4 => nodeType
case 5 => methodTags
case 6 => node
case 7 => method
case 8 => callsite
    }

  override def productPrefix = "Source"
  override def productArity = 9

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[SourceDb]

  override def property(key:String): AnyRef = {
    key match {
      case "SOURCE_TYPE" => this._sourceType
      case "callingMethod" => this._callingMethod.orNull
      case "tags" => this._tags
      case "nodeType" => this._nodeType
      case "methodTags" => this._methodTags
      case "node" => this._node
      case "method" => this._method
      case "callsite" => this._callsite.orNull
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "SOURCE_TYPE" => this._sourceType = value.asInstanceOf[String]
      case "callingMethod" => this._callingMethod = value match {
        case null | None => None
        case someVal: Method => Some(someVal)
      }
      case "tags" => this._tags = value match {
        case singleValue: Tag => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[Tag]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[Tag]]
      }
      case "nodeType" => this._nodeType = value.asInstanceOf[Type]
      case "methodTags" => this._methodTags = value match {
        case singleValue: Tag => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[Tag]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[Tag]]
      }
      case "node" => this._node = value.asInstanceOf[TrackingPoint]
      case "method" => this._method = value.asInstanceOf[Method]
      case "callsite" => this._callsite = value match {
        case null | None => None
        case someVal: Call => Some(someVal)
      }
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewSource]
   this._sourceType = other.sourceType
  this._callingMethod = other.callingMethod match {
    case null | None => None
    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[Method])
    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[Method])
    case _ => throw new MatchError("unreachable")
  }
  this._tags = if(other.tags == null) Nil else other.tags.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[Tag]
    case oldNode:StoredNode => oldNode.asInstanceOf[Tag]
    case _ => throw new MatchError("unreachable")
  }}
  this._nodeType = other.nodeType match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[Type]
    case oldNode: StoredNode => oldNode.asInstanceOf[Type]
    case _ => throw new MatchError("unreachable")
  }
  this._methodTags = if(other.methodTags == null) Nil else other.methodTags.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[Tag]
    case oldNode:StoredNode => oldNode.asInstanceOf[Tag]
    case _ => throw new MatchError("unreachable")
  }}
  this._node = other.node match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[TrackingPoint]
    case oldNode: StoredNode => oldNode.asInstanceOf[TrackingPoint]
    case _ => throw new MatchError("unreachable")
  }
  this._method = other.method match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[Method]
    case oldNode: StoredNode => oldNode.asInstanceOf[Method]
    case _ => throw new MatchError("unreachable")
  }
  this._callsite = other.callsite match {
    case null | None => None
    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[Call])
    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[Call])
    case _ => throw new MatchError("unreachable")
  }

}

}

/** Traversal steps for Source */
class SourceTraversal[NodeType <: Source](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to sourceType property */
  def sourceType: Traversal[String] =
    traversal.map(_.sourceType)

    /**
    * Traverse to nodes where the sourceType matches the regular expression `value`
    * */
  def sourceType(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.sourceType == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.sourceType); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the sourceType matches at least one of the regular expressions in `values`
    * */
  def sourceType(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.sourceType); matcher.matches()}}
   }

  /**
    * Traverse to nodes where sourceType matches `value` exactly.
    * */
  def sourceTypeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.sourceType == value}

  /**
    * Traverse to nodes where sourceType matches one of the elements in `values` exactly.
    * */
  def sourceTypeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.sourceType)}
  }

  /**
    * Traverse to nodes where sourceType does not match the regular expression `value`.
    * */
  def sourceTypeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.sourceType != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.sourceType); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where sourceType does not match any of the regular expressions in `values`.
    * */
  def sourceTypeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.sourceType); matcher.matches()}}
   }




}
