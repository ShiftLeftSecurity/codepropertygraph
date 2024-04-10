package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object MethodParameterIn {
  def apply(graph: Graph, id: Long) = new MethodParameterIn(graph, id)

  val Label = "METHOD_PARAMETER_IN"

  object PropertyNames {
    val ClosureBindingId        = "CLOSURE_BINDING_ID"
    val Code                    = "CODE"
    val ColumnNumber            = "COLUMN_NUMBER"
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME"
    val EvaluationStrategy      = "EVALUATION_STRATEGY"
    val Index                   = "INDEX"
    val IsVariadic              = "IS_VARIADIC"
    val LineNumber              = "LINE_NUMBER"
    val Name                    = "NAME"
    val Order                   = "ORDER"
    val PossibleTypes           = "POSSIBLE_TYPES"
    val TypeFullName            = "TYPE_FULL_NAME"
    val all: Set[String] = Set(
      ClosureBindingId,
      Code,
      ColumnNumber,
      DynamicTypeHintFullName,
      EvaluationStrategy,
      Index,
      IsVariadic,
      LineNumber,
      Name,
      Order,
      PossibleTypes,
      TypeFullName
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ClosureBindingId        = new overflowdb.PropertyKey[String]("CLOSURE_BINDING_ID")
    val Code                    = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber            = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val DynamicTypeHintFullName = new overflowdb.PropertyKey[IndexedSeq[String]]("DYNAMIC_TYPE_HINT_FULL_NAME")
    val EvaluationStrategy      = new overflowdb.PropertyKey[String]("EVALUATION_STRATEGY")
    val Index                   = new overflowdb.PropertyKey[scala.Int]("INDEX")
    val IsVariadic              = new overflowdb.PropertyKey[Boolean]("IS_VARIADIC")
    val LineNumber              = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name                    = new overflowdb.PropertyKey[String]("NAME")
    val Order                   = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val PossibleTypes           = new overflowdb.PropertyKey[IndexedSeq[String]]("POSSIBLE_TYPES")
    val TypeFullName            = new overflowdb.PropertyKey[String]("TYPE_FULL_NAME")

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
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.CapturedBy.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ParameterLink.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "CAPTURED_BY", "EVAL_TYPE", "PARAMETER_LINK", "REACHING_DEF", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CFG", "REACHING_DEF", "REF")
  }

  val factory = new NodeFactory[MethodParameterInDb] {
    override val forLabel = MethodParameterIn.Label

    override def createNode(ref: NodeRef[MethodParameterInDb]) =
      new MethodParameterInDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = MethodParameterIn(graph, id)
  }
}

trait MethodParameterInBase extends AbstractNode with AstNodeBase with CfgNodeBase with DeclarationBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def closureBindingId: Option[String]
  def code: String
  def columnNumber: Option[Integer]
  def dynamicTypeHintFullName: IndexedSeq[String]
  def evaluationStrategy: String
  def index: scala.Int
  def isVariadic: Boolean
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int
  def possibleTypes: IndexedSeq[String]
  def typeFullName: String

}

class MethodParameterIn(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodParameterInDb](graph_4762, id_4762)
    with MethodParameterInBase
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration {
  override def closureBindingId: Option[String]            = get().closureBindingId
  override def code: String                                = get().code
  override def columnNumber: Option[Integer]               = get().columnNumber
  override def dynamicTypeHintFullName: IndexedSeq[String] = get().dynamicTypeHintFullName
  override def evaluationStrategy: String                  = get().evaluationStrategy
  override def index: scala.Int                            = get().index
  override def isVariadic: Boolean                         = get().isVariadic
  override def lineNumber: Option[Integer]                 = get().lineNumber
  override def name: String                                = get().name
  override def order: scala.Int                            = get().order
  override def possibleTypes: IndexedSeq[String]           = get().possibleTypes
  override def typeFullName: String                        = get().typeFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"                => MethodParameterIn.PropertyDefaults.Code
      case "EVALUATION_STRATEGY" => MethodParameterIn.PropertyDefaults.EvaluationStrategy
      case "INDEX"               => MethodParameterIn.PropertyDefaults.Index
      case "IS_VARIADIC"         => MethodParameterIn.PropertyDefaults.IsVariadic
      case "NAME"                => MethodParameterIn.PropertyDefaults.Name
      case "ORDER"               => MethodParameterIn.PropertyDefaults.Order
      case "TYPE_FULL_NAME"      => MethodParameterIn.PropertyDefaults.TypeFullName
      case _                     => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[Expression] = get().astOut
  override def _astOut             = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstOut

  /** Traverse to UNKNOWN via AST OUT edge.
    */
  def _unknownViaAstOut: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaAstOut

  def capturedByOut: Iterator[ClosureBinding] = get().capturedByOut
  override def _capturedByOut                 = get()._capturedByOut

  /** Traverse to CLOSURE_BINDING via CAPTURED_BY OUT edge.
    */
  def _closureBindingViaCapturedByOut: overflowdb.traversal.Traversal[ClosureBinding] =
    get()._closureBindingViaCapturedByOut

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** Traverse to parameter type Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  /** Traverse to parameter type */
  @overflowdb.traversal.help.Doc(info = """Traverse to parameter type""")
  def typ: Type = get().typ

  def parameterLinkOut: Iterator[MethodParameterOut] = get().parameterLinkOut
  override def _parameterLinkOut                     = get()._parameterLinkOut

  /** Traverse to corresponding formal output parameter Traverse to METHOD_PARAMETER_OUT via PARAMETER_LINK OUT edge.
    */
  /** Traverse to corresponding formal output parameter */
  @overflowdb.traversal.help.Doc(info = """Traverse to corresponding formal output parameter""")
  def asOutput: overflowdb.traversal.Traversal[MethodParameterOut] = get().asOutput

  def reachingDefOut: Iterator[CfgNode] = get().reachingDefOut
  override def _reachingDefOut          = get()._reachingDefOut

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = get()._callViaReachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaReachingDefOut

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = get()._literalViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaReachingDefOut

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

  /** Traverse to method associated with this formal parameter Traverse to METHOD via AST IN edge.
    */
  /** Traverse to method associated with this formal parameter */
  @overflowdb.traversal.help.Doc(info = """Traverse to method associated with this formal parameter""")
  def method: Method = get().method

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def reachingDefIn: Iterator[Method] = get().reachingDefIn
  override def _reachingDefIn         = get()._reachingDefIn

  /** Traverse to METHOD via REACHING_DEF IN edge.
    */
  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = get()._methodViaReachingDefIn

  def refIn: Iterator[StoredNode] = get().refIn
  override def _refIn             = get()._refIn

  /** Traverse to CLOSURE_BINDING via REF IN edge.
    */
  def _closureBindingViaRefIn: overflowdb.traversal.Traversal[ClosureBinding] = get()._closureBindingViaRefIn

  /** Places (identifier) where this parameter is being referenced Traverse to IDENTIFIER via REF IN edge.
    */
  /** Places (identifier) where this parameter is being referenced */
  @overflowdb.traversal.help.Doc(info = """Places (identifier) where this parameter is being referenced""")
  def referencingIdentifiers: overflowdb.traversal.Traversal[Identifier] = get().referencingIdentifiers

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
    MethodParameterIn.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "closureBindingId"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dynamicTypeHintFullName"
      case 5  => "evaluationStrategy"
      case 6  => "index"
      case 7  => "isVariadic"
      case 8  => "lineNumber"
      case 9  => "name"
      case 10 => "order"
      case 11 => "possibleTypes"
      case 12 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => closureBindingId
      case 2  => code
      case 3  => columnNumber
      case 4  => dynamicTypeHintFullName
      case 5  => evaluationStrategy
      case 6  => index
      case 7  => isVariadic
      case 8  => lineNumber
      case 9  => name
      case 10 => order
      case 11 => possibleTypes
      case 12 => typeFullName
    }

  override def productPrefix = "MethodParameterIn"
  override def productArity  = 13
}

class MethodParameterInDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration
    with MethodParameterInBase {

  override def layoutInformation: NodeLayoutInformation = MethodParameterIn.layoutInformation

  private var _closureBindingId: String                    = null
  def closureBindingId: Option[String]                     = Option(_closureBindingId)
  private var _code: String                                = MethodParameterIn.PropertyDefaults.Code
  def code: String                                         = _code
  private var _columnNumber: Integer                       = null
  def columnNumber: Option[Integer]                        = Option(_columnNumber)
  private var _dynamicTypeHintFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def dynamicTypeHintFullName: IndexedSeq[String]          = _dynamicTypeHintFullName
  private var _evaluationStrategy: String                  = MethodParameterIn.PropertyDefaults.EvaluationStrategy
  def evaluationStrategy: String                           = _evaluationStrategy
  private var _index: scala.Int                            = MethodParameterIn.PropertyDefaults.Index
  def index: scala.Int                                     = _index
  private var _isVariadic: Boolean                         = MethodParameterIn.PropertyDefaults.IsVariadic
  def isVariadic: Boolean                                  = _isVariadic
  private var _lineNumber: Integer                         = null
  def lineNumber: Option[Integer]                          = Option(_lineNumber)
  private var _name: String                                = MethodParameterIn.PropertyDefaults.Name
  def name: String                                         = _name
  private var _order: scala.Int                            = MethodParameterIn.PropertyDefaults.Order
  def order: scala.Int                                     = _order
  private var _possibleTypes: IndexedSeq[String]           = collection.immutable.ArraySeq.empty
  def possibleTypes: IndexedSeq[String]                    = _possibleTypes
  private var _typeFullName: String                        = MethodParameterIn.PropertyDefaults.TypeFullName
  def typeFullName: String                                 = _typeFullName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    closureBindingId.map { value => properties.put("CLOSURE_BINDING_ID", value) }
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    properties.put("EVALUATION_STRATEGY", evaluationStrategy)
    properties.put("INDEX", index)
    properties.put("IS_VARIADIC", isVariadic)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
    properties.put("ORDER", order)
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    properties.put("TYPE_FULL_NAME", typeFullName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    closureBindingId.map { value => properties.put("CLOSURE_BINDING_ID", value) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (this._dynamicTypeHintFullName != null && this._dynamicTypeHintFullName.nonEmpty) {
      properties.put("DYNAMIC_TYPE_HINT_FULL_NAME", dynamicTypeHintFullName)
    }
    if (!(("<empty>") == evaluationStrategy)) { properties.put("EVALUATION_STRATEGY", evaluationStrategy) }
    if (!((-1: Int) == index)) { properties.put("INDEX", index) }
    if (!((false) == isVariadic)) { properties.put("IS_VARIADIC", isVariadic) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    if (!(("<empty>") == typeFullName)) { properties.put("TYPE_FULL_NAME", typeFullName) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[Expression] = createAdjacentNodeScalaIteratorByOffSet[Expression](0)
  override def _astOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = astOut.collectAll[Annotation]
  def _unknownViaAstOut: overflowdb.traversal.Traversal[Unknown]       = astOut.collectAll[Unknown]

  def capturedByOut: Iterator[ClosureBinding] = createAdjacentNodeScalaIteratorByOffSet[ClosureBinding](1)
  override def _capturedByOut                 = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _closureBindingViaCapturedByOut: overflowdb.traversal.Traversal[ClosureBinding] =
    capturedByOut.collectAll[ClosureBinding]

  def evalTypeOut: Iterator[Type] = createAdjacentNodeScalaIteratorByOffSet[Type](2)
  override def _evalTypeOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def typ: Type = try { evalTypeOut.collectAll[Type].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label EVAL_TYPE to an adjacent TYPE is mandatory, but not defined for this METHOD_PARAMETER_IN node with id=" + id,
        e
      )
  }

  def parameterLinkOut: Iterator[MethodParameterOut] = createAdjacentNodeScalaIteratorByOffSet[MethodParameterOut](3)
  override def _parameterLinkOut                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def asOutput: overflowdb.traversal.Traversal[MethodParameterOut] = parameterLinkOut.collectAll[MethodParameterOut]

  def reachingDefOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _reachingDefOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _callViaReachingDefOut: overflowdb.traversal.Traversal[Call]             = reachingDefOut.collectAll[Call]
  def _identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  def _literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal]       = reachingDefOut.collectAll[Literal]
  def _methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefOut.collectAll[MethodParameterOut]
  def _methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = reachingDefOut.collectAll[MethodRef]
  def _returnViaReachingDefOut: overflowdb.traversal.Traversal[Return]       = reachingDefOut.collectAll[Return]
  def _typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef]     = reachingDefOut.collectAll[TypeRef]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](5)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](6)
  override def _astIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def method: Method = try { astIn.collectAll[Method].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "IN edge with label AST to an adjacent METHOD is mandatory, but not defined for this METHOD_PARAMETER_IN node with id=" + id,
        e
      )
  }

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](7)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)

  def reachingDefIn: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](8)
  override def _reachingDefIn         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)
  def _methodViaReachingDefIn: overflowdb.traversal.Traversal[Method] = reachingDefIn.collectAll[Method]

  def refIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)
  override def _refIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)
  def _closureBindingViaRefIn: overflowdb.traversal.Traversal[ClosureBinding] = refIn.collectAll[ClosureBinding]
  def referencingIdentifiers: overflowdb.traversal.Traversal[Identifier]      = refIn.collectAll[Identifier]

  override def label: String = {
    MethodParameterIn.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "closureBindingId"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dynamicTypeHintFullName"
      case 5  => "evaluationStrategy"
      case 6  => "index"
      case 7  => "isVariadic"
      case 8  => "lineNumber"
      case 9  => "name"
      case 10 => "order"
      case 11 => "possibleTypes"
      case 12 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => closureBindingId
      case 2  => code
      case 3  => columnNumber
      case 4  => dynamicTypeHintFullName
      case 5  => evaluationStrategy
      case 6  => index
      case 7  => isVariadic
      case 8  => lineNumber
      case 9  => name
      case 10 => order
      case 11 => possibleTypes
      case 12 => typeFullName
    }

  override def productPrefix = "MethodParameterIn"
  override def productArity  = 13

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodParameterInDb]

  override def property(key: String): Any = {
    key match {
      case "CLOSURE_BINDING_ID"          => this._closureBindingId
      case "CODE"                        => this._code
      case "COLUMN_NUMBER"               => this._columnNumber
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
      case "EVALUATION_STRATEGY"         => this._evaluationStrategy
      case "INDEX"                       => this._index
      case "IS_VARIADIC"                 => this._isVariadic
      case "LINE_NUMBER"                 => this._lineNumber
      case "NAME"                        => this._name
      case "ORDER"                       => this._order
      case "POSSIBLE_TYPES"              => this._possibleTypes
      case "TYPE_FULL_NAME"              => this._typeFullName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CLOSURE_BINDING_ID" => this._closureBindingId = value.asInstanceOf[String]
      case "CODE"               => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"      => this._columnNumber = value.asInstanceOf[Integer]
      case "DYNAMIC_TYPE_HINT_FULL_NAME" =>
        this._dynamicTypeHintFullName = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: String                              => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[String]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[String]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "EVALUATION_STRATEGY" => this._evaluationStrategy = value.asInstanceOf[String]
      case "INDEX"               => this._index = value.asInstanceOf[scala.Int]
      case "IS_VARIADIC"         => this._isVariadic = value.asInstanceOf[Boolean]
      case "LINE_NUMBER"         => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"                => this._name = value.asInstanceOf[String]
      case "ORDER"               => this._order = value.asInstanceOf[scala.Int]
      case "POSSIBLE_TYPES" =>
        this._possibleTypes = value match {
          case null                                             => collection.immutable.ArraySeq.empty
          case singleValue: String                              => collection.immutable.ArraySeq(singleValue)
          case coll: IterableOnce[Any] if coll.iterator.isEmpty => collection.immutable.ArraySeq.empty
          case arr: Array[_] if arr.isEmpty                     => collection.immutable.ArraySeq.empty
          case arr: Array[_] => collection.immutable.ArraySeq.unsafeWrapArray(arr).asInstanceOf[IndexedSeq[String]]
          case jCollection: java.lang.Iterable[_] =>
            if (jCollection.iterator.hasNext) {
              collection.immutable.ArraySeq.unsafeWrapArray(
                jCollection.asInstanceOf[java.util.Collection[String]].iterator.asScala.toArray
              )
            } else collection.immutable.ArraySeq.empty
          case iter: Iterable[_] =>
            if (iter.nonEmpty) {
              collection.immutable.ArraySeq.unsafeWrapArray(iter.asInstanceOf[Iterable[String]].toArray)
            } else collection.immutable.ArraySeq.empty
        }
      case "TYPE_FULL_NAME" => this._typeFullName = value.asInstanceOf[String]

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
    this._closureBindingId = newNode.asInstanceOf[NewMethodParameterIn].closureBindingId.orNull
    this._code = newNode.asInstanceOf[NewMethodParameterIn].code
    this._columnNumber = newNode.asInstanceOf[NewMethodParameterIn].columnNumber.orNull
    this._dynamicTypeHintFullName =
      if (newNode.asInstanceOf[NewMethodParameterIn].dynamicTypeHintFullName != null)
        newNode.asInstanceOf[NewMethodParameterIn].dynamicTypeHintFullName
      else collection.immutable.ArraySeq.empty
    this._evaluationStrategy = newNode.asInstanceOf[NewMethodParameterIn].evaluationStrategy
    this._index = newNode.asInstanceOf[NewMethodParameterIn].index
    this._isVariadic = newNode.asInstanceOf[NewMethodParameterIn].isVariadic
    this._lineNumber = newNode.asInstanceOf[NewMethodParameterIn].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewMethodParameterIn].name
    this._order = newNode.asInstanceOf[NewMethodParameterIn].order
    this._possibleTypes =
      if (newNode.asInstanceOf[NewMethodParameterIn].possibleTypes != null)
        newNode.asInstanceOf[NewMethodParameterIn].possibleTypes
      else collection.immutable.ArraySeq.empty
    this._typeFullName = newNode.asInstanceOf[NewMethodParameterIn].typeFullName

  }

}
