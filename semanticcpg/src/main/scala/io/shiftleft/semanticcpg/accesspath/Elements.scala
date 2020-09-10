package io.shiftleft.semanticcpg.accesspath

/**
  * For handling of invertible elements, cf AccessPathAlgebra.md.
  * The general rule is that elements concatenate normally, except for:
  *
  *
  *   Elements(&) ++ Elements(*) == Elements()
  *   Elements(*) ++ Elements(&) == Elements()
  *   Elements(<0>) == Elements()
  *   Elements(<i>) ++ Elements(<j>) == Elements(<i+j>)
  *   Elements(<?>) ++ Elements(<j>) == Elements(<?>)
  *   Elements(<i>) ++ Elements(<?>) == Elements(<?>)
  *   Elements(<?>) ++ Elements(<?>) == Elements(<?>)
  *
  *   From this, once can see that <i>, * and & are invertible, <?> is idempotent and <0> is a convoluted way of describing
  *   and empty sequence of tokens. Nevertheless, we mostly consider * as noninvertible (because it is, in real computers!)
  *   and <?> as invertible (because it is in real computers, we just don't know the offset)
  *
  *   Elements get a private constructor. Users should use the no-argument Elements.apply() factory method to get an
  *   empty path, and the specific concat operators for building up pathes. The Elements.normalized(iter) factory method
  *   serves to build this in bulk.
  *
  *   The unnormalized factory method is more of an escape hatch.
  *
  *   The elements field should never be mutated outside of this file: We compare and hash Elements by their contents, not by
  *   identity, and this breaks in case of mutation.
  *
  *   The reason for using a mutable Array instead of an immutable Vector is that this is the lightest weight datastructure for
  *   the job.
  *
  *   The reason for making this non-private is simply that it is truly annoying to write wrappers for all possible uses.
  */
// TODO: Figure out sensible assertions to defend invariant that the empty instance is the only empty elements instance
// i.e. assert that elems.isEmpty implies elems eq Elements.empty

object Elements {
  val empty = new Elements()
  def normalized(elems: IterableOnce[AccessElement]): Elements =
    destructiveNormalized(elems.iterator.toArray)
  def normalized(elems: AccessElement*): Elements =
    destructiveNormalized(elems.toArray)

  private def destructiveNormalized(elems: Array[AccessElement]): Elements = {
    var idxRight = 0
    var idxLeft = -1
    while (idxRight < elems.length) {
      val nextE = elems(idxRight)
      nextE match {
        case shift: PointerShift if shift.logicalOffset == 0 =>
        //nothing to do
        case _ =>
          if (idxLeft == -1) {
            idxLeft = 0
            elems(0) = nextE
          } else {
            val lastE = elems(idxLeft)
            (lastE, nextE) match {
              case (last: PointerShift, next: PointerShift) =>
                val newShift = last.logicalOffset + next.logicalOffset
                if (newShift != 0) elems(idxLeft) = PointerShift(newShift)
                else idxLeft -= 1
              case (VariablePointerShift, _: PointerShift) | (VariablePointerShift, VariablePointerShift) =>
              case (_: PointerShift, VariablePointerShift) =>
                elems(idxLeft) = VariablePointerShift
              case (AddressOf, IndirectionAccess) =>
                idxLeft -= 1
              case (IndirectionAccess, AddressOf) =>
                idxLeft -= 1 // WRONG but useful, cf comment for `Elements.:+`
              case _ =>
                idxLeft += 1
                elems(idxLeft) = nextE
            }
          }
      }
      idxRight += 1
    }
    newIfNonEmpty(elems.take(idxLeft + 1))
  }

  def unnormalized(elems: IterableOnce[AccessElement]): Elements =
    newIfNonEmpty(elems.iterator.toArray)

  def newIfNonEmpty(elems: Array[AccessElement]): Elements = {
    if (!elems.isEmpty) new Elements(elems)
    else empty
  }

  def inverted(elems: Iterable[AccessElement]): Elements = {
    newIfNonEmpty(elems.toArray.reverse.map {
      case AddressOf            => IndirectionAccess
      case IndirectionAccess    => AddressOf
      case PointerShift(idx)    => PointerShift(-idx)
      case VariablePointerShift => VariablePointerShift
      case _                    => throw new RuntimeException(s"Cannot invert ${Elements.unnormalized(elems)}")
    })
  }

  def noOvertaint(elems: Iterable[AccessElement]): Boolean =
    elems.forall(_ != VariableAccess)

  def apply(): Elements =
    empty
}

final class Elements(val elements: Array[AccessElement] = Array[AccessElement]()) {

  def noOvertaint(start: Int = 0, untilExclusive: Int = elements.length): Boolean = {
    elements
      .slice(start, untilExclusive)
      .find {
        case (VariablePointerShift | VariableAccess) => true
        case _                                       => false
      }
      .forall(_ => false)
  }

  def invertibleTailLength: Int = {

    /**
      * In all sane situations, invertibleTailLength is 0 or 1:
      *   - we don't expect <i> &, because you cannot take the address of pointer+i (can only take address of rvalue)
      *   - we don't expect & <i>: The & should have collapsed against a preceding *.
      * An example where this occurs is (&(ptr->field))[1], which becomes ptr: * field & <2> *
      * This reads the _next_ field: It doesn't alias with ptr->field at all, but reads the next bytes in the struct,
      * after field.
      *
      * Such code is very un-idiomatic.
      */
    var i = 0
    val nElements = elements.length - 1
    while (nElements - i > -1) {
      elements(nElements - i) match {
        case (AddressOf | VariablePointerShift | _: PointerShift) => i += 1
        case _                                                    => return i
      }
    }
    i
  }

  def canEqual(that: Any): Boolean = that.isInstanceOf[Elements]

  override def equals(that: Any): Boolean = {
    that match {
      case thatElements: Elements =>
        Array.equals(elements.asInstanceOf[Array[AnyRef]], thatElements.elements.asInstanceOf[Array[AnyRef]])
      case _ => false
    }
  }

  override def hashCode(): Int = java.util.Arrays.hashCode(elements.asInstanceOf[Array[AnyRef]])

  def ++(otherElements: Elements): Elements = {

    if (elements.isEmpty) return otherElements
    if (otherElements.isEmpty) return this

    var buf = None: Option[AccessElement]
    val otherSize = otherElements.elements.length
    var idx = 0
    val until = scala.math.min(elements.length, otherSize)
    var done = false

    while (idx < until & !done) {
      (elements(elements.length - idx - 1), otherElements.elements(idx)) match {
        case (AddressOf, IndirectionAccess) =>
          idx += 1
        case (IndirectionAccess, AddressOf) =>
          // WRONG but useful, cf comment for `Elements.:+`
          idx += 1
        case (VariablePointerShift, VariablePointerShift) | (_: PointerShift, VariablePointerShift) |
            (VariablePointerShift, _: PointerShift) =>
          done = true
          buf = Some(VariablePointerShift)
          idx += 1
        case (last: PointerShift, first: PointerShift) =>
          val newOffset = last.logicalOffset + first.logicalOffset
          if (newOffset != 0) {
            done = true
            buf = Some(PointerShift(newOffset))
          }
          idx += 1
        case _ =>
          done = true
      }
    }
    val sz = elements.length + otherSize - 2 * idx + (if (buf.isDefined) 1 else 0)
    val res = Array.fill(sz) { null }: Array[AccessElement]
    elements.copyToArray(res, 0, elements.length - idx)
    if (buf.isDefined) {
      res(elements.length - idx) = buf.get
      java.lang.System.arraycopy(otherElements.elements, idx, res, elements.length - idx + 1, otherSize - idx)
    } else {
      java.lang.System.arraycopy(otherElements.elements, idx, res, elements.length - idx, otherSize - idx)
    }
    Elements.newIfNonEmpty(res)
  }

  def isEmpty: Boolean = elements.isEmpty

  override def toString: String = s"Elements(${elements.mkString(",")})"

}
