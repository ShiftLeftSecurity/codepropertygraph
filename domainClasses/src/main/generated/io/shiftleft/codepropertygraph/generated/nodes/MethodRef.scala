package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait MethodRefEMT
    extends AnyRef
    with ExpressionEMT
    with HasDynamicTypeHintFullNameEMT
    with HasMethodFullNameEMT
    with HasPossibleTypesEMT
    with HasTypeFullNameEMT

trait MethodRefBase extends AbstractNode with ExpressionBase with StaticType[MethodRefEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if ((-1: Int) != this.argumentIndex) res.put("ARGUMENT_INDEX", this.argumentIndex)
    this.argumentName.foreach { p => res.put("ARGUMENT_NAME", p) }
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    val tmpDynamicTypeHintFullName = this.dynamicTypeHintFullName;
    if (tmpDynamicTypeHintFullName.nonEmpty) res.put("DYNAMIC_TYPE_HINT_FULL_NAME", tmpDynamicTypeHintFullName)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.methodFullName) res.put("METHOD_FULL_NAME", this.methodFullName)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    val tmpPossibleTypes = this.possibleTypes;
    if (tmpPossibleTypes.nonEmpty) res.put("POSSIBLE_TYPES", tmpPossibleTypes)
    if (("<empty>": String) != this.typeFullName) res.put("TYPE_FULL_NAME", this.typeFullName)
    res
  }
}

object MethodRef {
  val Label = "METHOD_REF"
}

/** * NODE PROPERTIES:
  *
  * ▸ ArgumentIndex (Int); Cardinality `one` (mandatory with default value `-1`); AST-children of CALL nodes have an
  * argument index, that is used to match call-site arguments with callee parameters. Explicit parameters are numbered
  * from 1 to N, while index 0 is reserved for implicit self / this parameter. CALLs without implicit parameter
  * therefore have arguments starting with index 1. AST-children of BLOCK nodes may have an argument index as well; in
  * this case, the last argument index determines the return expression of a BLOCK expression. If the `PARAMETER_NAME`
  * field is set, then the `ARGUMENT_INDEX` field is ignored. It is suggested to set it to -1.
  *
  * ▸ ArgumentName (String); Cardinality `ZeroOrOne` (optional); For calls involving named parameters, the
  * `ARGUMENT_NAME` field holds the name of the parameter initialized by the expression. For all other calls, this field
  * is unset.
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
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
  *
  * ▸ MethodFullName (String); Cardinality `one` (mandatory with default value `<empty>`); The FULL_NAME of a method.
  * Used to link CALL and METHOD nodes. It is required to have exactly one METHOD node for each METHOD_FULL_NAME
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
class MethodRef(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 28, seq_4762)
    with MethodRefBase
    with Expression
    with StaticType[MethodRefEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "argumentIndex"
      case 1  => "argumentName"
      case 2  => "code"
      case 3  => "columnNumber"
      case 4  => "dynamicTypeHintFullName"
      case 5  => "lineNumber"
      case 6  => "methodFullName"
      case 7  => "offset"
      case 8  => "offsetEnd"
      case 9  => "order"
      case 10 => "possibleTypes"
      case 11 => "typeFullName"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.argumentIndex
      case 1  => this.argumentName
      case 2  => this.code
      case 3  => this.columnNumber
      case 4  => this.dynamicTypeHintFullName
      case 5  => this.lineNumber
      case 6  => this.methodFullName
      case 7  => this.offset
      case 8  => this.offsetEnd
      case 9  => this.order
      case 10 => this.possibleTypes
      case 11 => this.typeFullName
      case _  => null
    }

  override def productPrefix = "MethodRef"
  override def productArity  = 12

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MethodRef]
}
