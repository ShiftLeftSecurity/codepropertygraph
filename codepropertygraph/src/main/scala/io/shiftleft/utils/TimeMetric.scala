package io.shiftleft.utils

import com.sun.management.OperatingSystemMXBean
import org.slf4j.{Logger, LoggerFactory}

import java.io.PrintWriter
import java.lang.management.ManagementFactory
import java.util
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.{Calendar, Date}
import scala.collection.mutable

/** Stage wise Time, cpu, and memory recording config to pass file path and the recording frequency
  *
  * @param resultFile
  *   \- complete file path where the recording will be saved in csv format.
  * @param recordFreq
  *   \- Frequency to write the records in milliseconds.
  */
case class TimeMetricRecordConfig(resultFile: String = "cpuandmemoryusage.csv", recordFreq: Int = 60000)

/** These buffers are used for console logs to set the output fix length of the column. This will help make the console
  * output more readable. This has been kept configurable so that end consumer can adjust according to their use case.
  * @param MAX_FULLNAME_BUFFER
  *   \- If the class full names of passes is used to print the stage name on the console. Then this buffer is used
  *   considering the length the full name of the class
  * @param MAX_NAME_BUFFER
  *   \- In case the only simple name of the class is used to print the stage name on the console. Then this buffer is
  *   used considering the length of the only name of the class.
  * @param MAX_DONE_TIME_BUFFER
  *   \- When done status of the stage is updated, it also adds the total time taken by the time and then prints the cpu
  *   and memory stats afte that. This buffer will make `stats` information to be printed in the same colum for
  *   `invoked` status line to make it more readable.
  */
case class MaxPrintBuffers(MAX_FULLNAME_BUFFER: Int = 90, MAX_NAME_BUFFER: Int = 55, MAX_DONE_TIME_BUFFER: Int = 40)

/** Cpu and Memory stats information to log
  * @param usedOrAvgMemory
  *   \- current or average used memory in MB
  * @param maxHeap
  *   \- current Max Heap size in MB
  * @param usedOrAvgCpu
  *   \- current or average cpu used in %
  * @param maxStats
  *   \- Option with tuple having max CPU and Memory consumption.
  */
case class CpuMemoryStats(
  usedOrAvgMemory: Double,
  maxHeap: Double,
  usedOrAvgCpu: Double,
  maxStats: Option[(Double, Double)]
)

object TimeMetric {

  val cal = Calendar.getInstance()

  /** Stage wise performance will be only recorded if parallel recording is started through initialize(). This is
    * accessible outside of this class, in case this information is required to be consumed outside of this class.
    */
  val stagePerformance: mutable.Map[String, NumberProcessor] = mutable.Map()
  // This will hold start time when the process is initialised.
  private val start = cal.getTime
  // parent stage start time.
  private var stageStartTime = start
  // sub stage start time.
  private var subStageStartTime = start
  // latest time when updates.
  private var newTime = start
  // This constant is used to hold the `cpu` and `memory` numbers across the stages.
  private val ACROSS_PROCESS_STAGE = "<ACROSS_ALL>"
  // Constant used when stage or subStage is not set.
  private val STAGE_NOT_SET = "<not set>"
  // Constant used to indicate `Started` status while recording the stats inside .csv file.
  private val STARTED = "Started"
  // Constant used to indicate `Done` status while recording the stats inside .csv file.
  private val DONE = "Done"
  // This will hold the ongoing stage name
  private var lastStageName = STAGE_NOT_SET
  // This will hold the ongoing sub stage name
  private var lastSubStageName = STAGE_NOT_SET
  // This will hold the metadata for ongoing stage and sub stage combination.
  private var additionalMetaData = STAGE_NOT_SET
  // Flag to indicate the `invoked` and `done` status logs for every stage on console as well.
  private var printToConsole = false

  /** Stage `invoked` and `done` status's can be logged to both console logger file. By default it will always log to
    * logger file, printing the logs to console can be controlled using flag {@link TimeMetric#printToConsole}
    */
  private val logger: (String, Boolean) => Unit = (msg, subStage) => {
    if (printToConsole && (!subStage || !supressSubstages))
      println(msg)
    baseLogger.debug(msg)
  }
  private val baseLogger: Logger = LoggerFactory.getLogger(getClass)

  /** This recorder is the writer thread to write the stats to .csv file. This is not initialised by default, to make it
    * configurable and can be controlled from where it is getting used.
    */
  private var recorder: Option[TimeRecorder] = None
  // Flag which indicates whether to use full name or simple name of the pass while logging.
  private var fullName: Boolean = false
  // it holds buffer configuration.
  private var maxBuffers: MaxPrintBuffers = MaxPrintBuffers()

  /** This is to supress the sub stages stats being printed on console or not. This flag helps to print the stats only
    * for the passes being used in application.
    */
  private var supressSubstages: Boolean = true

  /** Calculate the buffer when it is being used. This is to avoid calculation again and again. Done lazy initialization
    * as the buffer configuration can be done by calling {@link TimeMetric#initialize()}
    */
  private lazy val maxStartingBuffer = {
    if (fullName) {
      maxBuffers.MAX_FULLNAME_BUFFER + maxBuffers.MAX_DONE_TIME_BUFFER
    } else {
      maxBuffers.MAX_NAME_BUFFER + maxBuffers.MAX_DONE_TIME_BUFFER
    }
  }

  /** Calculate the buffer when it is being used. This is to avoid calculation again and again. Done lazy initialization
    * as the buffer configuration can be done by calling {@link TimeMetric# initialize ( )}
    */
  private lazy val maxEndingBuffer = {
    if (fullName) {
      maxBuffers.MAX_FULLNAME_BUFFER
    } else {
      maxBuffers.MAX_NAME_BUFFER
    }
  }
  private val osBean = ManagementFactory.getOperatingSystemMXBean().asInstanceOf[OperatingSystemMXBean]

  /** initialize method is expected to be invoked at the very start of the code. One can initialise the required flags
    * and the other configurations to start the stats collections.
    *
    * @param useFullName
    *   \- Flag to indicate whether to use fullName or simple name of the stage for logging
    * @param consoleLog
    *   \- Flag to indicate log the `invoked` and `done` status of every stage on console as well along with debug logs.
    * @param timeMetricRecordConfig
    *   \- If you set this to None, it will not start the thread to log the `cpu` and `memory` consumption stats for
    *   each stage after regular interval of time to given file path. Configure the expected config values and it will
    *   trigger the thread to write the stats after configured period of time. Refer {@link TimeMetricRecordConfig}
    * @param maxPrintBuffers
    *   \- Print buffer configuration refer {@link MaxPrintBuffers}
    * @param supressSubStagesConsoleLog
    *   \- Flag to suppress the sub stage logs to consoles and only log them to debug logs.
    */
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
    printToConsole = consoleLog
    timeMetricRecordConfig match {
      case Some(config) =>
        stagePerformance += (ACROSS_PROCESS_STAGE -> NumberProcessor())
        recorder = Some(TimeRecorder(config))
        recorder.get.start()
      case _ =>
    }
  }

  /** This will help change the flag once it has been set initially using {@link TimeMetric#initialize()} method.
    * @param flag
    *   \- flag to suppress console log for sub stages.
    */
  def setSupressSubstagesFlag(flag: Boolean): Unit = supressSubstages = flag

  /** Writer thread to write the on going stage stats (CPU and Memory) after configured period of time in milliseconds.
    * @param timeMetricRecordConfig
    *   \- required configuration to log the stats into CSV file refer - {@link TimeMetricRecordConfig}
    */
  private class TimeRecorder(timeMetricRecordConfig: TimeMetricRecordConfig) extends Thread {
    // Non blocking Queue to log the stage `Started` and `Done` status, pushed from the respective methods who facilitate start and end of the stage.
    val queue: util.Queue[String] = new ConcurrentLinkedQueue[String]()

    /** Method to push the event to non blocking queue
      * @param event
      *   \- stage `Started` and `Done` status event
      */
    def pushEvent(event: String): Unit = queue.offer(event)

    /** Thread run method which goes into loop to log the ongoing stage stats (CPU and Memory) to CSV file after regular
      * interval of time (configured in milliseconds). When it wakes up from the configured sleep time, it will first
      * check if there are any events pushed to queue for logging them to csv file and log all of them in one go and
      * then log the stats of ongoing stage.
      */
    override def run(): Unit = {
      val writer = new PrintWriter(timeMetricRecordConfig.resultFile)
      try {
        writer.println(s"DateTime, MainStage, SubStage, MetaData, CPU (%), Used Memory (MB), Max Heap (MB)")
        while (true) {
          // This while loop will take care of writing all the accumulated events in the queue first.
          while (!queue.isEmpty) writer.println(queue.poll)
          val stats = getCurrentCpuMemoryStats()
          stagePerformance(ACROSS_PROCESS_STAGE).record(stats.usedOrAvgCpu, stats.usedOrAvgMemory)
          if (lastStageName != STAGE_NOT_SET)
            then stagePerformance (s"+ $lastStageName").record(stats.usedOrAvgCpu, stats.usedOrAvgMemory)
          if (lastSubStageName != STAGE_NOT_SET)
            then stagePerformance (s"-- $lastSubStageName").record(stats.usedOrAvgCpu, stats.usedOrAvgMemory)
          writer.println(
            s"${getNewTime()}, $lastStageName, $lastSubStageName, $additionalMetaData, ${getCpuAndMemoryStatsForCSVInString(stats)}"
          )
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

  /** API to signal start of the stage. This same method is used to initiate parent stage as well as sub stage. If this
    * method is called after parent stage is initiated, it will initiate child stage internally.
    * @param stageName
    *   \- stage name in case it is class name, this should be passed as class.simpleName
    * @param stageFullName
    *   \- class full name to use as stage when {@link TimeMetric#fullName} flag is set.
    * @param additionalMetaDataToLog
    *   \- Any additional meta data string in the context of ongoing stage and subStage.
    */
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
      val newTime = getNewTime()
      val logLine = pushToRecorderAndGetFormattedStringForLogger(
        s"$newTime",
        usedStageName,
        STAGE_NOT_SET,
        STARTED,
        STARTED,
        Some(s"+ $usedStageName"),
        None,
        s"- $usedStageName is invoked"
      )
      logger(logLine, false)
      stageStartTime = newTime
      lastStageName = usedStageName
      additionalMetaData = additionalMetaDataToLog
    } else {
      initiateNewSubStage(usedStageName, additionalMetaDataToLog)
    }
  }

  /** API to end the last stage. The same API to be used to end the last subStage and then call the same method to end
    * the last parent stage once all the child stages are done.
    */
  def endLastStage(): Unit = {
    if (lastSubStageName == STAGE_NOT_SET) {
      val logMessage = pushToRecorderAndGetFormattedStringForLogger(
        s"${getNewTime()}",
        lastStageName,
        STAGE_NOT_SET,
        DONE,
        DONE,
        Some(s"+ $lastStageName"),
        Some(getLastStageTimeTaken()),
        s"- $lastStageName is done ..."
      )
      logger(logMessage, false)
      lastStageName = STAGE_NOT_SET
      additionalMetaData = STAGE_NOT_SET
    } else {
      endLastSubStage()
    }
  }

  /** This will end the time logging along with stats. It you have started the cpu and memory stats logging with {@link
    * TimeMetric#initialize()}, you need to make sure to call this method once all the processing is done. This will
    * also end the writer deamon thread which has been started to log the stats in CSV file.
    * @param message
    *   \- final log message to end the logging.
    */
  def endTheTotalProcessing(message: String): Unit = {
    val logMessage = pushToRecorderAndGetFormattedStringForLogger(
      s"${getNewTime()}",
      message,
      "Total Time taken",
      DONE,
      s"${getTheTotalTime()}",
      Some(ACROSS_PROCESS_STAGE),
      Some(getTheTotalTime()),
      s"- $message"
    )
    logger(logMessage, false)
    recorder match {
      case Some(rec) =>
        // this small delay to make sure writer thread logs the last record before it gets exited with rec.interrupt() invocation.
        Thread.sleep(200)
        rec.interrupt()
      case _ =>
    }
  }

  /** Common utility method to push the `Started` and `Done` events as well as create debug or console log message
    * string with the required formatting.
    * @param time
    *   \- current time
    * @param parentStage
    *   \- main stage
    * @param subStage
    *   \- Sub stage
    * @param status
    *   \- Status `Started` or `Done`
    * @param metaData
    *   \- value to be inserted in meta data column
    * @param avgAndMaxStatsLookUpKey
    *   \- avg and max stats look up key
    * @param timeTaken
    *   \- Time taken to log with `Done` status
    * @param loggerMessage
    *   \- logger event message to log
    * @return
    */
  private def pushToRecorderAndGetFormattedStringForLogger(
    time: String,
    parentStage: String,
    subStage: String,
    status: String,
    metaData: String,
    avgAndMaxStatsLookUpKey: Option[String],
    timeTaken: Option[String],
    loggerMessage: String
  ): String = {
    val stats = getCurrentCpuMemoryStats(avgAndMaxStatsLookUpKey)
    recorder match {
      case Some(rec) =>
        rec.pushEvent(s"$time, $parentStage, $subStage, $metaData, ${getCpuAndMemoryStatsForCSVInString(stats)}")
        status match {
          case STARTED => stagePerformance += (avgAndMaxStatsLookUpKey.get -> NumberProcessor())
          case _       =>
        }
      case _ =>
    }
    status match {
      case STARTED =>
        val formattedString =
          s"%-${maxStartingBuffer}s%s".format(loggerMessage, s"- stats -> ${cpuAndMemoryStatsForLoggerInString(stats)}")
        s"${getNewTime()} $formattedString"
      case DONE =>
        val formattedString = s"%-${maxEndingBuffer}s%s".format(
          s"$loggerMessage",
          s"%-${maxBuffers.MAX_DONE_TIME_BUFFER}s%s"
            .format(s"- ${timeTaken.get}", s"- stats -> ${cpuAndMemoryStatsForLoggerInString(stats)}")
        )
        s"$time $formattedString"
    }
  }

  /** internal method initiate sub stgae
    * @param subStageName
    *   \- Sub stage name
    * @param additionalMetaDataToLog
    *   \- Additional meta data to log
    */
  private def initiateNewSubStage(subStageName: String, additionalMetaDataToLog: String = STAGE_NOT_SET): Unit = {
    val newTime = getNewTime()
    val logLine = pushToRecorderAndGetFormattedStringForLogger(
      s"$newTime",
      lastStageName,
      subStageName,
      STARTED,
      STARTED,
      Some(s"-- $subStageName"),
      None,
      s"- --$subStageName is invoked"
    )
    logger(logLine, false)
    subStageStartTime = newTime
    lastSubStageName = subStageName
    additionalMetaData = additionalMetaDataToLog
  }

  /** Internal method to end the last sub stage.
    */
  private def endLastSubStage(): Unit = {
    val logMessage = pushToRecorderAndGetFormattedStringForLogger(
      s"${getNewTime()}",
      lastStageName,
      lastSubStageName,
      DONE,
      DONE,
      Some(s"-- $lastSubStageName"),
      Some(getLastSubStageTimeTaken()),
      s"- --$lastSubStageName is done ..."
    )
    logger(logMessage, false)
    lastSubStageName = STAGE_NOT_SET
    additionalMetaData = STAGE_NOT_SET
  }

  /** Utility method to generate formatted string to log the stats into CSV file.
    *
    * @param stats
    *   \- machine stats data refer {@link CpuMemoryStats}
    * @return
    */
  private def getCpuAndMemoryStatsForCSVInString(stats: CpuMemoryStats): String =
    s"${doubleFormatter(stats.usedOrAvgCpu)}, ${doubleFormatter(stats.usedOrAvgMemory)}, ${doubleFormatter(stats.maxHeap)}"

  /** Internal utility method to fetch CPU and Memory stats.
    * @param stage
    *   \- Lookup key for avg and max stats.
    * @return
    *   \- Formatted CSV string
    */
  private def getCurrentCpuMemoryStats(stage: Option[String] = None): CpuMemoryStats = {
    stage match {
      case Some(st) if stagePerformance.contains(st) =>
        val numberProcessor     = stagePerformance(st)
        val (avgCpu, avgMemory) = numberProcessor.getAverage
        val (maxCpu, maxMemory) = numberProcessor.getMax
        CpuMemoryStats(
          usedOrAvgMemory = avgMemory,
          maxHeap = Runtime.getRuntime().maxMemory() * 0.000001,
          usedOrAvgCpu = avgCpu,
          maxStats = Some(maxCpu, maxMemory)
        )
      case _ =>
        CpuMemoryStats(
          usedOrAvgMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 0.000001,
          maxHeap = Runtime.getRuntime().maxMemory() * 0.000001,
          usedOrAvgCpu = osBean.getProcessCpuLoad * 100,
          None
        )
    }
  }

  /** Internal utility method to generate formatted string to log stats to console or debug logger.
    * @param stats
    *   \- machine stats data refer {@link CpuMemoryStats}
    * @return
    *   \- Formatted log string.
    */
  private def cpuAndMemoryStatsForLoggerInString(stats: CpuMemoryStats): String = {
    val maxStats = stats.maxStats match {
      case Some((maxCpu, maxMemory)) =>
        s"MAX => '${doubleFormatter(maxCpu)}%', '${doubleFormatter(maxMemory)}' MB"
      case _ => ""
    }
    s"'${doubleFormatter(stats.usedOrAvgCpu)}%', '${doubleFormatter(stats.usedOrAvgMemory)}' MB, '${doubleFormatter(stats.maxHeap)}' MB $maxStats"
  }

  /** Utility method to format double number to show 2 decimal points
    * @param value
    *   \- Double value to be formatted
    * @return
    *   \- Formatted double value in string.
    */
  private def doubleFormatter(value: Double): String = s"${"%.2f".formatLocal(java.util.Locale.US, value)}"

  /** Get current time by initialising internal latest data time tracker.
    * @return
    *   \- latest data time.
    */
  private def getNewTime(): Date = {
    newTime = Calendar.getInstance().getTime
    newTime
  }

  /** Calculate the ongoing stags total time
    * @return
    *   \- total time taken by the last stage.
    */
  private def getLastStageTimeTaken(): String = {
    val diff = newTime.getTime - stageStartTime.getTime
    getDiffFormatted(diff)
  }

  /** Calculate the total time taken by the last sub stage.
    * @return
    *   \- total time taken by the last sub stage.
    */
  private def getLastSubStageTimeTaken(): String = {
    val diff = newTime.getTime - subStageStartTime.getTime
    getDiffFormatted(diff)
  }

  /** Calculate the total time taken till this point
    * @return
    *   \- total time
    */
  private def getTheTotalTime(): String = {
    val diff = newTime.getTime - start.getTime
    getDiffFormatted(diff)
  }

  /** Get the time formatted in readable string format.
    * @param diff
    *   \- time diff to be formatted
    * @return
    *   \- Formatted time diff
    */
  def getDiffFormatted(diff: Long): String = {
    val seconds = diff / 1000
    val ms      = diff                  % 1000
    val s       = seconds               % 60
    val m       = (seconds / 60)        % 60
    val h       = (seconds / (60 * 60)) % 24
    String.format("%d ms - %02dh:%02dm:%02ds:%02dms", diff, h, m, s, ms)
  }
}

/** Common utility to record the CPU and Memory stats for a stage to calculate average and max values.
  */
private class NumberProcessor {
  private var cpu: Double       = 0.0
  private var cpuMax: Double    = 0.0
  private var memory: Double    = 0.0
  private var memoryMax: Double = 0.0
  private var count: Int        = 0

  /** Take the stats values for a given stage at each interval and do the aggregation.
    * @param cpuUsed
    *   \- current cpu % used.
    * @param memoryUsed
    *   \- current memory used.
    */
  def record(cpuUsed: Double, memoryUsed: Double): Unit = {
    cpu += cpuUsed
    cpuMax = if (cpuMax < cpuUsed) then cpuUsed else cpuMax
    memory += memoryUsed
    memoryMax = if (memoryMax < memoryUsed) then memoryUsed else memoryMax
    count += 1
  }

  /** get the average of CPU and memory used.
    * @return
    *   \- average CPU and memory used.
    */
  def getAverage: (Double, Double) = {
    val currentCount = count
    if (currentCount == 0) {
      (0.0, 0.0) // Avoid division by zero
    } else {
      (cpu / currentCount.toDouble, memory / currentCount.toDouble)
    }
  }

  /** Get max CPU and Memory used.
    * @return
    *   \- Max CPU and memory used.
    */
  def getMax: (Double, Double) = (cpuMax, memoryMax)
}
