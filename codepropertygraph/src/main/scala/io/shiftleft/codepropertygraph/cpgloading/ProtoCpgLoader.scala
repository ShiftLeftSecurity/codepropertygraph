package io.shiftleft.codepropertygraph.cpgloading

import com.google.protobuf.GeneratedMessageV3
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge
import io.shiftleft.proto.cpg.Cpg.CpgStruct.Edge.EdgeType
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct}
import org.slf4j.{Logger, LoggerFactory}

import java.io.InputStream
import java.nio.file.{Files, Path}
import java.util.{List => JList}
import scala.collection.mutable.ArrayDeque
import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try, Using}
import scala.jdk.CollectionConverters.*

object ProtoCpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def loadFromProtoZip(fileName: String, storagePath: Option[Path]): Cpg =
    measureAndReport {
      val builder = new ProtoToCpg(storagePath)
      Using.Manager { use =>
        val edges = ArrayDeque.empty[TmpEdge]
        use(new ZipArchive(fileName)).entries.foreach { entry =>
          val inputStream = use(Files.newInputStream(entry))
          val protoCpg   = getNextProtoCpgFromStream(inputStream)
          builder.addProtoCpg
          builder.addNodes(protoCpg.getNodeList)
          protoCpg.getEdgeList.asScala.foreach { edge =>
            edges.append(new TmpEdge(edge))
          }
        }
        while (edges.nonEmpty) {
          val edge = edges.removeHead()
          if (edge.properties.size > 1) {
            throw new IllegalArgumentException(s"flatgraph only supports zero or one edge properties, but the given edge has ${edge.properties.size}; details: $edge")
          } else {
            builder.addEdge(edge.dst, edge.src, EdgeType.forNumber(edge.typ), edge.properties.headOption)
          }
        }
      } match {
        case Failure(exception) => throw exception
        case Success(_)         => builder.build()
      }
    }

  def loadFromListOfProtos(protoCpgs: Seq[CpgStruct], storagePath: Option[Path]): Cpg = {
    val builder = new ProtoToCpg(storagePath)
    protoCpgs.foreach(cpg => builder.addNodes(cpg.getNodeList))
    protoCpgs.foreach { protoCpg =>
      protoCpg.getEdgeList.asScala.foreach { edge =>
        if (edge.getPropertyCount > 1) {
          throw new IllegalArgumentException(s"flatgraph only supports zero or one edge properties, but the given edge has ${edge.getPropertyCount}; details: $edge")
        } else {
          builder.addEdge(edge.getDst, edge.getSrc, edge.getType, edge.getPropertyList.asScala.headOption)
        }
      }
    }
    builder.build()
  }

   def loadFromListOfProtos(cpgs: JList[CpgStruct], storagePath: Option[Path]): Cpg =
     loadFromListOfProtos(cpgs.asScala.toSeq, storagePath)

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
