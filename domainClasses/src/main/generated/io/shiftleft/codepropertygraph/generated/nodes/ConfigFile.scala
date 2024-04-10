package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object ConfigFile {
  def apply(graph: Graph, id: Long) = new ConfigFile(graph, id)

  val Label = "CONFIG_FILE"

  object PropertyNames {
    val Content                          = "CONTENT"
    val Name                             = "NAME"
    val all: Set[String]                 = Set(Content, Name)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Content = new overflowdb.PropertyKey[String]("CONTENT")
    val Name    = new overflowdb.PropertyKey[String]("NAME")

  }

  object PropertyDefaults {
    val Content = "<empty>"
    val Name    = "<empty>"
  }

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[ConfigFileDb] {
    override val forLabel = ConfigFile.Label

    override def createNode(ref: NodeRef[ConfigFileDb]) =
      new ConfigFileDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = ConfigFile(graph, id)
  }
}

trait ConfigFileBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def content: String
  def name: String

}

class ConfigFile(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[ConfigFileDb](graph_4762, id_4762)
    with ConfigFileBase
    with StoredNode {
  override def content: String = get().content
  override def name: String    = get().name
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "CONTENT" => ConfigFile.PropertyDefaults.Content
      case "NAME"    => ConfigFile.PropertyDefaults.Name
      case _         => super.propertyDefaultValue(propertyKey)
    }
  }

  // In view of https://github.com/scala/bug/issues/4762 it is advisable to use different variable names in
  // patterns like `class Base(x:Int)` and `class Derived(x:Int) extends Base(x)`.
  // This must become `class Derived(x_4762:Int) extends Base(x_4762)`.
  // Otherwise, it is very hard to figure out whether uses of the identifier `x` refer to the base class x
  // or the derived class x.
  // When using that pattern, the class parameter `x_47672` should only be used in the `extends Base(x_4762)`
  // clause and nowhere else. Otherwise, the compiler may well decide that this is not just a constructor
  // parameter but also a field of the class, and we end up with two `x` fields. At best, this wastes memory;
  // at worst both fields go out-of-sync for hard-to-debug correctness bugs.

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = get().fromNewNode(newNode, mapping)
  override def canEqual(that: Any): Boolean                                        = get.canEqual(that)
  override def label: String = {
    ConfigFile.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "content"
      case 2 => "name"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => content
      case 2 => name
    }

  override def productPrefix = "ConfigFile"
  override def productArity  = 3
}

class ConfigFileDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with ConfigFileBase {

  override def layoutInformation: NodeLayoutInformation = ConfigFile.layoutInformation

  private var _content: String = ConfigFile.PropertyDefaults.Content
  def content: String          = _content
  private var _name: String    = ConfigFile.PropertyDefaults.Name
  def name: String             = _name

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("CONTENT", content)
    properties.put("NAME", name)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == content)) { properties.put("CONTENT", content) }
    if (!(("<empty>") == name)) { properties.put("NAME", name) }

    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    ConfigFile.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "content"
      case 2 => "name"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => content
      case 2 => name
    }

  override def productPrefix = "ConfigFile"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[ConfigFileDb]

  override def property(key: String): Any = {
    key match {
      case "CONTENT" => this._content
      case "NAME"    => this._name

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "CONTENT" => this._content = value.asInstanceOf[String]
      case "NAME"    => this._name = value.asInstanceOf[String]

      case _ => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
    }
  }

  override def removeSpecificProperty(key: String): Unit =
    this.updateSpecificProperty(key, null)

  override def _initializeFromDetached(
    data: overflowdb.DetachedNodeData,
    mapper: java.util.function.Function[overflowdb.DetachedNodeData, Node]
  ) =
    fromNewNode(data.asInstanceOf[NewNode], nn => mapper.apply(nn).asInstanceOf[StoredNode])

  override def fromNewNode(newNode: NewNode, mapping: NewNode => StoredNode): Unit = {
    this._content = newNode.asInstanceOf[NewConfigFile].content
    this._name = newNode.asInstanceOf[NewConfigFile].name

  }

}
