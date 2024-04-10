package io.shiftleft.codepropertygraph.generated.nodes

import overflowdb._
import scala.jdk.CollectionConverters._

object TagNodePair {
  def apply(graph: Graph, id: Long) = new TagNodePair(graph, id)

  val Label = "TAG_NODE_PAIR"

  object PropertyNames {
    val Node                             = "node"
    val Tag                              = "tag"
    val all: Set[String]                 = Set(Node, Tag)
    val allAsJava: java.util.Set[String] = all.asJava
  }

  object Properties {

    val Node = new overflowdb.PropertyKey[StoredNode]("node")
    val Tag  = new overflowdb.PropertyKey[Tag]("tag")
  }

  object PropertyDefaults {}

  val layoutInformation = new NodeLayoutInformation(Label, PropertyNames.allAsJava, List().asJava, List().asJava)

  object Edges {
    val Out: Array[String] = Array()
    val In: Array[String]  = Array()
  }

  val factory = new NodeFactory[TagNodePairDb] {
    override val forLabel = TagNodePair.Label

    override def createNode(ref: NodeRef[TagNodePairDb]) =
      new TagNodePairDb(ref.asInstanceOf[NodeRef[NodeDb]])

    override def createNodeRef(graph: Graph, id: Long) = TagNodePair(graph, id)
  }
}

trait TagNodePairBase extends AbstractNode {
  def asStored: StoredNode = this.asInstanceOf[StoredNode]

  def node: AbstractNode
  def tag: TagBase
}

class TagNodePair(graph_4762: Graph, id_4762: Long /*cf https://github.com/scala/bug/issues/4762 */ )
    extends NodeRef[TagNodePairDb](graph_4762, id_4762)
    with TagNodePairBase
    with StoredNode {

  override def propertyDefaultValue(propertyKey: String) = {
    propertyKey match {

      case _ => super.propertyDefaultValue(propertyKey)
    }
  }

  def node: StoredNode = get().node

  def tag: Tag = get().tag

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
    TagNodePair.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "node"
      case 2 => "tag"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => node
      case 2 => tag
    }

  override def productPrefix = "TagNodePair"
  override def productArity  = 3
}

class TagNodePairDb(ref: NodeRef[NodeDb]) extends NodeDb(ref) with StoredNode with TagNodePairBase {

  override def layoutInformation: NodeLayoutInformation = TagNodePair.layoutInformation

  private var _node: StoredNode = null
  def node: StoredNode          = this._node

  private var _tag: Tag = null
  def tag: Tag          = this._tag

  /** faster than the default implementation */
  override def propertiesMap: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]

    properties.put("node", this._node)
    properties.put("tag", this._tag)
    properties
  }

  /** faster than the default implementation */
  override def propertiesMapForStorage: java.util.Map[String, Any] = {
    val properties = new java.util.HashMap[String, Any]

    if (!((null) == this._node)) { properties.put("node", this._node) }
    if (!((null) == this._tag)) { properties.put("tag", this._tag) }
    properties
  }

  import overflowdb.traversal._

  override def label: String = {
    TagNodePair.Label
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "id"
      case 1 => "node"
      case 2 => "tag"
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => id
      case 1 => node
      case 2 => tag
    }

  override def productPrefix = "TagNodePair"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagNodePairDb]

  override def property(key: String): Any = {
    key match {

      case "node" => this._node
      case "tag"  => this._tag
      case _      => null
    }
  }

  override protected def updateSpecificProperty(key: String, value: Object): Unit = {
    key match {

      case "node" => this._node = value.asInstanceOf[StoredNode]
      case "tag"  => this._tag = value.asInstanceOf[Tag]
      case _      => PropertyErrorRegister.logPropertyErrorIfFirst(getClass, key)
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

    this._node = newNode.asInstanceOf[NewTagNodePair].node match {
      case null                => null
      case newNode: NewNode    => mapping(newNode).asInstanceOf[StoredNode]
      case oldNode: StoredNode => oldNode.asInstanceOf[StoredNode]
      case _                   => throw new MatchError("unreachable")
    }
    this._tag = newNode.asInstanceOf[NewTagNodePair].tag match {
      case null                => null
      case newNode: NewNode    => mapping(newNode).asInstanceOf[Tag]
      case oldNode: StoredNode => oldNode.asInstanceOf[Tag]
      case _                   => throw new MatchError("unreachable")
    }

  }

}
