package io.shiftleft.passes

import java.util.concurrent.LinkedBlockingQueue

import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import org.slf4j.LoggerFactory

abstract class ParallelCpgPass[T](cpg: Cpg, outName: String = "") extends CpgPassBase(cpg, outName) {

  def nodeIterator: Iterator[T]

  def runOnNode(node: T): DiffGraph

  def init() = {}

  var writer: SequentialDiffApplier = null

  /**
    * Execute the enhancement and apply result to the underlying graph
    */
  override def createAndApply(): Unit = {
    val thread = startWriterThread(false)
    withStartEndTimesLogged {
      try {
        init()
        val it = new ParallelIteratorExecutor(nodeIterator).map { node =>
          val diffGraph = Some(runOnNode(node))
          writer.enqueue(diffGraph)
        }
        consume(it)
      } finally {
        stopWriterThread()
        thread.join()
      }
    }
  }

  // TODO this one doesn't make much sense and we should remove it
  // from `CpgPass` as well in a follow-up PR
  override def createApplyAndSerialize(inverse: Boolean = false): Unit = ???

  /**
    * Run a CPG pass to create diff graphs, apply diff graphs, create corresponding
    * overlays and add them to the serialized CPG. The name of the overlay is derived
    * from the class name of the pass.
    *
    * @param serializedCpg the destination serialized CPG to add overlays to
    * @param inverse invert the diffgraph before serializing
    * @param prefix a prefix to add to the output name
    * */
  override def createApplySerializeAndStore(serializedCpg: SerializedCpg,
                                            inverse: Boolean = false,
                                            prefix: String = ""): Unit = {
    if (serializedCpg.isEmpty) {
      createAndApply()
    } else {
      withStartEndTimesLogged {
        init()
        val shouldStore = true
        val thread = startWriterThread(shouldStore, inverse, prefix, serializedCpg)
        try {
          val it = new ParallelIteratorExecutor(nodeIterator).map { node =>
            val diffGraph = Some(runOnNode(node))
            writer.enqueue(diffGraph)
          }
          consume(it)
        } finally {
          stopWriterThread()
          thread.join()
        }
      }
    }
  }

  private def startWriterThread(shouldStore: Boolean,
                                inverse: Boolean = false,
                                prefix: String = "",
                                serializedCpg: SerializedCpg = new SerializedCpg()) = {
    writer = new SequentialDiffApplier(cpg, shouldStore, inverse, prefix, serializedCpg)
    val writerThread = new Thread(writer)
    writerThread.setName("DiffGraphWriter")
    writerThread.start()
    writerThread
  }

  private def stopWriterThread(): Unit = {
    writer.enqueue(None)
  }

  private def consume(it: Iterator[_]): Unit = {
    while (it.hasNext) {
      it.next()
    }
  }

  class SequentialDiffApplier(cpg: Cpg,
                              shouldStore: Boolean,
                              inverse: Boolean,
                              prefix: String,
                              serializedCpg: SerializedCpg)
      extends Runnable {

    private val queue = new LinkedBlockingQueue[Option[DiffGraph]]()

    def enqueue(diffGraph: Option[DiffGraph]): Unit = {
      queue.put(diffGraph)
    }

    private val logger = LoggerFactory.getLogger(getClass)

    override def run(): Unit = {
      try {
        var terminate = false;
        var index: Int = 0
        while (!terminate) {
          val maybeDiffGraph = queue.take()
          if (maybeDiffGraph.isEmpty) {
            logger.info("Shutting down SequentialDiffApplier")
            terminate = true
          } else {
            val diffGraph = maybeDiffGraph.get
            if (shouldStore) {
              val overlay = diffGraphToSerialized(diffGraph, inverse)
              val name = prefix + "_" + outputName + "_" + index
              index += 1
              store(overlay, name, serializedCpg)
            } else {
              DiffGraph.Applier.applyDiff(diffGraph, cpg, inverse)
            }
          }
        }
      } catch {
        case _: InterruptedException => logger.warn("Interrupted SequentialDiffApplier.")
      }
    }
  }

}
