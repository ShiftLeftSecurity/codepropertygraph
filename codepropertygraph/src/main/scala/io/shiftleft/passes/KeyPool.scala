package io.shiftleft.passes

import java.util.concurrent.atomic.AtomicLong

/**
  * A pool of long integers that serve as node ids.
  * Using the method `next`, the pool provides the
  * next id from the interval [first, last] in a
  * thread-safe manner.
  * */
class KeyPool(val first: Long, val last: Long) {

  /**
    * Get next number in interval or raise if
    * number is larger than `last`
    * */
  def next: Long = {
    val n = cur.incrementAndGet()
    if (n > last) {
      throw new RuntimeException("Interval exhausted")
    } else {
      n
    }
  }

  private val cur: AtomicLong = new AtomicLong(first - 1)
}
