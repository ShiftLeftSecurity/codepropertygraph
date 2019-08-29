package io.shiftleft.utils.metrics

import scala.util.DynamicVariable

object Tracer {
  val root: Metric = new Metric("main")
  val current: DynamicVariable[Metric] = new DynamicVariable[Metric](root)

  def measure[T](name: String, timeMeasure: TimeMeasure = Measure.timeMeasure)(block: => T): T = {
    val metric = current.value.getInnerMetric(name, new Metric(name))
    try {
      current.withValue(metric) {
        metric.begin(timeMeasure)
        block
      }
    } finally {
      metric.end(timeMeasure)
    }
  }

  def dump(): Unit = {
    root.dump(System.out)
  }
}
