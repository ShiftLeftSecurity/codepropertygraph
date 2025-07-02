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

/** Node properties:
  *   - Hash
  *   - Language
  *   - Overlays
  *   - Root
  *   - Version
  */
class MetaData(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 24.toShort, seq_4762)
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

  object InsertionHelpers {
    object NewNodeInserter_MetaData_hash extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMetaData =>
              generated.hash match {
                case Some(item) =>
                  dstCast(offset) = item
                  offset += 1
                case _ =>
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_MetaData_language extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMetaData =>
              dstCast(offset) = generated.language
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_MetaData_overlays extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMetaData =>
              for (item <- generated.overlays) {
                dstCast(offset) = item
                offset += 1
              }
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_MetaData_root extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMetaData =>
              dstCast(offset) = generated.root
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_MetaData_version extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[String]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewMetaData =>
              dstCast(offset) = generated.version
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
  }
}

class NewMetaData extends NewNode(24.toShort) with MetaDataBase {
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
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 23, hash.size)
    interface.countProperty(this, 33, 1)
    interface.countProperty(this, 42, overlays.size)
    interface.countProperty(this, 45, 1)
    interface.countProperty(this, 50, 1)
  }

  override def copy: this.type = {
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
