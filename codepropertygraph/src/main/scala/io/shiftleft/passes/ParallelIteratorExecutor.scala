package io.shiftleft.passes

import scala.collection.mutable
import scala.collection.parallel.immutable.{ParSeq, ParVector}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.reflect.ClassTag

class ParallelIteratorExecutor[T: ClassTag](iterator: Iterator[T]) {
  def map[D <: DiffGraph](func: T => D): Iterator[D] = {
    iterator.toVector.par.map(func).iterator
  }
}
