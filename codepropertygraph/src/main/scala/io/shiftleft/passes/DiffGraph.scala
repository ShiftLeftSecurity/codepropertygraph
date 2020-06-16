package io.shiftleft.passes

import java.security.MessageDigest
import java.util

import gnu.trove.set.hash.TCustomHashSet
import gnu.trove.strategy.IdentityHashingStrategy
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{CpgNode, NewNode, StoredNode}
import io.shiftleft.proto.cpg.Cpg.{DiffGraph => DiffGraphProto}
import org.apache.logging.log4j.LogManager
import overflowdb._
import overflowdb.traversal._

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
      case c @ Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == New && c.destinationNodeKind == New =>
        DiffGraph.EdgeInDiffGraph(src.asInstanceOf[NewNode], dst.asInstanceOf[NewNode], label, c.properties)
    }.toVector
  def edgesToOriginal: Vector[EdgeToOriginal] =
    iterator.collect {
      case c @ Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == New && c.destinationNodeKind == Existing =>
        DiffGraph.EdgeToOriginal(src.asInstanceOf[NewNode], dst.asInstanceOf[StoredNode], label, c.properties)
    }.toVector
  def edgesFromOriginal: Vector[EdgeFromOriginal] =
    iterator.collect {
      case c @ Change.CreateEdge(src, dst, label, _) if c.sourceNodeKind == Existing && c.destinationNodeKind == New =>
        DiffGraph.EdgeFromOriginal(src.asInstanceOf[StoredNode], dst.asInstanceOf[NewNode], label, c.properties)
    }.toVector
  def edgesInOriginal: Vector[EdgeInOriginal] =
    iterator.collect {
      case c @ Change.CreateEdge(src, dst, label, _)
          if c.sourceNodeKind == Existing && c.destinationNodeKind == Existing =>
        DiffGraph.EdgeInOriginal(src.asInstanceOf[StoredNode], dst.asInstanceOf[StoredNode], label, c.properties)
    }.toVector
  def nodeProperties: Vector[NodeProperty] =
    iterator.collect {
      case c @ Change.SetNodeProperty(node, key, value) =>
        DiffGraph.NodeProperty(node, key, value)
    }.toVector
  def edgeProperties: Vector[EdgeProperty] =
    iterator.collect {
      case c @ Change.SetEdgeProperty(edge, key, value) =>
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
        buffer += Tuple2(p(i).asInstanceOf[String], p(i + 1).asInstanceOf[AnyRef])
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

    final case class RemoveNode(nodeId: Long) extends Change
    final case class RemoveNodeProperty(nodeId: Long, propertyKey: String) extends Change
    final case class RemoveEdge(edge: OdbEdge) extends Change
    final case class RemoveEdgeProperty(edge: OdbEdge, propertyKey: String) extends Change
    final case class CreateNode(node: NewNode) extends Change
    final case class SetNodeProperty(node: StoredNode, key: String, value: AnyRef) extends Change
    final case class SetEdgeProperty(edge: OdbEdge, propertyKey: String, propertyValue: AnyRef) extends Change
    final case class CreateEdge(src: CpgNode, dst: CpgNode, label: String, packedProperties: PackedProperties)
        extends Change {
      def properties: Properties = PackedProperties.unpack(packedProperties)

      def sourceNodeKind: NodeKind = src match {
        case _: NewNode    => NodeKind.New
        case _: StoredNode => NodeKind.Existing
      }
      def destinationNodeKind: NodeKind = dst match {
        case _: NewNode    => NodeKind.New
        case _: StoredNode => NodeKind.Existing
      }
    }
    object CreateEdge {
      def apply(src: CpgNode, dst: CpgNode, label: String, properties: Properties): CreateEdge =
        CreateEdge(src, dst, label, PackedProperties.pack(properties))
    }
  }

  def fromProto(inverseDiffGraphProto: DiffGraphProto, cpg: Cpg): DiffGraph = {
    import DiffGraphProto.Entry.ValueCase._
    val builder = newBuilder
    inverseDiffGraphProto.getEntriesList.forEach { entry =>
      entry.getValueCase match {
        case REMOVE_NODE =>
          builder.removeNode(entry.getRemoveNode.getKey)
        case REMOVE_NODE_PROPERTY =>
          val proto = entry.getRemoveNodeProperty
          builder.removeNodeProperty(proto.getKey, proto.getName.toString)
        case REMOVE_EDGE =>
          val proto = entry.getRemoveEdge
          val outNodeId = proto.getOutNodeKey
          val inNodeId = proto.getInNodeKey
          val edgeLabel = proto.getEdgeType.toString

          val edge = cpg.graph.V(outNodeId).outE(edgeLabel).filter(_.inVertex.id == inNodeId).l match {
            case edge :: Nil => edge
            case Nil         => throw new AssertionError(s"unable to find edge that is supposed to be removed: $proto")
            case candidates => // found multiple edges - try to disambiguate via propertiesHash
              val wantedPropertiesHash: List[Byte] = proto.getPropertiesHash.toByteArray.toList
              candidates.filter(edge => propertiesHash(edge.asInstanceOf[OdbEdge]).toList == wantedPropertiesHash) match {
                case edge :: Nil => edge
                case Nil =>
                  throw new AssertionError(
                    s"unable to find edge that is supposed to be removed: $proto. n.b. before filtering on propertiesHash, multiple candidates have been found: $candidates")
                case candidates =>
                  throw new AssertionError(
                    s"unable to disambiguate the edge to be removed, since multiple edges match the filter conditions of $proto. Candidates=$candidates")
              }
          }
          builder.removeEdge(edge.asInstanceOf[OdbEdge])
        case other => // TODO nasty, i know. sorry. yolo :(
      }
    }

    builder.build()
  }

  def propertiesHash(edge: OdbEdge): Array[Byte] = {
    import scala.jdk.CollectionConverters._
    val propertiesAsString = edge
      .properties[Any]()
      .asScala
      .collect {
        case prop if prop.isPresent => prop.key -> prop.value
      }
      .toList
      .sortBy(_._1)
      .mkString
    MessageDigest.getInstance("MD5").digest(propertiesAsString.getBytes)
  }

  def newBuilder: Builder = new Builder()

  class Builder {
    private var _buffer: mutable.ArrayBuffer[Change] = _
    private var _nodeSet: TCustomHashSet[NewNode] = _
    private def buffer: mutable.ArrayBuffer[Change] = {
      if (_buffer == null)
        _buffer = new mutable.ArrayBuffer[Change]()
      _buffer
    }
    private def nodeSet: TCustomHashSet[NewNode] = {
      if (_nodeSet == null)
        _nodeSet = new TCustomHashSet[NewNode](IdentityHashingStrategy.INSTANCE)
      _nodeSet
    }

    def +=(node: NewNode): Unit = {
      if (!nodeSet.contains(node)) {
        buffer += Change.CreateNode(node)
        nodeSet.add(node)
      }
    }
    def addEdge(src: CpgNode, dst: CpgNode, edgeLabel: String, properties: Seq[(String, AnyRef)] = List()): Unit = {
      buffer += Change.CreateEdge(src, dst, edgeLabel, properties)
    }
    def build(buf: mutable.ArrayBuffer[Change]) = {
      if (buf == null || buf.isEmpty)
        EmptyChangeSet
      else if (buf.size == 1)
        SingleChangeSet(buf.head)
      else
        ArrayChangeSet(buf.toArray)
    }

    def build(): DiffGraph = build(_buffer)
    def buildReverse(): DiffGraph = build(if (_buffer != null) _buffer.reverse else null)
    // compatibility api
    def addNode(node: NewNode): Unit = this += node
    def addEdgeToOriginal(srcNode: NewNode,
                          dstNode: StoredNode,
                          edgeLabel: String,
                          properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addEdgeFromOriginal(srcNode: StoredNode,
                            dstNode: NewNode,
                            edgeLabel: String,
                            properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addEdgeInOriginal(srcNode: StoredNode,
                          dstNode: StoredNode,
                          edgeLabel: String,
                          properties: Seq[(String, AnyRef)] = List()): Unit =
      addEdge(srcNode, dstNode, edgeLabel, properties)
    def addNodeProperty(node: StoredNode, key: String, value: AnyRef): Unit =
      buffer += Change.SetNodeProperty(node, key, value)
    def addEdgeProperty(edge: OdbEdge, key: String, value: AnyRef): Unit =
      buffer += Change.SetEdgeProperty(edge, key, value)
    def removeNode(id: Long): Unit =
      buffer += Change.RemoveNode(id)
    def removeNode(node: StoredNode): Unit =
      buffer += Change.RemoveNode(node.id.asInstanceOf[Long])
    def removeEdge(edge: OdbEdge): Unit = buffer += Change.RemoveEdge(edge)
    def removeNodeProperty(nodeId: Long, propertyKey: String): Unit =
      buffer += Change.RemoveNodeProperty(nodeId, propertyKey)
    def removeEdgeProperty(edge: OdbEdge, propertyKey: String): Unit =
      buffer += Change.RemoveEdgeProperty(edge, propertyKey)
  }

  trait InverseBuilder {
    def onNewNode(node: StoredNode): Unit
    def onNewEdge(edge: OdbEdge): Unit
    def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String): Unit
    def onBeforeEdgePropertyChange(edge: OdbEdge, propertyKey: String): Unit
    def build(): DiffGraph
  }
  object InverseBuilder {
    def newBuilder: InverseBuilder = new InverseBuilder {
      private val builder = DiffGraph.newBuilder
      def onNewNode(node: StoredNode) = builder.removeNode(node)
      def onNewEdge(edge: OdbEdge) = builder.removeEdge(edge)
      def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String) = {
        val prop = node.property(propertyKey)
        if (prop.isPresent)
          builder.addNodeProperty(node, propertyKey, node.property(propertyKey).value())
        else
          builder.removeNodeProperty(node.id2, propertyKey)
      }
      def onBeforeEdgePropertyChange(edge: OdbEdge, propertyKey: String) = {
        val prop = edge.property(propertyKey)
        if (prop.isPresent)
          builder.addEdgeProperty(edge, propertyKey, edge.property(propertyKey).value())
        else
          builder.removeEdgeProperty(edge, propertyKey)
      }

      def build(): DiffGraph = builder.buildReverse()
    }
    val noop: InverseBuilder = new InverseBuilder {
      def onNewNode(node: StoredNode) = ()
      def onNewEdge(edge: OdbEdge) = ()
      def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String) = ()
      def onBeforeEdgePropertyChange(edge: OdbEdge, propertyKey: String) = ()
      def build(): DiffGraph = ???
    }
  }

  private class Applier {
    import Applier.InternalProperty

    private val overlayNodeToOdbNode = new util.HashMap[IdentityHashWrapper[NewNode], Node]()

    def applyDiff(diffGraph: DiffGraph, cpg: Cpg, undoable: Boolean = false): AppliedDiffGraph = {
      applyDiff(diffGraph, cpg.graph, undoable)
    }

    private def applyDiff(diffGraph: DiffGraph, graph: OdbGraph, undoable: Boolean): AppliedDiffGraph = {
      val inverseBuilder: InverseBuilder = if (undoable) InverseBuilder.newBuilder else InverseBuilder.noop
      diffGraph.iterator.foreach(change => applyChange(graph, change, inverseBuilder))
      AppliedDiffGraph(
        diffGraph,
        if (undoable) Some(inverseBuilder.build()) else None,
        overlayNodeToOdbNode
      )
    }

    def unapplyDiff(graph: OdbGraph, inverseDiff: DiffGraph): Unit = {
      applyDiff(inverseDiff, graph, false)
    }

    private def applyChange(graph: OdbGraph, change: Change, inverseBuilder: DiffGraph.InverseBuilder) =
      change match {
        case Change.CreateNode(node) => addNode(graph, node, inverseBuilder)
        case c: Change.CreateEdge    => addEdge(c, inverseBuilder)
        case Change.SetNodeProperty(node, key, value) =>
          addNodeProperty(node, key, value, inverseBuilder)
        case Change.SetEdgeProperty(edge, key, value) =>
          addEdgeProperty(edge, key, value, inverseBuilder)
        case Change.RemoveEdge(edge)                      => edge.remove()
        case Change.RemoveEdgeProperty(edge, propertyKey) => edge.property(propertyKey).remove()
        case Change.RemoveNode(nodeId)                    => graph.vertices(nodeId).next().remove()
        case Change.RemoveNodeProperty(nodeId, propertyKey) =>
          graph.vertices(nodeId).next.property(propertyKey).remove()
      }

    private def addEdgeProperty(edge: OdbEdge, key: String, value: AnyRef, inverseBuilder: DiffGraph.InverseBuilder) = {
      inverseBuilder.onBeforeEdgePropertyChange(edge, key)
      edge.setProperty(key, value)
    }

    private def addNodeProperty(node: StoredNode,
                                key: String,
                                value: AnyRef,
                                inverseBuilder: DiffGraph.InverseBuilder) = {
      inverseBuilder.onBeforeNodePropertyChange(node, key)
      node.setProperty(key, value)
    }

    private def addEdge(edgeChange: Change.CreateEdge, inverseBuilder: DiffGraph.InverseBuilder): Unit = {
      val src = edgeChange.src
      val dst = edgeChange.dst

      val srcOdbNode =
        if (edgeChange.sourceNodeKind == Change.NodeKind.New)
          overlayNodeToOdbNode.get(IdentityHashWrapper(src))
        else
          src.asInstanceOf[Node]
      val dstOdbNode =
        if (edgeChange.destinationNodeKind == Change.NodeKind.New)
          overlayNodeToOdbNode.get(IdentityHashWrapper(dst))
        else
          dst.asInstanceOf[Node]
      odbAddEdge(srcOdbNode, dstOdbNode, edgeChange.label, edgeChange.properties, inverseBuilder)
    }

    private def odbAddEdge(src: Node,
                           dst: Node,
                           label: String,
                           properties: Seq[(String, AnyRef)],
                           inverseBuilder: DiffGraph.InverseBuilder): Unit = {
      val odbEdge = src --- label --> dst
      inverseBuilder.onNewEdge(odbEdge)
      properties.foreach {
        case (key, value) =>
          inverseBuilder.onBeforeEdgePropertyChange(odbEdge, key)
          odbEdge.property(key, value)
      }
    }

    private def addNode(graph: OdbGraph, node: NewNode, inverseBuilder: DiffGraph.InverseBuilder): Unit = {
      val newNode = graph + node.label
      inverseBuilder.onNewNode(newNode.asInstanceOf[StoredNode])
      node.properties.foreach {
        case (key, value) if !key.startsWith(InternalProperty) =>
          newNode.setProperty(key, value)
      }
      overlayNodeToOdbNode.put(IdentityHashWrapper(node), newNode)
    }
  }

  object Applier {
    private val InternalProperty = "_"

    def applyDiff(diff: DiffGraph, cpg: Cpg, undoable: Boolean = false): AppliedDiffGraph = {
      val applier = new Applier
      applier.applyDiff(diff, cpg, undoable)
    }

    def applyDiff(diff: DiffGraph, graph: OdbGraph, undoable: Boolean): AppliedDiffGraph = {
      val applier = new Applier
      applier.applyDiff(diff, graph, undoable)
    }

    def unapplyDiff(graph: OdbGraph, inverseDiff: DiffGraph): Unit = {
      val applier = new Applier
      applier.unapplyDiff(graph, inverseDiff)
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
  case class EdgeProperty(edge: OdbEdge, propertyKey: String, propertyValue: AnyRef)
  case class EdgeInDiffGraph(src: NewNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeToOriginal(src: NewNode, dst: StoredNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeFromOriginal(src: StoredNode, dst: NewNode, label: String, properties: Properties) extends DiffEdge
  case class EdgeInOriginal(src: StoredNode, dst: StoredNode, label: String, properties: Properties) extends DiffEdge
}
