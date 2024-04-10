package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object TypeDecl {
  def apply(graph: Graph, id: Long) = new TypeDecl(graph, id)

  val Label = "TYPE_DECL"

  object PropertyNames {
    val AliasTypeFullName        = "ALIAS_TYPE_FULL_NAME"
    val AstParentFullName        = "AST_PARENT_FULL_NAME"
    val AstParentType            = "AST_PARENT_TYPE"
    val Code                     = "CODE"
    val ColumnNumber             = "COLUMN_NUMBER"
    val Filename                 = "FILENAME"
    val FullName                 = "FULL_NAME"
    val InheritsFromTypeFullName = "INHERITS_FROM_TYPE_FULL_NAME"
    val IsExternal               = "IS_EXTERNAL"
    val LineNumber               = "LINE_NUMBER"
    val Name                     = "NAME"
    val Offset                   = "OFFSET"
    val OffsetEnd                = "OFFSET_END"
    val Order                    = "ORDER"
    val all: Set[String] = Set(
      AliasTypeFullName,
      AstParentFullName,
      AstParentType,
      Code,
      ColumnNumber,
      Filename,
      FullName,
      InheritsFromTypeFullName,
      IsExternal,
      LineNumber,
      Name,
      Offset,
      OffsetEnd,
      Order
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val AliasTypeFullName        = new overflowdb.PropertyKey[String]("ALIAS_TYPE_FULL_NAME")
    val AstParentFullName        = new overflowdb.PropertyKey[String]("AST_PARENT_FULL_NAME")
    val AstParentType            = new overflowdb.PropertyKey[String]("AST_PARENT_TYPE")
    val Code                     = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber             = new overflowdb.PropertyKey[Integer]("COLUMN_NUMBER")
    val Filename                 = new overflowdb.PropertyKey[String]("FILENAME")
    val FullName                 = new overflowdb.PropertyKey[String]("FULL_NAME")
    val InheritsFromTypeFullName = new overflowdb.PropertyKey[IndexedSeq[String]]("INHERITS_FROM_TYPE_FULL_NAME")
    val IsExternal               = new overflowdb.PropertyKey[Boolean]("IS_EXTERNAL")
    val LineNumber               = new overflowdb.PropertyKey[Integer]("LINE_NUMBER")
    val Name                     = new overflowdb.PropertyKey[String]("NAME")
    val Offset                   = new overflowdb.PropertyKey[Integer]("OFFSET")
    val OffsetEnd                = new overflowdb.PropertyKey[Integer]("OFFSET_END")
    val Order                    = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val AstParentFullName = "<empty>"
    val AstParentType     = "<empty>"
    val Code              = "<empty>"
    val Filename          = "<empty>"
    val FullName          = "<empty>"
    val IsExternal        = false
    val Name              = "<empty>"
    val Order             = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.AliasOf.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Binds.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.InheritsFrom.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ALIAS_OF", "AST", "BINDS", "CONTAINS", "INHERITS_FROM", "SOURCE_FILE", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CONTAINS", "REF")
  }

  val factory = new NodeFactory[TypeDeclDb] {
    override val forLabel = TypeDecl.Label

    override def createNode(ref: NodeRef[TypeDeclDb]) =
      new TypeDeclDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TypeDecl(graph, id)
  }
}

trait TypeDeclBase extends AbstractNode with AstNodeBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def aliasTypeFullName: Option[String]
  def astParentFullName: String
  def astParentType: String
  def code: String
  def columnNumber: Option[Integer]
  def filename: String
  def fullName: String
  def inheritsFromTypeFullName: IndexedSeq[String]
  def isExternal: Boolean
  def lineNumber: Option[Integer]
  def name: String
  def offset: Option[Integer]
  def offsetEnd: Option[Integer]
  def order: scala.Int

}

class TypeDecl(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[TypeDeclDb](graph_4762, id_4762)
    with TypeDeclBase
    with StoredNode
    with AstNode {
  override def aliasTypeFullName: Option[String]            = get().aliasTypeFullName
  override def astParentFullName: String                    = get().astParentFullName
  override def astParentType: String                        = get().astParentType
  override def code: String                                 = get().code
  override def columnNumber: Option[Integer]                = get().columnNumber
  override def filename: String                             = get().filename
  override def fullName: String                             = get().fullName
  override def inheritsFromTypeFullName: IndexedSeq[String] = get().inheritsFromTypeFullName
  override def isExternal: Boolean                          = get().isExternal
  override def lineNumber: Option[Integer]                  = get().lineNumber
  override def name: String                                 = get().name
  override def offset: Option[Integer]                      = get().offset
  override def offsetEnd: Option[Integer]                   = get().offsetEnd
  override def order: scala.Int                             = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "AST_PARENT_FULL_NAME" => TypeDecl.PropertyDefaults.AstParentFullName
      case "AST_PARENT_TYPE"      => TypeDecl.PropertyDefaults.AstParentType
      case "CODE"                 => TypeDecl.PropertyDefaults.Code
      case "FILENAME"             => TypeDecl.PropertyDefaults.Filename
      case "FULL_NAME"            => TypeDecl.PropertyDefaults.FullName
      case "IS_EXTERNAL"          => TypeDecl.PropertyDefaults.IsExternal
      case "NAME"                 => TypeDecl.PropertyDefaults.Name
      case "ORDER"                => TypeDecl.PropertyDefaults.Order
      case _                      => super.propertyDefaultValue(propertyKey)
    }
  }

  def aliasOfOut: Iterator[Type] = get().aliasOfOut
  override def _aliasOfOut       = get()._aliasOfOut

  /** Traverse to TYPE via ALIAS_OF OUT edge.
    */
  def aliasedType: overflowdb.traversal.Traversal[Type] = get().aliasedType

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get()._annotationViaAstOut

  /** Traverse to IMPORT via AST OUT edge.
    */
  def _importViaAstOut: overflowdb.traversal.Traversal[Import] = get()._importViaAstOut

  /** Traverse to MEMBER via AST OUT edge.
    */
  def _memberViaAstOut: overflowdb.traversal.Traversal[Member] = get()._memberViaAstOut

  /** Traverse to METHOD via AST OUT edge.
    */
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method] = get()._methodViaAstOut

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def _modifierViaAstOut: overflowdb.traversal.Traversal[Modifier] = get()._modifierViaAstOut

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaAstOut

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def _typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = get()._typeParameterViaAstOut

  def bindsOut: Iterator[Binding] = get().bindsOut
  override def _bindsOut          = get()._bindsOut

  /** Traverse to BINDING via BINDS OUT edge.
    */
  def _bindingViaBindsOut: overflowdb.traversal.Traversal[Binding] = get()._bindingViaBindsOut

  def containsOut: Iterator[Method] = get().containsOut
  override def _containsOut         = get()._containsOut

  /** Traverse to METHOD via CONTAINS OUT edge.
    */
  def _methodViaContainsOut: overflowdb.traversal.Traversal[Method] = get()._methodViaContainsOut

  def inheritsFromOut: Iterator[Type] = get().inheritsFromOut
  override def _inheritsFromOut       = get()._inheritsFromOut

  /** Traverse to TYPE via INHERITS_FROM OUT edge.
    */
  def _typeViaInheritsFromOut: overflowdb.traversal.Traversal[Type] = get()._typeViaInheritsFromOut

  def sourceFileOut: Iterator[File] = get().sourceFileOut
  override def _sourceFileOut       = get()._sourceFileOut

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = get()._fileViaSourceFileOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def _methodViaAstIn: Option[Method] = get()._methodViaAstIn

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlock: Option[NamespaceBlock] = get().namespaceBlock

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def _typeDeclViaAstIn: Option[TypeDecl] = get()._typeDeclViaAstIn

  def containsIn: Iterator[File] = get().containsIn
  override def _containsIn       = get()._containsIn

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = get()._fileViaContainsIn

  def refIn: Iterator[Type] = get().refIn
  override def _refIn       = get()._refIn

  /** Traverse to TYPE via REF IN edge.
    */
  def _typeViaRefIn: overflowdb.traversal.Traversal[Type] = get()._typeViaRefIn

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
    TypeDecl.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "aliasTypeFullName"
      case 2  => "astParentFullName"
      case 3  => "astParentType"
      case 4  => "code"
      case 5  => "columnNumber"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "inheritsFromTypeFullName"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "name"
      case 12 => "offset"
      case 13 => "offsetEnd"
      case 14 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => aliasTypeFullName
      case 2  => astParentFullName
      case 3  => astParentType
      case 4  => code
      case 5  => columnNumber
      case 6  => filename
      case 7  => fullName
      case 8  => inheritsFromTypeFullName
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => name
      case 12 => offset
      case 13 => offsetEnd
      case 14 => order
    }

  override def productPrefix = "TypeDecl"
  override def productArity  = 15
}

class TypeDeclDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with AstNode with TypeDeclBase {

  override def layoutInformation: NodeLayoutInformation = TypeDecl.layoutInformation

  private var _aliasTypeFullName: String                    = null
  def aliasTypeFullName: Option[String]                     = Option(_aliasTypeFullName)
  private var _astParentFullName: String                    = TypeDecl.PropertyDefaults.AstParentFullName
  def astParentFullName: String                             = _astParentFullName
  private var _astParentType: String                        = TypeDecl.PropertyDefaults.AstParentType
  def astParentType: String                                 = _astParentType
  private var _code: String                                 = TypeDecl.PropertyDefaults.Code
  def code: String                                          = _code
  private var _columnNumber: Integer                        = null
  def columnNumber: Option[Integer]                         = Option(_columnNumber)
  private var _filename: String                             = TypeDecl.PropertyDefaults.Filename
  def filename: String                                      = _filename
  private var _fullName: String                             = TypeDecl.PropertyDefaults.FullName
  def fullName: String                                      = _fullName
  private var _inheritsFromTypeFullName: IndexedSeq[String] = collection.immutable.ArraySeq.empty
  def inheritsFromTypeFullName: IndexedSeq[String]          = _inheritsFromTypeFullName
  private var _isExternal: Boolean                          = TypeDecl.PropertyDefaults.IsExternal
  def isExternal: Boolean                                   = _isExternal
  private var _lineNumber: Integer                          = null
  def lineNumber: Option[Integer]                           = Option(_lineNumber)
  private var _name: String                                 = TypeDecl.PropertyDefaults.Name
  def name: String                                          = _name
  private var _offset: Integer                              = null
  def offset: Option[Integer]                               = Option(_offset)
  private var _offsetEnd: Integer                           = null
  def offsetEnd: Option[Integer]                            = Option(_offsetEnd)
  private var _order: scala.Int                             = TypeDecl.PropertyDefaults.Order
  def order: scala.Int                                      = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    aliasTypeFullName.map { value => properties.put("ALIAS_TYPE_FULL_NAME", value) }
    properties.put("AST_PARENT_FULL_NAME", astParentFullName)
    properties.put("AST_PARENT_TYPE", astParentType)
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("FILENAME", filename)
    properties.put("FULL_NAME", fullName)
    if (this._inheritsFromTypeFullName != null && this._inheritsFromTypeFullName.nonEmpty) {
      properties.put("INHERITS_FROM_TYPE_FULL_NAME", inheritsFromTypeFullName)
    }
    properties.put("IS_EXTERNAL", isExternal)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    properties.put("ORDER", order)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    aliasTypeFullName.map { value => properties.put("ALIAS_TYPE_FULL_NAME", value) }
    if (!(("<empty>") == astParentFullName)) { properties.put("AST_PARENT_FULL_NAME", astParentFullName) }
    if (!(("<empty>") == astParentType)) { properties.put("AST_PARENT_TYPE", astParentType) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    if (this._inheritsFromTypeFullName != null && this._inheritsFromTypeFullName.nonEmpty) {
      properties.put("INHERITS_FROM_TYPE_FULL_NAME", inheritsFromTypeFullName)
    }
    if (!((false) == isExternal)) { properties.put("IS_EXTERNAL", isExternal) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def aliasOfOut: Iterator[Type]                        = createAdjacentNodeScalaIteratorByOffSet[Type](0)
  override def _aliasOfOut                              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)
  def aliasedType: overflowdb.traversal.Traversal[Type] = aliasOfOut.collectAll[Type]

  def astOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](1)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  def _annotationViaAstOut: overflowdb.traversal.Traversal[Annotation]       = astOut.collectAll[Annotation]
  def _importViaAstOut: overflowdb.traversal.Traversal[Import]               = astOut.collectAll[Import]
  def _memberViaAstOut: overflowdb.traversal.Traversal[Member]               = astOut.collectAll[Member]
  def _methodViaAstOut: overflowdb.traversal.Traversal[Method]               = astOut.collectAll[Method]
  def _modifierViaAstOut: overflowdb.traversal.Traversal[Modifier]           = astOut.collectAll[Modifier]
  def _typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl]           = astOut.collectAll[TypeDecl]
  def _typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = astOut.collectAll[TypeParameter]

  def bindsOut: Iterator[Binding] = createAdjacentNodeScalaIteratorByOffSet[Binding](2)
  override def _bindsOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)
  def _bindingViaBindsOut: overflowdb.traversal.Traversal[Binding] = bindsOut.collectAll[Binding]

  def containsOut: Iterator[Method] = createAdjacentNodeScalaIteratorByOffSet[Method](3)
  override def _containsOut         = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)
  def _methodViaContainsOut: overflowdb.traversal.Traversal[Method] = containsOut.collectAll[Method]

  def inheritsFromOut: Iterator[Type] = createAdjacentNodeScalaIteratorByOffSet[Type](4)
  override def _inheritsFromOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)
  def _typeViaInheritsFromOut: overflowdb.traversal.Traversal[Type] = inheritsFromOut.collectAll[Type]

  def sourceFileOut: Iterator[File] = createAdjacentNodeScalaIteratorByOffSet[File](5)
  override def _sourceFileOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)
  def _fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = sourceFileOut.collectAll[File]

  def taggedByOut: Iterator[Tag]                              = createAdjacentNodeScalaIteratorByOffSet[Tag](6)
  override def _taggedByOut                                   = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[AstNode]               = createAdjacentNodeScalaIteratorByOffSet[AstNode](7)
  override def _astIn                        = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)
  def _methodViaAstIn: Option[Method]        = astIn.collectAll[Method].nextOption()
  def namespaceBlock: Option[NamespaceBlock] = astIn.collectAll[NamespaceBlock].nextOption()
  def _typeDeclViaAstIn: Option[TypeDecl]    = astIn.collectAll[TypeDecl].nextOption()

  def containsIn: Iterator[File]                               = createAdjacentNodeScalaIteratorByOffSet[File](8)
  override def _containsIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)
  def _fileViaContainsIn: overflowdb.traversal.Traversal[File] = containsIn.collectAll[File]

  def refIn: Iterator[Type]                               = createAdjacentNodeScalaIteratorByOffSet[Type](9)
  override def _refIn                                     = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)
  def _typeViaRefIn: overflowdb.traversal.Traversal[Type] = refIn.collectAll[Type]

  override def label: String = {
    TypeDecl.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "aliasTypeFullName"
      case 2  => "astParentFullName"
      case 3  => "astParentType"
      case 4  => "code"
      case 5  => "columnNumber"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "inheritsFromTypeFullName"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "name"
      case 12 => "offset"
      case 13 => "offsetEnd"
      case 14 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => aliasTypeFullName
      case 2  => astParentFullName
      case 3  => astParentType
      case 4  => code
      case 5  => columnNumber
      case 6  => filename
      case 7  => fullName
      case 8  => inheritsFromTypeFullName
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => name
      case 12 => offset
      case 13 => offsetEnd
      case 14 => order
    }

  override def productPrefix = "TypeDecl"
  override def productArity  = 15

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeDeclDb]

  override def property(key: String): Any = {
    key match {
      case "ALIAS_TYPE_FULL_NAME"         => this._aliasTypeFullName
      case "AST_PARENT_FULL_NAME"         => this._astParentFullName
      case "AST_PARENT_TYPE"              => this._astParentType
      case "CODE"                         => this._code
      case "COLUMN_NUMBER"                => this._columnNumber
      case "FILENAME"                     => this._filename
      case "FULL_NAME"                    => this._fullName
      case "INHERITS_FROM_TYPE_FULL_NAME" => this._inheritsFromTypeFullName
      case "IS_EXTERNAL"                  => this._isExternal
      case "LINE_NUMBER"                  => this._lineNumber
      case "NAME"                         => this._name
      case "OFFSET"                       => this._offset
      case "OFFSET_END"                   => this._offsetEnd
      case "ORDER"                        => this._order

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "ALIAS_TYPE_FULL_NAME" => this._aliasTypeFullName = value.asInstanceOf[String]
      case "AST_PARENT_FULL_NAME" => this._astParentFullName = value.asInstanceOf[String]
      case "AST_PARENT_TYPE"      => this._astParentType = value.asInstanceOf[String]
      case "CODE"                 => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"        => this._columnNumber = value.asInstanceOf[Integer]
      case "FILENAME"             => this._filename = value.asInstanceOf[String]
      case "FULL_NAME"            => this._fullName = value.asInstanceOf[String]
      case "INHERITS_FROM_TYPE_FULL_NAME" =>
        this._inheritsFromTypeFullName = value match {
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
      case "IS_EXTERNAL" => this._isExternal = value.asInstanceOf[Boolean]
      case "LINE_NUMBER" => this._lineNumber = value.asInstanceOf[Integer]
      case "NAME"        => this._name = value.asInstanceOf[String]
      case "OFFSET"      => this._offset = value.asInstanceOf[Integer]
      case "OFFSET_END"  => this._offsetEnd = value.asInstanceOf[Integer]
      case "ORDER"       => this._order = value.asInstanceOf[scala.Int]

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
    this._aliasTypeFullName = newNode.asInstanceOf[NewTypeDecl].aliasTypeFullName.orNull
    this._astParentFullName = newNode.asInstanceOf[NewTypeDecl].astParentFullName
    this._astParentType = newNode.asInstanceOf[NewTypeDecl].astParentType
    this._code = newNode.asInstanceOf[NewTypeDecl].code
    this._columnNumber = newNode.asInstanceOf[NewTypeDecl].columnNumber.orNull
    this._filename = newNode.asInstanceOf[NewTypeDecl].filename
    this._fullName = newNode.asInstanceOf[NewTypeDecl].fullName
    this._inheritsFromTypeFullName =
      if (newNode.asInstanceOf[NewTypeDecl].inheritsFromTypeFullName != null)
        newNode.asInstanceOf[NewTypeDecl].inheritsFromTypeFullName
      else collection.immutable.ArraySeq.empty
    this._isExternal = newNode.asInstanceOf[NewTypeDecl].isExternal
    this._lineNumber = newNode.asInstanceOf[NewTypeDecl].lineNumber.orNull
    this._name = newNode.asInstanceOf[NewTypeDecl].name
    this._offset = newNode.asInstanceOf[NewTypeDecl].offset.orNull
    this._offsetEnd = newNode.asInstanceOf[NewTypeDecl].offsetEnd.orNull
    this._order = newNode.asInstanceOf[NewTypeDecl].order

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewTypeDecl].fullName, this.ref)
  }

}
