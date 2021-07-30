package io.shiftleft.passes
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.slf4j.{LoggerFactory, MDC}

import scala.collection.mutable
import scala.concurrent.{Await, Future}
import java.util.concurrent.LinkedBlockingQueue
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "", keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

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

  private def withWriter[X](serializedCpg: SerializedCpg = new SerializedCpg(),
                            prefix: String = "",
                            inverse: Boolean = false)(f: Writer => Unit): Unit = {
    val writer = new Writer(serializedCpg, prefix, inverse)
    val writerThread = new Thread(writer)
    writerThread.setName("Writer")
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
  }

  private def enqueueInParallel(writer: Writer): Unit = {
    withStartEndTimesLogged {
      init()
      val it = new ParallelIteratorExecutor(itWithKeyPools()).map {
        case (part, keyPool) =>
          // Note: write.enqueue(runOnPart(part)) would be wrong because
          // it would terminate the writer as soon as a pass returns None
          // as None is used as a termination symbol for the queue
          runOnPart(part).foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
      }
      consume(it)
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

    private val writeLogger = LoggerFactory.getLogger(classOf[Writer])

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
              writeLogger.debug("Shutting down WriterThread")
              terminate = true
          }
        }
      } catch {
        case exception: InterruptedException => writeLogger.warn("Interrupted WriterThread", exception)
      }
    }
  }
}

object LargeChunkPass {
  val writerQueueCapacity = 4
  val producerQueueCapacity = 16
}
abstract class LargeChunkPass[T <: AnyRef](cpg: Cpg, outName: String = "", keyPool: Option[KeyPool] = None)
    extends CpgPassBase {

  //generate Array of parts that can be processed in parallel
  def generateParts(): Array[_ <: AnyRef] = Array[AnyRef](null)
  //setup large data structures, acquire external resources
  def init(): Unit = {}
  //release large data structures and external resources
  def finish(): Unit = {}

  /** WARNING: runOnPart is executed in parallel to commiting of graph modifications.
    * The upshot is that it is unsafe to read ANY data from cpg, on pain of bad race conditions
    *
    * Only use LargeChunkPass if you are _very_ sure that you avoid races.
    *
    * E.g. adding a CFG edge to node X races with reading an AST edge of node X.
    * */
  def runOnPart(builder: DiffGraph.Builder, part: T): Unit

  override def createAndApply(): Unit = createApplySerializeAndStore(null)

  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = ""): Unit = {
    import LargeChunkPass.producerQueueCapacity
    baseLogger.info(s"Start of enhancement: $name")
    val tic = System.nanoTime()
    var nParts = 0
    var nDiff = 0

    init()
    val parts = generateParts()
    nParts = parts.size
    val partIter = parts.iterator
    val completionQueue = mutable.ArrayDeque[Future[DiffGraph]]()
    val writer = new Writer(serializedCpg, prefix, inverse)
    val writerThread = new Thread(writer)
    writerThread.setName("Writer")
    writerThread.start()
    try {
      try {
        var done = false
        while (!done) {
          if (completionQueue.size < producerQueueCapacity && partIter.hasNext) {
            val next = partIter.next()
            //todo: Verify that we get FIFO scheduling; otherwise, do something about it.
            //if this e.g. used LIFO with 15 threads, then they work on the last 15 parts, while the main thread blocks on the first;
            //at some time, the 15 threads finish, one starts on the first part while the remaining 14 idle;
            //once the first task is done, the main thread unblocks, schedules 16 new tasks and the same story begins again.
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
        //
      } finally {
        writer.queue.put(None)
        writerThread.join()
        finish()
      }
    } finally {
      // the nested finally is somewhat ugly -- but we promised to clean up with finish(), we want to include finish()
      // in the reported timings, and we must have our final log message if finish() throws
      val toc = System.nanoTime()
      MDC.put("time", s"${(toc - tic) * 1e-6}%.0f")
      baseLogger.info(
        f"Enhancement $name completed in ${(toc - tic) * 1e-6}%.0f ms. ${nDiff}%d changes commited from ${nParts}%d parts.")
      MDC.remove("time")
    }
  }

  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {

    private val weiteLogger = LoggerFactory.getLogger(classOf[Writer])

    val queue = new LinkedBlockingQueue[Option[DiffGraph]](4)

    override def run(): Unit = {
      try {
        var terminate = false
        var index: Int = 0
        while (!terminate) {
          queue.take() match {
            case None =>
              weiteLogger.debug("Shutting down WriterThread")
              terminate = true
            case Some(diffGraph) =>
              val withInverse = serializedCpg != null && !serializedCpg.isEmpty && inverse
              val doSerialize = serializedCpg != null && !serializedCpg.isEmpty
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
        case exception: InterruptedException => weiteLogger.warn("Interrupted WriterThread", exception)
      }
    }
  }

}
