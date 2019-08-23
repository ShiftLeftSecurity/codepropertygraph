package io.shiftleft.metrics

trait Measure {
  def currentValue: Long
}

trait TimeMeasure extends Measure

trait HeapMeasure extends Measure

object Measure {
  def mockTime(values: Iterable[Long]) = new TimeMeasure {
    override def currentValue: Long = values.iterator.next
  }
  val timeMeasure: TimeMeasure = new TimeMeasure {
    override def currentValue: Long = System.nanoTime
  }
  val heapMeasure = new HeapMeasure {
    override def currentValue: Long = {
      val rt = Runtime.getRuntime
      rt.totalMemory() - rt.freeMemory()
    }
  }
}
