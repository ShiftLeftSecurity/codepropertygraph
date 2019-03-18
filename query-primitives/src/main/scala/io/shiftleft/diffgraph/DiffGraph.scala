package io.shiftleft.diffgraph

import gremlin.scala.{Edge, ScalaGraph, Vertex}
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, Node}
import scala.collection.mutable.ListBuffer

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

  private val _nodes: ListBuffer[NewNode] = ListBuffer()
  private val _edges: ListBuffer[EdgeInDiffGraph] = ListBuffer()
  private val _edgesToOriginal: ListBuffer[EdgeToOriginal] = ListBuffer()
  private val _edgesFromOriginal: ListBuffer[EdgeFromOriginal] = ListBuffer()
  private val _edgesInOriginal: ListBuffer[EdgeInOriginal] = ListBuffer()
  private val _nodeProperties: ListBuffer[NodeProperty] = ListBuffer()
  private val _edgeProperties: ListBuffer[EdgeProperty] = ListBuffer()

  private val diffGraphApplier = new DiffGraphApplier()

  def nodes: List[NewNode] = _nodes.toList
  def edges: List[EdgeInDiffGraph] = _edges.toList
  def edgesToOriginal: List[EdgeToOriginal] = _edgesToOriginal.toList
  def edgesFromOriginal: List[EdgeFromOriginal] = _edgesFromOriginal.toList
  def edgesInOriginal: List[EdgeInOriginal] = _edgesInOriginal.toList
  def nodeProperties: List[NodeProperty] = _nodeProperties.toList
  def edgeProperties: List[EdgeProperty] = _edgeProperties.toList

  def applyDiff(graph: ScalaGraph): Unit = {
    diffGraphApplier.applyDiff(this, graph)
  }

  def addNode[A <: NewNode](node: A): Unit =
    _nodes.append(node)

  def mergeFrom(other: DiffGraph): Unit = {
    _nodes.appendAll(other._nodes)
    _edges.appendAll(other._edges)
    _edgesToOriginal.appendAll(other._edgesToOriginal)
    _edgesInOriginal.appendAll(other._edgesInOriginal)
    _nodeProperties.appendAll(other._nodeProperties)
    _edgeProperties.appendAll(other._edgeProperties)
  }

  /**
    * Add edge between nodes present in the diff graph
    * */
  def addEdge(srcNode: NewNode, dstNode: NewNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit =
    _edges.append(new EdgeInDiffGraph(srcNode, dstNode, edgeLabel, properties))

  /**
    * Add edge from a node in the diff graph to a node in the original graph
    * */
  def addEdgeToOriginal(srcNode: NewNode,
                        dstNode: Vertex,
                        edgeLabel: String,
                        properties: Seq[(String, AnyRef)] = List()): Unit =
    _edgesToOriginal.append(new EdgeToOriginal(srcNode, dstNode, edgeLabel, properties))

  /**
    * Add edge from a node in the original graph to a node in the diff graph
    * */
  def addEdgeFromOriginal(srcNode: Vertex,
                          dstNode: NewNode,
                          edgeLabel: String,
                          properties: Seq[(String, AnyRef)] = List()): Unit =
    _edgesFromOriginal.append(new EdgeFromOriginal(srcNode, dstNode, edgeLabel, properties))

  /**
    * Add edge between nodes of the original graph
    * */
  def addEdgeInOriginal(srcNode: Vertex,
                        dstNode: Vertex,
                        edgeLabel: String,
                        properties: Seq[(String, AnyRef)] = List()): Unit =
    _edgesInOriginal.append(new EdgeInOriginal(srcNode, dstNode, edgeLabel, properties))

  /**
    * Add a property to an existing node
    * */
  def addNodeProperty(node: Vertex, key: String, value: AnyRef) =
    _nodeProperties.append(new NodeProperty(node, key, value))

  /**
    * Add a property to an existing edge
    * */
  def addEdgeProperty(edge: Edge, key: String, value: AnyRef) =
    _edgeProperties.append(new EdgeProperty(edge, key, value))

}

object DiffGraph {

  abstract class DiffEdge {
    def label: String
    def properties: Seq[(String, AnyRef)]
  }
  case class NodeProperty(node: Vertex, propertyKey: String, propertyValue: AnyRef)
  case class EdgeProperty(edge: Edge, propertyKey: String, propertyValue: AnyRef)
  type Properties = Seq[(String, AnyRef)]

  case class EdgeInDiffGraph(src: NewNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeToOriginal(src: NewNode, dst: Vertex, label: String, properties: Properties) extends DiffEdge
  case class EdgeFromOriginal(src: Vertex, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeInOriginal(src: Vertex, dst: Vertex, label: String, properties: Properties) extends DiffEdge
}
