package io.shiftleft.x2cpg

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import better.files.File
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.DiffGraph
import io.shiftleft.codepropertygraph.generated.nodes
import org.slf4j.LoggerFactory
import overflowdb.{OdbConfig, OdbGraph}

import scala.util.{Failure, Success, Try}

/**
  * A threads that blocks on a queue of DiffGraphs and merges them into
  * the database at `outputPath` consecutively. This thread implements
  * the "reduce" step in our "map-reduce" CPG generation.
  * */
class DbWriter(outputPath: String) {

  private val queue = new LinkedBlockingQueue[DiffGraph]()
  private val writer = new DbWriterRunnable(outputPath, queue)
  private val thread = new Thread(writer)

  def start(): Unit = {
    thread.start()
  }

  def add(diffGraph: DiffGraph): Unit = {
    queue.put(diffGraph)
  }

  def stop(): Unit = {
    val node = nodes.NewUnknown(parserTypeName = "terminate-db-writer")
    val terminator = DiffGraph.newBuilder
    terminator.addNode(node)
    queue.put(terminator.build)
  }

}

private class DbWriterRunnable(outputPath: String, queue: BlockingQueue[DiffGraph]) extends Runnable {

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
        val diffGraph = queue.take()
        if (isTerminator(diffGraph)) {
          terminate = true
        } else {
          DiffGraph.Applier.applyDiff(diffGraph, cpg)
        }
      }
    }
  }

  def isTerminator(diffGraph: DiffGraph): Boolean = {
    diffGraph.nodes.size == 1 && {
      diffGraph.nodes.next match {
        case unknown: nodes.Unknown => unknown.parserTypeName == "terminate-db-writer"
        case _                      => false
      }
    }
  }
}
