package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object Method {
  def apply(graph: Graph, id: Long) = new Method(graph, id)

  val Label = "METHOD"

  object PropertyNames {
    val AstParentFullName = "AST_PARENT_FULL_NAME"
    val AstParentType     = "AST_PARENT_TYPE"
    val Code              = "CODE"
    val ColumnNumber      = "COLUMN_NUMBER"
    val ColumnNumberEnd   = "COLUMN_NUMBER_END"
    val Filename          = "FILENAME"
    val FullName          = "FULL_NAME"
    val Hash              = "HASH"
    val IsExternal        = "IS_EXTERNAL"
    val LineNumber        = "LINE_NUMBER"
    val LineNumberEnd     = "LINE_NUMBER_END"
    val Name              = "NAME"
    val Offset            = "OFFSET"
    val OffsetEnd         = "OFFSET_END"
    val Order             = "ORDER"
    val Signature         = "SIGNATURE"
    val all: Set[String] = Set(
      AstParentFullName,
      AstParentType,
      Code,
      ColumnNumber,
      ColumnNumberEnd,
      Filename,
      FullName,
      Hash,
      IsExternal,
      LineNumber,
      LineNumberEnd,
      Name,
      Offset,
      OffsetEnd,
      Order,
      Signature
    )
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val AstParentFullName = new overflowdb.PropertyKey[String]("AST_PARENT_FULL_NAME")
    val AstParentType     = new overflowdb.PropertyKey[String]("AST_PARENT_TYPE")
    val Code              = new overflowdb.PropertyKey[String]("CODE")
    val ColumnNumber      = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER")
    val ColumnNumberEnd   = new overflowdb.PropertyKey[scala.Int]("COLUMN_NUMBER_END")
    val Filename          = new overflowdb.PropertyKey[String]("FILENAME")
    val FullName          = new overflowdb.PropertyKey[String]("FULL_NAME")
    val Hash              = new overflowdb.PropertyKey[String]("HASH")
    val IsExternal        = new overflowdb.PropertyKey[Boolean]("IS_EXTERNAL")
    val LineNumber        = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER")
    val LineNumberEnd     = new overflowdb.PropertyKey[scala.Int]("LINE_NUMBER_END")
    val Name              = new overflowdb.PropertyKey[String]("NAME")
    val Offset            = new overflowdb.PropertyKey[scala.Int]("OFFSET")
    val OffsetEnd         = new overflowdb.PropertyKey[scala.Int]("OFFSET_END")
    val Order             = new overflowdb.PropertyKey[scala.Int]("ORDER")
    val Signature         = new overflowdb.PropertyKey[String]("SIGNATURE")

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
    val Signature         = ""
  }

  val layoutInformation = new NodeLayoutInformation(
    Label,
    PropertyNames.allAsJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Dominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.ReachingDef.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.SourceFile.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.TaggedBy.layoutInformation
    ).asJava,
    List(
      io.shiftleft.codepropertygraph.generated.edges.Ast.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Call.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Cfg.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Contains.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.PostDominate.layoutInformation,
      io.shiftleft.codepropertygraph.generated.edges.Ref.layoutInformation
    ).asJava
  )

  object Edges {
    val Out: Array[String] = Array("AST", "CFG", "CONTAINS", "DOMINATE", "REACHING_DEF", "SOURCE_FILE", "TAGGED_BY")
    val In: Array[String]  = Array("AST", "CALL", "CFG", "CONTAINS", "POST_DOMINATE", "REF")
  }

  val factory = new NodeFactory[MethodDb] {
    override val forLabel = Method.Label

    override def createNode(ref: NodeRef[MethodDb]) =
      new MethodDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = Method(graph, id)
  }
}

trait MethodBase extends AbstractNode with AstNodeBase with CfgNodeBase with DeclarationBase {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def astParentFullName: String
  def astParentType: String
  def code: String
  def columnNumber: Option[scala.Int]
  def columnNumberEnd: Option[scala.Int]
  def filename: String
  def fullName: String
  def hash: Option[String]
  def isExternal: Boolean
  def lineNumber: Option[scala.Int]
  def lineNumberEnd: Option[scala.Int]
  def name: String
  def offset: Option[scala.Int]
  def offsetEnd: Option[scala.Int]
  def order: scala.Int
  def signature: String

}

class Method(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[MethodDb](graph_4762, id_4762)
    with MethodBase
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration {
  override def astParentFullName: String          = get().astParentFullName
  override def astParentType: String              = get().astParentType
  override def code: String                       = get().code
  override def columnNumber: Option[scala.Int]    = get().columnNumber
  override def columnNumberEnd: Option[scala.Int] = get().columnNumberEnd
  override def filename: String                   = get().filename
  override def fullName: String                   = get().fullName
  override def hash: Option[String]               = get().hash
  override def isExternal: Boolean                = get().isExternal
  override def lineNumber: Option[scala.Int]      = get().lineNumber
  override def lineNumberEnd: Option[scala.Int]   = get().lineNumberEnd
  override def name: String                       = get().name
  override def offset: Option[scala.Int]          = get().offset
  override def offsetEnd: Option[scala.Int]       = get().offsetEnd
  override def order: scala.Int                   = get().order
  override def signature: String                  = get().signature
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "AST_PARENT_FULL_NAME" => Method.PropertyDefaults.AstParentFullName
      case "AST_PARENT_TYPE"      => Method.PropertyDefaults.AstParentType
      case "CODE"                 => Method.PropertyDefaults.Code
      case "FILENAME"             => Method.PropertyDefaults.Filename
      case "FULL_NAME"            => Method.PropertyDefaults.FullName
      case "IS_EXTERNAL"          => Method.PropertyDefaults.IsExternal
      case "NAME"                 => Method.PropertyDefaults.Name
      case "ORDER"                => Method.PropertyDefaults.Order
      case "SIGNATURE"            => Method.PropertyDefaults.Signature
      case _                      => super.propertyDefaultValue(propertyKey)
    }
  }

  def astOut: Iterator[AstNode] = get().astOut
  override def _astOut          = get()._astOut

  /** Traverse to ANNOTATION via AST OUT edge.
    */
  def annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = get().annotationViaAstOut

  @deprecated("please use `annotationViaAstOut`", "June 2024")
  def _annotationViaAstOut = annotationViaAstOut

  /** Root of the abstract syntax tree Traverse to BLOCK via AST OUT edge.
    */
  /** Root of the abstract syntax tree */
  @overflowdb.traversal.help.Doc(info = """Root of the abstract syntax tree""")
  def block: Block = get().block

  @deprecated("please use `block`", "June 2024")
  def _block = block

  /** Traverse to METHOD via AST OUT edge.
    */
  def methodViaAstOut: overflowdb.traversal.Traversal[Method] = get().methodViaAstOut

  @deprecated("please use `methodViaAstOut`", "June 2024")
  def _methodViaAstOut = methodViaAstOut

  /** Parameters of the method Traverse to METHOD_PARAMETER_IN via AST OUT edge.
    */
  /** Parameters of the method */
  @overflowdb.traversal.help.Doc(info = """Parameters of the method""")
  def parameter: overflowdb.traversal.Traversal[MethodParameterIn] = get().parameter

  @deprecated("please use `parameter`", "June 2024")
  def _parameter = parameter

  /** Traverse to METHOD_PARAMETER_OUT via AST OUT edge.
    */
  def methodParameterOutViaAstOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get().methodParameterOutViaAstOut

  @deprecated("please use `methodParameterOutViaAstOut`", "June 2024")
  def _methodParameterOutViaAstOut = methodParameterOutViaAstOut

  /** Formal return parameters Traverse to METHOD_RETURN via AST OUT edge.
    */
  /** Formal return parameters */
  @overflowdb.traversal.help.Doc(info = """Formal return parameters""")
  def methodReturn: MethodReturn = get().methodReturn

  @deprecated("please use `methodReturn`", "June 2024")
  def _methodReturn = methodReturn

  /** Traverse to MODIFIER via AST OUT edge.
    */
  def modifierViaAstOut: overflowdb.traversal.Traversal[Modifier] = get().modifierViaAstOut

  @deprecated("please use `modifierViaAstOut`", "June 2024")
  def _modifierViaAstOut = modifierViaAstOut

  /** Traverse to TYPE_DECL via AST OUT edge.
    */
  def typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = get().typeDeclViaAstOut

  @deprecated("please use `typeDeclViaAstOut`", "June 2024")
  def _typeDeclViaAstOut = typeDeclViaAstOut

  /** Traverse to TYPE_PARAMETER via AST OUT edge.
    */
  def typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = get().typeParameterViaAstOut

  @deprecated("please use `typeParameterViaAstOut`", "June 2024")
  def _typeParameterViaAstOut = typeParameterViaAstOut

  def cfgOut: Iterator[AstNode] = get().cfgOut
  override def _cfgOut          = get()._cfgOut

  /** First control flow graph node Traverse to CFG_NODE via CFG OUT edge.
    */
  /** First control flow graph node */
  @overflowdb.traversal.help.Doc(info = """First control flow graph node""")
  def cfgFirst: overflowdb.traversal.Traversal[CfgNode] = get().cfgFirst

  @deprecated("please use `cfgFirst`", "June 2024")
  def _cfgFirst = cfgFirst

  /** Traverse to METHOD_RETURN via CFG OUT edge.
    */
  def methodReturnViaCfgOut: Option[MethodReturn] = get().methodReturnViaCfgOut

  @deprecated("please use `methodReturnViaCfgOut`", "June 2024")
  def _methodReturnViaCfgOut = methodReturnViaCfgOut

  def containsOut: Iterator[CfgNode] = get().containsOut
  override def _containsOut          = get()._containsOut

  /** Traverse to BLOCK via CONTAINS OUT edge.
    */
  def blockViaContainsOut: overflowdb.traversal.Traversal[Block] = get().blockViaContainsOut

  @deprecated("please use `blockViaContainsOut`", "June 2024")
  def _blockViaContainsOut = blockViaContainsOut

  /** Traverse to CALL via CONTAINS OUT edge.
    */
  def callViaContainsOut: overflowdb.traversal.Traversal[Call] = get().callViaContainsOut

  @deprecated("please use `callViaContainsOut`", "June 2024")
  def _callViaContainsOut = callViaContainsOut

  /** Traverse to CONTROL_STRUCTURE via CONTAINS OUT edge.
    */
  def controlStructureViaContainsOut: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaContainsOut

  @deprecated("please use `controlStructureViaContainsOut`", "June 2024")
  def _controlStructureViaContainsOut = controlStructureViaContainsOut

  /** Traverse to FIELD_IDENTIFIER via CONTAINS OUT edge.
    */
  def fieldIdentifierViaContainsOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaContainsOut

  @deprecated("please use `fieldIdentifierViaContainsOut`", "June 2024")
  def _fieldIdentifierViaContainsOut = fieldIdentifierViaContainsOut

  /** Traverse to IDENTIFIER via CONTAINS OUT edge.
    */
  def identifierViaContainsOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaContainsOut

  @deprecated("please use `identifierViaContainsOut`", "June 2024")
  def _identifierViaContainsOut = identifierViaContainsOut

  /** Traverse to JUMP_TARGET via CONTAINS OUT edge.
    */
  def jumpTargetViaContainsOut: overflowdb.traversal.Traversal[JumpTarget] = get().jumpTargetViaContainsOut

  @deprecated("please use `jumpTargetViaContainsOut`", "June 2024")
  def _jumpTargetViaContainsOut = jumpTargetViaContainsOut

  /** Literals used in the method Traverse to LITERAL via CONTAINS OUT edge.
    */
  /** Literals used in the method */
  @overflowdb.traversal.help.Doc(info = """Literals used in the method""")
  def literal: overflowdb.traversal.Traversal[Literal] = get().literal

  @deprecated("please use `literal`", "June 2024")
  def _literal = literal

  /** Traverse to METHOD_REF via CONTAINS OUT edge.
    */
  def methodRefViaContainsOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaContainsOut

  @deprecated("please use `methodRefViaContainsOut`", "June 2024")
  def _methodRefViaContainsOut = methodRefViaContainsOut

  /** Traverse to RETURN via CONTAINS OUT edge.
    */
  def returnViaContainsOut: overflowdb.traversal.Traversal[Return] = get().returnViaContainsOut

  @deprecated("please use `returnViaContainsOut`", "June 2024")
  def _returnViaContainsOut = returnViaContainsOut

  /** Traverse to TEMPLATE_DOM via CONTAINS OUT edge.
    */
  def templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = get().templateDomViaContainsOut

  @deprecated("please use `templateDomViaContainsOut`", "June 2024")
  def _templateDomViaContainsOut = templateDomViaContainsOut

  /** Traverse to TYPE_REF via CONTAINS OUT edge.
    */
  def typeRefViaContainsOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaContainsOut

  @deprecated("please use `typeRefViaContainsOut`", "June 2024")
  def _typeRefViaContainsOut = typeRefViaContainsOut

  /** Traverse to UNKNOWN via CONTAINS OUT edge.
    */
  def unknownViaContainsOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaContainsOut

  @deprecated("please use `unknownViaContainsOut`", "June 2024")
  def _unknownViaContainsOut = unknownViaContainsOut

  def dominateOut: Iterator[CfgNode] = get().dominateOut
  override def _dominateOut          = get()._dominateOut

  /** Traverse to BLOCK via DOMINATE OUT edge.
    */
  def blockViaDominateOut: overflowdb.traversal.Traversal[Block] = get().blockViaDominateOut

  @deprecated("please use `blockViaDominateOut`", "June 2024")
  def _blockViaDominateOut = blockViaDominateOut

  /** Traverse to CALL via DOMINATE OUT edge.
    */
  def callViaDominateOut: overflowdb.traversal.Traversal[Call] = get().callViaDominateOut

  @deprecated("please use `callViaDominateOut`", "June 2024")
  def _callViaDominateOut = callViaDominateOut

  /** Traverse to FIELD_IDENTIFIER via DOMINATE OUT edge.
    */
  def fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaDominateOut

  @deprecated("please use `fieldIdentifierViaDominateOut`", "June 2024")
  def _fieldIdentifierViaDominateOut = fieldIdentifierViaDominateOut

  /** Traverse to IDENTIFIER via DOMINATE OUT edge.
    */
  def identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaDominateOut

  @deprecated("please use `identifierViaDominateOut`", "June 2024")
  def _identifierViaDominateOut = identifierViaDominateOut

  /** Traverse to LITERAL via DOMINATE OUT edge.
    */
  def literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = get().literalViaDominateOut

  @deprecated("please use `literalViaDominateOut`", "June 2024")
  def _literalViaDominateOut = literalViaDominateOut

  /** Traverse to METHOD_REF via DOMINATE OUT edge.
    */
  def methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaDominateOut

  @deprecated("please use `methodRefViaDominateOut`", "June 2024")
  def _methodRefViaDominateOut = methodRefViaDominateOut

  /** Traverse to METHOD_RETURN via DOMINATE OUT edge.
    */
  def methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaDominateOut

  @deprecated("please use `methodReturnViaDominateOut`", "June 2024")
  def _methodReturnViaDominateOut = methodReturnViaDominateOut

  /** Traverse to RETURN via DOMINATE OUT edge.
    */
  def returnViaDominateOut: overflowdb.traversal.Traversal[Return] = get().returnViaDominateOut

  @deprecated("please use `returnViaDominateOut`", "June 2024")
  def _returnViaDominateOut = returnViaDominateOut

  /** Traverse to TYPE_REF via DOMINATE OUT edge.
    */
  def typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaDominateOut

  @deprecated("please use `typeRefViaDominateOut`", "June 2024")
  def _typeRefViaDominateOut = typeRefViaDominateOut

  /** Traverse to UNKNOWN via DOMINATE OUT edge.
    */
  def unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = get().unknownViaDominateOut

  @deprecated("please use `unknownViaDominateOut`", "June 2024")
  def _unknownViaDominateOut = unknownViaDominateOut

  def reachingDefOut: Iterator[CfgNode] = get().reachingDefOut
  override def _reachingDefOut          = get()._reachingDefOut

  /** Traverse to CALL via REACHING_DEF OUT edge.
    */
  def callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = get().callViaReachingDefOut

  @deprecated("please use `callViaReachingDefOut`", "June 2024")
  def _callViaReachingDefOut = callViaReachingDefOut

  /** Traverse to IDENTIFIER via REACHING_DEF OUT edge.
    */
  def identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = get().identifierViaReachingDefOut

  @deprecated("please use `identifierViaReachingDefOut`", "June 2024")
  def _identifierViaReachingDefOut = identifierViaReachingDefOut

  /** Traverse to LITERAL via REACHING_DEF OUT edge.
    */
  def literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = get().literalViaReachingDefOut

  @deprecated("please use `literalViaReachingDefOut`", "June 2024")
  def _literalViaReachingDefOut = literalViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_IN via REACHING_DEF OUT edge.
    */
  def methodParameterInViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterIn] =
    get().methodParameterInViaReachingDefOut

  @deprecated("please use `methodParameterInViaReachingDefOut`", "June 2024")
  def _methodParameterInViaReachingDefOut = methodParameterInViaReachingDefOut

  /** Traverse to METHOD_PARAMETER_OUT via REACHING_DEF OUT edge.
    */
  def methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    get().methodParameterOutViaReachingDefOut

  @deprecated("please use `methodParameterOutViaReachingDefOut`", "June 2024")
  def _methodParameterOutViaReachingDefOut = methodParameterOutViaReachingDefOut

  /** Traverse to METHOD_REF via REACHING_DEF OUT edge.
    */
  def methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaReachingDefOut

  @deprecated("please use `methodRefViaReachingDefOut`", "June 2024")
  def _methodRefViaReachingDefOut = methodRefViaReachingDefOut

  /** Traverse to RETURN via REACHING_DEF OUT edge.
    */
  def returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = get().returnViaReachingDefOut

  @deprecated("please use `returnViaReachingDefOut`", "June 2024")
  def _returnViaReachingDefOut = returnViaReachingDefOut

  /** Traverse to TYPE_REF via REACHING_DEF OUT edge.
    */
  def typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaReachingDefOut

  @deprecated("please use `typeRefViaReachingDefOut`", "June 2024")
  def _typeRefViaReachingDefOut = typeRefViaReachingDefOut

  def sourceFileOut: Iterator[File] = get().sourceFileOut
  override def _sourceFileOut       = get()._sourceFileOut

  /** Traverse to FILE via SOURCE_FILE OUT edge.
    */
  def fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = get().fileViaSourceFileOut

  @deprecated("please use `fileViaSourceFileOut`", "June 2024")
  def _fileViaSourceFileOut = fileViaSourceFileOut

  def taggedByOut: Iterator[Tag] = get().taggedByOut
  override def _taggedByOut      = get()._taggedByOut

  /** Traverse to TAG via TAGGED_BY OUT edge.
    */
  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = get().tagViaTaggedByOut

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def astIn: Iterator[AstNode] = get().astIn
  override def _astIn          = get()._astIn

  /** Traverse to METHOD via AST IN edge.
    */
  def methodViaAstIn: Option[Method] = get().methodViaAstIn

  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

  /** Traverse to NAMESPACE_BLOCK via AST IN edge.
    */
  def namespaceBlockViaAstIn: Option[NamespaceBlock] = get().namespaceBlockViaAstIn

  @deprecated("please use `namespaceBlockViaAstIn`", "June 2024")
  def _namespaceBlockViaAstIn = namespaceBlockViaAstIn

  /** Traverse to TYPE_DECL via AST IN edge.
    */
  def typeDeclViaAstIn: Option[TypeDecl] = get().typeDeclViaAstIn

  @deprecated("please use `typeDeclViaAstIn`", "June 2024")
  def _typeDeclViaAstIn = typeDeclViaAstIn

  def callIn: Iterator[Call] = get().callIn
  override def _callIn       = get()._callIn

  /** Traverse to CALL via CALL IN edge.
    */
  def callViaCallIn: overflowdb.traversal.Traversal[Call] = get().callViaCallIn

  @deprecated("please use `callViaCallIn`", "June 2024")
  def _callViaCallIn = callViaCallIn

  def cfgIn: Iterator[CfgNode] = get().cfgIn
  override def _cfgIn          = get()._cfgIn

  def containsIn: Iterator[AstNode] = get().containsIn
  override def _containsIn          = get()._containsIn

  /** Traverse to FILE via CONTAINS IN edge.
    */
  def fileViaContainsIn: overflowdb.traversal.Traversal[File] = get().fileViaContainsIn

  @deprecated("please use `fileViaContainsIn`", "June 2024")
  def _fileViaContainsIn = fileViaContainsIn

  /** Traverse to TYPE_DECL via CONTAINS IN edge.
    */
  def typeDeclViaContainsIn: overflowdb.traversal.Traversal[TypeDecl] = get().typeDeclViaContainsIn

  @deprecated("please use `typeDeclViaContainsIn`", "June 2024")
  def _typeDeclViaContainsIn = typeDeclViaContainsIn

  def postDominateIn: Iterator[CfgNode] = get().postDominateIn
  override def _postDominateIn          = get()._postDominateIn

  /** Traverse to BLOCK via POST_DOMINATE IN edge.
    */
  def blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = get().blockViaPostDominateIn

  @deprecated("please use `blockViaPostDominateIn`", "June 2024")
  def _blockViaPostDominateIn = blockViaPostDominateIn

  /** Traverse to CALL via POST_DOMINATE IN edge.
    */
  def callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = get().callViaPostDominateIn

  @deprecated("please use `callViaPostDominateIn`", "June 2024")
  def _callViaPostDominateIn = callViaPostDominateIn

  /** Traverse to CONTROL_STRUCTURE via POST_DOMINATE IN edge.
    */
  def controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    get().controlStructureViaPostDominateIn

  @deprecated("please use `controlStructureViaPostDominateIn`", "June 2024")
  def _controlStructureViaPostDominateIn = controlStructureViaPostDominateIn

  /** Traverse to FIELD_IDENTIFIER via POST_DOMINATE IN edge.
    */
  def fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    get().fieldIdentifierViaPostDominateIn

  @deprecated("please use `fieldIdentifierViaPostDominateIn`", "June 2024")
  def _fieldIdentifierViaPostDominateIn = fieldIdentifierViaPostDominateIn

  /** Traverse to IDENTIFIER via POST_DOMINATE IN edge.
    */
  def identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = get().identifierViaPostDominateIn

  @deprecated("please use `identifierViaPostDominateIn`", "June 2024")
  def _identifierViaPostDominateIn = identifierViaPostDominateIn

  /** Traverse to LITERAL via POST_DOMINATE IN edge.
    */
  def literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = get().literalViaPostDominateIn

  @deprecated("please use `literalViaPostDominateIn`", "June 2024")
  def _literalViaPostDominateIn = literalViaPostDominateIn

  /** Traverse to METHOD_REF via POST_DOMINATE IN edge.
    */
  def methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaPostDominateIn

  @deprecated("please use `methodRefViaPostDominateIn`", "June 2024")
  def _methodRefViaPostDominateIn = methodRefViaPostDominateIn

  /** Traverse to METHOD_RETURN via POST_DOMINATE IN edge.
    */
  def methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] = get().methodReturnViaPostDominateIn

  @deprecated("please use `methodReturnViaPostDominateIn`", "June 2024")
  def _methodReturnViaPostDominateIn = methodReturnViaPostDominateIn

  /** Traverse to RETURN via POST_DOMINATE IN edge.
    */
  def returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = get().returnViaPostDominateIn

  @deprecated("please use `returnViaPostDominateIn`", "June 2024")
  def _returnViaPostDominateIn = returnViaPostDominateIn

  /** Traverse to TYPE_REF via POST_DOMINATE IN edge.
    */
  def typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = get().typeRefViaPostDominateIn

  @deprecated("please use `typeRefViaPostDominateIn`", "June 2024")
  def _typeRefViaPostDominateIn = typeRefViaPostDominateIn

  /** Traverse to UNKNOWN via POST_DOMINATE IN edge.
    */
  def unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = get().unknownViaPostDominateIn

  @deprecated("please use `unknownViaPostDominateIn`", "June 2024")
  def _unknownViaPostDominateIn = unknownViaPostDominateIn

  def refIn: Iterator[StoredNode] = get().refIn
  override def _refIn             = get()._refIn

  /** Traverse to BINDING via REF IN edge.
    */
  def bindingViaRefIn: overflowdb.traversal.Traversal[Binding] = get().bindingViaRefIn

  @deprecated("please use `bindingViaRefIn`", "June 2024")
  def _bindingViaRefIn = bindingViaRefIn

  /** Traverse to METHOD_REF via REF IN edge.
    */
  def methodRefViaRefIn: overflowdb.traversal.Traversal[MethodRef] = get().methodRefViaRefIn

  @deprecated("please use `methodRefViaRefIn`", "June 2024")
  def _methodRefViaRefIn = methodRefViaRefIn

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
    Method.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "astParentFullName"
      case 2  => "astParentType"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "columnNumberEnd"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "hash"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "lineNumberEnd"
      case 12 => "name"
      case 13 => "offset"
      case 14 => "offsetEnd"
      case 15 => "order"
      case 16 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => astParentFullName
      case 2  => astParentType
      case 3  => code
      case 4  => columnNumber
      case 5  => columnNumberEnd
      case 6  => filename
      case 7  => fullName
      case 8  => hash
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => lineNumberEnd
      case 12 => name
      case 13 => offset
      case 14 => offsetEnd
      case 15 => order
      case 16 => signature
    }

  override def productPrefix = "Method"
  override def productArity  = 17
}

class MethodDb(ref: NodeRef[NodeDb])
    extends NodeDb(ref)
    with StoredNode
    with AstNode
    with CfgNode
    with Declaration
    with MethodBase {

  override def layoutInformation: NodeLayoutInformation = Method.layoutInformation

  private var _astParentFullName: String     = Method.PropertyDefaults.AstParentFullName
  def astParentFullName: String              = _astParentFullName
  private var _astParentType: String         = Method.PropertyDefaults.AstParentType
  def astParentType: String                  = _astParentType
  private var _code: String                  = Method.PropertyDefaults.Code
  def code: String                           = _code
  private var _columnNumber: Integer         = null
  def columnNumber: Option[scala.Int]        = Option(_columnNumber).asInstanceOf[Option[scala.Int]]
  private var _columnNumberEnd: Integer      = null
  def columnNumberEnd: Option[scala.Int]     = Option(_columnNumberEnd).asInstanceOf[Option[scala.Int]]
  private var _filename: String              = Method.PropertyDefaults.Filename
  def filename: String                       = _filename
  private var _fullName: String              = Method.PropertyDefaults.FullName
  def fullName: String                       = _fullName
  private var _hash: String                  = null
  def hash: Option[String]                   = Option(_hash).asInstanceOf[Option[String]]
  private var _isExternal: java.lang.Boolean = Method.PropertyDefaults.IsExternal
  def isExternal: Boolean                    = _isExternal
  private var _lineNumber: Integer           = null
  def lineNumber: Option[scala.Int]          = Option(_lineNumber).asInstanceOf[Option[scala.Int]]
  private var _lineNumberEnd: Integer        = null
  def lineNumberEnd: Option[scala.Int]       = Option(_lineNumberEnd).asInstanceOf[Option[scala.Int]]
  private var _name: String                  = Method.PropertyDefaults.Name
  def name: String                           = _name
  private var _offset: Integer               = null
  def offset: Option[scala.Int]              = Option(_offset).asInstanceOf[Option[scala.Int]]
  private var _offsetEnd: Integer            = null
  def offsetEnd: Option[scala.Int]           = Option(_offsetEnd).asInstanceOf[Option[scala.Int]]
  private var _order: Integer                = Method.PropertyDefaults.Order
  def order: scala.Int                       = _order
  private var _signature: String             = Method.PropertyDefaults.Signature
  def signature: String                      = _signature

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("AST_PARENT_FULL_NAME", astParentFullName)
    properties.put("AST_PARENT_TYPE", astParentType)
    properties.put("CODE", code)
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    columnNumberEnd.map { value => properties.put("COLUMN_NUMBER_END", value) }
    properties.put("FILENAME", filename)
    properties.put("FULL_NAME", fullName)
    hash.map { value => properties.put("HASH", value) }
    properties.put("IS_EXTERNAL", isExternal)
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    lineNumberEnd.map { value => properties.put("LINE_NUMBER_END", value) }
    properties.put("NAME", name)
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    properties.put("ORDER", order)
    properties.put("SIGNATURE", signature)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == astParentFullName)) { properties.put("AST_PARENT_FULL_NAME", astParentFullName) }
    if (!(("<empty>") == astParentType)) { properties.put("AST_PARENT_TYPE", astParentType) }
    if (!(("<empty>") == code)) { properties.put("CODE", code) }
    columnNumber.map { value => properties.put("COLUMN_NUMBER", value) }
    columnNumberEnd.map { value => properties.put("COLUMN_NUMBER_END", value) }
    if (!(("<empty>") == filename)) { properties.put("FILENAME", filename) }
    if (!(("<empty>") == fullName)) { properties.put("FULL_NAME", fullName) }
    hash.map { value => properties.put("HASH", value) }
    if (!((false) == isExternal)) { properties.put("IS_EXTERNAL", isExternal) }
    lineNumber.map { value => properties.put("LINE_NUMBER", value) }
    lineNumberEnd.map { value => properties.put("LINE_NUMBER_END", value) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }
    offset.map { value => properties.put("OFFSET", value) }
    offsetEnd.map { value => properties.put("OFFSET_END", value) }
    if (!((-1: Int) == order)) { properties.put("ORDER", order) }
    if (!(("") == signature)) { properties.put("SIGNATURE", signature) }

    properties
  }

  import overflowdb.traversal._
  def astOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](0)
  override def _astOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](0)

  @deprecated("please use `annotationViaAstOut`", "June 2024")
  def _annotationViaAstOut = annotationViaAstOut

  def annotationViaAstOut: overflowdb.traversal.Traversal[Annotation] = astOut.collectAll[Annotation]
  @deprecated("please use `block`", "June 2024")
  def _block = block

  def block: Block = try { astOut.collectAll[Block].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label AST to an adjacent BLOCK is mandatory, but not defined for this METHOD node with id=" + id,
        e
      )
  }
  @deprecated("please use `methodViaAstOut`", "June 2024")
  def _methodViaAstOut = methodViaAstOut

  def methodViaAstOut: overflowdb.traversal.Traversal[Method] = astOut.collectAll[Method]
  @deprecated("please use `parameter`", "June 2024")
  def _parameter = parameter

  def parameter: overflowdb.traversal.Traversal[MethodParameterIn] = astOut.collectAll[MethodParameterIn]
  @deprecated("please use `methodParameterOutViaAstOut`", "June 2024")
  def _methodParameterOutViaAstOut = methodParameterOutViaAstOut

  def methodParameterOutViaAstOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    astOut.collectAll[MethodParameterOut]
  @deprecated("please use `methodReturn`", "June 2024")
  def _methodReturn = methodReturn

  def methodReturn: MethodReturn = try { astOut.collectAll[MethodReturn].next() }
  catch {
    case e: java.util.NoSuchElementException =>
      throw new overflowdb.SchemaViolationException(
        "OUT edge with label AST to an adjacent METHOD_RETURN is mandatory, but not defined for this METHOD node with id=" + id,
        e
      )
  }
  @deprecated("please use `modifierViaAstOut`", "June 2024")
  def _modifierViaAstOut = modifierViaAstOut

  def modifierViaAstOut: overflowdb.traversal.Traversal[Modifier] = astOut.collectAll[Modifier]
  @deprecated("please use `typeDeclViaAstOut`", "June 2024")
  def _typeDeclViaAstOut = typeDeclViaAstOut

  def typeDeclViaAstOut: overflowdb.traversal.Traversal[TypeDecl] = astOut.collectAll[TypeDecl]
  @deprecated("please use `typeParameterViaAstOut`", "June 2024")
  def _typeParameterViaAstOut = typeParameterViaAstOut

  def typeParameterViaAstOut: overflowdb.traversal.Traversal[TypeParameter] = astOut.collectAll[TypeParameter]

  def cfgOut: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](1)
  override def _cfgOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](1)

  @deprecated("please use `cfgFirst`", "June 2024")
  def _cfgFirst = cfgFirst

  def cfgFirst: overflowdb.traversal.Traversal[CfgNode] = cfgOut.collectAll[CfgNode]
  @deprecated("please use `methodReturnViaCfgOut`", "June 2024")
  def _methodReturnViaCfgOut = methodReturnViaCfgOut

  def methodReturnViaCfgOut: Option[MethodReturn] = cfgOut.collectAll[MethodReturn].nextOption()

  def containsOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](2)
  override def _containsOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](2)

  @deprecated("please use `blockViaContainsOut`", "June 2024")
  def _blockViaContainsOut = blockViaContainsOut

  def blockViaContainsOut: overflowdb.traversal.Traversal[Block] = containsOut.collectAll[Block]
  @deprecated("please use `callViaContainsOut`", "June 2024")
  def _callViaContainsOut = callViaContainsOut

  def callViaContainsOut: overflowdb.traversal.Traversal[Call] = containsOut.collectAll[Call]
  @deprecated("please use `controlStructureViaContainsOut`", "June 2024")
  def _controlStructureViaContainsOut = controlStructureViaContainsOut

  def controlStructureViaContainsOut: overflowdb.traversal.Traversal[ControlStructure] =
    containsOut.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaContainsOut`", "June 2024")
  def _fieldIdentifierViaContainsOut = fieldIdentifierViaContainsOut

  def fieldIdentifierViaContainsOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    containsOut.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaContainsOut`", "June 2024")
  def _identifierViaContainsOut = identifierViaContainsOut

  def identifierViaContainsOut: overflowdb.traversal.Traversal[Identifier] = containsOut.collectAll[Identifier]
  @deprecated("please use `jumpTargetViaContainsOut`", "June 2024")
  def _jumpTargetViaContainsOut = jumpTargetViaContainsOut

  def jumpTargetViaContainsOut: overflowdb.traversal.Traversal[JumpTarget] = containsOut.collectAll[JumpTarget]
  @deprecated("please use `literal`", "June 2024")
  def _literal = literal

  def literal: overflowdb.traversal.Traversal[Literal] = containsOut.collectAll[Literal]
  @deprecated("please use `methodRefViaContainsOut`", "June 2024")
  def _methodRefViaContainsOut = methodRefViaContainsOut

  def methodRefViaContainsOut: overflowdb.traversal.Traversal[MethodRef] = containsOut.collectAll[MethodRef]
  @deprecated("please use `returnViaContainsOut`", "June 2024")
  def _returnViaContainsOut = returnViaContainsOut

  def returnViaContainsOut: overflowdb.traversal.Traversal[Return] = containsOut.collectAll[Return]
  @deprecated("please use `templateDomViaContainsOut`", "June 2024")
  def _templateDomViaContainsOut = templateDomViaContainsOut

  def templateDomViaContainsOut: overflowdb.traversal.Traversal[TemplateDom] = containsOut.collectAll[TemplateDom]
  @deprecated("please use `typeRefViaContainsOut`", "June 2024")
  def _typeRefViaContainsOut = typeRefViaContainsOut

  def typeRefViaContainsOut: overflowdb.traversal.Traversal[TypeRef] = containsOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaContainsOut`", "June 2024")
  def _unknownViaContainsOut = unknownViaContainsOut

  def unknownViaContainsOut: overflowdb.traversal.Traversal[Unknown] = containsOut.collectAll[Unknown]

  def dominateOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](3)
  override def _dominateOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](3)

  @deprecated("please use `blockViaDominateOut`", "June 2024")
  def _blockViaDominateOut = blockViaDominateOut

  def blockViaDominateOut: overflowdb.traversal.Traversal[Block] = dominateOut.collectAll[Block]
  @deprecated("please use `callViaDominateOut`", "June 2024")
  def _callViaDominateOut = callViaDominateOut

  def callViaDominateOut: overflowdb.traversal.Traversal[Call] = dominateOut.collectAll[Call]
  @deprecated("please use `fieldIdentifierViaDominateOut`", "June 2024")
  def _fieldIdentifierViaDominateOut = fieldIdentifierViaDominateOut

  def fieldIdentifierViaDominateOut: overflowdb.traversal.Traversal[FieldIdentifier] =
    dominateOut.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaDominateOut`", "June 2024")
  def _identifierViaDominateOut = identifierViaDominateOut

  def identifierViaDominateOut: overflowdb.traversal.Traversal[Identifier] = dominateOut.collectAll[Identifier]
  @deprecated("please use `literalViaDominateOut`", "June 2024")
  def _literalViaDominateOut = literalViaDominateOut

  def literalViaDominateOut: overflowdb.traversal.Traversal[Literal] = dominateOut.collectAll[Literal]
  @deprecated("please use `methodRefViaDominateOut`", "June 2024")
  def _methodRefViaDominateOut = methodRefViaDominateOut

  def methodRefViaDominateOut: overflowdb.traversal.Traversal[MethodRef] = dominateOut.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaDominateOut`", "June 2024")
  def _methodReturnViaDominateOut = methodReturnViaDominateOut

  def methodReturnViaDominateOut: overflowdb.traversal.Traversal[MethodReturn] = dominateOut.collectAll[MethodReturn]
  @deprecated("please use `returnViaDominateOut`", "June 2024")
  def _returnViaDominateOut = returnViaDominateOut

  def returnViaDominateOut: overflowdb.traversal.Traversal[Return] = dominateOut.collectAll[Return]
  @deprecated("please use `typeRefViaDominateOut`", "June 2024")
  def _typeRefViaDominateOut = typeRefViaDominateOut

  def typeRefViaDominateOut: overflowdb.traversal.Traversal[TypeRef] = dominateOut.collectAll[TypeRef]
  @deprecated("please use `unknownViaDominateOut`", "June 2024")
  def _unknownViaDominateOut = unknownViaDominateOut

  def unknownViaDominateOut: overflowdb.traversal.Traversal[Unknown] = dominateOut.collectAll[Unknown]

  def reachingDefOut: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](4)
  override def _reachingDefOut          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](4)

  @deprecated("please use `callViaReachingDefOut`", "June 2024")
  def _callViaReachingDefOut = callViaReachingDefOut

  def callViaReachingDefOut: overflowdb.traversal.Traversal[Call] = reachingDefOut.collectAll[Call]
  @deprecated("please use `identifierViaReachingDefOut`", "June 2024")
  def _identifierViaReachingDefOut = identifierViaReachingDefOut

  def identifierViaReachingDefOut: overflowdb.traversal.Traversal[Identifier] = reachingDefOut.collectAll[Identifier]
  @deprecated("please use `literalViaReachingDefOut`", "June 2024")
  def _literalViaReachingDefOut = literalViaReachingDefOut

  def literalViaReachingDefOut: overflowdb.traversal.Traversal[Literal] = reachingDefOut.collectAll[Literal]
  @deprecated("please use `methodParameterInViaReachingDefOut`", "June 2024")
  def _methodParameterInViaReachingDefOut = methodParameterInViaReachingDefOut

  def methodParameterInViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterIn] =
    reachingDefOut.collectAll[MethodParameterIn]
  @deprecated("please use `methodParameterOutViaReachingDefOut`", "June 2024")
  def _methodParameterOutViaReachingDefOut = methodParameterOutViaReachingDefOut

  def methodParameterOutViaReachingDefOut: overflowdb.traversal.Traversal[MethodParameterOut] =
    reachingDefOut.collectAll[MethodParameterOut]
  @deprecated("please use `methodRefViaReachingDefOut`", "June 2024")
  def _methodRefViaReachingDefOut = methodRefViaReachingDefOut

  def methodRefViaReachingDefOut: overflowdb.traversal.Traversal[MethodRef] = reachingDefOut.collectAll[MethodRef]
  @deprecated("please use `returnViaReachingDefOut`", "June 2024")
  def _returnViaReachingDefOut = returnViaReachingDefOut

  def returnViaReachingDefOut: overflowdb.traversal.Traversal[Return] = reachingDefOut.collectAll[Return]
  @deprecated("please use `typeRefViaReachingDefOut`", "June 2024")
  def _typeRefViaReachingDefOut = typeRefViaReachingDefOut

  def typeRefViaReachingDefOut: overflowdb.traversal.Traversal[TypeRef] = reachingDefOut.collectAll[TypeRef]

  def sourceFileOut: Iterator[File] = createAdjacentNodeScalaIteratorByOffSet[File](5)
  override def _sourceFileOut       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](5)

  @deprecated("please use `fileViaSourceFileOut`", "June 2024")
  def _fileViaSourceFileOut = fileViaSourceFileOut

  def fileViaSourceFileOut: overflowdb.traversal.Traversal[File] = sourceFileOut.collectAll[File]

  def taggedByOut: Iterator[Tag] = createAdjacentNodeScalaIteratorByOffSet[Tag](6)
  override def _taggedByOut      = createAdjacentNodeScalaIteratorByOffSet[StoredNode](6)

  @deprecated("please use `tagViaTaggedByOut`", "June 2024")
  def _tagViaTaggedByOut = tagViaTaggedByOut

  def tagViaTaggedByOut: overflowdb.traversal.Traversal[Tag] = taggedByOut.collectAll[Tag]

  def astIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](7)
  override def _astIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](7)

  @deprecated("please use `methodViaAstIn`", "June 2024")
  def _methodViaAstIn = methodViaAstIn

  def methodViaAstIn: Option[Method] = astIn.collectAll[Method].nextOption()
  @deprecated("please use `namespaceBlockViaAstIn`", "June 2024")
  def _namespaceBlockViaAstIn = namespaceBlockViaAstIn

  def namespaceBlockViaAstIn: Option[NamespaceBlock] = astIn.collectAll[NamespaceBlock].nextOption()
  @deprecated("please use `typeDeclViaAstIn`", "June 2024")
  def _typeDeclViaAstIn = typeDeclViaAstIn

  def typeDeclViaAstIn: Option[TypeDecl] = astIn.collectAll[TypeDecl].nextOption()

  def callIn: Iterator[Call] = createAdjacentNodeScalaIteratorByOffSet[Call](8)
  override def _callIn       = createAdjacentNodeScalaIteratorByOffSet[StoredNode](8)

  @deprecated("please use `callViaCallIn`", "June 2024")
  def _callViaCallIn = callViaCallIn

  def callViaCallIn: overflowdb.traversal.Traversal[Call] = callIn.collectAll[Call]

  def cfgIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](9)
  override def _cfgIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](9)

  def containsIn: Iterator[AstNode] = createAdjacentNodeScalaIteratorByOffSet[AstNode](10)
  override def _containsIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](10)

  @deprecated("please use `fileViaContainsIn`", "June 2024")
  def _fileViaContainsIn = fileViaContainsIn

  def fileViaContainsIn: overflowdb.traversal.Traversal[File] = containsIn.collectAll[File]
  @deprecated("please use `typeDeclViaContainsIn`", "June 2024")
  def _typeDeclViaContainsIn = typeDeclViaContainsIn

  def typeDeclViaContainsIn: overflowdb.traversal.Traversal[TypeDecl] = containsIn.collectAll[TypeDecl]

  def postDominateIn: Iterator[CfgNode] = createAdjacentNodeScalaIteratorByOffSet[CfgNode](11)
  override def _postDominateIn          = createAdjacentNodeScalaIteratorByOffSet[StoredNode](11)

  @deprecated("please use `blockViaPostDominateIn`", "June 2024")
  def _blockViaPostDominateIn = blockViaPostDominateIn

  def blockViaPostDominateIn: overflowdb.traversal.Traversal[Block] = postDominateIn.collectAll[Block]
  @deprecated("please use `callViaPostDominateIn`", "June 2024")
  def _callViaPostDominateIn = callViaPostDominateIn

  def callViaPostDominateIn: overflowdb.traversal.Traversal[Call] = postDominateIn.collectAll[Call]
  @deprecated("please use `controlStructureViaPostDominateIn`", "June 2024")
  def _controlStructureViaPostDominateIn = controlStructureViaPostDominateIn

  def controlStructureViaPostDominateIn: overflowdb.traversal.Traversal[ControlStructure] =
    postDominateIn.collectAll[ControlStructure]
  @deprecated("please use `fieldIdentifierViaPostDominateIn`", "June 2024")
  def _fieldIdentifierViaPostDominateIn = fieldIdentifierViaPostDominateIn

  def fieldIdentifierViaPostDominateIn: overflowdb.traversal.Traversal[FieldIdentifier] =
    postDominateIn.collectAll[FieldIdentifier]
  @deprecated("please use `identifierViaPostDominateIn`", "June 2024")
  def _identifierViaPostDominateIn = identifierViaPostDominateIn

  def identifierViaPostDominateIn: overflowdb.traversal.Traversal[Identifier] = postDominateIn.collectAll[Identifier]
  @deprecated("please use `literalViaPostDominateIn`", "June 2024")
  def _literalViaPostDominateIn = literalViaPostDominateIn

  def literalViaPostDominateIn: overflowdb.traversal.Traversal[Literal] = postDominateIn.collectAll[Literal]
  @deprecated("please use `methodRefViaPostDominateIn`", "June 2024")
  def _methodRefViaPostDominateIn = methodRefViaPostDominateIn

  def methodRefViaPostDominateIn: overflowdb.traversal.Traversal[MethodRef] = postDominateIn.collectAll[MethodRef]
  @deprecated("please use `methodReturnViaPostDominateIn`", "June 2024")
  def _methodReturnViaPostDominateIn = methodReturnViaPostDominateIn

  def methodReturnViaPostDominateIn: overflowdb.traversal.Traversal[MethodReturn] =
    postDominateIn.collectAll[MethodReturn]
  @deprecated("please use `returnViaPostDominateIn`", "June 2024")
  def _returnViaPostDominateIn = returnViaPostDominateIn

  def returnViaPostDominateIn: overflowdb.traversal.Traversal[Return] = postDominateIn.collectAll[Return]
  @deprecated("please use `typeRefViaPostDominateIn`", "June 2024")
  def _typeRefViaPostDominateIn = typeRefViaPostDominateIn

  def typeRefViaPostDominateIn: overflowdb.traversal.Traversal[TypeRef] = postDominateIn.collectAll[TypeRef]
  @deprecated("please use `unknownViaPostDominateIn`", "June 2024")
  def _unknownViaPostDominateIn = unknownViaPostDominateIn

  def unknownViaPostDominateIn: overflowdb.traversal.Traversal[Unknown] = postDominateIn.collectAll[Unknown]

  def refIn: Iterator[StoredNode] = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)
  override def _refIn             = createAdjacentNodeScalaIteratorByOffSet[StoredNode](12)

  @deprecated("please use `bindingViaRefIn`", "June 2024")
  def _bindingViaRefIn = bindingViaRefIn

  def bindingViaRefIn: overflowdb.traversal.Traversal[Binding] = refIn.collectAll[Binding]
  @deprecated("please use `methodRefViaRefIn`", "June 2024")
  def _methodRefViaRefIn = methodRefViaRefIn

  def methodRefViaRefIn: overflowdb.traversal.Traversal[MethodRef] = refIn.collectAll[MethodRef]

  override def label: String = {
    Method.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "id"
      case 1  => "astParentFullName"
      case 2  => "astParentType"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "columnNumberEnd"
      case 6  => "filename"
      case 7  => "fullName"
      case 8  => "hash"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "lineNumberEnd"
      case 12 => "name"
      case 13 => "offset"
      case 14 => "offsetEnd"
      case 15 => "order"
      case 16 => "signature"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => id
      case 1  => astParentFullName
      case 2  => astParentType
      case 3  => code
      case 4  => columnNumber
      case 5  => columnNumberEnd
      case 6  => filename
      case 7  => fullName
      case 8  => hash
      case 9  => isExternal
      case 10 => lineNumber
      case 11 => lineNumberEnd
      case 12 => name
      case 13 => offset
      case 14 => offsetEnd
      case 15 => order
      case 16 => signature
    }

  override def productPrefix = "Method"
  override def productArity  = 17

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodDb]

  override def property(key: String): Any = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName
      case "AST_PARENT_TYPE"      => this._astParentType
      case "CODE"                 => this._code
      case "COLUMN_NUMBER"        => this._columnNumber
      case "COLUMN_NUMBER_END"    => this._columnNumberEnd
      case "FILENAME"             => this._filename
      case "FULL_NAME"            => this._fullName
      case "HASH"                 => this._hash
      case "IS_EXTERNAL"          => this._isExternal
      case "LINE_NUMBER"          => this._lineNumber
      case "LINE_NUMBER_END"      => this._lineNumberEnd
      case "NAME"                 => this._name
      case "OFFSET"               => this._offset
      case "OFFSET_END"           => this._offsetEnd
      case "ORDER"                => this._order
      case "SIGNATURE"            => this._signature

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "AST_PARENT_FULL_NAME" => this._astParentFullName = value.asInstanceOf[String]
      case "AST_PARENT_TYPE"      => this._astParentType = value.asInstanceOf[String]
      case "CODE"                 => this._code = value.asInstanceOf[String]
      case "COLUMN_NUMBER"        => this._columnNumber = value.asInstanceOf[scala.Int]
      case "COLUMN_NUMBER_END"    => this._columnNumberEnd = value.asInstanceOf[scala.Int]
      case "FILENAME"             => this._filename = value.asInstanceOf[String]
      case "FULL_NAME"            => this._fullName = value.asInstanceOf[String]
      case "HASH"                 => this._hash = value.asInstanceOf[String]
      case "IS_EXTERNAL"          => this._isExternal = value.asInstanceOf[Boolean]
      case "LINE_NUMBER"          => this._lineNumber = value.asInstanceOf[scala.Int]
      case "LINE_NUMBER_END"      => this._lineNumberEnd = value.asInstanceOf[scala.Int]
      case "NAME"                 => this._name = value.asInstanceOf[String]
      case "OFFSET"               => this._offset = value.asInstanceOf[scala.Int]
      case "OFFSET_END"           => this._offsetEnd = value.asInstanceOf[scala.Int]
      case "ORDER"                => this._order = value.asInstanceOf[scala.Int]
      case "SIGNATURE"            => this._signature = value.asInstanceOf[String]

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
    this._astParentFullName = newNode.asInstanceOf[NewMethod].astParentFullName
    this._astParentType = newNode.asInstanceOf[NewMethod].astParentType
    this._code = newNode.asInstanceOf[NewMethod].code
    this._columnNumber = newNode.asInstanceOf[NewMethod].columnNumber match {
      case None => null; case Some(value) => value
    }
    this._columnNumberEnd = newNode.asInstanceOf[NewMethod].columnNumberEnd match {
      case None => null; case Some(value) => value
    }
    this._filename = newNode.asInstanceOf[NewMethod].filename
    this._fullName = newNode.asInstanceOf[NewMethod].fullName
    this._hash = newNode.asInstanceOf[NewMethod].hash match { case None => null; case Some(value) => value }
    this._isExternal = newNode.asInstanceOf[NewMethod].isExternal
    this._lineNumber = newNode.asInstanceOf[NewMethod].lineNumber match { case None => null; case Some(value) => value }
    this._lineNumberEnd = newNode.asInstanceOf[NewMethod].lineNumberEnd match {
      case None => null; case Some(value) => value
    }
    this._name = newNode.asInstanceOf[NewMethod].name
    this._offset = newNode.asInstanceOf[NewMethod].offset match { case None => null; case Some(value) => value }
    this._offsetEnd = newNode.asInstanceOf[NewMethod].offsetEnd match { case None => null; case Some(value) => value }
    this._order = newNode.asInstanceOf[NewMethod].order
    this._signature = newNode.asInstanceOf[NewMethod].signature

    graph.indexManager.putIfIndexed("FULL_NAME", newNode.asInstanceOf[NewMethod].fullName, this.ref)
  }

}
