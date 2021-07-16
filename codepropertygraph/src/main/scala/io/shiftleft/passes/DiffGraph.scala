package io.shiftleft.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{AbstractNode, NewNode, StoredNode}
import io.shiftleft.proto.cpg.Cpg.{DiffGraph => DiffGraphProto}
import overflowdb._
import overflowdb.traversal._

import java.security.MessageDigest
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}
import scala.compat.java8.OptionConverters.RichOptionalGeneric
import scala.jdk.CollectionConverters._

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
      case Change.SetNodeProperty(node, key, value) =>
        DiffGraph.NodeProperty(node, key, value)
    }.toVector
  def edgeProperties: Vector[EdgeProperty] =
    iterator.collect {
      case Change.SetEdgeProperty(edge, key, value) =>
        DiffGraph.EdgeProperty(edge, key, value)
    }.toVector
}

object DiffGraph {
  type Properties = Seq[(String, AnyRef)]
  type PackedProperties = Array[Any]

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
    final case class RemoveEdge(edge: Edge) extends Change
    final case class RemoveEdgeProperty(edge: Edge, propertyKey: String) extends Change
    final case class CreateNode(node: NewNode) extends Change
    final case class SetNodeProperty(node: StoredNode, key: String, value: AnyRef) extends Change
    final case class SetEdgeProperty(edge: Edge, propertyKey: String, propertyValue: AnyRef) extends Change
    final case class CreateEdge(src: AbstractNode, dst: AbstractNode, label: String, packedProperties: PackedProperties)
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
      def apply(src: AbstractNode, dst: AbstractNode, label: String, properties: Properties): CreateEdge =
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

          val edge = cpg.graph.V(outNodeId).outE(edgeLabel).filter(_.inNode.id == inNodeId).l match {
            case edge :: Nil => edge
            case Nil         => throw new AssertionError(s"unable to find edge that is supposed to be removed: $proto")
            case candidates => // found multiple edges - try to disambiguate via propertiesHash
              val wantedPropertiesHash: List[Byte] = proto.getPropertiesHash.toByteArray.toList
              candidates.filter(edge => propertiesHash(edge.asInstanceOf[Edge]).toList == wantedPropertiesHash) match {
                case edge :: Nil => edge
                case Nil =>
                  throw new AssertionError(
                    s"unable to find edge that is supposed to be removed: $proto. n.b. before filtering on propertiesHash, multiple candidates have been found: $candidates")
                case candidates =>
                  throw new AssertionError(
                    s"unable to disambiguate the edge to be removed, since multiple edges match the filter conditions of $proto. Candidates=$candidates")
              }
          }
          builder.removeEdge(edge.asInstanceOf[Edge])
        case _ => // TODO nasty, i know. sorry. yolo :(
      }
    }

    builder.build()
  }

  def propertiesHash(edge: Edge): Array[Byte] = {
    val propertiesAsString = edge.propertiesMap.asScala.toList.sortBy(_._1).mkString
    MessageDigest.getInstance("MD5").digest(propertiesAsString.getBytes)
  }

  def newBuilder: Builder = new Builder()

  class Builder {
    private var _buffer: mutable.ArrayBuffer[Change] = null
    private var _nodeSet: java.util.IdentityHashMap[NewNode, NewNode] = null
    private def buffer: mutable.ArrayBuffer[Change] = {
      if (_buffer == null)
        _buffer = new mutable.ArrayBuffer[Change]()
      _buffer
    }

    def +=(node: NewNode): Unit = addNode(node)

    def addEdge(src: AbstractNode,
                dst: AbstractNode,
                edgeLabel: String,
                properties: Seq[(String, AnyRef)] = List()): Unit = {
      buffer.append(Change.CreateEdge(src, dst, edgeLabel, properties))
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

    def addNode(node: NewNode): Boolean = {
      if (_nodeSet == null) _nodeSet = new java.util.IdentityHashMap[NewNode, NewNode]()
      if (_nodeSet.put(node, node) == null) {
        buffer.append(Change.CreateNode(node))
        true
      } else false
    }

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
      buffer.append(Change.SetNodeProperty(node, key, value))
    def addEdgeProperty(edge: Edge, key: String, value: AnyRef): Unit =
      buffer.append(Change.SetEdgeProperty(edge, key, value))
    def removeNode(id: Long): Unit =
      buffer.append(Change.RemoveNode(id))
    def removeNode(node: StoredNode): Unit =
      buffer.append(Change.RemoveNode(node.id))
    def removeEdge(edge: Edge): Unit = buffer += Change.RemoveEdge(edge)
    def removeNodeProperty(nodeId: Long, propertyKey: String): Unit =
      buffer.append(Change.RemoveNodeProperty(nodeId, propertyKey))
    def removeEdgeProperty(edge: Edge, propertyKey: String): Unit =
      buffer.append(Change.RemoveEdgeProperty(edge, propertyKey))
  }

  abstract class InverseBuilder {
    def onNewNode(node: StoredNode): Unit
    def onNewEdge(edge: Edge): Unit
    def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String): Unit
    def onBeforeEdgePropertyChange(edge: Edge, propertyKey: String): Unit
    def build(): DiffGraph
  }
  object InverseBuilder {
    def newBuilder: InverseBuilder = new InverseBuilderImpl
    val noop = NoopInverseBuilder
  }

  class InverseBuilderImpl extends InverseBuilder {
    private val builder = DiffGraph.newBuilder

    def onNewNode(node: StoredNode) = builder.removeNode(node)
    def onNewEdge(edge: Edge) = builder.removeEdge(edge)

    def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String) =
      node.propertyOption(propertyKey).asScala match {
        case Some(value) if value != node.propertyDefaultValue(propertyKey) =>
          builder.addNodeProperty(node, propertyKey, value)
        case _ =>
          builder.removeNodeProperty(node.id, propertyKey)
      }

    def onBeforeEdgePropertyChange(edge: Edge, propertyKey: String) = {
      val prop = edge.propertyOption(propertyKey)
      if (prop.isPresent)
        builder.addEdgeProperty(edge, propertyKey, prop.get)
      else
        builder.removeEdgeProperty(edge, propertyKey)
    }

    def build(): DiffGraph = builder.buildReverse()
  }

  object NoopInverseBuilder extends InverseBuilder {
    def onNewNode(node: StoredNode) = ()
    def onNewEdge(edge: Edge) = ()
    def onBeforeNodePropertyChange(node: StoredNode, propertyKey: String) = ()
    def onBeforeEdgePropertyChange(edge: Edge, propertyKey: String) = ()
    def build(): DiffGraph = EmptyChangeSet
  }

  private class Applier(diffGraph: DiffGraph, graph: Graph, undoable: Boolean, keyPool: Option[KeyPool]) {
    private val overlayNodeToOdbNode = new java.util.IdentityHashMap[NewNode, StoredNode]()
    private val deferredInitList = mutable.ArrayDeque[NewNode]()
    val inverseBuilder: InverseBuilder = if (undoable) InverseBuilder.newBuilder else InverseBuilder.noop

    def nodeMapping(newNode: NewNode): StoredNode = {
      val alreadyStoredNode = overlayNodeToOdbNode.get(newNode)
      if (alreadyStoredNode == null) {
        val newlyStoredNode = keyPool match {
          case Some(pool) => graph.addNode(pool.next, newNode.label).asInstanceOf[StoredNode]
          case None       => graph.addNode(newNode.label).asInstanceOf[StoredNode]
        }
        overlayNodeToOdbNode.put(newNode, newlyStoredNode)
        deferredInitList.append(newNode)
        newlyStoredNode
      } else alreadyStoredNode
    }

    def drainDeferred(): Unit = {
      while (deferredInitList.nonEmpty) {
        val newNode = deferredInitList.removeHead()
        val stored = overlayNodeToOdbNode.get(newNode)
        stored.fromNewNode(newNode, nodeMapping)
        inverseBuilder.onNewNode(stored)
      }
    }

    def run(): AppliedDiffGraph = {
      for (change <- diffGraph.iterator) {
        change match {
          case Change.CreateNode(node) =>
            tryAddNodeInit(node)
          case c: Change.CreateEdge => addEdge(c)
          case Change.SetNodeProperty(node, key, value) =>
            addNodeProperty(node, key, value)
          case Change.SetEdgeProperty(edge, key, value) =>
            //deprecate?
            addEdgeProperty(edge, key, value, inverseBuilder)
          case Change.RemoveEdge(edge)                      => edge.remove()
          case Change.RemoveEdgeProperty(edge, propertyKey) =>
            //deprecate?
            edge.removeProperty(propertyKey)
          case Change.RemoveNode(nodeId) => graph.node(nodeId).remove()
          case Change.RemoveNodeProperty(nodeId, propertyKey) =>
            graph.node(nodeId).removeProperty(propertyKey)
        }
        drainDeferred()
      }

      AppliedDiffGraph(
        diffGraph,
        if (undoable) Some(inverseBuilder.build()) else None,
        overlayNodeToOdbNode
      )
    }

    private def addEdgeProperty(edge: Edge, key: String, value: AnyRef, inverseBuilder: DiffGraph.InverseBuilder) = {
      inverseBuilder.onBeforeEdgePropertyChange(edge, key)
      edge.setProperty(key, value)
    }

    private def addNodeProperty(node: StoredNode, key: String, value: AnyRef) = {
      inverseBuilder.onBeforeNodePropertyChange(node, key)
      node.setProperty(key, value)
    }

    private def addEdge(edgeChange: Change.CreateEdge): Unit = {
      val src = edgeChange.src
      val dst = edgeChange.dst

      val srcOdbNode =
        if (edgeChange.sourceNodeKind == Change.NodeKind.New)
          nodeMapping(src.asInstanceOf[NewNode])
        else
          src.asInstanceOf[Node]
      val dstOdbNode =
        if (edgeChange.destinationNodeKind == Change.NodeKind.New)
          nodeMapping(dst.asInstanceOf[NewNode])
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
          odbEdge.setProperty(key, value)
      }
    }
    private def tryAddNodeInit(node: NewNode): Unit = {
      if (overlayNodeToOdbNode.get(node) == null) {
        val newNode = keyPool match {
          case Some(pool) => graph.addNode(pool.next, node.label).asInstanceOf[StoredNode]
          case None       => graph.addNode(node.label).asInstanceOf[StoredNode]
        }
        inverseBuilder.onNewNode(newNode.asInstanceOf[StoredNode])
        newNode.fromNewNode(node, mapping = nodeMapping)
        overlayNodeToOdbNode.put(node, newNode)
      }
    }
  }

  object Applier {
    def applyDiff(diff: DiffGraph,
                  cpg: Cpg,
                  undoable: Boolean = false,
                  keyPool: Option[KeyPool] = None): AppliedDiffGraph = {
      val applier = new Applier(diff, cpg.graph, undoable, keyPool)
      applier.run()
    }

    def applyDiff(diff: DiffGraph, graph: Graph, undoable: Boolean, keyPool: Option[KeyPool]): AppliedDiffGraph = {
      val applier = new Applier(diff, graph, undoable, keyPool)
      applier.run()
    }

    def unapplyDiff(graph: Graph, inverseDiff: DiffGraph): Unit = {
      val applier = new Applier(inverseDiff, graph, undoable = false, keyPool = None)
      applier.run()
    }
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
