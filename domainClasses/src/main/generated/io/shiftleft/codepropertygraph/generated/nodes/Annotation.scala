package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Annotation {
  def apply(graph: Graph, id: Long) = new Annotation(graph, id)

  val Label = "ANNOTATION"

  object PropertyNames {
    val ArgumentIndex    = "ARGUMENT_INDEX"
    val ArgumentName     = "ARGUMENT_NAME"
    val Code             = "CODE"
    val ColumnNumber     = "COLUMN_NUMBER"
    val FullName         = "FULL_NAME"
    val LineNumber       = "LINE_NUMBER"
    val Name             = "NAME"
    val Order            = "ORDER"
    val all: Set[String] = Set(ArgumentIndex, ArgumentName, Code, ColumnNumber, FullName, LineNumber, Name, Order)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val ArgumentIndex = new overflowdb.PropertyKey[scala.Int]("ARGUMENT_INDEX")
    val ArgumentName  = new overflowdb.PropertyKey[String]("ARGUMENT_NAME")
    val Code          = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber  = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val FullName      = new overflowdb.PropertyKey[String]("FULL_NAME")
    val LineNumber    = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val Name          = new overflowdb.PropertyKey[String]("NAME")
    val Order         = new overflowdb.PropertyKey[scala.Int]("ORDER")

  }

  object PropertyDefaults {
    val ArgumentIndex = -1: Int
    val Code          = "<empty>"
    val FullName      = "<empty>"
    val Name          = "<empty>"
    val Order         = -1: Int
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Argument.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("ARGUMENT", "AST")
    val In: Array[String]  = Array("AST", "CFG", "REACHING_DEF")
  }

  val factory = new NodeFactory[AnnotationDb] {
    override val forLabel = Annotation.Label

    override def createNode(ref: NodeRef[AnnotationDb]) =
      new AnnotationDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Annotation(graph, id)
  }
}

trait AnnotationBase extends AbstractNode with ExpressionBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def argumentIndex: scala.Int
  def argumentName: Option[String]
  def code: String
  def columnNumber: Option[scala.Int]
  def fullName: String
  def lineNumber: Option[scala.Int]
  def name: String
  def order: scala.Int

}

class Annotation(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[AnnotationDb](graph_4762, id_4762)
    with AnnotationBase
    with StoredNode
    with Expression {
  override def argumentIndex: scala.Int        = get().argumentIndex
  override def argumentName: Option[String]    = get().argumentName
  override def code: String                    = get().code
  override def columnNumber: Option[scala.Int] = get().columnNumber
  override def fullName: String                = get().fullName
  override def lineNumber: Option[scala.Int]   = get().lineNumber
  override def name: String                    = get().name
  override def order: scala.Int                = get().order
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "ARGUMENT_INDEX" => Annotation.PropertyDefaults.ArgumentIndex
      case "CODE"           => Annotation.PropertyDefaults.Code
      case "FULL_NAME"      => Annotation.PropertyDefaults.FullName
      case "NAME"           => Annotation.PropertyDefaults.Name
      case "ORDER"          => Annotation.PropertyDefaults.Order
      case _                => super.propertyDefaultValue(propertyKey)
    }
  }

  def argumentOut: Iterator[TemplateDom] = get().argumentOut
  override def _argumentOut              = get()._argumentOut

  def astOut: Iterator[AnnotationParameterAssign] = get().astOut
  override def _astOut                            = get()._astOut

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST OUT edge.
    */
  def annotationParameterAssignViaAstOut: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    get().annotationParameterAssignViaAstOut

  @deprecated("please use `annotationParameterAssignViaAstOut`", "June 2024")
  def _annotationParameterAssignViaAstOut = annotationParameterAssignViaAstOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to ANNOTATION_PARAMETER_ASSIGN via AST IN edge.
    */
  def annotationParameterAssignViaAstIn: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    get().annotationParameterAssignViaAstIn

  @deprecated("please use `annotationParameterAssignViaAstIn`", "June 2024")
  def _annotationParameterAssignViaAstIn = annotationParameterAssignViaAstIn

  /** Traverse to IDENTIFIER via AST IN edge.
    */
  def identifierViaAstIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaAstIn

  @deprecated("please use `identifierViaAstIn`", "June 2024")
  def _identifierViaAstIn = identifierViaAstIn

  /** Traverse to LITERAL via AST IN edge.
    */
  def literalViaAstIn: overflowdb.traversal.Traversal[Literal] = get().literalViaAstIn

  @deprecated("please use `literalViaAstIn`", "June 2024")
  def _literalViaAstIn = literalViaAstIn

  /** Traverse to MEMBER via AST IN edge.
    */
  def memberViaAstIn: overflowdb.traversal.Traversal[Member] = get().memberViaAstIn

  @deprecated("please use `memberViaAstIn`", "June 2024")
  def _memberViaAstIn = memberViaAstIn

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: overflowdb.traversal.Traversal[Method] = get().methodViaAstIn

  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

  /** Traverse to METHOD_PARAMETER_IN via AST IN edge.
    */
  def methodParameterInViaAstIn: overflowdb.traversal.Traversal[MethodParameterIn] = get().methodParameterInViaAstIn

  @deprecated("please use `methodParameterInViaAstIn`", "June 2024")
  def _methodParameterInViaAstIn = methodParameterInViaAstIn

  /** Traverse to METHOD_REF via AST IN edge.
    */
  def methodRefViaAstIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaAstIn

  @deprecated("please use `methodRefViaAstIn`", "June 2024")
  def _methodRefViaAstIn = methodRefViaAstIn

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: overflowdb.traversal.Traversal[TypeDecl] = get().typeDeclViaAstIn

  @deprecated("please use `typeDeclViaAstIn`", "June 2024")
  def _typeDeclViaAstIn = typeDeclViaAstIn

  /** Traverse to UNKNOWN via AST IN edge.
    */
  def unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaAstIn

  @deprecated("please use `unknownViaAstIn`", "June 2024")
  def _unknownViaAstIn = unknownViaAstIn

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
    Annotation.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "fullName"
      case 6 => "lineNumber"
      case 7 => "name"
      case 8 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => fullName
      case 6 => lineNumber
      case 7 => name
      case 8 => order
    }

  override def productPrefix = "Annotation"
  override def productArity  = 9
}

class AnnotationDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with Expression with AnnotationBase {

  override def layoutInformation: NodeLayoutInformation = Annotation.layoutInformation

  private var _argumentIndex: Integer = Annotation.PropertyDefaults.ArgumentIndex
  def argumentIndex: scala.Int        = _argumentIndex
  private var _argumentName: String   = null
  def argumentName: Option[String]    = Option(_argumentName).asInstanceOf[Option[String]]
  private var _code: String           = Annotation.PropertyDefaults.Code
  def code: String                    = _code
  private var _columnNumber: Integer  = null
  def columnNumber: Option[scala.Int] = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _fullName: String       = Annotation.PropertyDefaults.FullName
  def fullName: String                = _fullName
  private var _lineNumber: Integer    = null
  def lineNumber: Option[scala.Int]   = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _name: String           = Annotation.PropertyDefaults.Name
  def name: String                    = _name
  private var _order: Integer         = Annotation.PropertyDefaults.Order
  def order: scala.Int                = _order

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("ARGUMENT_INDEX", argumentIndex)
    argumentName.map { value => properties.put("ARGUMENT_NAME", value) }
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    properties.put("FULL_NAME", fullName)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    properties.put("NAME", name)
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
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }

    properties
  }

  import overflowdb.traversal._
  def argumentOut: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](0)
  override def _argumentOut              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  def astOut: Iterator[AnnotationParameterAssign] =
    createAdjacentNodeScalaIteratorByOffSet[AnnotationParameterAssign](1)
  override def _astOut = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `annotationParameterAssignViaAstOut`", "June 2024")
  def _annotationParameterAssignViaAstOut = annotationParameterAssignViaAstOut

  def annotationParameterAssignViaAstOut: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    astOut.collectAll[AnnotationParameterAssign]

  def astIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](2)
  override def _astIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `annotationParameterAssignViaAstIn`", "June 2024")
  def _annotationParameterAssignViaAstIn = annotationParameterAssignViaAstIn

  def annotationParameterAssignViaAstIn: overflowdb.traversal.Traversal[AnnotationParameterAssign] =
    astIn.collectAll[AnnotationParameterAssign]
  @deprecated("please use `identifierViaAstIn`", "June 2024")
  def _identifierViaAstIn = identifierViaAstIn

  def identifierViaAstIn: overflowdb.traversal.Traversal[Identifier] = astIn.collectAll[Identifier]
  @deprecated("please use `literalViaAstIn`", "June 2024")
  def _literalViaAstIn = literalViaAstIn

  def literalViaAstIn: overflowdb.traversal.Traversal[Literal] = astIn.collectAll[Literal]
  @deprecated("please use `memberViaAstIn`", "June 2024")
  def _memberViaAstIn = memberViaAstIn

  def memberViaAstIn: overflowdb.traversal.Traversal[Member] = astIn.collectAll[Member]
  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

  def methodViaAstIn: overflowdb.traversal.Traversal[Method] = astIn.collectAll[Method]
  @deprecated("please use `methodParameterInViaAstIn`", "June 2024")
  def _methodParameterInViaAstIn = methodParameterInViaAstIn

  def methodParameterInViaAstIn: overflowdb.traversal.Traversal[MethodParameterIn] = astIn.collectAll[MethodParameterIn]
  @deprecated("please use `methodRefViaAstIn`", "June 2024")
  def _methodRefViaAstIn = methodRefViaAstIn

  def methodRefViaAstIn: overflowdb.traversal.Traversal[MethodRef] = astIn.collectAll[MethodRef]
  @deprecated("please use `typeDeclViaAstIn`", "June 2024")
  def _typeDeclViaAstIn = typeDeclViaAstIn

  def typeDeclViaAstIn: overflowdb.traversal.Traversal[TypeDecl] = astIn.collectAll[TypeDecl]
  @deprecated("please use `unknownViaAstIn`", "June 2024")
  def _unknownViaAstIn = unknownViaAstIn

  def unknownViaAstIn: overflowdb.traversal.Traversal[Unknown] = astIn.collectAll[Unknown]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  def reachingDefIn: Iterator[TemplateDom] = createAdjacentNodeScalaIteratorByOffSet[TemplateDom](4)
  override def _reachingDefIn              = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  override def label: String = {
    Annotation.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "argumentIndex"
      case 2 => "argumentName"
      case 3 => "code"
      case 4 => "columnNumber"
      case 5 => "fullName"
      case 6 => "lineNumber"
      case 7 => "name"
      case 8 => "order"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => argumentIndex
      case 2 => argumentName
      case 3 => code
      case 4 => columnNumber
      case 5 => fullName
      case 6 => lineNumber
      case 7 => name
      case 8 => order
    }

  override def productPrefix = "Annotation"
  override def productArity  = 9

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[AnnotationDb]

  override def property(key: String): Any = {
    key match {
      case "ARGUMENT_INDEX" => this._argumentIndex
      case "ARGUMENT_NAME"  => this._argumentName
      case "CODE"           => this._code
      case "COLUMN_NUMBER"  => this._columnNumber
      case "FULL_NAME"      => this._fullName
      case "LINE_NUMBER"    => this._lineNumber
      case "NAME"           => this._name
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
      case "FULL_NAME"      => this._fullName = value.asInstanceOf[String]
      case "LINE_NUMBER"    => this._lineNumber = value.asInstanceOf[scala.Int]
      case "NAME"           => this._name = value.asInstanceOf[String]
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
    this._argumentIndex = newNode.asInstanceOf[NewAnnotation].argumentIndex
    this._argumentName = newNode.asInstanceOf[NewAnnotation].argumentName match {
      case None => null; case Some(value) => value
    }
    this._code = newNode.asInstanceOf[NewAnnotation].code
    this._columnNumber = newNode.asInstanceOf[NewAnnotation].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._fullName = newNode.asInstanceOf[NewAnnotation].fullName
    this._lineNumber = newNode.asInstanceOf[NewAnnotation].lineNumber match {
      case None => null; case Some(value) => value
    }
    this._name = newNode.asInstanceOf[NewAnnotation].name
    this._order = newNode.asInstanceOf[NewAnnotation].order

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewAnnotation].fullName, this.ref)
  }

}
