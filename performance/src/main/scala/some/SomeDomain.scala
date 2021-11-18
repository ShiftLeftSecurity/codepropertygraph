package some

import io.shiftleft.semanticcpg.langv2._

object SomeDomain {
  implicit def toSynth(p: D1): SynthExt[Trav1] = {
    new SynthExt(p: Trav1[D1])
  }

  implicit def toSynth[IT[_]](p: IT[D1]): SynthExt[IT] = {
    new SynthExt(p)
  }

  class SynthExt[IT[_]](val trav: IT[D1]) extends AnyVal {
    def toD2(implicit ops: TravOps[IT]) = {
      ops.oneToOne(trav)(_.x)
    }
    def toD2Multi(implicit ops: TravOps[IT]) = {
      ops.oneToMany(trav)(x => Iterator.single(x))
    }
  }

  case class D1(x: D2)

  case class D2()
}
