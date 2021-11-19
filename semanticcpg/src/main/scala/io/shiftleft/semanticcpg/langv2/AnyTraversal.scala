package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatStepIterator

import scala.collection.IterableOnceOps

trait AnyTraversalImplicits {
  implicit def toAnyTraversalGeneric[I, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new AnyTraversal[I, TravTypesFor[T]](trav)
  }
  implicit def toAnyTraversalIterOnceOps[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AnyTraversal[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }
  implicit def toAnyTraversalInternal[I, TT <: TravTypes](trav: TT#Collection[I]) = {
    new AnyTraversal[I, TT](trav)
  }
}

class AnyTraversal[I, TT <: TravTypes](val trav: TT#Collection[I]) extends AnyVal {

  def cast[A]: TT#CCOneToOne[A] = {
    trav.asInstanceOf[TT#CCOneToOne[A]]
  }

  def map[O](f: I => O)(implicit ops: TravOps[TT]): TT#CCOneToOne[O] = {
    ops.oneToOne(trav)(f)
  }

  def filter(f: I => Boolean)(implicit ops: TravOps[TT]): TT#CCOneToBoolean[I] = {
    ops.oneToBoolean(trav)(f)
  }

  def flatMap[O](f: I => Iterator[O])(implicit ops: TravOps[TT]): TT#CCOneToMany[O] = {
    ops.oneToMany(trav)(f)
  }

  // We dont use ClassTag and instead use our own IsInstanceOfOps for performance reasons.
  def collectAll[T](implicit ops: TravOps[TT], isInstanceOfOps: IsInstanceOfOps[T]): TT#CCOneToBoolean[T] = {
    ops.oneToBoolean(trav)(isInstanceOfOps).asInstanceOf[TT#CCOneToBoolean[T]]
  }

  def rMap[O <: I](f: I => O, g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[TT]): TT#CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f.andThen(Iterator.single), behaviour))
  }

  def rFlatMap[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[TT]): TT#CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f, behaviour))
  }

  @deprecated("Use rFlatMap instead")
  def repeat[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[TT]): TT#CCOneToMany[I] = {
    rFlatMap(f, g)
  }

  @deprecated("Calls to this function can be omitted")
  def l: TT#Collection[I] = {
    trav
  }

}
