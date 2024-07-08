package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait MethodEMT
    extends AnyRef
    with CfgNodeEMT
    with DeclarationEMT
    with HasAstParentFullNameEMT
    with HasAstParentTypeEMT
    with HasColumnNumberEndEMT
    with HasFilenameEMT
    with HasFullNameEMT
    with HasHashEMT
    with HasIsExternalEMT
    with HasLineNumberEndEMT
    with HasOffsetEMT
    with HasOffsetEndEMT
    with HasSignatureEMT

trait MethodBase extends AbstractNode with CfgNodeBase with DeclarationBase with StaticType[MethodEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.astParentFullName) res.put("AST_PARENT_FULL_NAME", this.astParentFullName)
    if (("<empty>": String) != this.astParentType) res.put("AST_PARENT_TYPE", this.astParentType)
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    this.columnNumberEnd.foreach { p => res.put("COLUMN_NUMBER_END", p) }
    if (("<empty>": String) != this.filename) res.put("FILENAME", this.filename)
    if (("<empty>": String) != this.fullName) res.put("FULL_NAME", this.fullName)
    this.hash.foreach { p => res.put("HASH", p) }
    if ((false: Boolean) != this.isExternal) res.put("IS_EXTERNAL", this.isExternal)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    this.lineNumberEnd.foreach { p => res.put("LINE_NUMBER_END", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    if (("": String) != this.signature) res.put("SIGNATURE", this.signature)
    res
  }
}

object Method {
  val Label = "METHOD"
  object PropertyNames {

    /** This field holds the FULL_NAME of the AST parent of an entity. */
    val AstParentFullName = "AST_PARENT_FULL_NAME"

    /** The type of the AST parent. Since this is only used in some parts of the graph, the list does not include all
      * possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
      */
    val AstParentType = "AST_PARENT_TYPE"

    /** This field holds the code snippet that the node represents. */
    val Code = "CODE"

    /** This optional fields provides the column number of the program construct represented by the node.
      */
    val ColumnNumber = "COLUMN_NUMBER"

    /** This optional fields provides the column number at which the program construct represented by the node ends.
      */
    val ColumnNumberEnd = "COLUMN_NUMBER_END"

    /** The path of the source file this node was generated from, relative to the root path in the meta data node. This
      * field must be set but may be set to the value `<unknown>` to indicate that no source file can be associated with
      * the node, e.g., because the node represents an entity known to exist because it is referenced, but for which the
      * file that is is declared in is unknown.
      */
    val Filename = "FILENAME"

    /** This is the fully-qualified name of an entity, e.g., the fully-qualified name of a method or type. The details
      * of what constitutes a fully-qualified name are language specific. This field SHOULD be human readable.
      */
    val FullName = "FULL_NAME"

    /** This property contains a hash value in the form of a string. Hashes can be used to summarize data, e.g., to
      * summarize the contents of source files or sub graphs. Such summaries are useful to determine whether code has
      * already been analyzed in incremental analysis pipelines. This property is optional to allow its calculation to
      * be deferred or skipped if the hash is not needed.
      */
    val Hash = "HASH"

    /** Indicates that the construct (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the
      * code (applies both to insular parsing and to library functions where we have header files only)
      */
    val IsExternal = "IS_EXTERNAL"

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = "LINE_NUMBER"

    /** This optional fields provides the line number at which the program construct represented by the node ends.
      */
    val LineNumberEnd = "LINE_NUMBER_END"

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = "NAME"

    /** Start offset into the CONTENT property of the corresponding FILE node. The offset is such that parts of the
      * content can easily be accessed via `content.substring(offset, offsetEnd)`. This means that the offset must be
      * measured in utf16 encoding (i.e. neither in characters/codeunits nor in byte-offsets into a utf8 encoding). E.g.
      * for METHOD nodes this start offset points to the start of the methods source code in the string holding the
      * source code of the entire file.
      */
    val Offset = "OFFSET"

    /** End offset (exclusive) into the CONTENT property of the corresponding FILE node. See OFFSET documentation for
      * finer details. E.g. for METHOD nodes this end offset points to the first code position which is not part of the
      * method.
      */
    val OffsetEnd = "OFFSET_END"

    /** This integer indicates the position of the node among its siblings in the AST. The left-most child has an order
      * of 0.
      */
    val Order = "ORDER"

    /** The method signature encodes the types of parameters in a string. The string SHOULD be human readable and
      * suitable for differentiating methods with different parameter types sufficiently to allow for resolving of
      * function overloading. The present specification does not enforce a strict format for the signature, that is, it
      * can be chosen by the frontend implementor to fit the source language.
      */
    val Signature = "SIGNATURE"
  }
  object Properties {

    /** This field holds the FULL_NAME of the AST parent of an entity. */
    val AstParentFullName =
      flatgraph.SinglePropertyKey[String](kind = 3, name = "AST_PARENT_FULL_NAME", default = "<empty>")

    /** The type of the AST parent. Since this is only used in some parts of the graph, the list does not include all
      * possible parents by intention. Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
      */
    val AstParentType = flatgraph.SinglePropertyKey[String](kind = 4, name = "AST_PARENT_TYPE", default = "<empty>")

    /** This field holds the code snippet that the node represents. */
    val Code = flatgraph.SinglePropertyKey[String](kind = 10, name = "CODE", default = "<empty>")

    /** This optional fields provides the column number of the program construct represented by the node.
      */
    val ColumnNumber = flatgraph.OptionalPropertyKey[Int](kind = 11, name = "COLUMN_NUMBER")

    /** This optional fields provides the column number at which the program construct represented by the node ends.
      */
    val ColumnNumberEnd = flatgraph.OptionalPropertyKey[Int](kind = 12, name = "COLUMN_NUMBER_END")

    /** The path of the source file this node was generated from, relative to the root path in the meta data node. This
      * field must be set but may be set to the value `<unknown>` to indicate that no source file can be associated with
      * the node, e.g., because the node represents an entity known to exist because it is referenced, but for which the
      * file that is is declared in is unknown.
      */
    val Filename = flatgraph.SinglePropertyKey[String](kind = 21, name = "FILENAME", default = "<empty>")

    /** This is the fully-qualified name of an entity, e.g., the fully-qualified name of a method or type. The details
      * of what constitutes a fully-qualified name are language specific. This field SHOULD be human readable.
      */
    val FullName = flatgraph.SinglePropertyKey[String](kind = 22, name = "FULL_NAME", default = "<empty>")

    /** This property contains a hash value in the form of a string. Hashes can be used to summarize data, e.g., to
      * summarize the contents of source files or sub graphs. Such summaries are useful to determine whether code has
      * already been analyzed in incremental analysis pipelines. This property is optional to allow its calculation to
      * be deferred or skipped if the hash is not needed.
      */
    val Hash = flatgraph.OptionalPropertyKey[String](kind = 23, name = "HASH")

    /** Indicates that the construct (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the
      * code (applies both to insular parsing and to library functions where we have header files only)
      */
    val IsExternal = flatgraph.SinglePropertyKey[Boolean](kind = 29, name = "IS_EXTERNAL", default = false)

    /** This optional field provides the line number of the program construct represented by the node.
      */
    val LineNumber = flatgraph.OptionalPropertyKey[Int](kind = 34, name = "LINE_NUMBER")

    /** This optional fields provides the line number at which the program construct represented by the node ends.
      */
    val LineNumberEnd = flatgraph.OptionalPropertyKey[Int](kind = 35, name = "LINE_NUMBER_END")

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = flatgraph.SinglePropertyKey[String](kind = 39, name = "NAME", default = "<empty>")

    /** Start offset into the CONTENT property of the corresponding FILE node. The offset is such that parts of the
      * content can easily be accessed via `content.substring(offset, offsetEnd)`. This means that the offset must be
      * measured in utf16 encoding (i.e. neither in characters/codeunits nor in byte-offsets into a utf8 encoding). E.g.
      * for METHOD nodes this start offset points to the start of the methods source code in the string holding the
      * source code of the entire file.
      */
    val Offset = flatgraph.OptionalPropertyKey[Int](kind = 41, name = "OFFSET")

    /** End offset (exclusive) into the CONTENT property of the corresponding FILE node. See OFFSET documentation for
      * finer details. E.g. for METHOD nodes this end offset points to the first code position which is not part of the
      * method.
      */
    val OffsetEnd = flatgraph.OptionalPropertyKey[Int](kind = 42, name = "OFFSET_END")

    /** This integer indicates the position of the node among its siblings in the AST. The left-most child has an order
      * of 0.
      */
    val Order = flatgraph.SinglePropertyKey[Int](kind = 43, name = "ORDER", default = -1: Int)

    /** The method signature encodes the types of parameters in a string. The string SHOULD be human readable and
      * suitable for differentiating methods with different parameter types sufficiently to allow for resolving of
      * function overloading. The present specification does not enforce a strict format for the signature, that is, it
      * can be chosen by the frontend implementor to fit the source language.
      */
    val Signature = flatgraph.SinglePropertyKey[String](kind = 49, name = "SIGNATURE", default = "")
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
}

class Method(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 26.toShort, seq_4762)
    with MethodBase
    with CfgNode
    with Declaration
    with StaticType[MethodEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "astParentFullName"
      case 1  => "astParentType"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "columnNumberEnd"
      case 5  => "filename"
      case 6  => "fullName"
      case 7  => "hash"
      case 8  => "isExternal"
      case 9  => "lineNumber"
      case 10 => "lineNumberEnd"
      case 11 => "name"
      case 12 => "offset"
      case 13 => "offsetEnd"
      case 14 => "order"
      case 15 => "signature"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.astParentFullName
      case 1  => this.astParentType
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.columnNumberEnd
      case 5  => this.filename
      case 6  => this.fullName
      case 7  => this.hash
      case 8  => this.isExternal
      case 9  => this.lineNumber
      case 10 => this.lineNumberEnd
      case 11 => this.name
      case 12 => this.offset
      case 13 => this.offsetEnd
      case 14 => this.order
      case 15 => this.signature
      case _  => null
    }

  override def productPrefix = "Method"
  override def productArity  = 16

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Method]
}

object NewMethod {
  def apply(): NewMethod = new NewMethod
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

  object InsertionHelpers {
    object NewNodeInserter_Method_astParentFullName extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.astParentFullName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_astParentType extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.astParentType
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_code extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.code
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_columnNumber extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.columnNumber match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_columnNumberEnd extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.columnNumberEnd match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_filename extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.filename
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_fullName extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.fullName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_hash extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.hash match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_isExternal extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Boolean]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.isExternal
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_lineNumber extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.lineNumber match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_lineNumberEnd extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.lineNumberEnd match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_name extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.name
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_offset extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.offset match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_offsetEnd extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              generated.offsetEnd match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_order extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[Int]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.order
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Method_signature extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMethod =>
              dstCast(offset) = generated.signature
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
  }
}

class NewMethod extends NewNode(26.toShort) with MethodBase with AstNodeNew with CfgNodeNew with DeclarationNew {
  override type StoredNodeType = Method
  override def label: String = "METHOD"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMethod.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMethod.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var astParentFullName: String                      = "<empty>": String
  var astParentType: String                          = "<empty>": String
  var code: String                                   = "<empty>": String
  var columnNumber: Option[Int]                      = None
  var columnNumberEnd: Option[Int]                   = None
  var filename: String                               = "<empty>": String
  var fullName: String                               = "<empty>": String
  var hash: Option[String]                           = None
  var isExternal: Boolean                            = false: Boolean
  var lineNumber: Option[Int]                        = None
  var lineNumberEnd: Option[Int]                     = None
  var name: String                                   = "<empty>": String
  var offset: Option[Int]                            = None
  var offsetEnd: Option[Int]                         = None
  var order: Int                                     = -1: Int
  var signature: String                              = "": String
  def astParentFullName(value: String): this.type    = { this.astParentFullName = value; this }
  def astParentType(value: String): this.type        = { this.astParentType = value; this }
  def code(value: String): this.type                 = { this.code = value; this }
  def columnNumber(value: Int): this.type            = { this.columnNumber = Option(value); this }
  def columnNumber(value: Option[Int]): this.type    = { this.columnNumber = value; this }
  def columnNumberEnd(value: Int): this.type         = { this.columnNumberEnd = Option(value); this }
  def columnNumberEnd(value: Option[Int]): this.type = { this.columnNumberEnd = value; this }
  def filename(value: String): this.type             = { this.filename = value; this }
  def fullName(value: String): this.type             = { this.fullName = value; this }
  def hash(value: Option[String]): this.type         = { this.hash = value; this }
  def hash(value: String): this.type                 = { this.hash = Option(value); this }
  def isExternal(value: Boolean): this.type          = { this.isExternal = value; this }
  def lineNumber(value: Int): this.type              = { this.lineNumber = Option(value); this }
  def lineNumber(value: Option[Int]): this.type      = { this.lineNumber = value; this }
  def lineNumberEnd(value: Int): this.type           = { this.lineNumberEnd = Option(value); this }
  def lineNumberEnd(value: Option[Int]): this.type   = { this.lineNumberEnd = value; this }
  def name(value: String): this.type                 = { this.name = value; this }
  def offset(value: Int): this.type                  = { this.offset = Option(value); this }
  def offset(value: Option[Int]): this.type          = { this.offset = value; this }
  def offsetEnd(value: Int): this.type               = { this.offsetEnd = Option(value); this }
  def offsetEnd(value: Option[Int]): this.type       = { this.offsetEnd = value; this }
  def order(value: Int): this.type                   = { this.order = value; this }
  def signature(value: String): this.type            = { this.signature = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 3, 1)
    interface.countProperty(this, 4, 1)
    interface.countProperty(this, 10, 1)
    interface.countProperty(this, 11, columnNumber.size)
    interface.countProperty(this, 12, columnNumberEnd.size)
    interface.countProperty(this, 21, 1)
    interface.countProperty(this, 22, 1)
    interface.countProperty(this, 23, hash.size)
    interface.countProperty(this, 29, 1)
    interface.countProperty(this, 34, lineNumber.size)
    interface.countProperty(this, 35, lineNumberEnd.size)
    interface.countProperty(this, 39, 1)
    interface.countProperty(this, 41, offset.size)
    interface.countProperty(this, 42, offsetEnd.size)
    interface.countProperty(this, 43, 1)
    interface.countProperty(this, 49, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewMethod
    newInstance.astParentFullName = this.astParentFullName
    newInstance.astParentType = this.astParentType
    newInstance.code = this.code
    newInstance.columnNumber = this.columnNumber
    newInstance.columnNumberEnd = this.columnNumberEnd
    newInstance.filename = this.filename
    newInstance.fullName = this.fullName
    newInstance.hash = this.hash
    newInstance.isExternal = this.isExternal
    newInstance.lineNumber = this.lineNumber
    newInstance.lineNumberEnd = this.lineNumberEnd
    newInstance.name = this.name
    newInstance.offset = this.offset
    newInstance.offsetEnd = this.offsetEnd
    newInstance.order = this.order
    newInstance.signature = this.signature
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0  => "astParentFullName"
      case 1  => "astParentType"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "columnNumberEnd"
      case 5  => "filename"
      case 6  => "fullName"
      case 7  => "hash"
      case 8  => "isExternal"
      case 9  => "lineNumber"
      case 10 => "lineNumberEnd"
      case 11 => "name"
      case 12 => "offset"
      case 13 => "offsetEnd"
      case 14 => "order"
      case 15 => "signature"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.astParentFullName
      case 1  => this.astParentType
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.columnNumberEnd
      case 5  => this.filename
      case 6  => this.fullName
      case 7  => this.hash
      case 8  => this.isExternal
      case 9  => this.lineNumber
      case 10 => this.lineNumberEnd
      case 11 => this.name
      case 12 => this.offset
      case 13 => this.offsetEnd
      case 14 => this.order
      case 15 => this.signature
      case _  => null
    }

  override def productPrefix                = "NewMethod"
  override def productArity                 = 16
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewMethod]
}
