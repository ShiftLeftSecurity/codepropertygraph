package io.shiftleft.semanticcpg

package object langv2 extends InstanceOfOpsImplicits {
  type Trav1[T] = T

  implicit val trav1Ops = Trav1Ops
  // All Iterable implementations coming from the scala standard library return an Iterable of
  // the same runtime type as the input iterable as results of map, flatMap, filter etc..
  // We expect this to be true for all Iterabel implementations using iterableOps and thus cast
  // to TravOps[IT, IT].
  implicit def iterableOps[IT[_] <: Iterable[_]] = IterableOps.asInstanceOf[TravOps[IT, IT]]
  implicit def iteratorOps[IT[_] <: Iterator[_]] = IteratorOps.asInstanceOf[TravOps[IT, Iterator]]

  implicit def toAnyTraversal[I, IT[_], FT[_]](trav: IT[I]): AnyTraversal[I, IT, FT] = {
    new AnyTraversal(trav)
  }

}
