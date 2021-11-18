package some

import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps

object SomeDomain {
  implicit def toSynth1(p: D1) = {
    new SynthExt[Trav1, SingleTypes.type](p: Trav1[D1])
  }
  implicit def toSynth2(trav: Option[D1]) = {
    new SynthExt[Option, OptionTypes.type](trav)
  }
  implicit def toSynth3[CC[_], C](trav: IterableOnceOps[D1, CC, C]) = {
    //new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
    new SynthExt[({type X[B] = IterableOnceOps[B, CC, C]})#X, IterableTypes[CC, C]](trav)
  }

  //implicit def toSynth[IT[_]](p: IT[D1]): SynthExt[IT] = {
  //  new SynthExt(p)
  //}

  class SynthExt[IT[_], TM <: TypeMultiplexer](val trav: IT[D1]) extends AnyVal {
    def toD2(implicit ops: TravOps[IT, TM]) = {
      ops.oneToOne(trav)(_.x)
    }
    def toD2Multi(implicit ops: TravOps[IT, TM]) = {
      ops.oneToMany(trav)(x => Iterator.single(x))
    }
  }

  case class D1(x: D2)

  case class D2()
}
