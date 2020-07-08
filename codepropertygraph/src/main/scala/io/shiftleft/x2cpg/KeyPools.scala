package io.shiftleft.x2cpg

import io.shiftleft.passes.KeyPool

object KeyPools {

  /**
    * For a given sequence of compilation units, return pairs
    * of type (CompilationUnit, KeyPool), thereby associating each
    * compilation unit with an interval of keys. This method guarantees
    * that intervals do not overlap.
    * */
  def assign[CompilationUnit](compilationUnits: List[CompilationUnit],
                              maxValue: Long = Long.MaxValue): List[(CompilationUnit, KeyPool)] = {
    val nIntervals: Int = Math.max(compilationUnits.size, 1)
    val intervalLen: Long = maxValue / nIntervals
    compilationUnits.zipWithIndex.map {
      case (t, i: Int) =>
        val first = i * intervalLen
        val last = first + intervalLen - 1
        (t, new KeyPool(first, last))
    }
  }

}
