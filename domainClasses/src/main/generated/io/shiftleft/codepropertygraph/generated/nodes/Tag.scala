package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Tag {
  def apply(graph: Graph, id: Long) = new Tag(graph, id)

  val Label = "TAG"

  object PropertyNames {
    val Name                             = "NAME"
    val Value                            = "VALUE"
    val all: Set[String]                 = Set(Name, Value)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Name  = new overflowdb.PropertyKey[String]("NAME")
    val Value = new overflowdb.PropertyKey[String]("VALUE")

  }

  object PropertyDefaults {
    val Name  = "<empty>"
    val Value = ""
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation).asJava,
    List(io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation).asJava
  )

  object Edges {
    val Out: Array[String] = Array("TAGGED_BY")
    val In: Array[String]  = Array("TAGGED_BY")
  }

  val factory = new NodeFactory[TagDb] {
    override val forLabel = Tag.Label

    override def createNode(ref: NodeRef[TagDb]) =
      new TagDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Tag(graph, id)
  }
}

trait TagBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def name: String
  def value: String

}

class Tag(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[TagDb](graph_4762, id_4762)
    with TagBase
    with StoredNode {
  override def name: String  = get().name
  override def value: String = get().value
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "NAME"  => Tag.PropertyDefaults.Name
      case "VALUE" => Tag.PropertyDefaults.Value
      case _       => super.propertyDefaultValue(propertyKey)
    }
  }

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByOut

  @deprecated("please use `_tagViaTaggedByOut`", "June 2024")
  def __tagViaTaggedByOut = _tagViaTaggedByOut

  def taggedByIn: Iterator[StoredNode] = get().taggedByIn
  override def _taggedByIn             = get()._taggedByIn

  /** Traverse to BLOCK via TAGGED_BY IN edge.
    */
  def _blockViaTaggedByIn: overflowdb.traversal.Traversal[Block] = get()._blockViaTaggedByIn

  @deprecated("please use `_blockViaTaggedByIn`", "June 2024")
  def __blockViaTaggedByIn = _blockViaTaggedByIn

  /** Traverse to CALL via TAGGED_BY IN edge.
    */
  def _callViaTaggedByIn: overflowdb.traversal.Traversal[Call] = get()._callViaTaggedByIn

  @deprecated("please use `_callViaTaggedByIn`", "June 2024")
  def __callViaTaggedByIn = _callViaTaggedByIn

  /** Traverse to CONTROL_STRUCTURE via TAGGED_BY IN edge.
    */
  def _controlStructureViaTaggedByIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaTaggedByIn

  @deprecated("please use `_controlStructureViaTaggedByIn`", "June 2024")
  def __controlStructureViaTaggedByIn = _controlStructureViaTaggedByIn

  /** Traverse to FIELD_IDENTIFIER via TAGGED_BY IN edge.
    */
  def _fieldIdentifierViaTaggedByIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get()._fieldIdentifierViaTaggedByIn

  @deprecated("please use `_fieldIdentifierViaTaggedByIn`", "June 2024")
  def __fieldIdentifierViaTaggedByIn = _fieldIdentifierViaTaggedByIn

  /** Traverse to FILE via TAGGED_BY IN edge.
    */
  def _fileViaTaggedByIn: overflowdb.traversal.Traversal[File] = get()._fileViaTaggedByIn

  @deprecated("please use `_fileViaTaggedByIn`", "June 2024")
  def __fileViaTaggedByIn = _fileViaTaggedByIn

  /** Traverse to IDENTIFIER via TAGGED_BY IN edge.
    */
  def _identifierViaTaggedByIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaTaggedByIn

  @deprecated("please use `_identifierViaTaggedByIn`", "June 2024")
  def __identifierViaTaggedByIn = _identifierViaTaggedByIn

  /** Traverse to IMPORT via TAGGED_BY IN edge.
    */
  def _importViaTaggedByIn: overflowdb.traversal.Traversal[Import] = get()._importViaTaggedByIn

  @deprecated("please use `_importViaTaggedByIn`", "June 2024")
  def __importViaTaggedByIn = _importViaTaggedByIn

  /** Traverse to JUMP_TARGET via TAGGED_BY IN edge.
    */
  def _jumpTargetViaTaggedByIn: overflowdb.traversal.Traversal[JumpTarget] = get()._jumpTargetViaTaggedByIn

  @deprecated("please use `_jumpTargetViaTaggedByIn`", "June 2024")
  def __jumpTargetViaTaggedByIn = _jumpTargetViaTaggedByIn

  /** Traverse to LITERAL via TAGGED_BY IN edge.
    */
  def _literalViaTaggedByIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaTaggedByIn

  @deprecated("please use `_literalViaTaggedByIn`", "June 2024")
  def __literalViaTaggedByIn = _literalViaTaggedByIn

  /** Traverse to LOCAL via TAGGED_BY IN edge.
    */
  def _localViaTaggedByIn: overflowdb.traversal.Traversal[Local] = get()._localViaTaggedByIn

  @deprecated("please use `_localViaTaggedByIn`", "June 2024")
  def __localViaTaggedByIn = _localViaTaggedByIn

  /** Traverse to MEMBER via TAGGED_BY IN edge.
    */
  def _memberViaTaggedByIn: overflowdb.traversal.Traversal[Member] = get()._memberViaTaggedByIn

  @deprecated("please use `_memberViaTaggedByIn`", "June 2024")
  def __memberViaTaggedByIn = _memberViaTaggedByIn

  /** Traverse to METHOD via TAGGED_BY IN edge.
    */
  def _methodViaTaggedByIn: overflowdb.traversal.Traversal[Method] = get()._methodViaTaggedByIn

  @deprecated("please use `_methodViaTaggedByIn`", "June 2024")
  def __methodViaTaggedByIn = _methodViaTaggedByIn

  /** Traverse to METHOD_PARAMETER_IN via TAGGED_BY IN edge.
    */
  def _methodParameterInViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaTaggedByIn

  @deprecated("please use `_methodParameterInViaTaggedByIn`", "June 2024")
  def __methodParameterInViaTaggedByIn = _methodParameterInViaTaggedByIn

  /** Traverse to METHOD_PARAMETER_OUT via TAGGED_BY IN edge.
    */
  def _methodParameterOutViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaTaggedByIn

  @deprecated("please use `_methodParameterOutViaTaggedByIn`", "June 2024")
  def __methodParameterOutViaTaggedByIn = _methodParameterOutViaTaggedByIn

  /** Traverse to METHOD_REF via TAGGED_BY IN edge.
    */
  def _methodRefViaTaggedByIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaTaggedByIn

  @deprecated("please use `_methodRefViaTaggedByIn`", "June 2024")
  def __methodRefViaTaggedByIn = _methodRefViaTaggedByIn

  /** Traverse to METHOD_RETURN via TAGGED_BY IN edge.
    */
  def _methodReturnViaTaggedByIn: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaTaggedByIn

  @deprecated("please use `_methodReturnViaTaggedByIn`", "June 2024")
  def __methodReturnViaTaggedByIn = _methodReturnViaTaggedByIn

  /** Traverse to RETURN via TAGGED_BY IN edge.
    */
  def _returnViaTaggedByIn: overflowdb.traversal.Traversal[Return] = get()._returnViaTaggedByIn

  @deprecated("please use `_returnViaTaggedByIn`", "June 2024")
  def __returnViaTaggedByIn = _returnViaTaggedByIn

  /** Traverse to TAG via TAGGED_BY IN edge.
    */
  def _tagViaTaggedByIn: overflowdb.traversal.Traversal[Tag] = get()._tagViaTaggedByIn

  @deprecated("please use `_tagViaTaggedByIn`", "June 2024")
  def __tagViaTaggedByIn = _tagViaTaggedByIn

  /** Traverse to TEMPLATE_DOM via TAGGED_BY IN edge.
    */
  def _templateDomViaTaggedByIn: overflowdb.traversal.Traversal[TemplateDom] = get()._templateDomViaTaggedByIn

  @deprecated("please use `_templateDomViaTaggedByIn`", "June 2024")
  def __templateDomViaTaggedByIn = _templateDomViaTaggedByIn

  /** Traverse to TYPE_DECL via TAGGED_BY IN edge.
    */
  def _typeDeclViaTaggedByIn: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaTaggedByIn

  @deprecated("please use `_typeDeclViaTaggedByIn`", "June 2024")
  def __typeDeclViaTaggedByIn = _typeDeclViaTaggedByIn

  /** Traverse to TYPE_REF via TAGGED_BY IN edge.
    */
  def _typeRefViaTaggedByIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaTaggedByIn

  @deprecated("please use `_typeRefViaTaggedByIn`", "June 2024")
  def __typeRefViaTaggedByIn = _typeRefViaTaggedByIn

  /** Traverse to UNKNOWN via TAGGED_BY IN edge.
    */
  def _unknownViaTaggedByIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaTaggedByIn

  @deprecated("please use `_unknownViaTaggedByIn`", "June 2024")
  def __unknownViaTaggedByIn = _unknownViaTaggedByIn

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
    Tag.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "name"
      case 2 => "value"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => name
      case 2 => value
    }

  override def productPrefix = "Tag"
  override def productArity  = 3
}

class TagDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with TagBase {

  override def layoutInformation: NodeLayoutInformation = Tag.layoutInformation

  private var _name: String  = Tag.PropertyDefaults.Name
  def name: String           = _name
  private var _value: String = Tag.PropertyDefaults.Value
  def value: String          = _value

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("NAME", name)
    properties.put("VALUE", value)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!(("") == value)) { properties.put("VALUE", value) }

    properties
  }

  import overflowdb.traversal._
  def taggedByOut: Iterator[Tag] = createAdjacentNodeScalaIteratorByOffSet[Tag](0)
  override def _taggedByOut      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  @deprecated("please use `_tagViaTaggedByOut`", "June 2024")
  def __tagViaTaggedByOut = _tagViaTaggedByOut

  def _tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def taggedByIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  override def _taggedByIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `_blockViaTaggedByIn`", "June 2024")
  def __blockViaTaggedByIn = _blockViaTaggedByIn

  def _blockViaTaggedByIn: overflowdb.traversal.Traversal[Block] = taggedByIn.collectAll[Block]
  @deprecated("please use `_callViaTaggedByIn`", "June 2024")
  def __callViaTaggedByIn = _callViaTaggedByIn

  def _callViaTaggedByIn: overflowdb.traversal.Traversal[Call] = taggedByIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaTaggedByIn`", "June 2024")
  def __controlStructureViaTaggedByIn = _controlStructureViaTaggedByIn

  def _controlStructureViaTaggedByIn: overflowdb.traversal.Traversal[ControlStructure] =
    taggedByIn.collectAll[ControlStructure]
  @deprecated("please use `_fieldIdentifierViaTaggedByIn`", "June 2024")
  def __fieldIdentifierViaTaggedByIn = _fieldIdentifierViaTaggedByIn

  def _fieldIdentifierViaTaggedByIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    taggedByIn.collectAll[FieldIdentifier]
  @deprecated("please use `_fileViaTaggedByIn`", "June 2024")
  def __fileViaTaggedByIn = _fileViaTaggedByIn

  def _fileViaTaggedByIn: overflowdb.traversal.Traversal[File] = taggedByIn.collectAll[File]
  @deprecated("please use `_identifierViaTaggedByIn`", "June 2024")
  def __identifierViaTaggedByIn = _identifierViaTaggedByIn

  def _identifierViaTaggedByIn: overflowdb.traversal.Traversal[Identifier] = taggedByIn.collectAll[Identifier]
  @deprecated("please use `_importViaTaggedByIn`", "June 2024")
  def __importViaTaggedByIn = _importViaTaggedByIn

  def _importViaTaggedByIn: overflowdb.traversal.Traversal[Import] = taggedByIn.collectAll[Import]
  @deprecated("please use `_jumpTargetViaTaggedByIn`", "June 2024")
  def __jumpTargetViaTaggedByIn = _jumpTargetViaTaggedByIn

  def _jumpTargetViaTaggedByIn: overflowdb.traversal.Traversal[JumpTarget] = taggedByIn.collectAll[JumpTarget]
  @deprecated("please use `_literalViaTaggedByIn`", "June 2024")
  def __literalViaTaggedByIn = _literalViaTaggedByIn

  def _literalViaTaggedByIn: overflowdb.traversal.Traversal[Literal] = taggedByIn.collectAll[Literal]
  @deprecated("please use `_localViaTaggedByIn`", "June 2024")
  def __localViaTaggedByIn = _localViaTaggedByIn

  def _localViaTaggedByIn: overflowdb.traversal.Traversal[Local] = taggedByIn.collectAll[Local]
  @deprecated("please use `_memberViaTaggedByIn`", "June 2024")
  def __memberViaTaggedByIn = _memberViaTaggedByIn

  def _memberViaTaggedByIn: overflowdb.traversal.Traversal[Member] = taggedByIn.collectAll[Member]
  @deprecated("please use `_methodViaTaggedByIn`", "June 2024")
  def __methodViaTaggedByIn = _methodViaTaggedByIn

  def _methodViaTaggedByIn: overflowdb.traversal.Traversal[Method] = taggedByIn.collectAll[Method]
  @deprecated("please use `_methodParameterInViaTaggedByIn`", "June 2024")
  def __methodParameterInViaTaggedByIn = _methodParameterInViaTaggedByIn

  def _methodParameterInViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    taggedByIn.collectAll[MethodParameterIn]
  @deprecated("please use `_methodParameterOutViaTaggedByIn`", "June 2024")
  def __methodParameterOutViaTaggedByIn = _methodParameterOutViaTaggedByIn

  def _methodParameterOutViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    taggedByIn.collectAll[MethodParameterOut]
  @deprecated("please use `_methodRefViaTaggedByIn`", "June 2024")
  def __methodRefViaTaggedByIn = _methodRefViaTaggedByIn

  def _methodRefViaTaggedByIn: overflowdb.traversal.Traversal[MethodRef] = taggedByIn.collectAll[MethodRef]
  @deprecated("please use `_methodReturnViaTaggedByIn`", "June 2024")
  def __methodReturnViaTaggedByIn = _methodReturnViaTaggedByIn

  def _methodReturnViaTaggedByIn: overflowdb.traversal.Traversal[MethodReturn] = taggedByIn.collectAll[MethodReturn]
  @deprecated("please use `_returnViaTaggedByIn`", "June 2024")
  def __returnViaTaggedByIn = _returnViaTaggedByIn

  def _returnViaTaggedByIn: overflowdb.traversal.Traversal[Return] = taggedByIn.collectAll[Return]
  @deprecated("please use `_tagViaTaggedByIn`", "June 2024")
  def __tagViaTaggedByIn = _tagViaTaggedByIn

  def _tagViaTaggedByIn: overflowdb.traversal.Traversal[Tag] = taggedByIn.collectAll[Tag]
  @deprecated("please use `_templateDomViaTaggedByIn`", "June 2024")
  def __templateDomViaTaggedByIn = _templateDomViaTaggedByIn

  def _templateDomViaTaggedByIn: overflowdb.traversal.Traversal[TemplateDom] = taggedByIn.collectAll[TemplateDom]
  @deprecated("please use `_typeDeclViaTaggedByIn`", "June 2024")
  def __typeDeclViaTaggedByIn = _typeDeclViaTaggedByIn

  def _typeDeclViaTaggedByIn: overflowdb.traversal.Traversal[TypeDecl] = taggedByIn.collectAll[TypeDecl]
  @deprecated("please use `_typeRefViaTaggedByIn`", "June 2024")
  def __typeRefViaTaggedByIn = _typeRefViaTaggedByIn

  def _typeRefViaTaggedByIn: overflowdb.traversal.Traversal[TypeRef] = taggedByIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaTaggedByIn`", "June 2024")
  def __unknownViaTaggedByIn = _unknownViaTaggedByIn

  def _unknownViaTaggedByIn: overflowdb.traversal.Traversal[Unknown] = taggedByIn.collectAll[Unknown]

  override def label: String = {
    Tag.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "name"
      case 2 => "value"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => name
      case 2 => value
    }

  override def productPrefix = "Tag"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagDb]

  override def property(key: String): Any = {
    key match {
      case "NAME"  => this._name
      case "VALUE" => this._value

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "NAME"  => this._name = value.asInstanceOf[String]
      case "VALUE" => this._value = value.asInstanceOf[String]

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
    this._name = newNode.asInstanceOf[NewTag].name
    this._value = newNode.asInstanceOf[NewTag].value

  }

}
