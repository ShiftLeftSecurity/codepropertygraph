package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Modifier {
  def apply(graph: Graph, id: Long) = new Modifier(graph, id)

  val Label = "MODIFIER"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val ModifierType                     = "MODIFIER_TYPE"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, LineNumber, ModifierType, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val ModifierType = new overflowdb.PropertyKey[String]("MODIFIER_TYPE")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code         = "<empty>"
    val ModifierType = "<empty>"
    val Order        = -1: Int
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

  val factory = new NodeFactory[ModifierDb] {
    override val forLabel = Modifier.Label

    override def createNode(ref: NodeRef[ModifierDb]) =
      new ModifierDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Modifier(graph, id)
  }
}

trait ModifierBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def modifierType: String
  def order: scala.Int

}

class Modifier(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ModifierDb](graph_4762, id_4762)
    with ModifierBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def modifierType: String          = get().modifierType
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"          => Modifier.PropertyDefaults.Code
      case "MODIFIER_TYPE" => Modifier.PropertyDefaults.ModifierType
      case "ORDER"         => Modifier.PropertyDefaults.Order
      case _               => super.propertyDefaultValue(propertyKey)
    }
  }

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaAstIn

  /** Traverse to MEMBER via AST IN edge.
    */
  def _memberViaAstIn: overflowdb.traversal.Traversal[Member] = get()._memberViaAstIn

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Method = get()._methodViaAstIn

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: TypeDecl = get()._typeDeclViaAstIn

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaAstIn

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
    Modifier.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "modifierType"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => modifierType
      case 5 => order
    }

  override def productPrefix = "Modifier"
  override def productArity  = 6
}

class ModifierDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with ModifierBase {

  override def layoutInformation: NodeLayoutInformation = Modifier.layoutInformation

  private var _code: String          = Modifier.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _modifierType: String  = Modifier.PropertyDefaults.ModifierType
  def modifierType: String           = _modifierType
  private var _order: scala.Int      = Modifier.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("MODIFIER_TYPE", modifierType)
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == modifierType)) { properties.put("MODIFIER_TYPE", modifierType) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def astIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = astIn.collectAll[ControlStructure]
  def _memberViaAstIn: overflowdb.traversal.Traversal[Member]                     = astIn.collectAll[Member]
  def _methodViaAstIn: Method = try { astIn.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this MODIFIER node with id=" + id,
        e
      )
  }
  def _typeDeclViaAstIn: TypeDecl = try { astIn.collectAll[TypeDecl].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent TYPE_DECL is mandatory, but not defined for this MODIFIER node with id=" + id,
        e
      )
  }
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = astIn.collectAll[Unknown]

  override def label: String = {
    Modifier.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "modifierType"
      case 5 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => modifierType
      case 5 => order
    }

  override def productPrefix = "Modifier"
  override def productArity  = 6

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ModifierDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
      case "LINE_NUMBER"   => this._lineNumber
      case "MODIFIER_TYPE" => this._modifierType
      case "ORDER"         => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"          => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[Integer]
      case "LINE_NUMBER"   => this._lineNumber = value.asInstanceOf[Integer]
      case "MODIFIER_TYPE" => this._modifierType = value.asInstanceOf[String]
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
    this._code = newNode.asInstanceOf[NewModifier].code
    this._columnNumber = newNode.asInstanceOf[NewModifier].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewModifier].lineNumber.orNull
    this._modifierType = newNode.asInstanceOf[NewModifier].modifierType
    this._order = newNode.asInstanceOf[NewModifier].order

  }

}
