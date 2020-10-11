package io.shiftleft.fuzzyc2cpg

import io.shiftleft.passes.IntervalKeyPool

object KeyPools {

  /**
    * Divide the keyspace into n intervals and return
    * a list of corresponding key pools.
    * */
  def obtain(n: Long, minValue: Long = 0, maxValue: Long = Long.MaxValue): List[IntervalKeyPool] = {
    val nIntervals = Math.max(n, 1)
    val intervalLen: Long = (maxValue - minValue) / nIntervals
    List.range(0, nIntervals).map { i =>
      val first = i * intervalLen + minValue
      val last = first + intervalLen - 1
      new IntervalKeyPool(first, last)
    }
  }

}
