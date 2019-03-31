package io.shiftleft

import gremlin.scala.{Graph, ScalaGraph}
import io.shiftleft.codepropertygraph.generated.{NodeKeys, NodeTypes, nodes}
import io.shiftleft.cpgloading.{IgnoredProtoEntries, OnDiskOverflowConfig, ProtoCpgLoader}
import io.shiftleft.layers.enhancedbase.EnhancedBaseCreator
import io.shiftleft.queryprimitives.steps.starters.Cpg
import io.shiftleft.queryprimitives.CpgOverlayLoader
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.apache.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph
import scala.compat.java8.OptionConverters._

object CpgLoader {
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
    * @param filename      name of file that stores the code property graph
    * @param createIndices create indexes after loading the file
    */
  def loadCodePropertyGraph(filename: String,
                            createIndices: Boolean = true,
                            onDiskOverflowConfig: Option[OnDiskOverflowConfig] = None): Cpg = {
    logger.debug("Loading " + filename)
    val cpg = ProtoCpgLoader.loadFromProtoZip(
      filename, onDiskOverflowConfig.asJava, Some(ignoredProtoEntries).asJava)
    runEnhancements(cpg.graph)
    if (createIndices) { createIndexes(cpg) }
    cpg
  }

  protected def runEnhancements(graph: Graph): Unit = {
    val language = metaNode(graph).language
    val serializedCpg = new SerializedCpg
    new EnhancedBaseCreator(graph, language, serializedCpg).create
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

class CpgLoader {}
