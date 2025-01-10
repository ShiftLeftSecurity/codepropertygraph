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
import scala.jdk.CollectionConverters.*
import scala.util.{Try, Using}

object ProtoCpgLoader {
  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def loadFromProtoZip(fileName: String, storagePath: Option[Path]): Cpg = {
    measureAndReport {
      Using.Manager { use =>
        def cpgProtos: Iterator[CpgStruct] = {
          use(new ZipArchive(fileName)).entries.iterator.map { entry =>
            val inputStream = use(Files.newInputStream(entry))
            CpgStruct.parseFrom(inputStream)
          }
        }
        loadFromListOfProtos(() => cpgProtos, storagePath)
      }.get
    }
  }

  /** @param protoCpgs
    *   is a function because we need to run two passes due to flatgraph-specific implementation details: one to add the
    *   nodes, and one to add edges and set node properties
    */
  def loadFromListOfProtos(protoCpgs: () => Iterator[CpgStruct], storagePath: Option[Path]): Cpg = {
    // TODO use centralised string interner everywhere, maybe move to flatgraph core - keep in mind strong references / GC.
    implicit val interner: StringInterner = StringInterner.makeStrongInterner()
    val protoToGraphNodeMappings          = new ProtoToGraphNodeMappings
    val cpg                               = openOrCreateCpg(storagePath)

    // first pass: add the raw nodes without any properties or edges
    addNodesRaw(protoCpgs().flatMap(cpgProto => nodesIter(cpgProto)), cpg.graph, protoToGraphNodeMappings)

    // second pass: set node properties and add edges
    val diffGraph = Cpg.newDiffGraphBuilder
    protoCpgs().foreach { protoCpg =>
      nodesIter(protoCpg).foreach { protoNode =>
        val protoNodeId = protoNode.getKey
        lazy val gNode = protoToGraphNodeMappings
          .findGNode(protoNode)
          .getOrElse(throw new ConversionException(s"node with proto node id=$protoNodeId not found in graph"))
        protoNode.getPropertyList.iterator().asScala.foreach { protoProperty =>
          diffGraph.setNodeProperty(gNode, protoProperty.getName.name(), extractPropertyValue(protoProperty.getValue()))
        }
      }

      protoCpg.getEdgeList.iterator().asScala.foreach { protoEdge =>
        List(protoEdge.getSrc, protoEdge.getDst).map(protoToGraphNodeMappings.findGNode) match {
          case List(Some(srcNode), Some(dstNode)) =>
            diffGraph.addEdge(srcNode, dstNode, protoEdge.getType.name(), extractEdgePropertyValue(protoEdge))
          case _ => // at least one of the nodes doesn't exist in the cpg, most likely because it was filtered out - ignore
        }
      }
    }

    DiffGraphApplier.applyDiff(cpg.graph, diffGraph)
    cpg
  }

  def loadFromListOfProtos(cpgs: java.util.List[CpgStruct], storagePath: Option[Path]): Cpg =
    loadFromListOfProtos(() => cpgs.asScala.iterator, storagePath)

  private def openOrCreateCpg(storagePath: Option[Path]): Cpg = {
    storagePath match {
      case Some(storagePath) => Cpg.withStorage(storagePath)
      case None              => Cpg.empty
    }
  }

  private def nodesIter(protoCpg: CpgStruct): Iterator[CpgStruct.Node] =
    protoCpg.getNodeList.iterator().asScala

  private def addNodesRaw(
    protoNodes: Iterator[CpgStruct.Node],
    graph: Graph,
    protoToGraphNodeMappings: ProtoToGraphNodeMappings
  ): Unit = {
    val diffGraph = Cpg.newDiffGraphBuilder
    protoNodes.filterNot(protoToGraphNodeMappings.contains).foreach { protoNode =>
      val label = protoNode.getType.name()
      graph.schema.getNodeKindByLabelMaybe(label) match {
        case None =>
          logger.warn(s"nodeKind for label=`$label` not found - is this a valid proto cpg?")
        case Some(nodeKind) =>
          val newNode = new GenericDNode(graph.schema.getNodeKindByLabel(protoNode.getType.name()).toShortSafely)
          diffGraph.addNode(newNode)
          protoToGraphNodeMappings.add(protoNode, newNode)
      }
    }
    DiffGraphApplier.applyDiff(graph, diffGraph)
  }

  private def extractPropertyValue(value: PropertyValue)(implicit interner: StringInterner): Any = {
    value.getValueCase match {
      case INT_VALUE     => value.getIntValue
      case BOOL_VALUE    => value.getBoolValue
      case STRING_VALUE  => interner.intern(value.getStringValue)
      case STRING_LIST   => value.getStringList.getValuesList.asScala.map(interner.intern).toList
      case VALUE_NOT_SET => null
      case _             => throw new RuntimeException("Error: unsupported property case: " + value.getValueCase.name)
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
      throw new IllegalArgumentException(
        s"flatgraph only supports zero or one edge properties, but the given edge has ${protoProperties.size} properties: $protoProperties"
      )
  }

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

  private def measureAndReport[A](f: => A): A = {
    val start  = System.currentTimeMillis()
    val result = f
    logger.info("CPG construction finished in " + (System.currentTimeMillis() - start) + "ms.")
    result
  }
}
