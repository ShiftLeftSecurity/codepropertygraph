package io.shiftleft.cpgloading.tinkergraphshiftleft

import gremlin.scala._
import io.shiftleft.cpgloading.CpgLoaderBase
import io.shiftleft.codepropertygraph.generated.NodeKeys
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

/* singleton instance for convenience */
object CpgLoader extends CpgLoader

/** Load cpg proto (typically cpg.bin.zip) into a new Tinkergraph instance */
class CpgLoader extends CpgLoaderBase {
  override def builder = new ProtoToCpg
}
