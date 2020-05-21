package io.shiftleft.semanticcpg.passes.linking.linker

import gremlin.scala._
import io.shiftleft.codepropertygraph.generated._
import io.shiftleft.passes.{CpgPass, DiffGraph}
import io.shiftleft.semanticcpg.language.Steps
import io.shiftleft.Implicits.JavaIteratorDeco
import io.shiftleft.codepropertygraph.Cpg
import org.apache.tinkerpop.gremlin.structure.Direction
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable

object Linker {
  private val logger: Logger = LogManager.getLogger(this)

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
  def logFailedSrcLookup(edgeType: String,
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
