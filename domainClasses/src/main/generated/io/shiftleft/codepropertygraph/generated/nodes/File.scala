package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object File {
  def apply(graph: Graph, id: Long) = new File(graph, id)

  val Label = "FILE"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val Content                          = "CONTENT"
    val Hash                             = "HASH"
    val LineNumber                       = "LINE_NUMBER"
    val Name                             = "NAME"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, Content, Hash, LineNumber, Name, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val Content      = new overflowdb.PropertyKey[String]("CONTENT")
    val Hash         = new overflowdb.PropertyKey[String]("HASH")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name         = new overflowdb.PropertyKey[String]("NAME")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code    = "<empty>"
    val Content = "<empty>"
    val Name    = "<empty>"
    val Order   = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "CONTAINS", "TAGGED_BY")
    val In: Array[String]  = Array("SOURCE_FILE")
  }

  val factory = new NodeFactory[FileDb] {
    override val forLabel = File.Label

    override def createNode(ref: NodeRef[FileDb]) =
      new FileDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = File(graph, id)
  }
}

trait FileBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def content: String
  def hash: Option[String]
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int

}

class File(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[FileDb](graph_4762, id_4762)
    with FileBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def content: String               = get().content
  override def hash: Option[String]          = get().hash
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"    => File.PropertyDefaults.Code
      case "CONTENT" => File.PropertyDefaults.Content
      case "NAME"    => File.PropertyDefaults.Name
      case "ORDER"   => File.PropertyDefaults.Order
      case _         => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to COMMENT via AST OUT edge.
    */
  def comment: overflowdb.traversal.Traversal[Comment] = get().comment

  /** Traverse to IMPORT via AST OUT edge.
    */
  def _importViaAstOut: overflowdb.traversal.Traversal[Import] = get()._importViaAstOut

  /** Traverse to NAMESPACE_BLOCK via AST OUT edge.
    */
  def _namespaceBlockViaAstOut: overflowdb.traversal.Traversal[NamespaceBlock] = get()._namespaceBlockViaAstOut

  def containsOut: Iterator[AstNode] = get().containsOut
  override def _containsOut          = get()._containsOut

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def _methodViaContainsOut: overflowdb.traversal.Traversal[Method] = get()._methodViaContainsOut

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def _templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = get()._templateDomViaContainsOut

  /** Traverse to TYPE_DECL via CONTAINS OUT edge.
    */
  def _typeDeclViaContainsOut: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaContainsOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def sourceFileIn: Iterator[AstNode] = get().sourceFileIn
  override def _sourceFileIn          = get()._sourceFileIn

  /** Traverse to METHOD via SOURCE_FILE IN edge.
    */
  def method: overflowdb.traversal.Traversal[Method] = get().method

  /** Traverse to NAMESPACE_BLOCK via SOURCE_FILE IN edge.
    */
  def namespaceBlock: overflowdb.traversal.Traversal[NamespaceBlock] = get().namespaceBlock

  /** Traverse to TYPE_DECL via SOURCE_FILE IN edge.
    */
  def typeDecl: overflowdb.traversal.Traversal[TypeDecl] = get().typeDecl

  // In view of https://github.com/scala/bug/issues/4762 it is advisable to use different variable names in
  // patterns like `class Base(x:Int)` and `class Derived(x:Int) extends Base(x)`.
  // This must become `class Derived(x_4762:Int) extends Base(x_4762)`.
  // Otherwise, it is very hard to figure out whether uses of the identifier `x` refer to the base class x
  // or the derived class x.
  // When using that pattern, the class parameter `x_47672` should only be used in the `extends Base(x_4762)`
  // clause and nowhere else. Otherwise, the compiler may well decide that this is not just a constructor
  // parameter but also a field of the class, and we end up with two `x` fields. At best, this wastes memory;
  // at worst both fields go out-of-sync for hard-to-debug correctness bugs.

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def canEqual(that: Any): Boolean                                        = get.canEqual(that)
  override def label: String = {
    File.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "content"
      case 4 => "hash"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => content
      case 4 => hash
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "File"
  override def productArity  = 8
}

class FileDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with FileBase {

  override def layoutInformation: NodeLayoutInformation = File.layoutInformation

  private var _code: String          = File.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _content: String       = File.PropertyDefaults.Content
  def content: String                = _content
  private var _hash: String          = null
  def hash: Option[String]           = Option(_hash)
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _name: String          = File.PropertyDefaults.Name
  def name: String                   = _name
  private var _order: scala.Int      = File.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("CONTENT", content)
    hash.map { value => properties.put("HASH", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (!(("<empty>") == content)) { properties.put("CONTENT", content) }
    hash.map { value => properties.put("HASH", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[AstNode]                                = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astOut                                         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def comment: overflowdb.traversal.Traversal[Comment]         = astOut.collectAll[Comment]
  def _importViaAstOut: overflowdb.traversal.Traversal[Import] = astOut.collectAll[Import]
  def _namespaceBlockViaAstOut: overflowdb.traversal.Traversal[NamespaceBlock] = astOut.collectAll[NamespaceBlock]

  def containsOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](1)
  override def _containsOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _methodViaContainsOut: overflowdb.traversal.Traversal[Method]           = containsOut.collectAll[Method]
  def _templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = containsOut.collectAll[TemplateDom]
  def _typeDeclViaContainsOut: overflowdb.traversal.Traversal[TypeDecl]       = containsOut.collectAll[TypeDecl]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](2)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def sourceFileIn: Iterator[AstNode]                = createAdjacentNodeScalaIteratorByOffSet[AstNode](3)
  override def _sourceFileIn                         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def method: overflowdb.traversal.Traversal[Method] = sourceFileIn.collectAll[Method]
  def namespaceBlock: overflowdb.traversal.Traversal[NamespaceBlock] = sourceFileIn.collectAll[NamespaceBlock]
  def typeDecl: overflowdb.traversal.Traversal[TypeDecl]             = sourceFileIn.collectAll[TypeDecl]

  override def label: String = {
    File.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "content"
      case 4 => "hash"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => content
      case 4 => hash
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "File"
  override def productArity  = 8

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FileDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
      case "CONTENT"       => this._content
      case "HASH"          => this._hash
      case "LINE_NUMBER"   => this._lineNumber
      case "NAME"          => this._name
      case "ORDER"         => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"          => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[Integer]
      case "CONTENT"       => this._content = value.asInstanceOf[String]
      case "HASH"          => this._hash = value.asInstanceOf[String]
      case "LINE_NUMBER"   => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"          => this._name = value.asInstanceOf[String]
      case "ORDER"         => this._order = value.asInstanceOf[scala.Int]

      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

  override def removeSpecificProperty(key: String): Unit =
    this.updateSpecificProperty(key, null)

  override def _initializeFromDetached(
    data: overflowdb.DetachedNodeData,
    mapper: java.util.function.Function[overflowdb.DetachedNodeData, Node]
  ) =
    fromNewNode(data.asInstanceOf[NewNode], nn => mapper.apply(nn).asInstanceOf[StoredNode])

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = {
    this._code = newNode.asInstanceOf[NewFile].code
    this._columnNumber = newNode.asInstanceOf[NewFile].columnNumber.orNull
    this._content = newNode.asInstanceOf[NewFile].content
    this._hash = newNode.asInstanceOf[NewFile].hash.orNull
    this._lineNumber = newNode.asInstanceOf[NewFile].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewFile].name
    this._order = newNode.asInstanceOf[NewFile].order

  }

}
