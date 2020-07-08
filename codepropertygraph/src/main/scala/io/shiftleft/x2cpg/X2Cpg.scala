package io.shiftleft.x2cpg

import io.shiftleft.passes.{DiffGraph, ParallelIteratorExecutor}

/**
  * For each compilation unit in `units`, create a DiffGraph using `f`
  * and merge all DiffGraphs into the Code Property Graph at `outputPath`.
  **/
class X2Cpg[CompilationUnit](units: List[CompilationUnit], outputPath: String)(
    f: ((CompilationUnit, KeyInterval)) => DiffGraph) {

  private val it = KeyIntervals.assign(units).iterator
  private val dbWriter = new DbWriter(outputPath)
  dbWriter.start()
  new ParallelIteratorExecutor(it).map[DiffGraph](f)
  dbWriter.stop()
}
