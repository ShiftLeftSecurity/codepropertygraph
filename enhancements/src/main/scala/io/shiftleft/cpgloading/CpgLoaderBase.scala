package io.shiftleft.cpgloading

import io.shiftleft.cpgenhancements.CpgEnhancements
import io.shiftleft.cpgloading.ProtoCpgLoader
import io.shiftleft.queryprimitives.steps.starters.Cpg

/** Load cpg proto (typically cpg.bin.zip) into a graph instance */
abstract class CpgLoaderBase {

  protected def builder: ProtoToCpgBase

  def loadCodePropertyGraph(filename: String, runEnhancements: Boolean = true): Cpg = {
    val cpg = new ProtoCpgLoader(builder).loadFromProtoZip(filename)
    if (runEnhancements) {
      CpgEnhancements.run(cpg.graph)
    }
    cpg
  }

}
