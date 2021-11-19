package io.shiftleft.semanticcpg

import scala.collection.{IterableFactory, IterableOnceOps}

package object langv2 extends InstanceOfOpsImplicits {
  type Trav1[T] = T

  trait TypeMultiplexer {
    type IT[_]
    type CCOneToOne[_]
    type CCOneToOption[_]
    type CCOneToBoolean[_]
    type CCOneToMany[_]
  }

  object SingleTypes extends TypeMultiplexer {
    override type IT[T] = T
    override type CCOneToOne[T] = T
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  object OptionTypes extends TypeMultiplexer {
    override type IT[T] = Option[T]
    override type CCOneToOne[T] = Option[T]
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  class IterableTypes[CC[_], C] extends TypeMultiplexer {
    override type IT[T] = IterableOnceOps[T, CC, C]
    override type CCOneToOne[T] = CC[T]
    override type CCOneToOption[T] = CC[T]
    override type CCOneToBoolean[T] = C
    override type CCOneToMany[T] = CC[T]
  }

  type TypeBound[T] = Option[T]
  type TypesClassFor[IT[_]] = OptionTypes.type

  implicit val trav1Ops = Trav1Ops
  implicit val optionOps = OptionOps
  private val it2Ops = new IterableOnceOpsOps()
  implicit def toIt2Ops[CC[_], C] = it2Ops.asInstanceOf[IterableOnceOpsOps[CC, C]]


  implicit def toAnyTraversalNew1[I, IT[_] <: TypeBound[_]](trav: TypesClassFor[IT]#IT[I]) = {
    new AnyTraversal[I, TypesClassFor[IT]](trav)
  }
  implicit def toAnyTraversalNew2[I, TM <: TypeMultiplexer](trav: TM#IT[I]) = {
    new AnyTraversal[I, TM](trav)
  }
  implicit def toAnyTraversalNew3[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AnyTraversal[I, IterableTypes[CC, C]](trav)
  }

}
