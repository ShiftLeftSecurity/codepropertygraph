package io.shiftleft.x2cpg

import java.util.concurrent.atomic.AtomicLong

object KeyIntervals {

  /**
    * For a given sequence of compilation units, return pairs
    * of type (CompilationUnit, KeyInterval), thereby associating each
    * compilation unit with a key interval. This method guarantees
    * that intervals do not overlap.
    * */
  def assign[CompilationUnitType](compilationUnits: Seq[CompilationUnitType],
                                  maxValue: Long = Long.MaxValue): Seq[(CompilationUnitType, KeyInterval)] = {
    val nIntervals: Int = Math.max(compilationUnits.size, 1)
    val intervalLen: Long = maxValue / nIntervals
    compilationUnits.zipWithIndex.map {
      case (t, i: Int) =>
        val first = i * intervalLen
        val last = first + intervalLen - 1
        (t, new KeyInterval(first, last))
    }
  }

}

/**
  * Integer interval [first, last]. We associate each
  * compilation unit with an interval such that intervals
  * do not overlap, that is, the pairwise intersection of
  * intervals is empty.
  * */
class KeyInterval(val first: Long, val last: Long) {

  private val cur: AtomicLong = new AtomicLong(first - 1)

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

}
