package io.shiftleft.passes

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.{Cpg, DiffGraphBuilder}
import org.slf4j.{Logger, LoggerFactory, MDC}

import java.util.function.{BiConsumer, Supplier}
import scala.annotation.nowarn
import scala.compiletime.uninitialized
import scala.concurrent.duration.DurationLong
import scala.util.{Failure, Success, Try}

/* CpgPass
 *
 * Base class of a program which receives a CPG as input for the purpose of modifying it.
 * */
abstract class CpgPass(cpg: Cpg, outName: String = "") extends ForkJoinParallelCpgPass[AnyRef](cpg, outName) {

  def run(builder: DiffGraphBuilder): Unit

  final override def generateParts(): Array[? <: AnyRef] = Array[AnyRef](null)

  final override def runOnPart(builder: DiffGraphBuilder, part: AnyRef): Unit =
    run(builder)

  override def isParallel: Boolean = false
}

@deprecated abstract class SimpleCpgPass(cpg: Cpg, outName: String = "") extends CpgPass(cpg, outName)

/* ForkJoinParallelCpgPass is a possible replacement for CpgPass and ParallelCpgPass.
 *
 * Instead of returning an Iterator, generateParts() returns an Array. This means that the entire collection
 * of parts must live on the heap at the same time; on the other hand, there are no possible issues with iterator invalidation,
 * e.g. when running over all METHOD nodes and deleting some of them.
 *
 * Instead of streaming writes as ParallelCpgPass do, all `runOnPart` invocations read the initial state
 * of the graph. Then all changes (accumulated in the DiffGraphBuilders) are merged into a single change, and applied in one go.
 *
 * In other words, the parallelism follows the fork/join parallel map-reduce (java: collect, scala: aggregate) model.
 * The effect is identical as if one were to sequentially run `runOnParts` on all output elements of `generateParts()`
 * in sequential order, with the same builder.
 *
 * This simplifies semantics and makes it easy to reason about possible races.
 *
 * Note that ForkJoinParallelCpgPass never writes intermediate results, so one must consider peak memory consumption when porting from ParallelCpgPass.
 *
 * Initialization and cleanup of external resources or large datastructures can be done in the `init()` and `finish()`
 * methods. This may be better than using the constructor or GC, because e.g. SCPG chains of passes construct
 * passes eagerly, and releases them only when the entire chain has run.
 * */
abstract class ForkJoinParallelCpgPass[T <: AnyRef](cpg: Cpg, @nowarn outName: String = "") extends CpgPassBase {
  type DiffGraphBuilder = io.shiftleft.codepropertygraph.generated.DiffGraphBuilder
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

  override def createAndApply(): Unit = {
    baseLogger.info(s"Start of pass: $name")
    val nanosStart = System.nanoTime()
    var nParts     = 0
    var nanosBuilt = -1L
    var nDiff      = -1
    var nDiffT     = -1
    try {
      val diffGraph = Cpg.newDiffGraphBuilder
      nParts = runWithBuilder(diffGraph)
      nanosBuilt = System.nanoTime()
      nDiff = diffGraph.size

      nDiffT = flatgraph.DiffGraphApplier.applyDiff(cpg.graph, diffGraph)
    } catch {
      case exc: Exception =>
        baseLogger.error(s"Pass ${name} failed", exc)
        throw exc
    } finally {
      val nanosStop = System.nanoTime()
      val fracRun   = if (nanosBuilt == -1) 0.0 else (nanosStop - nanosBuilt) * 100.0 / (nanosStop - nanosStart + 1)
      baseLogger.info(
        f"Pass $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms ($fracRun%.0f%% on mutations). $nDiff%d + ${nDiffT - nDiff}%d changes committed from $nParts%d parts."
      )
    }
  }

  override def runWithBuilder(externalBuilder: DiffGraphBuilder): Int = {
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
                Cpg.newDiffGraphBuilder
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

  @deprecated("Please use createAndApply")
  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit = {
    createAndApply()
  }

}

/** A [[ForkJoinParallelCpgPass]] that additionally maintains a thread-local accumulator of type [[R]] which is merged
  * across all threads after processing completes. This enables map-reduce style aggregation alongside the usual
  * DiffGraph-based graph modifications.
  *
  * Each thread gets its own accumulator instance (via [[newAccumulator]]). After all parts are processed, the
  * accumulators are merged using [[mergeAccumulators]] and the result is passed to [[onAccumulatorComplete]].
  *
  * This variant uses the `stream.collect` / `BiConsumer` API (just like [[ForkJoinParallelCpgPass]]) with a combined
  * container that holds both a [[DiffGraphBuilder]] and an accumulator per fork, so no `ThreadLocal` or
  * `ConcurrentLinkedQueue` is needed.
  *
  * @tparam T
  *   the part type (same as in [[ForkJoinParallelCpgPass]])
  * @tparam R
  *   the accumulator type
  */
abstract class ForkJoinParallelCpgPassWithAccumulator[T <: AnyRef, R](cpg: Cpg, @nowarn outName: String = "")
    extends CpgPassBase {
  type DiffGraphBuilder = io.shiftleft.codepropertygraph.generated.DiffGraphBuilder

  /** Generate Array of parts that can be processed in parallel. */
  def generateParts(): Array[? <: AnyRef]

  /** Setup large data structures, acquire external resources. */
  def init(): Unit = {}

  /** Override this to disable parallelism of passes. Useful for debugging. */
  def isParallel: Boolean = true

  /** Create a fresh, empty accumulator. Called once per fork (thread). */
  protected def newAccumulator(): R

  /** Merge two accumulators. Must be associative. The result may reuse either argument. */
  protected def mergeAccumulators(left: R, right: R): R

  /** Process a single part, writing graph changes to `builder` and aggregated data to `acc`. */
  protected def runOnPartWithAccumulator(builder: DiffGraphBuilder, acc: R, part: T): Unit

  /** Called after all parts are processed with the fully merged accumulator. Override `finish()` if you need to release
    * resources; `onAccumulatorComplete` is invoked from within the default `finish()` implementation.
    */
  protected def onAccumulatorComplete(acc: R): Unit = {}

  /** Container pairing a per-fork DiffGraphBuilder with a per-fork accumulator. */
  private class BuilderWithAccumulator(val builder: DiffGraphBuilder, var acc: R)

  @volatile private var _accResult: R       = uninitialized
  @volatile private var _hasResult: Boolean = false

  /** Release large data structures and external resources. The default implementation calls [[onAccumulatorComplete]]
    * with the merged accumulator (or a fresh one if processing failed). Subclasses that override this method must call
    * `super.finish()` to ensure the accumulator callback fires.
    */
  def finish(): Unit = {
    val acc = if (_hasResult) _accResult else newAccumulator()
    onAccumulatorComplete(acc)
    _hasResult = false
  }

  override def createAndApply(): Unit = {
    baseLogger.info(s"Start of pass: $name")
    val nanosStart = System.nanoTime()
    var nParts     = 0
    var nanosBuilt = -1L
    var nDiff      = -1
    var nDiffT     = -1
    try {
      val diffGraph = Cpg.newDiffGraphBuilder
      nParts = runWithBuilder(diffGraph)
      nanosBuilt = System.nanoTime()
      nDiff = diffGraph.size

      nDiffT = flatgraph.DiffGraphApplier.applyDiff(cpg.graph, diffGraph)
    } catch {
      case exc: Exception =>
        baseLogger.error(s"Pass ${name} failed", exc)
        throw exc
    } finally {
      val nanosStop = System.nanoTime()
      val fracRun   = if (nanosBuilt == -1) 0.0 else (nanosStop - nanosBuilt) * 100.0 / (nanosStop - nanosStart + 1)
      baseLogger.info(
        f"Pass $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms ($fracRun%.0f%% on mutations). $nDiff%d + ${nDiffT - nDiff}%d changes committed from $nParts%d parts."
      )
    }
  }

  override def runWithBuilder(externalBuilder: DiffGraphBuilder): Int = {
    _hasResult = false
    try {
      init()
      val parts  = generateParts()
      val nParts = parts.size
      _accResult = nParts match {
        case 0 =>
          newAccumulator()
        case 1 =>
          val acc = newAccumulator()
          runOnPartWithAccumulator(externalBuilder, acc, parts(0).asInstanceOf[T])
          acc
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
          val result = stream.collect(
            new Supplier[BuilderWithAccumulator] {
              override def get(): BuilderWithAccumulator =
                new BuilderWithAccumulator(Cpg.newDiffGraphBuilder, newAccumulator())
            },
            new BiConsumer[BuilderWithAccumulator, AnyRef] {
              override def accept(bwa: BuilderWithAccumulator, part: AnyRef): Unit =
                runOnPartWithAccumulator(bwa.builder, bwa.acc, part.asInstanceOf[T])
            },
            new BiConsumer[BuilderWithAccumulator, BuilderWithAccumulator] {
              override def accept(left: BuilderWithAccumulator, right: BuilderWithAccumulator): Unit = {
                left.builder.absorb(right.builder)
                left.acc = mergeAccumulators(left.acc, right.acc)
              }
            }
          )
          externalBuilder.absorb(result.builder)
          result.acc
      }
      _hasResult = true
      nParts
    } finally {
      finish()
    }
  }

  @deprecated("Please use createAndApply")
  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit = {
    createAndApply()
  }
}

trait CpgPassBase {

  protected def baseLogger: Logger = LoggerFactory.getLogger(getClass)

  def createAndApply(): Unit

  @deprecated("Please use createAndApply")
  def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit

  /** Name of the pass. By default it is inferred from the name of the class, override if needed.
    */
  def name: String = getClass.getName

  /** Runs the cpg pass, adding changes to the passed builder. Use with caution -- API is unstable. Returns max(nParts,
    * 1), where nParts is either the number of parallel parts, or the number of iterarator elements in case of legacy
    * passes. Includes init() and finish() logic.
    */
  def runWithBuilder(builder: DiffGraphBuilder): Int

  /** Wraps runWithBuilder with logging, and swallows raised exceptions. Use with caution -- API is unstable. A return
    * value of -1 indicates failure, otherwise the return value of runWithBuilder is passed through.
    */
  def runWithBuilderLogged(builder: DiffGraphBuilder): Int = {
    baseLogger.info(s"Start of pass: $name")
    val nanoStart = System.nanoTime()
    val size0     = builder.size
    Try(runWithBuilder(builder)) match {
      case Success(nParts) =>
        baseLogger.info(
          f"Pass ${name} completed in ${(System.nanoTime() - nanoStart) * 1e-6}%.0f ms.  ${builder.size - size0}%d changes generated from ${nParts}%d parts."
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

  @deprecated
  protected def store(overlay: GeneratedMessageV3, name: String, serializedCpg: SerializedCpg): Unit = {}

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
