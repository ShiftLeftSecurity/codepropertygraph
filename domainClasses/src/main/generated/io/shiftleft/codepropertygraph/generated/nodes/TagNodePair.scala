package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait TagNodePairEMT extends AnyRef

trait TagNodePairBase extends AbstractNode with StaticType[TagNodePairEMT] {
  def node: AbstractNode
  def tag: TagBase
  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res = new java.util.HashMap[String, Any]()
    res.put("node", this.node)
    res.put("tag", this.tag)
    res
  }
}

object TagNodePair {
  val Label = "TAG_NODE_PAIR"
  object PropertyNames {
    val Node = "node"
    val Tag  = "tag"
  }
  object PropertyKeys     {}
  object PropertyDefaults {}
}

class TagNodePair(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 36.toShort, seq_4762)
    with TagNodePairBase
    with StaticType[TagNodePairEMT] {
  def node: StoredNode = flatgraph.Accessors.getNodePropertySingle(graph, nodeKind, 57, seq, null: StoredNode)
  def tag: Tag         = flatgraph.Accessors.getNodePropertySingle(graph, nodeKind, 58, seq, null: Tag)

  override def productElementName(n: Int): String =
    n match {
      case 0 => "node"
      case 1 => "tag"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.node
      case 1 => this.tag
      case _ => null
    }

  override def productPrefix = "TagNodePair"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[TagNodePair]
}

object NewTagNodePair {
  def apply(): NewTagNodePair                        = new NewTagNodePair
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()
}
class NewTagNodePair extends NewNode(36.toShort) with TagNodePairBase {
  override type StoredNodeType = TagNodePair
  override def label: String = "TAG_NODE_PAIR"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTagNodePair.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewTagNodePair.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var node: AbstractNode                   = null
  var tag: TagBase                         = null
  def node(value: AbstractNode): this.type = { this.node = value; this }
  def tag(value: TagBase): this.type       = { this.tag = value; this }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    interface.insertProperty(this, 57, Iterator(this.node))
    interface.insertProperty(this, 58, Iterator(this.tag))
  }

  override def copy(): this.type = {
    val newInstance = new NewTagNodePair
    newInstance.node = this.node
    newInstance.tag = this.tag
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "node"
      case 1 => "tag"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.node
      case 1 => this.tag
      case _ => null
    }

  override def productPrefix                = "NewTagNodePair"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewTagNodePair]
}
