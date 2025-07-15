package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewClosureBinding {
  def apply(): NewClosureBinding                     = new NewClosureBinding
  private val outNeighbors: Map[String, Set[String]] = Map("REF" -> Set("LOCAL", "METHOD_PARAMETER_IN"))
  private val inNeighbors: Map[String, Set[String]] =
    Map("CAPTURE" -> Set("METHOD_REF", "TYPE_REF"), "CAPTURED_BY" -> Set("LOCAL", "METHOD_PARAMETER_IN"))

  object InsertionHelpers {
    object NewNodeInserter_ClosureBinding_closureBindingId extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewClosureBinding =>
              generated.closureBindingId match {
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
    object NewNodeInserter_ClosureBinding_evaluationStrategy extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewClosureBinding =>
              dstCast(offset) = generated.evaluationStrategy
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

class NewClosureBinding extends NewNode(nodeKind = 8) with ClosureBindingBase {
  override type StoredNodeType = ClosureBinding
  override def label: String = "CLOSURE_BINDING"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewClosureBinding.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewClosureBinding.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var closureBindingId: Option[String]                   = None
  var evaluationStrategy: String                         = "<empty>": String
  def closureBindingId(value: Option[String]): this.type = { this.closureBindingId = value; this }
  def closureBindingId(value: String): this.type         = { this.closureBindingId = Option(value); this }
  def evaluationStrategy(value: String): this.type       = { this.evaluationStrategy = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 6, closureBindingId.size)
    interface.countProperty(this, 16, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewClosureBinding
    newInstance.closureBindingId = this.closureBindingId
    newInstance.evaluationStrategy = this.evaluationStrategy
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "closureBindingId"
      case 1 => "evaluationStrategy"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.closureBindingId
      case 1 => this.evaluationStrategy
      case _ => null
    }

  override def productPrefix                = "NewClosureBinding"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewClosureBinding]
}
