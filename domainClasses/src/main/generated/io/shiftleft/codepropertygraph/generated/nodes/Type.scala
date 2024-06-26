package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Type {
  def apply(graph: Graph, id: Long) = new Type(graph, id)

  val Label = "TYPE"

  object PropertyNames {
    val FullName                         = "FULL_NAME"
    val Name                             = "NAME"
    val TypeDeclFullName                 = "TYPE_DECL_FULL_NAME"
    val all: Set[String]                 = Set(FullName, Name, TypeDeclFullName)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val FullName         = new overflowdb.PropertyKey[String]("FULL_NAME")
    val Name             = new overflowdb.PropertyKey[String]("NAME")
    val TypeDeclFullName = new overflowdb.PropertyKey[String]("TYPE_DECL_FULL_NAME")

  }

  object PropertyDefaults {
    val FullName         = "<empty>"
    val Name             = "<empty>"
    val TypeDeclFullName = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.AliasOf.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.EvalType.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.InheritsFrom.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "REF")
    val In: Array[String]  = Array("ALIAS_OF", "EVAL_TYPE", "INHERITS_FROM", "REF")
  }

  val factory = new NodeFactory[TypeDb] {
    override val forLabel = Type.Label

    override def createNode(ref: NodeRef[TypeDb]) =
      new TypeDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Type(graph, id)
  }
}

trait TypeBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def fullName: String
  def name: String
  def typeDeclFullName: String

}

class Type(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[TypeDb](graph_4762, id_4762)
    with TypeBase
    with StoredNode {
  override def fullName: String         = get().fullName
  override def name: String             = get().name
  override def typeDeclFullName: String = get().typeDeclFullName
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "FULL_NAME"           => Type.PropertyDefaults.FullName
      case "NAME"                => Type.PropertyDefaults.Name
      case "TYPE_DECL_FULL_NAME" => Type.PropertyDefaults.TypeDeclFullName
      case _                     => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[TypeArgument] = get().astOut
  override def _astOut               = get()._astOut

  /** Traverse to TYPE_ARGUMENT via AST OUT edge.
    */
  def _typeArgumentViaAstOut: overflowdb.traversal.Traversal[TypeArgument] = get()._typeArgumentViaAstOut

  @deprecated("please use `_typeArgumentViaAstOut`", "June 2024")
  def __typeArgumentViaAstOut = _typeArgumentViaAstOut

  def refOut: Iterator[TypeDecl] = get().refOut
  override def _refOut           = get()._refOut

  /** Type declaration which is referenced by this type. Traverse to TYPE_DECL via REF OUT edge.
    */
  /** Type declaration which is referenced by this type. */
  @overflowdb.traversal.help.Doc(info = """Type declaration which is referenced by this type.""")
  def referencedTypeDecl: overflowdb.traversal.Traversal[TypeDecl] = get().referencedTypeDecl

  @deprecated("please use `referencedTypeDecl`", "June 2024")
  def _referencedTypeDecl = referencedTypeDecl

  def aliasOfIn: Iterator[TypeDecl] = get().aliasOfIn
  override def _aliasOfIn           = get()._aliasOfIn

  /** Direct alias type declarations. Traverse to TYPE_DECL via ALIAS_OF IN edge.
    */
  /** Direct alias type declarations. */
  @overflowdb.traversal.help.Doc(info = """Direct alias type declarations.""")
  def aliasTypeDecl: overflowdb.traversal.Traversal[TypeDecl] = get().aliasTypeDecl

  @deprecated("please use `aliasTypeDecl`", "June 2024")
  def _aliasTypeDecl = aliasTypeDecl

  def evalTypeIn: Iterator[AstNode] = get().evalTypeIn
  override def _evalTypeIn          = get()._evalTypeIn

  /** Traverse to ARRAY_INITIALIZER via EVAL_TYPE IN edge.
    */
  def _arrayInitializerViaEvalTypeIn: overflowdb.traversal.Traversal[ArrayInitializer] =
    get()._arrayInitializerViaEvalTypeIn

  @deprecated("please use `_arrayInitializerViaEvalTypeIn`", "June 2024")
  def __arrayInitializerViaEvalTypeIn = _arrayInitializerViaEvalTypeIn

  /** Traverse to BLOCK via EVAL_TYPE IN edge.
    */
  def _blockViaEvalTypeIn: overflowdb.traversal.Traversal[Block] = get()._blockViaEvalTypeIn

  @deprecated("please use `_blockViaEvalTypeIn`", "June 2024")
  def __blockViaEvalTypeIn = _blockViaEvalTypeIn

  /** Traverse to CALL via EVAL_TYPE IN edge.
    */
  def _callViaEvalTypeIn: overflowdb.traversal.Traversal[Call] = get()._callViaEvalTypeIn

  @deprecated("please use `_callViaEvalTypeIn`", "June 2024")
  def __callViaEvalTypeIn = _callViaEvalTypeIn

  /** Traverse to CONTROL_STRUCTURE via EVAL_TYPE IN edge.
    */
  def _controlStructureViaEvalTypeIn: overflowdb.traversal.Traversal[ControlStructure] =
    get()._controlStructureViaEvalTypeIn

  @deprecated("please use `_controlStructureViaEvalTypeIn`", "June 2024")
  def __controlStructureViaEvalTypeIn = _controlStructureViaEvalTypeIn

  /** Traverse to IDENTIFIER via EVAL_TYPE IN edge.
    */
  def _identifierViaEvalTypeIn: overflowdb.traversal.Traversal[Identifier] = get()._identifierViaEvalTypeIn

  @deprecated("please use `_identifierViaEvalTypeIn`", "June 2024")
  def __identifierViaEvalTypeIn = _identifierViaEvalTypeIn

  /** Traverse to LITERAL via EVAL_TYPE IN edge.
    */
  def _literalViaEvalTypeIn: overflowdb.traversal.Traversal[Literal] = get()._literalViaEvalTypeIn

  @deprecated("please use `_literalViaEvalTypeIn`", "June 2024")
  def __literalViaEvalTypeIn = _literalViaEvalTypeIn

  /** Traverse to LOCAL via EVAL_TYPE IN edge.
    */
  def _localViaEvalTypeIn: overflowdb.traversal.Traversal[Local] = get()._localViaEvalTypeIn

  @deprecated("please use `_localViaEvalTypeIn`", "June 2024")
  def __localViaEvalTypeIn = _localViaEvalTypeIn

  /** Traverse to MEMBER via EVAL_TYPE IN edge.
    */
  def _memberViaEvalTypeIn: overflowdb.traversal.Traversal[Member] = get()._memberViaEvalTypeIn

  @deprecated("please use `_memberViaEvalTypeIn`", "June 2024")
  def __memberViaEvalTypeIn = _memberViaEvalTypeIn

  /** Traverse to METHOD_PARAMETER_IN via EVAL_TYPE IN edge.
    */
  def _methodParameterInViaEvalTypeIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    get()._methodParameterInViaEvalTypeIn

  @deprecated("please use `_methodParameterInViaEvalTypeIn`", "June 2024")
  def __methodParameterInViaEvalTypeIn = _methodParameterInViaEvalTypeIn

  /** Traverse to METHOD_PARAMETER_OUT via EVAL_TYPE IN edge.
    */
  def _methodParameterOutViaEvalTypeIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    get()._methodParameterOutViaEvalTypeIn

  @deprecated("please use `_methodParameterOutViaEvalTypeIn`", "June 2024")
  def __methodParameterOutViaEvalTypeIn = _methodParameterOutViaEvalTypeIn

  /** Traverse to METHOD_REF via EVAL_TYPE IN edge.
    */
  def _methodRefViaEvalTypeIn: overflowdb.traversal.Traversal[MethodRef] = get()._methodRefViaEvalTypeIn

  @deprecated("please use `_methodRefViaEvalTypeIn`", "June 2024")
  def __methodRefViaEvalTypeIn = _methodRefViaEvalTypeIn

  /** Traverse to METHOD_RETURN via EVAL_TYPE IN edge.
    */
  def _methodReturnViaEvalTypeIn: overflowdb.traversal.Traversal[MethodReturn] = get()._methodReturnViaEvalTypeIn

  @deprecated("please use `_methodReturnViaEvalTypeIn`", "June 2024")
  def __methodReturnViaEvalTypeIn = _methodReturnViaEvalTypeIn

  /** Traverse to TYPE_REF via EVAL_TYPE IN edge.
    */
  def _typeRefViaEvalTypeIn: overflowdb.traversal.Traversal[TypeRef] = get()._typeRefViaEvalTypeIn

  @deprecated("please use `_typeRefViaEvalTypeIn`", "June 2024")
  def __typeRefViaEvalTypeIn = _typeRefViaEvalTypeIn

  /** Traverse to UNKNOWN via EVAL_TYPE IN edge.
    */
  def _unknownViaEvalTypeIn: overflowdb.traversal.Traversal[Unknown] = get()._unknownViaEvalTypeIn

  @deprecated("please use `_unknownViaEvalTypeIn`", "June 2024")
  def __unknownViaEvalTypeIn = _unknownViaEvalTypeIn

  def inheritsFromIn: Iterator[TypeDecl] = get().inheritsFromIn
  override def _inheritsFromIn           = get()._inheritsFromIn

  /** Traverse to TYPE_DECL via INHERITS_FROM IN edge.
    */
  def _typeDeclViaInheritsFromIn: overflowdb.traversal.Traversal[TypeDecl] = get()._typeDeclViaInheritsFromIn

  @deprecated("please use `_typeDeclViaInheritsFromIn`", "June 2024")
  def __typeDeclViaInheritsFromIn = _typeDeclViaInheritsFromIn

  def refIn: Iterator[TypeArgument] = get().refIn
  override def _refIn               = get()._refIn

  /** Traverse to TYPE_ARGUMENT via REF IN edge.
    */
  def _typeArgumentViaRefIn: overflowdb.traversal.Traversal[TypeArgument] = get()._typeArgumentViaRefIn

  @deprecated("please use `_typeArgumentViaRefIn`", "June 2024")
  def __typeArgumentViaRefIn = _typeArgumentViaRefIn

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
    Type.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "fullName"
      case 2 => "name"
      case 3 => "typeDeclFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => fullName
      case 2 => name
      case 3 => typeDeclFullName
    }

  override def productPrefix = "Type"
  override def productArity  = 4
}

class TypeDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with TypeBase {

  override def layoutInformation: NodeLayoutInformation = Type.layoutInformation

  private var _fullName: String         = Type.PropertyDefaults.FullName
  def fullName: String                  = _fullName
  private var _name: String             = Type.PropertyDefaults.Name
  def name: String                      = _name
  private var _typeDeclFullName: String = Type.PropertyDefaults.TypeDeclFullName
  def typeDeclFullName: String          = _typeDeclFullName

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("FULL_NAME", fullName)
    properties.put("NAME", name)
    properties.put("TYPE_DECL_FULL_NAME", typeDeclFullName)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!(("<empty>") == typeDeclFullName)) { properties.put("TYPE_DECL_FULL_NAME", typeDeclFullName) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[TypeArgument] = createAdjacentNodeScalaIteratorByOffSet[TypeArgument](0)
  override def _astOut               = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  @deprecated("please use `_typeArgumentViaAstOut`", "June 2024")
  def __typeArgumentViaAstOut = _typeArgumentViaAstOut

  def _typeArgumentViaAstOut: overflowdb.traversal.Traversal[TypeArgument] = astOut.collectAll[TypeArgument]

  def refOut: Iterator[TypeDecl] = createAdjacentNodeScalaIteratorByOffSet[TypeDecl](1)
  override def _refOut           = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `referencedTypeDecl`", "June 2024")
  def _referencedTypeDecl = referencedTypeDecl

  def referencedTypeDecl: overflowdb.traversal.Traversal[TypeDecl] = refOut.collectAll[TypeDecl]

  def aliasOfIn: Iterator[TypeDecl] = createAdjacentNodeScalaIteratorByOffSet[TypeDecl](2)
  override def _aliasOfIn           = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `aliasTypeDecl`", "June 2024")
  def _aliasTypeDecl = aliasTypeDecl

  def aliasTypeDecl: overflowdb.traversal.Traversal[TypeDecl] = aliasOfIn.collectAll[TypeDecl]

  def evalTypeIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](3)
  override def _evalTypeIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `_arrayInitializerViaEvalTypeIn`", "June 2024")
  def __arrayInitializerViaEvalTypeIn = _arrayInitializerViaEvalTypeIn

  def _arrayInitializerViaEvalTypeIn: overflowdb.traversal.Traversal[ArrayInitializer] =
    evalTypeIn.collectAll[ArrayInitializer]
  @deprecated("please use `_blockViaEvalTypeIn`", "June 2024")
  def __blockViaEvalTypeIn = _blockViaEvalTypeIn

  def _blockViaEvalTypeIn: overflowdb.traversal.Traversal[Block] = evalTypeIn.collectAll[Block]
  @deprecated("please use `_callViaEvalTypeIn`", "June 2024")
  def __callViaEvalTypeIn = _callViaEvalTypeIn

  def _callViaEvalTypeIn: overflowdb.traversal.Traversal[Call] = evalTypeIn.collectAll[Call]
  @deprecated("please use `_controlStructureViaEvalTypeIn`", "June 2024")
  def __controlStructureViaEvalTypeIn = _controlStructureViaEvalTypeIn

  def _controlStructureViaEvalTypeIn: overflowdb.traversal.Traversal[ControlStructure] =
    evalTypeIn.collectAll[ControlStructure]
  @deprecated("please use `_identifierViaEvalTypeIn`", "June 2024")
  def __identifierViaEvalTypeIn = _identifierViaEvalTypeIn

  def _identifierViaEvalTypeIn: overflowdb.traversal.Traversal[Identifier] = evalTypeIn.collectAll[Identifier]
  @deprecated("please use `_literalViaEvalTypeIn`", "June 2024")
  def __literalViaEvalTypeIn = _literalViaEvalTypeIn

  def _literalViaEvalTypeIn: overflowdb.traversal.Traversal[Literal] = evalTypeIn.collectAll[Literal]
  @deprecated("please use `_localViaEvalTypeIn`", "June 2024")
  def __localViaEvalTypeIn = _localViaEvalTypeIn

  def _localViaEvalTypeIn: overflowdb.traversal.Traversal[Local] = evalTypeIn.collectAll[Local]
  @deprecated("please use `_memberViaEvalTypeIn`", "June 2024")
  def __memberViaEvalTypeIn = _memberViaEvalTypeIn

  def _memberViaEvalTypeIn: overflowdb.traversal.Traversal[Member] = evalTypeIn.collectAll[Member]
  @deprecated("please use `_methodParameterInViaEvalTypeIn`", "June 2024")
  def __methodParameterInViaEvalTypeIn = _methodParameterInViaEvalTypeIn

  def _methodParameterInViaEvalTypeIn: overflowdb.traversal.Traversal[MethodParameterIn] =
    evalTypeIn.collectAll[MethodParameterIn]
  @deprecated("please use `_methodParameterOutViaEvalTypeIn`", "June 2024")
  def __methodParameterOutViaEvalTypeIn = _methodParameterOutViaEvalTypeIn

  def _methodParameterOutViaEvalTypeIn: overflowdb.traversal.Traversal[MethodParameterOut] =
    evalTypeIn.collectAll[MethodParameterOut]
  @deprecated("please use `_methodRefViaEvalTypeIn`", "June 2024")
  def __methodRefViaEvalTypeIn = _methodRefViaEvalTypeIn

  def _methodRefViaEvalTypeIn: overflowdb.traversal.Traversal[MethodRef] = evalTypeIn.collectAll[MethodRef]
  @deprecated("please use `_methodReturnViaEvalTypeIn`", "June 2024")
  def __methodReturnViaEvalTypeIn = _methodReturnViaEvalTypeIn

  def _methodReturnViaEvalTypeIn: overflowdb.traversal.Traversal[MethodReturn] = evalTypeIn.collectAll[MethodReturn]
  @deprecated("please use `_typeRefViaEvalTypeIn`", "June 2024")
  def __typeRefViaEvalTypeIn = _typeRefViaEvalTypeIn

  def _typeRefViaEvalTypeIn: overflowdb.traversal.Traversal[TypeRef] = evalTypeIn.collectAll[TypeRef]
  @deprecated("please use `_unknownViaEvalTypeIn`", "June 2024")
  def __unknownViaEvalTypeIn = _unknownViaEvalTypeIn

  def _unknownViaEvalTypeIn: overflowdb.traversal.Traversal[Unknown] = evalTypeIn.collectAll[Unknown]

  def inheritsFromIn: Iterator[TypeDecl] = createAdjacentNodeScalaIteratorByOffSet[TypeDecl](4)
  override def _inheritsFromIn           = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  @deprecated("please use `_typeDeclViaInheritsFromIn`", "June 2024")
  def __typeDeclViaInheritsFromIn = _typeDeclViaInheritsFromIn

  def _typeDeclViaInheritsFromIn: overflowdb.traversal.Traversal[TypeDecl] = inheritsFromIn.collectAll[TypeDecl]

  def refIn: Iterator[TypeArgument] = createAdjacentNodeScalaIteratorByOffSet[TypeArgument](5)
  override def _refIn               = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  @deprecated("please use `_typeArgumentViaRefIn`", "June 2024")
  def __typeArgumentViaRefIn = _typeArgumentViaRefIn

  def _typeArgumentViaRefIn: overflowdb.traversal.Traversal[TypeArgument] = refIn.collectAll[TypeArgument]

  override def label: String = {
    Type.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "fullName"
      case 2 => "name"
      case 3 => "typeDeclFullName"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => fullName
      case 2 => name
      case 3 => typeDeclFullName
    }

  override def productPrefix = "Type"
  override def productArity  = 4

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeDb]

  override def property(key: String): Any = {
    key match {
      case "FULL_NAME"           => this._fullName
      case "NAME"                => this._name
      case "TYPE_DECL_FULL_NAME" => this._typeDeclFullName

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "FULL_NAME"           => this._fullName = value.asInstanceOf[String]
      case "NAME"                => this._name = value.asInstanceOf[String]
      case "TYPE_DECL_FULL_NAME" => this._typeDeclFullName = value.asInstanceOf[String]

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
    this._fullName = newNode.asInstanceOf[NewType].fullName
    this._name = newNode.asInstanceOf[NewType].name
    this._typeDeclFullName = newNode.asInstanceOf[NewType].typeDeclFullName

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewType].fullName, this.ref)
  }

}
