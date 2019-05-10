package io.shiftleft.cpgloading

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{IgnoredProtoEntries, OnDiskOverflowConfig, ProtoCpgLoader}
import io.shiftleft.codepropertygraph.generated.NodeKeys
import io.shiftleft.queryprimitives.CpgOverlayLoader
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

import scala.compat.java8.OptionConverters._

object CpgLoaderConfig {

  def default: CpgLoaderConfig = CpgLoaderConfig(
    createIndices = true,
    onDiskOverflowConfig = None,
    ignoredProtoEntries = None
  )
}

/**
  * Configuration for the CPG loader
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  *  */
case class CpgLoaderConfig(var createIndices: Boolean,
                           var onDiskOverflowConfig: Option[OnDiskOverflowConfig],
                           var ignoredProtoEntries: Option[IgnoredProtoEntries])

object CpgLoader {

  /**
    * Load a Code Property Graph
    *
    * @param filename name of file that stores the code property graph
    * @param config loader configuration
    * */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default): Cpg = {
    new CpgLoader().load(filename, config)
  }
}

private class CpgLoader {

  private val logger = LogManager.getLogger(getClass)

  /**
    * Load a Code Property Graph
    *
    * @param filename name of file that stores the code property graph
    * @param config loader configuration
    * */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default): Cpg = {
    logger.debug("Loading " + filename)
    val cpg =
      ProtoCpgLoader.loadFromProtoZip(filename, config.onDiskOverflowConfig.asJava, config.ignoredProtoEntries.asJava)
    if (config.createIndices) { createIndexes(cpg) }
    cpg
  }

  /**
    * Create any indexes necessary for quick access.
    *
    * @param cpg the CPG to create indexes in
    */
  def createIndexes(cpg: Cpg): Unit =
    cpg.graph.asInstanceOf[TinkerGraph].createIndex(NodeKeys.FULL_NAME.name, classOf[Vertex])

  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg) =
    overlayFilenames.foreach { overlayFilename =>
      CpgOverlayLoader.load(overlayFilename, cpg)
    }

}
