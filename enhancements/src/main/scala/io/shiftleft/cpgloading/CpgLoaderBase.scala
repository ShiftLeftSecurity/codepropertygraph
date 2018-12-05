package io.shiftleft.cpgloading

import io.shiftleft.layers.EnhancementLayers
import io.shiftleft.proto.cpg.Cpg.CpgOverlay
import io.shiftleft.queryprimitives.steps.starters.Cpg
import scala.collection.JavaConverters._


/** Load cpg proto (typically cpg.bin.zip) into a graph instance */
abstract class CpgLoaderBase {

  protected def builder: ProtoToCpgBase

  // override def createIndexes(graph: Graph): Unit = {

  def loadCodePropertyGraph(filename: String, runEnhancements: Boolean = true): Cpg = {
    val cpg = new ProtoCpgLoader(builder).loadFromProtoZip(filename)
    if (runEnhancements) {
      new EnhancementLayers().run(cpg.graph)
    }
    cpg
  }

  def loadOverlays(filename: String): List[CpgOverlay] =
    new ProtoCpgLoader(builder).loadOverlays(filename).asScala.toList

}
