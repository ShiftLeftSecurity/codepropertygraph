package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait MetaDataEMT
    extends AnyRef
    with HasHashEMT
    with HasLanguageEMT
    with HasOverlaysEMT
    with HasRootEMT
    with HasVersionEMT

trait MetaDataBase extends AbstractNode with StaticType[MetaDataEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
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
  object PropertyNames {

    /** This property contains a hash value in the form of a string. Hashes can be used to summarize data, e.g., to
      * summarize the contents of source files or sub graphs. Such summaries are useful to determine whether code has
      * already been analyzed in incremental analysis pipelines. This property is optional to allow its calculation to
      * be deferred or skipped if the hash is not needed.
      */
    val Hash = "HASH"

    /** This field indicates which CPG language frontend generated the CPG. Frontend developers may freely choose a
      * value that describes their frontend so long as it is not used by an existing frontend. Reserved values are to
      * date: C, LLVM, GHIDRA, PHP.
      */
    val Language = "LANGUAGE"

    /** The field contains the names of the overlays applied to this CPG, in order of their application. Names are
      * free-form strings, that is, this specification does not dictate them but rather requires tool producers and
      * consumers to communicate them between each other.
      */
    val Overlays = "OVERLAYS"

    /** The path to the root directory of the source/binary this CPG is generated from. */
    val Root = "ROOT"

    /** A version, given as a string. Used, for example, in the META_DATA node to indicate which version of the CPG spec
      * this CPG conforms to
      */
    val Version = "VERSION"
  }
  object PropertyKeys {

    /** This property contains a hash value in the form of a string. Hashes can be used to summarize data, e.g., to
      * summarize the contents of source files or sub graphs. Such summaries are useful to determine whether code has
      * already been analyzed in incremental analysis pipelines. This property is optional to allow its calculation to
      * be deferred or skipped if the hash is not needed.
      */
    val Hash = flatgraph.OptionalPropertyKey[String](kind = 23, name = "HASH")

    /** This field indicates which CPG language frontend generated the CPG. Frontend developers may freely choose a
      * value that describes their frontend so long as it is not used by an existing frontend. Reserved values are to
      * date: C, LLVM, GHIDRA, PHP.
      */
    val Language = flatgraph.SinglePropertyKey[String](kind = 33, name = "LANGUAGE", default = "<empty>")

    /** The field contains the names of the overlays applied to this CPG, in order of their application. Names are
      * free-form strings, that is, this specification does not dictate them but rather requires tool producers and
      * consumers to communicate them between each other.
      */
    val Overlays = flatgraph.MultiPropertyKey[String](kind = 44, name = "OVERLAYS")

    /** The path to the root directory of the source/binary this CPG is generated from. */
    val Root = flatgraph.SinglePropertyKey[String](kind = 48, name = "ROOT", default = "<empty>")

    /** A version, given as a string. Used, for example, in the META_DATA node to indicate which version of the CPG spec
      * this CPG conforms to
      */
    val Version = flatgraph.SinglePropertyKey[String](kind = 54, name = "VERSION", default = "<empty>")
  }
  object PropertyDefaults {
    val Language = "<empty>"
    val Root     = "<empty>"
    val Version  = "<empty>"
  }
}

class MetaData(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 25.toShort, seq_4762)
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

object NewMetaData {
  def apply(): NewMetaData                           = new NewMetaData
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()
}
class NewMetaData extends NewNode(25.toShort) with MetaDataBase {
  override type StoredNodeType = MetaData
  override def label: String = "META_DATA"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMetaData.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewMetaData.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var hash: Option[String]                             = None
  var language: String                                 = "<empty>": String
  var overlays: IndexedSeq[String]                     = ArraySeq.empty
  var root: String                                     = "<empty>": String
  var version: String                                  = "<empty>": String
  def hash(value: Option[String]): this.type           = { this.hash = value; this }
  def hash(value: String): this.type                   = { this.hash = Option(value); this }
  def language(value: String): this.type               = { this.language = value; this }
  def overlays(value: IterableOnce[String]): this.type = { this.overlays = value.iterator.to(ArraySeq); this }
  def root(value: String): this.type                   = { this.root = value; this }
  def version(value: String): this.type                = { this.version = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    if (hash.nonEmpty) interface.insertProperty(this, 23, this.hash)
    interface.insertProperty(this, 33, Iterator(this.language))
    if (overlays.nonEmpty) interface.insertProperty(this, 44, this.overlays)
    interface.insertProperty(this, 48, Iterator(this.root))
    interface.insertProperty(this, 54, Iterator(this.version))
  }

  override def copy(): this.type = {
    val newInstance = new NewMetaData
    newInstance.hash = this.hash
    newInstance.language = this.language
    newInstance.overlays = this.overlays
    newInstance.root = this.root
    newInstance.version = this.version
    newInstance.asInstanceOf[this.type]
  }

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

  override def productPrefix                = "NewMetaData"
  override def productArity                 = 5
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewMetaData]
}
