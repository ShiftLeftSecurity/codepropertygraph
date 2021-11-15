package io.shiftleft.passes

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.nodes.{NewNode, StoredNode}
import org.slf4j.{Logger, LoggerFactory, MDC}

import java.lang.{Long => JLong}
import java.util.function.{BiConsumer, Supplier}
import scala.annotation.nowarn
import scala.concurrent.ExecutionContext
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
abstract class CpgPass(cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None) extends CpgPassBase {

  /**
    * Main method of pass - to be implemented by child class
    * */
  def run(): Iterator[DiffGraph] = ???

  /**
    * Secondary main method of pass - child classes may implement it if they need an execution context.
    */
  @nowarn def runWithExecutionContext()(implicit ec: ExecutionContext): Iterator[DiffGraph] = run()

  /**
    * Execute the pass and apply result to the underlying graph
    */
  override def createAndApply()(implicit ec: ExecutionContext): Unit =
    withStartEndTimesLogged {
      runWithExecutionContext().foreach(diffGraph =>
        DiffGraph.Applier.applyDiff(diffGraph, cpg, undoable = false, keyPool))
    }

  /**
    * Execute and create a serialized overlay
    * @param inverse invert the diffgraph before serializing
    */
  def createApplyAndSerialize(inverse: Boolean = false)(implicit ec: ExecutionContext): Iterator[GeneratedMessageV3] =
    withStartEndTimesLogged {
      val overlays = runWithExecutionContext().map { diffGraph =>
        val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse, keyPool)
        serialize(appliedDiffGraph, inverse)
      }
      overlays
    }

  /**
    * Run a CPG pass to create diff graphs, apply diff graphs, create corresponding
    * overlays and add them to the serialized CPG. The name of the overlay is derived
    * from the class name of the pass.
    *
    * @param serializedCpg the destination serialized CPG to add overlays to
    * @param inverse invert the diffgraph before serializing
    * @param prefix a prefix to add to the output name
    * */
  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = "")(implicit ec: ExecutionContext): Unit = {
    if (serializedCpg.isEmpty) {
      createAndApply()
    } else {
      val overlays = createApplyAndSerialize(inverse)
      overlays.zipWithIndex.foreach {
        case (overlay, index) =>
          val name = generateOutFileName(prefix, outName, index)
          store(overlay, name, serializedCpg)
      }
    }
  }

}

/* SimpleCpgPass is a possible replacement for CpgPass.
 *
 *  Instead of returning an Iterator[DiffGraph], the `run` function gets a DiffGraphBuilder as input, and can attach its
 *  modifications to it (i.e. mutate the builder).
 *
 * CpgPass has somewhat subtle semantics with respect to lazy evaluation order of the returned iterator and graph writes.
 * The subtleties are gone with SimpleCpgPass.
 *
 * Note that SimpleCpgPass does not support lazy evaluation to reduce peak memory consumption. Take care before porting passes that
 * write large amounts of data that risk OOM errors.
 *
 * Initialization and cleanup of external resources or large datastructures can be done in the `init()` and `finish()`
 * methods. This may be better than using the constructor or GC, because e.g. SCPG chains of passes construct
 * passes eagerly, and releases them only when the entire chain has run.
 * */

abstract class SimpleCpgPass(cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends ForkJoinParallelCpgPass[AnyRef](cpg, outName, keyPool) {

  def run(builder: DiffGraph.Builder): Unit

  final override def generateParts(): Array[_ <: AnyRef] = Array[AnyRef](null)

  final override def runOnPart(builder: DiffGraph.Builder, part: AnyRef): Unit = run(builder)
}

/* ForkJoinParallelCpgPass is a possible replacement for CpgPass and ParallelCpgPass.
 *
 * Instead of returning an Iterator, generateParts() returns an Array. This means that the entire collection
 * of parts must live on the heap at the same time; on the other hand, there are no possible issues with iterator invalidation,
 * e.g. when running over all METHOD nodes and deleting some of them.
 *
 * Instead of streaming writes as ParallelCpgPass or ConcurrentWriterCpgPass do, all `runOnPart` invocations read the initial state
 * of the graph. Then all changes (accumulated in the DiffGraphBuilders) are merged into a single change, and applied in one go.
 *
 * In other words, the parallelism follows the fork/join parallel map-reduce (java: collect, scala: aggregate) model.
 * The effect is identical as if one were to sequentially run `runOnParts` on all output elements of `generateParts()`
 * in sequential order, with the same builder.
 *
 * This simplifies semantics and makes it easy to reason about possible races.
 *
 * Note that ForkJoinParallelCpgPass never writes intermediate results, so one must consider peak memory consumption when
 * porting from ParallelCpgPass. Consider ConcurrentWriterCpgPass when this is a problem.
 *
 * Initialization and cleanup of external resources or large datastructures can be done in the `init()` and `finish()`
 * methods. This may be better than using the constructor or GC, because e.g. SCPG chains of passes construct
 * passes eagerly, and releases them only when the entire chain has run.
 * */
abstract class ForkJoinParallelCpgPass[T <: AnyRef](cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends CpgPassBase {
  // generate Array of parts that can be processed in parallel
  def generateParts(): Array[_ <: AnyRef]
  // setup large data structures, acquire external resources
  def init(): Unit = {}
  // release large data structures and external resources
  def finish(): Unit = {}
  // main function: add desired changes to builder
  def runOnPart(builder: DiffGraph.Builder, part: T): Unit

  override def createAndApply()(implicit ec: ExecutionContext): Unit = createApplySerializeAndStore(null)

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = "")(implicit ec: ExecutionContext): Unit = {
    baseLogger.info("Start of pass: {}", name)
    val nanosStart = System.nanoTime()
    var nParts = 0
    var nanosBuilt = -1L
    var nDiff = -1
    try {
      init()
      val parts = generateParts()
      nParts = parts.size
      val diffGraph = nParts match {
        case 0 => DiffGraph.EmptyChangeSet
        case 1 =>
          val builder = DiffGraph.newBuilder
          runOnPart(builder, parts(0).asInstanceOf[T])
          builder.build()
        case _ =>
          java.util.Arrays
            .stream(parts)
            .parallel()
            .collect(
              new Supplier[DiffGraph.Builder] {
                override def get(): DiffGraph.Builder = DiffGraph.newBuilder
              },
              new BiConsumer[DiffGraph.Builder, AnyRef] {
                override def accept(builder: DiffGraph.Builder, part: AnyRef): Unit =
                  runOnPart(builder, part.asInstanceOf[T])
              },
              new BiConsumer[DiffGraph.Builder, DiffGraph.Builder] {
                override def accept(leftBuilder: DiffGraph.Builder, rightBuilder: DiffGraph.Builder): Unit =
                  leftBuilder.moveFrom(rightBuilder)
              }
            )
            .build()
      }
      nanosBuilt = System.nanoTime()
      nDiff = diffGraph.size
      val doSerialize = serializedCpg != null && !serializedCpg.isEmpty
      val withInverse = doSerialize && inverse

      val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, withInverse, keyPool)
      if (doSerialize) {
        store(serialize(appliedDiffGraph, withInverse), generateOutFileName(prefix, outName, 0), serializedCpg)
      }
    } catch {
      case exc: Exception =>
        baseLogger.error("Pass {} failed", name, exc)
        throw exc
    } finally {
      try {
        finish()
      } finally {
        // the nested finally is somewhat ugly -- but we promised to clean up with finish(), we want to include finish()
        // in the reported timings, and we must have our final log message if finish() throws
        val nanosStop = System.nanoTime()
        val fracRun = if (nanosBuilt == -1) 100.0 else (nanosBuilt - nanosStart) * 100.0 / (nanosStop - nanosStart + 1)
        baseLogger.info(
          f"Pass $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms ($fracRun%.0f%% on mutations). $nDiff%d changes committed from $nParts%d parts.")
      }
    }
  }
}

object CpgPassBase {
  private val baseLogger: Logger = LoggerFactory.getLogger(classOf[CpgPass])
}

trait CpgPassBase {

  protected def baseLogger: Logger = CpgPassBase.baseLogger

  def createAndApply()(implicit ec: ExecutionContext): Unit

  def createApplySerializeAndStore(serializedCpg: SerializedCpg, inverse: Boolean = false, prefix: String = "")(
      implicit ec: ExecutionContext): Unit

  /**
    * Name of the pass.
    * By default it is inferred from the name of the class, override if needed.
    */
  def name: String = getClass.getName

  protected def serialize(appliedDiffGraph: AppliedDiffGraph, inverse: Boolean): GeneratedMessageV3 = {
    if (inverse) {
      new DiffGraphProtoSerializer().serialize(appliedDiffGraph.inverseDiffGraph.get)
    } else {
      new DiffGraphProtoSerializer().serialize(appliedDiffGraph)
    }
  }

  protected def generateOutFileName(prefix: String, outName: String, index: Int): String = {
    val outputName = {
      if (outName.isEmpty) {
        this.getClass.getSimpleName
      } else {
        outName
      }
    }
    prefix + "_" + outputName + "_" + index
  }

  protected def store(overlay: GeneratedMessageV3, name: String, serializedCpg: SerializedCpg): Unit = {
    if (overlay.getSerializedSize > 0) {
      serializedCpg.addOverlay(overlay, name)
    }
  }

  protected def withStartEndTimesLogged[A](fun: => A): A = {
    baseLogger.info("Running pass: {}", name)
    val startTime = System.currentTimeMillis
    try {
      fun
    } finally {
      val duration = (System.currentTimeMillis - startTime).millis.toCoarsest
      MDC.put("time", duration.toString())
      baseLogger.info("Pass {} completed in {}", name, duration)
      MDC.remove("time")
    }
  }

}

/**
  * Diff Graph that has been applied to a source graph. This is a wrapper around
  * diff graph, which additionally provides a map from nodes to graph ids.
  * */
case class AppliedDiffGraph(diffGraph: DiffGraph,
                            inverseDiffGraph: Option[DiffGraph],
                            private val nodeToOdbNode: java.util.IdentityHashMap[NewNode, StoredNode]) {

  /**
    * Obtain the id this node has in the applied graph
    * */
  def nodeToGraphId(node: NewNode): JLong = {
    nodeToOdbNode.get(node).id
  }
}
