package io.shiftleft.cpgloading.tinkergraphshiftleft

import io.shiftleft.cpgenhancements.CpgEnhancements
import io.shiftleft.cpgloading.{ProtoCpgLoader, ProtoToTinkerShiftleftCpg}
import io.shiftleft.queryprimitives.steps.starters.Cpg


/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Tinkergraph instance */
class CpgLoader {

  def loadCodePropertyGraph(filename: String, runEnhancements: Boolean = true): Cpg = {
    val builder = new ProtoToTinkerShiftleftCpg
    val cpg = new ProtoCpgLoader(builder).loadFromProtoZip(filename)
    if (runEnhancements) {
      CpgEnhancements.run(cpg.graph)
    }
    cpg
  }

}
