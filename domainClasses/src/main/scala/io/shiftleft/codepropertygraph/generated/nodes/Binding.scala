package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Binding {
  def apply(graph: Graph, id: Long) = new Binding(graph, id)

  val Label = "BINDING"

  object PropertyNames {
    val IsMethodNeverOverridden = "IS_METHOD_NEVER_OVERRIDDEN" 
    val Name = "NAME" 
    val Signature = "SIGNATURE" 
    val all: Set[String] = Set(IsMethodNeverOverridden, Name, Signature)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val IsMethodNeverOverridden = new PropertyKey[java.lang.Boolean]("IS_METHOD_NEVER_OVERRIDDEN") 
    val Name = new PropertyKey[String]("NAME") 
    val Signature = new PropertyKey[String]("SIGNATURE") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ref.layoutInformation).asJava,
    List(edges.Binds.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("REF")
    val In: Array[String] = Array("BINDS")
  }

  val factory = new NodeFactory[BindingDb] {
    override val forLabel = Binding.Label

    override def createNode(ref: NodeRef[BindingDb]) =
      new BindingDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Binding(graph, id)
  }
}

trait BindingBase extends CpgNode  with HasIsMethodNeverOverridden with HasName with HasSignature {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Binding(graph: Graph, id: Long) extends NodeRef[BindingDb](graph, id)
  with BindingBase
  with StoredNode
   {
    override def isMethodNeverOverridden: Option[java.lang.Boolean] = get().isMethodNeverOverridden
  override def name: String = get().name
  override def signature: String = get().signature
  
  def _methodViaRefOut: Method = get()._methodViaRefOut
override def _refOut: JIterator[StoredNode] = get()._refOut
def _typeDeclViaBindsIn: TypeDecl = get()._typeDeclViaBindsIn
override def _bindsIn: JIterator[StoredNode] = get()._bindsIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Binding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "isMethodNeverOverridden" 
case 2 => "name" 
case 3 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => isMethodNeverOverridden
case 2 => name
case 3 => signature
    }

  override def productPrefix = "Binding"
  override def productArity = 4
}

class BindingDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with BindingBase {

  override def layoutInformation: NodeLayoutInformation = Binding.layoutInformation

private var _isMethodNeverOverridden: Option[java.lang.Boolean] = None
def isMethodNeverOverridden: Option[java.lang.Boolean] = _isMethodNeverOverridden

private var _name: String = null
def name: String = _name

private var _signature: String = null
def signature: String = _signature


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
isMethodNeverOverridden.map { value => properties.put("IS_METHOD_NEVER_OVERRIDDEN", value) }
if (name != null) { properties.put("NAME", name) }
if (signature != null) { properties.put("SIGNATURE", signature) }

  properties
}

  def _methodViaRefOut: Method = _refOut.asScala.collect { case node: Method => node }.next()
override def _refOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaBindsIn: TypeDecl = _bindsIn.asScala.collect { case node: TypeDecl => node }.next()
override def _bindsIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Binding.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "isMethodNeverOverridden" 
case 2 => "name" 
case 3 => "signature" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => isMethodNeverOverridden
case 2 => name
case 3 => signature
    }

  override def productPrefix = "Binding"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[BindingDb]

  override def property(key:String): AnyRef = {
    key match {
      case "IS_METHOD_NEVER_OVERRIDDEN" => this._isMethodNeverOverridden.orNull
      case "NAME" => this._name
      case "SIGNATURE" => this._signature
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "IS_METHOD_NEVER_OVERRIDDEN" => this._isMethodNeverOverridden = value match {
        case null | None => None
        case someVal: java.lang.Boolean => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "SIGNATURE" => this._signature = value.asInstanceOf[String]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewBinding]
   this._isMethodNeverOverridden = if(other.isMethodNeverOverridden != null) other.isMethodNeverOverridden else None
   this._name = other.name
   this._signature = other.signature


}

}

/** Traversal steps for Binding */
class BindingTraversal[NodeType <: Binding](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to isMethodNeverOverridden property */
  def isMethodNeverOverridden: Traversal[java.lang.Boolean] =
    traversal.flatMap(_.isMethodNeverOverridden)

    /**
    * Traverse to nodes where the isMethodNeverOverridden equals the given `value`
    * */
  def isMethodNeverOverridden(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{node => node.isMethodNeverOverridden.isDefined && node.isMethodNeverOverridden.get == value}

  /**
    * Traverse to nodes where isMethodNeverOverridden is not equal to the given `value`.
    * */
  def isMethodNeverOverriddenNot(value: java.lang.Boolean): Traversal[NodeType] =
    traversal.filter{node => !node.isMethodNeverOverridden.isDefined || node.isMethodNeverOverridden.get == value}


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



  /** Traverse to signature property */
  def signature: Traversal[String] =
    traversal.map(_.signature)

    /**
    * Traverse to nodes where the signature matches the regular expression `value`
    * */
  def signature(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the signature matches at least one of the regular expressions in `values`
    * */
  def signature(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }

  /**
    * Traverse to nodes where signature matches `value` exactly.
    * */
  def signatureExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.signature == value}

  /**
    * Traverse to nodes where signature matches one of the elements in `values` exactly.
    * */
  def signatureExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.signature)}
  }

  /**
    * Traverse to nodes where signature does not match the regular expression `value`.
    * */
  def signatureNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.signature != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.signature); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where signature does not match any of the regular expressions in `values`.
    * */
  def signatureNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.signature); matcher.matches()}}
   }




}
