package io.shiftleft.x2cpg

import io.shiftleft.passes.DiffGraph

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class X2Cpg[CompilationUnit] {

  private val dbWriter = new DbWriter()

  /**
    * For each compilation unit in `units`, create a DiffGraph using `f`
    * and merge all DiffGraphs into the Code Property Graph at `outputPath`.
    * This component guarantees that ids are stable.
    *
    * @param units compilation units (to be processed in parallel)
    * @param outputPath location where CPG will be written
    * @param f side-effect free function that maps compilation units to DiffGraphs.
    *
    **/
  def run(units: List[CompilationUnit], outputPath: String)(f: CompilationUnit => DiffGraph): Unit = {

    val it = KeyPools.assign(units).iterator
    dbWriter.open(outputPath)

    val futures = Future.traverse(it) {
      case (unit, keyPool) =>
        Future {
          val diffGraph = f(unit)
          dbWriter.add(diffGraph, keyPool)
        }
    }

    Await.result(futures, Duration.Inf)
  }

  /**
    * Perform cleanup work. Calling this method is mandatory
    * to end up with a valid CPG.
    * */
  def shutdown(): Unit = {
    dbWriter.close()
  }

}
