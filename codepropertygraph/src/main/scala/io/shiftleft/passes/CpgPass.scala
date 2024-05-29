package io.shiftleft.passes

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.utils.StatsLogger
import org.slf4j.{Logger, LoggerFactory, MDC}
import overflowdb.BatchedUpdate

import java.util.function.{BiConsumer, Supplier}
import scala.annotation.nowarn
import scala.concurrent.duration.DurationLong
import scala.util.{Failure, Success, Try}

/* CpgPass
 *
 * Base class of a program which receives a CPG as input for the purpose of modifying it.
 * */

abstract class CpgPass(cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends ForkJoinParallelCpgPass[AnyRef](cpg, outName, keyPool) {

  def run(builder: overflowdb.BatchedUpdate.DiffGraphBuilder): Unit

  final override def generateParts(): Array[? <: AnyRef] = Array[AnyRef](null)

  final override def runOnPart(builder: overflowdb.BatchedUpdate.DiffGraphBuilder, part: AnyRef): Unit =
    run(builder)

  override def isParallel: Boolean = false
}

@deprecated abstract class SimpleCpgPass(cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends CpgPass(cpg, outName, keyPool)

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
abstract class ForkJoinParallelCpgPass[T <: AnyRef](
  cpg: Cpg,
  @nowarn outName: String = "",
  keyPool: Option[KeyPool] = None
) extends NewStyleCpgPassBase[T] {

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit = {
    baseLogger.info(s"Start of pass: $name")
    StatsLogger.initiateNewStage(getClass.getSimpleName, Some(name), getClass.getSuperclass.getSimpleName)
    val nanosStart = System.nanoTime()
    var nParts     = 0
    var nanosBuilt = -1L
    var nDiff      = -1
    var nDiffT     = -1
    try {
      val diffGraph = new DiffGraphBuilder
      nParts = runWithBuilder(diffGraph)
      nanosBuilt = System.nanoTime()
      nDiff = diffGraph.size()

      nDiffT = overflowdb.BatchedUpdate
        .applyDiff(cpg.graph, diffGraph, keyPool.getOrElse(null), null)
        .transitiveModifications()

    } catch {
      case exc: Exception =>
        baseLogger.error(s"Pass ${name} failed", exc)
        throw exc
    } finally {
      try {
        finish()
      } finally {
        // the nested finally is somewhat ugly -- but we promised to clean up with finish(), we want to include finish()
        // in the reported timings, and we must have our final log message if finish() throws
        val nanosStop = System.nanoTime()
        val fracRun   = if (nanosBuilt == -1) 0.0 else (nanosStop - nanosBuilt) * 100.0 / (nanosStop - nanosStart + 1)
        val serializationString = if (serializedCpg != null && !serializedCpg.isEmpty) {
          " Diff serialized and stored."
        } else ""
        baseLogger.info(
          f"Pass $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms (${fracRun}%.0f%% on mutations). ${nDiff}%d + ${nDiffT - nDiff}%d changes committed from ${nParts}%d parts.${serializationString}%s"
        )
        StatsLogger.endLastStage()
      }
    }
  }

}

/** NewStyleCpgPassBase is the shared base between ForkJoinParallelCpgPass and ConcurrentWriterCpgPass, containing
  * shared boilerplate. We don't want ConcurrentWriterCpgPass as a subclass of ForkJoinParallelCpgPass because that
  * would make it hard to whether an instance is non-racy.
  *
  * Please don't subclass this directly. The only reason it's not sealed is that this would mess with our file
  * hierarchy.
  */
abstract class NewStyleCpgPassBase[T <: AnyRef] extends CpgPassBase {
  type DiffGraphBuilder = overflowdb.BatchedUpdate.DiffGraphBuilder
  // generate Array of parts that can be processed in parallel
  def generateParts(): Array[? <: AnyRef]
  // setup large data structures, acquire external resources
  def init(): Unit = {}
  // release large data structures and external resources
  def finish(): Unit = {}
  // main function: add desired changes to builder
  def runOnPart(builder: DiffGraphBuilder, part: T): Unit
  // Override this to disable parallelism of passes. Useful for debugging.
  def isParallel: Boolean = true

  override def createAndApply(): Unit = createApplySerializeAndStore(null)

  override def runWithBuilder(externalBuilder: BatchedUpdate.DiffGraphBuilder): Int = {
    try {
      init()
      val parts  = generateParts()
      val nParts = parts.size
      nParts match {
        case 0 =>
        case 1 =>
          runOnPart(externalBuilder, parts(0).asInstanceOf[T])
        case _ =>
          val stream =
            if (!isParallel)
              java.util.Arrays
                .stream(parts)
                .sequential()
            else
              java.util.Arrays
                .stream(parts)
                .parallel()
          val diff = stream.collect(
            new Supplier[DiffGraphBuilder] {
              override def get(): DiffGraphBuilder =
                new DiffGraphBuilder
            },
            new BiConsumer[DiffGraphBuilder, AnyRef] {
              override def accept(builder: DiffGraphBuilder, part: AnyRef): Unit =
                runOnPart(builder, part.asInstanceOf[T])
            },
            new BiConsumer[DiffGraphBuilder, DiffGraphBuilder] {
              override def accept(leftBuilder: DiffGraphBuilder, rightBuilder: DiffGraphBuilder): Unit =
                leftBuilder.absorb(rightBuilder)
            }
          )
          externalBuilder.absorb(diff)
      }
      nParts
    } finally {
      finish()
    }
  }
}

object CpgPassBase {
  private val baseLogger: Logger = LoggerFactory.getLogger(classOf[CpgPassBase])
}

trait CpgPassBase {

  protected def baseLogger: Logger = CpgPassBase.baseLogger

  def createAndApply(): Unit

  def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit

  /** Name of the pass. By default it is inferred from the name of the class, override if needed.
    */
  def name: String = getClass.getName

  /** Runs the cpg pass, adding changes to the passed builder. Use with caution -- API is unstable. Returns max(nParts,
    * 1), where nParts is either the number of parallel parts, or the number of iterarator elements in case of legacy
    * passes. Includes init() and finish() logic.
    */
  def runWithBuilder(builder: overflowdb.BatchedUpdate.DiffGraphBuilder): Int

  /** Wraps runWithBuilder with logging, and swallows raised exceptions. Use with caution -- API is unstable. A return
    * value of -1 indicates failure, otherwise the return value of runWithBuilder is passed through.
    */
  def runWithBuilderLogged(builder: overflowdb.BatchedUpdate.DiffGraphBuilder): Int = {
    baseLogger.info(s"Start of pass: $name")
    val nanoStart = System.nanoTime()
    val size0     = builder.size()
    Try(runWithBuilder(builder)) match {
      case Success(nParts) =>
        baseLogger.info(
          f"Pass ${name} completed in ${(System.nanoTime() - nanoStart) * 1e-6}%.0f ms.  ${builder.size() - size0}%d changes generated from ${nParts}%d parts."
        )
        nParts
      case Failure(exception) =>
        baseLogger.warn(f"Pass ${name} failed in ${(System.nanoTime() - nanoStart) * 1e-6}%.0f ms", exception)
        -1
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
    baseLogger.info(s"Running pass: $name")
    val startTime = System.currentTimeMillis
    try {
      fun
    } finally {
      val duration = (System.currentTimeMillis - startTime).millis.toCoarsest
      MDC.put("time", duration.toString())
      baseLogger.info(s"Pass $name completed in $duration")
      MDC.remove("time")
    }
  }

}
