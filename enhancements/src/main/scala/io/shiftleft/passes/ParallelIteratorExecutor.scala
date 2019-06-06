package io.shiftleft.passes

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration

class ParallelIteratorExecutor[T](iterator: Iterator[T]) {
  def map[D](func: T => D): Iterator[D] = {
    val futures = Future.traverse(iterator) { element =>
      Future {
        func(element)
      }
    }

    Await.result(futures, Duration.Inf)
  }
}
