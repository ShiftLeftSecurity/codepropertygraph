package io.shiftleft.codepropertygraph.cpgloading

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}
import org.slf4j.{Logger, LoggerFactory}
import overflowdb.Config

import java.io.InputStream
import java.nio.file.{Files, Path}
import java.util.{List => JList}
import scala.collection.mutable.ArrayDeque
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try, Using}

// This class is used to temporarily store edges we have red from proto before we store them
// in the CPG. As usual proto is very memory inefficiet when it comes to memory and saving a
// few bytes adds up since we keep all graph edge in this intermediate representation before
// we start adding the edges to the CPG.
case class TmpEdge(dst: Long, src: Long, typ: Int, properties: List[Edge.Property]) {
  def this(edge: Edge) = {
    this(edge.getDst, edge.getSrc, edge.getTypeValue, List.from(edge.getPropertyList.asScala))
  }
}

object ProtoCpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def loadFromProtoZip(fileName: String, overflowDbConfig: Config = Config.withoutOverflow): Cpg =
    measureAndReport {
      val builder = new ProtoToCpg(overflowDbConfig)
      Using.Manager { use =>
        val edges = ArrayDeque.empty[TmpEdge]
        use(new ZipArchive(fileName)).entries.foreach { entry =>
          val inputStream = use(Files.newInputStream(entry))
          val cpgStruct   = getNextProtoCpgFromStream(inputStream)
          builder.addNodes(cpgStruct.getNodeList)
          cpgStruct.getEdgeList.asScala.foreach { edge =>
            edges.append(new TmpEdge(edge))
          }
        }
        // We remove the edges while iterating so that the GC can already collect them.
        while (edges.nonEmpty) {
          val edge = edges.removeHead()
          builder.addEdge(edge.dst, edge.src, EdgeType.forNumber(edge.typ), edge.properties)
        }
      } match {
        case Failure(exception) => throw exception
        case Success(_)         => builder.build()
      }
    }

  def loadFromListOfProtos(cpgs: Seq[CpgStruct], overflowDbConfig: Config): Cpg = {
    val builder = new ProtoToCpg(overflowDbConfig)
    cpgs.foreach(cpg => builder.addNodes(cpg.getNodeList))
    cpgs.foreach { cpg =>
      cpg.getEdgeList.asScala.foreach { edge =>
        builder.addEdge(edge.getDst, edge.getSrc, edge.getType, edge.getPropertyList.asScala)
      }
    }
    builder.build()
  }

  def loadFromListOfProtos(cpgs: JList[CpgStruct], overflowDbConfig: Config): Cpg =
    loadFromListOfProtos(cpgs.asScala.toSeq, overflowDbConfig)

  def loadOverlays(fileName: String): Try[Iterator[CpgOverlay]] =
    loadOverlays(fileName, CpgOverlay.parseFrom)

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
    val start  = System.currentTimeMillis()
    val result = f
    logger.info("CPG construction finished in " + (System.currentTimeMillis() - start) + "ms.")
    result
  }
}
