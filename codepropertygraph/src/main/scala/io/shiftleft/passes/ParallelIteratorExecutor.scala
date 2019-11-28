package io.shiftleft.passes

import scala.collection.mutable
import scala.collection.parallel.immutable.{ParSeq, ParVector}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.reflect.ClassTag

class ParallelIteratorExecutor[T: ClassTag](iterator: Iterator[T]) {
  private val newTimes = new mutable.ArrayBuffer[Long]
  private val oldTimes = new mutable.ArrayBuffer[Long]
  private val collSizes = new mutable.ArrayBuffer[Int]

  def map[D <: DiffGraph](func: T => D): Iterator[D] = {
    iterator.toVector.par.map(func).iterator
  }
}
