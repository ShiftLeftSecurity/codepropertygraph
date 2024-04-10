package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object TemplateDom {
  def apply(graph: Graph, id: Long) = new TemplateDom(graph, id)

  val Label = "TEMPLATE_DOM"

  object PropertyNames {
    val ArgumentIndex                    = "ARGUMENT_INDEX"
    val ArgumentName                     = "ARGUMENT_NAME"
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Name                             = "NAME"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(ArgumentIndex, ArgumentName, Code, ColumnNumber, LineNumber, Name, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName  = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val Code          = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber  = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber    = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name          = new overflowdb.PropertyKey[String]("NAME")
    val Order         = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val ArgumentIndex = -1: Int
    val Code          = "<empty>"
    val Name          = "<empty>"
    val Order         = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ARGUMENT", "AST", "REACHING_DEF", "TAGGED_BY")
    val In: Array[String]  = Array("ARGUMENT", "AST", "CFG", "CONTAINS", "REACHING_DEF")
  }

  val factory = new NodeFactory[TemplateDomDb] {
    override val forLabel = TemplateDom.Label

    override def createNode(ref: NodeRef[TemplateDomDb]) =
      new TemplateDomDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TemplateDom(graph, id)
  }
}

trait TemplateDomBase extends AbstractNode with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int

}

class TemplateDom(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[TemplateDomDb](graph_4762, id_4762)
    with TemplateDomBase
    with StoredNode
    with Expression {
  override def argumentIndex: scala.Int      = get().argumentIndex
  override def argumentName: Option[String]  = get().argumentName
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX" => TemplateDom.PropertyDefaults.ArgumentIndex
      case "CODE"           => TemplateDom.PropertyDefaults.Code
      case "NAME"           => TemplateDom.PropertyDefaults.Name
      case "ORDER"          => TemplateDom.PropertyDefaults.Order
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[TemplateDom] = get().argumentOut
  override def _argumentOut              = get()._argumentOut

  def astOut: Iterator[Expression] = get().astOut
  override def _astOut             = get()._astOut

  /** Traverse to EXPRESSION via AST OUT edge.
    */
  def _expressionViaAstOut: overflowdb.traversal.Traversal[Expression] = get()._expressionViaAstOut

  def reachingDefOut: Iterator[Expression] = get().reachingDefOut
  override def _reachingDefOut             = get()._reachingDefOut

  /** Traverse to EXPRESSION via REACHING_DEF OUT edge.
    */
  def _expressionViaReachingDefOut: overflowdb.traversal.Traversal[Expression] = get()._expressionViaReachingDefOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def argumentIn: Iterator[Expression] = get().argumentIn
  override def _argumentIn             = get()._argumentIn

  /** Traverse to EXPRESSION via ARGUMENT IN edge.
    */
  def _expressionViaArgumentIn: overflowdb.traversal.Traversal[Expression] = get()._expressionViaArgumentIn

  def astIn: Iterator[TemplateDom] = get().astIn
  override def _astIn              = get()._astIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def containsIn: Iterator[AstNode] = get().containsIn
  override def _containsIn          = get()._containsIn

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = get()._fileViaContainsIn

  /** Traverse to METHOD via CONTAINS IN edge.
    */
  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = get()._methodViaContainsIn

  def reachingDefIn: Iterator[TemplateDom] = get().reachingDefIn
  override def _reachingDefIn              = get()._reachingDefIn

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
    TemplateDom.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "TemplateDom"
  override def productArity  = 8
}

class TemplateDomDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with Expression with TemplateDomBase {

  override def layoutInformation: NodeLayoutInformation = TemplateDom.layoutInformation

  private var _argumentIndex: scala.Int = TemplateDom.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int          = _argumentIndex
  private var _argumentName: String     = null
  def argumentName: Option[String]      = Option(_argumentName)
  private var _code: String             = TemplateDom.PropertyDefaults.Code
  def code: String                      = _code
  private var _columnNumber: Integer    = null
  def columnNumber: Option[Integer]     = Option(_columnNumber)
  private var _lineNumber: Integer      = null
  def lineNumber: Option[Integer]       = Option(_lineNumber)
  private var _name: String             = TemplateDom.PropertyDefaults.Name
  def name: String                      = _name
  private var _order: scala.Int         = TemplateDom.PropertyDefaults.Order
  def order: scala.Int                  = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
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
    if (!((-1: Int) == argumentIndex)) { properties.put("ARGUMENT_INDEX", argumentIndex) }
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](0)
  override def _argumentOut              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  def astOut: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](1)
  override def _astOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _expressionViaAstOut: overflowdb.traversal.Traversal[Expression] = astOut.collectAll[Expression]

  def reachingDefOut: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](2)
  override def _reachingDefOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _expressionViaReachingDefOut: overflowdb.traversal.Traversal[Expression] = reachingDefOut.collectAll[Expression]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](3)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def argumentIn: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](4)
  override def _argumentIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _expressionViaArgumentIn: overflowdb.traversal.Traversal[Expression] = argumentIn.collectAll[Expression]

  def astIn: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](5)
  override def _astIn              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](6)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)

  def containsIn: Iterator[AstNode]                            = createAdjacentNodeScalaIteratorByOffSet[AstNode](7)
  override def _containsIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = containsIn.collectAll[File]
  def _methodViaContainsIn: overflowdb.traversal.Traversal[Method] = containsIn.collectAll[Method]

  def reachingDefIn: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](8)
  override def _reachingDefIn              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)

  override def label: String = {
    TemplateDom.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => name
      case 7 => order
    }

  override def productPrefix = "TemplateDom"
  override def productArity  = 8

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TemplateDomDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "ARGUMENT_NAME"  => this._argumentName
      case "CODE"           => this._code
      case "COLUMN_NUMBER"  => this._columnNumber
      case "LINE_NUMBER"    => this._lineNumber
      case "NAME"           => this._name
      case "ORDER"          => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[scala.Int]
      case "ARGUMENT_NAME"  => this._argumentName = value.asInstanceOf[String]
      case "CODE"           => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"  => this._columnNumber = value.asInstanceOf[Integer]
      case "LINE_NUMBER"    => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"           => this._name = value.asInstanceOf[String]
      case "ORDER"          => this._order = value.asInstanceOf[scala.Int]

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
    this._argumentIndex = newNode.asInstanceOf[NewTemplateDom].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewTemplateDom].argumentName.orNull
    this._code = newNode.asInstanceOf[NewTemplateDom].code
    this._columnNumber = newNode.asInstanceOf[NewTemplateDom].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewTemplateDom].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewTemplateDom].name
    this._order = newNode.asInstanceOf[NewTemplateDom].order

  }

}
