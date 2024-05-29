package io.shiftleft.utils

object StatsLogger extends DataLogger {
  private var logger: Option[DataLogger] = None
  def initialise(logger: Option[DataLogger] = None): Unit = {
    this.logger = logger
  }

  def initiateNewStage(
    stageName: String,
    stageFullName: Option[String] = None,
    additionalMetaDataToLog: String = STAGE_NOT_SET
  ): Unit = { logger.foreach(log => log.initiateNewStage(stageName, stageFullName, additionalMetaDataToLog)) }

  def endLastStage(): Unit = { logger.foreach(log => log.endLastStage()) }
}

trait DataLogger {
  // Constant used when stage or subStage is not set.
  val STAGE_NOT_SET = "<not set>"
  def initiateNewStage(
    stageName: String,
    stageFullName: Option[String] = None,
    additionalMetaDataToLog: String = STAGE_NOT_SET
  ): Unit

  def endLastStage(): Unit
}
