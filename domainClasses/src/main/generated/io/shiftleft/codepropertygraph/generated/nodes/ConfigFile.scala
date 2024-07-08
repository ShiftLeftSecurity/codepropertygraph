package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait ConfigFileEMT extends AnyRef with HasContentEMT with HasNameEMT

trait ConfigFileBase extends AbstractNode with StaticType[ConfigFileEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    if (("<empty>": String) != this.content) res.put("CONTENT", this.content)
    if (("<empty>": String) != this.name) res.put("NAME", this.name)
    res
  }
}

object ConfigFile {
  val Label = "CONFIG_FILE"
  object PropertyNames {

    /** Certain files, e.g., configuration files, may be included in the CPG as-is. For such files, the `CONTENT` field
      * contains the files content.
      */
    val Content = "CONTENT"

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = "NAME"
  }
  object Properties {

    /** Certain files, e.g., configuration files, may be included in the CPG as-is. For such files, the `CONTENT` field
      * contains the files content.
      */
    val Content = flatgraph.SinglePropertyKey[String](kind = 14, name = "CONTENT", default = "<empty>")

    /** Name of represented object, e.g., method name (e.g. "run") */
    val Name = flatgraph.SinglePropertyKey[String](kind = 39, name = "NAME", default = "<empty>")
  }
  object PropertyDefaults {
    val Content = "<empty>"
    val Name    = "<empty>"
  }
}

class ConfigFile(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 10.toShort, seq_4762)
    with ConfigFileBase
    with StaticType[ConfigFileEMT] {

  override def productElementName(n: Int): String =
    n match {
      case 0 => "content"
      case 1 => "name"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.content
      case 1 => this.name
      case _ => null
    }

  override def productPrefix = "ConfigFile"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ConfigFile]
}

object NewConfigFile {
  def apply(): NewConfigFile                         = new NewConfigFile
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()

  object InsertionHelpers {
    object NewNodeInserter_ConfigFile_content extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewConfigFile =>
              dstCast(offset) = generated.content
              offset += 1
            case _ =>
          }
          assert(seq + idx == nn.storedRef.get.seq(), "internal consistency check")
          idx += 1
          offsets(idx + seq) = offset
        }
      }
    }
    object NewNodeInserter_ConfigFile_name extends flatgraph.NewNodePropertyInsertionHelper {
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
            case generated: NewConfigFile =>
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
  }
}

class NewConfigFile extends NewNode(10.toShort) with ConfigFileBase {
  override type StoredNodeType = ConfigFile
  override def label: String = "CONFIG_FILE"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewConfigFile.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewConfigFile.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var content: String                   = "<empty>": String
  var name: String                      = "<empty>": String
  def content(value: String): this.type = { this.content = value; this }
  def name(value: String): this.type    = { this.name = value; this }
  override def countAndVisitProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.countProperty(this, 14, 1)
    interface.countProperty(this, 39, 1)
  }

  override def copy: this.type = {
    val newInstance = new NewConfigFile
    newInstance.content = this.content
    newInstance.name = this.name
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "content"
      case 1 => "name"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.content
      case 1 => this.name
      case _ => null
    }

  override def productPrefix                = "NewConfigFile"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewConfigFile]
}
