package io.shiftleft.codepropertygraph.generated.nodes

trait StaticType[+T]

trait AbstractNode extends flatgraph.DNodeOrNode with StaticType[AnyRef] with Product {
  def label: String

  def properties: Map[String, Any] = {
    scala.jdk.CollectionConverters.MapHasAsScala(propertiesMap).asScala.toMap
  }

  /** TODO deprecate and phase out */
  def propertiesMap: java.util.Map[String, Any]
}

abstract class StoredNode(graph_4762: flatgraph.Graph, kind_4762: Short, seq_4762: Int)
    extends flatgraph.GNode(graph_4762, kind_4762, seq_4762)
    with AbstractNode {

  final def _aliasOfOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 0).asInstanceOf[Iterator[StoredNode]]
  final def _aliasOfIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 0).asInstanceOf[Iterator[StoredNode]]

  final def _argumentOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 1).asInstanceOf[Iterator[StoredNode]]
  final def _argumentIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 1).asInstanceOf[Iterator[StoredNode]]

  final def _astOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 2).asInstanceOf[Iterator[StoredNode]]
  final def _astIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 2).asInstanceOf[Iterator[StoredNode]]

  final def _bindsOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 3).asInstanceOf[Iterator[StoredNode]]
  final def _bindsIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 3).asInstanceOf[Iterator[StoredNode]]

  final def _bindsToOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 4).asInstanceOf[Iterator[StoredNode]]
  final def _bindsToIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 4).asInstanceOf[Iterator[StoredNode]]

  final def _callOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 5).asInstanceOf[Iterator[StoredNode]]
  final def _callIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 5).asInstanceOf[Iterator[StoredNode]]

  final def _captureOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 6).asInstanceOf[Iterator[StoredNode]]
  final def _captureIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 6).asInstanceOf[Iterator[StoredNode]]

  final def _capturedByOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 7).asInstanceOf[Iterator[StoredNode]]
  final def _capturedByIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 7).asInstanceOf[Iterator[StoredNode]]

  final def _cdgOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 8).asInstanceOf[Iterator[StoredNode]]
  final def _cdgIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 8).asInstanceOf[Iterator[StoredNode]]

  final def _cfgOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 9).asInstanceOf[Iterator[StoredNode]]
  final def _cfgIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 9).asInstanceOf[Iterator[StoredNode]]

  final def _conditionOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 10).asInstanceOf[Iterator[StoredNode]]
  final def _conditionIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 10).asInstanceOf[Iterator[StoredNode]]

  final def _containsOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 11).asInstanceOf[Iterator[StoredNode]]
  final def _containsIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 11).asInstanceOf[Iterator[StoredNode]]

  final def _dominateOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 12).asInstanceOf[Iterator[StoredNode]]
  final def _dominateIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 12).asInstanceOf[Iterator[StoredNode]]

  final def _evalTypeOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 13).asInstanceOf[Iterator[StoredNode]]
  final def _evalTypeIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 13).asInstanceOf[Iterator[StoredNode]]

  final def _importsOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 14).asInstanceOf[Iterator[StoredNode]]
  final def _importsIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 14).asInstanceOf[Iterator[StoredNode]]

  final def _inheritsFromOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 15).asInstanceOf[Iterator[StoredNode]]
  final def _inheritsFromIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 15).asInstanceOf[Iterator[StoredNode]]

  final def _isCallForImportOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 16).asInstanceOf[Iterator[StoredNode]]
  final def _isCallForImportIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 16).asInstanceOf[Iterator[StoredNode]]

  final def _parameterLinkOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 17).asInstanceOf[Iterator[StoredNode]]
  final def _parameterLinkIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 17).asInstanceOf[Iterator[StoredNode]]

  final def _postDominateOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 18).asInstanceOf[Iterator[StoredNode]]
  final def _postDominateIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 18).asInstanceOf[Iterator[StoredNode]]

  final def _reachingDefOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 19).asInstanceOf[Iterator[StoredNode]]
  final def _reachingDefIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 19).asInstanceOf[Iterator[StoredNode]]

  final def _receiverOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 20).asInstanceOf[Iterator[StoredNode]]
  final def _receiverIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 20).asInstanceOf[Iterator[StoredNode]]

  final def _refOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 21).asInstanceOf[Iterator[StoredNode]]
  final def _refIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 21).asInstanceOf[Iterator[StoredNode]]

  final def _sourceFileOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 22).asInstanceOf[Iterator[StoredNode]]
  final def _sourceFileIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 22).asInstanceOf[Iterator[StoredNode]]

  final def _taggedByOut: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsOut(this.graph, this.nodeKind, this.seq, 23).asInstanceOf[Iterator[StoredNode]]
  final def _taggedByIn: Iterator[StoredNode] =
    flatgraph.Accessors.getNeighborsIn(this.graph, this.nodeKind, this.seq, 23).asInstanceOf[Iterator[StoredNode]]

}

abstract class NewNode(val nodeKind: Short) extends AbstractNode with flatgraph.DNode {
  private /* volatile? */ var _storedRef: StoredNodeType = null.asInstanceOf[StoredNodeType]
  override def storedRef: Option[StoredNodeType]         = Option(this._storedRef)
  override def storedRef_=(stored: Option[flatgraph.GNode]): Unit = this._storedRef =
    stored.orNull.asInstanceOf[StoredNodeType]
  def isValidOutNeighbor(edgeLabel: String, n: NewNode): Boolean
  def isValidInNeighbor(edgeLabel: String, n: NewNode): Boolean
  def copy: this.type
}
