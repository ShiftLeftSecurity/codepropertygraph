package io.shiftleft

import org.slf4j.{Logger, LoggerFactory}

object Implicits {
  private val logger: Logger = LoggerFactory.getLogger(Implicits.getClass)

  implicit class IterableOnceDeco[T](val iterable: IterableOnce[T]) extends AnyVal {
    @deprecated(
      "please use `.loneElement` from flatgraph (mixed into the generated `language` packages) instead, which has a better name and will throw if the iterable has more than one element (rather than just log.warn)",
      since = "1.7.42 (July 2025)"
    )
    def onlyChecked: T = {
      if (iterable.iterator.hasNext) {
        val res = iterable.iterator.next()
        if (iterable.iterator.hasNext) {
          logger.warn("iterator was expected to have exactly one element, but it actually has more")
        }
        res
      } else {
        throw new NoSuchElementException()
      }
    }
  }
}
