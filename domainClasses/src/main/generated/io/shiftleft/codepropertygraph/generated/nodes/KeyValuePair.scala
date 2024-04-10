package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object KeyValuePair {
  def apply(graph: Graph, id: Long) = new KeyValuePair(graph, id)

  val Label = "KEY_VALUE_PAIR"

  object PropertyNames {
    val Key                              = "KEY"
    val Value                            = "VALUE"
    val all: Set[String]                 = Set(Key, Value)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {
    val Key   = new overflowdb.PropertyKey[String]("KEY")
    val Value = new overflowdb.PropertyKey[String]("VALUE")

  }

  object PropertyDefaults {
    val Key   = "<empty>"
    val Value = ""
  }

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[KeyValuePairDb] {
    override val forLabel = KeyValuePair.Label

    override def createNode(ref: NodeRef[KeyValuePairDb]) =
      new KeyValuePairDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = KeyValuePair(graph, id)
  }
}

trait KeyValuePairBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def key: String
  def value: String

}

class KeyValuePair(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[KeyValuePairDb](graph_4762, id_4762)
    with KeyValuePairBase
    with StoredNode {
  override def key: String   = get().key
  override def value: String = get().value
  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {
      case "KEY"   => KeyValuePair.PropertyDefaults.Key
      case "VALUE" => KeyValuePair.PropertyDefaults.Value
      case _       => super.propertyDefaultValue(propertyKey)
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
    KeyValuePair.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "key"
      case 2 => "value"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => key
      case 2 => value
    }

  override def productPrefix = "KeyValuePair"
  override def productArity  = 3
}

class KeyValuePairDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with KeyValuePairBase {

  override def layoutInformation: NodeLayoutInformation = KeyValuePair.layoutInformation

  private var _key: String   = KeyValuePair.PropertyDefaults.Key
  def key: String            = _key
  private var _value: String = KeyValuePair.PropertyDefaults.Value
  def value: String          = _value

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    properties.put("KEY", key)
    properties.put("VALUE", value)

    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]
    if (!(("<empty>") == key)) { properties.put("KEY", key) }
    if (!(("") == value)) { properties.put("VALUE", value) }

    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    KeyValuePair.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "key"
      case 2 => "value"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => key
      case 2 => value
    }

  override def productPrefix = "KeyValuePair"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[KeyValuePairDb]

  override def property(key: String): Any = {
    key match {
      case "KEY"   => this._key
      case "VALUE" => this._value

      case _ => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {
      case "KEY"   => this._key = value.asInstanceOf[String]
      case "VALUE" => this._value = value.asInstanceOf[String]

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
    this._key = newNode.asInstanceOf[NewKeyValuePair].key
    this._value = newNode.asInstanceOf[NewKeyValuePair].value

  }

}
