package io.shiftleft.codepropertygraph.cpgloading

import java.io.InputStream
import java.nio.file.{Files, Path}

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct, DiffGraph}
import org.apache.logging.log4j.LogManager
import java.util.{Collection => JCollection, List => JList}

import com.google.protobuf.GeneratedMessageV3

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try, Using}
import io.shiftleft.overflowdb.OdbConfig
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge

import scala.collection.mutable.ArrayBuffer

object ProtoCpgLoader {
  private val logger = LogManager.getLogger(getClass)

  def loadFromProtoZip(fileName: String, overflowDbConfig: OdbConfig = OdbConfig.withoutOverflow): Cpg =
    measureAndReport {
      val builder = new ProtoToCpg(overflowDbConfig)
      Using.Manager { use =>
        val edgeLists: ArrayBuffer[JCollection[Edge]] = ArrayBuffer.empty
        use(new ZipArchive(fileName)).entries.foreach { entry =>
          val inputStream = use(Files.newInputStream(entry))
          val cpgStruct = getNextProtoCpgFromStream(inputStream)
          builder.addNodes(cpgStruct.getNodeList)
          edgeLists += cpgStruct.getEdgeList
        }
        edgeLists.foreach(edgeCollection => builder.addEdges(edgeCollection))
      } match {
        case Failure(exception) => throw exception
        case Success(_)         => builder.build()
      }
    }

  def loadFromListOfProtos(cpgs: Seq[CpgStruct], overflowDbConfig: OdbConfig): Cpg = {
    val builder = new ProtoToCpg(overflowDbConfig)
    cpgs.foreach(cpg => builder.addNodes(cpg.getNodeList))
    cpgs.foreach(cpg => builder.addEdges(cpg.getEdgeList))
    builder.build()
  }

  def loadFromListOfProtos(cpgs: JList[CpgStruct], overflowDbConfig: OdbConfig): Cpg =
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
