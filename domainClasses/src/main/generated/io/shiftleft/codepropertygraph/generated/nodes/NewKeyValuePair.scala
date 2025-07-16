package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

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

class NewKeyValuePair extends NewNode(nodeKind = 20) with KeyValuePairBase {
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
    interface.countProperty(this, 31, 1)
    interface.countProperty(this, 49, 1)
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
