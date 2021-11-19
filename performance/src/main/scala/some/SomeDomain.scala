package some

import io.shiftleft.semanticcpg.langv2._

import scala.collection.IterableOnceOps

object SomeDomain {
  implicit def toSynth1(p: D1) = {
    new SynthExt[SingleTypes.type](p: Trav1[D1])
  }
  implicit def toSynth2[IT[_]](trav: TypesClassFor[IT]#IT[D1]) = {
    new SynthExt[TypesClassFor[IT]](trav)
  }
  implicit def toSynth3[CC[_], C](trav: IterableOnceOps[D1, CC, C]) = {
    //new AstTraversalNew[I, ({type X[B] = IterableOnceOps[B, CC, C]})#X, CC, CC, ({type X[B] = C})#X, CC](trav)
    new SynthExt[IterableTypes[CC, C]](trav)
  }

  //implicit def toSynth[IT[_]](p: IT[D1]): SynthExt[IT] = {
  //  new SynthExt(p)
  //}

  class SynthExt[TM <: TypeMultiplexer](val trav: TM#IT[D1]) extends AnyVal {
    def toD2(implicit ops: TravOps[TM]) = {
      trav.map(_.x)
    }
    def toD2Multi(implicit ops: TravOps[TM]) = {
      trav.flatMap(Iterator.single)
    }
  }

  case class D1(x: D2)

  case class D2()
}
