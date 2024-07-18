package io.shiftleft.codepropertygraph.cpgloading

import better.files.File
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.PropertyNames
import org.slf4j.{Logger, LoggerFactory}

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}
import scala.util.Using

object CpgLoader {

  private val logger: Logger = LoggerFactory.getLogger(classOf[CpgLoader])

  /** Load a Code Property Graph
    *
    * @param filename
    *   name of file that stores the code property graph
    * @param config
    *   loader configuration
    */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig()): Cpg =
    new CpgLoader().load(filename, config)

  /** Load Code Property Graph from an overflow DB file
    *
    * @param config
    *   loader config
    *
    * This methods loads the CPG from an existing overflow DB file, specified in config.overflowDbConfig. In particular,
    * this config specifies the filename. For example, to load the database at "foo.db", you can issue the following:
    *
    * val odbConfig = Config.withDefaults().withStorageLocation(config.spPath) val config =
    * CpgLoaderConfig().withOverflowConfig(odbConfig) CpgLoader.loadFromOverflowDb(config)
    */
  def loadFromOverflowDb(config: CpgLoaderConfig = CpgLoaderConfig()): Cpg = {
    new CpgLoader().loadFromOverflowDb(config)
  }

  /** Create any indexes necessary for quick access.
    *
    * @param cpg
    *   the CPG to create indexes in
    */
  def createIndexes(cpg: Cpg): Unit =
    new CpgLoader().createIndexes(cpg)

  /** Determine whether the CPG is a legacy (proto) CPG */
  def isProtoFormat(path: Path): Boolean =
    probeFirstBytes(path, "PK")

  /** Determine whether the CPG is a proto CPG */
  def isProtoFormat(filename: String): Boolean =
    isProtoFormat(Paths.get(filename))

  def isOverflowDbFormat(path: Path): Boolean =
    probeFirstBytes(path, "H:2")

  def isFlatgraphFormat(path: Path): Boolean =
    probeFirstBytes(path, "FLT GRPH") // flatgraph.storage.MagicBytesString

  /** Load Code Property Graph from an overflow DB file, by first converting it into a flatgraph binary */
  def loadFromOverflowDb(path: Path, persistTo: Path): Cpg = {
    logger.info(s"Converting $path from overflowdb to new flatgraph storage: $persistTo")
    flatgraph.convert.Convert.convertOdbToFlatgraph(overflowDbFile = path, outputFile = persistTo)
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

private class CpgLoader {

  import CpgLoader.logger

  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.withoutOverflow): Cpg = {
    logger.debug("Loading " + filename)

    val cpg =
      ProtoCpgLoader.loadFromProtoZip(filename, config.overflowDbConfig)
    if (config.createIndexes) { createIndexes(cpg) }
    cpg
  }

  def loadFromOverflowDb(config: CpgLoaderConfig = CpgLoaderConfig()): Cpg = {
    config.overflowDbConfig.getStorageLocation.ifPresent { storagePath =>
      if (CpgLoader.isFlatgraphFormat(storagePath)) {
        logger.info(s"converting flatgraph storage $storagePath to overflowdb")
        val odbFilename = s"$storagePath-converted.odb"
        flatgraph.convert.Convert.convertFlatgraphToOdb(fgFile = storagePath, outputFile = Paths.get(odbFilename))
        config.overflowDbConfig.withStorageLocation(odbFilename)
      }
    }

    val cpg = Cpg.withConfig(config.overflowDbConfig)
    if (config.createIndexes) { createIndexes(cpg) }
    cpg
  }

  def createIndexes(cpg: Cpg): Unit =
    cpg.graph.indexManager.createNodePropertyIndex(PropertyNames.FULL_NAME)

}
