package io.shiftleft.cpgloading.neo4j

import io.shiftleft.cpgloading.CpgLoaderBase

/* singleton instance for convenience */
object CpgLoader extends CpgLoader("/tmp/cpg_data")

/** Load cpg proto (typically cpg.bin.zip) into a new neo4j instance */
class CpgLoader(dbPath: String) extends CpgLoaderBase {
  override def builder = new ProtoToCpg(dbPath)
}
