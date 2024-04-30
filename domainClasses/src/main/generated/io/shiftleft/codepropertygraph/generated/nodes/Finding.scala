package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.Language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}

trait FindingEMT extends AnyRef

trait FindingBase extends AbstractNode with StaticType[FindingEMT] {
  def evidence: IndexedSeq[AbstractNode]
  def keyValuePairs: IndexedSeq[KeyValuePairBase]
  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.Lang.*
    val res              = new java.util.HashMap[String, Any]()
    val tmpevidence      = this.evidence; if (tmpevidence.nonEmpty) res.put("evidence", tmpevidence)
    val tmpkeyValuePairs = this.keyValuePairs; if (tmpkeyValuePairs.nonEmpty) res.put("keyValuePairs", tmpkeyValuePairs)
    res
  }
}

object Finding {
  val Label = "FINDING"
  object PropertyNames {
    val Evidence      = "evidence"
    val Keyvaluepairs = "keyValuePairs"
  }
  object PropertyKeys     {}
  object PropertyDefaults {}
}

class Finding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 15.toShort, seq_4762)
    with FindingBase
    with StaticType[FindingEMT] {
  def evidence: IndexedSeq[StoredNode] = flatgraph.Accessors.getNodePropertyMulti[StoredNode](graph, nodeKind, 55, seq)
  def keyValuePairs: IndexedSeq[KeyValuePair] =
    flatgraph.Accessors.getNodePropertyMulti[KeyValuePair](graph, nodeKind, 56, seq)

  override def productElementName(n: Int): String =
    n match {
      case 0 => "evidence"
      case 1 => "keyValuePairs"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.evidence
      case 1 => this.keyValuePairs
      case _ => null
    }

  override def productPrefix = "Finding"
  override def productArity  = 2

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Finding]
}

object NewFinding {
  def apply(): NewFinding                            = new NewFinding
  private val outNeighbors: Map[String, Set[String]] = Map()
  private val inNeighbors: Map[String, Set[String]]  = Map()
}
class NewFinding extends NewNode(15.toShort) with FindingBase {
  override type StoredNodeType = Finding
  override def label: String = "FINDING"

  override def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewFinding.outNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }
  override def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean = {
    NewFinding.inNeighbors.getOrElse(edgeLabel, Set.empty).contains(n.label)
  }

  var evidence: IndexedSeq[AbstractNode]                     = ArraySeq.empty
  var keyValuePairs: IndexedSeq[KeyValuePairBase]            = ArraySeq.empty
  def evidence(value: IterableOnce[AbstractNode]): this.type = { this.evidence = value.iterator.to(ArraySeq); this }
  def keyValuePairs(value: IterableOnce[KeyValuePairBase]): this.type = {
    this.keyValuePairs = value.iterator.to(ArraySeq); this
  }
  override def flattenProperties(interface: flatgraph.BatchedUpdateInterface): Unit = {
    if (evidence.nonEmpty) interface.insertProperty(this, 55, this.evidence)
    if (keyValuePairs.nonEmpty) interface.insertProperty(this, 56, this.keyValuePairs)
  }

  override def copy(): this.type = {
    val newInstance = new NewFinding
    newInstance.evidence = this.evidence
    newInstance.keyValuePairs = this.keyValuePairs
    newInstance.asInstanceOf[this.type]
  }

  override def productElementName(n: Int): String =
    n match {
      case 0 => "evidence"
      case 1 => "keyValuePairs"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.evidence
      case 1 => this.keyValuePairs
      case _ => null
    }

  override def productPrefix                = "NewFinding"
  override def productArity                 = 2
  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[NewFinding]
}
