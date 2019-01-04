package io.shiftleft.cpgloading.janusgraph

import io.shiftleft.cpgloading.CpgLoaderBase

/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Janusgraph instance */
class CpgLoader extends CpgLoaderBase {
  override def builder = new ProtoToCpg
}
