package io.shiftleft.codepropertygraph.cpgloading

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct, DiffGraph}
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge
import java.util.{Collection => JCollection, List => JList}
import java.io.InputStream
import java.nio.file.{Files, Path}

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import overflowdb.Config

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try, Using}

object ProtoCpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def loadFromProtoZip(fileName: String, overflowDbConfig: Config = Config.withoutOverflow): Cpg =
    measureAndReport {
      val builder = new ProtoToCpg(overflowDbConfig)
      Using.Manager { use =>
        val edgeLists: ArrayBuffer[JCollection[Edge]] = ArrayBuffer.empty
        val zip = use(new java.util.zip.ZipFile(fileName))
        val names = mutable.Set[String]()
        for (zipEntry <- zip.entries().asScala) {
          if (!names.add(zipEntry.getName)) {
            logger.warn(
              s"""Zip file contains multiple entries with name "${zipEntry.getName}" -- is this really intended?""")
          }
          val inputStream = use(zip.getInputStream(zipEntry))
          val cpgStruct = getNextProtoCpgFromStream(inputStream)
          builder.addNodes(cpgStruct.getNodeList)
          edgeLists.addOne(cpgStruct.getEdgeList)
        }
        edgeLists.foreach(edgeCollection => builder.addEdges(edgeCollection))
      } match {
        case Failure(exception) => throw exception
        case Success(_)         => builder.build()
      }
    }

  def loadFromListOfProtos(cpgs: Seq[CpgStruct], overflowDbConfig: Config): Cpg = {
    val builder = new ProtoToCpg(overflowDbConfig)
    cpgs.foreach(cpg => builder.addNodes(cpg.getNodeList))
    cpgs.foreach(cpg => builder.addEdges(cpg.getEdgeList))
    builder.build()
  }

  def loadFromListOfProtos(cpgs: JList[CpgStruct], overflowDbConfig: Config): Cpg =
    loadFromListOfProtos(cpgs.asScala.toSeq, overflowDbConfig)

  def loadOverlays(fileName: String): Try[Iterator[CpgOverlay]] =
    loadOverlays(fileName, CpgOverlay.parseFrom)

  def loadDiffGraphs(fileName: String): Try[Iterator[DiffGraph]] =
    loadOverlays(fileName, DiffGraph.parseFrom)

  private def loadOverlays[T <: GeneratedMessageV3](fileName: String, f: InputStream => T): Try[Iterator[T]] =
    Using(new ZipArchive(fileName)) { zip =>
      zip.entries
        .sortWith(compareOverlayPath)
        .map { path =>
          val is = Files.newInputStream(path)
          f(is)
        }
        .iterator
    }

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
