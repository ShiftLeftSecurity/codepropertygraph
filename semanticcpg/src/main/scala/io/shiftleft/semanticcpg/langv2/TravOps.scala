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
trait TravOps[_TT <: TravTypes] {
  type TT = _TT
  def oneToOne[I, O](trav: TT#Collection[I])(f: I => O): TT#CCOneToOne[O]
  def oneToOption[I, O](trav: TT#Collection[I])(f: I => Option[O]): TT#CCOneToOption[O]
  def oneToBoolean[I](trav: TT#Collection[I])(f: I => Boolean): TT#CCOneToBoolean[I]
  def oneToMany[I, O](trav: TT#Collection[I])(f: I => Iterator[O]): TT#CCOneToMany[O]
}

object SingleOps extends TravOps[SingleTravTypes.type] {

  override def oneToOne[I, O](trav: this.type#TT#Collection[I])(f: I => O): this.type#TT#CCOneToOne[O] = {
    f(trav)
  }

  override def oneToOption[I, O](trav: this.type#TT#Collection[I])(f: I => Option[O]): this.type#TT#CCOneToOption[O] = {
    f(trav)
  }

  override def oneToBoolean[I](trav: this.type#TT#Collection[I])(f: I => Boolean): this.type#TT#CCOneToBoolean[I] = {
    if (f(trav)) {
      Some(trav)
    } else {
      None
    }
  }

  override def oneToMany[I, O](trav: this.type#TT#Collection[I])(f: I => Iterator[O]): this.type#TT#CCOneToMany[O] = {
    Seq.from(f(trav))
  }
}

object OptionOps extends TravOps[OptionTravTypes.type] {

  override def oneToOne[I, O](trav: this.type#TT#Collection[I])(f: I => O): this.type#TT#CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.type#TT#Collection[I])(f: I => Option[O]): this.type#TT#CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.type#TT#Collection[I])(f: I => Boolean): this.type#TT#CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.type#TT#Collection[I])(f: I => Iterator[O]): this.type#TT#CCOneToMany[O] = {
    trav match {
      case Some(t) =>
        Seq.from(f(t))
      case None =>
        Seq.empty
    }
  }
}

class IterableOnceOpsOps[CC[_], C] extends TravOps[IterableOnceOpsTravTypes[CC, C] ] {

  override def oneToOne[I, O](trav: this.type#TT#Collection[I])(f: I => O): this.type#TT#CCOneToOne[O] = {
    trav.map(f)
  }

  override def oneToOption[I, O](trav: this.type#TT#Collection[I])(f: I => Option[O]): this.type#TT#CCOneToOption[O] = {
    trav.flatMap(f)
  }

  override def oneToBoolean[I](trav: this.type#TT#Collection[I])(f: I => Boolean): this.type#TT#CCOneToBoolean[I] = {
    trav.filter(f)
  }

  override def oneToMany[I, O](trav: this.type#TT#Collection[I])(f: I => Iterator[O]): this.type#TT#CCOneToMany[O] = {
    trav.flatMap(f)
  }
}
