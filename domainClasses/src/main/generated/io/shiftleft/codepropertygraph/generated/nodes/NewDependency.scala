package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

object NewDependency {
  def apply(): NewDependency                         = new NewDependency
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map("IMPORTS" -> Set("IMPORT"))

  object InsertionHelpers {
    object NewNodeInserter_Dependency_dependencyGroupId extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewDependency =>
              generated.dependencyGroupId match {
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
    object NewNodeInserter_Dependency_name extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewDependency =>
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
    object NewNodeInserter_Dependency_version extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewDependency =>
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

class NewDependency extends NewNode(nodeKind = 12) with DependencyBase {
  override type StoredNodeType = Dependency
  override def label: String = "DEPENDENCY"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewDependency.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewDependency.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var dependencyGroupId: Option[String]                   = None
  var name: String                                        = "<empty>": String
  var version: String                                     = "<empty>": String
  def dependencyGroupId(value: Option[String]): this.type = { this.dependencyGroupId = value; this }
  def dependencyGroupId(value: String): this.type         = { this.dependencyGroupId = Option(value); this }
  def name(value: String): this.type                      = { this.name = value; this }
  def version(value: String): this.type                   = { this.version = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 13, dependencyGroupId.size)
    interface.countProperty(this, 37, 1)
    interface.countProperty(this, 49, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewDependency
    newInstance.dependencyGroupId = this.dependencyGroupId
    newInstance.name = this.name
    newInstance.version = this.version
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "dependencyGroupId"
      case 1 => "name"
      case 2 => "version"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.dependencyGroupId
      case 1 => this.name
      case 2 => this.version
      case _ => null
    }

  override def productPrefix                = "NewDependency"
  override def productArity                 = 3
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewDependency]
}
