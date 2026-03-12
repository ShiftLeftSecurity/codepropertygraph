package io.shiftleft.passes

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.generated.{Cpg, DiffGraphBuilder}
import org.slf4j.{Logger, LoggerFactory, MDC}

import java.util.function.{BiConsumer, Supplier}
import scala.annotation.nowarn
import scala.concurrent.duration.DurationLong
import scala.util.{Failure, Success, Try}

/** A single-threaded CPG pass. This is the simplest pass to implement: override [[run]] and add desired graph
  * modifications to the provided [[DiffGraphBuilder]].
  *
  * Internally implemented as a [[ForkJoinParallelCpgPass]] with a single part and parallelism disabled.
  *
  * @param cpg
  *   the code property graph to modify
  * @param outName
  *   optional name for output
  */
abstract class CpgPass(cpg: Cpg, outName: String = "") extends ForkJoinParallelCpgPass[AnyRef](cpg, outName) {

  /** The main method to implement. Add all desired graph changes (nodes, edges, properties) to the provided builder.
    *
    * @param builder
    *   the [[DiffGraphBuilder]] that accumulates graph modifications
    */
  def run(builder: DiffGraphBuilder): Unit

  final override def generateParts(): Array[? <: AnyRef] = Array[AnyRef](null)

  final override def runOnPart(builder: DiffGraphBuilder, part: AnyRef): Unit =
    run(builder)

  override def isParallel: Boolean = false
}

/** @deprecated Use [[CpgPass]] instead. */
@deprecated abstract class SimpleCpgPass(cpg: Cpg, outName: String = "") extends CpgPass(cpg, outName)

/** A parallel CPG pass using the fork/join model.
  *
  * Instead of returning an Iterator, [[generateParts]] returns an Array. This means that the entire collection of parts
  * must live on the heap at the same time; on the other hand, there are no possible issues with iterator invalidation,
  * e.g. when running over all METHOD nodes and deleting some of them.
  *
  * Instead of streaming writes as ParallelCpgPass do, all [[runOnPart]] invocations read the initial state of the
  * graph. Then all changes (accumulated in the DiffGraphBuilders) are merged into a single change, and applied in one
  * go.
  *
  * In other words, the parallelism follows the fork/join parallel map-reduce (java: collect, scala: aggregate) model.
  * The effect is identical as if one were to sequentially run [[runOnPart]] on all output elements of [[generateParts]]
  * in sequential order, with the same builder.
  *
  * This simplifies semantics and makes it easy to reason about possible races.
  *
  * Note that ForkJoinParallelCpgPass never writes intermediate results, so one must consider peak memory consumption
  * when porting from ParallelCpgPass.
  *
  * Initialization and cleanup of external resources or large datastructures can be done in the [[init]] and [[finish]]
  * methods. This may be better than using the constructor or GC, because e.g. SCPG chains of passes construct passes
  * eagerly, and releases them only when the entire chain has run.
  *
  * This is a simplified form of [[ForkJoinParallelCpgPassWithAccumulator]] that does not use an accumulator.
  *
  * @tparam T
  *   the type of each part produced by [[generateParts]]
  * @param cpg
  *   the code property graph to modify
  * @param outname
  *   optional output name
  */
abstract class ForkJoinParallelCpgPass[T <: AnyRef](cpg: Cpg, @nowarn outname: String = "")
    extends ForkJoinParallelCpgPassWithAccumulator[T, Null](cpg, outname) {

  /** Process a single part and record graph modifications in the provided builder.
    *
    * @param builder
    *   the [[DiffGraphBuilder]] that accumulates graph modifications
    * @param part
    *   the part to process, as produced by [[generateParts]]
    */
  def runOnPart(builder: DiffGraphBuilder, part: T): Unit

  override def createAccumulator(): Null                                                 = null
  override def runOnPart(builder: DiffGraphBuilder, part: T, acc: Null): Unit            = runOnPart(builder, part)
  override def onAccumulatorComplete(builder: DiffGraphBuilder, accumulator: Null): Unit = {}
  override def mergeAccumulator(left: Null, accumulator: Null): Unit                     = {}
}

/** A parallel CPG pass with an accumulator for aggregating side results.
  *
  * This is the most general form of the fork/join pass framework. It extends [[ForkJoinParallelCpgPass]] with an
  * accumulator of type [[Accumulator]] that each parallel worker maintains locally. After all parts are processed,
  * worker accumulators are merged via [[mergeAccumulator]], and the final merged accumulator is passed to
  * [[onAccumulatorComplete]] where additional graph changes can be recorded.
  *
  * @tparam T
  *   the type of each part produced by [[generateParts]]
  * @tparam Accumulator
  *   the type of the accumulator used during parallel execution
  * @param cpg
  *   the code property graph to modify
  * @param outName
  *   optional output name
  */
abstract class ForkJoinParallelCpgPassWithAccumulator[T <: AnyRef, Accumulator <: AnyRef](
  cpg: Cpg,
  @nowarn outName: String = ""
) extends CpgPassBase {
  type DiffGraphBuilder = io.shiftleft.codepropertygraph.generated.DiffGraphBuilder

  /** Generate an array of parts to be processed in parallel by [[runOnPart]]. */
  def generateParts(): Array[? <: AnyRef]

  /** Called once before [[generateParts]]. Use to set up large data structures or acquire external resources. */
  def init(): Unit = {}

  /** Called once after all parts have been processed (in a `finally` block). Use to release resources acquired in
    * [[init]].
    */
  def finish(): Unit = {}

  /** Process a single part, recording graph changes in `builder` and side results in `accumulator`.
    *
    * @param builder
    *   the [[DiffGraphBuilder]] that accumulates graph modifications
    * @param part
    *   the part to process
    * @param accumulator
    *   the thread-local accumulator for this worker
    */
  def runOnPart(builder: DiffGraphBuilder, part: T, accumulator: Accumulator): Unit

  /** Override and return `false` to disable parallel execution. Useful for debugging. */
  def isParallel: Boolean = true

  /** Create a fresh accumulator instance. Called once per parallel worker thread. */
  def createAccumulator(): Accumulator

  /** Merge the `accumulator` (right) into `left`. Called during the combine phase of fork/join. */
  def mergeAccumulator(left: Accumulator, accumulator: Accumulator): Unit

  /** Called once after all parts are processed and accumulators are merged. Use to record additional graph changes
    * based on the fully merged accumulator.
    *
    * @param builder
    *   the [[DiffGraphBuilder]] for any additional modifications
    * @param accumulator
    *   the final merged accumulator
    */
  def onAccumulatorComplete(builder: DiffGraphBuilder, accumulator: Accumulator): Unit

  /** Creates a new [[DiffGraphBuilder]], runs the pass (init, generateParts, runOnPart, finish), applies all
    * accumulated changes to the graph, and logs timing information. Exceptions during execution are logged and
    * re-thrown.
    */
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

  /** Runs the full pass lifecycle (init, generateParts, parallel runOnPart, accumulator merge, finish) and absorbs all
    * changes into `externalBuilder` without applying them to the graph. The caller is responsible for applying the
    * builder.
    *
    * @param externalBuilder
    *   the builder to absorb all generated changes into
    * @return
    *   the number of parts that were processed
    */
  override def runWithBuilder(externalBuilder: DiffGraphBuilder): Int = {
    try {
      init()

      val parts  = generateParts()
      val nParts = parts.size
      val stream =
        if (!isParallel) java.util.Arrays.stream(parts).sequential()
        else java.util.Arrays.stream(parts).parallel()

      val (diff, acc) = stream.collect(
        new Supplier[(DiffGraphBuilder, Accumulator)] {
          override def get(): (DiffGraphBuilder, Accumulator) =
            (Cpg.newDiffGraphBuilder, createAccumulator())
        },
        new BiConsumer[(DiffGraphBuilder, Accumulator), AnyRef] {
          override def accept(consumedArg: (DiffGraphBuilder, Accumulator), part: AnyRef): Unit = {
            val (diff, acc) = consumedArg
            runOnPart(diff, part.asInstanceOf[T], acc)
          }
        },
        new BiConsumer[(DiffGraphBuilder, Accumulator), (DiffGraphBuilder, Accumulator)] {
          override def accept(
            leftConsumedArg: (DiffGraphBuilder, Accumulator),
            rightConsumedArg: (DiffGraphBuilder, Accumulator)
          ): Unit = {
            val (leftDiff, leftAcc)   = leftConsumedArg
            val (rightDiff, rightAcc) = leftConsumedArg
            leftDiff.absorb(rightDiff)
            mergeAccumulator(leftAcc, rightAcc)
          }
        }
      )
      onAccumulatorComplete(diff, acc)
      externalBuilder.absorb(diff)
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

/** Base trait for all CPG passes. Defines the lifecycle methods that every pass must implement: [[createAndApply]] for
  * standalone execution, and [[runWithBuilder]] for composing passes that share a single [[DiffGraphBuilder]].
  */
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

  /** Wraps [[runWithBuilder]] with logging and exception handling. Use with caution — API is unstable.
    *
    * @param builder
    *   the [[DiffGraphBuilder]] to absorb changes into
    * @return
    *   the number of parts processed, or `-1` if the pass threw an exception
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

  /** Executes `fun` while logging the pass start and completion time (including duration via MDC).
    *
    * @tparam A
    *   the return type of the wrapped computation
    * @param fun
    *   the computation to execute
    * @return
    *   the result of `fun`
    */
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
