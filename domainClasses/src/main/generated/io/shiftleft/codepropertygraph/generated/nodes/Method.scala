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
    with HasGenericSignatureEMT
    with HasHashEMT
    with HasIsExternalEMT
    with HasLineNumberEndEMT
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
    if (("<empty>": String) != this.genericSignature) res.put("GENERIC_SIGNATURE", this.genericSignature)
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
}

/** * NODE PROPERTIES:
  *
  * ▸ AstParentFullName (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the
  * FULL_NAME of the AST parent of an entity.
  *
  * ▸ AstParentType (String); Cardinality `one` (mandatory with default value `<empty>`); The type of the AST parent.
  * Since this is only used in some parts of the graph, the list does not include all possible parents by intention.
  * Possible parents: METHOD, TYPE_DECL, NAMESPACE_BLOCK.
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ ColumnNumberEnd (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number at
  * which the program construct represented by the node ends.
  *
  * ▸ Filename (String); Cardinality `one` (mandatory with default value `<empty>`); The path of the source file this
  * node was generated from, relative to the root path in the meta data node. This field must be set but may be set to
  * the value `<unknown>` to indicate that no source file can be associated with the node, e.g., because the node
  * represents an entity known to exist because it is referenced, but for which the file that is is declared in is
  * unknown.
  *
  * ▸ FullName (String); Cardinality `one` (mandatory with default value `<empty>`); This is the fully-qualified name of
  * an entity, e.g., the fully-qualified name of a method or type. The details of what constitutes a fully-qualified
  * name are language specific. This field SHOULD be human readable.
  *
  * ▸ GenericSignature (String); Cardinality `one` (mandatory with default value `<empty>`); This field is experimental.
  * It will likely be removed in the future without any notice. It stores type information for generic types and methods
  * as well as type information for members and locals where the type either contains a type parameter reference or an
  * instantiated type reference.
  *
  * ▸ Hash (String); Cardinality `ZeroOrOne` (optional); This property contains a hash value in the form of a string.
  * Hashes can be used to summarize data, e.g., to summarize the contents of source files or sub graphs. Such summaries
  * are useful to determine whether code has already been analyzed in incremental analysis pipelines. This property is
  * optional to allow its calculation to be deferred or skipped if the hash is not needed.
  *
  * ▸ IsExternal (Boolean); Cardinality `one` (mandatory with default value `false`); Indicates that the construct
  * (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the code (applies both to insular
  * parsing and to library functions where we have header files only)
  *
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
  *
  * ▸ LineNumberEnd (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the line number at which
  * the program construct represented by the node ends.
  *
  * ▸ Name (String); Cardinality `one` (mandatory with default value `<empty>`); Name of represented object, e.g.,
  * method name (e.g. "run")
  *
  * ▸ Offset (Int); Cardinality `ZeroOrOne` (optional); Start offset into the CONTENT property of the corresponding FILE
  * node. The offset is such that parts of the content can easily be accessed via
  * `content.substring(offset, offsetEnd)`. This means that the offset must be measured in utf16 encoding (i.e. neither
  * in characters/codeunits nor in byte-offsets into a utf8 encoding). E.g. for METHOD nodes this start offset points to
  * the start of the methods source code in the string holding the source code of the entire file.
  *
  * ▸ OffsetEnd (Int); Cardinality `ZeroOrOne` (optional); End offset (exclusive) into the CONTENT property of the
  * corresponding FILE node. See OFFSET documentation for finer details. E.g. for METHOD nodes this end offset points to
  * the first code position which is not part of the method.
  *
  * ▸ Order (Int); Cardinality `one` (mandatory with default value `-1`); This integer indicates the position of the
  * node among its siblings in the AST. The left-most child has an order of 0.
  *
  * ▸ Signature (String); Cardinality `one` (mandatory with default value ``); The method signature encodes the types of
  * parameters in a string. The string SHOULD be human readable and suitable for differentiating methods with different
  * parameter types sufficiently to allow for resolving of function overloading. The present specification does not
  * enforce a strict format for the signature, that is, it can be chosen by the frontend implementor to fit the source
  * language.
  */
class Method(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 25, seq_4762)
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
      case 7  => "genericSignature"
      case 8  => "hash"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "lineNumberEnd"
      case 12 => "name"
      case 13 => "offset"
      case 14 => "offsetEnd"
      case 15 => "order"
      case 16 => "signature"
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
      case 7  => this.genericSignature
      case 8  => this.hash
      case 9  => this.isExternal
      case 10 => this.lineNumber
      case 11 => this.lineNumberEnd
      case 12 => this.name
      case 13 => this.offset
      case 14 => this.offsetEnd
      case 15 => this.order
      case 16 => this.signature
      case _  => null
    }

  override def productPrefix = "Method"
  override def productArity  = 17

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Method]
}
