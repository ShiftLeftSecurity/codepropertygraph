package io.shiftleft.cpgloading.tinkergraph

import io.shiftleft.cpgenhancements.CpgEnhancements
import io.shiftleft.cpgloading.{ProtoCpgLoader, ProtoToTinkerCpg}
import io.shiftleft.queryprimitives.steps.starters.Cpg


/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Tinkergraph instance */
class CpgLoader {

  def loadCodePropertyGraph(filename: String, runEnhancements: Boolean = true): Cpg = {
    val builder = new ProtoToTinkerCpg
    val cpg = new ProtoCpgLoader(builder).loadFromProtoZip(filename)
    if (runEnhancements) {
      CpgEnhancements.run(cpg.graph)
    }
    cpg
  }

}
