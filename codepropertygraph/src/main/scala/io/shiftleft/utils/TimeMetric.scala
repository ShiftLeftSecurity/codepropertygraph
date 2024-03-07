package io.shiftleft.utils

import com.sun.management.OperatingSystemMXBean
import org.slf4j.{Logger, LoggerFactory}

import java.io.PrintWriter
import java.lang.management.ManagementFactory
import java.util
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.{Calendar, Date}
import scala.collection.mutable
case class TimeMetricRecordConfig(resultFile: String = "cpuandmemoryusage.csv", recordFreq: Int = 60000)
case class MaxPrintBuffers(MAX_FULLNAME_BUFFER: Int = 90, MAX_NAME_BUFFER: Int = 55, MAX_DONE_TIME_BUFFER: Int = 40)
object TimeMetric {
  val cal = Calendar.getInstance()
  // Stage wise performance will be only recorded if parallel recording is started through initialize()
  val stagePerformance: mutable.Map[String, NumberProcessor] = mutable.Map()
  private val start                                          = cal.getTime
  private var subStageStartTime                              = start
  private var newTime                                        = start
  private var stageStartTime                                 = start
  private val ACROSS_PROCESS_STAGE                           = "<ACROSS_ALL>"
  private val STAGE_NOT_SET                                  = "<not set>"
  private val STARTED                                        = "Started"
  private val DONE                                           = "Done"
  private var lastStageName                                  = STAGE_NOT_SET
  private var lastSubStageName                               = STAGE_NOT_SET
  private var additionalMetaData                             = STAGE_NOT_SET
  private var alsoLogConsole                                 = false
  private val logger: (String, Boolean) => Unit = (msg, subStage) => {
    if (alsoLogConsole && (!subStage || !supressSubstages))
      println(msg)
    baseLogger.debug(msg)
  }
  private val baseLogger: Logger             = LoggerFactory.getLogger(getClass)
  private var recorder: Option[TimeRecorder] = None
  private var fullName: Boolean              = false
  private var maxBuffers: MaxPrintBuffers    = MaxPrintBuffers()
  private var supressSubstages: Boolean      = true
  private lazy val maxStartingBuffer = {
    if (fullName) {
      maxBuffers.MAX_FULLNAME_BUFFER + maxBuffers.MAX_DONE_TIME_BUFFER
    } else {
      maxBuffers.MAX_NAME_BUFFER + maxBuffers.MAX_DONE_TIME_BUFFER
    }
  }
  private lazy val maxEndingBuffer = {
    if (fullName) {
      maxBuffers.MAX_FULLNAME_BUFFER
    } else {
      maxBuffers.MAX_NAME_BUFFER
    }
  }
  private val osBean = ManagementFactory.getOperatingSystemMXBean().asInstanceOf[OperatingSystemMXBean]

  def initialize(
    useFullName: Boolean = false,
    consoleLog: Boolean = false,
    timeMetricRecordConfig: Option[TimeMetricRecordConfig] = None,
    maxPrintBuffers: MaxPrintBuffers = MaxPrintBuffers(),
    supressSubStagesConsoleLog: Boolean = true
  ): Unit = {
    fullName = useFullName
    maxBuffers = maxPrintBuffers
    supressSubstages = supressSubStagesConsoleLog
    alsoLogConsole = consoleLog
    timeMetricRecordConfig match {
      case Some(config) =>
        stagePerformance += (ACROSS_PROCESS_STAGE -> NumberProcessor())
        recorder = Some(TimeRecorder(config))
        recorder.get.start()
      case _ =>
    }
  }

  def setSupressSubstagesFlag(flag: Boolean): Unit = supressSubstages = flag

  private class TimeRecorder(timeMetricRecordConfig: TimeMetricRecordConfig) extends Thread {

    val queue: util.Queue[String] = new ConcurrentLinkedQueue[String]()

    def pushEvent(event: String): Unit = queue.offer(event)

    override def run(): Unit = {
      val writer = new PrintWriter(timeMetricRecordConfig.resultFile)
      try {
        writer.println(s"DateTime, MainStage, SubStage, MetaData, CPU (%), Used Memory (MB), Max Heap (MB)")
        while (true) {
          // This while loop will take care of writing all the accumulated events in the queue first.
          while (!queue.isEmpty) writer.println(queue.poll)
          val (usedMemory, maxHeap, cpuUsed, _) = getCurrentCpuMemoryStats()
          stagePerformance(ACROSS_PROCESS_STAGE).process(cpuUsed, usedMemory)
          if (lastStageName != STAGE_NOT_SET) then stagePerformance (s"+ $lastStageName").process(cpuUsed, usedMemory)
          if (lastSubStageName != STAGE_NOT_SET)
            then stagePerformance (s"-- $lastSubStageName").process(cpuUsed, usedMemory)
          writer.println(s"${getNewTime()}, $lastStageName, $lastSubStageName, $additionalMetaData, ${String
              .format("%.2f", cpuUsed)}, ${String.format("%.2f", usedMemory)}, ${String.format("%.2f", maxHeap)}")
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

  def initiateNewStage(
    stageName: String,
    stageFullName: Option[String] = None,
    additionalMetaDataToLog: String = STAGE_NOT_SET
  ): Unit = {
    val usedStageName = stageFullName match {
      case Some(useFullName) if fullName => useFullName
      case _                             => stageName
    }
    if (lastStageName == STAGE_NOT_SET) {
      val (usedMemory, maxHeap, cpuUsed, _) = getCurrentCpuMemoryStats()
      pushToRecorder(
        s"${getNewTime()}, $usedStageName, $STAGE_NOT_SET",
        s"+ $usedStageName",
        STARTED,
        usedMemory,
        maxHeap,
        cpuUsed
      )
      val formattedString =
        s"%-${maxStartingBuffer}s%s"
          .format(
            s"- $usedStageName is invoked",
            s"- stats -> ${currentCpuMemoryStatsInString(cpuUsed, usedMemory, maxHeap)}"
          )
      logger(s"${getNewTime()} $formattedString", false)
      lastStageName = usedStageName
      additionalMetaData = additionalMetaDataToLog
    } else {
      initiateNewSubStage(usedStageName, additionalMetaDataToLog)
    }
  }

  def endLastStage(): Unit = {
    if (lastSubStageName == STAGE_NOT_SET) {
      val (usedMemory, maxHeap, cpuUsed, maxStats) = getCurrentCpuMemoryStats(Some(s"+ $lastStageName"))
      pushToRecorder(
        s"${getNewTime()}, $lastStageName, $STAGE_NOT_SET",
        lastStageName,
        DONE,
        usedMemory,
        maxHeap,
        cpuUsed
      )
      val formattedString = s"%-${maxEndingBuffer}s%s".format(
        s"- $lastStageName is done ...",
        s"%-${maxBuffers.MAX_DONE_TIME_BUFFER}s%s".format(
          s"- ${setNewTimeToStageLastAndGetTimeDiff()}",
          s"- stats -> ${currentCpuMemoryStatsInString(cpuUsed, usedMemory, maxHeap, maxStats)}"
        )
      )
      logger(s"${getNewTime()} $formattedString", false)
      lastStageName = STAGE_NOT_SET
      additionalMetaData = STAGE_NOT_SET
    } else {
      endLastSubStage()
    }
  }

  def endTheTotalProcessing(message: String): Unit = {
    val (usedMemory, maxHeap, cpuUsed, maxStats) = getCurrentCpuMemoryStats(Some(ACROSS_PROCESS_STAGE))
    val formattedString = s"%-${maxEndingBuffer}s%s".format(
      s"- $message",
      s"%-${maxBuffers.MAX_DONE_TIME_BUFFER}s%s".format(
        s"- ${getTheTotalTime()}",
        s"- stats -> ${currentCpuMemoryStatsInString(cpuUsed, usedMemory, maxHeap, maxStats)}"
      )
    )
    logger(s"${getNewTime()} $formattedString", false)
    recorder match {
      case Some(rec) =>
        rec.pushEvent(
          s"${getNewTime()}, $message, Total Time taken, ${getTheTotalTime()}, ${String
              .format("%.2f", osBean.getProcessCpuLoad * 100)}, ${String.format("%.2f", usedMemory)}, ${String.format("%.2f", maxHeap)}"
        )
        Thread.sleep(100)
        rec.interrupt()
      case _ =>
    }
  }

  private def pushToRecorder(
    event: String,
    stage: String,
    status: String,
    usedMemory: Double,
    maxHeap: Double,
    cpuUsed: Double
  ): Unit = {
    recorder match {
      case Some(rec) =>
        rec.pushEvent(s"$event, $status, ${String
            .format("%.2f", cpuUsed)}, ${String.format("%.2f", usedMemory)}, ${String.format("%.2f", maxHeap)}")
        status match {
          case STARTED => stagePerformance += (stage -> NumberProcessor())
          case _       =>
        }
      case _ =>
    }
  }

  private def initiateNewSubStage(subStageName: String, additionalMetaDataToLog: String = STAGE_NOT_SET): Unit = {
    val (usedMemory, maxHeap, cpuUsed, _) = getCurrentCpuMemoryStats()
    pushToRecorder(
      s"${getNewTime()}, $lastStageName, $subStageName",
      s"-- $subStageName",
      STARTED,
      usedMemory,
      maxHeap,
      cpuUsed
    )
    val formattedString =
      s"%-${maxStartingBuffer}s%s"
        .format(
          s"- --$subStageName is invoked",
          s"- stats -> ${currentCpuMemoryStatsInString(cpuUsed, usedMemory, maxHeap)}"
        )
    logger(s"${getNewTime()} $formattedString", true)
    lastSubStageName = subStageName
    additionalMetaData = additionalMetaDataToLog
  }

  private def endLastSubStage(): Unit = {
    val (usedMemory, maxHeap, cpuUsed, maxStats) = getCurrentCpuMemoryStats(Some(s"-- $lastSubStageName"))
    pushToRecorder(
      s"${getNewTime()}, $lastStageName, $lastSubStageName",
      lastSubStageName,
      DONE,
      usedMemory,
      maxHeap,
      cpuUsed
    )
    val formattedString = s"%-${maxEndingBuffer}s%s".format(
      s"- --$lastSubStageName is done ...",
      s"%-${maxBuffers.MAX_DONE_TIME_BUFFER}s%s".format(
        s"- ${setNewTimeToLastAndGetTimeDiff()}",
        s"- stats -> ${currentCpuMemoryStatsInString(cpuUsed, usedMemory, maxHeap, maxStats)}"
      )
    )
    logger(s"${getNewTime()} $formattedString", true)
    lastSubStageName = STAGE_NOT_SET
    additionalMetaData = STAGE_NOT_SET
  }

  private def getCurrentCpuMemoryStats(
    stage: Option[String] = None
  ): (Double, Double, Double, Option[(Double, Double)]) = {
    stage match {
      case Some(st) if stagePerformance.contains(st) =>
        val numberProcessor     = stagePerformance(st)
        val (avgCpu, avgMemory) = numberProcessor.getAverage
        val (maxCpu, maxMemory) = numberProcessor.getMax
        (avgMemory, Runtime.getRuntime().maxMemory() * 0.000001, avgCpu, Some(maxCpu, maxMemory))
      case _ =>
        (
          (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 0.000001,
          Runtime.getRuntime().maxMemory() * 0.000001,
          osBean.getProcessCpuLoad * 100,
          None
        )
    }
  }

  private def currentCpuMemoryStatsInString(
    cpuUsed: Double,
    usedMemory: Double,
    maxHeap: Double,
    maxCpuAndMemoryUsed: Option[(Double, Double)] = None
  ): String = {
    val maxStats = maxCpuAndMemoryUsed match {
      case Some((maxCpu, maxMemory)) =>
        s"MAX => '${String.format("%.2f", maxCpu)}%', '${String.format("%.2f", maxMemory)}' MB"
      case _ => ""
    }
    s"'${String.format("%.2f", cpuUsed)}%', '${String.format("%.2f", usedMemory)}' MB, '${String.format("%.2f", maxHeap)}' MB $maxStats"

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

private class NumberProcessor {
  private var cpu: Double       = 0.0
  private var cpuMax: Double    = 0.0
  private var memory: Double    = 0.0
  private var memoryMax: Double = 0.0
  private var count: Int        = 0

  def process(cpuUsed: Double, memoryUsed: Double): Unit = {
    cpu += cpuUsed
    cpuMax = if (cpuMax < cpuUsed) then cpuUsed else cpuMax
    memory += memoryUsed
    memoryMax = if (memoryMax < memoryUsed) then memoryUsed else memoryMax
    count += 1
  }

  def getAverage: (Double, Double) = {
    val currentCount = count
    if (currentCount == 0) {
      (0.0, 0.0) // Avoid division by zero
    } else {
      (cpu / currentCount.toDouble, memory / currentCount.toDouble)
    }
  }

  def getMax: (Double, Double) = (cpuMax, memoryMax)
}
