package io.shiftleft.semanticcpg.passes.linking.linker

import io.shiftleft.codepropertygraph.Cpg
import io.shiftleft.passes.{DiffGraph, ParallelCpgPass}
import io.shiftleft.semanticcpg.language.Steps
import gremlin.scala._
import io.shiftleft.codepropertygraph.generated.{NodeKeys, nodes}
import org.apache.tinkerpop.gremlin.structure.Direction
import io.shiftleft.Implicits.JavaIteratorDeco
import org.apache.logging.log4j.{LogManager, Logger}

import scala.collection.mutable

class LinkToSinglePass(cpg: Cpg,
                       srcLabels: List[String],
                       dstNodeLabel: String,
                       edgeType: String,
                       dstNodeMap: mutable.Map[String, nodes.StoredNode],
                       dstFullNameKey: String)
    extends ParallelCpgPass[Vertex](cpg) {

  private val logger: Logger = LogManager.getLogger(classOf[LinkToSinglePass])
  private var loggedDeprecationWarning = false

  def dstNotExistsHandler(srcNode: nodes.StoredNode, dstFullName: String, diffGraph: DiffGraph.Builder): Unit = {
    LinkerShared.logFailedDstLookup(edgeType, srcNode.label, srcNode.id.toString, dstNodeLabel, dstFullName)
  }

  override def partIterator: Iterator[Vertex] = {
    val sourceTraversal = cpg.graph.V.hasLabel(srcLabels.head, srcLabels.tail: _*)
    new Steps(sourceTraversal).toIterator()
  }

  override def runOnPart(srcNode: Vertex): Option[DiffGraph] = {
    val dstGraph = DiffGraph.newBuilder
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
            dstNotExistsHandler(srcStoredNode, dstFullName, dstGraph)
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
    Some(dstGraph.build())
  }
}
