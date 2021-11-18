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
trait TravOps[TM <: TypeMultiplexer] {
  type tm = TM
  def oneToOne[I, O](trav: TM#IT[I])(f: I => O): TM#CCOneToOne[O]
  def oneToOption[I, O](trav: TM#IT[I])(f: I => Option[O]): TM#CCOneToOption[O]
  def oneToBoolean[I](trav: TM#IT[I])(f: I => Boolean): TM#CCOneToBoolean[I]
  def oneToMany[I, O](trav: TM#IT[I])(f: I => Iterator[O]): TM#CCOneToMany[O]
}

object Trav1Ops extends TravOps[SingleTypes.type] {

  override def oneToOne[I, O](trav: this.type#tm#IT[I])(f: I => O): this.type#tm#CCOneToOne[O] = {
    f(trav)
  }

  override def oneToOption[I, O](trav: this.type#tm#IT[I])(f: I => Option[O]): this.type#tm#CCOneToOption[O] = {
    f(trav)
  }

  override def oneToBoolean[I](trav: this.type#tm#IT[I])(f: I => Boolean): this.type#tm#CCOneToBoolean[I] = {
    if (f(trav)) {
      Some(trav)
    } else {
      None
    }
  }

  override def oneToMany[I, O](trav: this.type#tm#IT[I])(f: I => Iterator[O]): this.type#tm#CCOneToMany[O] = {
    Seq.from(f(trav))
  }
}

object OptionOps extends TravOps[OptionTypes.type ] {

  override def oneToOne[I, O](trav: this.type#tm#IT[I])(f: I => O): this.type#tm#CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.type#tm#IT[I])(f: I => Option[O]): this.type#tm#CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.type#tm#IT[I])(f: I => Boolean): this.type#tm#CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.type#tm#IT[I])(f: I => Iterator[O]): this.type#tm#CCOneToMany[O] = {
    trav match {
      case Some(t) =>
        Seq.from(f(t))
      case None =>
        Seq.empty
    }
  }
}

//class IterableOps[Col[A] <: Iterable[A] ] extends TravOps[Col] {
//  override type CCOneToOne[T] = Col[T]
//  override type CCOneToOption[T] = Col[T]
//  override type CCOneToBoolean[T] = Col[T]
//  override type CCOneToMany[T] = Col[T]
//
//  override def oneToOne[I, O](trav: Col[I])(f: I => O): CCOneToOne[O] = {
//    trav.map(f).asInstanceOf[Col[O]]
//  }
//
//  override def oneToOption[I, O](trav: Col[I])(f: I => Option[O]): CCOneToOption[O] = {
//    trav.flatMap(f).asInstanceOf[Col[O]]
//  }
//
//  override def oneToBoolean[I](trav: Col[I])(f: I => Boolean): CCOneToBoolean[I] = {
//    trav.filter(f).asInstanceOf[Col[I]]
//  }
//
//  override def oneToMany[I, O](trav: Col[I])(f: I => Iterator[O]): CCOneToMany[O] = {
//    trav.flatMap(f).asInstanceOf[Col[O]]
//  }
//}

//class IterableOnceOpsOps[CC[_], C](unused: IterableOnceOps[_, CC, C]) extends TravOps[({type X[B] = IterableOnceOps[B, CC, C]})#X] {
class IterableOnceOpsOps[CC[_], C] extends TravOps[IterableTypes[CC, C] ] {

  override def oneToOne[I, O](trav: this.type#tm#IT[I])(f: I => O): this.type#tm#CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.type#tm#IT[I])(f: I => Option[O]): this.type#tm#CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.type#tm#IT[I])(f: I => Boolean): this.type#tm#CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.type#tm#IT[I])(f: I => Iterator[O]): this.type#tm#CCOneToMany[O] = {
    trav.flatMap(f)
  }
}
