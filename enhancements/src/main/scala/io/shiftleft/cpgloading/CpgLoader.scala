package io.shiftleft.cpgloading

import io.shiftleft.layers.EnhancementLayers
import io.shiftleft.queryprimitives.steps.starters.Cpg

/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Tinkergraph instance */
class CpgLoader {
  protected def builder = new ProtoToCpg()

  def loadCodePropertyGraph(filename: String, runEnhancements: Boolean = true): Cpg = {
    val cpg = new ProtoCpgLoader(builder).loadFromProtoZip(filename)
    if (runEnhancements) {
      new EnhancementLayers().run(cpg.graph)
    }
    cpg
  }

}
