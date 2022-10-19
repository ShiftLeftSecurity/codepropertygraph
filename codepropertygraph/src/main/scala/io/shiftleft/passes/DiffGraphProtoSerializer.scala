package io.shiftleft.passes

import com.google.protobuf.ByteString
import io.shiftleft.codepropertygraph.generated.nodes.{AbstractNode, NewNode, StoredNode}
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType
import io.shiftleft.proto.cpg.Cpg.{
  AdditionalEdgeProperty,
  AdditionalNodeProperty,
  BoolList,
  ContainedRefs,
  CpgOverlay,
  CpgStruct,
  DiffGraph => DiffGraphProto,
  DoubleList,
  EdgePropertyName,
  FloatList,
  IntList,
  LongList,
  NodePropertyName,
  PropertyValue,
  StringList
}
import org.slf4j.LoggerFactory.getLogger
import overflowdb._
import overflowdb.traversal.{jIteratortoTraversal, toNodeTraversalViaAdditionalImplicit}

import java.lang.{Long => JLong}
import java.security.MessageDigest
import scala.collection.mutable
import scala.jdk.CollectionConverters._

object DiffGraphProtoSerializer {
  val nodePropertyNames: Set[String] = NodePropertyName.values().map { _.name() }.toSet

  def deserialize(inverseDiffGraphProto: DiffGraphProto, graph: Graph): overflowdb.BatchedUpdate.DiffGraphBuilder = {
    def propertiesHash(edge: Edge): Array[Byte] = {
      val propertiesAsString = edge.propertiesMap.asScala.toList.sortBy(_._1).mkString
      MessageDigest.getInstance("MD5").digest(propertiesAsString.getBytes)
    }
    val builder = new BatchedUpdate.DiffGraphBuilder
    import DiffGraphProto.Entry.ValueCase._
    inverseDiffGraphProto.getEntriesList.forEach { entry =>
      entry.getValueCase match {
        case REMOVE_NODE =>
          builder.removeNode(graph.nodes(entry.getRemoveNode.getKey).next)
        case REMOVE_NODE_PROPERTY =>
          val proto = entry.getRemoveNodeProperty
          builder.setNodeProperty(graph.nodes(proto.getKey).next(), proto.getName.toString, null)
        case REMOVE_EDGE =>
          val proto     = entry.getRemoveEdge
          val outNodeId = proto.getOutNodeKey
          val inNodeId  = proto.getInNodeKey
          val edgeLabel = proto.getEdgeType.toString

          val edge = toNodeTraversalViaAdditionalImplicit(graph.V(outNodeId))
            .outE(edgeLabel)
            .filter(_.inNode.id == inNodeId)
            .l match {
            case edge :: Nil => edge
            case Nil         => throw new AssertionError(s"unable to find edge that is supposed to be removed: $proto")
            case candidates => // found multiple edges - try to disambiguate via propertiesHash
              val wantedPropertiesHash: List[Byte] = proto.getPropertiesHash.toByteArray.toList
              candidates.filter(edge => propertiesHash(edge.asInstanceOf[Edge]).toList == wantedPropertiesHash) match {
                case edge :: Nil => edge
                case Nil =>
                  throw new AssertionError(
                    s"unable to find edge that is supposed to be removed: $proto. n.b. before filtering on propertiesHash, multiple candidates have been found: $candidates"
                  )
                case candidates =>
                  throw new AssertionError(
                    s"unable to disambiguate the edge to be removed, since multiple edges match the filter conditions of $proto. Candidates=$candidates"
                  )
              }
          }
          builder.removeEdge(edge.asInstanceOf[Edge])
        case _ => // TODO nasty, i know. sorry. yolo :(
      }
    }
    builder
  }

  def addNode(node: StoredNode): CpgStruct.Node = {
    val nodeBuilder = CpgStruct.Node.newBuilder
      .setKey(node.id())
      .setType(NodeType.valueOf(node.label))
    node.propertiesMap.asScala.foreach {
      case (key, value) if !key.startsWith("_") =>
        val property = nodeProperty(key, value, null)
        nodeBuilder.addProperty(property)
    }

    nodeBuilder.build
  }
  def addNode(node: NewNode, nodeToId: NewNode => Long): CpgStruct.Node = {
    val nodeId = nodeToId(node)

    val nodeBuilder = CpgStruct.Node.newBuilder
      .setKey(nodeId)
      .setType(NodeType.valueOf(node.label))

    node.properties.foreach {
      case (key, value) if !key.startsWith("_") =>
        val property = nodeProperty(key, value, nodeToId)
        nodeBuilder.addProperty(property)
    }

    nodeBuilder.build
  }

  def propertiesHash(edge: Edge): Array[Byte] = {
    val propertiesAsString = edge.propertiesMap.asScala.toList.sortBy(_._1).mkString
    MessageDigest.getInstance("MD5").digest(propertiesAsString.getBytes)
  }

  def makeEdge(label: String, srcId: Long, dstId: Long, properties: Seq[(String, AnyRef)]) = {
    val edgeBuilder = CpgStruct.Edge.newBuilder()

    edgeBuilder
      .setSrc(srcId)
      .setDst(dstId)
      .setType(EdgeType.valueOf(label))

    properties.foreach { property =>
      edgeBuilder.addProperty(edgeProperty(property._1, property._2))
    }

    edgeBuilder.build()
  }

  def removeNodeProto(nodeId: Long) =
    DiffGraphProto.RemoveNode.newBuilder.setKey(nodeId).build

  def removeEdgeProto(edge: Edge) =
    DiffGraphProto.RemoveEdge.newBuilder
      .setOutNodeKey(edge.outNode.id)
      .setInNodeKey(edge.inNode.id)
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setPropertiesHash(ByteString.copyFrom(propertiesHash(edge.asInstanceOf[Edge])))
      .build

  def removeNodePropertyProto(nodeId: Long, propertyKey: String) = {
    if (!DiffGraphProtoSerializer.nodePropertyNames.contains(propertyKey)) {
      DiffGraphProto.RemoveNodeProperty.newBuilder
        .setKey(nodeId)
        .setName(NodePropertyName.CONTAINED_REF)
        .setLocalName(propertyKey)
        .build
    } else {
      DiffGraphProto.RemoveNodeProperty.newBuilder
        .setKey(nodeId)
        .setName(NodePropertyName.valueOf(propertyKey))
        .build
    }
  }

  def removeEdgePropertyProto(edge: Edge, propertyKey: String) =
    DiffGraphProto.RemoveEdgeProperty.newBuilder
      .setOutNodeKey(edge.outNode.id)
      .setInNodeKey(edge.inNode.id)
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setPropertyName(EdgePropertyName.valueOf(propertyKey))
      .build

  def nodeProperty(key: String, value: Any, nodeToId: NewNode => Long) = {
    if (!DiffGraphProtoSerializer.nodePropertyNames.contains(key)) {
      CpgStruct.Node.Property
        .newBuilder()
        .setName(NodePropertyName.CONTAINED_REF)
        .setValue(PropertyValue.newBuilder().setContainedRefs(protoForNodes(value, nodeToId).setLocalName(key).build))
        .build
    } else {
      CpgStruct.Node.Property
        .newBuilder()
        .setName(NodePropertyName.valueOf(key))
        .setValue(protoValue(value))
        .build()
    }
  }

  def edgeProperty(key: String, value: Any) =
    CpgStruct.Edge.Property
      .newBuilder()
      .setName(EdgePropertyName.valueOf(key))
      .setValue(protoValue(value))
      .build()

  def addNodeProperty(nodeId: Long, key: String, value: AnyRef, nodeToId: NewNode => Long): AdditionalNodeProperty =
    AdditionalNodeProperty.newBuilder
      .setNodeId(nodeId)
      .setProperty(nodeProperty(key, value, nodeToId))
      .build

  def addEdgeProperty(edge: Edge, key: String, value: AnyRef): AdditionalEdgeProperty =
    AdditionalEdgeProperty.newBuilder
      .setOutNodeKey(edge.outNode.id)
      .setInNodeKey(edge.inNode.id)
      .setEdgeType(EdgeType.valueOf(edge.label))
      .setProperty(
        CpgStruct.Edge.Property.newBuilder
          .setName(EdgePropertyName.valueOf(key))
          .setValue(protoValue(value))
      )
      .build

  def protoForNodes(value: Any, nodeToId: NewNode => Long): ContainedRefs.Builder = {
    val builder = ContainedRefs.newBuilder
    value match {
      case iterable: Iterable[_] =>
        iterable.foreach {
          case storedNode: StoredNode =>
            builder.addRefs(storedNode.id)
          case newNode: NewNode =>
            if (nodeToId == null) {
              throw new NullPointerException(
                s"Cannot serialize references to NewNode ${newNode} without AppliedDiffGraph: NodeID is not yet assigned"
              )
            }
            builder.addRefs(nodeToId(newNode))
        }
      case storedNode: StoredNode =>
        builder.addRefs(storedNode.id)
      case newNode: NewNode =>
        if (nodeToId == null) {
          throw new NullPointerException(
            s"Cannot serialize references to NewNode ${newNode} without AppliedDiffGraph: NodeID is not yet assigned"
          )
        }
        builder.addRefs(nodeToId(newNode))
    }
    builder
  }

  private def protoValue(value: Any): PropertyValue.Builder = {
    val builder = PropertyValue.newBuilder
    value match {
      case iterable: Iterable[_] if iterable.isEmpty => builder // empty property
      case iterable: Iterable[_]                     =>
        // determine property list type based on first element - assuming it's a homogeneous list
        iterable.head match {
          case _: String =>
            val b = StringList.newBuilder
            iterable.asInstanceOf[Iterable[String]].foreach(b.addValues)
            builder.setStringList(b)
          case _: Boolean =>
            val b = BoolList.newBuilder
            iterable.asInstanceOf[Iterable[Boolean]].foreach(b.addValues)
            builder.setBoolList(b)
          case _: Int =>
            val b = IntList.newBuilder
            iterable.asInstanceOf[Iterable[Int]].foreach(b.addValues)
            builder.setIntList(b)
          case _: Long =>
            val b = LongList.newBuilder
            iterable.asInstanceOf[Iterable[Long]].foreach(b.addValues)
            builder.setLongList(b)
          case _: Float =>
            val b = FloatList.newBuilder
            iterable.asInstanceOf[Iterable[Float]].foreach(b.addValues)
            builder.setFloatList(b)
          case _: Double =>
            val b = DoubleList.newBuilder
            iterable.asInstanceOf[Iterable[Double]].foreach(b.addValues)
            builder.setDoubleList(b)
          case _ => throw new RuntimeException("Unsupported primitive value type " + value.getClass)
        }
      case value => protoValueForPrimitive(value)
    }
  }

  def protoValueForPrimitive(value: Any): PropertyValue.Builder = {
    val builder = PropertyValue.newBuilder
    value match {
      case v: String  => builder.setStringValue(v)
      case v: Boolean => builder.setBoolValue(v)
      case v: Int     => builder.setIntValue(v)
      case v: JLong   => builder.setLongValue(v)
      case v: Float   => builder.setFloatValue(v)
      case v: Double  => builder.setDoubleValue(v)
      case _          => throw new RuntimeException("Unsupported primitive value type " + value.getClass)
    }
  }
}

object BatchUpdateListenerLogger {
  val logger = getLogger(getClass)
}

class BatchUpdateBiListener(forward: Boolean, inverse: Boolean) extends overflowdb.BatchedUpdate.ModificationListener {
  val forwardListener   = if (forward) new BatchUpdateForwardListener else null
  val backwardsListener = if (inverse) new BatchUpdateInverseListener else null

  def getForward: Option[CpgOverlay] = if (forwardListener != null) Some(forwardListener.builder.build()) else None

  def getBackward: Option[DiffGraphProto] =
    if (backwardsListener != null) Some(backwardsListener.getSerialization()) else None

  override def onAfterInitNewNode(node: Node): Unit = {
    if (forwardListener != null) forwardListener.onAfterInitNewNode(node)
    if (backwardsListener != null) backwardsListener.onAfterInitNewNode(node)
  }

  override def onAfterAddNewEdge(edge: Edge): Unit = {
    if (forwardListener != null) forwardListener.onAfterAddNewEdge(edge)
    if (backwardsListener != null) backwardsListener.onAfterAddNewEdge(edge)
  }

  override def onBeforePropertyChange(node: Node, key: String): Unit = {
    if (forwardListener != null) forwardListener.onBeforePropertyChange(node: Node, key: String)
    if (backwardsListener != null) backwardsListener.onBeforePropertyChange(node: Node, key: String)
  }

  override def onAfterPropertyChange(node: Node, key: String, value: Any): Unit = {
    if (forwardListener != null) forwardListener.onAfterPropertyChange(node: Node, key: String, value: Any)
    if (backwardsListener != null) backwardsListener.onAfterPropertyChange(node: Node, key: String, value: Any)
  }

  override def onBeforeRemoveNode(node: Node): Unit = {
    if (forwardListener != null) forwardListener.onBeforeRemoveNode(node: Node)
    if (backwardsListener != null) backwardsListener.onBeforeRemoveNode(node: Node)
  }

  override def onBeforeRemoveEdge(edge: Edge): Unit = {
    if (forwardListener != null) forwardListener.onBeforeRemoveEdge(edge: Edge)
    if (backwardsListener != null) backwardsListener.onBeforeRemoveEdge(edge: Edge)
  }

  override def finish(): Unit = {
    if (forwardListener != null) forwardListener.finish()
    if (backwardsListener != null) backwardsListener.finish()
  }
}

class BatchUpdateForwardListener extends overflowdb.BatchedUpdate.ModificationListener {
  import DiffGraphProtoSerializer._
  val builder   = CpgOverlay.newBuilder
  var hasLogged = false
  import BatchUpdateListenerLogger.logger

  def nodeToId(nn: AbstractNode): Long = nn.asInstanceOf[StoredNode].id()

  override def onAfterInitNewNode(node: Node): Unit = builder.addNode(addNode(node.asInstanceOf[StoredNode]))

  override def onAfterAddNewEdge(edge: Edge): Unit =
    builder.addEdge(
      makeEdge(
        edge.label(),
        srcId = edge.outNode().id(),
        dstId = edge.inNode().id(),
        properties = edge
          .propertiesMap()
          .entrySet()
          .iterator()
          .asScala
          .map { t =>
            (t.getKey, t.getValue)
          }
          .toList
      )
    )

  override def onBeforePropertyChange(node: Node, key: String): Unit = {}

  override def onAfterPropertyChange(node: Node, key: String, value: Any): Unit =
    builder.addNodeProperty(addNodeProperty(node.id, key, value.asInstanceOf[AnyRef], nodeToId))

  override def onBeforeRemoveNode(node: Node): Unit = if (!hasLogged) {
    hasLogged = true
    logger.warn("CpgOverlays can be stacked onto each other, therefore they cannot remove nodes from the graph")
  }

  override def onBeforeRemoveEdge(edge: Edge): Unit = if (!hasLogged) {
    hasLogged = true
    logger.warn("CpgOverlays can be stacked onto each other, therefore they cannot remove edges from the graph")
  }

  override def finish(): Unit = {}
}

class BatchUpdateInverseListener extends overflowdb.BatchedUpdate.ModificationListener {
  import DiffGraphProtoSerializer._
  val buffer    = mutable.ArrayBuffer[DiffGraphProto.Entry]()
  var hasLogged = false
  import BatchUpdateListenerLogger.logger

  private def addEntry(entry: DiffGraphProto.Entry.Builder): Unit = buffer.append(entry.build())

  def getSerialization(): DiffGraphProto = {
    val builder = DiffGraphProto.newBuilder()
    buffer.reverseIterator.foreach(builder.addEntries)
    builder.build()
  }

  private def nodeToId(nn: AbstractNode): Long = nn match {
    case stored: StoredNode => stored.id()
    case newNode: NewNode   => newNode.stored.get.id()
  }
  private def newEntry = DiffGraphProto.Entry.newBuilder

  override def onAfterInitNewNode(node: Node): Unit =
    addEntry(newEntry.setRemoveNode(removeNodeProto(node.id())))

  override def onAfterAddNewEdge(edge: Edge): Unit =
    addEntry(newEntry.setRemoveEdge(removeEdgeProto(edge)))

  override def onBeforePropertyChange(node: Node, key: String): Unit = {
    val n      = node.asInstanceOf[StoredNode]
    val oldval = node.propertyOption(key)
    if (oldval.isPresent)
      addEntry(newEntry.setNodeProperty(addNodeProperty(node.id(), key, oldval.get(), nodeToId)))
    else addEntry(newEntry.setRemoveNodeProperty(removeNodePropertyProto(n.id(), key)))

  }

  override def onAfterPropertyChange(node: Node, key: String, value: Any): Unit = {}

  override def onBeforeRemoveNode(node: Node): Unit = if (!hasLogged) {
    hasLogged = true
    // we _do_ support inversion of node removal now. We don't support edge removal, though.
    logger.warn("We currently do not support inversion of node removal")
  }

  override def onBeforeRemoveEdge(edge: Edge): Unit = if (!hasLogged) {
    hasLogged = true
    logger.warn("We currently do not support inversion of edge removal")
  }

  override def finish(): Unit = {}
}
