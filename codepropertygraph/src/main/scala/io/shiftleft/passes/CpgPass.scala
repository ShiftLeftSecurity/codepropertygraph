package io.shiftleft.passes

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.NewNode
import io.shiftleft.SerializedCpg
import io.shiftleft.proto.cpg.Cpg._
import java.util
import java.lang.{Long => JLong}

import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import org.apache.logging.log4j.{LogManager, Logger}
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality

import scala.concurrent.duration.DurationLong

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
abstract class CpgPass(cpg: Cpg, outName: String = "") {
  import CpgPass.logger

  def outputName : String = {
    if (outName.isEmpty) {
      this.getClass.getSimpleName()
    } else {
     outName
    }
  }

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
    * */
  def createApplySerializeAndStore(serializedCpg: SerializedCpg): Unit = {
    if (serializedCpg.isEmpty) {
      createAndApply()
    } else {
      val overlays = createApplyAndSerialize()
      overlays.zipWithIndex.foreach {
        case (overlay, index) => {
          if (overlay.getSerializedSize > 0) {
            serializedCpg.addOverlay(overlay, outputName + "_" + index)
          }
        }
      }
    }
  }

  /**
    * Execute and create a serialized overlay
    */
  def createApplyAndSerialize(): Iterator[CpgOverlay] =
    withStartEndTimesLogged {
      val overlays = run().map { diffGraph =>
        val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg)
        new DiffGraphProtoSerializer().serialize(appliedDiffGraph)
      }
      overlays
    }

  /**
    * Execute the enhancement and apply result to the underlying graph
    */
  def createAndApply(): Unit =
    withStartEndTimesLogged {
      run().foreach(diffGraph => DiffGraph.Applier.applyDiff(diffGraph, cpg))
    }

  private def withStartEndTimesLogged[A](fun: => A): A = {
    logger.debug(s"Start of enhancement: $name")
    val startTime = System.currentTimeMillis
    try {
      fun
    } finally {
      val duration = (System.currentTimeMillis - startTime).millis.toCoarsest
      logger.info(s"Enhancement $name completed in $duration")
    }
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
                            inverseDiffGraph: Option[DiffGraph],
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
