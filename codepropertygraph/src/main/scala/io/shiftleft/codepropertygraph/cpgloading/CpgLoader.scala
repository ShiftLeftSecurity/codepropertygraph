package io.shiftleft.codepropertygraph.cpgloading

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.generated.NodeKeys
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

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

  /**
    * Create any indexes necessary for quick access.
    *
    * @param cpg the CPG to create indexes in
    */
  def createIndexes(cpg: Cpg): Unit = {
    new CpgLoader().createIndexes(cpg)
  }

  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg): Unit = {
    new CpgLoader().addOverlays(overlayFilenames, cpg)
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

    import scala.compat.java8.OptionConverters._

    val cpg =
      ProtoCpgLoader.loadFromProtoZip(filename, config.onDiskOverflowConfig.asJava)
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

  def addOverlays(overlayFilenames: Seq[String], cpg: Cpg): Unit = {
    overlayFilenames.foreach { overlayFilename =>
      CpgOverlayLoader.load(overlayFilename, cpg)
    }
  }

}
