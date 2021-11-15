package io.shiftleft.passes
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg

import java.util.concurrent.LinkedBlockingQueue
import scala.collection.mutable
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "", keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

  override def createAndApply()(implicit ec: ExecutionContext): Unit = {
    withWriter() { writer =>
      enqueueInParallel(writer)
    }
  }

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, inverse: Boolean, prefix: String)(implicit ec: ExecutionContext): Unit = {
    withWriter(serializedCpg, prefix, inverse) { writer =>
      enqueueInParallel(writer)
    }
  }

  private def withWriter[X](serializedCpg: SerializedCpg = new SerializedCpg(),
                            prefix: String = "",
                            inverse: Boolean = false)(f: Writer => Unit)(implicit ec: ExecutionContext): Unit = {
    val writer = new Writer(serializedCpg, prefix, inverse)
    val task = Future { writer.run() }
    try {
      f(writer)
    } catch {
      case exception: Exception =>
        baseLogger.warn("pass failed", exception)
    } finally {
      writer.enqueue(None, None)
    }
    Await.ready(task, Duration.Inf)
  }

  private def enqueueInParallel(writer: Writer)(implicit ec: ExecutionContext): Unit = {
    withStartEndTimesLogged {
      try {
        init()
        val it = new ParallelIteratorExecutor(itWithKeyPools()).map {
          case (part, keyPool) =>
            // Note: write.enqueue(runOnPart(part)) would be wrong because
            // it would terminate the writer as soon as a pass returns None
            // as None is used as a termination symbol for the queue
            runOnPart(part).foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
        }
        consume(it)
      } catch {
        case exception: Exception =>
          baseLogger.warn("Exception in parallel CPG pass {}:", name, exception)
      }
    }
  }

  private def itWithKeyPools(): Iterator[(T, Option[KeyPool])] = {
    if (keyPools.isEmpty) {
      partIterator.map(p => (p, None))
    } else {
      val pools = keyPools.get
      partIterator.map { p =>
        (p, pools.nextOption() match {
          case Some(pool) => Some(pool)
          case None =>
            baseLogger.warn("Not enough key pools provided. Ids may not be constant across runs")
            None
        })
      }
    }
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {

    case class DiffGraphAndKeyPool(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool])

    private val queue = new LinkedBlockingQueue[DiffGraphAndKeyPool]

    def enqueue(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool]): Unit = {
      queue.put(DiffGraphAndKeyPool(diffGraph, keyPool))
    }

    override def run(): Unit = {
      try {
        var terminate = false
        var index: Int = 0
        while (!terminate) {
          queue.take() match {
            case DiffGraphAndKeyPool(Some(diffGraph), keyPool) =>
              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse, keyPool)
              if (!serializedCpg.isEmpty) {
                val overlay = serialize(appliedDiffGraph, inverse)
                val name = generateOutFileName(prefix, outName, index)
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
  private val writerQueueCapacity = 4
  private val producerQueueCapacity = 2 + 4 * Runtime.getRuntime().availableProcessors()
}

abstract class ConcurrentWriterCpgPass[T <: AnyRef](cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends CpgPassBase {

  // generate Array of parts that can be processed in parallel
  def generateParts(): Array[_ <: AnyRef] = Array[AnyRef](null)
  // setup large data structures, acquire external resources
  def init(): Unit = {}
  // release large data structures and external resources
  def finish(): Unit = {}

  /** WARNING: runOnPart is executed in parallel to committing of graph modifications.
    * The upshot is that it is unsafe to read ANY data from cpg, on pain of bad race conditions
    *
    * Only use ConcurrentWriterCpgPass if you are _very_ sure that you avoid races.
    *
    * E.g. adding a CFG edge to node X races with reading an AST edge of node X.
    * */
  def runOnPart(builder: DiffGraph.Builder, part: T): Unit

  override def createAndApply()(implicit ec: ExecutionContext): Unit = createApplySerializeAndStore(null)

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = "")(implicit ec: ExecutionContext): Unit = {
    import ConcurrentWriterCpgPass.producerQueueCapacity
    baseLogger.info("Start of enhancement: {}", name)
    val nanosStart = System.nanoTime()
    var nParts = 0
    var nDiff = 0

    init()
    val parts = generateParts()
    nParts = parts.size
    val partIter = parts.iterator
    val completionQueue = mutable.ArrayDeque[Future[DiffGraph]]()
    val writer = new Writer(serializedCpg, prefix, inverse)
    val task = Future { writer.run() }
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
        while (!done) {
          if (completionQueue.size < producerQueueCapacity && partIter.hasNext) {
            val next = partIter.next()
            // TODO: Verify that we get FIFO scheduling; otherwise, do something about it.
            // if this e.g. used LIFO with 4 cores and 18 size of ringbuffer, then 3 cores may idle while we block on the front item.
            completionQueue.append(Future.apply {
              val builder = DiffGraph.newBuilder
              runOnPart(builder, next.asInstanceOf[T])
              builder.build()
            })
          } else if (completionQueue.nonEmpty) {
            val future = completionQueue.removeHead()
            val res = Await.result(future, Duration.Inf)
            nDiff += res.size
            writer.queue.put(Some(res))
          } else {
            done = true
          }
        }
      } finally {
        try {
          writer.queue.put(None)
          Await.ready(task, Duration.Inf)
        } finally { finish() }
      }
    } finally {
      // the nested finally is somewhat ugly -- but we promised to clean up with finish(), we want to include finish()
      // in the reported timings, and we must have our final log message if finish() throws
      val nanosStop = System.nanoTime()
      baseLogger.info(
        f"Enhancement $name completed in ${(nanosStop - nanosStart) * 1e-6}%.0f ms. $nDiff%d changes committed from $nParts%d parts.")
    }
  }

  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {

    val queue = new LinkedBlockingQueue[Option[DiffGraph]](ConcurrentWriterCpgPass.writerQueueCapacity)

    override def run(): Unit = {
      try {
        var terminate = false
        var index: Int = 0
        val doSerialize = serializedCpg != null && !serializedCpg.isEmpty
        val withInverse = doSerialize && inverse
        while (!terminate) {
          queue.take() match {
            case None =>
              baseLogger.debug("Shutting down WriterThread")
              terminate = true
            case Some(diffGraph) =>
              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, withInverse, keyPool)
              if (doSerialize) {
                store(serialize(appliedDiffGraph, withInverse),
                      generateOutFileName(prefix, outName, index),
                      serializedCpg)
              }
              index += 1
          }
        }
      } catch {
        case exception: InterruptedException => baseLogger.warn("Interrupted WriterThread", exception)
      }
    }
  }

}
