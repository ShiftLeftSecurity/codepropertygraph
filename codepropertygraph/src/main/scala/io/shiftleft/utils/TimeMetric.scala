package io.shiftleft.utils

import com.sun.management.OperatingSystemMXBean
import org.slf4j.{Logger, LoggerFactory}

import java.io.PrintWriter
import java.lang.management.ManagementFactory
import java.util.{Calendar, Date}

case class TimeMetricRecordConfig(resultFile: String = "cpuandmemoryusage.csv", recordFreq: Int = 60000)

object TimeMetric {
  val cal                                    = Calendar.getInstance()
  private val start                          = cal.getTime
  private var subStageStartTime              = start
  private var newTime                        = start
  private var stageStartTime                 = start
  private val STAGE_NOT_SET                  = "<not set>"
  private var lastStageName                  = STAGE_NOT_SET
  private var lastSubStageName               = STAGE_NOT_SET
  private var additionalMetaData             = STAGE_NOT_SET
  private var logger: String => Unit         = msg => baseLogger.debug(msg)
  private val baseLogger: Logger             = LoggerFactory.getLogger(getClass)
  private var recorder: Option[TimeRecorder] = None

  def initialize(
    newLogger: String => Unit = msg => println(msg),
    timeMetricRecordConfig: Option[TimeMetricRecordConfig]
  ): Unit = {
    logger = newLogger
    timeMetricRecordConfig match {
      case Some(config) =>
        recorder = Some(TimeRecorder(config))
        recorder.get.start()
      case _ =>
    }
  }

  private class TimeRecorder(timeMetricRecordConfig: TimeMetricRecordConfig) extends Thread {
    val osBean = ManagementFactory.getOperatingSystemMXBean().asInstanceOf[OperatingSystemMXBean]
    override def run(): Unit = {
      val writer = new PrintWriter(timeMetricRecordConfig.resultFile)
      try {
        writer.println(s"DateTime, MainStage, SubStage, MetaData, CPU (%), Used Memory (MB), Max Heap (MB)")
        while (true) {
          val (usedMemory, maxHeap) = getCurrentMemoryStats()
          writer.println(s"${getNewTime()}, $lastStageName, $lastSubStageName, $additionalMetaData, ${String
              .format("%.2f", osBean.getProcessCpuLoad * 100)}, $usedMemory, $maxHeap")
          Thread.sleep(timeMetricRecordConfig.recordFreq)
        }
      } catch {
        case exception: Exception =>
          baseLogger.debug("Some error in Time Recorder: ", exception)
      } finally {
        writer.close()
      }
    }
  }

  def initiateNewStage(stageName: String, additionalMetaDataToLog: String = STAGE_NOT_SET) = {
    if (lastStageName == STAGE_NOT_SET) {
      logger(s"${getNewTime()} - $stageName is invoked  \t\t\t- memory -> ${currentMemoryStatsInString()}")
      lastStageName = stageName
      additionalMetaData = additionalMetaDataToLog
    } else {
      initiateNewSubStage(stageName, additionalMetaDataToLog)
    }
  }

  def endLastStage() = {
    if (lastSubStageName == STAGE_NOT_SET) {
      logger(
        s"${getNewTime()} - $lastStageName is done ... \t\t\t- ${setNewTimeToStageLastAndGetTimeDiff()} - memory -> ${currentMemoryStatsInString()}"
      )
      lastStageName = STAGE_NOT_SET
      additionalMetaData = STAGE_NOT_SET
    } else {
      endLastSubStage()
    }
  }

  def endTheTotalProcessing(message: String) = {
    logger(s"${getNewTime()} - $message  \t\t\t- ${getTheTotalTime()} - memory -> ${currentMemoryStatsInString()}")
    recorder match {
      case Some(rec) => rec.interrupt()
      case _         =>
    }
    println(s"${getNewTime()} ------ Some one called this last end")
  }

  private def initiateNewSubStage(subStageName: String, additionalMetaDataToLog: String = STAGE_NOT_SET) = {
    logger(s"${getNewTime()} - --$subStageName is invoked  \t\t\t- memory -> ${currentMemoryStatsInString()}")
    lastSubStageName = subStageName
    additionalMetaData = additionalMetaDataToLog
  }

  private def endLastSubStage() = {
    logger(
      s"${getNewTime()} - --$lastSubStageName is done ... \t\t\t- ${setNewTimeToLastAndGetTimeDiff()} - memory -> ${currentMemoryStatsInString()}"
    )
    lastSubStageName = STAGE_NOT_SET
    additionalMetaData = STAGE_NOT_SET
  }

  private def getCurrentMemoryStats(): (String, String) = (
    s"${String.format("%.2f", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 0.000001)}",
    s"${String
        .format("%.2f", Runtime.getRuntime().maxMemory() * 0.000001)}"
  )

  private def currentMemoryStatsInString(): String = {
    val (usedMemory, maxHeap) = getCurrentMemoryStats()
    s"used '$usedMemory' MB, max '$maxHeap' MB"

  }

  private def getNewTime(): Date = {
    newTime = Calendar.getInstance().getTime
    newTime
  }

  private def getNewTimeAndSetItToStageLast(): Date = {
    stageStartTime = getNewTime()
    stageStartTime
  }

  private def setNewTimeToStageLastAndGetTimeDiff(): String = {
    val diff = newTime.getTime - stageStartTime.getTime
    stageStartTime = newTime
    getDiffFormatted(diff)
  }

  private def setNewTimeToLastAndGetTimeDiff(): String = {
    val diff = newTime.getTime - subStageStartTime.getTime
    subStageStartTime = newTime
    getDiffFormatted(diff)
  }

  def getDiffFormatted(diff: Long): String = {
    val seconds = diff / 1000
    val ms      = diff                  % 1000
    val s       = seconds               % 60
    val m       = (seconds / 60)        % 60
    val h       = (seconds / (60 * 60)) % 24
    String.format("%d ms - %02dh:%02dm:%02ds:%02dms", diff, h, m, s, ms)
  }

  private def getTheTotalTime(): String = {
    val diff = newTime.getTime - start.getTime
    getDiffFormatted(diff)
  }

}
