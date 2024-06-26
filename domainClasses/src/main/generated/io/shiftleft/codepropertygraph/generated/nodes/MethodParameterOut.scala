package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object MethodParameterOut {
  def apply(graph: Graph, id: Long) = new MethodParameterOut(graph, id)

  val Label = "METHOD_PARAMETER_OUT"

  object PropertyNames {
    val Code               = "CODE"
    val ColumnNumber       = "COLUMN_NUMBER"
    val EvaluationStrategy = "EVALUATION_STRATEGY"
    val Index              = "INDEX"
    val IsVariadic         = "IS_VARIADIC"
    val LineNumber         = "LINE_NUMBER"
    val Name               = "NAME"
    val Order              = "ORDER"
    val TypeFullName       = "TYPE_FULL_NAME"
    val all: Set[String] =
      Set(Code, ColumnNumber, EvaluationStrategy, Index, IsVariadic, LineNumber, Name, Order, TypeFullName)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code               = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber       = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val EvaluationStrategy = new overflowdb.PropertyKey[String]("EVALUATION_STRATEGY")
    val Index              = new overflowdb.PropertyKey[scala.Int]("INDEX")
    val IsVariadic         = new overflowdb.PropertyKey[Boolean]("IS_VARIADIC")
    val LineNumber         = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name               = new overflowdb.PropertyKey[String]("NAME")
    val Order              = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val TypeFullName       = new overflowdb.PropertyKey[String]("TYPE_FULL_NAME")

  }

  object PropertyDefaults {
    val Code               = "<empty>"
    val EvaluationStrategy = "<empty>"
    val Index              = -1: Int
    val IsVariadic         = false
    val Name               = "<empty>"
    val Order              = -1: Int
    val TypeFullName       = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ParameterLink.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("EVAL_TYPE", "REACHING_DEF", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CFG", "PARAMETER_LINK", "REACHING_DEF")
  }

  val factory = new NodeFactory[MethodParameterOutDb] {
    override val forLabel = MethodParameterOut.Label

    override def createNode(ref: NodeRef[MethodParameterOutDb]) =
      new MethodParameterOutDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodParameterOut(graph, id)
  }
}

trait MethodParameterOutBase extends AbstractNode with AstNodeBase with CfgNodeBase with DeclarationBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def evaluationStrategy: String
  def index: scala.Int
  def isVariadic: Boolean
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int
  def typeFullName: String

}

class MethodParameterOut(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodParameterOutDb](graph_4762, id_4762)
    with MethodParameterOutBase
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration {
  override def code: String                  = get().code
  override def columnNumber: Option[Integer] = get().columnNumber
  override def evaluationStrategy: String    = get().evaluationStrategy
  override def index: scala.Int              = get().index
  override def isVariadic: Boolean           = get().isVariadic
  override def lineNumber: Option[Integer]   = get().lineNumber
  override def name: String                  = get().name
  override def order: scala.Int              = get().order
  override def typeFullName: String          = get().typeFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"                => MethodParameterOut.PropertyDefaults.Code
      case "EVALUATION_STRATEGY" => MethodParameterOut.PropertyDefaults.EvaluationStrategy
      case "INDEX"               => MethodParameterOut.PropertyDefaults.Index
      case "IS_VARIADIC"         => MethodParameterOut.PropertyDefaults.IsVariadic
      case "NAME"                => MethodParameterOut.PropertyDefaults.Name
      case "ORDER"               => MethodParameterOut.PropertyDefaults.Order
      case "TYPE_FULL_NAME"      => MethodParameterOut.PropertyDefaults.TypeFullName
      case _                     => super.propertyDefaultValue(propertyKey)
    }
  }

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** Traverse to parameter type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  /** Traverse to parameter type */
  @overflowdb.traversal.help.Doc(info = """Traverse to parameter type""")
  def typ: overflowdb.traversal.Traversal[Type] = get().typ

  def reachingDefOut: Iterator[Expression] = get().reachingDefOut
  override def _reachingDefOut             = get()._reachingDefOut

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefOut

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefOut

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaReachingDefOut

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefOut

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaReachingDefOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[Method] = get().astIn
  override def _astIn         = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def method: Method = get().method

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def parameterLinkIn: Iterator[MethodParameterIn] = get().parameterLinkIn
  override def _parameterLinkIn                    = get()._parameterLinkIn

  /** Traverse to METHOD_PARAMETER_IN via PARAMETER_LINK IN edge.
    */
  def asInput: overflowdb.traversal.Traversal[MethodParameterIn] = get().asInput

  def reachingDefIn: Iterator[CfgNode] = get().reachingDefIn
  override def _reachingDefIn          = get()._reachingDefIn

  /** Traverse to BLOCK via REACHING_DEF IN edge.
    */
  def _blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = get()._blockViaReachingDefIn

  /** Traverse to CALL via REACHING_DEF IN edge.
    */
  def _callViaReachingDefIn: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefIn

  /** Traverse to CONTROL_STRUCTURE via REACHING_DEF IN edge.
    */
  def _controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaReachingDefIn

  /** Traverse to IDENTIFIER via REACHING_DEF IN edge.
    */
  def _identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefIn

  /** Traverse to LITERAL via REACHING_DEF IN edge.
    */
  def _literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefIn

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = get()._methodViaReachingDefIn

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF IN edge.
    */
  def _methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaReachingDefIn

  /** Traverse to METHOD_REF via REACHING_DEF IN edge.
    */
  def _methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaReachingDefIn

  /** Traverse to RETURN via REACHING_DEF IN edge.
    */
  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return] = get()._returnViaReachingDefIn

  /** Traverse to TYPE_REF via REACHING_DEF IN edge.
    */
  def _typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaReachingDefIn

  /** Traverse to UNKNOWN via REACHING_DEF IN edge.
    */
  def _unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaReachingDefIn

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
    MethodParameterOut.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "evaluationStrategy"
      case 4 => "index"
      case 5 => "isVariadic"
      case 6 => "lineNumber"
      case 7 => "name"
      case 8 => "order"
      case 9 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => evaluationStrategy
      case 4 => index
      case 5 => isVariadic
      case 6 => lineNumber
      case 7 => name
      case 8 => order
      case 9 => typeFullName
    }

  override def productPrefix = "MethodParameterOut"
  override def productArity  = 10
}

class MethodParameterOutDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration
    with MethodParameterOutBase {

  override def layoutInformation: NodeLayoutInformation = MethodParameterOut.layoutInformation

  private var _code: String               = MethodParameterOut.PropertyDefaults.Code
  def code: String                        = _code
  private var _columnNumber: Integer      = null
  def columnNumber: Option[Integer]       = Option(_columnNumber)
  private var _evaluationStrategy: String = MethodParameterOut.PropertyDefaults.EvaluationStrategy
  def evaluationStrategy: String          = _evaluationStrategy
  private var _index: scala.Int           = MethodParameterOut.PropertyDefaults.Index
  def index: scala.Int                    = _index
  private var _isVariadic: Boolean        = MethodParameterOut.PropertyDefaults.IsVariadic
  def isVariadic: Boolean                 = _isVariadic
  private var _lineNumber: Integer        = null
  def lineNumber: Option[Integer]         = Option(_lineNumber)
  private var _name: String               = MethodParameterOut.PropertyDefaults.Name
  def name: String                        = _name
  private var _order: scala.Int           = MethodParameterOut.PropertyDefaults.Order
  def order: scala.Int                    = _order
  private var _typeFullName: String       = MethodParameterOut.PropertyDefaults.TypeFullName
  def typeFullName: String                = _typeFullName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("EVALUATION_STRATEGY", evaluationStrategy)
    properties.put("INDEX", index)
    properties.put("IS_VARIADIC", isVariadic)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
    properties.put("ORDER", order)
    properties.put("TYPE_FULL_NAME", typeFullName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (!(("<empty>") == evaluationStrategy)) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }
    if (!((-1: Int) == index)) { properties.put("INDEX", index) }
    if (!((false) == isVariadic)) { properties.put("IS_VARIADIC", isVariadic) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (!(("<empty>") == typeFullName)) { properties.put("TYPE_FULL_NAME", typeFullName) }

    properties
  }

  import overflowdb.traversal._
  def evalTypeOut: Iterator[Type]               = createAdjacentNodeScalaIteratorByOffSet[Type](0)
  override def _evalTypeOut                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def typ: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def reachingDefOut: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](1)
  override def _reachingDefOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call]             = reachingDefOut.collectAll[Call]
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal]       = reachingDefOut.collectAll[Literal]
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef]   = reachingDefOut.collectAll[MethodRef]
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return]         = reachingDefOut.collectAll[Return]
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef]       = reachingDefOut.collectAll[TypeRef]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](2)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](3)
  override def _astIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def method: Method = try { astIn.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_PARAMETER_OUT node with id=" + id,
        e
      )
  }

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  def parameterLinkIn: Iterator[MethodParameterIn] = createAdjacentNodeScalaIteratorByOffSet[MethodParameterIn](5)
  override def _parameterLinkIn                    = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def asInput: overflowdb.traversal.Traversal[MethodParameterIn] = parameterLinkIn.collectAll[MethodParameterIn]

  def reachingDefIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](6)
  override def _reachingDefIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def _blockViaReachingDefIn: overflowdb.traversal.Traversal[Block] = reachingDefIn.collectAll[Block]
  def _callViaReachingDefIn: overflowdb.traversal.Traversal[Call]   = reachingDefIn.collectAll[Call]
  def _controlStructureViaReachingDefIn: overflowdb.traversal.Traversal[ControlStructure] =
    reachingDefIn.collectAll[ControlStructure]
  def _identifierViaReachingDefIn: overflowdb.traversal.Traversal[Identifier] = reachingDefIn.collectAll[Identifier]
  def _literalViaReachingDefIn: overflowdb.traversal.Traversal[Literal]       = reachingDefIn.collectAll[Literal]
  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method]         = reachingDefIn.collectAll[Method]
  def _methodParameterInViaReachingDefIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    reachingDefIn.collectAll[MethodParameterIn]
  def _methodRefViaReachingDefIn: overflowdb.traversal.Traversal[MethodRef] = reachingDefIn.collectAll[MethodRef]
  def _returnViaReachingDefIn: overflowdb.traversal.Traversal[Return]       = reachingDefIn.collectAll[Return]
  def _typeRefViaReachingDefIn: overflowdb.traversal.Traversal[TypeRef]     = reachingDefIn.collectAll[TypeRef]
  def _unknownViaReachingDefIn: overflowdb.traversal.Traversal[Unknown]     = reachingDefIn.collectAll[Unknown]

  override def label: String = {
    MethodParameterOut.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "evaluationStrategy"
      case 4 => "index"
      case 5 => "isVariadic"
      case 6 => "lineNumber"
      case 7 => "name"
      case 8 => "order"
      case 9 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => evaluationStrategy
      case 4 => index
      case 5 => isVariadic
      case 6 => lineNumber
      case 7 => name
      case 8 => order
      case 9 => typeFullName
    }

  override def productPrefix = "MethodParameterOut"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodParameterOutDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"                => this._code
      case "COLUMN_NUMBER"       => this._columnNumber
      case "EVALUATION_STRATEGY" => this._evaluationStrategy
      case "INDEX"               => this._index
      case "IS_VARIADIC"         => this._isVariadic
      case "LINE_NUMBER"         => this._lineNumber
      case "NAME"                => this._name
      case "ORDER"               => this._order
      case "TYPE_FULL_NAME"      => this._typeFullName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"                => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"       => this._columnNumber = value.asInstanceOf[Integer]
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
      case "INDEX"               => this._index = value.asInstanceOf[scala.Int]
      case "IS_VARIADIC"         => this._isVariadic = value.asInstanceOf[Boolean]
      case "LINE_NUMBER"         => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"                => this._name = value.asInstanceOf[String]
      case "ORDER"               => this._order = value.asInstanceOf[scala.Int]
      case "TYPE_FULL_NAME"      => this._typeFullName = value.asInstanceOf[String]

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
    this._code = newNode.asInstanceOf[NewMethodParameterOut].code
    this._columnNumber = newNode.asInstanceOf[NewMethodParameterOut].columnNumber.orNull
    this._evaluationStrategy = newNode.asInstanceOf[NewMethodParameterOut].evaluationStrategy
    this._index = newNode.asInstanceOf[NewMethodParameterOut].index
    this._isVariadic = newNode.asInstanceOf[NewMethodParameterOut].isVariadic
    this._lineNumber = newNode.asInstanceOf[NewMethodParameterOut].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewMethodParameterOut].name
    this._order = newNode.asInstanceOf[NewMethodParameterOut].order
    this._typeFullName = newNode.asInstanceOf[NewMethodParameterOut].typeFullName

  }

}
