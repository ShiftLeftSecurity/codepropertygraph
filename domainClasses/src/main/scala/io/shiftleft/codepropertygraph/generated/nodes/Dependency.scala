package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Dependency {
  def apply(graph: Graph, id: Long) = new Dependency(graph, id)

  val Label = "DEPENDENCY"

  object PropertyNames {
    val DependencyGroupId = "DEPENDENCY_GROUP_ID" 
    val Name = "NAME" 
    val Version = "VERSION" 
    val all: Set[String] = Set(DependencyGroupId, Name, Version)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val DependencyGroupId = new PropertyKey[String]("DEPENDENCY_GROUP_ID") 
    val Name = new PropertyKey[String]("NAME") 
    val Version = new PropertyKey[String]("VERSION") 
    
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

  val factory = new NodeFactory[DependencyDb] {
    override val forLabel = Dependency.Label

    override def createNode(ref: NodeRef[DependencyDb]) =
      new DependencyDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Dependency(graph, id)
  }
}

trait DependencyBase extends CpgNode  with HasDependencyGroupId with HasName with HasVersion {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Dependency(graph: Graph, id: Long) extends NodeRef[DependencyDb](graph, id)
  with DependencyBase
  with StoredNode
   {
    override def dependencyGroupId: Option[String] = get().dependencyGroupId
  override def name: String = get().name
  override def version: String = get().version
  
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Dependency.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "dependencyGroupId" 
case 2 => "name" 
case 3 => "version" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => dependencyGroupId
case 2 => name
case 3 => version
    }

  override def productPrefix = "Dependency"
  override def productArity = 4
}

class DependencyDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with DependencyBase {

  override def layoutInformation: NodeLayoutInformation = Dependency.layoutInformation

private var _dependencyGroupId: Option[String] = None
def dependencyGroupId: Option[String] = _dependencyGroupId

private var _name: String = null
def name: String = _name

private var _version: String = null
def version: String = _version


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
dependencyGroupId.map { value => properties.put("DEPENDENCY_GROUP_ID", value) }
if (name != null) { properties.put("NAME", name) }
if (version != null) { properties.put("VERSION", version) }

  properties
}

  

  override def label: String = {
    Dependency.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "dependencyGroupId" 
case 2 => "name" 
case 3 => "version" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => dependencyGroupId
case 2 => name
case 3 => version
    }

  override def productPrefix = "Dependency"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[DependencyDb]

  override def property(key:String): AnyRef = {
    key match {
      case "DEPENDENCY_GROUP_ID" => this._dependencyGroupId.orNull
      case "NAME" => this._name
      case "VERSION" => this._version
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "DEPENDENCY_GROUP_ID" => this._dependencyGroupId = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "VERSION" => this._version = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewDependency]
   this._dependencyGroupId = if(other.dependencyGroupId != null) other.dependencyGroupId else None
   this._name = other.name
   this._version = other.version


}

}

/** Traversal steps for Dependency */
class DependencyTraversal[NodeType <: Dependency](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to dependencyGroupId property */
  def dependencyGroupId: Traversal[String] =
    traversal.flatMap(_.dependencyGroupId)

    /**
    * Traverse to nodes where the dependencyGroupId matches the regular expression `value`
    * */
  def dependencyGroupId(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.dependencyGroupId.isDefined && node.dependencyGroupId.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.dependencyGroupId.isDefined && {matcher.reset(node.dependencyGroupId.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the dependencyGroupId matches at least one of the regular expressions in `values`
    * */
  def dependencyGroupId(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.dependencyGroupId.isDefined && matchers.exists{ matcher => matcher.reset(node.dependencyGroupId.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where dependencyGroupId matches `value` exactly.
    * */
  def dependencyGroupIdExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.dependencyGroupId.isDefined && node.dependencyGroupId.get == value}

  /**
    * Traverse to nodes where dependencyGroupId matches one of the elements in `values` exactly.
    * */
  def dependencyGroupIdExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.dependencyGroupId.isDefined && vset.contains(node.dependencyGroupId.get)}
  }

  /**
    * Traverse to nodes where dependencyGroupId does not match the regular expression `value`.
    * */
  def dependencyGroupIdNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.dependencyGroupId.isEmpty || node.dependencyGroupId.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.dependencyGroupId.isEmpty || {matcher.reset(node.dependencyGroupId.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where dependencyGroupId does not match any of the regular expressions in `values`.
    * */
  def dependencyGroupIdNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.dependencyGroupId.isEmpty || !matchers.exists{ matcher => matcher.reset(node.dependencyGroupId.get); matcher.matches()}}
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



  /** Traverse to version property */
  def version: Traversal[String] =
    traversal.map(_.version)

    /**
    * Traverse to nodes where the version matches the regular expression `value`
    * */
  def version(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.version == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.version); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the version matches at least one of the regular expressions in `values`
    * */
  def version(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.version); matcher.matches()}}
   }

  /**
    * Traverse to nodes where version matches `value` exactly.
    * */
  def versionExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.version == value}

  /**
    * Traverse to nodes where version matches one of the elements in `values` exactly.
    * */
  def versionExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.version)}
  }

  /**
    * Traverse to nodes where version does not match the regular expression `value`.
    * */
  def versionNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.version != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.version); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where version does not match any of the regular expressions in `values`.
    * */
  def versionNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.version); matcher.matches()}}
   }




}
