package io.shiftleft.passes

import gremlin.scala.ScalaGraph
import io.shiftleft.SerializedCpg
import io.shiftleft.diffgraph.{DiffGraph, DiffGraphProtoSerializer}
import io.shiftleft.proto.cpg.Cpg.CpgOverlay
import org.apache.logging.log4j.{LogManager, Logger}

/**
  * Base class for CPG enhancements
  *
  * The class provides access to an underlying source graph and destination Diff graph
  */
abstract class CpgPass(srcGraph: ScalaGraph) {
  import CpgPass.logger

  /**
    * Main method of enhancement - to be implemented by child class
    * */
  def run(): Iterator[DiffGraph]

  /**
    * Name of the enhancement pass.
    * By default it is inferred from the name of the class, override if needed.
    */
  def name: String = getClass.getName

  /**
    * Run a CPG pass to create diff graphs, apply diff graphs, create corresponding
    * overlays and add them to the serialized CPG. The name of the overlay is derived
    * from the class name of the pass.
    *
    * @param serializedCpg the destination serialized CPG to add overlays to
    * @param counter an optional integer to keep apart different runs of the same pass
    * */
  def createStoreAndApplyOverlay(serializedCpg: SerializedCpg, counter: Int = 0): Unit = {
    val overlays = executeAndApplyAndCreateOverlay()
    overlays.zipWithIndex.foreach {
      case (overlay, index) => {
        if (overlay.getSerializedSize > 0) {
          serializedCpg.addOverlay(overlay, getClass.getSimpleName + counter.toString + "_" + index)
        }
      }
    }
  }

  /**
    * Execute and create a serialized overlay
    */
  def executeAndApplyAndCreateOverlay(): Iterator[CpgOverlay] = {
    try {
      logStart()
      run().map { dstGraph =>
        val appliedDiffGraph = dstGraph.applyDiff(srcGraph)
        new DiffGraphProtoSerializer().serialize()(appliedDiffGraph)
      }
    } finally {
      logEnd()
    }
  }

  /**
    * Execute the enhancement and apply result to the underlying graph
    */
  def executeAndApply(): Unit = {
    logStart()
    try {
      val dstGraphs = run()
      dstGraphs.foreach(_.applyDiff(srcGraph))
    } finally {
      logEnd()
    }
  }

  private var startTime: Long = _

  private def logStart(): Unit = {
    logger.info(s"Start of enhancement: ${name}")
    startTime = System.currentTimeMillis()
  }

  private def logEnd(): Unit = {
    val endTime = System.currentTimeMillis()
    logger.info(s"End of enhancement: ${name}, after ${endTime - startTime}ms")
  }

}

object CpgPass {
  private val logger: Logger = LogManager.getLogger(classOf[CpgPass])
}
