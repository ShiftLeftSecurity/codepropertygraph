package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object DetachedTrackingPoint {
  def apply(graph: Graph, id: Long) = new DetachedTrackingPoint(graph, id)

  val Label = "DETACHED_TRACKING_POINT"

  object PropertyNames {
    val Cfgnode = "cfgNode" 
    val all: Set[String] = Set(Cfgnode)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    
    val Cfgnode = new PropertyKey[CfgNode]("cfgNode") 
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

  val factory = new NodeFactory[DetachedTrackingPointDb] {
    override val forLabel = DetachedTrackingPoint.Label

    override def createNode(ref: NodeRef[DetachedTrackingPointDb]) =
      new DetachedTrackingPointDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = DetachedTrackingPoint(graph, id)
  }
}

trait DetachedTrackingPointBase extends CpgNode with TrackingPointBase  {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  def cfgNode: CfgNodeBase
}

class DetachedTrackingPoint(graph: Graph, id: Long) extends NodeRef[DetachedTrackingPointDb](graph, id)
  with DetachedTrackingPointBase
  with StoredNode
  with TrackingPoint {
  
    def cfgNode: CfgNode = get().cfgNode
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    DetachedTrackingPoint.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "cfgNode" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => cfgNode
    }

  override def productPrefix = "DetachedTrackingPoint"
  override def productArity = 2
}

class DetachedTrackingPointDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with TrackingPoint with DetachedTrackingPointBase {

  override def layoutInformation: NodeLayoutInformation = DetachedTrackingPoint.layoutInformation



private var _cfgNode: CfgNode = null
def cfgNode: CfgNode = this._cfgNode


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]

   if (this._cfgNode != null) { properties.put("cfgNode", this._cfgNode) }
  properties
}

  

  override def label: String = {
    DetachedTrackingPoint.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "cfgNode" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => cfgNode
    }

  override def productPrefix = "DetachedTrackingPoint"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[DetachedTrackingPointDb]

  override def property(key:String): AnyRef = {
    key match {
      
      case "cfgNode" => this._cfgNode
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
    
      case "cfgNode" => this._cfgNode = value.asInstanceOf[CfgNode]
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewDetachedTrackingPoint]

  this._cfgNode = other.cfgNode match {
    case null => null
    case newNode: NewNode => mapping(newNode).asInstanceOf[CfgNode]
    case oldNode: StoredNode => oldNode.asInstanceOf[CfgNode]
    case _ => throw new MatchError("unreachable")
  }

}

}

/** Traversal steps for DetachedTrackingPoint */
class DetachedTrackingPointTraversal[NodeType <: DetachedTrackingPoint](val traversal: Traversal[NodeType]) extends AnyVal {



}
