package io.shiftleft.passes

import java.util

import gremlin.scala.{Edge, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import io.shiftleft.proto.cpg.Cpg._
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Node.NodeType

import scala.collection.JavaConverters._
import java.lang.{Long => JLong}

import io.shiftleft.codepropertygraph.Cpg

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future, blocking}

/**
  * Base class for CPG pass - a program, which receives an input graph
  * and outputs a sequence of additive diff graphs. These diff graphs can
  * be merged into the original graph ("applied"), they can be serialized
  * into a binary format, and finally, they can be added to an existing
  * cpg.bin.zip file.
  *
  * A pass is provided by inheriting from this class and implementing `run`,
  * a method, which creates the sequence of diff graphs from an input graph.
  *
  * Overview of steps and their meaning:
  *
  * 1. Create: A sequence of diff graphs is created from the source graph
  * 2. Apply: Each diff graph can be applied to the source graph
  * 3. Serialize: After applying a diff graph, the diff graph can be serialized into a CPG overlay
  * 4. Store: The CPG overlay can be stored in a serialized CPG.
  *
  * @param cpg the source CPG this pass traverses
  */
abstract class CpgPass(cpg: Cpg) {
  import CpgPass.logger
  implicit val executionContext: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global

  /**
    * Main method of enhancement - to be implemented by child class
    * */
  def run(): Iterator[DiffGraph]

  /**
    * Name of the enhancement pass.
    * By default it is inferred from the name of the class, override if needed.
    */
  def name: String = getClass.getName

  /**
    * Run a CPG pass to create diff graphs, apply diff graphs, create corresponding
    * overlays and add them to the serialized CPG. The name of the overlay is derived
    * from the class name of the pass.
    *
    * @param serializedCpg the destination serialized CPG to add overlays to
    * @param counter an optional integer to keep apart different runs of the same pass
    * */
  def createApplySerializeAndStore(serializedCpg: SerializedCpg, counter: Int = 0): Unit = {
    val overlays = createApplyAndSerialize()
    overlays.zipWithIndex.foreach {
      case (overlay, index) => {
        if (overlay.getSerializedSize > 0) {
          serializedCpg.addOverlay(overlay, getClass.getSimpleName + counter.toString + "_" + index)
        }
      }
    }
  }

  def createApplySerializeAndStoreAsync(serializedCpg: SerializedCpg, counter: Int = 0): Unit = {
    val overlays: Iterator[CpgOverlay] = createApplyAndSerialize()
    val futures: List[Future[Unit]] = overlays.zipWithIndex.map {
      case (overlay, index) => {
        if (overlay.getSerializedSize > 0) {
        Future {
            blocking {
              serializedCpg.addOverlay(overlay, getClass.getSimpleName + counter.toString + "_" + index)
            }
          }
        } else {
          Future.unit
        }
      }
    }.toList
    futures.foreach(Await.result(_, Duration.Inf))
  }

  /**
    * Execute and create a serialized overlay
    */
  def createApplyAndSerialize(): Iterator[CpgOverlay] = {
    try {
      logStart()
      run().map { dstGraph =>
        val appliedDiffGraph = DiffGraph.Applier.applyDiff(dstGraph, cpg)
        new DiffGraphProtoSerializer().serialize(appliedDiffGraph)
      }
    } finally {
      logEnd()
    }
  }

  /**
    * Execute the enhancement and apply result to the underlying graph
    */
  def createAndApply(): Unit = {
    logStart()
    try {
      run().foreach(diff => DiffGraph.Applier.applyDiff(diff, cpg))
    } finally {
      logEnd()
    }
  }

  private var startTime: Long = _

  private def logStart(): Unit = {
    logger.info(s"Start of enhancement: ${name}")
    startTime = System.currentTimeMillis()
  }

  private def logEnd(): Unit = {
    val endTime = System.currentTimeMillis()
    logger.info(s"End of enhancement: ${name}, after ${endTime - startTime}ms")
  }

}

object CpgPass {
  private val logger: Logger = LogManager.getLogger(classOf[CpgPass])

}

/**
  * Diff Graph that has been applied to a source graph. This is a wrapper around
  * diff graph, which additionally provides a map from nodes to graph ids.
  * */
case class AppliedDiffGraph(diffGraph: DiffGraph,
                            private val nodeToTinkerNode: util.HashMap[IdentityHashWrapper[NewNode], Vertex]) {

  /**
    * Obtain the id this node has in the applied graph
    * */
  def nodeToGraphId(node: NewNode): JLong = {
    val wrappedNode = IdentityHashWrapper(node)
    nodeToTinkerNode.get(wrappedNode).id.asInstanceOf[JLong]
  }
}

private[passes] case class IdentityHashWrapper[T <: AnyRef](value: T) {
  override def hashCode(): Int = {
    System.identityHashCode(value)
  }

  override def equals(other: Any): Boolean =
    other != null &&
      other.isInstanceOf[IdentityHashWrapper[T]] &&
      (this.value eq other.asInstanceOf[IdentityHashWrapper[T]].value)
}

/**
  * Provides functionality to serialize diff graphs and add them
  * to existing serialized CPGs as graph overlays.
  * */
private[passes] class DiffGraphProtoSerializer() {
  private val logger = LogManager.getLogger(getClass)
  /**
    * Generates a serialized graph overlay representing this graph
    * */
  def serialize(appliedDiffGraph: AppliedDiffGraph): CpgOverlay = {
    implicit val builder = CpgOverlay.newBuilder()
    implicit val graph = appliedDiffGraph
    val diff = appliedDiffGraph.diffGraph
    diff.iterator.foreach {
      case c:DiffGraph.Change.CreateEdge => addEdge(c, builder, appliedDiffGraph)
      case DiffGraph.Change.CreateNode(newNode) => addNode(builder, newNode, appliedDiffGraph)
      case DiffGraph.Change.SetNodeProperty(node, key, value) => addNodeProperty(node.getId, key, value, builder, appliedDiffGraph)
      case DiffGraph.Change.SetEdgeProperty(_, _, _) => ???
    }
    val overlay = builder.build()
    overlay
  }

  private def addNode(implicit builder: CpgOverlay.Builder, node: NewNode, appliedDiffGraph: AppliedDiffGraph): Unit = {
    val nodeId = appliedDiffGraph.nodeToGraphId(node)

    val nodeBuilder = CpgStruct.Node
      .newBuilder() // TODO: Can we cache builders???
      .setKey(nodeId)
      .setType(NodeType.valueOf(node.label))

    node.properties
      .foreach {
        case (key, value) if !key.startsWith("_") =>
          val property = nodeProperty(key, value)
          nodeBuilder.addProperty(property)
      }

    val finalNode = nodeBuilder.build()
    builder.addNode(finalNode)
  }

  private def addEdge(change: DiffGraph.Change.CreateEdge, builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph): Unit = {
    val diffGraph = appliedDiffGraph.diffGraph
    val srcId =
      if (change.sourceNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.src.asInstanceOf[NewNode])
      else
        change.src.getId

    val dstId =
      if (change.destinationNodeKind == DiffGraph.Change.NodeKind.New)
        appliedDiffGraph.nodeToGraphId(change.dst.asInstanceOf[NewNode])
      else
        change.dst.getId

    builder.addEdge(makeEdge(change.label, srcId, dstId, change.properties))
  }

  private def makeEdge(label: String, srcId: Long, dstId: Long, properties: DiffGraph.Properties) = {
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

  private def nodeProperty(key: String, value: Any) = {
    CpgStruct.Node.Property
      .newBuilder()
      .setName(NodePropertyName.valueOf(key))
      .setValue(protoValue(value))
      .build()
  }

  private def edgeProperty(key: String, value: Any) =
    CpgStruct.Edge.Property
      .newBuilder()
      .setName(EdgePropertyName.valueOf(key))
      .setValue(protoValue(value))
      .build()

  private def addNodeProperty(nodeId: Long, key: String, value: AnyRef, builder: CpgOverlay.Builder, appliedDiffGraph: AppliedDiffGraph): Unit = {
    builder.addNodeProperty(
        AdditionalNodeProperty
          .newBuilder()
          .setNodeId(nodeId)
          .setProperty(nodeProperty(key, value))
          .build
    )
  }

  private def protoValue(value: Any): PropertyValue.Builder = {
    val builder = PropertyValue.newBuilder
    value match {
      case t: Traversable[_] if t.isEmpty =>
        builder //empty property
      case t: Traversable[_] =>
        // determine property list type based on first element - assuming it's a homogeneous list
        t.head match {
          case _: String =>
            val b = StringList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[String]))
            builder.setStringList(b)
          case _: Boolean =>
            val b = BoolList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Boolean]))
            builder.setBoolList(b)
          case _: Int =>
            val b = IntList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Int]))
            builder.setIntList(b)
          case _: Long =>
            val b = LongList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Long]))
            builder.setLongList(b)
          case _: Float =>
            val b = FloatList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Float]))
            builder.setFloatList(b)
          case _: Double =>
            val b = DoubleList.newBuilder
            t.foreach(value => b.addValues(value.asInstanceOf[Double]))
            builder.setDoubleList(b)
          case _ => throw new RuntimeException("Unsupported primitive value type " + value.getClass)
        }
      case value => protoValueForPrimitive(value)
    }
  }

  private def protoValueForPrimitive(value: Any): PropertyValue.Builder = {
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
