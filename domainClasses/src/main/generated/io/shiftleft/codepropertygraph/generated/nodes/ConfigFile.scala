package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait ConfigFileEMT extends AnyRef with HasContentEMT with HasNameEMT

trait ConfigFileBase extends AbstractNode with StaticType[ConfigFileEMT] {

  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res = new java.util.HashMap[String, Any]()
    res.put("CONTENT", this.content)
    res.put("NAME", this.name)
    res
  }
}

object ConfigFile {
  val Label = "CONFIG_FILE"
  object PropertyNames {
    val Content = io.shiftleft.codepropertygraph.generated.PropertyNames.CONTENT
    val Name    = io.shiftleft.codepropertygraph.generated.PropertyNames.NAME
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
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.insertProperty(this, 14, Iterator(this.content))
    interface.insertProperty(this, 39, Iterator(this.name))
  }

  override def copy(): this.type = {
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
