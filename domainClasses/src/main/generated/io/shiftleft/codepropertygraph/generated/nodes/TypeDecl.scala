package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait TypeDeclEMT
    extends AnyRef
    with AstNodeEMT
    with HasAliasTypeFullNameEMT
    with HasAstParentFullNameEMT
    with HasAstParentTypeEMT
    with HasFilenameEMT
    with HasFullNameEMT
    with HasGenericSignatureEMT
    with HasInheritsFromTypeFullNameEMT
    with HasIsExternalEMT
    with HasNameEMT

trait TypeDeclBase extends AbstractNode with AstNodeBase with StaticType[TypeDeclEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.aliasTypeFullName.foreach { p => res.put("ALIAS_TYPE_FULL_NAME", p) }
    if (("<empty>": String) != this.astParentFullName) res.put("AST_PARENT_FULL_NAME", this.astParentFullName)
    if (("<empty>": String) != this.astParentType) res.put("AST_PARENT_TYPE", this.astParentType)
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.filename) res.put("FILENAME", this.filename)
    if (("<empty>": String) != this.fullName) res.put("FULL_NAME", this.fullName)
    if (("<empty>": String) != this.genericSignature) res.put("GENERIC_SIGNATURE", this.genericSignature)
    val tmpInheritsFromTypeFullName = this.inheritsFromTypeFullName;
    if (tmpInheritsFromTypeFullName.nonEmpty) res.put("INHERITS_FROM_TYPE_FULL_NAME", tmpInheritsFromTypeFullName)
    if ((false: Boolean) != this.isExternal) res.put("IS_EXTERNAL", this.isExternal)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object TypeDecl {
  val Label = "TYPE_DECL"
}

/** * NODE PROPERTIES:
  *
  * ▸ AliasTypeFullName (String); Cardinality `ZeroOrOne` (optional); This property holds the fully qualified name of
  * the type that the node is a type alias of.
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
  * ▸ InheritsFromTypeFullName (String); Cardinality `List` (many); The static types a TYPE_DECL inherits from. This
  * property is matched against the FULL_NAME of TYPE nodes and thus it is required to have at least one TYPE node for
  * each TYPE_FULL_NAME
  *
  * ▸ IsExternal (Boolean); Cardinality `one` (mandatory with default value `false`); Indicates that the construct
  * (METHOD or TYPE_DECL) is external, that is, it is referenced but not defined in the code (applies both to insular
  * parsing and to library functions where we have header files only)
  *
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
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
  */
class TypeDecl(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 39, seq_4762)
    with TypeDeclBase
    with AstNode
    with StaticType[TypeDeclEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "aliasTypeFullName"
      case 1  => "astParentFullName"
      case 2  => "astParentType"
      case 3  => "code"
      case 4  => "columnNumber"
      case 5  => "filename"
      case 6  => "fullName"
      case 7  => "genericSignature"
      case 8  => "inheritsFromTypeFullName"
      case 9  => "isExternal"
      case 10 => "lineNumber"
      case 11 => "name"
      case 12 => "offset"
      case 13 => "offsetEnd"
      case 14 => "order"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.aliasTypeFullName
      case 1  => this.astParentFullName
      case 2  => this.astParentType
      case 3  => this.code
      case 4  => this.columnNumber
      case 5  => this.filename
      case 6  => this.fullName
      case 7  => this.genericSignature
      case 8  => this.inheritsFromTypeFullName
      case 9  => this.isExternal
      case 10 => this.lineNumber
      case 11 => this.name
      case 12 => this.offset
      case 13 => this.offsetEnd
      case 14 => this.order
      case _  => null
    }

  override def productPrefix = "TypeDecl"
  override def productArity  = 15

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TypeDecl]
}
