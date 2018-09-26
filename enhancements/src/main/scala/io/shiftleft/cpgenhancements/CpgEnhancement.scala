package io.shiftleft.cpgenhancements

import gremlin.scala.ScalaGraph
import io.shiftleft.diffgraph.{DiffGraph, DiffGraphProtoSerializer}
import io.shiftleft.proto.cpg.Cpg.CpgOverlay
import org.apache.logging.log4j.LogManager

/**
  * Base class for CPG enhancements - provides access to a src graph and destination Diff graph
  * */
abstract class CpgEnhancement(srcGraph: ScalaGraph) {

  implicit val dstGraph = new DiffGraph()

  protected val logger = LogManager.getLogger(getClass)
  private var startTime: Long = _

  /**
    * Execute the enhancement and apply result to graph
    * */
  def executeAndApply(): Unit = {
    logStart()
    run()
    applyDiff
    logEnd()
  }

  def applyDiff = {
    dstGraph.applyDiff(srcGraph)
  }

  /**
    * Execute and create a serialized overlay
    * */
  def executeAndCreateOverlay(): CpgOverlay = {
    try {
      logStart()
      run()
      new DiffGraphProtoSerializer().serialize(dstGraph)
    } finally {
      logEnd()
    }
  }

  /**
    * Main method of enhancement - to be implemented by child class
    * */
  def run(): Unit

  private def logStart(): Unit = {
    logger.info(s"Start of enhancement: ${getClass.getName}")
    startTime = System.currentTimeMillis()
  }

  private def logEnd(): Unit = {
    val endTime = System.currentTimeMillis()
    logger.info(s"End of enhancement: ${getClass.getName}, after ${endTime - startTime}ms")
  }

}
