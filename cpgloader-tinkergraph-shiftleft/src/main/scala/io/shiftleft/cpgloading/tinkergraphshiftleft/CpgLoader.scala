package io.shiftleft.cpgloading.tinkergraphshiftleft

import io.shiftleft.cpgloading.{CpgLoaderBase, ProtoToTinkerShiftleftCpg}

/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Tinkergraph instance */
class CpgLoader extends CpgLoaderBase {
  override def builder = new ProtoToTinkerShiftleftCpg
}
