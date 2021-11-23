package io.shiftleft.passes

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContext, Future}

class ParallelIteratorExecutor[T](iterator: Iterator[T])(implicit val executionContext: ExecutionContext) {
  def map[D](func: T => D): Iterator[D] = {
    val futures = Future.traverse(iterator) { element =>
      Future {
        func(element)
      }
    }
    Await.result(futures, Duration.Inf)
  }
}
