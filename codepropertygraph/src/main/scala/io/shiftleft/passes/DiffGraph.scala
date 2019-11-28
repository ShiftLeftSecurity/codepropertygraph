package io.shiftleft.passes

import java.util
import gremlin.scala.{Edge, ScalaGraph}
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, StoredNode, Node}
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/**
  * A lightweight write-only graph used for creation of CPG graph overlays
  *
  * The graph can store edges to/from nodes that do not exist in the base graph.
  * It doesn't assign ids for these nodes until the diff graph is serialized.
  * Ids of new nodes may collide with ids of nodes in the base graph, which are
  * not sources or destinations of edges of the diff graph. When the CPG loader
  * adds nodes of the overlay, it therefor needs to reassign ids for nodes if they
  * are already used in the original CPG.
  */
sealed trait DiffGraph {
  import DiffGraph._
  import Change.NodeKind._

  def size: Int
  def iterator: Iterator[Change]
  def +(other: DiffGraph): DiffGraph = ???
  // compatibility
  def nodes: Iterator[NewNode] =
    iterator.collect { case Change.CreateNode(newNode) => newNode }
  def edges: Vector[EdgeInDiffGraph] =
    iterator.collect {
      case c@Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == New && c.destinationNodeKind == New =>
        DiffGraph.EdgeInDiffGraph(src.asInstanceOf[NewNode], dst.asInstanceOf[NewNode], label, c.properties)
    }.toVector
  def edgesToOriginal: Vector[EdgeToOriginal] =
    iterator.collect {
      case c@Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == New && c.destinationNodeKind == Existing =>
        DiffGraph.EdgeToOriginal(src.asInstanceOf[NewNode], dst.asInstanceOf[StoredNode], label, c.properties)
    }.toVector
  def edgesFromOriginal: Vector[EdgeFromOriginal] =
    iterator.collect {
      case c@Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == Existing && c.destinationNodeKind == New =>
        DiffGraph.EdgeFromOriginal(src.asInstanceOf[StoredNode], dst.asInstanceOf[NewNode], label, c.properties)
    }.toVector
  def edgesInOriginal: Vector[EdgeInOriginal] =
    iterator.collect {
      case c@Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == Existing && c.destinationNodeKind == Existing =>
        DiffGraph.EdgeInOriginal(src.asInstanceOf[StoredNode], dst.asInstanceOf[StoredNode], label, c.properties)
    }.toVector
  def nodeProperties: Vector[NodeProperty] =
    iterator.collect {
      case c@Change.SetNodeProperty(node, key, value) =>
        DiffGraph.NodeProperty(node, key, value)
    }.toVector
  def edgeProperties: Vector[EdgeProperty] =
    iterator.collect {
      case c@Change.SetEdgeProperty(edge, key, value) =>
        DiffGraph.EdgeProperty(edge, key, value)
    }.toVector
}

object DiffGraph {
  private val logger = LogManager.getLogger(getClass)
  type Properties = Seq[(String, AnyRef)]
  type PackedProperties = Array[Any]
  object PackedProperties {
    val Empty: Array[Any] = Array()

    def pack(p: Properties): Array[Any] = {
      if (p.isEmpty) {
        Empty
      } else {
        val buffer = new ArrayBuffer[Any](p.size * 2)
        p.foreach { pair =>
          buffer += pair._1
          buffer += pair._2
        }
        buffer.toArray
      }
    }

    def unpack(p: PackedProperties): Properties = {
      val buffer = new ListBuffer[(String, AnyRef)]()
      var i = 0
      while (i < p.length) {
        buffer += Tuple2(p(i).asInstanceOf[String], p(i+1).asInstanceOf[AnyRef])
        i += 2
      }
      buffer.toList
    }
  }

  sealed trait Change
  object Change {
    sealed trait NodeKind
    object NodeKind {
      case object New extends NodeKind
      case object Existing extends NodeKind
    }

    final case class CreateNode(node: NewNode) extends Change
    final case class SetNodeProperty(node: StoredNode, key: String, value: AnyRef) extends Change
    final case class SetEdgeProperty(edge: Edge, propertyKey: String, propertyValue: AnyRef) extends Change
    sealed case class CreateEdge(src: Node, dst: Node, label: String, packedProperties: PackedProperties) extends Change {
      def properties: Properties = PackedProperties.unpack(packedProperties)

      def sourceNodeKind: NodeKind = src match {
        case _:NewNode => NodeKind.New
        case _:StoredNode => NodeKind.Existing
      }
      def destinationNodeKind: NodeKind = dst match {
        case _:NewNode => NodeKind.New
        case _:StoredNode => NodeKind.Existing
      }
    }
    object CreateEdge {
      def apply(src: Node, dst: Node, label: String, properties: Properties): CreateEdge =
        CreateEdge(src, dst, label, PackedProperties.pack(properties))
    }
  }

  def newBuilder: Builder = new Builder()

  class Builder {
    var _buffer: mutable.ArrayBuffer[Change] = _
    private def buffer: mutable.ArrayBuffer[Change] = {
      if (_buffer == null)
        _buffer = new mutable.ArrayBuffer[Change]()
      _buffer
    }
    def +=(node: NewNode): Unit = {
      buffer += Change.CreateNode(node)
    }
    def addEdge(src: Node, dst: Node, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit = {
      buffer += Change.CreateEdge(src, dst, edgeLabel, properties)
    }
    def build(): DiffGraph = {
      if (_buffer == null || _buffer.isEmpty)
        EmptyChangeSet
      else if (_buffer.size == 1)
        SingleChangeSet(_buffer.head)
      else
        ArrayChangeSet(buffer.toArray)
    }
    // compatibility api
    def addNode(node: NewNode): Unit = this += node
    def addEdgeToOriginal(srcNode: NewNode, dstNode: StoredNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addEdgeFromOriginal(srcNode: StoredNode, dstNode: NewNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addEdgeInOriginal(srcNode: StoredNode, dstNode: StoredNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addNodeProperty(node: StoredNode, key: String, value: AnyRef): Unit =
      buffer += Change.SetNodeProperty(node, key, value)
    def addEdgeProperty(edge: Edge, key: String, value: AnyRef): Unit =
      buffer += Change.SetEdgeProperty(edge, key, value)
  }

  private class Applier {
    import Applier.InternalProperty

    private val overlayNodeToTinkerNode = new util.HashMap[IdentityHashWrapper[NewNode], Vertex]()

    def applyDiff(diffGraph: DiffGraph, cpg: Cpg): AppliedDiffGraph = {
      val graph = cpg.graph
      diffGraph.iterator.foreach(change => applyChange(graph, change))
      AppliedDiffGraph(diffGraph, overlayNodeToTinkerNode)
    }

    private def applyChange(graph: gremlin.scala.Graph, change: Change) = change match {
      case Change.CreateNode(node) => addNode(graph, node)
      case c: Change.CreateEdge => addEdge(c)
      case Change.SetNodeProperty(node, key, value) =>
        node.property(key, value)
      case Change.SetEdgeProperty(edge, key, value) =>
        edge.property(key, value)
    }

    private def addEdge(edgeChange: Change.CreateEdge): Unit = {
      val src = edgeChange.src
      val dst = edgeChange.dst

      val srcTinkerNode =
        if (edgeChange.sourceNodeKind == Change.NodeKind.New)
          overlayNodeToTinkerNode.get(IdentityHashWrapper(src))
        else
          src.asInstanceOf[StoredNode]
      val dstTinkerNode =
        if (edgeChange.destinationNodeKind == Change.NodeKind.New)
          overlayNodeToTinkerNode.get(IdentityHashWrapper(dst))
        else
          dst.asInstanceOf[StoredNode]
      tinkerAddEdge(srcTinkerNode, dstTinkerNode, edgeChange.label, edgeChange.properties)
    }

    private def tinkerAddEdge(src: Vertex, dst: Vertex, label: String, properties: Seq[(String, AnyRef)]): Unit = {
      val tinkerEdge = src.addEdge(label, dst)
      properties.foreach {
        case (key, value) =>
          tinkerEdge.property(key, value)
      }
    }

    def addNode(graph: ScalaGraph, node: NewNode): Unit = {
      val newNode = graph.graph.addVertex(node.label)
      node.properties.filter { case (key, _) => !key.startsWith(InternalProperty) }.foreach {
        case (key, value: Traversable[_]) =>
          value.foreach { value =>
            newNode.property(Cardinality.list, key, value)
          }
        case (key, value) =>
          newNode.property(key, value)
      }
      overlayNodeToTinkerNode.put(IdentityHashWrapper(node), newNode)
    }
  }

  object Applier {
    private val InternalProperty = "_"
    def applyDiff(diff: DiffGraph, cpg: Cpg): AppliedDiffGraph = {
      val applier = new Applier
      applier.applyDiff(diff, cpg)
    }
  }

  case object EmptyChangeSet extends DiffGraph {
    override def size: Int = 0
    override def iterator: Iterator[Change] = Iterator.empty
  }

  case class SingleChangeSet(change: Change) extends DiffGraph {
    override def size: Int = 1
    override def iterator: Iterator[Change] = Iterator(change)
  }

  case class ArrayChangeSet(changes: Array[Change]) extends DiffGraph {
    override def size: Int = changes.length
    override def iterator: Iterator[Change] = changes.iterator
  }

  // TODO: Remove those later, they are only here to keep old unit tests working
  abstract class DiffEdge {
    def label: String
    def properties: Seq[(String, AnyRef)]
  }
  case class NodeProperty(node: StoredNode, propertyKey: String, propertyValue: AnyRef)
  case class EdgeProperty(edge: Edge, propertyKey: String, propertyValue: AnyRef)
  case class EdgeInDiffGraph(src: NewNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeToOriginal(src: NewNode, dst: StoredNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeFromOriginal(src: StoredNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeInOriginal(src: StoredNode, dst: StoredNode, label: String, properties: Properties) extends DiffEdge
}
