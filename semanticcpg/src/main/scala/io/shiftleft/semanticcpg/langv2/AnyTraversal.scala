package io.shiftleft.semanticcpg.langv2

import overflowdb.traversal.RepeatStepIterator

import scala.collection.IterableOnceOps

trait AnyTraversalImplicits {
  implicit def toAnyTraversalIter[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AnyTraversal[I, ({type X[A] = IterableOnceOps[A, CC, C]})#X, IterTypes[CC, C]](trav)
  }
  implicit def toAnyTraversalInternal[I, IT[_], Marker](trav: IT[I]) = {
    new AnyTraversal[I, IT, Marker](trav)
  }
}

class AnyTraversal[I, IT[_], Marker](val trav: IT[I]) extends AnyVal {

  def cast[A](implicit applier: ToOne[IT, Marker]): applier.OUT[A] = {
    trav.asInstanceOf[applier.OUT[A]]
  }

  // We dont use ClassTag and instead use our own IsInstanceOfOps for performance reasons.
  def collectAll[T](implicit applier: ToBoolean[IT, Marker], isInstanceOfOps: IsInstanceOfOps[T]): applier.OUT[T] = {
    applier.apply(trav)(isInstanceOfOps).asInstanceOf[applier.OUT[T]]
  }

  def rMap[O <: I](f: I => O, g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
    implicit applier: ToMany[IT, Marker]): applier.OUT[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    applier.apply(trav)(i => new RepeatStepIterator(i, f.andThen(Iterator.single), behaviour))
  }

  def rFlatMap[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
    implicit applier: ToMany[IT, Marker]): applier.OUT[I] = {
    val behaviour = g(new RepeatBehaviourBuilder()).build
    applier.apply(trav)(i => new RepeatStepIterator(i, f, behaviour))
  }

  //@deprecated("Use rFlatMap instead")
  def repeat[O <: I](f: I => Iterator[O], g: RepeatBehaviourBuilder[I] => RepeatBehaviourBuilder[I] = identity)(
    implicit applier: ToMany[IT, Marker]): applier.OUT[I] = {
    rFlatMap(f, g)
  }

  @deprecated("Calls to this function can be omitted")
  def l: IT[I] = {
    trav
  }

}
