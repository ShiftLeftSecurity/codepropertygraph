package io.shiftleft.semanticcpg

import scala.collection.{IterableFactory, IterableOnceOps}

package object langv2 extends InstanceOfOpsImplicits {
  type Single[T] = T

  trait TravTypes {
    type Collection[_]
    type CCOneToOne[_]
    type CCOneToOption[_]
    type CCOneToBoolean[_]
    type CCOneToMany[_]
  }

  object SingleTravTypes extends TravTypes {
    override type Collection[T] = T
    override type CCOneToOne[T] = T
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  object OptionTravTypes extends TravTypes {
    override type Collection[T] = Option[T]
    override type CCOneToOne[T] = Option[T]
    override type CCOneToOption[T] = Option[T]
    override type CCOneToBoolean[T] = Option[T]
    override type CCOneToMany[T] = Seq[T]
  }

  class IterableOnceOpsTravTypes[CC[_], C] extends TravTypes {
    override type Collection[T] = IterableOnceOps[T, CC, C]
    override type CCOneToOne[T] = CC[T]
    override type CCOneToOption[T] = CC[T]
    override type CCOneToBoolean[T] = C
    override type CCOneToMany[T] = CC[T]
  }

  type SupportedTypes[T] = Option[T]
  type TravTypesFor[T[_]] = OptionTravTypes.type

  implicit val singleOps = SingleOps
  implicit val optionOps = OptionOps
  private val it2Ops = new IterableOnceOpsOps()
  implicit def toIt2Ops[CC[_], C] = it2Ops.asInstanceOf[IterableOnceOpsOps[CC, C]]


  implicit def toAnyTraversalNew1[I, T[_] <: SupportedTypes[_]](trav: TravTypesFor[T]#Collection[I]) = {
    new AnyTraversal[I, TravTypesFor[T]](trav)
  }
  implicit def toAnyTraversalNew2[I, TT <: TravTypes](trav: TT#Collection[I]) = {
    new AnyTraversal[I, TT](trav)
  }
  implicit def toAnyTraversalNew3[I, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
    new AnyTraversal[I, IterableOnceOpsTravTypes[CC, C]](trav)
  }

}
