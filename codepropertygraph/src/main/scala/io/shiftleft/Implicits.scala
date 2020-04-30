package io.shiftleft

import org.apache.logging.log4j.LogManager

object Implicits {

  private val logger = LogManager.getLogger(getClass)

  implicit class IteratorDeco[T](iterator: Iterator[T]) {
    def onlyChecked: T = {
      if (iterator.hasNext) {
        val res = iterator.next
        if (iterator.hasNext) {
          logger.error("iterator was expected to have exactly one element, but it actually has more")
        }
        res
      } else { throw new NoSuchElementException() }
    }
  }

  /**
    * A wrapper around a Java iterator that throws a proper NoSuchElementException.
    *
    * Proper in this case means an exception with a stack trace.
    * This is intended to be used as a replacement for next() on the iterators
    * returned from TinkerPop since those are missing stack traces.
    */
  implicit class JavaIteratorDeco[T](iterator: java.util.Iterator[T]) {
    def nextChecked: T = {
      try {
        iterator.next
      } catch {
        case _: NoSuchElementException =>
          throw new NoSuchElementException()
      }
    }

    def onlyChecked: T = {
      if (iterator.hasNext) {
        val res = iterator.next
        if (iterator.hasNext) {
          logger.error("iterator was expected to have exactly one element, but it actually has more")
        }
        res
      } else { throw new NoSuchElementException() }
    }

    def nextOption: Option[T] = {
      if (iterator.hasNext) {
        Some(iterator.next)
      } else {
        None
      }
    }
  }

}
