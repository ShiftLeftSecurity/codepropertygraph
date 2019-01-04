package io.shiftleft.cpgloading

import io.shiftleft.proto.cpg.Cpg.{CpgStruct, CpgOverlay}
import io.shiftleft.queryprimitives.steps.starters.Cpg
import java.io.InputStream
import java.util.{List => JList}
import scala.collection.JavaConverters._

/** Load cpg proto (typically cpg.bin.zip) into a graph instance */
abstract class CpgLoaderBase {

  def builder: ProtoToCpgBase
  def protoCpgLoader = new ProtoCpgLoader(builder)

  def loadFromFile(filename: String): Cpg =
    protoCpgLoader.loadFromProtoZip(filename)

  def loadFromInputStream(inputStream: InputStream): Cpg =
    protoCpgLoader.loadFromInputStream(inputStream)

  def loadFromListOfProtos(cpgs: JList[CpgStruct]): Cpg = 
    protoCpgLoader.loadFromListOfProtos(cpgs)

  def loadOverlays(filename: String): List[CpgOverlay] =
    protoCpgLoader.loadOverlays(filename).asScala.toList

}
