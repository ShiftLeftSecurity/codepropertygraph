package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.codepropertygraph.generated.Cpg
import org.slf4j.{Logger, LoggerFactory}

import java.io.FileNotFoundException
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

object CpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  /** Load a Code Property Graph from the given file */
  def load(filename: String): Cpg =
    load(Paths.get(filename))

  /** Load a Code Property Graph from the given file - persist in given second file.
    * I.e. the given input file will not be modified, all changes will be written to the given 'persistTo' file. *
    */
  def load(from: String, persistTo: String): Cpg =
    load(Paths.get(from), Paths.get(persistTo))

  /** Load a Code Property Graph from the given file
    *
    * Notes:
    *   - detects the format as either flatgraph, overflowdb or proto
    *   - a flatgraph storage opened straight away
    *   - OverflowDb and proto formats are first converted to flatgraph, which is written to the `persistTo` file
    *   - OverflowDb and proto formats are first converted to flatgraph, and therefor we create a new flatgraph storage
    *     path, which can be obtained via `cpg.graph.storagePathMaybe`
    */
  def load(path: Path): Cpg = {
    val absolutePath = path.toAbsolutePath
    if (!Files.exists(absolutePath)) {
      throw new FileNotFoundException(s"given input file $absolutePath does not exist")
    } else if (isProtoFormat(absolutePath)) {
      load(path, persistTo = absolutePath.resolveSibling(s"${path.getFileName}.fg"))
    } else if (isOverflowDbFormat(absolutePath)) {
      load(absolutePath, persistTo = path.resolveSibling(s"${path.getFileName}.fg"))
    } else {
      // assuming it's flatgraph format
      Cpg.withStorage(absolutePath)
    }
  }

  /** Load a Code Property Graph from the given file, but persist it in the given second file.
    * I.e. the given input file will not be modified, all changes will be written to the given 'persistTo' file.
    *
    * Notes:
    *   - if the given 'persistTo' file already exists, it will be overridden
    *   - detects the format as either flatgraph, overflowdb or proto
    *   - a flatgraph storage is copied to the `persistTo` file and then opened straight away
    *   - OverflowDb and proto formats are first converted to flatgraph, which is written to the `persistTo` file
    */
  def load(from: Path, persistTo: Path): Cpg = {
    val absolutePath = from.toAbsolutePath
    if (persistTo != from)
      Files.deleteIfExists(persistTo)

    if (!Files.exists(absolutePath)) {
      throw new FileNotFoundException(s"given input file $absolutePath does not exist")
    } else if (isProtoFormat(absolutePath)) {
      logger.debug(s"Converting $from from proto cpg into new flatgraph storage: $persistTo")
      ProtoCpgLoader.loadFromProtoZip(absolutePath.toString, Option(persistTo))
    } else if (isOverflowDbFormat(absolutePath)) {
      loadFromOverflowDb(absolutePath, persistTo)
    } else {
      // assuming it's flatgraph format
      Files.copy(absolutePath, persistTo)
      Cpg.withStorage(persistTo)
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
  def loadFromOverflowDb(path: Path, persistTo: Path): Cpg = {
    logger.info(s"Converting $path from overflowdb to new flatgraph storage: $persistTo")
    flatgraph.convert.Convert(overflowDbFile = path, outputFile = persistTo)
    Cpg.withStorage(persistTo)
  }

  /** Determine whether the CPG is a legacy (proto) CPG */
  @deprecated("use `isProtoCpg` instead")
  def isLegacyCpg(filename: String): Boolean =
    isProtoFormat(Paths.get(filename))

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
