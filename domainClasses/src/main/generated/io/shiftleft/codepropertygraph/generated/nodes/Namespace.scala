package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait NamespaceEMT extends AnyRef with AstNodeEMT with HasNameEMT

trait NamespaceBase extends AbstractNode with AstNodeBase with StaticType[NamespaceEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object Namespace {
  val Label = "NAMESPACE"
  object PropertyNames {

    /** This field holds the code snippet that the node represents. */
    val Code = "CODE"

    /** This optional fields provides the column number of the program construct represented by the node.
      */
    val ColumnNumber = "COLUMN_NUMBER"

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = "LINE_NUMBER"

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = "NAME"

    /** This integer indicates the position of the node among its siblings in the AST. The left-most child has an order
      * of 0.
      */
    val Order = "ORDER"
  }
  object PropertyKeys {

    /** This field holds the code snippet that the node represents. */
    val Code = flatgraph.SinglePropertyKey[String](kind = 10, name = "CODE", default = "<empty>")

    /** This optional fields provides the column number of the program construct represented by the node.
      */
    val ColumnNumber = flatgraph.OptionalPropertyKey[Int](kind = 11, name = "COLUMN_NUMBER")

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = flatgraph.OptionalPropertyKey[Int](kind = 34, name = "LINE_NUMBER")

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = flatgraph.SinglePropertyKey[String](kind = 39, name = "NAME", default = "<empty>")

    /** This integer indicates the position of the node among its siblings in the AST. The left-most child has an order
      * of 0.
      */
    val Order = flatgraph.SinglePropertyKey[Int](kind = 43, name = "ORDER", default = -1: Int)
  }
  object PropertyDefaults {
    val Code  = "<empty>"
    val Name  = "<empty>"
    val Order = -1: Int
  }
}

class Namespace(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 32.toShort, seq_4762)
    with NamespaceBase
    with AstNode
    with StaticType[NamespaceEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "code"
      case 1 => "columnNumber"
      case 2 => "lineNumber"
      case 3 => "name"
      case 4 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.code
      case 1 => this.columnNumber
      case 2 => this.lineNumber
      case 3 => this.name
      case 4 => this.order
      case _ => null
    }

  override def productPrefix = "Namespace"
  override def productArity  = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Namespace]
}

object NewNamespace {
  def apply(): NewNamespace = new NewNamespace
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
class NewNamespace extends NewNode(32.toShort) with NamespaceBase with AstNodeNew {
  override type StoredNodeType = Namespace
  override def label: String = "NAMESPACE"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewNamespace.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewNamespace.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var code: String                                = "<empty>": String
  var columnNumber: Option[Int]                   = None
  var lineNumber: Option[Int]                     = None
  var name: String                                = "<empty>": String
  var order: Int                                  = -1: Int
  def code(value: String): this.type              = { this.code = value; this }
  def columnNumber(value: Int): this.type         = { this.columnNumber = Option(value); this }
  def columnNumber(value: Option[Int]): this.type = { this.columnNumber = value; this }
  def lineNumber(value: Int): this.type           = { this.lineNumber = Option(value); this }
  def lineNumber(value: Option[Int]): this.type   = { this.lineNumber = value; this }
  def name(value: String): this.type              = { this.name = value; this }
  def order(value: Int): this.type                = { this.order = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.insertProperty(this, 10, Iterator(this.code))
    if (columnNumber.nonEmpty) interface.insertProperty(this, 11, this.columnNumber)
    if (lineNumber.nonEmpty) interface.insertProperty(this, 34, this.lineNumber)
    interface.insertProperty(this, 39, Iterator(this.name))
    interface.insertProperty(this, 43, Iterator(this.order))
  }

  override def copy(): this.type = {
    val newInstance = new NewNamespace
    newInstance.code = this.code
    newInstance.columnNumber = this.columnNumber
    newInstance.lineNumber = this.lineNumber
    newInstance.name = this.name
    newInstance.order = this.order
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "code"
      case 1 => "columnNumber"
      case 2 => "lineNumber"
      case 3 => "name"
      case 4 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.code
      case 1 => this.columnNumber
      case 2 => this.lineNumber
      case 3 => this.name
      case 4 => this.order
      case _ => null
    }

  override def productPrefix                = "NewNamespace"
  override def productArity                 = 5
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewNamespace]
}
