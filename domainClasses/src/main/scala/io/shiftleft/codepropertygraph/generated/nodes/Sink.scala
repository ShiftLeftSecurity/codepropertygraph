package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Sink {
  def apply(graph: Graph, id: Long) = new Sink(graph, id)

  val Label = "SINK"

  object PropertyNames {
    val SinkType = "SINK_TYPE" 
    val Callingmethod = "callingMethod" 
    val Nodetype = "nodeType" 
    val Methodtags = "methodTags" 
    val Node = "node" 
    val Parameterintags = "parameterInTags" 
    val Parameterin = "parameterIn" 
    val Method = "method" 
    val Callsite = "callsite" 
    val all: Set[String] = Set(SinkType, Callingmethod, Nodetype, Methodtags, Node, Parameterintags, Parameterin, Method, Callsite)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val SinkType = new PropertyKey[String]("SINK_TYPE") 
    val Callingmethod = new PropertyKey[Method]("callingMethod") 
    val Nodetype = new PropertyKey[Type]("nodeType") 
    val Methodtags = new PropertyKey[Seq[Tag]]("methodTags") 
    val Node = new PropertyKey[TrackingPoint]("node") 
    val Parameterintags = new PropertyKey[Seq[Tag]]("parameterInTags") 
    val Parameterin = new PropertyKey[MethodParameterIn]("parameterIn") 
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

  val factory = new NodeFactory[SinkDb] {
    override val forLabel = Sink.Label

    override def createNode(ref: NodeRef[SinkDb]) =
      new SinkDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Sink(graph, id)
  }
}

trait SinkBase extends CpgNode  with HasSinkType {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def callingMethod: Option[MethodBase]
def nodeType: TypeBase
def methodTags: Seq[TagBase]
def node: TrackingPointBase
def parameterInTags: Seq[TagBase]
def parameterIn: Option[MethodParameterInBase]
def method: MethodBase
def callsite: Option[CallBase]
}

class Sink(graph: Graph, id: Long) extends NodeRef[SinkDb](graph, id)
  with SinkBase
  with StoredNode
   {
    override def sinkType: String = get().sinkType
    def callingMethod: Option[Method] = get().callingMethod
  def nodeType: Type = get().nodeType
  def methodTags: Seq[Tag] = get().methodTags
  def node: TrackingPoint = get().node
  def parameterInTags: Seq[Tag] = get().parameterInTags
  def parameterIn: Option[MethodParameterIn] = get().parameterIn
  def method: Method = get().method
  def callsite: Option[Call] = get().callsite
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Sink.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "sinkType" 
case 2 => "callingMethod" 
case 3 => "nodeType" 
case 4 => "methodTags" 
case 5 => "node" 
case 6 => "parameterInTags" 
case 7 => "parameterIn" 
case 8 => "method" 
case 9 => "callsite" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => sinkType
case 2 => callingMethod
case 3 => nodeType
case 4 => methodTags
case 5 => node
case 6 => parameterInTags
case 7 => parameterIn
case 8 => method
case 9 => callsite
    }

  override def productPrefix = "Sink"
  override def productArity = 10
}

class SinkDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with SinkBase {

  override def layoutInformation: NodeLayoutInformation = Sink.layoutInformation

private var _sinkType: String = null
def sinkType: String = _sinkType

private var _callingMethod: Option[Method] = None
def callingMethod: Option[Method] = this._callingMethod


private var _nodeType: Type = null
def nodeType: Type = this._nodeType


private var _methodTags: Seq[Tag] = Seq.empty
def methodTags: Seq[Tag] = this._methodTags


private var _node: TrackingPoint = null
def node: TrackingPoint = this._node


private var _parameterInTags: Seq[Tag] = Seq.empty
def parameterInTags: Seq[Tag] = this._parameterInTags


private var _parameterIn: Option[MethodParameterIn] = None
def parameterIn: Option[MethodParameterIn] = this._parameterIn


private var _method: Method = null
def method: Method = this._method


private var _callsite: Option[Call] = None
def callsite: Option[Call] = this._callsite


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (sinkType != null) { properties.put("SINK_TYPE", sinkType) }
   if (this._callingMethod != null && this._callingMethod.nonEmpty) { properties.put("callingMethod", this._callingMethod.get) }
   if (this._nodeType != null) { properties.put("nodeType", this._nodeType) }
  if (this._methodTags != null && this._methodTags.nonEmpty) { properties.put("methodTags", this.methodTags.asJava) }
   if (this._node != null) { properties.put("node", this._node) }
  if (this._parameterInTags != null && this._parameterInTags.nonEmpty) { properties.put("parameterInTags", this.parameterInTags.asJava) }
   if (this._parameterIn != null && this._parameterIn.nonEmpty) { properties.put("parameterIn", this._parameterIn.get) }
   if (this._method != null) { properties.put("method", this._method) }
   if (this._callsite != null && this._callsite.nonEmpty) { properties.put("callsite", this._callsite.get) }
  properties
}

  

  override def label: String = {
    Sink.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "sinkType" 
case 2 => "callingMethod" 
case 3 => "nodeType" 
case 4 => "methodTags" 
case 5 => "node" 
case 6 => "parameterInTags" 
case 7 => "parameterIn" 
case 8 => "method" 
case 9 => "callsite" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => sinkType
case 2 => callingMethod
case 3 => nodeType
case 4 => methodTags
case 5 => node
case 6 => parameterInTags
case 7 => parameterIn
case 8 => method
case 9 => callsite
    }

  override def productPrefix = "Sink"
  override def productArity = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[SinkDb]

  override def property(key:String): AnyRef = {
    key match {
      case "SINK_TYPE" => this._sinkType
      case "callingMethod" => this._callingMethod.orNull
      case "nodeType" => this._nodeType
      case "methodTags" => this._methodTags
      case "node" => this._node
      case "parameterInTags" => this._parameterInTags
      case "parameterIn" => this._parameterIn.orNull
      case "method" => this._method
      case "callsite" => this._callsite.orNull
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "SINK_TYPE" => this._sinkType = value.asInstanceOf[String]
      case "callingMethod" => this._callingMethod = value match {
        case null | None => None
        case someVal: Method => Some(someVal)
      }
      case "nodeType" => this._nodeType = value.asInstanceOf[Type]
      case "methodTags" => this._methodTags = value match {
        case singleValue: Tag => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[Tag]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[Tag]]
      }
      case "node" => this._node = value.asInstanceOf[TrackingPoint]
      case "parameterInTags" => this._parameterInTags = value match {
        case singleValue: Tag => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[Tag]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[Tag]]
      }
      case "parameterIn" => this._parameterIn = value match {
        case null | None => None
        case someVal: MethodParameterIn => Some(someVal)
      }
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
  val other = someNewNode.asInstanceOf[NewSink]
   this._sinkType = other.sinkType
  this._callingMethod = other.callingMethod match {
    case null | None => None
    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[Method])
    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[Method])
    case _ => throw new MatchError("unreachable")
  }
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
  this._parameterInTags = if(other.parameterInTags == null) Nil else other.parameterInTags.map { nodeRef => nodeRef match {
    case null => throw new NullPointerException("Nullpointers forbidden in contained nodes")
    case newNode:NewNode => mapping(newNode).asInstanceOf[Tag]
    case oldNode:StoredNode => oldNode.asInstanceOf[Tag]
    case _ => throw new MatchError("unreachable")
  }}
  this._parameterIn = other.parameterIn match {
    case null | None => None
    case Some(newNode:NewNode) => Some(mapping(newNode).asInstanceOf[MethodParameterIn])
    case Some(oldNode:StoredNode) => Some(oldNode.asInstanceOf[MethodParameterIn])
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

/** Traversal steps for Sink */
class SinkTraversal[NodeType <: Sink](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to sinkType property */
  def sinkType: Traversal[String] =
    traversal.map(_.sinkType)

    /**
    * Traverse to nodes where the sinkType matches the regular expression `value`
    * */
  def sinkType(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.sinkType == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.sinkType); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the sinkType matches at least one of the regular expressions in `values`
    * */
  def sinkType(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.sinkType); matcher.matches()}}
   }

  /**
    * Traverse to nodes where sinkType matches `value` exactly.
    * */
  def sinkTypeExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.sinkType == value}

  /**
    * Traverse to nodes where sinkType matches one of the elements in `values` exactly.
    * */
  def sinkTypeExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.sinkType)}
  }

  /**
    * Traverse to nodes where sinkType does not match the regular expression `value`.
    * */
  def sinkTypeNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.sinkType != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.sinkType); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where sinkType does not match any of the regular expressions in `values`.
    * */
  def sinkTypeNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.sinkType); matcher.matches()}}
   }




}
