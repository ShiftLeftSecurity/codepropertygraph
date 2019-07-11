package io.shiftleft.codepropertygraph.cpgloading

import java.io.{File, FileInputStream}
import java.nio.file.{Files, Path}
import java.util.{HashMap => JHashMap, Map => JMap}

import gnu.trove.set.TLongSet
import gnu.trove.set.hash.TLongHashSet
import io.shiftleft.proto.cpg.Cpg.CpgStruct
import org.apache.logging.log4j.LogManager
import org.apache.tinkerpop.gremlin.tinkergraph.storage.OndiskOverflow

import scala.collection.JavaConverters._
import resource.managed

/**
  * converts cpg.bin.zip proto to OverflowDb, so we don't need to first import into Tinkergraph and then serialize it out again
  * Useful especially for large cpgs. Uses scala parallel collections.
  *
  * Usage example: create the overflowdb and load it in ocular. This will load all references in memory, and accessed elements/properties will be lazily fetched from disk.
  * ./proto2overflowdb.sh --cpg cpg.bin.zip --out overflowdb.bin
  * ./ocular.sh
  * Cpg.withStorage("overflowdb.bin")
  */
object ProtoToOverflowDb extends App {
  type NodeId = java.lang.Long
  type EdgeLabel = String

  private lazy val logger = LogManager.getLogger(getClass)
  private lazy val edgeSerializer = new ProtoEdgeSerializer
  private lazy val nodeFilter = new NodeFilter

  parseConfig.map(run)

  def run(config: Config): File = {
    val writeTo = config.writeTo.getOrElse(new File("overflowdb.bin"))
    logger.info(s"running ProtoToOverflowDb with cpg=${config.cpg}; writing results to $writeTo")
    if (writeTo.exists) writeTo.delete()
    val start = System.currentTimeMillis

    for (overflowDb <- managed(OndiskOverflow.createWithSpecificLocation(writeTo));
         zipArchive <- managed(new ZipArchive(config.cpg.getAbsolutePath))) {
      zipArchive.getFileEntries.asScala.par.foreach(importProtoBin(overflowDb))
    }
    logger.info("OverflowDb construction finished in " + (System.currentTimeMillis - start) + "ms.")
    writeTo
  }

  private def importProtoBin(overflowDb: OndiskOverflow)(protoFile: Path): Unit =
    for (inputStream <- managed(Files.newInputStream(protoFile))) {
      importCpgStruct(CpgStruct.parseFrom(inputStream), overflowDb)
    }

  private def importCpgStruct(cpgProto: CpgStruct, overflowDb: OndiskOverflow): Unit = {

    /** cpg proto nodes don't know their adjacent edges, but those are required for the OverflowDb serializer,
      * so we need to build some helper maps to import the nodes */
    val inEdgesByNodeId: JMap[NodeId, JMap[EdgeLabel, TLongSet]] = new JHashMap
    val outEdgesByNodeId: JMap[NodeId, JMap[EdgeLabel, TLongSet]] = new JHashMap

    cpgProto.getEdgeList.asScala.par.foreach { edge =>
      val edgeWithId = new ProtoEdgeWithId(edge)
      val label = edge.getType.name
      inEdgesByNodeId
        .computeIfAbsent(edgeWithId.edge.getSrc, _ => new JHashMap)
        .computeIfAbsent(label, _ => new TLongHashSet)
        .add(edgeWithId.id)
      outEdgesByNodeId
        .computeIfAbsent(edgeWithId.edge.getDst, _ => new JHashMap)
        .computeIfAbsent(label, _ => new TLongHashSet)
        .add(edgeWithId.id)
      overflowDb.getEdgeMVMap.put(edgeWithId.id, edgeSerializer.serialize(edgeWithId))
    }

    val nodeSerializer = new ProtoNodeSerializer(inEdgesByNodeId, outEdgesByNodeId)
    cpgProto.getNodeList.asScala.par.filter(nodeFilter.filterNode).foreach { node =>
      overflowDb.getVertexMVMap.put(node.getKey, nodeSerializer.serialize(node))
    }
  }

  private def parseConfig: Option[Config] = {
    new scopt.OptionParser[Config](getClass.getSimpleName) {
      opt[File]("cpg").required
        .action((x, c) => c.copy(cpg = x))
        .text("path to cpg.bin.zip")
      opt[File]("out")
        .action((x, c) => c.copy(writeTo = Some(x)))
        .text("where to write overflowdb.bin output to (will delete if exists)")
      help("help").text("prints this usage text")
    }.parse(args, Config(cpg = null))
  }

}

case class Config(cpg: File, writeTo: Option[File] = None)
