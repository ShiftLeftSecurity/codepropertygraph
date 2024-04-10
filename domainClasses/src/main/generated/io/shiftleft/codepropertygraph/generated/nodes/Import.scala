package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Import {
  def apply(graph: Graph, id: Long) = new Import(graph, id)

  val Label = "IMPORT"

  object PropertyNames {
    val Code           = "CODE"
    val ColumnNumber   = "COLUMN_NUMBER"
    val ExplicitAs     = "EXPLICIT_AS"
    val ImportedAs     = "IMPORTED_AS"
    val ImportedEntity = "IMPORTED_ENTITY"
    val IsExplicit     = "IS_EXPLICIT"
    val IsWildcard     = "IS_WILDCARD"
    val LineNumber     = "LINE_NUMBER"
    val Order          = "ORDER"
    val all: Set[String] =
      Set(Code, ColumnNumber, ExplicitAs, ImportedAs, ImportedEntity, IsExplicit, IsWildcard, LineNumber, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Code           = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber   = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val ExplicitAs     = new overflowdb.PropertyKey[java.lang.Boolean]("EXPLICIT_AS")
    val ImportedAs     = new overflowdb.PropertyKey[String]("IMPORTED_AS")
    val ImportedEntity = new overflowdb.PropertyKey[String]("IMPORTED_ENTITY")
    val IsExplicit     = new overflowdb.PropertyKey[java.lang.Boolean]("IS_EXPLICIT")
    val IsWildcard     = new overflowdb.PropertyKey[java.lang.Boolean]("IS_WILDCARD")
    val LineNumber     = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Order          = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val Code  = "<empty>"
    val Order = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Imports.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.IsCallForImport.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("IMPORTS", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "IS_CALL_FOR_IMPORT")
  }

  val factory = new NodeFactory[ImportDb] {
    override val forLabel = Import.Label

    override def createNode(ref: NodeRef[ImportDb]) =
      new ImportDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Import(graph, id)
  }
}

trait ImportBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def code: String
  def columnNumber: Option[Integer]
  def explicitAs: Option[java.lang.Boolean]
  def importedAs: Option[String]
  def importedEntity: Option[String]
  def isExplicit: Option[java.lang.Boolean]
  def isWildcard: Option[java.lang.Boolean]
  def lineNumber: Option[Integer]
  def order: scala.Int

}

class Import(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ImportDb](graph_4762, id_4762)
    with ImportBase
    with StoredNode
    with AstNode {
  override def code: String                          = get().code
  override def columnNumber: Option[Integer]         = get().columnNumber
  override def explicitAs: Option[java.lang.Boolean] = get().explicitAs
  override def importedAs: Option[String]            = get().importedAs
  override def importedEntity: Option[String]        = get().importedEntity
  override def isExplicit: Option[java.lang.Boolean] = get().isExplicit
  override def isWildcard: Option[java.lang.Boolean] = get().isWildcard
  override def lineNumber: Option[Integer]           = get().lineNumber
  override def order: scala.Int                      = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CODE"  => Import.PropertyDefaults.Code
      case "ORDER" => Import.PropertyDefaults.Order
      case _       => super.propertyDefaultValue(propertyKey)
    }
  }

  def importsOut: Iterator[Dependency] = get().importsOut
  override def _importsOut             = get()._importsOut

  /** Traverse to DEPENDENCY via IMPORTS OUT edge.
    */
  def _dependencyViaImportsOut: overflowdb.traversal.Traversal[Dependency] = get()._dependencyViaImportsOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to BLOCK via AST IN edge.
    */
  def _blockViaAstIn: overflowdb.traversal.Traversal[Block] = get()._blockViaAstIn

  /** Traverse to FILE via AST IN edge.
    */
  def _fileViaAstIn: overflowdb.traversal.Traversal[File] = get()._fileViaAstIn

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaAstIn

  def isCallForImportIn: Iterator[Call] = get().isCallForImportIn
  override def _isCallForImportIn       = get()._isCallForImportIn

  /** Traverse to CALL via IS_CALL_FOR_IMPORT IN edge.
    */
  def _callViaIsCallForImportIn: overflowdb.traversal.Traversal[Call] = get()._callViaIsCallForImportIn

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
    Import.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "explicitAs"
      case 4 => "importedAs"
      case 5 => "importedEntity"
      case 6 => "isExplicit"
      case 7 => "isWildcard"
      case 8 => "lineNumber"
      case 9 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => explicitAs
      case 4 => importedAs
      case 5 => importedEntity
      case 6 => isExplicit
      case 7 => isWildcard
      case 8 => lineNumber
      case 9 => order
    }

  override def productPrefix = "Import"
  override def productArity  = 10
}

class ImportDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with ImportBase {

  override def layoutInformation: NodeLayoutInformation = Import.layoutInformation

  private var _code: String                  = Import.PropertyDefaults.Code
  def code: String                           = _code
  private var _columnNumber: Integer         = null
  def columnNumber: Option[Integer]          = Option(_columnNumber)
  private var _explicitAs: java.lang.Boolean = null
  def explicitAs: Option[java.lang.Boolean]  = Option(_explicitAs)
  private var _importedAs: String            = null
  def importedAs: Option[String]             = Option(_importedAs)
  private var _importedEntity: String        = null
  def importedEntity: Option[String]         = Option(_importedEntity)
  private var _isExplicit: java.lang.Boolean = null
  def isExplicit: Option[java.lang.Boolean]  = Option(_isExplicit)
  private var _isWildcard: java.lang.Boolean = null
  def isWildcard: Option[java.lang.Boolean]  = Option(_isWildcard)
  private var _lineNumber: Integer           = null
  def lineNumber: Option[Integer]            = Option(_lineNumber)
  private var _order: scala.Int              = Import.PropertyDefaults.Order
  def order: scala.Int                       = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    explicitAs.map { value => properties.put("EXPLICIT_AS", value) }
    importedAs.map { value => properties.put("IMPORTED_AS", value) }
    importedEntity.map { value => properties.put("IMPORTED_ENTITY", value) }
    isExplicit.map { value => properties.put("IS_EXPLICIT", value) }
    isWildcard.map { value => properties.put("IS_WILDCARD", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    explicitAs.map { value => properties.put("EXPLICIT_AS", value) }
    importedAs.map { value => properties.put("IMPORTED_AS", value) }
    importedEntity.map { value => properties.put("IMPORTED_ENTITY", value) }
    isExplicit.map { value => properties.put("IS_EXPLICIT", value) }
    isWildcard.map { value => properties.put("IS_WILDCARD", value) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def importsOut: Iterator[Dependency] = createAdjacentNodeScalaIteratorByOffSet[Dependency](0)
  override def _importsOut             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def _dependencyViaImportsOut: overflowdb.traversal.Traversal[Dependency] = importsOut.collectAll[Dependency]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](1)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[AstNode]                              = createAdjacentNodeScalaIteratorByOffSet[AstNode](2)
  override def _astIn                                       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _blockViaAstIn: overflowdb.traversal.Traversal[Block] = astIn.collectAll[Block]
  def _fileViaAstIn: overflowdb.traversal.Traversal[File]   = astIn.collectAll[File]
  def _typeDeclViaAstIn: overflowdb.traversal.Traversal[TypeDecl] = astIn.collectAll[TypeDecl]

  def isCallForImportIn: Iterator[Call] = createAdjacentNodeScalaIteratorByOffSet[Call](3)
  override def _isCallForImportIn       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _callViaIsCallForImportIn: overflowdb.traversal.Traversal[Call] = isCallForImportIn.collectAll[Call]

  override def label: String = {
    Import.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "code"
      case 2 => "columnNumber"
      case 3 => "explicitAs"
      case 4 => "importedAs"
      case 5 => "importedEntity"
      case 6 => "isExplicit"
      case 7 => "isWildcard"
      case 8 => "lineNumber"
      case 9 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => code
      case 2 => columnNumber
      case 3 => explicitAs
      case 4 => importedAs
      case 5 => importedEntity
      case 6 => isExplicit
      case 7 => isWildcard
      case 8 => lineNumber
      case 9 => order
    }

  override def productPrefix = "Import"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ImportDb]

  override def property(key: String): Any = {
    key match {
      case "CODE"            => this._code
      case "COLUMN_NUMBER"   => this._columnNumber
      case "EXPLICIT_AS"     => this._explicitAs
      case "IMPORTED_AS"     => this._importedAs
      case "IMPORTED_ENTITY" => this._importedEntity
      case "IS_EXPLICIT"     => this._isExplicit
      case "IS_WILDCARD"     => this._isWildcard
      case "LINE_NUMBER"     => this._lineNumber
      case "ORDER"           => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CODE"            => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"   => this._columnNumber = value.asInstanceOf[Integer]
      case "EXPLICIT_AS"     => this._explicitAs = value.asInstanceOf[java.lang.Boolean]
      case "IMPORTED_AS"     => this._importedAs = value.asInstanceOf[String]
      case "IMPORTED_ENTITY" => this._importedEntity = value.asInstanceOf[String]
      case "IS_EXPLICIT"     => this._isExplicit = value.asInstanceOf[java.lang.Boolean]
      case "IS_WILDCARD"     => this._isWildcard = value.asInstanceOf[java.lang.Boolean]
      case "LINE_NUMBER"     => this._lineNumber = value.asInstanceOf[Integer]
      case "ORDER"           => this._order = value.asInstanceOf[scala.Int]

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
    this._code = newNode.asInstanceOf[NewImport].code
    this._columnNumber = newNode.asInstanceOf[NewImport].columnNumber.orNull
    this._explicitAs = newNode.asInstanceOf[NewImport].explicitAs.orNull
    this._importedAs = newNode.asInstanceOf[NewImport].importedAs.orNull
    this._importedEntity = newNode.asInstanceOf[NewImport].importedEntity.orNull
    this._isExplicit = newNode.asInstanceOf[NewImport].isExplicit.orNull
    this._isWildcard = newNode.asInstanceOf[NewImport].isWildcard.orNull
    this._lineNumber = newNode.asInstanceOf[NewImport].lineNumber.orNull
    this._order = newNode.asInstanceOf[NewImport].order

  }

}
