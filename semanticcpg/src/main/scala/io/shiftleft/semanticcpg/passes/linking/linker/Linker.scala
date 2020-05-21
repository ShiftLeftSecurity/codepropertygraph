package io.shiftleft.semanticcpg.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.tinkerpop.gremlin.structure.VertexProperty.Cardinality
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable
import scala.jdk.CollectionConverters._

/**
  * This pass has MethodStubCreator and TypeDeclStubCreator as prerequisite for
  * language frontends which do not provide method stubs and type decl stubs.
  */
class Linker(cpg: Cpg) extends CpgPass(cpg) {
  import Linker.linkToSingle, Linker.logFailedSrcLookup, Linker.logFailedDstLookup, Linker.logger

  private val typeDeclFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val typeFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val methodFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]
  private val namespaceBlockFullNameToNode = mutable.Map.empty[String, nodes.StoredNode]

  override def run(): Iterator[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder

    initMaps()

    def initMaps(): Unit = {
      cpg.graph.graph.vertices().asScala.foreach {
        case node: nodes.TypeDecl       => typeDeclFullNameToNode += node.fullName -> node
        case node: nodes.Type           => typeFullNameToNode += node.fullName -> node
        case node: nodes.Method         => methodFullNameToNode += node.fullName -> node
        case node: nodes.NamespaceBlock => namespaceBlockFullNameToNode += node.fullName -> node
        case _                          => // ignore
      }
    }

    val graphsFromAstPass = linkAstChildToParent()

    graphsFromAstPass ++ Iterator(dstGraph.build())
  }

  private def linkToMultiple[SRC_NODE_TYPE <: nodes.StoredNode](srcLabels: List[String],
                                                                dstNodeLabel: String,
                                                                edgeType: String,
                                                                dstNodeMap: mutable.Map[String, nodes.StoredNode],
                                                                getDstFullNames: SRC_NODE_TYPE => Iterable[String],
                                                                dstFullNameKey: String): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false

    def nodeIterator =
      cpg.graph.V
        .hasLabel(srcLabels.head, srcLabels.tail: _*)

    nodeIterator
      .map {
        case srcNode: SRC_NODE_TYPE @unchecked =>
          val dstGraph = DiffGraph.newBuilder
          if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
            getDstFullNames(srcNode).foreach { dstFullName =>
              dstNodeMap.get(dstFullName) match {
                case Some(dstNode) =>
                  dstGraph.addEdgeInOriginal(srcNode, dstNode, edgeType)
                case None =>
                  logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
              }
            }
          } else {
            val dstFullNames = srcNode
              .vertices(Direction.OUT, edgeType)
              .asScala
              .map(_.value2(NodeKeys.FULL_NAME))
              .iterator
              .to(Iterable)
            srcNode.removeProperty(Key(dstFullNameKey))
            dstFullNames.foreach { name =>
              srcNode.property(Cardinality.list, dstFullNameKey, name)
            }
            if (!loggedDeprecationWarning) {
              logger.warn(
                s"Using deprecated CPG format with already existing $edgeType edge between" +
                  s" a source node of type $srcLabels and a $dstNodeLabel node.")
              loggedDeprecationWarning = true
            }
          }
          dstGraph.build
      }
      .toIterator()
  }

  /**
    * For each node and type decl, check if there is an incoming AST edge.
    * If there is not, look up parent node according to `parentType` field
    * in the corresponding table and add an AST edge from parent to child
    * */
  private def linkAstChildToParent(): Iterator[DiffGraph] = {
    type ChildType = nodes.HasAstParentType with nodes.HasAstParentFullName with nodes.StoredNode

    def nodeIterator: Iterator[ChildType] =
      cpg.graph.V
        .hasLabel(NodeTypes.METHOD, NodeTypes.TYPE_DECL)
        .toIterator()
        .map(_.asInstanceOf[ChildType])

    def runOnPart(astChild: ChildType): DiffGraph = {
      val dstGraph = DiffGraph.newBuilder
      try {
        astChild.edges(Direction.IN, EdgeTypes.AST).nextOption match {
          case None =>
            val astParentOption: Option[nodes.StoredNode] =
              astChild.astParentType match {
                case NodeTypes.METHOD          => methodFullNameToNode.get(astChild.astParentFullName)
                case NodeTypes.TYPE_DECL       => typeDeclFullNameToNode.get(astChild.astParentFullName)
                case NodeTypes.NAMESPACE_BLOCK => namespaceBlockFullNameToNode.get(astChild.astParentFullName)
                case _ =>
                  logger.error(
                    s"Invalid AST_PARENT_TYPE=${astChild.valueOption(NodeKeys.AST_PARENT_FULL_NAME)};" +
                      s" astChild LABEL=${astChild.label};" +
                      s" astChild FULL_NAME=${astChild.valueOption(NodeKeys.FULL_NAME)}")
                  None
              }

            astParentOption match {
              case Some(astParent) =>
                dstGraph.addEdgeInOriginal(astParent, astChild, EdgeTypes.AST)
              case None =>
                logFailedSrcLookup(EdgeTypes.AST,
                                   astChild.astParentType,
                                   astChild.astParentFullName,
                                   astChild.label,
                                   astChild.id.toString())
            }
          case _ =>
        }
      } catch {
        case _: NoSuchElementException =>
          logger.info("No such element in `linkAstChildToParent`. Tinkerpop used to not tell us, now we know.")
      }
      dstGraph.build()
    }

    nodeIterator.map(runOnPart)
  }
}

object Linker {
  private val logger: Logger = LogManager.getLogger(classOf[Linker])

  /**
    * For all nodes `n` with a label in `srcLabels`, determine
    * the value of `n.\$dstFullNameKey`, use that to lookup the
    * destination node in `dstNodeMap`, and create an edge of type
    * `edgeType` between `n` and the destination node.
    * */
  def linkToSingle(
      cpg: Cpg,
      srcLabels: List[String],
      dstNodeLabel: String,
      edgeType: String,
      dstNodeMap: mutable.Map[String, nodes.StoredNode],
      dstFullNameKey: String,
      dstNotExistsHandler: Option[(nodes.StoredNode, String, DiffGraph.Builder) => Unit]): Iterator[DiffGraph] = {
    var loggedDeprecationWarning = false

    val dstGraph = DiffGraph.newBuilder

    def sourceIterator = {
      val sourceTraversal = cpg.graph.V.hasLabel(srcLabels.head, srcLabels.tail: _*)
      new Steps(sourceTraversal).toIterator()
    }

    sourceIterator.foreach { srcNode =>
      // If the source node does not have any outgoing edges of this type
      // This check is just required for backward compatibility
      if (!srcNode.edges(Direction.OUT, edgeType).hasNext) {
        srcNode.valueOption[String](dstFullNameKey).foreach { dstFullName =>
          // for `UNKNOWN` this is not always set, so we're using an Option here
          val srcStoredNode = srcNode.asInstanceOf[nodes.StoredNode]
          val dstNode: Option[nodes.StoredNode] = dstNodeMap.get(dstFullName)
          dstNode match {
            case Some(dstNodeInner) =>
              dstGraph
                .addEdgeInOriginal(srcStoredNode, dstNodeInner, edgeType)
            case None =>
              if (dstNotExistsHandler.isDefined) {
                dstNotExistsHandler.get(srcStoredNode, dstFullName, dstGraph)
              } else {
                logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
              }
          }
        }
      } else {
        val maybeDstFullName = srcNode.vertices(Direction.OUT, edgeType).nextOption.map(_.value2(NodeKeys.FULL_NAME))
        maybeDstFullName match {
          case Some(dstFullName) => srcNode.property(dstFullNameKey, dstFullName)
          case None              => logger.error(s"Missing outgoing edge of type ${edgeType} from node ${srcNode}")
        }
        if (!loggedDeprecationWarning) {
          logger.warn(
            s"Using deprecated CPG format with already existing $edgeType edge between" +
              s" a source node of type $srcLabels and a $dstNodeLabel node.")
          loggedDeprecationWarning = true
        }
      }
    }
    Iterator(dstGraph.build())
  }

  @inline
  def logFailedDstLookup(edgeType: String,
                         srcNodeType: String,
                         srcNodeId: String,
                         dstNodeType: String,
                         dstFullName: String): Unit = {
    logger.error(
      "Could not create edge. Destination lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcNodeId=$srcNodeId, " +
        s"dstNodeType=$dstNodeType, dstFullName=$dstFullName")
  }

  @inline
  private def logFailedSrcLookup(edgeType: String,
                                 srcNodeType: String,
                                 srcFullName: String,
                                 dstNodeType: String,
                                 dstNodeId: String): Unit = {
    logger.error(
      "Could not create edge. Source lookup failed. " +
        s"edgeType=$edgeType, srcNodeType=$srcNodeType, srcFullName=$srcFullName, " +
        s"dstNodeType=$dstNodeType, dstNodeId=$dstNodeId")
  }
}
