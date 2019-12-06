package io.shiftleft.codepropertygraph.cpgloading

import java.nio.file.FileSystemNotFoundException

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeys
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import io.shiftleft.overflowdb.OdbGraph

object CpgLoader {

  /**
    * Load a Code Property Graph
    *
    * @param filename name of file that stores the code property graph
    * @param config loader configuration
    * @throws FileSystemNotFoundException if filename refers to a non-existing file
    */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig()): Cpg =
    new CpgLoader().load(filename, config)

  /**
    * Create any indexes necessary for quick access.
    *
    * @param cpg the CPG to create indexes in
    */
  def createIndexes(cpg: Cpg): Unit =
    new CpgLoader().createIndexes(cpg)

  /**
    * Load and apply overlays from archives to the given CPG.
    *
    * @param overlayFilenames filenames of proto archives
    * @param cpg The CPG to apply overlays to
    * */
  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg): Unit =
    new CpgLoader().addOverlays(overlayFilenames, cpg)
}

private class CpgLoader {

  private val logger = LogManager.getLogger(getClass)

  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.withoutOverflow): Cpg = {
    logger.debug("Loading " + filename)

    val cpg =
      ProtoCpgLoader.loadFromProtoZip(filename, config.overflowDbConfig)
    if (config.createIndexes) { createIndexes(cpg) }
    cpg
  }

  def createIndexes(cpg: Cpg): Unit =
    cpg.graph.asInstanceOf[OdbGraph].createIndex(NodeKeys.FULL_NAME.name, classOf[Vertex])

  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg): Unit = {
    overlayFilenames.foreach { overlayFilename =>
      CpgOverlayLoader.load(overlayFilename, cpg)
    }
  }

}
