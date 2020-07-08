package io.shiftleft.x2cpg

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{DiffGraph, KeyPool}
import io.shiftleft.codepropertygraph.generated.nodes
import org.slf4j.LoggerFactory
import overflowdb.{OdbConfig, OdbGraph}

import scala.util.{Failure, Success, Try}

/**
  * A threads that blocks on a queue of DiffGraphs and merges them into
  * the database at `outputPath` consecutively. This thread implements
  * the "reduce" step in our "map-reduce" CPG generation.
  * */
class DbWriter {

  private val queue = new LinkedBlockingQueue[(DiffGraph, KeyPool)]()
  private var thread: Thread = _

  /**
    * Open the CPG at `outputPath` for writing, overwriting
    * it if it already exists. Note that calling `close` when
    * you are done is mandatory for program termination and
    * obtaining a valid CPG.
    * */
  def open(outputPath: String): Unit = {
    thread = new Thread(new DbWriterRunnable(outputPath, queue))
    thread.start()
  }

  /**
    * Merge `diffGraph` into CPG. Node ids are taken from the
    * provided `keyPool`.
    * */
  def add(diffGraph: DiffGraph, keyPool: KeyPool): Unit = {
    queue.put(diffGraph, keyPool)
  }

  /**
    * Close the CPG.
    * */
  def close(): Unit = {
    if (thread != null) {
      val node = nodes.NewUnknown(parserTypeName = "terminate-db-writer")
      val terminator = DiffGraph.newBuilder
      terminator.addNode(node)
      queue.put(terminator.build(), null)
      thread.join()
    }
  }

}

private class DbWriterRunnable(outputPath: String, queue: BlockingQueue[(DiffGraph, KeyPool)]) extends Runnable {

  private val logger = LoggerFactory.getLogger(getClass)

  override def run(): Unit = {
    val cpg = freshCpg(outputPath)
    applyDiffGraphs(cpg) match {
      case Failure(exc) =>
        exc match {
          case _: InterruptedException =>
            logger.warn("DbWriter interrupted. Resulting CPG may be incomplete")
          case e =>
            logger.warn(e.getMessage)
        }
      case Success(_) =>
    }
    cpg.close()
  }

  private def freshCpg(pathToCpg: String): Cpg = {
    val outFile = File(pathToCpg)
    if (pathToCpg != "" && outFile.exists) {
      logger.info("Output file exists, removing: " + pathToCpg)
      outFile.delete()
    }
    val odbConfig = OdbConfig.withDefaults.withStorageLocation(pathToCpg)
    val odbGraph = OdbGraph.open(odbConfig,
                                 io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
                                 io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava)
    new Cpg(odbGraph)
  }

  private def applyDiffGraphs(cpg: Cpg): Try[Unit] = {
    Try {
      var terminate = false
      while (!terminate) {
        val (diffGraph, keyPool) = queue.take()
        if (isTerminator(diffGraph)) {
          terminate = true
        } else {
          DiffGraph.Applier.applyDiff(diffGraph, cpg, false, Some(keyPool))
        }
      }
    }
  }

  private def isTerminator(diffGraph: DiffGraph): Boolean = {
    diffGraph.nodes.size == 1 && {
      diffGraph.nodes.next match {
        case unknown: nodes.Unknown => unknown.parserTypeName == "terminate-db-writer"
        case _                      => false
      }
    }
  }
}
