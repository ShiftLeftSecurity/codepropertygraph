package io.shiftleft.passes
import java.util.concurrent.LinkedBlockingQueue

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.apache.logging.log4j.{LogManager, Logger}
import org.slf4j.LoggerFactory

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "", keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  private val logger: Logger = LogManager.getLogger(classOf[ParallelCpgPass[T]])

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
        logger.warn(exception)
    } finally {
      writer.enqueue(None, None)
      writerThread.join()
    }
  }

  private def enqueueInParallel(writer: Writer): Unit = {
    init()
    val it = new ParallelIteratorExecutor(partIterator).map { part =>
      val keyPool = this synchronized { keyPools.map(_.next) }

      // Note: write.enqueue(runOnPart(part)) would be wrong because
      // it would terminate the writer as soon as a pass returns None
      // as None is used as a termination symbol for the queue
      runOnPart(part).foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
    }
    consume(it)
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {

    case class DiffGraphAndKeyPool(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool])

    private val logger = LoggerFactory.getLogger(getClass)

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
              logger.info("Shutting down WriterThread")
              terminate = true
          }
        }
      } catch {
        case _: InterruptedException => logger.info("Interrupted WriterThread")
      }
    }
  }

}
