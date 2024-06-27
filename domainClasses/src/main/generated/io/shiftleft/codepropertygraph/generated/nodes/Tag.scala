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
  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get().tagViaTaggedByOut

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def taggedByIn: Iterator[StoredNode] = get().taggedByIn
  override def _taggedByIn             = get()._taggedByIn

  /** Traverse to BLOCK via TAGGED_BY IN edge.
    */
  def blockViaTaggedByIn: overflowdb.traversal.Traversal[Block] = get().blockViaTaggedByIn

  @deprecated("please use `blockViaTaggedByIn`", "June 2024")
  def _blockViaTaggedByIn = blockViaTaggedByIn

  /** Traverse to CALL via TAGGED_BY IN edge.
    */
  def callViaTaggedByIn: overflowdb.traversal.Traversal[Call] = get().callViaTaggedByIn

  @deprecated("please use `callViaTaggedByIn`", "June 2024")
  def _callViaTaggedByIn = callViaTaggedByIn

  /** Traverse to CONTROL_STRUCTURE via TAGGED_BY IN edge.
    */
  def controlStructureViaTaggedByIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaTaggedByIn

  @deprecated("please use `controlStructureViaTaggedByIn`", "June 2024")
  def _controlStructureViaTaggedByIn = controlStructureViaTaggedByIn

  /** Traverse to FIELD_IDENTIFIER via TAGGED_BY IN edge.
    */
  def fieldIdentifierViaTaggedByIn: overflowdb.traversal.Traversal[FieldIdentifier] = get().fieldIdentifierViaTaggedByIn

  @deprecated("please use `fieldIdentifierViaTaggedByIn`", "June 2024")
  def _fieldIdentifierViaTaggedByIn = fieldIdentifierViaTaggedByIn

  /** Traverse to FILE via TAGGED_BY IN edge.
    */
  def fileViaTaggedByIn: overflowdb.traversal.Traversal[File] = get().fileViaTaggedByIn

  @deprecated("please use `fileViaTaggedByIn`", "June 2024")
  def _fileViaTaggedByIn = fileViaTaggedByIn

  /** Traverse to IDENTIFIER via TAGGED_BY IN edge.
    */
  def identifierViaTaggedByIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaTaggedByIn

  @deprecated("please use `identifierViaTaggedByIn`", "June 2024")
  def _identifierViaTaggedByIn = identifierViaTaggedByIn

  /** Traverse to IMPORT via TAGGED_BY IN edge.
    */
  def importViaTaggedByIn: overflowdb.traversal.Traversal[Import] = get().importViaTaggedByIn

  @deprecated("please use `importViaTaggedByIn`", "June 2024")
  def _importViaTaggedByIn = importViaTaggedByIn

  /** Traverse to JUMP_TARGET via TAGGED_BY IN edge.
    */
  def jumpTargetViaTaggedByIn: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaTaggedByIn

  @deprecated("please use `jumpTargetViaTaggedByIn`", "June 2024")
  def _jumpTargetViaTaggedByIn = jumpTargetViaTaggedByIn

  /** Traverse to LITERAL via TAGGED_BY IN edge.
    */
  def literalViaTaggedByIn: overflowdb.traversal.Traversal[Literal] = get().literalViaTaggedByIn

  @deprecated("please use `literalViaTaggedByIn`", "June 2024")
  def _literalViaTaggedByIn = literalViaTaggedByIn

  /** Traverse to LOCAL via TAGGED_BY IN edge.
    */
  def localViaTaggedByIn: overflowdb.traversal.Traversal[Local] = get().localViaTaggedByIn

  @deprecated("please use `localViaTaggedByIn`", "June 2024")
  def _localViaTaggedByIn = localViaTaggedByIn

  /** Traverse to MEMBER via TAGGED_BY IN edge.
    */
  def memberViaTaggedByIn: overflowdb.traversal.Traversal[Member] = get().memberViaTaggedByIn

  @deprecated("please use `memberViaTaggedByIn`", "June 2024")
  def _memberViaTaggedByIn = memberViaTaggedByIn

  /** Traverse to METHOD via TAGGED_BY IN edge.
    */
  def methodViaTaggedByIn: overflowdb.traversal.Traversal[Method] = get().methodViaTaggedByIn

  @deprecated("please use `methodViaTaggedByIn`", "June 2024")
  def _methodViaTaggedByIn = methodViaTaggedByIn

  /** Traverse to METHOD_PARAMETER_IN via TAGGED_BY IN edge.
    */
  def methodParameterInViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get().methodParameterInViaTaggedByIn

  @deprecated("please use `methodParameterInViaTaggedByIn`", "June 2024")
  def _methodParameterInViaTaggedByIn = methodParameterInViaTaggedByIn

  /** Traverse to METHOD_PARAMETER_OUT via TAGGED_BY IN edge.
    */
  def methodParameterOutViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    get().methodParameterOutViaTaggedByIn

  @deprecated("please use `methodParameterOutViaTaggedByIn`", "June 2024")
  def _methodParameterOutViaTaggedByIn = methodParameterOutViaTaggedByIn

  /** Traverse to METHOD_REF via TAGGED_BY IN edge.
    */
  def methodRefViaTaggedByIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaTaggedByIn

  @deprecated("please use `methodRefViaTaggedByIn`", "June 2024")
  def _methodRefViaTaggedByIn = methodRefViaTaggedByIn

  /** Traverse to METHOD_RETURN via TAGGED_BY IN edge.
    */
  def methodReturnViaTaggedByIn: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaTaggedByIn

  @deprecated("please use `methodReturnViaTaggedByIn`", "June 2024")
  def _methodReturnViaTaggedByIn = methodReturnViaTaggedByIn

  /** Traverse to RETURN via TAGGED_BY IN edge.
    */
  def returnViaTaggedByIn: overflowdb.traversal.Traversal[Return] = get().returnViaTaggedByIn

  @deprecated("please use `returnViaTaggedByIn`", "June 2024")
  def _returnViaTaggedByIn = returnViaTaggedByIn

  /** Traverse to TAG via TAGGED_BY IN edge.
    */
  def tagViaTaggedByIn: overflowdb.traversal.Traversal[Tag] = get().tagViaTaggedByIn

  @deprecated("please use `tagViaTaggedByIn`", "June 2024")
  def _tagViaTaggedByIn = tagViaTaggedByIn

  /** Traverse to TEMPLATE_DOM via TAGGED_BY IN edge.
    */
  def templateDomViaTaggedByIn: overflowdb.traversal.Traversal[TemplateDom] = get().templateDomViaTaggedByIn

  @deprecated("please use `templateDomViaTaggedByIn`", "June 2024")
  def _templateDomViaTaggedByIn = templateDomViaTaggedByIn

  /** Traverse to TYPE_DECL via TAGGED_BY IN edge.
    */
  def typeDeclViaTaggedByIn: overflowdb.traversal.Traversal[TypeDecl] = get().typeDeclViaTaggedByIn

  @deprecated("please use `typeDeclViaTaggedByIn`", "June 2024")
  def _typeDeclViaTaggedByIn = typeDeclViaTaggedByIn

  /** Traverse to TYPE_REF via TAGGED_BY IN edge.
    */
  def typeRefViaTaggedByIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaTaggedByIn

  @deprecated("please use `typeRefViaTaggedByIn`", "June 2024")
  def _typeRefViaTaggedByIn = typeRefViaTaggedByIn

  /** Traverse to UNKNOWN via TAGGED_BY IN edge.
    */
  def unknownViaTaggedByIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaTaggedByIn

  @deprecated("please use `unknownViaTaggedByIn`", "June 2024")
  def _unknownViaTaggedByIn = unknownViaTaggedByIn

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

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def taggedByIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)
  override def _taggedByIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `blockViaTaggedByIn`", "June 2024")
  def _blockViaTaggedByIn = blockViaTaggedByIn

  def blockViaTaggedByIn: overflowdb.traversal.Traversal[Block] = taggedByIn.collectAll[Block]
  @deprecated("please use `callViaTaggedByIn`", "June 2024")
  def _callViaTaggedByIn = callViaTaggedByIn

  def callViaTaggedByIn: overflowdb.traversal.Traversal[Call] = taggedByIn.collectAll[Call]
  @deprecated("please use `controlStructureViaTaggedByIn`", "June 2024")
  def _controlStructureViaTaggedByIn = controlStructureViaTaggedByIn

  def controlStructureViaTaggedByIn: overflowdb.traversal.Traversal[ControlStructure] =
    taggedByIn.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaTaggedByIn`", "June 2024")
  def _fieldIdentifierViaTaggedByIn = fieldIdentifierViaTaggedByIn

  def fieldIdentifierViaTaggedByIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    taggedByIn.collectAll[FieldIdentifier]
  @deprecated("please use `fileViaTaggedByIn`", "June 2024")
  def _fileViaTaggedByIn = fileViaTaggedByIn

  def fileViaTaggedByIn: overflowdb.traversal.Traversal[File] = taggedByIn.collectAll[File]
  @deprecated("please use `identifierViaTaggedByIn`", "June 2024")
  def _identifierViaTaggedByIn = identifierViaTaggedByIn

  def identifierViaTaggedByIn: overflowdb.traversal.Traversal[Identifier] = taggedByIn.collectAll[Identifier]
  @deprecated("please use `importViaTaggedByIn`", "June 2024")
  def _importViaTaggedByIn = importViaTaggedByIn

  def importViaTaggedByIn: overflowdb.traversal.Traversal[Import] = taggedByIn.collectAll[Import]
  @deprecated("please use `jumpTargetViaTaggedByIn`", "June 2024")
  def _jumpTargetViaTaggedByIn = jumpTargetViaTaggedByIn

  def jumpTargetViaTaggedByIn: overflowdb.traversal.Traversal[JumpTarget] = taggedByIn.collectAll[JumpTarget]
  @deprecated("please use `literalViaTaggedByIn`", "June 2024")
  def _literalViaTaggedByIn = literalViaTaggedByIn

  def literalViaTaggedByIn: overflowdb.traversal.Traversal[Literal] = taggedByIn.collectAll[Literal]
  @deprecated("please use `localViaTaggedByIn`", "June 2024")
  def _localViaTaggedByIn = localViaTaggedByIn

  def localViaTaggedByIn: overflowdb.traversal.Traversal[Local] = taggedByIn.collectAll[Local]
  @deprecated("please use `memberViaTaggedByIn`", "June 2024")
  def _memberViaTaggedByIn = memberViaTaggedByIn

  def memberViaTaggedByIn: overflowdb.traversal.Traversal[Member] = taggedByIn.collectAll[Member]
  @deprecated("please use `methodViaTaggedByIn`", "June 2024")
  def _methodViaTaggedByIn = methodViaTaggedByIn

  def methodViaTaggedByIn: overflowdb.traversal.Traversal[Method] = taggedByIn.collectAll[Method]
  @deprecated("please use `methodParameterInViaTaggedByIn`", "June 2024")
  def _methodParameterInViaTaggedByIn = methodParameterInViaTaggedByIn

  def methodParameterInViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    taggedByIn.collectAll[MethodParameterIn]
  @deprecated("please use `methodParameterOutViaTaggedByIn`", "June 2024")
  def _methodParameterOutViaTaggedByIn = methodParameterOutViaTaggedByIn

  def methodParameterOutViaTaggedByIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    taggedByIn.collectAll[MethodParameterOut]
  @deprecated("please use `methodRefViaTaggedByIn`", "June 2024")
  def _methodRefViaTaggedByIn = methodRefViaTaggedByIn

  def methodRefViaTaggedByIn: overflowdb.traversal.Traversal[MethodRef] = taggedByIn.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaTaggedByIn`", "June 2024")
  def _methodReturnViaTaggedByIn = methodReturnViaTaggedByIn

  def methodReturnViaTaggedByIn: overflowdb.traversal.Traversal[MethodReturn] = taggedByIn.collectAll[MethodReturn]
  @deprecated("please use `returnViaTaggedByIn`", "June 2024")
  def _returnViaTaggedByIn = returnViaTaggedByIn

  def returnViaTaggedByIn: overflowdb.traversal.Traversal[Return] = taggedByIn.collectAll[Return]
  @deprecated("please use `tagViaTaggedByIn`", "June 2024")
  def _tagViaTaggedByIn = tagViaTaggedByIn

  def tagViaTaggedByIn: overflowdb.traversal.Traversal[Tag] = taggedByIn.collectAll[Tag]
  @deprecated("please use `templateDomViaTaggedByIn`", "June 2024")
  def _templateDomViaTaggedByIn = templateDomViaTaggedByIn

  def templateDomViaTaggedByIn: overflowdb.traversal.Traversal[TemplateDom] = taggedByIn.collectAll[TemplateDom]
  @deprecated("please use `typeDeclViaTaggedByIn`", "June 2024")
  def _typeDeclViaTaggedByIn = typeDeclViaTaggedByIn

  def typeDeclViaTaggedByIn: overflowdb.traversal.Traversal[TypeDecl] = taggedByIn.collectAll[TypeDecl]
  @deprecated("please use `typeRefViaTaggedByIn`", "June 2024")
  def _typeRefViaTaggedByIn = typeRefViaTaggedByIn

  def typeRefViaTaggedByIn: overflowdb.traversal.Traversal[TypeRef] = taggedByIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaTaggedByIn`", "June 2024")
  def _unknownViaTaggedByIn = unknownViaTaggedByIn

  def unknownViaTaggedByIn: overflowdb.traversal.Traversal[Unknown] = taggedByIn.collectAll[Unknown]

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
