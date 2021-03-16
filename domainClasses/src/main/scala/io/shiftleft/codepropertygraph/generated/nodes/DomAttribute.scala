package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object DomAttribute {
  def apply(graph: Graph, id: Long) = new DomAttribute(graph, id)

  val Label = "DOM_ATTRIBUTE"

  object PropertyNames {
    val Name = "NAME" 
    val Value = "VALUE" 
    val all: Set[String] = Set(Name, Value)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Name = new PropertyKey[String]("NAME") 
    val Value = new PropertyKey[String]("VALUE") 
    
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

  val factory = new NodeFactory[DomAttributeDb] {
    override val forLabel = DomAttribute.Label

    override def createNode(ref: NodeRef[DomAttributeDb]) =
      new DomAttributeDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = DomAttribute(graph, id)
  }
}

trait DomAttributeBase extends CpgNode  with HasName with HasValue {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class DomAttribute(graph: Graph, id: Long) extends NodeRef[DomAttributeDb](graph, id)
  with DomAttributeBase
  with StoredNode
   {
    override def name: String = get().name
  override def value: String = get().value
  
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    DomAttribute.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => value
    }

  override def productPrefix = "DomAttribute"
  override def productArity = 3
}

class DomAttributeDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with DomAttributeBase {

  override def layoutInformation: NodeLayoutInformation = DomAttribute.layoutInformation

private var _name: String = null
def name: String = _name

private var _value: String = null
def value: String = _value


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (name != null) { properties.put("NAME", name) }
if (value != null) { properties.put("VALUE", value) }

  properties
}

  

  override def label: String = {
    DomAttribute.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "name" 
case 2 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => name
case 2 => value
    }

  override def productPrefix = "DomAttribute"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[DomAttributeDb]

  override def property(key:String): AnyRef = {
    key match {
      case "NAME" => this._name
      case "VALUE" => this._value
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "NAME" => this._name = value.asInstanceOf[String]
      case "VALUE" => this._value = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewDomAttribute]
   this._name = other.name
   this._value = other.value


}

}

/** Traversal steps for DomAttribute */
class DomAttributeTraversal[NodeType <: DomAttribute](val traversal: Traversal[NodeType]) extends AnyVal {

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



  /** Traverse to value property */
  def value: Traversal[String] =
    traversal.map(_.value)

    /**
    * Traverse to nodes where the value matches the regular expression `value`
    * */
  def value(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.value == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.value); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the value matches at least one of the regular expressions in `values`
    * */
  def value(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.value); matcher.matches()}}
   }

  /**
    * Traverse to nodes where value matches `value` exactly.
    * */
  def valueExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.value == value}

  /**
    * Traverse to nodes where value matches one of the elements in `values` exactly.
    * */
  def valueExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.value)}
  }

  /**
    * Traverse to nodes where value does not match the regular expression `value`.
    * */
  def valueNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.value != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.value); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where value does not match any of the regular expressions in `values`.
    * */
  def valueNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.value); matcher.matches()}}
   }




}
