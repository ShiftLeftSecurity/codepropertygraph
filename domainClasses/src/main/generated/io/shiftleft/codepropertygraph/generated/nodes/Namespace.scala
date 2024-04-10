package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Namespace {
  def apply(graph: Graph, id: Long) = new Namespace(graph, id)

  val Label = "NAMESPACE"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Name                             = "NAME"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, LineNumber, Name, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name         = new overflowdb.PropertyKey[String]("NAME")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code  = "<empty>"
    val Name  = "<empty>"
    val Order = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List().asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array("REF")
  }

  val factory = new NodeFactory[NamespaceDb] {
    override val forLabel = Namespace.Label

    override def createNode(ref: NodeRef[NamespaceDb]) =
      new NamespaceDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Namespace(graph, id)
  }
}

trait NamespaceBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int

}

class Namespace(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[NamespaceDb](graph_4762, id_4762)
    with NamespaceBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"  => Namespace.PropertyDefaults.Code
      case "NAME"  => Namespace.PropertyDefaults.Name
      case "ORDER" => Namespace.PropertyDefaults.Order
      case _       => super.propertyDefaultValue(propertyKey)
    }
  }

  def refIn: Iterator[NamespaceBlock] = get().refIn
  override def _refIn                 = get()._refIn

  /** Traverse to NAMESPACE_BLOCK via REF IN edge.
    */
  def _namespaceBlockViaRefIn: overflowdb.traversal.Traversal[NamespaceBlock] = get()._namespaceBlockViaRefIn

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
    Namespace.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "name"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => name
      case 5 => order
    }

  override def productPrefix = "Namespace"
  override def productArity  = 6
}

class NamespaceDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with NamespaceBase {

  override def layoutInformation: NodeLayoutInformation = Namespace.layoutInformation

  private var _code: String          = Namespace.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _name: String          = Namespace.PropertyDefaults.Name
  def name: String                   = _name
  private var _order: scala.Int      = Namespace.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
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
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def refIn: Iterator[NamespaceBlock] = createAdjacentNodeScalaIteratorByOffSet[NamespaceBlock](0)
  override def _refIn                 = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _namespaceBlockViaRefIn: overflowdb.traversal.Traversal[NamespaceBlock] = refIn.collectAll[NamespaceBlock]

  override def label: String = {
    Namespace.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "name"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => name
      case 5 => order
    }

  override def productPrefix = "Namespace"
  override def productArity  = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NamespaceDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
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
    this._code = newNode.asInstanceOf[NewNamespace].code
    this._columnNumber = newNode.asInstanceOf[NewNamespace].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewNamespace].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewNamespace].name
    this._order = newNode.asInstanceOf[NewNamespace].order

  }

}
