package io.shiftleft.codepropertygraph.cpgloading

import com.google.protobuf.GeneratedMessageV3
import flatgraph.*
import flatgraph.misc.ConversionException
import flatgraph.misc.Conversions.toShortSafely
import io.shiftleft.codepropertygraph.generated.Cpg
import io.shiftleft.proto.cpg.Cpg.PropertyValue.ValueCase.*
import io.shiftleft.proto.cpg.Cpg.{CpgOverlay, CpgStruct, PropertyValue}
import io.shiftleft.utils.StringInterner
import org.slf4j.{Logger, LoggerFactory}

import java.io.InputStream
import java.nio.file.{Files, Path}
import java.util.List as JList
import scala.collection.mutable
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}

object ProtoCpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def loadFromProtoZip(fileName: String, storagePath: Option[Path]): Cpg =
    measureAndReport {
      val cpg = openOrCreateCpg(storagePath)
      Using.Manager { use =>
        use(new ZipArchive(fileName)).entries.foreach { entry =>
          val inputStream = use(Files.newInputStream(entry))
          val protoCpg   = getNextProtoCpgFromStream(inputStream)
          importProtoIntoCpg(protoCpg, cpg)
        }
      }.get
      cpg
    }

  def loadFromListOfProtos(protoCpgs: Seq[CpgStruct], storagePath: Option[Path]): Cpg = {
    val cpg = openOrCreateCpg(storagePath)
    protoCpgs.foreach { protoCpg =>
      importProtoIntoCpg(protoCpg, cpg)
    }
    cpg
  }
  
  private def openOrCreateCpg(storagePath: Option[Path]): Cpg = {
    storagePath match {
      case Some(storagePath) => Cpg.withStorage(storagePath)
      case None => Cpg.empty
    }
  }

  def importProtoIntoCpg(protoCpg: CpgStruct, cpg: Cpg, nodeFilter: NodeFilter = new NodeFilter): Unit = {
    // TODO use centralised string interner everywhere, maybe move to flatgraph core - keep in mind strong references / GC.
    implicit val interner: StringInterner = StringInterner.makeStrongInterner()

    def nodesIter = protoCpg.getNodeList.iterator().asScala.filter(nodeFilter.filterNode)

    // we need to run two passes of DiffGraphs: one to add the nodes, and one to add edges, set node properties etc
    val protoNodeIdToGNode = addNodesRaw(nodesIter, cpg.graph)

    // add vertex properties
    val diffGraph = Cpg.newDiffGraphBuilder
    nodesIter.foreach { protoNode =>
      val protoNodeId = protoNode.getKey
      lazy val gNode = protoNodeIdToGNode.get(protoNodeId)
        .getOrElse(throw new ConversionException(s"node with proto node id=$protoNodeId not found in graph"))
      protoNode.getPropertyList.iterator().asScala.foreach { protoProperty =>
        diffGraph.setNodeProperty(gNode, protoProperty.getName.name(), extractPropertyValue(protoProperty.getValue()))
      }
    }

    protoCpg.getEdgeList.iterator().asScala.foreach { protoEdge =>
      List(protoEdge.getSrc, protoEdge.getDst).map(protoNodeIdToGNode.get) match {
        case List(Some(srcNode), Some(dstNode)) =>
          diffGraph.addEdge(srcNode, dstNode, protoEdge.getType.name(), extractEdgePropertyValue(protoEdge))
        case _ => // at least one of the nodes doesn't exist in the cpg, most likely because it was filtered out - ignore
      }
    }
    DiffGraphApplier.applyDiff(cpg.graph, diffGraph)
  }

  private def addNodesRaw(protoNodes: Iterator[CpgStruct.Node], graph: Graph): Map[Long, GNode] = {
    val diffGraphForRawNodes  = new DiffGraphBuilder(graph.schema)
    val protoNodeIdToGNode = mutable.Map.empty[Long, GenericDNode]
    protoNodes.foreach { protoNode =>
      val newNode = new GenericDNode(graph.schema.getNodeKindByLabel(protoNode.getType.name()).toShortSafely)
      diffGraphForRawNodes.addNode(newNode)
      protoNodeIdToGNode.put(protoNode.getKey, newNode)
    }
    DiffGraphApplier.applyDiff(graph, diffGraphForRawNodes)
    protoNodeIdToGNode.view.mapValues(_.storedRef.get).toMap
  }

  private def extractPropertyValue(value: PropertyValue)(implicit interner: StringInterner): Any = {
    value.getValueCase match {
      case INT_VALUE => value.getIntValue
      case BOOL_VALUE => value.getBoolValue
      case STRING_VALUE => interner.intern(value.getStringValue)
      case STRING_LIST => value.getStringList.getValuesList.asScala.map(interner.intern).toList
      case VALUE_NOT_SET => null
      case _ => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
    }
  }

  /** flatgraph only supports 0..1 edge properties */
  private def extractEdgePropertyValue(protoEdge: CpgStruct.Edge)(implicit interner: StringInterner): Any = {
    val protoProperties = protoEdge.getPropertyList.asScala
    if (protoProperties.isEmpty)
      null
    else if (protoProperties.size == 1)
      extractPropertyValue(protoProperties.head.getValue)
    else
      throw new IllegalArgumentException(s"flatgraph only supports zero or one edge properties, but the given edge has ${protoProperties.size} properties: $protoProperties")
  }

   def loadFromListOfProtos(cpgs: JList[CpgStruct], storagePath: Option[Path]): Cpg =
     loadFromListOfProtos(cpgs.asScala.toSeq, storagePath)

   def loadOverlays(fileName: String): Try[Iterator[CpgOverlay]] =
     loadOverlays(fileName, CpgOverlay.parseFrom)

  private def loadOverlays[T <: GeneratedMessageV3](fileName: String, f: InputStream => T): Try[Iterator[T]] =
    Using(new ZipArchive(fileName)) { zip =>
      zip.entries
        .sortWith(compareOverlayPath)
        .map { path =>
          val is = Files.newInputStream(path)
          f(is)
        }
        .iterator
    }

  private def compareOverlayPath(a: Path, b: Path): Boolean = {
    val file1Split: Array[String] = a.toString.replace("/", "").split("_")
    val file2Split: Array[String] = b.toString.replace("/", "").split("_")
    if (file1Split.length < 2 || file2Split.length < 2)
      a.toString < b.toString
    else
      file1Split(0).toInt < file2Split(0).toInt
  }

  private def getNextProtoCpgFromStream(inputStream: InputStream) =
    CpgStruct.parseFrom(inputStream)

  private def measureAndReport[A](f: => A): A = {
    val start  = System.currentTimeMillis()
    val result = f
    logger.info("CPG construction finished in " + (System.currentTimeMillis() - start) + "ms.")
    result
  }
}
