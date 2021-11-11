package io.shiftleft.semanticcpg.langv2

/** Traversal operations trait.
  * Implementations of this trait handle the basic traversal operations for the
  * different kind of input traversal types.
  * @tparam InputTraversal Input traversal type. Abbreviated in other generics as IT.
  * @tparam FlatMapTraversal Flat map operation output traversal type.
  *                          Abbreviated in other generics as FT.
  */
trait TravOps[InputTraversal[_], FlatMapTraversal[_]] {
  def head[I](trav: InputTraversal[I]): I
  def headOption[I](trav: InputTraversal[I]): Option[I]
  def map[I, O](trav: InputTraversal[I])(f: I => O): InputTraversal[O]
  def filter[I](trav: InputTraversal[I])(f: I => Boolean): FlatMapTraversal[I]
  def flatMap[I, O](trav: InputTraversal[I])(f: I => Iterator[O]): FlatMapTraversal[O]
}

object Trav1Ops extends TravOps[Trav1, Iterable] {

  override def head[I](trav: Trav1[I]): I = {
    trav
  }

  override def headOption[I](trav: Trav1[I]): Option[I] = {
    Some(trav)
  }

  override def map[I, O](trav: Trav1[I])(f: I => O): Trav1[O] = {
    f(trav)
  }

  override def filter[I](trav: Trav1[I])(f: I => Boolean): Iterable[I] = {
    if (f(trav)) {
      trav :: Nil
    } else {
      Nil
    }
  }

  override def flatMap[I, O](trav: Trav1[I])(f: I => Iterator[O]): Iterable[O] = {
    Iterable.from(f(trav))
  }
}

object IterableOps extends TravOps[Iterable, Iterable] {

  override def head[I](trav: Iterable[I]): I = {
    trav.head
  }

  override def headOption[I](trav: Iterable[I]): Option[I] = {
    trav.headOption
  }

  override def map[I, O](trav: Iterable[I])(f: I => O): Iterable[O] = {
    trav.map(f)
  }

  override def filter[I](trav: Iterable[I])(f: I => Boolean): Iterable[I] = {
    trav.filter(f)
  }

  override def flatMap[I, O](trav: Iterable[I])(f: I => Iterator[O]): Iterable[O] = {
    trav.flatMap(f)
  }
}
