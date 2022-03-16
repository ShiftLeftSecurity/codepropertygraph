package io.shiftleft.passes
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.utils.ExecutionContextProvider
import org.slf4j.MDC

import java.util.concurrent.LinkedBlockingQueue
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

@deprecated(
  message = "Please use ForkJoinParallelCpgPass or ConcurrentWriterCpgPass as a replacement.",
  since = "v1.3.508"
)
abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "", keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

  implicit val executionContext: ExecutionContext = ExecutionContextProvider.getExecutionContext

  override def createAndApply(): Unit = {
    withWriter() { writer =>
      enqueueInParallel(writer)
    }
  }

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, inverse: Boolean, prefix: String): Unit = {
    withWriter(serializedCpg, prefix, inverse) { writer =>
      enqueueInParallel(writer)
    }
  }

  private def withWriter[X](
    serializedCpg: SerializedCpg = new SerializedCpg(),
    prefix: String = "",
    inverse: Boolean = false
  )(f: Writer => Unit): Unit = {
    val writer       = new Writer(serializedCpg, prefix, inverse, MDC.getCopyOfContextMap())
    val writerThread = new Thread(writer)
    writerThread.setName("Writer")
    @volatile var exceptionCaught: Option[Throwable] = None
    writerThread.setUncaughtExceptionHandler { (_: Thread, t: Throwable) =>
      exceptionCaught = Option(t)
    }
    writerThread.start()
    try {
      f(writer)
    } catch {
      case exception: Exception =>
        baseLogger.warn("pass failed", exception)
    } finally {
      writer.enqueue(None, None)
      writerThread.join()
    }

    exceptionCaught.foreach { exception =>
      baseLogger.warn(s"Exception in parallel CPG pass $name:", exception)
      throw exception
    }
  }

  private def enqueueInParallel(writer: Writer): Unit = {
    withStartEndTimesLogged {
      try {
        init()
        val it = new ParallelIteratorExecutor(itWithKeyPools()).map { case (part, keyPool) =>
          // Note: write.enqueue(runOnPart(part)) would be wrong because
          // it would terminate the writer as soon as a pass returns None
          // as None is used as a termination symbol for the queue
          runOnPart(part).foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
        }
        consume(it)
      } catch {
        case exception: Exception =>
          baseLogger.warn(s"Exception in parallel CPG pass $name:", exception)
      }
    }
  }

  private def itWithKeyPools(): Iterator[(T, Option[KeyPool])] = {
    if (keyPools.isEmpty) {
      partIterator.map(p => (p, None))
    } else {
      val pools = keyPools.get
      partIterator.map { p =>
        (
          p,
          pools.nextOption() match {
            case Some(pool) => Some(pool)
            case None =>
              baseLogger.warn("Not enough key pools provided. Ids may not be constant across runs")
              None
          }
        )
      }
    }
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  private class Writer(
    serializedCpg: SerializedCpg,
    prefix: String,
    inverse: Boolean,
    mdc: java.util.Map[String, String]
  ) extends Runnable {

    case class DiffGraphAndKeyPool(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool])

    private val queue = new LinkedBlockingQueue[DiffGraphAndKeyPool]

    def enqueue(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool]): Unit = {
      queue.put(DiffGraphAndKeyPool(diffGraph, keyPool))
    }

    override def run(): Unit = {
      try {
        //logback chokes on null context maps
        if(mdc != null) MDC.setContextMap(mdc)
        var terminate  = false
        var index: Int = 0
        while (!terminate) {
          queue.take() match {
            case DiffGraphAndKeyPool(Some(diffGraph), keyPool) =>
              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse, keyPool)
              if (!serializedCpg.isEmpty) {
                val overlay = serialize(appliedDiffGraph, inverse)
                val name    = generateOutFileName(prefix, outName, index)
                index += 1
                store(overlay, name, serializedCpg)
              }
            case DiffGraphAndKeyPool(None, _) =>
              baseLogger.debug("Shutting down WriterThread")
              terminate = true
          }
        }
      } catch {
        case exception: InterruptedException => baseLogger.warn("Interrupted WriterThread", exception)
      }
    }
  }
}
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
abstract class ConcurrentWriterCpgPass[T <: AnyRef](cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends CpgPassBase {
  type DiffGraphBuilder = overflowdb.BatchedUpdate.DiffGraphBuilder

  @volatile var nDiffT = -1

  // generate Array of parts that can be processed in parallel
  def generateParts(): Array[_ <: AnyRef] = Array[AnyRef](null)
  // setup large data structures, acquire external resources
  def init(): Unit = {}
  // release large data structures and external resources
  def finish(): Unit = {}

  /** WARNING: runOnPart is executed in parallel to committing of graph modifications. The upshot is that it is unsafe
    * to read ANY data from cpg, on pain of bad race conditions
    *
    * Only use ConcurrentWriterCpgPass if you are _very_ sure that you avoid races.
    *
    * E.g. adding a CFG edge to node X races with reading an AST edge of node X.
    */
  def runOnPart(builder: DiffGraphBuilder, part: T): Unit

  override def createAndApply(): Unit = createApplySerializeAndStore(null)

  override def createApplySerializeAndStore(
    serializedCpg: SerializedCpg,
    inverse: Boolean = false,
    prefix: String = ""
  ): Unit = {
    import ConcurrentWriterCpgPass.producerQueueCapacity
    baseLogger.info(s"Start of enhancement: $name")
    val nanosStart = System.nanoTime()
    var nParts     = 0
    var nDiff      = 0
    nDiffT = -1
    init()
    val parts = generateParts()
    nParts = parts.size
    val partIter        = parts.iterator
    val completionQueue = mutable.ArrayDeque[Future[overflowdb.BatchedUpdate.DiffGraph]]()
    val writer          = new Writer(serializedCpg, prefix, inverse, MDC.getCopyOfContextMap())
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
      val serializationString = if (serializedCpg != null && !serializedCpg.isEmpty) {
        if (inverse) " Inverse serialized and stored." else " Diff serialized and stored."
      } else ""
      baseLogger.info(
        f"Enhancement $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms. ${nDiff}%d  + ${nDiffT - nDiff}%d changes commited from ${nParts}%d parts.${serializationString}%s"
      )
    }
  }

  private class Writer(
    serializedCpg: SerializedCpg,
    prefix: String,
    inverse: Boolean,
    mdc: java.util.Map[String, String]
  ) extends Runnable {

    val queue =
      new LinkedBlockingQueue[Option[overflowdb.BatchedUpdate.DiffGraph]](ConcurrentWriterCpgPass.writerQueueCapacity)

    @volatile var raisedException: Exception = null

    override def run(): Unit = {
      try {
        nDiffT = 0
        MDC.setContextMap(mdc)
        var terminate   = false
        var index: Int  = 0
        val doSerialize = serializedCpg != null && !serializedCpg.isEmpty
        val withInverse = doSerialize && inverse
        while (!terminate) {
          queue.take() match {
            case None =>
              baseLogger.debug("Shutting down WriterThread")
              terminate = true
            case Some(diffGraph) =>
              val listener =
                if (withInverse) new BatchUpdateInverseListener
                else if (doSerialize) new BatchUpdateForwardListener
                else null

              nDiffT += overflowdb.BatchedUpdate
                .applyDiff(cpg.graph, diffGraph, keyPool.getOrElse(null), listener)
                .transitiveModifications()

              if (withInverse) {
                store(
                  listener.asInstanceOf[BatchUpdateInverseListener].getSerialization(),
                  generateOutFileName(prefix, outName, 0),
                  serializedCpg
                )
              } else if (doSerialize) {
                store(
                  listener.asInstanceOf[BatchUpdateForwardListener].builder.build(),
                  generateOutFileName(prefix, outName, 0),
                  serializedCpg
                )
              }
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
