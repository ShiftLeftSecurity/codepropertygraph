package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object NamespaceBlock {
  def apply(graph: Graph, id: Long) = new NamespaceBlock(graph, id)

  val Label = "NAMESPACE_BLOCK"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val Filename                         = "FILENAME"
    val FullName                         = "FULL_NAME"
    val LineNumber                       = "LINE_NUMBER"
    val Name                             = "NAME"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, Filename, FullName, LineNumber, Name, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val Filename     = new overflowdb.PropertyKey[String]("FILENAME")
    val FullName     = new overflowdb.PropertyKey[String]("FULL_NAME")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name         = new overflowdb.PropertyKey[String]("NAME")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code     = "<empty>"
    val Filename = "<empty>"
    val FullName = "<empty>"
    val Name     = "<empty>"
    val Order    = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation
    ).asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "REF", "SOURCE_FILE")
    val In: Array[String]  = Array("AST")
  }

  val factory = new NodeFactory[NamespaceBlockDb] {
    override val forLabel = NamespaceBlock.Label

    override def createNode(ref: NodeRef[NamespaceBlockDb]) =
      new NamespaceBlockDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = NamespaceBlock(graph, id)
  }
}

trait NamespaceBlockBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def filename: String
  def fullName: String
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int

}

class NamespaceBlock(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[NamespaceBlockDb](graph_4762, id_4762)
    with NamespaceBlockBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def filename: String              = get().filename
  override def fullName: String              = get().fullName
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"      => NamespaceBlock.PropertyDefaults.Code
      case "FILENAME"  => NamespaceBlock.PropertyDefaults.Filename
      case "FULL_NAME" => NamespaceBlock.PropertyDefaults.FullName
      case "NAME"      => NamespaceBlock.PropertyDefaults.Name
      case "ORDER"     => NamespaceBlock.PropertyDefaults.Order
      case _           => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method] = get()._methodViaAstOut

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaAstOut

  def refOut: Iterator[Namespace] = get().refOut
  override def _refOut            = get()._refOut

  /** Traverse to NAMESPACE via REF OUT edge.
    */
  def _namespaceViaRefOut: overflowdb.traversal.Traversal[Namespace] = get()._namespaceViaRefOut

  def sourceFileOut: Iterator[File] = get().sourceFileOut
  override def _sourceFileOut       = get()._sourceFileOut

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = get()._fileViaSourceFileOut

  def astIn: Iterator[File] = get().astIn
  override def _astIn       = get()._astIn

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: Option[File] = get()._fileViaAstIn

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
    NamespaceBlock.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "filename"
      case 4 => "fullName"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => filename
      case 4 => fullName
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "NamespaceBlock"
  override def productArity  = 8
}

class NamespaceBlockDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with NamespaceBlockBase {

  override def layoutInformation: NodeLayoutInformation = NamespaceBlock.layoutInformation

  private var _code: String          = NamespaceBlock.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _filename: String      = NamespaceBlock.PropertyDefaults.Filename
  def filename: String               = _filename
  private var _fullName: String      = NamespaceBlock.PropertyDefaults.FullName
  def fullName: String               = _fullName
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _name: String          = NamespaceBlock.PropertyDefaults.Name
  def name: String                   = _name
  private var _order: scala.Int      = NamespaceBlock.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("FILENAME", filename)
    properties.put("FULL_NAME", fullName)
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
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[AstNode]                                = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astOut                                         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method] = astOut.collectAll[Method]
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = astOut.collectAll[TypeDecl]

  def refOut: Iterator[Namespace] = createAdjacentNodeScalaIteratorByOffSet[Namespace](1)
  override def _refOut            = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _namespaceViaRefOut: overflowdb.traversal.Traversal[Namespace] = refOut.collectAll[Namespace]

  def sourceFileOut: Iterator[File] = createAdjacentNodeScalaIteratorByOffSet[File](2)
  override def _sourceFileOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = sourceFileOut.collectAll[File]

  def astIn: Iterator[File]       = createAdjacentNodeScalaIteratorByOffSet[File](3)
  override def _astIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _fileViaAstIn: Option[File] = astIn.collectAll[File].nextOption()

  override def label: String = {
    NamespaceBlock.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "filename"
      case 4 => "fullName"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => filename
      case 4 => fullName
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "NamespaceBlock"
  override def productArity  = 8

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NamespaceBlockDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
      case "FILENAME"      => this._filename
      case "FULL_NAME"     => this._fullName
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
      case "FILENAME"      => this._filename = value.asInstanceOf[String]
      case "FULL_NAME"     => this._fullName = value.asInstanceOf[String]
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
    this._code = newNode.asInstanceOf[NewNamespaceBlock].code
    this._columnNumber = newNode.asInstanceOf[NewNamespaceBlock].columnNumber.orNull
    this._filename = newNode.asInstanceOf[NewNamespaceBlock].filename
    this._fullName = newNode.asInstanceOf[NewNamespaceBlock].fullName
    this._lineNumber = newNode.asInstanceOf[NewNamespaceBlock].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewNamespaceBlock].name
    this._order = newNode.asInstanceOf[NewNamespaceBlock].order

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewNamespaceBlock].fullName, this.ref)
  }

}
