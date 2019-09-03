package io.shiftleft.codepropertygraph.cpgloading

import java.io.InputStream
import java.nio.file.{Files, Path}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}
import org.apache.logging.log4j.LogManager
import resource.{ManagedResource, managed}
import java.util.{List => JList}

import scala.collection.JavaConverters._
import io.shiftleft.overflowdb.OdbConfig

object ProtoCpgLoader {
  private val logger = LogManager.getLogger(getClass)

  def loadFromProtoZip(fileName: String, overflowDbConfig: OdbConfig = OdbConfig.withoutOverflow): Cpg = {
    measureAndReport {
      val builder = new ProtoToCpg(overflowDbConfig)
      for {
        zip <- managed(new ZipArchive(fileName))
        entry <- zip.entries
        inputStream <- managed(Files.newInputStream(entry))
      } builder.addNodes(getNextProtoCpgFromStream(inputStream).getNodeList)

      /* second pass so we can stream for the edges
       * -> holding them all in memory is potentially too much
       * -> adding them as we go isn't an option because we may only have one of the adjacent vertices
       */
      for (zip <- managed(new ZipArchive(fileName));
           entry <- zip.entries;
           inputStream <- managed(Files.newInputStream(entry))) {
        builder.addEdges(getNextProtoCpgFromStream(inputStream).getEdgeList)
      }

      builder.build()
    }
  }

  def loadFromListOfProtos(cpgs: Seq[CpgStruct], overflowDbConfig: OdbConfig): Cpg = {
    val builder = new ProtoToCpg(overflowDbConfig)
    cpgs.foreach(cpg => builder.addNodes(cpg.getNodeList))
    cpgs.foreach(cpg => builder.addEdges(cpg.getEdgeList))
    builder.build()
  }

  def loadFromListOfProtos(cpgs: JList[CpgStruct], overflowDbConfig: OdbConfig): Cpg =
    loadFromListOfProtos(cpgs.asScala, overflowDbConfig)

  def loadOverlays(fileName: String): ManagedResource[Iterator[CpgOverlay]] =
    managed(new ZipArchive(fileName)).map(readOverlayEntries)

  private def readOverlay(path: Path): CpgOverlay =
    managed(Files.newInputStream(path)).map(CpgOverlay.parseFrom).tried.get

  private def readOverlayEntries(zip: ZipArchive): Iterator[CpgOverlay] =
    zip.entries
      .sortWith(compareOverlayPath)
      .iterator
      .map(readOverlay)

  private def compareOverlayPath(a: Path, b: Path): Boolean = {
    val file1Split: Array[String] = a.toString.replace("/", "").split("_")
    val file2Split: Array[String] = b.toString.replace("/", "").split("_")
    if (file1Split.length < 2 || file2Split.length < 2)
      a.toString < b.toString
    else
      file1Split(0).toInt < file2Split(0).toInt
  }

  private def getNextProtoCpgFromStream(inputStream: InputStream) =
    CpgStruct.parseFrom(inputStream)

  private def measureAndReport[A](f: => A): A = {
    val start = System.currentTimeMillis()
    val result = f
    logger.info("CPG construction finished in " + (System.currentTimeMillis() - start) + "ms.")
    result
  }
}
