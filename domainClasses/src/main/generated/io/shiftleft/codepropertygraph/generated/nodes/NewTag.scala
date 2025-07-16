package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewTag {
  def apply(): NewTag                                = new NewTag
  private val outNeighbors: Map[String, Set[String]] = Map("TAGGED_BY" -> Set("TAG"))
  private val inNeighbors: Map[String, Set[String]] = Map(
    "TAGGED_BY" -> Set(
      "BLOCK",
      "CALL",
      "CONTROL_STRUCTURE",
      "FIELD_IDENTIFIER",
      "FILE",
      "IDENTIFIER",
      "IMPORT",
      "JUMP_TARGET",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "RETURN",
      "TAG",
      "TEMPLATE_DOM",
      "TYPE_DECL",
      "TYPE_REF",
      "UNKNOWN"
    )
  )

  object InsertionHelpers {
    object NewNodeInserter_Tag_name extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewTag =>
              dstCast(offset) = generated.name
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Tag_value extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewTag =>
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

class NewTag extends NewNode(nodeKind = 34) with TagBase {
  override type StoredNodeType = Tag
  override def label: String = "TAG"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTag.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTag.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var name: String                    = "<empty>": String
  var value: String                   = "": String
  def name(value: String): this.type  = { this.name = value; this }
  def value(value: String): this.type = { this.value = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 37, 1)
    interface.countProperty(this, 49, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewTag
    newInstance.name = this.name
    newInstance.value = this.value
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "name"
      case 1 => "value"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.name
      case 1 => this.value
      case _ => null
    }

  override def productPrefix                = "NewTag"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewTag]
}
