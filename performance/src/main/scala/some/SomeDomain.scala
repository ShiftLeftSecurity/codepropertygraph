package some

import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps

object SomeDomain {
  implicit def toSynth1(p: D1) = {
    new SynthExt[SingleTravTypes.type](p: Single[D1])
  }
  implicit def toSynth2[T[_]](trav: TravTypesFor[T]#Collection[D1]) = {
    new SynthExt[TravTypesFor[T]](trav)
  }
  implicit def toSynth3[CC[_], C](trav: IterableOnceOps[D1, CC, C]) = {
    //new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
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
