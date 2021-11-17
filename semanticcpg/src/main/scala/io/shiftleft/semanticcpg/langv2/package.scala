package io.shiftleft.semanticcpg

package object langv2 {
  type Trav1[T] = T

  implicit val trav1Ops = Trav1Ops
  implicit def iterableOps[IT[_] <: Iterable[_]] = IterableOps.asInstanceOf[TravOps[IT, IT]]

  implicit def toAnyTraversal[I, IT[_], FT[_]](trav: IT[I]): AnyTraversal[I, IT, FT] = {
    new AnyTraversal(trav)
  }

}
