package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait MemberEMT
    extends AnyRef
    with AstNodeEMT
    with DeclarationEMT
    with HasAstParentFullNameEMT
    with HasAstParentTypeEMT
    with HasDynamicTypeHintFullNameEMT
    with HasGenericSignatureEMT
    with HasPossibleTypesEMT
    with HasTypeFullNameEMT

trait MemberBase extends AbstractNode with AstNodeBase with DeclarationBase with StaticType[MemberEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.astParentFullName) res.put("AST_PARENT_FULL_NAME", this.astParentFullName)
    if (("<empty>": String) != this.astParentType) res.put("AST_PARENT_TYPE", this.astParentType)
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    val tmpDynamicTypeHintFullName = this.dynamicTypeHintFullName;
    if (tmpDynamicTypeHintFullName.nonEmpty) res.put("DYNAMIC_TYPE_HINT_FULL_NAME", tmpDynamicTypeHintFullName)
    if (("<empty>": String) != this.genericSignature) res.put("GENERIC_SIGNATURE", this.genericSignature)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    val tmpPossibleTypes = this.possibleTypes;
    if (tmpPossibleTypes.nonEmpty) res.put("POSSIBLE_TYPES", tmpPossibleTypes)
    if (("<empty>": String) != this.typeFullName) res.put("TYPE_FULL_NAME", this.typeFullName)
    res
  }
}

object Member {
  val Label = "MEMBER"
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
  * ▸ DynamicTypeHintFullName (String); Cardinality `List` (many); Type hint for the dynamic type. These are observed to
  * be verifiable at runtime.
  *
  * ▸ GenericSignature (String); Cardinality `one` (mandatory with default value `<empty>`); This field is experimental.
  * It will likely be removed in the future without any notice. It stores type information for generic types and methods
  * as well as type information for members and locals where the type either contains a type parameter reference or an
  * instantiated type reference.
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
  *
  * ▸ PossibleTypes (String); Cardinality `List` (many); Similar to `DYNAMIC_TYPE_HINT_FULL_NAME`, but that this makes
  * no guarantee that types within this property are correct. This property is used to capture observations between node
  * interactions during a 'may-analysis'.
  *
  * ▸ TypeFullName (String); Cardinality `one` (mandatory with default value `<empty>`); This field contains the
  * fully-qualified static type name of the program construct represented by a node. It is the name of an instantiated
  * type, e.g., `java.util.List<Integer>`, rather than `java.util.List[T]`. If the type cannot be determined, this field
  * should be set to the empty string.
  */
class Member(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 23, seq_4762)
    with MemberBase
    with AstNode
    with Declaration
    with StaticType[MemberEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "astParentFullName"
      case 1  => "astParentType"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dynamicTypeHintFullName"
      case 5  => "genericSignature"
      case 6  => "lineNumber"
      case 7  => "name"
      case 8  => "offset"
      case 9  => "offsetEnd"
      case 10 => "order"
      case 11 => "possibleTypes"
      case 12 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.astParentFullName
      case 1  => this.astParentType
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.dynamicTypeHintFullName
      case 5  => this.genericSignature
      case 6  => this.lineNumber
      case 7  => this.name
      case 8  => this.offset
      case 9  => this.offsetEnd
      case 10 => this.order
      case 11 => this.possibleTypes
      case 12 => this.typeFullName
      case _  => null
    }

  override def productPrefix = "Member"
  override def productArity  = 13

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Member]
}
