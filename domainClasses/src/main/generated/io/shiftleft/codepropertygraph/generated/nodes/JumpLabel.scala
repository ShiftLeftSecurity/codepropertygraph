package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object JumpLabel {
  def apply(graph: Graph, id: Long) = new JumpLabel(graph, id)

  val Label = "JUMP_LABEL"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Name                             = "NAME"
    val Order                            = "ORDER"
    val ParserTypeName                   = "PARSER_TYPE_NAME"
    val all: Set[String]                 = Set(Code, ColumnNumber, LineNumber, Name, Order, ParserTypeName)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code           = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber   = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber     = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name           = new overflowdb.PropertyKey[String]("NAME")
    val Order          = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val ParserTypeName = new overflowdb.PropertyKey[String]("PARSER_TYPE_NAME")

  }

  object PropertyDefaults {
    val Code           = "<empty>"
    val Name           = "<empty>"
    val Order          = -1: Int
    val ParserTypeName = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array("AST")
  }

  val factory = new NodeFactory[JumpLabelDb] {
    override val forLabel = JumpLabel.Label

    override def createNode(ref: NodeRef[JumpLabelDb]) =
      new JumpLabelDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = JumpLabel(graph, id)
  }
}

trait JumpLabelBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int
  def parserTypeName: String

}

class JumpLabel(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[JumpLabelDb](graph_4762, id_4762)
    with JumpLabelBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def parserTypeName: String        = get().parserTypeName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"             => JumpLabel.PropertyDefaults.Code
      case "NAME"             => JumpLabel.PropertyDefaults.Name
      case "ORDER"            => JumpLabel.PropertyDefaults.Order
      case "PARSER_TYPE_NAME" => JumpLabel.PropertyDefaults.ParserTypeName
      case _                  => super.propertyDefaultValue(propertyKey)
    }
  }

  def astIn: Iterator[ControlStructure] = get().astIn
  override def _astIn                   = get()._astIn

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaAstIn

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
    JumpLabel.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "name"
      case 5 => "order"
      case 6 => "parserTypeName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => name
      case 5 => order
      case 6 => parserTypeName
    }

  override def productPrefix = "JumpLabel"
  override def productArity  = 7
}

class JumpLabelDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with JumpLabelBase {

  override def layoutInformation: NodeLayoutInformation = JumpLabel.layoutInformation

  private var _code: String           = JumpLabel.PropertyDefaults.Code
  def code: String                    = _code
  private var _columnNumber: Integer  = null
  def columnNumber: Option[Integer]   = Option(_columnNumber)
  private var _lineNumber: Integer    = null
  def lineNumber: Option[Integer]     = Option(_lineNumber)
  private var _name: String           = JumpLabel.PropertyDefaults.Name
  def name: String                    = _name
  private var _order: scala.Int       = JumpLabel.PropertyDefaults.Order
  def order: scala.Int                = _order
  private var _parserTypeName: String = JumpLabel.PropertyDefaults.ParserTypeName
  def parserTypeName: String          = _parserTypeName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
    properties.put("ORDER", order)
    properties.put("PARSER_TYPE_NAME", parserTypeName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (!(("<empty>") == parserTypeName)) { properties.put("PARSER_TYPE_NAME", parserTypeName) }

    properties
  }

  import overflowdb.traversal._
  def astIn: Iterator[ControlStructure] = createAdjacentNodeScalaIteratorByOffSet[ControlStructure](0)
  override def _astIn                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = astIn.collectAll[ControlStructure]

  override def label: String = {
    JumpLabel.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "name"
      case 5 => "order"
      case 6 => "parserTypeName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => name
      case 5 => order
      case 6 => parserTypeName
    }

  override def productPrefix = "JumpLabel"
  override def productArity  = 7

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[JumpLabelDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"             => this._code
      case "COLUMN_NUMBER"    => this._columnNumber
      case "LINE_NUMBER"      => this._lineNumber
      case "NAME"             => this._name
      case "ORDER"            => this._order
      case "PARSER_TYPE_NAME" => this._parserTypeName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"             => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"    => this._columnNumber = value.asInstanceOf[Integer]
      case "LINE_NUMBER"      => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"             => this._name = value.asInstanceOf[String]
      case "ORDER"            => this._order = value.asInstanceOf[scala.Int]
      case "PARSER_TYPE_NAME" => this._parserTypeName = value.asInstanceOf[String]

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
    this._code = newNode.asInstanceOf[NewJumpLabel].code
    this._columnNumber = newNode.asInstanceOf[NewJumpLabel].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewJumpLabel].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewJumpLabel].name
    this._order = newNode.asInstanceOf[NewJumpLabel].order
    this._parserTypeName = newNode.asInstanceOf[NewJumpLabel].parserTypeName

  }

}
