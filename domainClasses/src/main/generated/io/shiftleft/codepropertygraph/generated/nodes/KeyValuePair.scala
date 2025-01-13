package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait KeyValuePairEMT extends AnyRef with HasKeyEMT with HasValueEMT

trait KeyValuePairBase extends AbstractNode with StaticType[KeyValuePairEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.key) res.put("KEY", this.key)
    if (("": String) != this.value) res.put("VALUE", this.value)
    res
  }
}

object KeyValuePair {
  val Label = "KEY_VALUE_PAIR"
  object PropertyNames {

    /** This property denotes a key of a key-value pair. */
    val Key = "KEY"

    /** This property denotes a string value as used in a key-value pair. */
    val Value = "VALUE"
  }
  object Properties {

    /** This property denotes a key of a key-value pair. */
    val Key = flatgraph.SinglePropertyKey[String](kind = 33, name = "KEY", default = "<empty>")

    /** This property denotes a string value as used in a key-value pair. */
    val Value = flatgraph.SinglePropertyKey[String](kind = 54, name = "VALUE", default = "")
  }
  object PropertyDefaults {
    val Key   = "<empty>"
    val Value = ""
  }
}

class KeyValuePair(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 20.toShort, seq_4762)
    with KeyValuePairBase
    with StaticType[KeyValuePairEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "key"
      case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.key
      case 1 => this.value
      case _ => null
    }

  override def productPrefix = "KeyValuePair"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[KeyValuePair]
}

object NewKeyValuePair {
  def apply(): NewKeyValuePair                       = new NewKeyValuePair
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()

  object InsertionHelpers {
    object NewNodeInserter_KeyValuePair_key extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewKeyValuePair =>
              dstCast(offset) = generated.key
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_KeyValuePair_value extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewKeyValuePair =>
              dstCast(offset) = generated.value
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

class NewKeyValuePair extends NewNode(20.toShort) with KeyValuePairBase {
  override type StoredNodeType = KeyValuePair
  override def label: String = "KEY_VALUE_PAIR"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewKeyValuePair.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewKeyValuePair.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var key: String                     = "<empty>": String
  var value: String                   = "": String
  def key(value: String): this.type   = { this.key = value; this }
  def value(value: String): this.type = { this.value = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 33, 1)
    interface.countProperty(this, 54, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewKeyValuePair
    newInstance.key = this.key
    newInstance.value = this.value
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "key"
      case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.key
      case 1 => this.value
      case _ => null
    }

  override def productPrefix                = "NewKeyValuePair"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewKeyValuePair]
}
