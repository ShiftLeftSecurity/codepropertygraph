package io.shiftleft.queryprimitives

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.ProtoCpgLoader

import scala.collection.JavaConverters._

class CpgOverlayLoader {

  /**
    * Load overlay stored in the file with the name `filename`.
    * */
  def load(filename: String, baseCpg: Cpg): Unit =
    ProtoCpgLoader.loadOverlays(filename).asScala.foreach { overlay =>
      new CpgOverlayApplier().applyDiff(overlay, baseCpg.graph)
    }

}

/** singleton instance for convenience */
object CpgOverlayLoader extends CpgOverlayLoader
