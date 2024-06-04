package io.shiftleft.codepropertygraph.cpgloading

import better.files.File
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.codepropertygraph.generated.PropertyNames
import org.slf4j.{Logger, LoggerFactory}

import scala.util.Try

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

  /** Determine whether the CPG is a legacy (proto) CPG
    *
    * @param filename
    *   name of the file to probe
    */
  def isLegacyCpg(filename: String): Boolean =
    isLegacyCpg(File(filename))

  /** Determine whether the CPG is a legacy (proto) CPG
    *
    * @param file
    *   file to probe
    */
  def isLegacyCpg(file: File): Boolean = {
    val bytes = file.bytes
    Try {
      bytes.next() == 'P' && bytes.next() == 'K'
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
    val cpg = Cpg.withConfig(config.overflowDbConfig)
    if (config.createIndexes) { createIndexes(cpg) }
    cpg
  }

  def createIndexes(cpg: Cpg): Unit =
    cpg.graph.indexManager.createNodePropertyIndex(PropertyNames.FULL_NAME)

}
