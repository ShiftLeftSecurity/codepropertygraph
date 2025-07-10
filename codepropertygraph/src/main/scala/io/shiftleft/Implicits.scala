package io.shiftleft

import org.slf4j.{Logger, LoggerFactory}

object Implicits {
  private val logger: Logger = LoggerFactory.getLogger(Implicits.getClass)

  extension [A](iterable: IterableOnce[A]) {

    /** @see {{{loneElement(hint)}}} */
    def loneElement: A =
      loneElement(hint = "")

    /** @return
      *   the one and only element from an Iterable
      * @throws NoSuchElementException
      *   if the Iterable is empty
      * @throws AssertionError
      *   if the Iterable has more than one element
      */
    def loneElement(hint: String): A = {
      lazy val hintMaybe =
        if (hint.isEmpty) ""
        else s" Hint: $hint"

      val iter = iterable.iterator
      if (iter.isEmpty) {
        throw new NoSuchElementException(
          s"Iterable was expected to have exactly one element, but it is empty.$hintMaybe"
        )
      } else {
        val res = iter.next()
        if (iter.hasNext) {
          val collectionSizeHint = iterable.knownSize match {
            case -1 => "it has more than one" // cannot be computed cheaply, i.e. without traversing the collection
            case knownSize => s"it has $knownSize"
          }
          throw new AssertionError(
            s"Iterable was expected to have exactly one element, but $collectionSizeHint.$hintMaybe"
          )
        }
        res
      }
    }

    /** @return
      *   {{{Some(element)}}} if the Iterable has exactly one element, or {{{None}}} if the Iterable has zero or more
      *   than 1 element. Note: if the lone element is {{{null}}}, this will return {{{Some(null)}}}, which is in
      *   accordance with how {{{headOption}}} works.
      */
    def loneElementOption: Option[A] = {
      val iter = iterable.iterator
      if (iter.isEmpty) {
        None
      } else {
        val result = iter.next()
        if (iter.hasNext) {
          None
        } else {
          Some(result)
        }
      }
    }
  }

  implicit class IterableOnceDeco[T](val iterable: IterableOnce[T]) extends AnyVal {
    @deprecated(
      "please use `loneElement` instead, which has a better name and will throw if the iterable has more than one element (rather than just log.warn)",
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
