package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.codepropertygraph.Cpg

import scala.collection.JavaConverters._

class CpgOverlayLoader {

  /**
    * Load overlay stored in the file with the name `filename`.
    */
  def load(filename: String, baseCpg: Cpg): Unit = {
    val applier = new CpgOverlayApplier(baseCpg.graph)
    ProtoCpgLoader.loadOverlays(filename).asScala.foreach { overlay =>
      applier.applyDiff(overlay)
    }
  }

}

/** singleton instance for convenience */
object CpgOverlayLoader extends CpgOverlayLoader
