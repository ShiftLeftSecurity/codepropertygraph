package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.codepropertygraph.generated.Cpg
import org.slf4j.{Logger, LoggerFactory}

import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

object CpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  /** Load a Code Property Graph
   *
   * @param filename
   *   name of file that stores the code property graph
   */
  def load(filename: String): Cpg =
    load(Paths.get(filename))

  /** Load a Code Property Graph - it detects the format as either flatgraph, overflowdb or proto.
   * A flatgraph storage is opened straight away without conversion.
   * Note: OverflowDb and proto formats are first converted to flatgraph, and therefor we create a new flatgraph
   * storage path, which can be obtained via `cpg.graph.storagePathMaybe` */
  def load(path: Path): Cpg = {
    val absolutePath = path.toAbsolutePath
    if (!Files.exists(absolutePath)) {
      throw new FileNotFoundException(s"given input file $absolutePath does not exist")
    } else if (isProtoFormat(absolutePath)) {
      val flatgraphStoragePath = absolutePath.resolveSibling("cpg.fg")
      logger.debug(s"Converting $path from proto cpg into new flatgraph storage: $flatgraphStoragePath")
      ProtoCpgLoader.loadFromProtoZip(absolutePath.toString, Option(flatgraphStoragePath))
    } else if (isOverflowDbFormat(absolutePath)) {
      loadFromOverflowDb(absolutePath)
    } else {
      Cpg.withStorage(absolutePath)
    }
  }

  /** Determine whether the CPG is a legacy (proto) CPG */
  def isProtoFormat(path: Path): Boolean =
    probeFirstBytes(path, "PK")

  /** Determine whether the CPG is a proto CPG */
  def isProtoFormat(filename: String): Boolean =
    isProtoFormat(Paths.get(filename))

  def isOverflowDbFormat(path: Path): Boolean =
    probeFirstBytes(path, "H:2")

  /** Load Code Property Graph from an overflow DB file, by first converting it into a flatgraph binary */
  def loadFromOverflowDb(path: Path): Cpg = {
    val flatgraphStoragePath = path.resolveSibling("cpg.fg")
    logger.debug(s"Converting $path from overflowdb to new flatgraph storage: $flatgraphStoragePath")
    flatgraph.convert.Convert(overflowDbFile = path, outputFile = flatgraphStoragePath)
    Cpg.withStorage(flatgraphStoragePath)
  }

  /** Determine whether the CPG is a legacy (proto) CPG */
  @deprecated("use `isProtoCpg` instead")
  def isLegacyCpg(filename: String): Boolean =
    isLegacyCpg(Paths.get(filename))

  /** Determine whether the CPG is a legacy (proto) CPG */
  @deprecated("use `isProtoCpg` instead")
  def isLegacyCpg(path: Path): Boolean =
    isProtoFormat(path)

  private def probeFirstBytes(path: Path, probeFor: String): Boolean = {
    Using(Files.newInputStream(path)) { is =>
      val buffer = new Array[Byte](probeFor.size)
      is.read(buffer)
      new String(buffer, StandardCharsets.UTF_8) == probeFor
    }.getOrElse(false)
  }

}
