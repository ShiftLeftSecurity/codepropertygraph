package io.shiftleft.semanticcpg

import scala.collection.{IterableFactory, IterableOnceOps}

package object langv2 extends InstanceOfOpsImplicits {
  type Trav1[T] = T

  implicit val trav1Ops = Trav1Ops


  private val it2Ops = new IterableOps()
  implicit def toIt2Ops[Col[A] <: Iterable[A]] = it2Ops.asInstanceOf[IterableOps[Col]]

  private val iterableOnceOpsOps = new IterableOnceOpsOps()
  //implicit def toIterOnceOps[CC[_], C]: TravOps[({type X[+B] = IterableOnceOps[B, CC, C]})#X] = {
  //  iterableOnceOpsOps.asInstanceOf[TravOps[({type X[+B] = IterableOnceOps[B, CC, C]})#X]]
  //}

  // This works if u provide CC and C eplicitly as [Iterable, Iterable[Any]]
  implicit def toIterOnceOps[CC[_], C]: IterableOnceOpsOps[CC, C] = {
    iterableOnceOpsOps.asInstanceOf[IterableOnceOpsOps[CC, C]]
  }

  // Iterable[A] = IterableOnceOps[A, Iterable, Iterable[A]]
  // io.shiftleft.semanticcpg.langv2.IterableOnceOpsOps[[+A]Iterable[A],Iterable[Any]]



  // implicit val iterOps = new IterableOnceOpsOps(Iterable.empty[Any])

  implicit def toAnyTraversal[I, IT[_]](trav: IT[I]): AnyTraversal[I, IT] = {
    new AnyTraversal(trav)
  }

}
