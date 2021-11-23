package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatStepIterator

import scala.collection.IterableOnceOps

trait AnyTraversalImplicits {
  implicit def toAnyTraversalIterOnceOps[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AnyTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterMarker[CC, C]](trav)
  }
  implicit def toAnyTraversalInternal[I, IT[_], Marker](trav: IT[I]) = {
    new AnyTraversal[I, IT, Marker](trav)
  }
}

class AnyTraversal[I, IT[_], Marker](val trav: IT[I]) extends AnyVal {

  def cast[A](implicit ops: TravOps[IT, Marker]): ops.CCOneToOne[A] = {
    trav.asInstanceOf[ops.CCOneToOne[A]]
  }

  def map[O](f: I => O)(implicit ops: TravOps[IT, Marker]): ops.CCOneToOne[O] = {
    ops.oneToOne(trav)(f)
  }

  def filter(f: I => Boolean)(implicit ops: TravOps[IT, Marker]): ops.CCOneToBoolean[I] = {
    ops.oneToBoolean(trav)(f)
  }

  def flatMap[O](f: I => Iterator[O])(implicit ops: TravOps[IT, Marker]): ops.CCOneToMany[O] = {
    ops.oneToMany(trav)(f)
  }

  // We dont use ClassTag and instead use our own IsInstanceOfOps for performance reasons.
  def collectAll[T](implicit ops: TravOps[IT, Marker], isInstanceOfOps: IsInstanceOfOps[T]): ops.CCOneToBoolean[T] = {
    ops.oneToBoolean(trav)(isInstanceOfOps).asInstanceOf[ops.CCOneToBoolean[T]]
  }

  def rMap[O <: I](f: I => O, g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, Marker]): ops.CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f.andThen(Iterator.single), behaviour))
  }

  def rFlatMap[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, Marker]): ops.CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f, behaviour))
  }

  @deprecated("Use rFlatMap instead")
  def repeat[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, Marker]): ops.CCOneToMany[I] = {
    rFlatMap(f, g)
  }

  @deprecated("Calls to this function can be omitted")
  def l: IT[I] = {
    trav
  }

}
