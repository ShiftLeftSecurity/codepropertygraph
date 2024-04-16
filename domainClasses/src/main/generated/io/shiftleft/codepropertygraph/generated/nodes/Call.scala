package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait CallEMT
    extends AnyRef
    with CallReprEMT
    with ExpressionEMT
    with HasDispatchTypeEMT
    with HasDynamicTypeHintFullNameEMT
    with HasMethodFullNameEMT
    with HasPossibleTypesEMT
    with HasTypeFullNameEMT

trait CallBase extends AbstractNode with CallReprBase with ExpressionBase with StaticType[CallEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res = new java.util.HashMap[String, Any]()
    if ((-1: Int) != this.argumentIndex) res.put("ARGUMENT_INDEX", this.argumentIndex)
    this.argumentName.foreach { p => res.put("ARGUMENT_NAME", p) }
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.dispatchType) res.put("DISPATCH_TYPE", this.dispatchType)
    val tmpDynamicTypeHintFullName = this.dynamicTypeHintFullName;
    if (tmpDynamicTypeHintFullName.nonEmpty) res.put("DYNAMIC_TYPE_HINT_FULL_NAME", tmpDynamicTypeHintFullName)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.methodFullName) res.put("METHOD_FULL_NAME", this.methodFullName)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    val tmpPossibleTypes = this.possibleTypes;
    if (tmpPossibleTypes.nonEmpty) res.put("POSSIBLE_TYPES", tmpPossibleTypes)
    if (("": String) != this.signature) res.put("SIGNATURE", this.signature)
    if (("<empty>": String) != this.typeFullName) res.put("TYPE_FULL_NAME", this.typeFullName)
    res
  }
}

object Call {
  val Label = "CALL"
  object PropertyNames {
    val ArgumentIndex           = io.shiftleft.codepropertygraph.generated.PropertyNames.ARGUMENT_INDEX
    val ArgumentName            = io.shiftleft.codepropertygraph.generated.PropertyNames.ARGUMENT_NAME
    val Code                    = io.shiftleft.codepropertygraph.generated.PropertyNames.CODE
    val ColumnNumber            = io.shiftleft.codepropertygraph.generated.PropertyNames.COLUMN_NUMBER
    val DispatchType            = io.shiftleft.codepropertygraph.generated.PropertyNames.DISPATCH_TYPE
    val DynamicTypeHintFullName = io.shiftleft.codepropertygraph.generated.PropertyNames.DYNAMIC_TYPE_HINT_FULL_NAME
    val LineNumber              = io.shiftleft.codepropertygraph.generated.PropertyNames.LINE_NUMBER
    val MethodFullName          = io.shiftleft.codepropertygraph.generated.PropertyNames.METHOD_FULL_NAME
    val Name                    = io.shiftleft.codepropertygraph.generated.PropertyNames.NAME
    val Order                   = io.shiftleft.codepropertygraph.generated.PropertyNames.ORDER
    val PossibleTypes           = io.shiftleft.codepropertygraph.generated.PropertyNames.POSSIBLE_TYPES
    val Signature               = io.shiftleft.codepropertygraph.generated.PropertyNames.SIGNATURE
    val TypeFullName            = io.shiftleft.codepropertygraph.generated.PropertyNames.TYPE_FULL_NAME
  }
  object PropertyDefaults {
    val ArgumentIndex  = -1: Int
    val Code           = "<empty>"
    val DispatchType   = "<empty>"
    val MethodFullName = "<empty>"
    val Name           = "<empty>"
    val Order          = -1: Int
    val Signature      = ""
    val TypeFullName   = "<empty>"
  }
}

class Call(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 7.toShort, seq_4762)
    with CallBase
    with CallRepr
    with Expression
    with StaticType[CallEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "argumentIndex"
      case 1  => "argumentName"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dispatchType"
      case 5  => "dynamicTypeHintFullName"
      case 6  => "lineNumber"
      case 7  => "methodFullName"
      case 8  => "name"
      case 9  => "order"
      case 10 => "possibleTypes"
      case 11 => "signature"
      case 12 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.argumentIndex
      case 1  => this.argumentName
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.dispatchType
      case 5  => this.dynamicTypeHintFullName
      case 6  => this.lineNumber
      case 7  => this.methodFullName
      case 8  => this.name
      case 9  => this.order
      case 10 => this.possibleTypes
      case 11 => this.signature
      case 12 => this.typeFullName
      case _  => null
    }

  override def productPrefix = "Call"
  override def productArity  = 13

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Call]
}

object NewCall {
  def apply(): NewCall = new NewCall
  private val outNeighbors: Map[String, Set[String]] = Map(
    "ALIAS_OF" -> Set("TYPE"),
    "ARGUMENT" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "AST" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "BINDS" -> Set("BINDING"),
    "BINDS_TO" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CALL" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CAPTURE"     -> Set("CLOSURE_BINDING"),
    "CAPTURED_BY" -> Set("CLOSURE_BINDING"),
    "CDG" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CFG" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CONDITION" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CONTAINS" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "DOMINATE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "EVAL_TYPE"     -> Set("TYPE"),
    "IMPORTS"       -> Set("DEPENDENCY"),
    "INHERITS_FROM" -> Set("TYPE"),
    "IS_CALL_FOR_IMPORT" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "PARAMETER_LINK" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "POST_DOMINATE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "REACHING_DEF" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "RECEIVER" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "REF" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "SOURCE_FILE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "TAGGED_BY" -> Set("TAG")
  )
  private val inNeighbors: Map[String, Set[String]] = Map(
    "ARGUMENT" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "AST" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "BINDS_TO" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CALL" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CDG" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CFG" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CONDITION" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "CONTAINS" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "DOMINATE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "IS_CALL_FOR_IMPORT" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "PARAMETER_LINK" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "POST_DOMINATE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "REACHING_DEF" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "RECEIVER" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "REF" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BINDING",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "CLOSURE_BINDING",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "SOURCE_FILE" -> Set(
      "ANNOTATION",
      "ANNOTATION_LITERAL",
      "ANNOTATION_PARAMETER",
      "ANNOTATION_PARAMETER_ASSIGN",
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CALL_REPR",
      "CFG_NODE",
      "COMMENT",
      "CONTROL_STRUCTURE",
      "EXPRESSION",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_LABEL",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "MODIFIER",
      "NAMESPACE",
      "NAMESPACE_BLOCK",
      "RETURN",
      "TEMPLATE_DOM",
      "TYPE_ARGUMENT",
      "TYPE_DECL",
      "TYPE_PARAMETER",
      "TYPE_REF",
      "UNKNOWN"
    )
  )
}
class NewCall extends NewNode(7.toShort) with CallBase with CallReprNew with ExpressionNew {
  override type StoredNodeType = Call
  override def label: String = "CALL"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewCall.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewCall.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var argumentIndex: Int                             = -1: Int
  var argumentName: Option[String]                   = None
  var code: String                                   = "<empty>": String
  var columnNumber: Option[Int]                      = None
  var dispatchType: String                           = "<empty>": String
  var dynamicTypeHintFullName: IndexedSeq[String]    = ArraySeq.empty
  var lineNumber: Option[Int]                        = None
  var methodFullName: String                         = "<empty>": String
  var name: String                                   = "<empty>": String
  var order: Int                                     = -1: Int
  var possibleTypes: IndexedSeq[String]              = ArraySeq.empty
  var signature: String                              = "": String
  var typeFullName: String                           = "<empty>": String
  def argumentIndex(value: Int): this.type           = { this.argumentIndex = value; this }
  def argumentName(value: Option[String]): this.type = { this.argumentName = value; this }
  def argumentName(value: String): this.type         = { this.argumentName = Option(value); this }
  def code(value: String): this.type                 = { this.code = value; this }
  def columnNumber(value: Int): this.type            = { this.columnNumber = Option(value); this }
  def columnNumber(value: Option[Int]): this.type    = { this.columnNumber = value; this }
  def dispatchType(value: String): this.type         = { this.dispatchType = value; this }
  def dynamicTypeHintFullName(value: IterableOnce[String]): this.type = {
    this.dynamicTypeHintFullName = value.iterator.to(ArraySeq); this
  }
  def lineNumber(value: Int): this.type                     = { this.lineNumber = Option(value); this }
  def lineNumber(value: Option[Int]): this.type             = { this.lineNumber = value; this }
  def methodFullName(value: String): this.type              = { this.methodFullName = value; this }
  def name(value: String): this.type                        = { this.name = value; this }
  def order(value: Int): this.type                          = { this.order = value; this }
  def possibleTypes(value: IterableOnce[String]): this.type = { this.possibleTypes = value.iterator.to(ArraySeq); this }
  def signature(value: String): this.type                   = { this.signature = value; this }
  def typeFullName(value: String): this.type                = { this.typeFullName = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.insertProperty(this, 1, Iterator(this.argumentIndex))
    if (argumentName.nonEmpty) interface.insertProperty(this, 2, this.argumentName)
    interface.insertProperty(this, 10, Iterator(this.code))
    if (columnNumber.nonEmpty) interface.insertProperty(this, 11, this.columnNumber)
    interface.insertProperty(this, 17, Iterator(this.dispatchType))
    if (dynamicTypeHintFullName.nonEmpty) interface.insertProperty(this, 18, this.dynamicTypeHintFullName)
    if (lineNumber.nonEmpty) interface.insertProperty(this, 34, this.lineNumber)
    interface.insertProperty(this, 36, Iterator(this.methodFullName))
    interface.insertProperty(this, 39, Iterator(this.name))
    interface.insertProperty(this, 43, Iterator(this.order))
    if (possibleTypes.nonEmpty) interface.insertProperty(this, 47, this.possibleTypes)
    interface.insertProperty(this, 49, Iterator(this.signature))
    interface.insertProperty(this, 52, Iterator(this.typeFullName))
  }

  override def copy(): this.type = {
    val newInstance = new NewCall
    newInstance.argumentIndex = this.argumentIndex
    newInstance.argumentName = this.argumentName
    newInstance.code = this.code
    newInstance.columnNumber = this.columnNumber
    newInstance.dispatchType = this.dispatchType
    newInstance.dynamicTypeHintFullName = this.dynamicTypeHintFullName
    newInstance.lineNumber = this.lineNumber
    newInstance.methodFullName = this.methodFullName
    newInstance.name = this.name
    newInstance.order = this.order
    newInstance.possibleTypes = this.possibleTypes
    newInstance.signature = this.signature
    newInstance.typeFullName = this.typeFullName
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "argumentIndex"
      case 1  => "argumentName"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dispatchType"
      case 5  => "dynamicTypeHintFullName"
      case 6  => "lineNumber"
      case 7  => "methodFullName"
      case 8  => "name"
      case 9  => "order"
      case 10 => "possibleTypes"
      case 11 => "signature"
      case 12 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.argumentIndex
      case 1  => this.argumentName
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.dispatchType
      case 5  => this.dynamicTypeHintFullName
      case 6  => this.lineNumber
      case 7  => this.methodFullName
      case 8  => this.name
      case 9  => this.order
      case 10 => this.possibleTypes
      case 11 => this.signature
      case 12 => this.typeFullName
      case _  => null
    }

  override def productPrefix                = "NewCall"
  override def productArity                 = 13
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewCall]
}
