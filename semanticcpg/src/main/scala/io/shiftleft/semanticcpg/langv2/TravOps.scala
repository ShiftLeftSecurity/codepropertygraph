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
trait TravOps[_Collection[_], Marker] {
  type Collection[T] = _Collection[T]
  type CCOneToOne[_]
  type CCOneToOption[_]
  type CCOneToBoolean[_]
  type CCOneToMany[_]

  def oneToOne[I, O](trav: Collection[I])(f: I => O): CCOneToOne[O]
  def oneToOption[I, O](trav: Collection[I])(f: I => Option[O]): CCOneToOption[O]
  def oneToBoolean[I](trav: Collection[I])(f: I => Boolean): CCOneToBoolean[I]
  def oneToMany[I, O](trav: Collection[I])(f: I => Iterator[O]): CCOneToMany[O]
  def cast[T](trav: Collection[_]): CCOneToOne[T] = trav.asInstanceOf[CCOneToOne[T]]
}

object SingleOps extends TravOps[Single, SingleMarker] {
  override type CCOneToOne[T] = T
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]

  override def oneToOne[I, O](trav: this.Collection[I])(f: I => O): CCOneToOne[O] = {
    f(trav)
  }

  override def oneToOption[I, O](trav: this.Collection[I])(f: I => Option[O]): CCOneToOption[O] = {
    f(trav)
  }

  override def oneToBoolean[I](trav: this.Collection[I])(f: I => Boolean): CCOneToBoolean[I] = {
    if (f(trav)) {
      Some(trav)
    } else {
      None
    }
  }

  override def oneToMany[I, O](trav: this.Collection[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    Seq.from(f(trav))
  }
}

object OptionOps extends TravOps[Option, OptionMarker] {
  override type CCOneToOne[T] = Option[T]
  override type CCOneToOption[T] = Option[T]
  override type CCOneToBoolean[T] = Option[T]
  override type CCOneToMany[T] = Seq[T]

  override def oneToOne[I, O](trav: this.Collection[I])(f: I => O): CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.Collection[I])(f: I => Option[O]): CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.Collection[I])(f: I => Boolean): CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.Collection[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    trav match {
      case Some(t) =>
        Seq.from(f(t))
      case None =>
        Seq.empty
    }
  }
}

class IterableOnceOpsOps[CC[_], C] extends TravOps[({type X[A] = IterableOnceOps[A, CC, C]})#X, IterMarker[CC, C]] {
  override type CCOneToOne[T] = CC[T]
  override type CCOneToOption[T] = CC[T]
  override type CCOneToBoolean[T] = C
  override type CCOneToMany[T] = CC[T]

  override def oneToOne[I, O](trav: this.Collection[I])(f: I => O): CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.Collection[I])(f: I => Option[O]): CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.Collection[I])(f: I => Boolean): CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.Collection[I])(f: I => Iterator[O]): CCOneToMany[O] = {
    trav.flatMap(f)
  }
}
