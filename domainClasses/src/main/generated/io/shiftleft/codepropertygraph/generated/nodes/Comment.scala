package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait CommentEMT extends AnyRef with AstNodeEMT with HasFilenameEMT

trait CommentBase extends AbstractNode with AstNodeBase with StaticType[CommentEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    if (("<empty>": String) != this.filename) res.put("FILENAME", this.filename)
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object Comment {
  val Label = "COMMENT"
}

/** * NODE PROPERTIES:
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
class Comment(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 9, seq_4762)
    with CommentBase
    with AstNode
    with StaticType[CommentEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "code"
      case 1 => "columnNumber"
      case 2 => "filename"
      case 3 => "lineNumber"
      case 4 => "offset"
      case 5 => "offsetEnd"
      case 6 => "order"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.code
      case 1 => this.columnNumber
      case 2 => this.filename
      case 3 => this.lineNumber
      case 4 => this.offset
      case 5 => this.offsetEnd
      case 6 => this.order
      case _ => null
    }

  override def productPrefix = "Comment"
  override def productArity  = 7

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Comment]
}
