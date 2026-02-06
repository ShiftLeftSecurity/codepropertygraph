package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewType {
  def apply(): NewType                               = new NewType
  private val outNeighbors: Map[String, Set[String]] = Map("AST" -> Set("TYPE_ARGUMENT"), "REF" -> Set("TYPE_DECL"))
  private val inNeighbors: Map[String, Set[String]] = Map(
    "ALIAS_OF" -> Set("TYPE_DECL"),
    "EVAL_TYPE" -> Set(
      "ARRAY_INITIALIZER",
      "BLOCK",
      "CALL",
      "CONTROL_STRUCTURE",
      "IDENTIFIER",
      "LITERAL",
      "LOCAL",
      "MEMBER",
      "METHOD_PARAMETER_IN",
      "METHOD_PARAMETER_OUT",
      "METHOD_REF",
      "METHOD_RETURN",
      "TYPE_REF",
      "UNKNOWN"
    ),
    "INHERITS_FROM" -> Set("TYPE_DECL"),
    "REF"           -> Set("TYPE_ARGUMENT")
  )

  object InsertionHelpers {
    object NewNodeInserter_Type_fullName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewType =>
              dstCast(offset) = generated.fullName
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_Type_name extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewType =>
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
    object NewNodeInserter_Type_typeDeclFullName extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewType =>
              dstCast(offset) = generated.typeDeclFullName
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

class NewType extends NewNode(nodeKind = 37) with TypeBase {
  override type StoredNodeType = Type
  override def label: String = "TYPE"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewType.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewType.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var fullName: String                           = "<empty>": String
  var name: String                               = "<empty>": String
  var typeDeclFullName: String                   = "<empty>": String
  def fullName(value: String): this.type         = { this.fullName = value; this }
  def name(value: String): this.type             = { this.name = value; this }
  def typeDeclFullName(value: String): this.type = { this.typeDeclFullName = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 21, 1)
    interface.countProperty(this, 38, 1)
    interface.countProperty(this, 48, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewType
    newInstance.fullName = this.fullName
    newInstance.name = this.name
    newInstance.typeDeclFullName = this.typeDeclFullName
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "fullName"
      case 1 => "name"
      case 2 => "typeDeclFullName"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.fullName
      case 1 => this.name
      case 2 => this.typeDeclFullName
      case _ => null
    }

  override def productPrefix                = "NewType"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewType]
}
