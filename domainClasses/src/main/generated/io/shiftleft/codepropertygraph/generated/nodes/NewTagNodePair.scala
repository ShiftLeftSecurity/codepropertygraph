package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewTagNodePair {
  def apply(): NewTagNodePair                        = new NewTagNodePair
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()

  object InsertionHelpers {
    object NewNodeInserter_TagNodePair_node extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[flatgraph.GNode]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewTagNodePair =>
              dstCast(offset) = generated.node match {
                case newV: flatgraph.DNode => newV.storedRef.get; case oldV: flatgraph.GNode => oldV; case null => null
              }
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_TagNodePair_tag extends flatgraph.NewNodePropertyInsertionHelper {
      override def insertNewNodeProperties(
        newNodes: mutable.ArrayBuffer[flatgraph.DNode],
        dst: AnyRef,
        offsets: Array[Int]
      ): Unit = {
        if (newNodes.isEmpty) return
        val dstCast = dst.asInstanceOf[Array[flatgraph.GNode]]
        val seq     = newNodes.head.storedRef.get.seq()
        var offset  = offsets(seq)
        var idx     = 0
        while (idx < newNodes.length) {
          val nn = newNodes(idx)
          nn match {
            case generated: NewTagNodePair =>
              dstCast(offset) = generated.tag match {
                case newV: flatgraph.DNode => newV.storedRef.get; case oldV: flatgraph.GNode => oldV; case null => null
              }
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

class NewTagNodePair extends NewNode(nodeKind = 35) with TagNodePairBase {
  override type StoredNodeType = TagNodePair
  override def label: String = "TAG_NODE_PAIR"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTagNodePair.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTagNodePair.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var node: AbstractNode                   = null
  var tag: TagBase                         = null
  def node(value: AbstractNode): this.type = { this.node = value; this }
  def tag(value: TagBase): this.type       = { this.tag = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 54, 1)
    interface.visitContainedNode(node)
    interface.countProperty(this, 55, 1)
    interface.visitContainedNode(tag)
  }

  override def copy: this.type = {
    val newInstance = new NewTagNodePair
    newInstance.node = this.node
    newInstance.tag = this.tag
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "node"
      case 1 => "tag"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.node
      case 1 => this.tag
      case _ => null
    }

  override def productPrefix                = "NewTagNodePair"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewTagNodePair]
}
