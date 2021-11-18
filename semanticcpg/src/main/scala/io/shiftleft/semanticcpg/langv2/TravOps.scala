package io.shiftleft.semanticcpg.langv2

import scala.collection.IterableOnceOps

/** Traversal operations trait.
  * Implementations of this trait handle the basic traversal operations for the
  * different kind of input traversal types.
 *
  * @tparam InputTraversal Input traversal type. Abbreviated in other generics as IT.
  * @tparam FlatMapTraversal Flat map operation output traversal type.
  *                          Abbreviated in other generics as FT.
  */
trait TravOps[InputTraversal[_]] {
  type CCOneToOne[_]
  type CCOneToOption[_]
  type CCOneToBoolean[_]
  type CCOneToMany[_]

  def oneToOne[I, O](trav: InputTraversal[I])(f: I => O): CCOneToOne[O]
  def oneToOption[I, O](trav: InputTraversal[I])(f: I => Option[O]): CCOneToOption[O]
  def oneToBoolean[I](trav: InputTraversal[I])(f: I => Boolean): CCOneToBoolean[I]
  def oneToMany[I, O](trav: InputTraversal[I])(f: I => Iterator[O]): CCOneToMany[O]
}

object Trav1Ops extends TravOps[Trav1] {
  override type CCOneToOne[T] = T
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]

  override def oneToOne[I, O](trav: Trav1[I])(f: I => O): CCOneToOne[O] = {
    f(trav)
  }

  override def oneToOption[I, O](trav: Trav1[I])(f: I => Option[O]): CCOneToOption[O] = {
    f(trav)
  }

  override def oneToBoolean[I](trav: Trav1[I])(f: I => Boolean): CCOneToBoolean[I] = {
    if (f(trav)) {
      Some(trav)
    } else {
      None
    }
  }

  override def oneToMany[I, O](trav: Trav1[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    Seq.from(f(trav))
  }
}

object OptionOps extends TravOps[Option] {
  override type CCOneToOne[T] = Option[T]
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]

  override def oneToOne[I, O](trav: Option[I])(f: I => O): CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: Option[I])(f: I => Option[O]): CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: Option[I])(f: I => Boolean): CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: Option[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    trav match {
      case Some(t) =>
        Seq.from(f(t))
      case None =>
        Seq.empty
    }
  }
}

class IterableOps[Col[A] <: Iterable[A] ] extends TravOps[Col] {
  override type CCOneToOne[T] = Col[T]
  override type CCOneToOption[T] = Col[T]
  override type CCOneToBoolean[T] = Col[T]
  override type CCOneToMany[T] = Col[T]

  override def oneToOne[I, O](trav: Col[I])(f: I => O): CCOneToOne[O] = {
    trav.map(f).asInstanceOf[Col[O]]
  }

  override def oneToOption[I, O](trav: Col[I])(f: I => Option[O]): CCOneToOption[O] = {
    trav.flatMap(f).asInstanceOf[Col[O]]
  }

  override def oneToBoolean[I](trav: Col[I])(f: I => Boolean): CCOneToBoolean[I] = {
    trav.filter(f).asInstanceOf[Col[I]]
  }

  override def oneToMany[I, O](trav: Col[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    trav.flatMap(f).asInstanceOf[Col[O]]
  }
}

//class IterableOnceOpsOps[CC[_], C](unused: IterableOnceOps[_, CC, C]) extends TravOps[({type X[B] = IterableOnceOps[B, CC, C]})#X] {
class IterableOnceOpsOps[CC[_], C] extends TravOps[({type X[B] = IterableOnceOps[B, CC, C]})#X] {
  override type CCOneToOne[T] = CC[T]
  override type CCOneToOption[T] = CC[T]
  override type CCOneToBoolean[T] = C
  override type CCOneToMany[T] = CC[T]

  override def oneToOne[I, O](trav: IterableOnceOps[I, CC, C])(f: I => O): CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: IterableOnceOps[I, CC, C])(f: I => Option[O]): CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: IterableOnceOps[I, CC, C])(f: I => Boolean): CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: IterableOnceOps[I, CC, C])(f: I => Iterator[O]): CCOneToMany[O] = {
    trav.flatMap(f)
  }
}
