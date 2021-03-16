package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object FrameworkData {
  def apply(graph: Graph, id: Long) = new FrameworkData(graph, id)

  val Label = "FRAMEWORK_DATA"

  object PropertyNames {
    val Content = "CONTENT" 
    val Name = "NAME" 
    val all: Set[String] = Set(Content, Name)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Content = new PropertyKey[String]("CONTENT") 
    val Name = new PropertyKey[String]("NAME") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(edges.AttachedData.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array("ATTACHED_DATA")
  }

  val factory = new NodeFactory[FrameworkDataDb] {
    override val forLabel = FrameworkData.Label

    override def createNode(ref: NodeRef[FrameworkDataDb]) =
      new FrameworkDataDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = FrameworkData(graph, id)
  }
}

trait FrameworkDataBase extends CpgNode  with HasContent with HasName {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class FrameworkData(graph: Graph, id: Long) extends NodeRef[FrameworkDataDb](graph, id)
  with FrameworkDataBase
  with StoredNode
   {
    override def content: String = get().content
  override def name: String = get().name
  
  def _frameworkViaAttachedDataIn: Iterator[Framework] = get()._frameworkViaAttachedDataIn
override def _attachedDataIn: JIterator[StoredNode] = get()._attachedDataIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    FrameworkData.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "content" 
case 2 => "name" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => content
case 2 => name
    }

  override def productPrefix = "FrameworkData"
  override def productArity = 3
}

class FrameworkDataDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with FrameworkDataBase {

  override def layoutInformation: NodeLayoutInformation = FrameworkData.layoutInformation

private var _content: String = null
def content: String = _content

private var _name: String = null
def name: String = _name


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (content != null) { properties.put("CONTENT", content) }
if (name != null) { properties.put("NAME", name) }

  properties
}

  def _frameworkViaAttachedDataIn: Iterator[Framework] = _attachedDataIn.asScala.collect { case node: Framework => node }
override def _attachedDataIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    FrameworkData.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "content" 
case 2 => "name" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => content
case 2 => name
    }

  override def productPrefix = "FrameworkData"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FrameworkDataDb]

  override def property(key:String): AnyRef = {
    key match {
      case "CONTENT" => this._content
      case "NAME" => this._name
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "CONTENT" => this._content = value.asInstanceOf[String]
      case "NAME" => this._name = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewFrameworkData]
   this._content = other.content
   this._name = other.name


}

}

/** Traversal steps for FrameworkData */
class FrameworkDataTraversal[NodeType <: FrameworkData](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to content property */
  def content: Traversal[String] =
    traversal.map(_.content)

    /**
    * Traverse to nodes where the content matches the regular expression `value`
    * */
  def content(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.content == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.content); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the content matches at least one of the regular expressions in `values`
    * */
  def content(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.content); matcher.matches()}}
   }

  /**
    * Traverse to nodes where content matches `value` exactly.
    * */
  def contentExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.content == value}

  /**
    * Traverse to nodes where content matches one of the elements in `values` exactly.
    * */
  def contentExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.content)}
  }

  /**
    * Traverse to nodes where content does not match the regular expression `value`.
    * */
  def contentNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.content != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.content); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where content does not match any of the regular expressions in `values`.
    * */
  def contentNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.content); matcher.matches()}}
   }



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
