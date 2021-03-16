package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object PackagePrefix {
  def apply(graph: Graph, id: Long) = new PackagePrefix(graph, id)

  val Label = "PACKAGE_PREFIX"

  object PropertyNames {
    val Value = "VALUE" 
    val all: Set[String] = Set(Value)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
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

  val factory = new NodeFactory[PackagePrefixDb] {
    override val forLabel = PackagePrefix.Label

    override def createNode(ref: NodeRef[PackagePrefixDb]) =
      new PackagePrefixDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = PackagePrefix(graph, id)
  }
}

trait PackagePrefixBase extends CpgNode  with HasValue {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class PackagePrefix(graph: Graph, id: Long) extends NodeRef[PackagePrefixDb](graph, id)
  with PackagePrefixBase
  with StoredNode
   {
    override def value: String = get().value
  
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    PackagePrefix.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => value
    }

  override def productPrefix = "PackagePrefix"
  override def productArity = 2
}

class PackagePrefixDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with PackagePrefixBase {

  override def layoutInformation: NodeLayoutInformation = PackagePrefix.layoutInformation

private var _value: String = null
def value: String = _value


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (value != null) { properties.put("VALUE", value) }

  properties
}

  

  override def label: String = {
    PackagePrefix.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "value" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => value
    }

  override def productPrefix = "PackagePrefix"
  override def productArity = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[PackagePrefixDb]

  override def property(key:String): AnyRef = {
    key match {
      case "VALUE" => this._value
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "VALUE" => this._value = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewPackagePrefix]
   this._value = other.value


}

}

/** Traversal steps for PackagePrefix */
class PackagePrefixTraversal[NodeType <: PackagePrefix](val traversal: Traversal[NodeType]) extends AnyVal {

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
