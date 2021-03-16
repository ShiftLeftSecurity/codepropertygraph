package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object KeyValuePair {
  def apply(graph: Graph, id: Long) = new KeyValuePair(graph, id)

  val Label = "KEY_VALUE_PAIR"

  object PropertyNames {
    val Key = "KEY" 
    val Value = "VALUE" 
    val all: Set[String] = Set(Key, Value)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Key = new PropertyKey[String]("KEY") 
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

  val factory = new NodeFactory[KeyValuePairDb] {
    override val forLabel = KeyValuePair.Label

    override def createNode(ref: NodeRef[KeyValuePairDb]) =
      new KeyValuePairDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = KeyValuePair(graph, id)
  }
}

trait KeyValuePairBase extends CpgNode  with HasKey with HasValue {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class KeyValuePair(graph: Graph, id: Long) extends NodeRef[KeyValuePairDb](graph, id)
  with KeyValuePairBase
  with StoredNode
   {
    override def key: String = get().key
  override def value: String = get().value
  
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    KeyValuePair.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "key" 
case 2 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => key
case 2 => value
    }

  override def productPrefix = "KeyValuePair"
  override def productArity = 3
}

class KeyValuePairDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with KeyValuePairBase {

  override def layoutInformation: NodeLayoutInformation = KeyValuePair.layoutInformation

private var _key: String = null
def key: String = _key

private var _value: String = null
def value: String = _value


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (key != null) { properties.put("KEY", key) }
if (value != null) { properties.put("VALUE", value) }

  properties
}

  

  override def label: String = {
    KeyValuePair.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "key" 
case 2 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => key
case 2 => value
    }

  override def productPrefix = "KeyValuePair"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[KeyValuePairDb]

  override def property(key:String): AnyRef = {
    key match {
      case "KEY" => this._key
      case "VALUE" => this._value
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "KEY" => this._key = value.asInstanceOf[String]
      case "VALUE" => this._value = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewKeyValuePair]
   this._key = other.key
   this._value = other.value


}

}

/** Traversal steps for KeyValuePair */
class KeyValuePairTraversal[NodeType <: KeyValuePair](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to key property */
  def key: Traversal[String] =
    traversal.map(_.key)

    /**
    * Traverse to nodes where the key matches the regular expression `value`
    * */
  def key(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.key == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.key); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the key matches at least one of the regular expressions in `values`
    * */
  def key(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.key); matcher.matches()}}
   }

  /**
    * Traverse to nodes where key matches `value` exactly.
    * */
  def keyExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.key == value}

  /**
    * Traverse to nodes where key matches one of the elements in `values` exactly.
    * */
  def keyExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.key)}
  }

  /**
    * Traverse to nodes where key does not match the regular expression `value`.
    * */
  def keyNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.key != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.key); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where key does not match any of the regular expressions in `values`.
    * */
  def keyNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.key); matcher.matches()}}
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
