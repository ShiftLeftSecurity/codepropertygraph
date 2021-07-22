package io.shiftleft.passes

import scala.annotation.nowarn

class WorkItem[T](item: T, runOnPart: T => Iterator[DiffGraph]) {
  def run(): Iterator[DiffGraph] = {
    runOnPart(item)
  }
}

@nowarn
abstract class ParallelCpgPass[T](outName: String = "", val keyPools: Option[Iterator[KeyPool]] = None)
    extends CpgPassBase {

  def init(): Unit = {}

  def partIterator: Iterator[T]

  def runOnPart(part: T): Iterator[DiffGraph]

  def workItemIterator: Iterator[WorkItem[T]] = {
    partIterator.map(new WorkItem(_, runOnPart))
  }

//  override def createAndApply(diffGraphConsumer: DiffGraph => Unit): Unit = {
//    withWriter(new SerializedCpg(), "", false) { writer =>
//      enqueueInParallel(writer)
//    }
//  }
//
//  override def createApplySerializeAndStore(serializedCpg: SerializedCpg, inverse: Boolean, prefix: String): Unit = {
//    withWriter(serializedCpg, prefix, inverse) { writer =>
//      enqueueInParallel(writer)
//    }
//  }
//
//  private def withWriter[X](serializedCpg: SerializedCpg,
//                            prefix: String,
//                            inverse: Boolean)(f: Writer => Unit): Unit = {
//    val writer = new Writer(serializedCpg, prefix, inverse)
//    val writerThread = new Thread(writer)
//    writerThread.setName("Writer")
//    writerThread.start()
//    try {
//      f(writer)
//    } catch {
//      case exception: Exception =>
//        logger.warn("pass failed", exception)
//    } finally {
//      writer.enqueue(None, None)
//      writerThread.join()
//    }
//  }
//
//  private def enqueueInParallel(writer: Writer): Unit = {
//    withStartEndTimesLogged {
//      init()
//      val it = new ParallelIteratorExecutor(itWithKeyPools()).map {
//        case (part, keyPool) =>
//          // Note: write.enqueue(runOnPart(part)) would be wrong because
//          // it would terminate the writer as soon as a pass returns None
//          // as None is used as a termination symbol for the queue
//          runOnPart(part).foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
//      }
//      consume(it)
//    }
//  }
//
//  private def itWithKeyPools(): Iterator[(T, Option[KeyPool])] = {
//    if (keyPools.isEmpty) {
//      partIterator.map(p => (p, None))
//    } else {
//      val pools = keyPools.get
//      partIterator.map { p =>
//        (p, pools.nextOption() match {
//          case Some(pool) => Some(pool)
//          case None =>
//            logger.warn("Not enough key pools provided. Ids may not be constant across runs")
//            None
//        })
//      }
//    }
//  }
//
//  private def consume(it: Iterator[_]): Unit = {
//    while (it.hasNext) {
//      it.next()
//    }
//  }
//
//  private class Writer(serializedCpg: SerializedCpg, prefix: String, inverse: Boolean) extends Runnable {
//
//    case class DiffGraphAndKeyPool(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool])
//
//    private val logger = LoggerFactory.getLogger(classOf[Writer])
//
//    private val queue = new LinkedBlockingQueue[DiffGraphAndKeyPool]
//
//    def enqueue(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool]): Unit = {
//      queue.put(DiffGraphAndKeyPool(diffGraph, keyPool))
//    }
//
//    override def run(): Unit = {
//      try {
//        var terminate = false
//        var index: Int = 0
//        while (!terminate) {
//          queue.take() match {
//            case DiffGraphAndKeyPool(Some(diffGraph), keyPool) =>
//              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse, keyPool)
//              if (!serializedCpg.isEmpty) {
//                val overlay = serialize(appliedDiffGraph, inverse)
//                val name = generateOutFileName(prefix, outName, index)
//                index += 1
//                store(overlay, name, serializedCpg)
//              }
//            case DiffGraphAndKeyPool(None, _) =>
//              logger.debug("Shutting down WriterThread")
//              terminate = true
//          }
//        }
//      } catch {
//        case exception: InterruptedException => logger.warn("Interrupted WriterThread", exception)
//      }
//    }
//  }

}
