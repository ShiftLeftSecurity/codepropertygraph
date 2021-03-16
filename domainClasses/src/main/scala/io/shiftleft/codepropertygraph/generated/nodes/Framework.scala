package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Framework {
  def apply(graph: Graph, id: Long) = new Framework(graph, id)

  val Label = "FRAMEWORK"

  object PropertyNames {
    val Name = "NAME" 
    val all: Set[String] = Set(Name)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Name = new PropertyKey[String]("NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.AttachedData.layoutInformation).asJava,
    List().asJava)


  object Edges {
    val Out: Array[String] = Array("ATTACHED_DATA")
    val In: Array[String] = Array()
  }

  val factory = new NodeFactory[FrameworkDb] {
    override val forLabel = Framework.Label

    override def createNode(ref: NodeRef[FrameworkDb]) =
      new FrameworkDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Framework(graph, id)
  }
}

trait FrameworkBase extends CpgNode  with HasName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Framework(graph: Graph, id: Long) extends NodeRef[FrameworkDb](graph, id)
  with FrameworkBase
  with StoredNode
   {
    override def name: String = get().name
  
  def _frameworkDataViaAttachedDataOut: Iterator[FrameworkData] = get()._frameworkDataViaAttachedDataOut
override def _attachedDataOut: JIterator[StoredNode] = get()._attachedDataOut
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Framework.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
    }

  override def productPrefix = "Framework"
  override def productArity = 2
}

class FrameworkDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with FrameworkBase {

  override def layoutInformation: NodeLayoutInformation = Framework.layoutInformation

private var _name: String = null
def name: String = _name


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (name != null) { properties.put("NAME", name) }

  properties
}

  def _frameworkDataViaAttachedDataOut: Iterator[FrameworkData] = _attachedDataOut.asScala.collect { case node: FrameworkData => node }
override def _attachedDataOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Framework.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
    }

  override def productPrefix = "Framework"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FrameworkDb]

  override def property(key:String): AnyRef = {
    key match {
      case "NAME" => this._name
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "NAME" => this._name = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewFramework]
   this._name = other.name


}

}

/** Traversal steps for Framework */
class FrameworkTraversal[NodeType <: Framework](val traversal: Traversal[NodeType]) extends AnyVal {

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
