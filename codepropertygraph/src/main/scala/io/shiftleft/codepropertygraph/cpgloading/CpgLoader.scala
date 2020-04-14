package io.shiftleft.codepropertygraph.cpgloading

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
    */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig()): Cpg =
    new CpgLoader().load(filename, config)

  /**
    * Load Code Property Graph from an overflow DB file
    *
    * @param config loader config
    *
    *               This methods loads the CPG from an existing overflow DB file,
    *               specified in config.overflowDbConfig. In particular, this config
    *               specifies the filename. For example, to load the database at "foo.db",
    *               you can issue the following:
    *
    * val odbConfig = OdbConfig.withDefaults().withStorageLocation(config.spPath)
    * val config = CpgLoaderConfig().withOverflowConfig(odbConfig)
    * CpgLoader.loadFromOverflowDb(config)
    * */
  def loadFromOverflowDb(config: CpgLoaderConfig = CpgLoaderConfig()): Cpg = {
    new CpgLoader().loadFromOverflowDb(config)
  }

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

  def addDiffGraphs(diffGraphFilenames : Seq[String], cpg : Cpg) : Unit =
    new CpgLoader().addDiffGraphs(diffGraphFilenames, cpg)

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

  def loadFromOverflowDb(config: CpgLoaderConfig = CpgLoaderConfig()): Cpg = {
    val odbGraph =
      OdbGraph.open(
        config.overflowDbConfig,
        io.shiftleft.codepropertygraph.generated.nodes.Factories.allAsJava,
        io.shiftleft.codepropertygraph.generated.edges.Factories.allAsJava
      )
    val cpg = Cpg(odbGraph)
    if (config.createIndexes) { createIndexes(cpg) }
    cpg
  }

  def createIndexes(cpg: Cpg): Unit =
    cpg.graph.asInstanceOf[OdbGraph].indexManager.createNodePropertyIndex(NodeKeys.FULL_NAME.name)

  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg): Unit = {
    overlayFilenames.foreach { overlayFilename =>
      CpgOverlayLoader.load(overlayFilename, cpg)
    }
  }

  def addDiffGraphs(diffGraphFilenames: Seq[String], cpg: Cpg): Unit = {
    diffGraphFilenames.foreach { filename =>
      CpgOverlayLoader.loadInverse(filename, cpg)
    }
  }

}
