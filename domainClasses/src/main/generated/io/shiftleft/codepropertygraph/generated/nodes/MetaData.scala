package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait MetaDataEMT
    extends AnyRef
    with HasHashEMT
    with HasLanguageEMT
    with HasOverlaysEMT
    with HasRootEMT
    with HasVersionEMT

trait MetaDataBase extends AbstractNode with StaticType[MetaDataEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.hash.foreach { p => res.put("HASH", p) }
    if (("<empty>": String) != this.language) res.put("LANGUAGE", this.language)
    val tmpOverlays = this.overlays; if (tmpOverlays.nonEmpty) res.put("OVERLAYS", tmpOverlays)
    if (("<empty>": String) != this.root) res.put("ROOT", this.root)
    if (("<empty>": String) != this.version) res.put("VERSION", this.version)
    res
  }
}

object MetaData {
  val Label = "META_DATA"
}

/** * NODE PROPERTIES:
  *
  * ▸ Hash (String); Cardinality `ZeroOrOne` (optional); This property contains a hash value in the form of a string.
  * Hashes can be used to summarize data, e.g., to summarize the contents of source files or sub graphs. Such summaries
  * are useful to determine whether code has already been analyzed in incremental analysis pipelines. This property is
  * optional to allow its calculation to be deferred or skipped if the hash is not needed.
  *
  * ▸ Language (String); Cardinality `one` (mandatory with default value `<empty>`); This field indicates which CPG
  * language frontend generated the CPG. Frontend developers may freely choose a value that describes their frontend so
  * long as it is not used by an existing frontend. Reserved values are to date: C, LLVM, GHIDRA, PHP.
  *
  * ▸ Overlays (String); Cardinality `List` (many); The field contains the names of the overlays applied to this CPG, in
  * order of their application. Names are free-form strings, that is, this specification does not dictate them but
  * rather requires tool producers and consumers to communicate them between each other.
  *
  * ▸ Root (String); Cardinality `one` (mandatory with default value `<empty>`); The path to the root directory of the
  * source/binary this CPG is generated from.
  *
  * ▸ Version (String); Cardinality `one` (mandatory with default value `<empty>`); A version, given as a string. Used,
  * for example, in the META_DATA node to indicate which version of the CPG spec this CPG conforms to
  */
class MetaData(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 24, seq_4762)
    with MetaDataBase
    with StaticType[MetaDataEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "hash"
      case 1 => "language"
      case 2 => "overlays"
      case 3 => "root"
      case 4 => "version"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.hash
      case 1 => this.language
      case 2 => this.overlays
      case 3 => this.root
      case 4 => this.version
      case _ => null
    }

  override def productPrefix = "MetaData"
  override def productArity  = 5

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[MetaData]
}
