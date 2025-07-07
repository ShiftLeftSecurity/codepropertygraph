package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewFinding {
  def apply(): NewFinding                            = new NewFinding
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()

  object InsertionHelpers {
    object NewNodeInserter_Finding_evidenceDescription extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewFinding =>
              for (item <- generated.evidenceDescription) {
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
    object NewNodeInserter_Finding_evidence extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewFinding =>
              for (item <- generated.evidence) {
                dstCast(offset) = item match {
                  case newV: flatgraph.DNode => newV.storedRef.get; case oldV: flatgraph.GNode => oldV;
                  case null                  => null
                }
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
    object NewNodeInserter_Finding_keyValuePairs extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewFinding =>
              for (item <- generated.keyValuePairs) {
                dstCast(offset) = item match {
                  case newV: flatgraph.DNode => newV.storedRef.get; case oldV: flatgraph.GNode => oldV;
                  case null                  => null
                }
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
  }
}

class NewFinding extends NewNode(nodeKind = 15) with FindingBase {
  override type StoredNodeType = Finding
  override def label: String = "FINDING"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewFinding.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewFinding.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var evidence: IndexedSeq[AbstractNode]                     = ArraySeq.empty
  var evidenceDescription: IndexedSeq[String]                = ArraySeq.empty
  var keyValuePairs: IndexedSeq[KeyValuePairBase]            = ArraySeq.empty
  def evidence(value: IterableOnce[AbstractNode]): this.type = { this.evidence = value.iterator.to(ArraySeq); this }
  def evidenceDescription(value: IterableOnce[String]): this.type = {
    this.evidenceDescription = value.iterator.to(ArraySeq); this
  }
  def keyValuePairs(value: IterableOnce[KeyValuePairBase]): this.type = {
    this.keyValuePairs = value.iterator.to(ArraySeq); this
  }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 18, evidenceDescription.size)
    interface.countProperty(this, 51, evidence.size)
    evidence.foreach(interface.visitContainedNode)
    interface.countProperty(this, 52, keyValuePairs.size)
    keyValuePairs.foreach(interface.visitContainedNode)
  }

  override def copy: this.type = {
    val newInstance = new NewFinding
    newInstance.evidenceDescription = this.evidenceDescription
    newInstance.evidence = this.evidence
    newInstance.keyValuePairs = this.keyValuePairs
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "evidenceDescription"
      case 1 => "evidence"
      case 2 => "keyValuePairs"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.evidenceDescription
      case 1 => this.evidence
      case 2 => this.keyValuePairs
      case _ => null
    }

  override def productPrefix                = "NewFinding"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewFinding]
}
