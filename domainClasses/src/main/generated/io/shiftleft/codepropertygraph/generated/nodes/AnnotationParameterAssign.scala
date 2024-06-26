package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object AnnotationParameterAssign {
  def apply(graph: Graph, id: Long) = new AnnotationParameterAssign(graph, id)

  val Label = "ANNOTATION_PARAMETER_ASSIGN"

  object PropertyNames {
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(Code, ColumnNumber, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code         = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val LineNumber   = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order        = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code  = "<empty>"
    val Order = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation).asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST")
    val In: Array[String]  = Array("AST")
  }

  val factory = new NodeFactory[AnnotationParameterAssignDb] {
    override val forLabel = AnnotationParameterAssign.Label

    override def createNode(ref: NodeRef[AnnotationParameterAssignDb]) =
      new AnnotationParameterAssignDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = AnnotationParameterAssign(graph, id)
  }
}

trait AnnotationParameterAssignBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def lineNumber: Option[Integer]
  def order: scala.Int

}

class AnnotationParameterAssign(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[AnnotationParameterAssignDb](graph_4762, id_4762)
    with AnnotationParameterAssignBase
    with StoredNode
    with AstNode {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def order: scala.Int              = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"  => AnnotationParameterAssign.PropertyDefaults.Code
      case "ORDER" => AnnotationParameterAssign.PropertyDefaults.Order
      case _       => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstOut

  /** Traverse to ANNOTATION_LITERAL via AST OUT edge.
    */
  def _annotationLiteralViaAstOut: overflowdb.traversal.Traversal[AnnotationLiteral] = get()._annotationLiteralViaAstOut

  /** Traverse to ANNOTATION_PARAMETER via AST OUT edge.
    */
  def _annotationParameterViaAstOut: overflowdb.traversal.Traversal[AnnotationParameter] =
    get()._annotationParameterViaAstOut

  /** Traverse to ARRAY_INITIALIZER via AST OUT edge.
    */
  def _arrayInitializerViaAstOut: overflowdb.traversal.Traversal[ArrayInitializer] = get()._arrayInitializerViaAstOut

  def astIn: Iterator[Annotation] = get().astIn
  override def _astIn             = get()._astIn

  /** Traverse to ANNOTATION via AST IN edge.
    */
  def _annotationViaAstIn: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstIn

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
    AnnotationParameterAssign.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => order
    }

  override def productPrefix = "AnnotationParameterAssign"
  override def productArity  = 5
}

class AnnotationParameterAssignDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with AnnotationParameterAssignBase {

  override def layoutInformation: NodeLayoutInformation = AnnotationParameterAssign.layoutInformation

  private var _code: String          = AnnotationParameterAssign.PropertyDefaults.Code
  def code: String                   = _code
  private var _columnNumber: Integer = null
  def columnNumber: Option[Integer]  = Option(_columnNumber)
  private var _lineNumber: Integer   = null
  def lineNumber: Option[Integer]    = Option(_lineNumber)
  private var _order: scala.Int      = AnnotationParameterAssign.PropertyDefaults.Order
  def order: scala.Int               = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = astOut.collectAll[Annotation]
  def _annotationLiteralViaAstOut: overflowdb.traversal.Traversal[AnnotationLiteral] =
    astOut.collectAll[AnnotationLiteral]
  def _annotationParameterViaAstOut: overflowdb.traversal.Traversal[AnnotationParameter] =
    astOut.collectAll[AnnotationParameter]
  def _arrayInitializerViaAstOut: overflowdb.traversal.Traversal[ArrayInitializer] = astOut.collectAll[ArrayInitializer]

  def astIn: Iterator[Annotation] = createAdjacentNodeScalaIteratorByOffSet[Annotation](1)
  override def _astIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _annotationViaAstIn: overflowdb.traversal.Traversal[Annotation] = astIn.collectAll[Annotation]

  override def label: String = {
    AnnotationParameterAssign.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "lineNumber"
      case 4 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => lineNumber
      case 4 => order
    }

  override def productPrefix = "AnnotationParameterAssign"
  override def productArity  = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[AnnotationParameterAssignDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"          => this._code
      case "COLUMN_NUMBER" => this._columnNumber
      case "LINE_NUMBER"   => this._lineNumber
      case "ORDER"         => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"          => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER" => this._columnNumber = value.asInstanceOf[Integer]
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
    this._code = newNode.asInstanceOf[NewAnnotationParameterAssign].code
    this._columnNumber = newNode.asInstanceOf[NewAnnotationParameterAssign].columnNumber.orNull
    this._lineNumber = newNode.asInstanceOf[NewAnnotationParameterAssign].lineNumber.orNull
    this._order = newNode.asInstanceOf[NewAnnotationParameterAssign].order

  }

}
