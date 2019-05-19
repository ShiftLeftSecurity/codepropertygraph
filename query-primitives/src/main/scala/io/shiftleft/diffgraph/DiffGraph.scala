package io.shiftleft.diffgraph

import gremlin.scala.{Edge, ScalaGraph}
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, StoredNode}
import java.lang.{Long => JLong}
import org.apache.logging.log4j.LogManager
import scala.collection.mutable

/**
  * A lightweight write-only graph used for creation of CPG graph overlays
  *
  * The graph can store edges to/from nodes that do not exist in the base graph.
  * It doesn't assign ids for these nodes until the diff graph is serialized.
  * Ids of new nodes may collide with ids of nodes in the base graph, which are
  * not sources or destinations of edges of the diff graph. When the CPG loader
  * adds nodes of the overlay, it therefor needs to reassign ids for nodes if they
  * are already used in the original CPG.
  *
  * TODO Michael: make DiffGraph extend tinkerpop.Graph to simplify and foolproof the model
  * */
class DiffGraph {
  import DiffGraph._

  private val _edges = mutable.ListBuffer.empty[EdgeInDiffGraph]
  private val _edgesToOriginal = mutable.ListBuffer.empty[EdgeToOriginal]
  private val _edgesFromOriginal = mutable.ListBuffer.empty[EdgeFromOriginal]
  private val _edgesInOriginal = mutable.ListBuffer.empty[EdgeInOriginal]
  private val _nodeProperties = mutable.ListBuffer.empty[NodeProperty]
  private val _edgeProperties = mutable.ListBuffer.empty[EdgeProperty]

  private var _nodes = Set[IdentityHashWrapper[NewNode]]()

  /* this could be done much nicer if we wouldn't hold the DiffGraph locally in the CpgPass  */
  private var applied = false

  def nodes: List[NewNode] = _nodes.toList.map(_.value)
  def edges: List[EdgeInDiffGraph] = _edges.toList
  def edgesToOriginal: List[EdgeToOriginal] = _edgesToOriginal.toList
  def edgesFromOriginal: List[EdgeFromOriginal] = _edgesFromOriginal.toList
  def edgesInOriginal: List[EdgeInOriginal] = _edgesInOriginal.toList
  def nodeProperties: List[NodeProperty] = _nodeProperties.toList
  def edgeProperties: List[EdgeProperty] = _edgeProperties.toList

  def applyDiff(graph: ScalaGraph): AppliedDiffGraph = {
    assert(!applied, "DiffGraph has already been applied, this is probably a bug")
    val appliedDiffGraph = new DiffGraphApplier().applyDiff(this, graph)
    applied = true
    appliedDiffGraph
  }

  def addNode(node: NewNode): Unit = {
    _nodes += IdentityHashWrapper(node)
    incrementNodeCreatedCount()
  }

  def mergeFrom(other: DiffGraph): Unit = {
    other.nodes.foreach(addNode)
    _edges.appendAll(other._edges)
    _edgesToOriginal.appendAll(other._edgesToOriginal)
    _edgesInOriginal.appendAll(other._edgesInOriginal)
    _nodeProperties.appendAll(other._nodeProperties)
    _edgeProperties.appendAll(other._edgeProperties)
  }

  /**
    * Add edge between nodes present in the diff graph
    * */
  def addEdge(srcNode: NewNode, dstNode: NewNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit = {
    _edges += new EdgeInDiffGraph(srcNode, dstNode, edgeLabel, properties)
    incrementEdgeCreatedCount()
  }

  /**
    * Add edge from a node in the diff graph to a node in the original graph
    * */
  def addEdgeToOriginal(srcNode: NewNode,
                        dstNode: StoredNode,
                        edgeLabel: String,
    properties: Seq[(String, AnyRef)] = List()): Unit = {
    _edgesToOriginal += new EdgeToOriginal(srcNode, dstNode.getId, edgeLabel, properties)
    incrementEdgeCreatedCount()
  }

  /**
    * Add edge from a node in the original graph to a node in the diff graph
    * */
  def addEdgeFromOriginal(srcNode: StoredNode,
                          dstNode: NewNode,
                          edgeLabel: String,
                          properties: Seq[(String, AnyRef)] = List()): Unit = {
    _edgesFromOriginal += new EdgeFromOriginal(srcNode.getId, dstNode, edgeLabel, properties)
    incrementEdgeCreatedCount()
  }

  /**
    * Add edge between nodes of the original graph
    * */
  def addEdgeInOriginal(srcNode: StoredNode,
                        dstNode: StoredNode,
                        edgeLabel: String,
                        properties: Seq[(String, AnyRef)] = List()): Unit = {
    _edgesInOriginal += new EdgeInOriginal(srcNode.getId, dstNode.getId, edgeLabel, properties)
    incrementEdgeCreatedCount()
  }

  /**
    * Add a property to an existing node
    * */
  def addNodeProperty(node: StoredNode, key: String, value: AnyRef) =
    _nodeProperties += new NodeProperty(node.getId, key, value)

  /**
    * Add a property to an existing edge
    * */
  def addEdgeProperty(edge: Edge, key: String, value: AnyRef) =
    _edgeProperties += new EdgeProperty(edge.id().asInstanceOf[JLong], key, value)

}

object DiffGraph {
  private val logger = LogManager.getLogger(getClass)
  private var nodesCreatedCount = 0
  private var edgesCreatedCount = 0

  def incrementNodeCreatedCount(): Unit = {
    nodesCreatedCount += 1
    if (nodesCreatedCount % 100000 == 0)
      logger.debug(s"added $nodesCreatedCount nodes (total across all DiffGraphs)")
  }

  def incrementEdgeCreatedCount(): Unit = {
    edgesCreatedCount += 1
    if (edgesCreatedCount % 100000 == 0)
      logger.debug(s"added $edgesCreatedCount edges (total across all DiffGraphs)")
  }

  abstract class DiffEdge {
    def label: String
    def properties: Seq[(String, AnyRef)]
  }
  case class NodeProperty(nodeId: Long, propertyKey: String, propertyValue: AnyRef)
  case class EdgeProperty(edgeId: Long, propertyKey: String, propertyValue: AnyRef)
  type Properties = Seq[(String, AnyRef)]

  case class EdgeInDiffGraph(src: NewNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeToOriginal(src: NewNode, dstId: Long, label: String, properties: Properties) extends DiffEdge
  case class EdgeFromOriginal(srcId: Long, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeInOriginal(srcId: Long, dstId: Long, label: String, properties: Properties) extends DiffEdge
}
