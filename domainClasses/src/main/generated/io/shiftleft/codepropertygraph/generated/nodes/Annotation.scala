package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait AnnotationEMT extends AnyRef with ExpressionEMT with HasFullNameEMT with HasNameEMT

trait AnnotationBase extends AbstractNode with ExpressionBase with StaticType[AnnotationEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if ((-1: Int) != this.argumentIndex) res.put("ARGUMENT_INDEX", this.argumentIndex)
    this.argumentName.foreach { p => res.put("ARGUMENT_NAME", p) }
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.fullName) res.put("FULL_NAME", this.fullName)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object Annotation {
  val Label = "ANNOTATION"
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
  * ▸ FullName (String); Cardinality `one` (mandatory with default value `<empty>`); This is the fully-qualified name of
  * an entity, e.g., the fully-qualified name of a method or type. The details of what constitutes a fully-qualified
  * name are language specific. This field SHOULD be human readable.
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
class Annotation(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 0, seq_4762)
    with AnnotationBase
    with Expression
    with StaticType[AnnotationEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "argumentIndex"
      case 1 => "argumentName"
      case 2 => "code"
      case 3 => "columnNumber"
      case 4 => "fullName"
      case 5 => "lineNumber"
      case 6 => "name"
      case 7 => "offset"
      case 8 => "offsetEnd"
      case 9 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.argumentIndex
      case 1 => this.argumentName
      case 2 => this.code
      case 3 => this.columnNumber
      case 4 => this.fullName
      case 5 => this.lineNumber
      case 6 => this.name
      case 7 => this.offset
      case 8 => this.offsetEnd
      case 9 => this.order
      case _ => null
    }

  override def productPrefix = "Annotation"
  override def productArity  = 10

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Annotation]
}
