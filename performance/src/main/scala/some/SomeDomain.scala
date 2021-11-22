package some

import io.shiftleft.semanticcpg.langv2._
import some.SomeDomain.D1

import scala.collection.IterableOnceOps


object SomeDomain {
    implicit def toSynthSingle[I <: D1](p: I) = {
      new SynthExt[SingleTravTypes](p: Single[I])
    }

    implicit def toSynthOption[I <: D1](trav: Option[I]) = {
      new SynthExt[OptionTravTypes](trav)
    }

    implicit def toSynthIter[I <: D1, CC[_], C](trav: IterableOnceOps[I, CC, C]) = {
      new SynthExt[IterableOnceOpsTravTypes[CC, C]](trav)
    }

  class SynthExt[TT <: TravTypes](val trav: TT#Collection[D1]) extends AnyVal {
    def toD2(implicit ops: TravOps[TT]) = {
      trav.map(_.x)
    }
    def toD2Multi(implicit ops: TravOps[TT]) = {
      trav.flatMap(Iterator.single)
    }
  }

  case class D1(x: D2)

  case class D2()
}
/*
object SomeDomain2 {
  trait SyncExtImplicits {
    implicit def toSynthExtSingle(p: D1) = {
      new SynthExt[Single, Single, Option, Option, Seq](p: Single[D1])
    }

    implicit def toSynthExtOption[T[_]](trav: Option[D1]) = {
      new SynthExt[Option, Option, Option, Option, Seq](trav)
    }

    implicit def toSynthExtIter[CC[_], C](trav: IterableOnceOps[D1, CC, C])
    : SynthExt[({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC] = {
      new SynthExt[({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
    }
  }

  class SynthExt[Collection[_], CCOneToOne[_], CCOneToOption[_], CCOneToBoolean[_], CCOneToMany[_]]
  (val trav: Collection[D1]) extends AnyVal {
    def toD2(implicit ops: TravOpsToOne[Collection]) = {
      trav.map(_.x)
    }

    def toD2Multi(implicit ops: TravOpsToOne[Collection]) = {
      trav.flatMap(Iterator.single)
    }
  }
}
 */