package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object MetaData {
  def apply(graph: Graph, id: Long) = new MetaData(graph, id)

  val Label = "META_DATA"

  object PropertyNames {
    val Hash = "HASH" 
    val Language = "LANGUAGE" 
    val Overlays = "OVERLAYS" 
    val PolicyDirectories = "POLICY_DIRECTORIES" 
    val Version = "VERSION" 
    val all: Set[String] = Set(Hash, Language, Overlays, PolicyDirectories, Version)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Hash = new PropertyKey[String]("HASH") 
    val Language = new PropertyKey[String]("LANGUAGE") 
    val Overlays = new PropertyKey[Seq[String]]("OVERLAYS") 
    val PolicyDirectories = new PropertyKey[Seq[String]]("POLICY_DIRECTORIES") 
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

  val factory = new NodeFactory[MetaDataDb] {
    override val forLabel = MetaData.Label

    override def createNode(ref: NodeRef[MetaDataDb]) =
      new MetaDataDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MetaData(graph, id)
  }
}

trait MetaDataBase extends CpgNode  with HasHash with HasLanguage with HasOverlays with HasPolicyDirectories with HasVersion {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class MetaData(graph: Graph, id: Long) extends NodeRef[MetaDataDb](graph, id)
  with MetaDataBase
  with StoredNode
   {
    override def hash: Option[String] = get().hash
  override def language: String = get().language
  override def overlays: List[String] = get().overlays
  override def policyDirectories: List[String] = get().policyDirectories
  override def version: String = get().version
  
  
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    MetaData.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "hash" 
case 2 => "language" 
case 3 => "overlays" 
case 4 => "policyDirectories" 
case 5 => "version" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => hash
case 2 => language
case 3 => overlays
case 4 => policyDirectories
case 5 => version
    }

  override def productPrefix = "MetaData"
  override def productArity = 6
}

class MetaDataDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with MetaDataBase {

  override def layoutInformation: NodeLayoutInformation = MetaData.layoutInformation

private var _hash: Option[String] = None
def hash: Option[String] = _hash

private var _language: String = null
def language: String = _language

private var _overlays: List[String] = Nil
def overlays: List[String] = _overlays

private var _policyDirectories: List[String] = Nil
def policyDirectories: List[String] = _policyDirectories

private var _version: String = null
def version: String = _version


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
hash.map { value => properties.put("HASH", value) }
if (language != null) { properties.put("LANGUAGE", language) }
if (this._overlays != null && this._overlays.nonEmpty) { properties.put("OVERLAYS", overlays.asJava) }
if (this._policyDirectories != null && this._policyDirectories.nonEmpty) { properties.put("POLICY_DIRECTORIES", policyDirectories.asJava) }
if (version != null) { properties.put("VERSION", version) }

  properties
}

  

  override def label: String = {
    MetaData.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "hash" 
case 2 => "language" 
case 3 => "overlays" 
case 4 => "policyDirectories" 
case 5 => "version" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => hash
case 2 => language
case 3 => overlays
case 4 => policyDirectories
case 5 => version
    }

  override def productPrefix = "MetaData"
  override def productArity = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MetaDataDb]

  override def property(key:String): AnyRef = {
    key match {
      case "HASH" => this._hash.orNull
      case "LANGUAGE" => this._language
      case "OVERLAYS" => this._overlays
      case "POLICY_DIRECTORIES" => this._policyDirectories
      case "VERSION" => this._version
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "HASH" => this._hash = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "LANGUAGE" => this._language = value.asInstanceOf[String]
      case "OVERLAYS" => this._overlays = value match {
        case singleValue: String => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[String]]
      }
      case "POLICY_DIRECTORIES" => this._policyDirectories = value match {
        case singleValue: String => List(singleValue)
        case null | None | Nil => Nil
        case jCollection: java.lang.Iterable[_] => jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toList
        case lst: List[_] => value.asInstanceOf[List[String]]
      }
      case "VERSION" => this._version = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewMetaData]
   this._hash = if(other.hash != null) other.hash else None
   this._language = other.language
   this._overlays = if(other.overlays != null) other.overlays else Nil
   this._policyDirectories = if(other.policyDirectories != null) other.policyDirectories else Nil
   this._version = other.version


}

}

/** Traversal steps for MetaData */
class MetaDataTraversal[NodeType <: MetaData](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to hash property */
  def hash: Traversal[String] =
    traversal.flatMap(_.hash)

    /**
    * Traverse to nodes where the hash matches the regular expression `value`
    * */
  def hash(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.hash.isDefined && node.hash.get == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.hash.isDefined && {matcher.reset(node.hash.get); matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where the hash matches at least one of the regular expressions in `values`
    * */
  def hash(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.hash.isDefined && matchers.exists{ matcher => matcher.reset(node.hash.get); matcher.matches()}}
   }

  /**
    * Traverse to nodes where hash matches `value` exactly.
    * */
  def hashExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.hash.isDefined && node.hash.get == value}

  /**
    * Traverse to nodes where hash matches one of the elements in `values` exactly.
    * */
  def hashExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => node.hash.isDefined && vset.contains(node.hash.get)}
  }

  /**
    * Traverse to nodes where hash does not match the regular expression `value`.
    * */
  def hashNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.hash.isEmpty || node.hash.get != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node => node.hash.isEmpty || {matcher.reset(node.hash.get); !matcher.matches()}}
    }
  }

  /**
    * Traverse to nodes where hash does not match any of the regular expressions in `values`.
    * */
  def hashNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => node.hash.isEmpty || !matchers.exists{ matcher => matcher.reset(node.hash.get); matcher.matches()}}
   }



  /** Traverse to language property */
  def language: Traversal[String] =
    traversal.map(_.language)

    /**
    * Traverse to nodes where the language matches the regular expression `value`
    * */
  def language(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.language == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.language); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the language matches at least one of the regular expressions in `values`
    * */
  def language(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.language); matcher.matches()}}
   }

  /**
    * Traverse to nodes where language matches `value` exactly.
    * */
  def languageExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.language == value}

  /**
    * Traverse to nodes where language matches one of the elements in `values` exactly.
    * */
  def languageExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.language)}
  }

  /**
    * Traverse to nodes where language does not match the regular expression `value`.
    * */
  def languageNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.language != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.language); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where language does not match any of the regular expressions in `values`.
    * */
  def languageNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.language); matcher.matches()}}
   }



  /** Traverse to overlays property */
  def overlays: Traversal[String] =
    traversal.flatMap(_.overlays)

  

  /** Traverse to policyDirectories property */
  def policyDirectories: Traversal[String] =
    traversal.flatMap(_.policyDirectories)

  

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
