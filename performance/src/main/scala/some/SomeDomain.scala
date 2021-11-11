package some

import io.shiftleft.semanticcpg.langv2._

object SomeDomain {
  implicit def toSynth[FT[_]](p: D1): SynthExt[Trav1, FT] = {
    new SynthExt(p: Trav1[D1])
  }

  implicit def toSynth[IT[_], FT[_]](p: IT[D1]): SynthExt[IT, FT] = {
    new SynthExt(p)
  }

  class SynthExt[IT[_], FT[_]](val trav: IT[D1]) extends AnyVal {
    def toD2(implicit ops: TravOps[IT, FT]) = {
      trav.map(_.x)
    }
  }

  case class D1(x: D2)

  case class D2()
}
