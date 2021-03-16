package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object Tag {
  def apply(graph: Graph, id: Long) = new Tag(graph, id)

  val Label = "TAG"

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
    List(edges.TaggedBy.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String] = Array("TAGGED_BY")
  }

  val factory = new NodeFactory[TagDb] {
    override val forLabel = Tag.Label

    override def createNode(ref: NodeRef[TagDb]) =
      new TagDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Tag(graph, id)
  }
}

trait TagBase extends CpgNode  with HasName with HasValue {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class Tag(graph: Graph, id: Long) extends NodeRef[TagDb](graph, id)
  with TagBase
  with StoredNode
   {
    override def name: String = get().name
  override def value: String = get().value
  
  def _identifierViaTaggedByIn: Iterator[Identifier] = get()._identifierViaTaggedByIn
def _unknownViaTaggedByIn: Iterator[Unknown] = get()._unknownViaTaggedByIn
def _memberViaTaggedByIn: Iterator[Member] = get()._memberViaTaggedByIn
def _localViaTaggedByIn: Iterator[Local] = get()._localViaTaggedByIn
def _methodReturnViaTaggedByIn: Iterator[MethodReturn] = get()._methodReturnViaTaggedByIn
def _methodViaTaggedByIn: Iterator[Method] = get()._methodViaTaggedByIn
def _methodParameterInViaTaggedByIn: Iterator[MethodParameterIn] = get()._methodParameterInViaTaggedByIn
def _methodParameterOutViaTaggedByIn: Iterator[MethodParameterOut] = get()._methodParameterOutViaTaggedByIn
def _controlStructureViaTaggedByIn: Iterator[ControlStructure] = get()._controlStructureViaTaggedByIn
def _fieldIdentifierViaTaggedByIn: Iterator[FieldIdentifier] = get()._fieldIdentifierViaTaggedByIn
def _fileViaTaggedByIn: Iterator[File] = get()._fileViaTaggedByIn
def _literalViaTaggedByIn: Iterator[Literal] = get()._literalViaTaggedByIn
def _callViaTaggedByIn: Iterator[Call] = get()._callViaTaggedByIn
override def _taggedByIn: JIterator[StoredNode] = get()._taggedByIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    Tag.Label
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

  override def productPrefix = "Tag"
  override def productArity = 3
}

class TagDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
   with TagBase {

  override def layoutInformation: NodeLayoutInformation = Tag.layoutInformation

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

  def _identifierViaTaggedByIn: Iterator[Identifier] = _taggedByIn.asScala.collect { case node: Identifier => node }
def _unknownViaTaggedByIn: Iterator[Unknown] = _taggedByIn.asScala.collect { case node: Unknown => node }
def _memberViaTaggedByIn: Iterator[Member] = _taggedByIn.asScala.collect { case node: Member => node }
def _localViaTaggedByIn: Iterator[Local] = _taggedByIn.asScala.collect { case node: Local => node }
def _methodReturnViaTaggedByIn: Iterator[MethodReturn] = _taggedByIn.asScala.collect { case node: MethodReturn => node }
def _methodViaTaggedByIn: Iterator[Method] = _taggedByIn.asScala.collect { case node: Method => node }
def _methodParameterInViaTaggedByIn: Iterator[MethodParameterIn] = _taggedByIn.asScala.collect { case node: MethodParameterIn => node }
def _methodParameterOutViaTaggedByIn: Iterator[MethodParameterOut] = _taggedByIn.asScala.collect { case node: MethodParameterOut => node }
def _controlStructureViaTaggedByIn: Iterator[ControlStructure] = _taggedByIn.asScala.collect { case node: ControlStructure => node }
def _fieldIdentifierViaTaggedByIn: Iterator[FieldIdentifier] = _taggedByIn.asScala.collect { case node: FieldIdentifier => node }
def _fileViaTaggedByIn: Iterator[File] = _taggedByIn.asScala.collect { case node: File => node }
def _literalViaTaggedByIn: Iterator[Literal] = _taggedByIn.asScala.collect { case node: Literal => node }
def _callViaTaggedByIn: Iterator[Call] = _taggedByIn.asScala.collect { case node: Call => node }
override def _taggedByIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    Tag.Label
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

  override def productPrefix = "Tag"
  override def productArity = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagDb]

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
  val other = someNewNode.asInstanceOf[NewTag]
   this._name = other.name
   this._value = other.value


}

}

/** Traversal steps for Tag */
class TagTraversal[NodeType <: Tag](val traversal: Traversal[NodeType]) extends AnyVal {

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
