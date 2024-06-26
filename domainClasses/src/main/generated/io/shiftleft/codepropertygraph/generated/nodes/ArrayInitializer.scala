package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object ArrayInitializer {
  def apply(graph: Graph, id: Long) = new ArrayInitializer(graph, id)

  val Label = "ARRAY_INITIALIZER"

  object PropertyNames {
    val ArgumentIndex                    = "ARGUMENT_INDEX"
    val ArgumentName                     = "ARGUMENT_NAME"
    val Code                             = "CODE"
    val ColumnNumber                     = "COLUMN_NUMBER"
    val LineNumber                       = "LINE_NUMBER"
    val Order                            = "ORDER"
    val all: Set[String]                 = Set(ArgumentIndex, ArgumentName, Code, ColumnNumber, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName  = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val Code          = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber  = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val LineNumber    = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val Order         = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val ArgumentIndex = -1: Int
    val Code          = "<empty>"
    val Order         = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ARGUMENT", "AST", "EVAL_TYPE")
    val In: Array[String]  = Array("AST", "CFG", "REACHING_DEF")
  }

  val factory = new NodeFactory[ArrayInitializerDb] {
    override val forLabel = ArrayInitializer.Label

    override def createNode(ref: NodeRef[ArrayInitializerDb]) =
      new ArrayInitializerDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = ArrayInitializer(graph, id)
  }
}

trait ArrayInitializerBase extends AbstractNode with AstNodeBase with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[scala.Int]
  def lineNumber: Option[scala.Int]
  def order: scala.Int

}

class ArrayInitializer(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ArrayInitializerDb](graph_4762, id_4762)
    with ArrayInitializerBase
    with StoredNode
    with AstNode
    with Expression {
  override def argumentIndex: scala.Int        = get().argumentIndex
  override def argumentName: Option[String]    = get().argumentName
  override def code: String                    = get().code
  override def columnNumber: Option[scala.Int] = get().columnNumber
  override def lineNumber: Option[scala.Int]   = get().lineNumber
  override def order: scala.Int                = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX" => ArrayInitializer.PropertyDefaults.ArgumentIndex
      case "CODE"           => ArrayInitializer.PropertyDefaults.Code
      case "ORDER"          => ArrayInitializer.PropertyDefaults.Order
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[TemplateDom] = get().argumentOut
  override def _argumentOut              = get()._argumentOut

  def astOut: Iterator[Literal] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to LITERAL via AST OUT edge.
    */
  def _literalViaAstOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaAstOut

  @deprecated("please use `_literalViaAstOut`", "June 2024")
  def __literalViaAstOut = _literalViaAstOut

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = get()._typeViaEvalTypeOut

  @deprecated("please use `_typeViaEvalTypeOut`", "June 2024")
  def __typeViaEvalTypeOut = _typeViaEvalTypeOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def _annotationParameterAssignViaAstIn: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    get()._annotationParameterAssignViaAstIn

  @deprecated("please use `_annotationParameterAssignViaAstIn`", "June 2024")
  def __annotationParameterAssignViaAstIn = _annotationParameterAssignViaAstIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

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
    ArrayInitializer.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => order
    }

  override def productPrefix = "ArrayInitializer"
  override def productArity  = 7
}

class ArrayInitializerDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with Expression
    with ArrayInitializerBase {

  override def layoutInformation: NodeLayoutInformation = ArrayInitializer.layoutInformation

  private var _argumentIndex: Integer = ArrayInitializer.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int        = _argumentIndex
  private var _argumentName: String   = null
  def argumentName: Option[String]    = Option(_argumentName).asInstanceOf[Option[String]]
  private var _code: String           = ArrayInitializer.PropertyDefaults.Code
  def code: String                    = _code
  private var _columnNumber: Integer  = null
  def columnNumber: Option[scala.Int] = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _lineNumber: Integer    = null
  def lineNumber: Option[scala.Int]   = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _order: Integer         = ArrayInitializer.PropertyDefaults.Order
  def order: scala.Int                = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
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
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](0)
  override def _argumentOut              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  def astOut: Iterator[Literal] = createAdjacentNodeScalaIteratorByOffSet[Literal](1)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `_literalViaAstOut`", "June 2024")
  def __literalViaAstOut = _literalViaAstOut

  def _literalViaAstOut: overflowdb.traversal.Traversal[Literal] = astOut.collectAll[Literal]

  def evalTypeOut: Iterator[Type] = createAdjacentNodeScalaIteratorByOffSet[Type](2)
  override def _evalTypeOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `_typeViaEvalTypeOut`", "June 2024")
  def __typeViaEvalTypeOut = _typeViaEvalTypeOut

  def _typeViaEvalTypeOut: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def astIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](3)
  override def _astIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `_annotationParameterAssignViaAstIn`", "June 2024")
  def __annotationParameterAssignViaAstIn = _annotationParameterAssignViaAstIn

  def _annotationParameterAssignViaAstIn: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    astIn.collectAll[AnnotationParameterAssign]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  def reachingDefIn: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](5)
  override def _reachingDefIn              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  override def label: String = {
    ArrayInitializer.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "lineNumber"
      case 6 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => lineNumber
      case 6 => order
    }

  override def productPrefix = "ArrayInitializer"
  override def productArity  = 7

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ArrayInitializerDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "ARGUMENT_NAME"  => this._argumentName
      case "CODE"           => this._code
      case "COLUMN_NUMBER"  => this._columnNumber
      case "LINE_NUMBER"    => this._lineNumber
      case "ORDER"          => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex = value.asInstanceOf[scala.Int]
      case "ARGUMENT_NAME"  => this._argumentName = value.asInstanceOf[String]
      case "CODE"           => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"  => this._columnNumber = value.asInstanceOf[scala.Int]
      case "LINE_NUMBER"    => this._lineNumber = value.asInstanceOf[scala.Int]
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
    this._argumentIndex = newNode.asInstanceOf[NewArrayInitializer].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewArrayInitializer].argumentName match {
      case None => null; case Some(value) => value
    }
    this._code = newNode.asInstanceOf[NewArrayInitializer].code
    this._columnNumber = newNode.asInstanceOf[NewArrayInitializer].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._lineNumber = newNode.asInstanceOf[NewArrayInitializer].lineNumber match {
      case None => null; case Some(value) => value
    }
    this._order = newNode.asInstanceOf[NewArrayInitializer].order

  }

}
