package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatStepIterator


class AnyTraversal[I, IT[_], TM <: TypeMultiplexer](val trav: IT[I]) extends AnyVal {

  def cast[A]: TM#CCOneToOne[A] = {
    trav.asInstanceOf[TM#CCOneToOne[A]]
  }

  // We dont use ClassTag and instead use our own IsInstanceOfOps for performance reasons.
  def collectAll[T](implicit ops: TravOps[IT, TM], isInstanceOfOps: IsInstanceOfOps[T]): TM#CCOneToBoolean[T] = {
    ops.oneToBoolean(trav)(isInstanceOfOps).asInstanceOf[TM#CCOneToBoolean[T]]
  }

  def rMap[O <: I](f: I => O, g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, TM]): TM#CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f.andThen(Iterator.single), behaviour))
  }

  def rFlatMap[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, TM]): TM#CCOneToMany[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.oneToMany(trav)(i => new RepeatStepIterator(i, f, behaviour))
  }

  @deprecated("Use rFlatMap instead")
  def repeat[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, TM]): TM#CCOneToMany[I] = {
    rFlatMap(f, g)
  }

  @deprecated("Calls to this function can be omitted")
  def l: IT[I] = {
    trav
  }

}
