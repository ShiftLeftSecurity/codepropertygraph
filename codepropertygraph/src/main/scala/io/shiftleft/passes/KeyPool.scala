package io.shiftleft.passes

import java.util.concurrent.atomic.{AtomicInteger, AtomicLong}

/**
  * A pool of long integers that serve as node ids.
  * Using the method `next`, the pool provides the
  * next id in a thread-safe manner.
  * */
trait KeyPool {

  /**
    * Returns the next id from the key pool
    * */
  def next: Long

}

/**
  A key pool that returns the integers of the interval
  [first, last] in a thread-safe manner.
  * */
class IntervalKeyPool(val first: Long, val last: Long) extends KeyPool {

  /**
    * Get next number in interval or raise if
    * number is larger than `last`
    * */
  def next: Long = {
    val n = cur.incrementAndGet()
    if (n > last) {
      throw new RuntimeException("Pool exhausted")
    } else {
      n
    }
  }

  private val cur: AtomicLong = new AtomicLong(first - 1)
}

/**
  * A key pool that returns elements of `seq`
  * in order in a thread-safe manner.
  * */
class SequenceKeyPool(seq: Seq[Long]) extends KeyPool {

  val seqLen: Int = seq.size
  var cur = new AtomicInteger(-1)

  override def next: Long = {
    val i = cur.incrementAndGet()
    if (i >= seqLen) {
      throw new RuntimeException("Pool exhausted")
    } else {
      seq(i)
    }
  }
}
