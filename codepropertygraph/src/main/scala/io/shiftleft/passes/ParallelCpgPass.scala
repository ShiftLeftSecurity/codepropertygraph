package io.shiftleft.passes
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.utils.{ExecutionContextProvider, StatsLogger}
import org.slf4j.MDC

import java.util.concurrent.LinkedBlockingQueue
import scala.annotation.nowarn
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

/* ConcurrentWriterCpgPass is a possible replacement for ParallelCpgPass and NewStylePass.
 *
 * Instead of returning an Iterator, generateParts() returns an Array. This means that the entire collection
 * of parts must live on the heap at the same time; on the other hand, there are no possible issues with iterator invalidation,
 * e.g. when running over all METHOD nodes and deleting some of them.
 *
 * Changes are applied sequentially, in the same order as the output of `runOnParts`, as opposed to `ParallelCpgPass`,
 * where the ordering of change application is non-deterministic. For this reason, ConcurrentWriterCpgPass only accepts a single KeyPool.
 *
 * However, as opposed to NewStylePass, changes are not buffered and applied in one go; instead, they are applied as the respective
 * `runOnPart` finishes, concurrently with other `runOnPart` invocations.
 *
 * Compared to NewStylePass, this avoids excessive peak memory consumption. On the other hand, `runOnPart` sees the CPG
 * in an intermediate state: No promises are made about which previous changes are already applied; and changes are
 * applied concurrently, such that all reads from the graph are potential race conditions. Furthermore, this variant has
 * higher constant overhead per part than NewStylePass, i.e. is better suited to passes that create few large diffs.
 *
 *
 * Initialization and cleanup of external resources or large datastructures can be done in the `init()` and `finish()`
 * methods. This may be better than using the constructor or GC, because e.g. SCPG chains of passes construct
 * passes eagerly, and releases them only when the entire chain has run.
 * */
object ConcurrentWriterCpgPass {
  private val writerQueueCapacity   = 4
  private val producerQueueCapacity = 2 + 4 * Runtime.getRuntime().availableProcessors()
}
abstract class ConcurrentWriterCpgPass[T <: AnyRef](
  cpg: Cpg,
  @nowarn outName: String = "",
  keyPool: Option[KeyPool] = None
) extends NewStyleCpgPassBase[T] {

  @volatile var nDiffT = -1

  /** WARNING: runOnPart is executed in parallel to committing of graph modifications. The upshot is that it is unsafe
    * to read ANY data from cpg, on pain of bad race conditions
    *
    * Only use ConcurrentWriterCpgPass if you are _very_ sure that you avoid races.
    *
    * E.g. adding a CFG edge to node X races with reading an AST edge of node X.
    */
  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, prefix: String = ""): Unit = {
    import ConcurrentWriterCpgPass.producerQueueCapacity
    baseLogger.info(s"Start of enhancement: $name")
    StatsLogger.initiateNewStage(getClass.getSimpleName, Some(name), getClass.getSuperclass.getSimpleName)
    val nanosStart = System.nanoTime()
    var nParts     = 0
    var nDiff      = 0
    nDiffT = -1
    init()
    val parts = generateParts()
    nParts = parts.size
    val partIter        = parts.iterator
    val completionQueue = mutable.ArrayDeque[Future[overflowdb.BatchedUpdate.DiffGraph]]()
    val writer          = new Writer(MDC.getCopyOfContextMap())
    val writerThread    = new Thread(writer)
    writerThread.setName("Writer")
    writerThread.start()
    implicit val ec: ExecutionContext = ExecutionContextProvider.getExecutionContext
    try {
      try {
        // The idea is that we have a ringbuffer completionQueue that contains the workunits that are currently in-flight.
        // We add futures to the end of the ringbuffer, and take futures from the front.
        // then we await the future from the front, and add it to the writer-queue.
        // the end result is that we get deterministic output (esp. deterministic order of changes), while having up to one
        // writer-thread and up to producerQueueCapacity many threads in-flight.
        // as opposed to ParallelCpgPass, there is no race between diffgraph-generators to enqueue into the writer -- everything
        // is nice and ordered. Downside is that a very slow part may gum up the works (i.e. the completionQueue fills up and threads go idle)
        var done = false
        while (!done && writer.raisedException == null) {
          if (writer.raisedException != null)
            throw writer.raisedException // will be wrapped with good stacktrace in the finally block

          if (completionQueue.size < producerQueueCapacity && partIter.hasNext) {
            val next = partIter.next()
            // todo: Verify that we get FIFO scheduling; otherwise, do something about it.
            // if this e.g. used LIFO with 4 cores and 18 size of ringbuffer, then 3 cores may idle while we block on the front item.
            completionQueue.append(Future.apply {
              val builder = new DiffGraphBuilder
              runOnPart(builder, next.asInstanceOf[T])
              builder.build()
            })
          } else if (completionQueue.nonEmpty) {
            val future = completionQueue.removeHead()
            val res    = Await.result(future, Duration.Inf)
            nDiff += res.size
            writer.queue.put(Some(res))
          } else {
            done = true
          }
        }
      } finally {
        try {
          // if the writer died on us, then the queue might be full and we could deadlock
          if (writer.raisedException == null) writer.queue.put(None)
          writerThread.join()
          // we need to reraise exceptions
          if (writer.raisedException != null)
            throw new RuntimeException("Failure in diffgraph application", writer.raisedException)

        } finally { finish() }
      }
    } finally {
      // the nested finally is somewhat ugly -- but we promised to clean up with finish(), we want to include finish()
      // in the reported timings, and we must have our final log message if finish() throws

      val nanosStop = System.nanoTime()

      baseLogger.info(
        f"Enhancement $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms. ${nDiff}%d  + ${nDiffT - nDiff}%d changes committed from ${nParts}%d parts."
      )
      StatsLogger.endLastStage()
    }
  }

  private class Writer(mdc: java.util.Map[String, String]) extends Runnable {

    val queue =
      new LinkedBlockingQueue[Option[overflowdb.BatchedUpdate.DiffGraph]](ConcurrentWriterCpgPass.writerQueueCapacity)

    @volatile var raisedException: Exception = null

    override def run(): Unit = {
      try {
        nDiffT = 0
        // logback chokes on null context maps
        if (mdc != null) MDC.setContextMap(mdc)
        var terminate  = false
        var index: Int = 0
        while (!terminate) {
          queue.take() match {
            case None =>
              baseLogger.debug("Shutting down WriterThread")
              terminate = true
            case Some(diffGraph) =>
              nDiffT += overflowdb.BatchedUpdate
                .applyDiff(cpg.graph, diffGraph, keyPool.getOrElse(null), null)
                .transitiveModifications()
              index += 1
          }
        }
      } catch {
        case exception: InterruptedException => baseLogger.warn("Interrupted WriterThread", exception)
        case exc: Exception =>
          raisedException = exc
          queue.clear()
          throw exc
      }
    }
  }

}
