package io.shiftleft.semanticcpg

import scala.collection.{IterableFactory, IterableOnceOps}

package object langv2 extends InstanceOfOpsImplicits {
  type Trav1[T] = T

  trait TypeMultiplexer {
    type CCOneToOne[_]
    type CCOneToOption[_]
    type CCOneToBoolean[_]
    type CCOneToMany[_]
  }

  object SingleTypes extends TypeMultiplexer {
    override type CCOneToOne[T] = T
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  object OptionTypes extends TypeMultiplexer {
    override type CCOneToOne[T] = Option[T]
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  class IterableTypes[CC[_], C] extends TypeMultiplexer {
    override type CCOneToOne[T] = CC[T]
    override type CCOneToOption[T] = CC[T]
    override type CCOneToBoolean[T] = C
    override type CCOneToMany[T] = CC[T]
  }


  implicit val trav1Ops = Trav1Ops


  private val it2Ops = new IterableOnceOpsOps()
  implicit def toIt2Ops[CC[_], C] = it2Ops.asInstanceOf[IterableOnceOpsOps[CC, C]]

  // Iterable[A] = IterableOnceOps[A, Iterable, Iterable[A]]
  // io.shiftleft.semanticcpg.langv2.IterableOnceOpsOps[[+A]Iterable[A],Iterable[Any]]



  // implicit val iterOps = new IterableOnceOpsOps(Iterable.empty[Any])

  implicit def toAnyTraversalNew1[I](trav: Option[I]) = {
    new AnyTraversal[I, Option, OptionTypes.type](trav)
  }
  implicit def toAnyTraversalNew2[I, IT[_], TM <: TypeMultiplexer](trav: IT[I]) = {
    new AnyTraversal[I, IT, TM](trav)
  }
  implicit def toAnyTraversalNew3[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    //new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
    new AnyTraversal[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, IterableTypes[CC, C]](trav)
  }

}
