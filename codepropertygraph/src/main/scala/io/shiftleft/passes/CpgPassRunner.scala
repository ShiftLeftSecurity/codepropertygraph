package io.shiftleft.passes

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.slf4j.{Logger, LoggerFactory, MDC}

import java.nio.file.Paths
import java.util.concurrent.LinkedBlockingQueue
import scala.collection.mutable
import scala.concurrent.duration.DurationLong

object CpgPassRunner {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def apply(cpg: Cpg, pass: CpgPassBase): Unit = {
    val runner = new CpgPassRunner(cpg, outputDir = None, inverse = false)
    runner.addPass(pass)
    runner.run()
  }

  def applyAndStore(cpg: Cpg, pass: CpgPassBase, outputDir: String, inverse: Boolean): Unit = {
    val runner = new CpgPassRunner(cpg, Some(outputDir), inverse)
    runner.addPass(pass)
    runner.run()
  }
}

class CpgPassRunner(cpg: Cpg,
                    outputDir: Option[String],
                    inverse: Boolean,
                   ) {
  import CpgPassRunner._

  private val passes = mutable.ArrayBuffer.empty[CpgPassBase]

  def addPass(pass: CpgPassBase): Unit = {
    passes.append(pass)
  }

  def run(): Unit = {
    var index = 0

    passes.foreach { pass =>
      val serializedCpg = if (outputDir.isDefined) {
        val serializedCpgPath = Paths.get(outputDir.get).resolve(s"${index}_${pass.name}")
        new SerializedCpg(serializedCpgPath.toString)
      } else {
        new SerializedCpg()
      }

      try {
        pass match {
          case parallelPass: ParallelCpgPass[_] =>
            withWriter(serializedCpg) { writer =>
              enqueueInParallel(writer, parallelPass)
            }
          case cpgPass: CpgPass =>
            withStartEndTimesLogged(cpgPass.name) {
              cpgPass.run().foreach { diffGraph =>
                applyDiffGraph(serializedCpg, diffGraph, cpgPass)
              }
            }
        }
      } finally {
        serializedCpg.close()
      }

      index += 1
    }
  }

  private def applyDiffGraph(serializedCpg: SerializedCpg, diffGraph: DiffGraph, cpgPass: CpgPass): Unit = {
    val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, outputDir.isDefined && inverse, cpgPass.keyPool)
    if (!serializedCpg.isEmpty) {
      val serialization = serialize(appliedDiffGraph)
      if (serialization.getSerializedSize > 0) {
        serializedCpg.addOverlay(serialization, "serialization")
      }
    }
  }

  private def serialize(appliedDiffGraph: AppliedDiffGraph): GeneratedMessageV3 = {
    if (inverse) {
      new DiffGraphProtoSerializer().serialize(appliedDiffGraph.inverseDiffGraph.get)
    } else {
      new DiffGraphProtoSerializer().serialize(appliedDiffGraph)
    }
  }

  private def enqueueInParallel(writer: Writer, parallelCpgPass: ParallelCpgPass[_]): Unit = {
    withStartEndTimesLogged(parallelCpgPass.name) {
      parallelCpgPass.init()
      val it = new ParallelIteratorExecutor(itWithKeyPools(parallelCpgPass)).map {
        case (workItem, keyPool) =>
          // Note: write.enqueue(runOnPart(part)) would be wrong because
          // it would terminate the writer as soon as a pass returns None
          // as None is used as a termination symbol for the queue
          workItem.run().foreach(diffGraph => writer.enqueue(Some(diffGraph), keyPool))
      }
      consume(it)
    }
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  private def withWriter[X](serializedCpg: SerializedCpg)(f: Writer => Unit): Unit = {
    val writer = new Writer(serializedCpg)
    val writerThread = new Thread(writer)
    writerThread.setName("Writer")
    writerThread.start()
    try {
      f(writer)
    } catch {
      case exception: Exception =>
        logger.warn("pass failed", exception)
    } finally {
      writer.enqueue(None, None)
      writerThread.join()
    }
  }

  private def itWithKeyPools(parallelCpgPass: ParallelCpgPass[_]): Iterator[(WorkItem[_], Option[KeyPool])] = {
    if (parallelCpgPass.keyPools.isEmpty) {
      parallelCpgPass.workItemIterator.map(p => (p, None))
    } else {
      val pools = parallelCpgPass.keyPools.get
      parallelCpgPass.workItemIterator.map { p =>
        (p, pools.nextOption() match {
          case Some(pool) => Some(pool)
          case None =>
            logger.warn("Not enough key pools provided. Ids may not be constant across runs")
            None
        })
      }
    }
  }

  protected def withStartEndTimesLogged[A](name: String)(fun: => A): A = {
    logger.info(s"Start of enhancement: $name")
    val startTime = System.currentTimeMillis
    try {
      fun
    } finally {
      val duration = (System.currentTimeMillis - startTime).millis.toCoarsest
      MDC.put("time", duration.toString())
      logger.info(s"Enhancement $name completed in $duration")
      MDC.remove("time")
    }
  }

  private class Writer(serializedCpg: SerializedCpg) extends Runnable {

    case class DiffGraphAndKeyPool(diffGraph: Option[DiffGraph], keyPool: Option[KeyPool])

    private val logger = LoggerFactory.getLogger(classOf[Writer])

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
              val appliedDiffGraph = DiffGraph.Applier.applyDiff(diffGraph, cpg, outputDir.isDefined && inverse, keyPool)
              if (!serializedCpg.isEmpty) {
                val overlay = serialize(appliedDiffGraph)
                index += 1
                if (overlay.getSerializedSize > 0) {
                  serializedCpg.addOverlay(overlay, "serialization")
                }
              }
            case DiffGraphAndKeyPool(None, _) =>
              logger.debug("Shutting down WriterThread")
              terminate = true
          }
        }
      } catch {
        case exception: InterruptedException => logger.warn("Interrupted WriterThread", exception)
      }
    }
  }
}
