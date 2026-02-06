package io.shiftleft.codepropertygraph.generated.nodes

import io.shiftleft.codepropertygraph.generated.language.*
import scala.collection.immutable.{IndexedSeq, ArraySeq}
import scala.collection.mutable

/** Node base type for compiletime-only checks to improve type safety. EMT stands for: "erased marker trait", i.e. it is
  * erased at runtime
  */
trait FindingEMT extends AnyRef with HasEvidenceDescriptionEMT

trait FindingBase extends AbstractNode with StaticType[FindingEMT] {
  def evidence: IndexedSeq[AbstractNode]
  def keyValuePairs: IndexedSeq[KeyValuePairBase]
  override def propertiesMap: java.util.Map[String, Any] = {
    import io.shiftleft.codepropertygraph.generated.accessors.languagebootstrap.*
    val res                    = new java.util.HashMap[String, Any]()
    val tmpEvidenceDescription = this.evidenceDescription;
    if (tmpEvidenceDescription.nonEmpty) res.put("EVIDENCE_DESCRIPTION", tmpEvidenceDescription)
    val tmpevidence      = this.evidence; if (tmpevidence.nonEmpty) res.put("evidence", tmpevidence)
    val tmpkeyValuePairs = this.keyValuePairs; if (tmpkeyValuePairs.nonEmpty) res.put("keyValuePairs", tmpkeyValuePairs)
    res
  }
}

object Finding {
  val Label = "FINDING"
}

/** * NODE PROPERTIES:
  *
  * ▸ EvidenceDescription (String); Cardinality `List` (many); Optional description for nodes in evidence. Used to give
  * a hint about the kind of evidence provided by a node. The evidence description and evidence nodes are associated by
  * index.
  *
  * CONTAINED NODES:
  *
  * ▸ evidence (Anynode); Cardinality `List` (many)
  *
  * ▸ keyValuePairs (KeyValuePair); Cardinality `List` (many)
  */
class Finding(graph_4762: flatgraph.Graph, seq_4762: Int)
    extends StoredNode(graph_4762, 15, seq_4762)
    with FindingBase
    with StaticType[FindingEMT] {
  def evidence: IndexedSeq[StoredNode] =
    flatgraph.Accessors.getNodePropertyMulti[StoredNode](graph, nodeKind = nodeKind, propertyKind = 52, seq = seq)
  def keyValuePairs: IndexedSeq[KeyValuePair] =
    flatgraph.Accessors.getNodePropertyMulti[KeyValuePair](graph, nodeKind = nodeKind, propertyKind = 53, seq = seq)

  override def productElementName(n: Int): String =
    n match {
      case 0 => "evidenceDescription"
      case 1 => "evidence"
      case 2 => "keyValuePairs"
      case _ => ""
    }

  override def productElement(n: Int): Any =
    n match {
      case 0 => this.evidenceDescription
      case 1 => this.evidence
      case 2 => this.keyValuePairs
      case _ => null
    }

  override def productPrefix = "Finding"
  override def productArity  = 3

  override def canEqual(that: Any): Boolean = that != null && that.isInstanceOf[Finding]
}
