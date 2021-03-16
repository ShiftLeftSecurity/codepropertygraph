package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object File {
  def apply(graph: Graph, id: Long) = new File(graph, id)

  val Label = "FILE"

  object PropertyNames {
    val Hash = "HASH" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val all: Set[String] = Set(Hash, Name, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Hash = new PropertyKey[String]("HASH") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Contains.layoutInformation, edges.TaggedBy.layoutInformation, edges.Ast.layoutInformation).asJava,
    List(edges.SourceFile.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST","CONTAINS","TAGGED_BY")
    val In: Array[String] = Array("SOURCE_FILE")
  }

  val factory = new NodeFactory[FileDb] {
    override val forLabel = File.Label

    override def createNode(ref: NodeRef[FileDb]) =
      new FileDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = File(graph, id)
  }
}

trait FileBase extends CpgNode with AstNodeBase with HasHash with HasName with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class File(graph: Graph, id: Long) extends NodeRef[FileDb](graph, id)
  with FileBase
  with StoredNode
  with AstNode {
    override def hash: Option[String] = get().hash
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  
  def _typeDeclViaContainsOut: Iterator[TypeDecl] = get()._typeDeclViaContainsOut
def _methodViaContainsOut: Iterator[Method] = get()._methodViaContainsOut
override def _containsOut: JIterator[StoredNode] = get()._containsOut
def _tagViaTaggedByOut: Iterator[Tag] = get()._tagViaTaggedByOut
override def _taggedByOut: JIterator[StoredNode] = get()._taggedByOut
def _commentViaAstOut: Iterator[Comment] = get()._commentViaAstOut
def _namespaceBlockViaAstOut: Iterator[NamespaceBlock] = get()._namespaceBlockViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _typeDeclViaSourceFileIn: Iterator[TypeDecl] = get()._typeDeclViaSourceFileIn
def _methodViaSourceFileIn: Iterator[Method] = get()._methodViaSourceFileIn
def _namespaceBlockViaSourceFileIn: Iterator[NamespaceBlock] = get()._namespaceBlockViaSourceFileIn
override def _sourceFileIn: JIterator[StoredNode] = get()._sourceFileIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    File.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "hash" 
case 2 => "name" 
case 3 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => hash
case 2 => name
case 3 => order
    }

  override def productPrefix = "File"
  override def productArity = 4
}

class FileDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with FileBase {

  override def layoutInformation: NodeLayoutInformation = File.layoutInformation

private var _hash: Option[String] = None
def hash: Option[String] = _hash

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
hash.map { value => properties.put("HASH", value) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _typeDeclViaContainsOut: Iterator[TypeDecl] = _containsOut.asScala.collect { case node: TypeDecl => node }
def _methodViaContainsOut: Iterator[Method] = _containsOut.asScala.collect { case node: Method => node }
override def _containsOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _tagViaTaggedByOut: Iterator[Tag] = _taggedByOut.asScala.collect { case node: Tag => node }
override def _taggedByOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _commentViaAstOut: Iterator[Comment] = _astOut.asScala.collect { case node: Comment => node }
def _namespaceBlockViaAstOut: Iterator[NamespaceBlock] = _astOut.asScala.collect { case node: NamespaceBlock => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaSourceFileIn: Iterator[TypeDecl] = _sourceFileIn.asScala.collect { case node: TypeDecl => node }
def _methodViaSourceFileIn: Iterator[Method] = _sourceFileIn.asScala.collect { case node: Method => node }
def _namespaceBlockViaSourceFileIn: Iterator[NamespaceBlock] = _sourceFileIn.asScala.collect { case node: NamespaceBlock => node }
override def _sourceFileIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    File.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "hash" 
case 2 => "name" 
case 3 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => hash
case 2 => name
case 3 => order
    }

  override def productPrefix = "File"
  override def productArity = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FileDb]

  override def property(key:String): AnyRef = {
    key match {
      case "HASH" => this._hash.orNull
      case "NAME" => this._name
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "HASH" => this._hash = value match {
        case null | None => None
        case someVal: String => Some(someVal)
      }
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewFile]
   this._hash = if(other.hash != null) other.hash else None
   this._name = other.name
   this._order = other.order


}

}

/** Traversal steps for File */
class FileTraversal[NodeType <: File](val traversal: Traversal[NodeType]) extends AnyVal {

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



  /** Traverse to order property */
  def order: Traversal[java.lang.Integer] =
    traversal.map(_.order)

    /**
    * Traverse to nodes where the order equals the given `value`
    * */
  def order(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order == value}

  /**
    * Traverse to nodes where the order equals at least one of the given `values`
    * */
  def order(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => vset.contains(node.order)}
  }

  /**
    * Traverse to nodes where the order is greater than the given `value`
    * */
  def orderGt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order > value}

  /**
    * Traverse to nodes where the order is greater than or equal the given `value`
    * */
  def orderGte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order >= value}

  /**
    * Traverse to nodes where the order is less than the given `value`
    * */
  def orderLt(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order < value}

  /**
    * Traverse to nodes where the order is less than or equal the given `value`
    * */
  def orderLte(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order <= value}

  /**
    * Traverse to nodes where order is not equal to the given `value`.
    * */
  def orderNot(value: java.lang.Integer): Traversal[NodeType] =
    traversal.filter{_.order != value}

  /**
    * Traverse to nodes where order is not equal to any of the given `values`.
    * */
  def orderNot(values: java.lang.Integer*): Traversal[NodeType] = {
    val vset = values.toSet
    traversal.filter{node => !vset.contains(node.order)}
  }



}
