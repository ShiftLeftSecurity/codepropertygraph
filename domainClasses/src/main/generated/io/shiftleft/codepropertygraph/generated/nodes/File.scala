package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait FileEMT extends AnyRef with AstNodeEMT with HasContentEMT with HasHashEMT with HasNameEMT

trait FileBase extends AbstractNode with AstNodeBase with StaticType[FileEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.content) res.put("CONTENT", this.content)
    this.hash.foreach { p => res.put("HASH", p) }
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object File {
  val Label = "FILE"
}

/** * NODE PROPERTIES:
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ Content (String); Cardinality `one` (mandatory with default value `<empty>`); Certain files, e.g., configuration
  * files, may be included in the CPG as-is. For such files, the `CONTENT` field contains the files content.
  *
  * ▸ Hash (String); Cardinality `ZeroOrOne` (optional); This property contains a hash value in the form of a string.
  * Hashes can be used to summarize data, e.g., to summarize the contents of source files or sub graphs. Such summaries
  * are useful to determine whether code has already been analyzed in incremental analysis pipelines. This property is
  * optional to allow its calculation to be deferred or skipped if the hash is not needed.
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
class File(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 14, seq_4762)
    with FileBase
    with AstNode
    with StaticType[FileEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "code"
      case 1 => "columnNumber"
      case 2 => "content"
      case 3 => "hash"
      case 4 => "lineNumber"
      case 5 => "name"
      case 6 => "offset"
      case 7 => "offsetEnd"
      case 8 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.code
      case 1 => this.columnNumber
      case 2 => this.content
      case 3 => this.hash
      case 4 => this.lineNumber
      case 5 => this.name
      case 6 => this.offset
      case 7 => this.offsetEnd
      case 8 => this.order
      case _ => null
    }

  override def productPrefix = "File"
  override def productArity  = 9

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[File]
}
