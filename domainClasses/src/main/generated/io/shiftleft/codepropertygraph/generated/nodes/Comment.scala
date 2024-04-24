package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Comment {
  def apply(graph: Graph, id: Long) = new Comment(graph, id)

  val Label = "COMMENT"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val Filename                         = "FILENAME"
    val LineNumber                       = "LINE_NUMBER"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, Filename, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val Filename     = new overflowdb.PropertyKey[String]("FILENAME")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code     = "<empty>"
    val Filename = "<empty>"
    val Order    = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("SOURCE_FILE")
    val In: Array[String]  = Array("AST", "SOURCE_FILE")
  }

  val factory = new NodeFactory[CommentDb] {
    override val forLabel = Comment.Label

    override def createNode(ref: NodeRef[CommentDb]) =
      new CommentDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Comment(graph, id)
  }
}

trait CommentBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def filename: String
  def lineNumber: Option[Integer]
  def order: scala.Int

}

class Comment(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[CommentDb](graph_4762, id_4762)
    with CommentBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def filename: String              = get().filename
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"     => Comment.PropertyDefaults.Code
      case "FILENAME" => Comment.PropertyDefaults.Filename
      case "ORDER"    => Comment.PropertyDefaults.Order
      case _          => super.propertyDefaultValue(propertyKey)
    }
  }

  def sourceFileOut: Iterator[Comment] = get().sourceFileOut
  override def _sourceFileOut          = get()._sourceFileOut

  /** Traverse to COMMENT via SOURCE_FILE OUT edge.
    */
  def file: overflowdb.traversal.Traversal[Comment] = get().file

  def astIn: Iterator[File] = get().astIn
  override def _astIn       = get()._astIn

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: overflowdb.traversal.Traversal[File] = get()._fileViaAstIn

  def sourceFileIn: Iterator[Comment] = get().sourceFileIn
  override def _sourceFileIn          = get()._sourceFileIn

  /** Traverse to COMMENT via SOURCE_FILE IN edge.
    */
  def _commentViaSourceFileIn: overflowdb.traversal.Traversal[Comment] = get()._commentViaSourceFileIn

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
    Comment.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "filename"
      case 4 => "lineNumber"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => filename
      case 4 => lineNumber
      case 5 => order
    }

  override def productPrefix = "Comment"
  override def productArity  = 6
}

class CommentDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with CommentBase {

  override def layoutInformation: NodeLayoutInformation = Comment.layoutInformation

  private var _code: String          = Comment.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _filename: String      = Comment.PropertyDefaults.Filename
  def filename: String               = _filename
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _order: scala.Int      = Comment.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("FILENAME", filename)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def sourceFileOut: Iterator[Comment]              = createAdjacentNodeScalaIteratorByOffSet[Comment](0)
  override def _sourceFileOut                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def file: overflowdb.traversal.Traversal[Comment] = sourceFileOut.collectAll[Comment]

  def astIn: Iterator[File]                               = createAdjacentNodeScalaIteratorByOffSet[File](1)
  override def _astIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _fileViaAstIn: overflowdb.traversal.Traversal[File] = astIn.collectAll[File]

  def sourceFileIn: Iterator[Comment] = createAdjacentNodeScalaIteratorByOffSet[Comment](2)
  override def _sourceFileIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _commentViaSourceFileIn: overflowdb.traversal.Traversal[Comment] = sourceFileIn.collectAll[Comment]

  override def label: String = {
    Comment.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "filename"
      case 4 => "lineNumber"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => filename
      case 4 => lineNumber
      case 5 => order
    }

  override def productPrefix = "Comment"
  override def productArity  = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[CommentDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
      case "FILENAME"      => this._filename
      case "LINE_NUMBER"   => this._lineNumber
      case "ORDER"         => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"          => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[Integer]
      case "FILENAME"      => this._filename = value.asInstanceOf[String]
      case "LINE_NUMBER"   => this._lineNumber = value.asInstanceOf[Integer]
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
    this._code = newNode.asInstanceOf[NewComment].code
    this._columnNumber = newNode.asInstanceOf[NewComment].columnNumber.orNull
    this._filename = newNode.asInstanceOf[NewComment].filename
    this._lineNumber = newNode.asInstanceOf[NewComment].lineNumber.orNull
    this._order = newNode.asInstanceOf[NewComment].order

  }

}
