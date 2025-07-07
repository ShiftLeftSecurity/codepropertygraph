package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait ImportEMT
    extends AnyRef
    with AstNodeEMT
    with HasExplicitAsEMT
    with HasImportedAsEMT
    with HasImportedEntityEMT
    with HasIsExplicitEMT
    with HasIsWildcardEMT

trait ImportBase extends AbstractNode with AstNodeBase with StaticType[ImportEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.code) res.put("CODE", this.code)
    this.columnNumber.foreach { p => res.put("COLUMN_NUMBER", p) }
    this.explicitAs.foreach { p => res.put("EXPLICIT_AS", p) }
    this.importedAs.foreach { p => res.put("IMPORTED_AS", p) }
    this.importedEntity.foreach { p => res.put("IMPORTED_ENTITY", p) }
    this.isExplicit.foreach { p => res.put("IS_EXPLICIT", p) }
    this.isWildcard.foreach { p => res.put("IS_WILDCARD", p) }
    this.lineNumber.foreach { p => res.put("LINE_NUMBER", p) }
    this.offset.foreach { p => res.put("OFFSET", p) }
    this.offsetEnd.foreach { p => res.put("OFFSET_END", p) }
    if ((-1: Int) != this.order) res.put("ORDER", this.order)
    res
  }
}

object Import {
  val Label = "IMPORT"
}

/** * NODE PROPERTIES:
  *
  * ▸ Code (String); Cardinality `one` (mandatory with default value `<empty>`); This field holds the code snippet that
  * the node represents.
  *
  * ▸ ColumnNumber (Int); Cardinality `ZeroOrOne` (optional); This optional fields provides the column number of the
  * program construct represented by the node.
  *
  * ▸ ExplicitAs (Boolean); Cardinality `ZeroOrOne` (optional); Specifies whether the IMPORTED_AS property was
  * explicitly present in the code. For languages like Java which do not allow a renaming during import this is always
  * false. For e.g. Kotlin it depends on the existence of the "as" keyword.
  *
  * ▸ ImportedAs (String); Cardinality `ZeroOrOne` (optional); The identifier under which the import can be accessed in
  * the importing context. For a Java import this is always identical to the class name. But e.g. for a Kotlin import
  * like "import java.nio.ByteBuffer as BBuffer" this would be "BBuffer". This property is ignored if IS_WILDCARD is
  * true.
  *
  * ▸ ImportedEntity (String); Cardinality `ZeroOrOne` (optional); The identifying string of the imported entity. For a
  * Java import like "import java.nio.ByteBuffer;" this would be "java.nio.ByteBuffer".
  *
  * ▸ IsExplicit (Boolean); Cardinality `ZeroOrOne` (optional); Specifies whether this is an explicit import. Most
  * languages have implicit default imports of some standard library elements and this flag is used to distinguish those
  * from explicit imports found in the code base.
  *
  * ▸ IsWildcard (Boolean); Cardinality `ZeroOrOne` (optional); Specifies whether this is a wildcard import. For a Java
  * import like "import java.nio.*;" IS_WILDCARD would be "true" and IMPORTED_ENTITY would be "java.nio". For wildcard
  * imports the IMPORTED_AS property is ignored.
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
class Import(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 17, seq_4762)
    with ImportBase
    with AstNode
    with StaticType[ImportEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0  => "code"
      case 1  => "columnNumber"
      case 2  => "explicitAs"
      case 3  => "importedAs"
      case 4  => "importedEntity"
      case 5  => "isExplicit"
      case 6  => "isWildcard"
      case 7  => "lineNumber"
      case 8  => "offset"
      case 9  => "offsetEnd"
      case 10 => "order"
      case _  => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0  => this.code
      case 1  => this.columnNumber
      case 2  => this.explicitAs
      case 3  => this.importedAs
      case 4  => this.importedEntity
      case 5  => this.isExplicit
      case 6  => this.isWildcard
      case 7  => this.lineNumber
      case 8  => this.offset
      case 9  => this.offsetEnd
      case 10 => this.order
      case _  => null
    }

  override def productPrefix = "Import"
  override def productArity  = 11

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Import]
}
