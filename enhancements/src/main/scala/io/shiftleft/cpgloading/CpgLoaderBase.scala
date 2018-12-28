package io.shiftleft.cpgloading

import io.shiftleft.layers.EnhancementLayers
import io.shiftleft.proto.cpg.Cpg.{CpgStruct, CpgOverlay}
import io.shiftleft.queryprimitives.steps.starters.Cpg
import java.io.InputStream
import java.util.{List => JList}
import scala.collection.JavaConverters._

/** Load cpg proto (typically cpg.bin.zip) into a graph instance */
abstract class CpgLoaderBase {

  protected def builder: ProtoToCpgBase
  lazy val protoCpgLoader = new ProtoCpgLoader(builder)

  def loadFromFile(filename: String, runEnhancements: Boolean = true): Cpg = {
    val cpg = protoCpgLoader.loadFromProtoZip(filename)
    if (runEnhancements) {
      new EnhancementLayers().run(cpg.graph)
    }
    cpg
  }

  def loadFromInputStream(inputStream: InputStream, runEnhancements: Boolean = true): Cpg = {
    val cpg = protoCpgLoader.loadFromInputStream(inputStream)
    if (runEnhancements) {
      new EnhancementLayers().run(cpg.graph)
    }
    cpg
  }

  def loadFromListOfProtos(cpgs: JList[CpgStruct]): Cpg = 
    protoCpgLoader.loadFromListOfProtos(cpgs)

  def loadOverlays(filename: String): List[CpgOverlay] =
    protoCpgLoader.loadOverlays(filename).asScala.toList

}
