package io.shiftleft.metrics

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


object TracerExample {
  def f(): Unit = {
    Tracer.measure("f") {
      g()
      g()
    }
  }
  def g(): Unit = {
    Tracer.measure("g") {
      q()
      Thread.sleep(100)
      (1 to 1000).par.map { _ => q(); () }
    }
  }
  def q(): Unit = {
    Tracer.measure("q") {
      Thread.sleep(10)
    }
  }

  def main(args: Array[String]): Unit = {
    f()
    Tracer.dump()
  }
}


