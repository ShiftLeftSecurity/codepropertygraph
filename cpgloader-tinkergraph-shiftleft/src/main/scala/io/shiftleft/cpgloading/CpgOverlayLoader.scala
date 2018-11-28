package io.shiftleft.cpgloading

import io.shiftleft.queryprimitives.steps.starters.Cpg
import scala.collection.JavaConverters._

class CpgOverlayLoader {

  /**
    * Load overlay stored in the file with the name `filename`.
    * */
  def load(filename: String, baseCpg: Cpg): Unit = {
    tinkergraphshiftleft.CpgLoader.loadOverlays(filename).foreach { overlay =>
      new CpgOverlayApplier().applyDiff(overlay, baseCpg.graph)
    }
  }

}

/** singleton instance for convenience */
object CpgOverlayLoader extends CpgOverlayLoader
