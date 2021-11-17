package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatStepIterator

import scala.reflect.ClassTag

class AnyTraversal[I, IT[_], FT[_]](val trav: IT[I]) extends AnyVal {
  def head(implicit ops: TravOps[IT, FT]): I = {
    ops.head(trav)
  }

  def headOption(implicit ops: TravOps[IT, FT]): Option[I] = {
    ops.headOption(trav)
  }

  def map[O](f: I => O)(implicit ops: TravOps[IT, FT]): IT[O] = {
    ops.map(trav)(f)
  }

  def filter(f: I => Boolean)(implicit ops: TravOps[IT, FT]): FT[I] = {
    ops.filter(trav)(f)
  }

  def flatMap[O](f: I => Iterator[O])(implicit ops: TravOps[IT, FT]): FT[O] = {
    ops.flatMap(trav)(f)
  }

  def cast[A]: IT[A] = {
    trav.asInstanceOf[IT[A]]
  }

  // We dont use ClassTag and instead use our own IsInstanceOfOps for performance reasons.
  def collectAll[T](implicit ops: TravOps[IT, FT], isInstanceOfOps: IsInstanceOfOps[T]): FT[T] = {
    ops.filter(trav)(isInstanceOfOps).cast[T]
  }

  def rMap[O <: I](f: I => O, g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, FT]): FT[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.flatMap(trav)(i => new RepeatStepIterator(i, f.andThen(Iterator.single), behaviour))
  }

  def rFlatMap[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, FT]): FT[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    ops.flatMap(trav)(i => new RepeatStepIterator(i, f, behaviour))
  }

  @deprecated("Use rFlatMap instead")
  def repeat[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
      implicit ops: TravOps[IT, FT]): FT[I] = {
    rFlatMap(f, g)
  }

  @deprecated("Calls to this function can be omitted")
  def l: IT[I] = {
    trav
  }

}
