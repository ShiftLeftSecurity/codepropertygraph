package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait DependencyEMT extends AnyRef with HasDependencyGroupIdEMT with HasNameEMT with HasVersionEMT

trait DependencyBase extends AbstractNode with StaticType[DependencyEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    this.dependencyGroupId.foreach { p => res.put("DEPENDENCY_GROUP_ID", p) }
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    if (("<empty>": String) != this.version) res.put("VERSION", this.version)
    res
  }
}

object Dependency {
  val Label = "DEPENDENCY"
  object PropertyNames {

    /** The group ID for a dependency */
    val DependencyGroupId = "DEPENDENCY_GROUP_ID"

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = "NAME"

    /** A version, given as a string. Used, for example, in the META_DATA node to indicate which version of the CPG spec
      * this CPG conforms to
      */
    val Version = "VERSION"
  }
  object Properties {

    /** The group ID for a dependency */
    val DependencyGroupId = flatgraph.OptionalPropertyKey[String](kind = 16, name = "DEPENDENCY_GROUP_ID")

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = flatgraph.SinglePropertyKey[String](kind = 40, name = "NAME", default = "<empty>")

    /** A version, given as a string. Used, for example, in the META_DATA node to indicate which version of the CPG spec
      * this CPG conforms to
      */
    val Version = flatgraph.SinglePropertyKey[String](kind = 55, name = "VERSION", default = "<empty>")
  }
  object PropertyDefaults {
    val Name    = "<empty>"
    val Version = "<empty>"
  }
}

class Dependency(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 12.toShort, seq_4762)
    with DependencyBase
    with StaticType[DependencyEMT] {

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

  override def productPrefix = "Dependency"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Dependency]
}

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

class NewDependency extends NewNode(12.toShort) with DependencyBase {
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
    interface.countProperty(this, 16, dependencyGroupId.size)
    interface.countProperty(this, 40, 1)
    interface.countProperty(this, 55, 1)
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
