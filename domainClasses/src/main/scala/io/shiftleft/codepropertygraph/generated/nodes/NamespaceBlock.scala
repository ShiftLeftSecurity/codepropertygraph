package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.edges
import java.util.{Collections => JCollections, HashMap => JHashMap, Iterator => JIterator, Map => JMap, Set => JSet}
import overflowdb._
import overflowdb.traversal.Traversal
import scala.jdk.CollectionConverters._

object NamespaceBlock {
  def apply(graph: Graph, id: Long) = new NamespaceBlock(graph, id)

  val Label = "NAMESPACE_BLOCK"

  object PropertyNames {
    val Filename = "FILENAME" 
    val FullName = "FULL_NAME" 
    val Name = "NAME" 
    val Order = "ORDER" 
    val all: Set[String] = Set(Filename, FullName, Name, Order)
    val allAsJava: JSet[String] = all.asJava
  }

  object Properties {
    val Filename = new PropertyKey[String]("FILENAME") 
    val FullName = new PropertyKey[String]("FULL_NAME") 
    val Name = new PropertyKey[String]("NAME") 
    val Order = new PropertyKey[java.lang.Integer]("ORDER") 
    
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(edges.Ref.layoutInformation, edges.Ast.layoutInformation, edges.SourceFile.layoutInformation).asJava,
    List(edges.Ast.layoutInformation).asJava)


  object Edges {
    val Out: Array[String] = Array("AST","REF","SOURCE_FILE")
    val In: Array[String] = Array("AST")
  }

  val factory = new NodeFactory[NamespaceBlockDb] {
    override val forLabel = NamespaceBlock.Label

    override def createNode(ref: NodeRef[NamespaceBlockDb]) =
      new NamespaceBlockDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = NamespaceBlock(graph, id)
  }
}

trait NamespaceBlockBase extends CpgNode with AstNodeBase with HasFilename with HasFullName with HasName with HasOrder {
  def asStored : StoredNode = this.asInstanceOf[StoredNode]

  
}

class NamespaceBlock(graph: Graph, id: Long) extends NodeRef[NamespaceBlockDb](graph, id)
  with NamespaceBlockBase
  with StoredNode
  with AstNode {
    override def filename: String = get().filename
  override def fullName: String = get().fullName
  override def name: String = get().name
  override def order: java.lang.Integer = get().order
  
  def _namespaceViaRefOut: Iterator[Namespace] = get()._namespaceViaRefOut
override def _refOut: JIterator[StoredNode] = get()._refOut
def _typeDeclViaAstOut: Iterator[TypeDecl] = get()._typeDeclViaAstOut
def _methodViaAstOut: Iterator[Method] = get()._methodViaAstOut
override def _astOut: JIterator[StoredNode] = get()._astOut
def _fileViaSourceFileOut: Iterator[File] = get()._fileViaSourceFileOut
override def _sourceFileOut: JIterator[StoredNode] = get()._sourceFileOut
def _fileViaAstIn: Option[File] = get()._fileViaAstIn
override def _astIn: JIterator[StoredNode] = get()._astIn
  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def valueMap: JMap[String, AnyRef] = get.valueMap
  override def canEqual(that: Any): Boolean = get.canEqual(that)
  override def label: String = {
    NamespaceBlock.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "filename" 
case 2 => "fullName" 
case 3 => "name" 
case 4 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => filename
case 2 => fullName
case 3 => name
case 4 => order
    }

  override def productPrefix = "NamespaceBlock"
  override def productArity = 5
}

class NamespaceBlockDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode
  with AstNode with NamespaceBlockBase {

  override def layoutInformation: NodeLayoutInformation = NamespaceBlock.layoutInformation

private var _filename: String = null
def filename: String = _filename

private var _fullName: String = null
def fullName: String = _fullName

private var _name: String = null
def name: String = _name

private var _order: java.lang.Integer = null
def order: java.lang.Integer = _order


  /* all properties */
  override def valueMap: JMap[String, AnyRef] =  {
  val properties = new JHashMap[String, AnyRef]
if (filename != null) { properties.put("FILENAME", filename) }
if (fullName != null) { properties.put("FULL_NAME", fullName) }
if (name != null) { properties.put("NAME", name) }
if (order != null) { properties.put("ORDER", order) }

  properties
}

  def _namespaceViaRefOut: Iterator[Namespace] = _refOut.asScala.collect { case node: Namespace => node }
override def _refOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(0).asInstanceOf[JIterator[StoredNode]]
def _typeDeclViaAstOut: Iterator[TypeDecl] = _astOut.asScala.collect { case node: TypeDecl => node }
def _methodViaAstOut: Iterator[Method] = _astOut.asScala.collect { case node: Method => node }
override def _astOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(1).asInstanceOf[JIterator[StoredNode]]
def _fileViaSourceFileOut: Iterator[File] = _sourceFileOut.asScala.collect { case node: File => node }
override def _sourceFileOut: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(2).asInstanceOf[JIterator[StoredNode]]
def _fileViaAstIn: Option[File] = _astIn.asScala.collect { case node: File => node }.nextOption()
override def _astIn: JIterator[StoredNode] = createAdjacentNodeIteratorByOffSet(3).asInstanceOf[JIterator[StoredNode]]

  override def label: String = {
    NamespaceBlock.Label
  }

  override def productElementLabel(n: Int): String =
    n match {
      case 0 => "id" 
case 1 => "filename" 
case 2 => "fullName" 
case 3 => "name" 
case 4 => "order" 
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
case 1 => filename
case 2 => fullName
case 3 => name
case 4 => order
    }

  override def productPrefix = "NamespaceBlock"
  override def productArity = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NamespaceBlockDb]

  override def property(key:String): AnyRef = {
    key match {
      case "FILENAME" => this._filename
      case "FULL_NAME" => this._fullName
      case "NAME" => this._name
      case "ORDER" => this._order
      
      case _ => null
    }
  }

  override protected def updateSpecificProperty(key:String, value: Object): Unit = {
    key match {
      case "FILENAME" => this._filename = value.asInstanceOf[String]
      case "FULL_NAME" => this._fullName = value.asInstanceOf[String]
      case "NAME" => this._name = value.asInstanceOf[String]
      case "ORDER" => this._order = value.asInstanceOf[java.lang.Integer]
    
      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

override def removeSpecificProperty(key: String): Unit =
  this.updateSpecificProperty(key, null)

override def fromNewNode(someNewNode: NewNode, mapping: NewNode => StoredNode):Unit = {
  //this will throw for bad types -- no need to check by hand, we don't have a better error message
  val other = someNewNode.asInstanceOf[NewNamespaceBlock]
   this._filename = other.filename
   this._fullName = other.fullName
   this._name = other.name
   this._order = other.order

  graph.indexManager.putIfIndexed("FULL_NAME", other.fullName, this.ref)
}

}

/** Traversal steps for NamespaceBlock */
class NamespaceBlockTraversal[NodeType <: NamespaceBlock](val traversal: Traversal[NodeType]) extends AnyVal {

  /** Traverse to filename property */
  def filename: Traversal[String] =
    traversal.map(_.filename)

    /**
    * Traverse to nodes where the filename matches the regular expression `value`
    * */
  def filename(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the filename matches at least one of the regular expressions in `values`
    * */
  def filename(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }

  /**
    * Traverse to nodes where filename matches `value` exactly.
    * */
  def filenameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.filename == value}

  /**
    * Traverse to nodes where filename matches one of the elements in `values` exactly.
    * */
  def filenameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.filename)}
  }

  /**
    * Traverse to nodes where filename does not match the regular expression `value`.
    * */
  def filenameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.filename != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.filename); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where filename does not match any of the regular expressions in `values`.
    * */
  def filenameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.filename); matcher.matches()}}
   }



  /** Traverse to fullName property */
  def fullName: Traversal[String] =
    traversal.map(_.fullName)

    /**
    * Traverse to nodes where the fullName matches the regular expression `value`
    * */
  def fullName(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName == pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where the fullName matches at least one of the regular expressions in `values`
    * */
  def fullName(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
   }

  /**
    * Traverse to nodes where fullName matches `value` exactly.
    * */
  def fullNameExact(value: String): Traversal[NodeType] =
    traversal.filter{node => node.fullName == value}

  /**
    * Traverse to nodes where fullName matches one of the elements in `values` exactly.
    * */
  def fullNameExact(values: String*): Traversal[NodeType] = {
    val vset = values.to(Set)
    traversal.filter{node => vset.contains(node.fullName)}
  }

  /**
    * Traverse to nodes where fullName does not match the regular expression `value`.
    * */
  def fullNameNot(pattern: String): Traversal[NodeType] = {
    if(!Misc.isRegex(pattern)){
      traversal.filter{node => node.fullName != pattern}
    } else {
    val matcher = java.util.regex.Pattern.compile(pattern).matcher("")
    traversal.filter{node =>  matcher.reset(node.fullName); !matcher.matches()}
    }
  }

  /**
    * Traverse to nodes where fullName does not match any of the regular expressions in `values`.
    * */
  def fullNameNot(patterns: String*): Traversal[NodeType] = {
    val matchers = patterns.map{pattern => java.util.regex.Pattern.compile(pattern).matcher("")}.toArray
    traversal.filter{node => !matchers.exists{ matcher => matcher.reset(node.fullName); matcher.matches()}}
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
