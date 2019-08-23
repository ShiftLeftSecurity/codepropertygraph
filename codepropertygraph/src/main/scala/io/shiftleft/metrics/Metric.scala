package io.shiftleft.metrics

import java.io.PrintStream
import java.text.DecimalFormat

import scala.collection.concurrent.TrieMap

class Metric(val name: String) {
  type ThreadId = Long
  private val _inner: TrieMap[(ThreadId, String), Metric] = TrieMap()
  private var minTime: Long = Long.MaxValue
  private var maxTime: Long = Long.MinValue
  private var totalTime: Long = 0
  private var invocations: Long = 0
  private var startTime: Long = 0
  private val initialThreadId: ThreadId = Thread.currentThread().getId()

  def getInnerMetric(name: String, factory: => Metric): Metric = {
    val currentThreadId = Thread.currentThread().getId()
    _inner.getOrElseUpdate((currentThreadId, name), factory)
  }

  def begin(timeMeasure: TimeMeasure): Unit = {
    this.startTime = timeMeasure.currentValue
  }
  
  def end(timeMeasure: TimeMeasure): Unit = {
    val timeDelta = timeMeasure.currentValue - this.startTime
    this.invocations += 1
    this.totalTime += timeDelta
    if (timeDelta < this.minTime) this.minTime = timeDelta
    if (timeDelta > this.maxTime) this.maxTime = timeDelta
  }

  private def formatTime(nanos: Long): String = {
    val seconds: Double = nanos / 1000000000.0
    val df = new DecimalFormat("#.##s")
    df.format(seconds)
  }
  
  def dump(writer: PrintStream = System.out, parent: Option[Metric] = None, level: Int = 0): Unit = {
    val indent = "  " * level + "+"
    val percentageFormat = new DecimalFormat("#.#")
    val percentage = parent match {
      case None => ""
      case Some(m) => 
        if (m.totalTime != 0)
          percentageFormat.format(this.totalTime.toDouble / m.totalTime * 100) + "%"
        else
          "100%"
    }
    writer.println(indent + this.name + " "
      + percentage + " "
      + s"""total=${formatTime(totalTime)}, n=${invocations}, maxTime=${formatTime(maxTime)}, minTime=${formatTime(minTime)}"""
      + s""", threadId=${initialThreadId}"""
      )
    _inner
      .toList
      .sortBy { case (k, m) => m.totalTime }
      .foreach { case (k, m) => m.dump(writer, Some(this), level + 1)}
  }
}
