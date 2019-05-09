package io.shiftleft.cpgloading

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.SerializedCpg
import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.codepropertygraph.cpgloading.{IgnoredProtoEntries, OnDiskOverflowConfig, ProtoCpgLoader}
import io.shiftleft.semanticsloader.{ArgumentDefs, SemanticsLoader}
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.layers.enhancedbase.EnhancedBaseCreator
import io.shiftleft.queryprimitives.CpgOverlayLoader
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph

import scala.compat.java8.OptionConverters._

object CpgLoaderConfig {

  def default: CpgLoaderConfig =
    CpgLoaderConfig(semanticsFilename = None, createIndices = true, onDiskOverflowConfig = None)
}

/**
  * Configuration for the CPG loader
  * @param semanticsFilename filename of the file containing taint semantics
  * @param createIndices indicate whether to create indices or not
  * @param onDiskOverflowConfig configuration for the on-disk-overflow feature
  *  */
case class CpgLoaderConfig(var semanticsFilename: Option[String],
                           var createIndices: Boolean,
                           var onDiskOverflowConfig: Option[OnDiskOverflowConfig]) {}

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
    * Load a Code Property Graph
    *
    * @param filename      name of file that stores the code property graph
    * @param semanticsFilename file containing argument definition entries
    * @param createIndices whether or not to create indices
    * @param onDiskOverflowConfig for the on-disk-overflow feature
    */
  @deprecated("this method will be removed", "codepropertygraph")
  def loadCodePropertyGraph(filename: String,
                            semanticsFilename: Option[String] = None,
                            createIndices: Boolean = true,
                            onDiskOverflowConfig: Option[OnDiskOverflowConfig] = None): Cpg = {
    new CpgLoader().loadCodePropertyGraph(filename, semanticsFilename, createIndices, onDiskOverflowConfig)
  }

}

private class CpgLoader {

  private val logger = LogManager.getLogger(getClass)

  /* some non-public frontends (e.g. java2cpg) use cpg proto entries that are `UNRECOGNIZED` by cpg-public.
   * we'll ignore those during the import */
  val ignoredProtoEntries =
    IgnoredProtoEntries(
      nodeTypes = Set(5, 6, 7, 49),
      nodeKeys = Set(14, 16)
    )

  /**
    * Load a Code Property Graph
    *
    * @param filename name of file that stores the code property graph
    * @param config loader configuration
    * */
  def load(filename: String, config: CpgLoaderConfig = CpgLoaderConfig.default): Cpg = {
    logger.debug("Loading " + filename)
    val argumentDefs =
      if (config.semanticsFilename.isDefined) {
        val argumentDefLoader = new SemanticsLoader(config.semanticsFilename.get)
        argumentDefLoader.load()
      } else {
        ArgumentDefs(Nil)
      }
    val cpg =
      ProtoCpgLoader.loadFromProtoZip(filename, config.onDiskOverflowConfig.asJava, Some(ignoredProtoEntries).asJava)
    runEnhancements(cpg.graph, argumentDefs)
    if (config.createIndices) { createIndexes(cpg) }
    cpg
  }

  /**
    * Load a Code Property Graph
    *
    * @param filename      name of file that stores the code property graph
    * @param semanticsFilename file containing argument definition entries
    * @param createIndices whether or not to create indices
    * @param onDiskOverflowConfig for the on-disk-overflow feature
    */
  @deprecated("this method will be removed", "codepropertygraph")
  def loadCodePropertyGraph(filename: String,
                            semanticsFilename: Option[String],
                            createIndices: Boolean,
                            onDiskOverflowConfig: Option[OnDiskOverflowConfig]): Cpg = {

    val config = new CpgLoaderConfig(semanticsFilename, createIndices, onDiskOverflowConfig)
    load(filename, config)
  }

  protected def runEnhancements(graph: Graph, argumentDefs: ArgumentDefs): Unit = {
    val language = metaNode(graph).language
    val serializedCpg = new SerializedCpg
    new EnhancedBaseCreator(graph, language, serializedCpg, argumentDefs).create
  }

  private def metaNode(graph: ScalaGraph): nodes.MetaData = {
    graph.V
      .hasLabel(NodeTypes.META_DATA)
      .headOption()
      .getOrElse(throw new Exception("Meta node missing."))
      .asInstanceOf[nodes.MetaData]
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
