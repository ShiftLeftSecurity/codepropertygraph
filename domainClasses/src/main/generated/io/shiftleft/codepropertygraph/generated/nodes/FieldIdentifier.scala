package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait FieldIdentifierEMT extends AnyRef with ExpressionEMT with HasCanonicalNameEMT

trait FieldIdentifierBase extends AbstractNode with ExpressionBase with StaticType[FieldIdentifierEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if ((-1: Int) != this.argumentIndex) res.put("ARGUMENT_INDEX", this.argumentIndex)
    this.argumentLabel.foreach { p => res.put("ARGUMENT_LABEL", p) }
    this.argumentName.foreach { p => res.put("ARGUMENT_NAME", p) }
    if (("<empty>": String) != this.canonicalName) res.put("CANONICAL_NAME", this.canonicalName)
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object FieldIdentifier {
  val Label = "FIELD_IDENTIFIER"
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
  * ▸ ArgumentLabel (String); Cardinality `ZeroOrOne` (optional); This field is used to keep track of the argument label
  * for languages that support them, such as Swift. It is used in addition to `ARGUMENT_INDEX` and can be used to
  * reconstruct the original call syntax more faithfully. For example, in Swift, a method call may look like
  * `foo(arg1: 42, arg2: "hello")` where `arg1` and `arg2` are argument labels. In this case, the `ARGUMENT_LABEL` field
  * for the first argument would be set to `arg1` and for the second argument it would be set to `arg2`. Contrary to the
  * `ARGUMENT_NAME` the label should not be expected to match the name of any parameter, and is not needed for dataflow
  * purposes at all.
  *
  * ▸ ArgumentName (String); Cardinality `ZeroOrOne` (optional); For calls involving named parameters, the
  * `ARGUMENT_NAME` field holds the name of the parameter initialized by the expression. For all other calls, this field
  * is unset. Note that the `ARGUMENT_NAME` should be an exact match of the NAME of a METHOD_PARAMETER_{IN,OUT}. It
  * overrides ARGUMENT_INDEX for dataflow purposes.
  *
  * ▸ CanonicalName (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the canonical
  * name of a `FIELD_IDENTIFIER`. It is typically identical to the CODE field, but canonicalized according to source
  * language semantics. Human readable names are preferable. `FIELD_IDENTIFIER` nodes must share identical
  * `CANONICAL_NAME` if and only if they alias, e.g., in C-style unions (if the aliasing relationship is unknown or
  * there are partial overlaps, then one must make a reasonable guess, and trade off between false negatives and false
  * positives).
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ LineNumber (Int); Cardinality `ZeroOrOne` (optional); This optional field provides the line number of the program
  * construct represented by the node.
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
class FieldIdentifier(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 13, seq_4762)
    with FieldIdentifierBase
    with Expression
    with StaticType[FieldIdentifierEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "argumentIndex"
      case 1 => "argumentLabel"
      case 2 => "argumentName"
      case 3 => "canonicalName"
      case 4 => "code"
      case 5 => "columnNumber"
      case 6 => "lineNumber"
      case 7 => "offset"
      case 8 => "offsetEnd"
      case 9 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.argumentIndex
      case 1 => this.argumentLabel
      case 2 => this.argumentName
      case 3 => this.canonicalName
      case 4 => this.code
      case 5 => this.columnNumber
      case 6 => this.lineNumber
      case 7 => this.offset
      case 8 => this.offsetEnd
      case 9 => this.order
      case _ => null
    }

  override def productPrefix = "FieldIdentifier"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[FieldIdentifier]
}
