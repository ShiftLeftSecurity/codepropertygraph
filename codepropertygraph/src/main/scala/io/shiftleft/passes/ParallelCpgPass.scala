package io.shiftleft.passes
import java.util.concurrent.LinkedBlockingQueue

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.apache.logging.log4j.{LogManager, Logger}
import org.slf4j.LoggerFactory

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "") extends CpgPassBase {

  private val logger: Logger = LogManager.getLogger(classOf[ParallelCpgPass[T]])

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): DiffGraph

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
      writer.enqueue(None)
      writerThread.join()
    }
  }

  private def enqueueInParallel(writer: Writer): Unit = {
    init()
    val it = new ParallelIteratorExecutor(partIterator).map { part =>
      writer.enqueue(Some(runOnPart(part)))
    }
    consume(it)
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {

    private val logger = LoggerFactory.getLogger(getClass)

    private val queue = new LinkedBlockingQueue[Option[DiffGraph]]

    def enqueue(diffGraph: Option[DiffGraph]): Unit = {
      queue.put(diffGraph)
    }

    override def run(): Unit = {
      try {
        var terminate = false
        var index: Int = 0
        while (!terminate) {
          queue.take() match {
            case Some(diffGraph) =>
              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse)
              if (!serializedCpg.isEmpty) {
                val overlay = serialize(appliedDiffGraph, inverse)
                val name = generateOutFileName(prefix, outName, index)
                index += 1
                store(overlay, name, serializedCpg)
              }
            case None =>
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
