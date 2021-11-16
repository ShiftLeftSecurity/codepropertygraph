package io.shiftleft.semanticcpg

package object langv2 {
  type Trav1[T] = T
  type TravNOps[T[_]] = TravOps[T, T]

  implicit val trav1Ops = Trav1Ops
  implicit def iterableOps[IT[_] <: Iterable[_]] = IterableOps.asInstanceOf[TravOps[IT, Iterable]]

  implicit def toAnyTraversal[I, IT[_], FT[_]](p: IT[I]): AnyTraversal[I, IT, FT] = {
    new AnyTraversal(p)
  }

}
object foo {
  implicit class MethoddParameterSingleExt(val node: Int) extends AnyVal {
    def parameterB: Iterator[Int] = Iterator.single(2)
  }
}
