package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Local {
  def apply(graph: Graph, id: Long) = new Local(graph, id)

  val Label = "LOCAL"

  object PropertyNames {
    val ClosureBindingId        = "CLOSURE_BINDING_ID"
    val Code                    = "CODE"
    val ColumnNumber            = "COLUMN_NUMBER"
    val DynamicTypeHintFullName = "DYNAMIC_TYPE_HINT_FULL_NAME"
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
    val LineNumber              = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name                    = new overflowdb.PropertyKey[String]("NAME")
    val Order                   = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val PossibleTypes           = new overflowdb.PropertyKey[IndexedSeq[String]]("POSSIBLE_TYPES")
    val TypeFullName            = new overflowdb.PropertyKey[String]("TYPE_FULL_NAME")

  }

  object PropertyDefaults {
    val Code         = "<empty>"
    val Name         = "<empty>"
    val Order        = -1: Int
    val TypeFullName = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.CapturedBy.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("CAPTURED_BY", "EVAL_TYPE", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "REF")
  }

  val factory = new NodeFactory[LocalDb] {
    override val forLabel = Local.Label

    override def createNode(ref: NodeRef[LocalDb]) =
      new LocalDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Local(graph, id)
  }
}

trait LocalBase extends AbstractNode with AstNodeBase with DeclarationBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def closureBindingId: Option[String]
  def code: String
  def columnNumber: Option[Integer]
  def dynamicTypeHintFullName: IndexedSeq[String]
  def lineNumber: Option[Integer]
  def name: String
  def order: scala.Int
  def possibleTypes: IndexedSeq[String]
  def typeFullName: String

}

class Local(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[LocalDb](graph_4762, id_4762)
    with LocalBase
    with StoredNode
    with AstNode
    with Declaration {
  override def closureBindingId: Option[String]            = get().closureBindingId
  override def code: String                                = get().code
  override def columnNumber: Option[Integer]               = get().columnNumber
  override def dynamicTypeHintFullName: IndexedSeq[String] = get().dynamicTypeHintFullName
  override def lineNumber: Option[Integer]                 = get().lineNumber
  override def name: String                                = get().name
  override def order: scala.Int                            = get().order
  override def possibleTypes: IndexedSeq[String]           = get().possibleTypes
  override def typeFullName: String                        = get().typeFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"           => Local.PropertyDefaults.Code
      case "NAME"           => Local.PropertyDefaults.Name
      case "ORDER"          => Local.PropertyDefaults.Order
      case "TYPE_FULL_NAME" => Local.PropertyDefaults.TypeFullName
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def capturedByOut: Iterator[ClosureBinding] = get().capturedByOut
  override def _capturedByOut                 = get()._capturedByOut

  /** Traverse to CLOSURE_BINDING via CAPTURED_BY OUT edge.
    */
  def _closureBindingViaCapturedByOut: overflowdb.traversal.Traversal[ClosureBinding] =
    get()._closureBindingViaCapturedByOut

  def evalTypeOut: Iterator[Type] = get().evalTypeOut
  override def _evalTypeOut       = get()._evalTypeOut

  /** The type of the local. Traverse to TYPE via EVAL_TYPE OUT edge.
    */
  /** The type of the local. */
  @overflowdb.traversal.help.Doc(info = """The type of the local.""")
  def typ: overflowdb.traversal.Traversal[Type] = get().typ

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[Expression] = get().astIn
  override def _astIn             = get()._astIn

  /** The block in which local is declared. Traverse to BLOCK via AST IN edge.
    */
  /** The block in which local is declared. */
  @overflowdb.traversal.help.Doc(info = """The block in which local is declared.""")
  def definingBlock: overflowdb.traversal.Traversal[Block] = get().definingBlock

  /** Traverse to CONTROL_STRUCTURE via AST IN edge.
    */
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = get()._controlStructureViaAstIn

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaAstIn

  def refIn: Iterator[StoredNode] = get().refIn
  override def _refIn             = get()._refIn

  /** Traverse to CLOSURE_BINDING via REF IN edge.
    */
  def _closureBindingViaRefIn: overflowdb.traversal.Traversal[ClosureBinding] = get()._closureBindingViaRefIn

  /** Places (identifier) where this local is being referenced Traverse to IDENTIFIER via REF IN edge.
    */
  /** Places (identifier) where this local is being referenced */
  @overflowdb.traversal.help.Doc(info = """Places (identifier) where this local is being referenced""")
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
    Local.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "closureBindingId"
      case 2 => "code"
      case 3 => "columnNumber"
      case 4 => "dynamicTypeHintFullName"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
      case 8 => "possibleTypes"
      case 9 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => closureBindingId
      case 2 => code
      case 3 => columnNumber
      case 4 => dynamicTypeHintFullName
      case 5 => lineNumber
      case 6 => name
      case 7 => order
      case 8 => possibleTypes
      case 9 => typeFullName
    }

  override def productPrefix = "Local"
  override def productArity  = 10
}

class LocalDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with Declaration with LocalBase {

  override def layoutInformation: NodeLayoutInformation = Local.layoutInformation

  private var _closureBindingId: String                    = null
  def closureBindingId: Option[String]                     = Option(_closureBindingId)
  private var _code: String                                = Local.PropertyDefaults.Code
  def code: String                                         = _code
  private var _columnNumber: Integer                       = null
  def columnNumber: Option[Integer]                        = Option(_columnNumber)
  private var _dynamicTypeHintFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def dynamicTypeHintFullName: IndexedSeq[String]          = _dynamicTypeHintFullName
  private var _lineNumber: Integer                         = null
  def lineNumber: Option[Integer]                          = Option(_lineNumber)
  private var _name: String                                = Local.PropertyDefaults.Name
  def name: String                                         = _name
  private var _order: scala.Int                            = Local.PropertyDefaults.Order
  def order: scala.Int                                     = _order
  private var _possibleTypes: IndexedSeq[String]           = collection.immutable.ArraySeq.empty
  def possibleTypes: IndexedSeq[String]                    = _possibleTypes
  private var _typeFullName: String                        = Local.PropertyDefaults.TypeFullName
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
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (this._possibleTypes != null && this._possibleTypes.nonEmpty) { properties.put("POSSIBLE_TYPES", possibleTypes) }
    if (!(("<empty>") == typeFullName)) { properties.put("TYPE_FULL_NAME", typeFullName) }

    properties
  }

  import overflowdb.traversal._
  def capturedByOut: Iterator[ClosureBinding] = createAdjacentNodeScalaIteratorByOffSet[ClosureBinding](0)
  override def _capturedByOut                 = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _closureBindingViaCapturedByOut: overflowdb.traversal.Traversal[ClosureBinding] =
    capturedByOut.collectAll[ClosureBinding]

  def evalTypeOut: Iterator[Type]               = createAdjacentNodeScalaIteratorByOffSet[Type](1)
  override def _evalTypeOut                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def typ: overflowdb.traversal.Traversal[Type] = evalTypeOut.collectAll[Type]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](2)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[Expression]                          = createAdjacentNodeScalaIteratorByOffSet[Expression](3)
  override def _astIn                                      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def definingBlock: overflowdb.traversal.Traversal[Block] = astIn.collectAll[Block]
  def _controlStructureViaAstIn: overflowdb.traversal.Traversal[ControlStructure] = astIn.collectAll[ControlStructure]
  def _unknownViaAstIn: overflowdb.traversal.Traversal[Unknown]                   = astIn.collectAll[Unknown]

  def refIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  override def _refIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _closureBindingViaRefIn: overflowdb.traversal.Traversal[ClosureBinding] = refIn.collectAll[ClosureBinding]
  def referencingIdentifiers: overflowdb.traversal.Traversal[Identifier]      = refIn.collectAll[Identifier]

  override def label: String = {
    Local.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "closureBindingId"
      case 2 => "code"
      case 3 => "columnNumber"
      case 4 => "dynamicTypeHintFullName"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "order"
      case 8 => "possibleTypes"
      case 9 => "typeFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => closureBindingId
      case 2 => code
      case 3 => columnNumber
      case 4 => dynamicTypeHintFullName
      case 5 => lineNumber
      case 6 => name
      case 7 => order
      case 8 => possibleTypes
      case 9 => typeFullName
    }

  override def productPrefix = "Local"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[LocalDb]

  override def property(key: String): Any = {
    key match {
      case "CLOSURE_BINDING_ID"          => this._closureBindingId
      case "CODE"                        => this._code
      case "COLUMN_NUMBER"               => this._columnNumber
      case "DYNAMIC_TYPE_HINT_FULL_NAME" => this._dynamicTypeHintFullName
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
      case "LINE_NUMBER" => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"        => this._name = value.asInstanceOf[String]
      case "ORDER"       => this._order = value.asInstanceOf[scala.Int]
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
    this._closureBindingId = newNode.asInstanceOf[NewLocal].closureBindingId.orNull
    this._code = newNode.asInstanceOf[NewLocal].code
    this._columnNumber = newNode.asInstanceOf[NewLocal].columnNumber.orNull
    this._dynamicTypeHintFullName =
      if (newNode.asInstanceOf[NewLocal].dynamicTypeHintFullName != null)
        newNode.asInstanceOf[NewLocal].dynamicTypeHintFullName
      else collection.immutable.ArraySeq.empty
    this._lineNumber = newNode.asInstanceOf[NewLocal].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewLocal].name
    this._order = newNode.asInstanceOf[NewLocal].order
    this._possibleTypes =
      if (newNode.asInstanceOf[NewLocal].possibleTypes != null) newNode.asInstanceOf[NewLocal].possibleTypes
      else collection.immutable.ArraySeq.empty
    this._typeFullName = newNode.asInstanceOf[NewLocal].typeFullName

  }

}
