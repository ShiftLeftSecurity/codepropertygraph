package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait TagNodePairEMT extends AnyRef

trait TagNodePairBase extends AbstractNode with StaticType[TagNodePairEMT] {
  def node: AbstractNode
  def tag: TagBase
  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res = new java.util.HashMap[String, Any]()
    res.put("node", this.node)
    res.put("tag", this.tag)
    res
  }
}

object TagNodePair {
  val Label = "TAG_NODE_PAIR"
}

/** * CONTAINED NODES:
  *
  * ▸ node (Anynode); Cardinality `one` (mandatory with default value `null`)
  *
  * ▸ tag (Tag); Cardinality `one` (mandatory with default value `null`)
  */
class TagNodePair(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 35, seq_4762)
    with TagNodePairBase
    with StaticType[TagNodePairEMT] {
  def node: StoredNode = flatgraph.Accessors.getNodePropertySingle(
    graph,
    nodeKind = nodeKind,
    propertyKind = 54,
    seq = seq,
    default = null: StoredNode
  )
  def tag: Tag = flatgraph.Accessors.getNodePropertySingle(
    graph,
    nodeKind = nodeKind,
    propertyKind = 55,
    seq = seq,
    default = null: Tag
  )

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
