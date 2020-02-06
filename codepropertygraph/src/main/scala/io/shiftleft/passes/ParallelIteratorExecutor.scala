package io.shiftleft.passes

import scala.collection.mutable
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class ParallelIteratorExecutor[T](iterator: Iterator[T]) {
  def map[D](func: T => D): Iterator[D] = {
    val futures = Future.traverse(iterator) { element =>
      Future {
        func(element)
      }
    }
    Await.result(futures, Duration.Inf)
  }
  def foreach[D](func: T => Unit): Unit = {
    map(x => func(x))
  }
}
